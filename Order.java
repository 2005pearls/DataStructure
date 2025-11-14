package draft;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Order {

    private static class OrderItem {
        Product product;
        int qty;
        
        OrderItem(Product p, int q) { 
            product = p; qty = q; }
        
        double lineTotal() {
            return product.getPrice() * qty; }
        
        
        void display() {
            System.out.println(" - " + product.getName() + " (x" + qty + ")  price=" + product.getPrice() +"  lineTotal=" + String.format("%.2f", lineTotal()));
        }
    }

    
    
    private int orderID;
    private Customer customer;                     // reference
     private LinkedList<OrderItem> items = new LinkedList<>();
     private double totalPrice;
     private String orderDate;                      // yyyy-MM-dd
     private String status;                         // pending/shipped/delivered/canceled

    private static LinkedList<Order> allOrders = new LinkedList<>();
// to format the date and make it acceptable
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
// create with device date
    public Order(int orderID, Customer customer, String status) {
        this.orderID   = orderID;
        this.customer  = customer;
        this.status    = status;
        this.orderDate = LocalDate.now().format(DF);
        this.totalPrice = 0.0;
    }
// create with manual date input 

    public Order(int orderID, Customer customer, String status, String ymd) {
        this.orderID   = orderID;
        this.customer  = customer;
        this.status    = status;
        this.orderDate = ymd;
        this.totalPrice = 0.0;
    }

    //  getters 
    public int getOrderID()      { return orderID; }
    public String getStatus()    { return status; }
    public String getOrderDate() { return orderDate; }
    public double getTotalPrice(){ return totalPrice; }
    public Customer getCustomer(){ return customer; }

    //  add obj at the end of the list 
    private static void insertAtEnd(Order o) {
        if (allOrders.empty()) allOrders.insert(o);
        else {
            allOrders.findFirst();
            while (!allOrders.last()) allOrders.findNext();
            allOrders.insert(o);
        }
    }

    //  search / create / cancel / update 
    public static Order searchById(int id) {
        if (allOrders.empty()) 
            return null;
        allOrders.findFirst();
        while (true) {
            Order cur = allOrders.retrieve();
            if (cur.orderID == id) return cur;  // current stops here
            if (allOrders.last()) break;
            allOrders.findNext();
        }
        return null;
    }

    //Create order and register 
    public static Order create(int orderID, int customerID, String status) {
        if (searchById(orderID) != null) {
            System.out.println("Order " + orderID + " already exists!");
            return null;
        }
        
        Customer c = Customer.searchById(customerID);
        if (c == null) {
            System.out.println("Customer " + customerID + " not found.");
            return null;
            
        }
        
        Order o = new Order(orderID, c, status);
        insertAtEnd(o);
        c.placeOrder(o);
        
        return o;
    }

    // Cancel order
    public void cancel() {
        if ("canceled".equalsIgnoreCase(status)) {
            System.out.println("Order already canceled.");
            return;
        }
        if (!items.empty()) {
            items.findFirst();
            while (true) {
               OrderItem it = items.retrieve();
              it.product.setStock(it.product.getStock() + it.qty);
              if (items.last()) break;
               items.findNext();
            }
        }
        status = "canceled";
       System.out.println("Order " + orderID + " canceled.");
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("Order " + orderID + " -> status = " + newStatus);
    }

    //add products to the order
    //Add product by ID with qty
    
    public boolean addItemByProductId(int productId, int qty) {
        if (qty <= 0) {
            System.out.println("qty must be > 0");
            return false;
        }
       Product p = Product.searchById(productId);
      if (p == null) {
           System.out.println("Product " + productId + " not found.");
           return false;
        }
        if (p.getStock() < qty) {
           System.out.println("Not enough stock for " + p.getName());
          return false;
        }

        // append item 
        OrderItem it = new OrderItem(p, qty);
        if (items.empty()) items.insert(it);
        else {
            items.findFirst();
            while (!items.last()) items.findNext();
            items.insert(it);
        }

        // update totals
        totalPrice += it.lineTotal();
        p.setStock(p.getStock() - qty);
        return true;
    }

    //  display 
    public void display() {
        System.out.println("Order ID: " + orderID +
               " | date: " + orderDate +
                " | status: " + status +
               " | customer: " + (customer != null ? customer.getName() : "null") +
                " | total: " + String.format("%.2f", totalPrice));
        if (items.empty()) {
            System.out.println("  (no items)");
      
        }else
            
        {
            items.findFirst();
            while (true) {
                items.retrieve().display();
                if (items.last()) break;
                items.findNext();
            }
        }
        System.out.println("------------------------------------");
    }

    //  views and  queries 
    
    //Show all orders of one customer 
    public static void viewOrdersOfCustomer(int customerID) {
        Customer c = Customer.searchById(customerID);
        if (c == null) { System.out.println("Customer not found."); return; }

        System.out.println("\n== Orders for " + c.getName() + " ==");
        boolean any = false;
        if (!allOrders.empty()) {
            allOrders.findFirst();
            while (true) {
                Order o = allOrders.retrieve();
                
                if (o.customer != null && o.customer.getCustomerID() == customerID) {
                    o.display();
                    any = true;
                }
                
                if (allOrders.last()) break;
                allOrders.findNext();
            }
        }
     if (!any) System.out.println("(no orders)");
    }

    // print all orders between two date in this yyyy-MM-dd */
   public static void ordersBetween(String fromYmd, String toYmd) {
        fromYmd = formatDate(fromYmd);
        toYmd = formatDate(toYmd);

        try {
            LocalDate a = LocalDate.parse(fromYmd, DF);
            LocalDate b = LocalDate.parse(toYmd, DF);

            if (a.isAfter(b)) {
                LocalDate t = a;
                a = b;
                b = t;
            }

            System.out.println("\n== All orders ==");

            boolean any = false;

            if (!allOrders.empty()) {
                allOrders.findFirst();
                while (true) {
                    Order o = allOrders.retrieve();
                    String[] orderDetails = o.orderDate.split(",");  
                    LocalDate d = LocalDate.parse(orderDetails[1], DF);  

                    if ((!d.isBefore(a)) && (!d.isAfter(b))) {
                        o.display();  
                        any = true;
                    }
                    if (allOrders.last()) break;
                    allOrders.findNext();
                }
            }

        if (!any) System.out.println("(none)");
        } catch (Exception e) {
            System.out.println("Error parsing dates: " + e.getMessage());
        }
    }
// method make suer fo date format 
    private static String formatDate(String date) {
        if (date.matches("\\d{4}/\\d{1}/\\d{1}")) {
            return date.replaceAll("(\\d{4})/(\\d{1})/(\\d{1})", "$1-0$2-0$3"); 
        } else if (date.matches("\\d{4}-\\d{1}-\\d{1}")) {
            return date.replaceAll("(\\d{4})-(\\d{1})-(\\d{1})", "$1-0$2-0$3"); 
        }
        return date;  
    }


    //  CSV load that includes orderId and customerId and status and yyyy-MM-dd of date 
    public static void load_orders(String fileName) {
        try (Scanner sc = new Scanner(new File(fileName), "UTF-8")) {
            if (sc.hasNextLine()) sc.nextLine(); // skip header
            int cnt = 0;

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

               String[] a = line.split(",", 4);
               int oid = Integer.parseInt(a[0].trim());
                int cid = Integer.parseInt(a[1].trim());
               String status = a[2].trim();
               String date = a[3].trim();

               if (searchById(oid) != null) continue;

             Customer c = Customer.searchById(cid);
              if (c == null) {
                   System.out.println("Skip order " + oid + ": customer " + cid + " not found.");
                   continue;
               }
                Order o = new Order(oid, c, status, date);
               insertAtEnd(o);
                c.placeOrder(o);
                cnt++;
            }
        } catch (Exception e) {
            System.out.println("✗ Error reading orders: " + e.getMessage());
        }
    }
}

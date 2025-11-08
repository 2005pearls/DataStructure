package javaapplication19;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;
//import javafx.util.converter.LocalDateTimeStringConverter;

public class Orders {
    private LinkedList<Order> Allorders;  
    private Customers AllCustomers; 
    static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // لا تغيير هنا

    public Orders(LinkedList<Customer> input_customers, LinkedList<Order> Allorders) {
        AllCustomers = new Customers(input_customers);
        this.Allorders = Allorders;
    }

    
    public Orders() {
        AllCustomers = new Customers();
        Allorders = new LinkedList<>();
    }

    public LinkedList<Order> getOrders() {
        return Allorders;
    }
    
    public Order searchOrderById(int id) {
    if (Allorders.empty()) return null;
    Allorders.findFirst();
    
    while (!Allorders.last()) {
        Order o = Allorders.retrieve();
        if (o.getOrderId() == id) {
            return o;
        }
        Allorders.findNext();
    }
    Order last = Allorders.retrieve();
    if (last.getOrderId() == id) {
        return last;
    }
    return null;
}

 public void assign(Order ord) {
    Customer p = AllCustomers.searchCustomerById(ord.getCustomerId());
    if (p == null) {
        System.out.println("Cannot assign review to Order " + ord.getCustomerId() + " as the customer does not exist.");
    } else {
        p.addOrder(ord);
    }
}

public void addOrder(Order ord) {
    if (searchOrderById(ord.getOrderId()) == null) {
           Allorders.addLast(ord);
         assign(ord);
        System.out.println("Order successfully added: " + ord.getOrderId());
    } else {
        System.out.println("Order with ID " + ord.getOrderId() + " already exists in the system.");
    }
}

public static Order convert_String_to_product(String Line) {
    String[] a = Line.split(",");
    int orderId = Integer.parseInt(a[0].trim().replace("\"", ""));
    int customerId = Integer.parseInt(a[1].trim().replace("\"", ""));
    String productIds = a[2].trim().replace("\"", "");
    double total_Price = Double.parseDouble(a[3]);
    LocalDate date = LocalDate.parse(a[4], df);
    String status = a[5].trim();

    Order o = new Order(orderId, customerId, productIds, total_Price, date, status);
    return o;
}

   
 public void loadOrders(String fileName) {
    try {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        System.out.println("Loading Orders from: " + fileName);
        System.out.println();

        if (scanner.hasNextLine()) { 
            scanner.nextLine(); 
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;  
            }
            Order o = convert_String_to_product(line); // تحويل السطر إلى كائن Order
            addOrder(o); // إضافة الطلب
        }

        scanner.close();
        System.out.println("Orders loaded successfully.");
        System.out.println("------------------------------");
    } catch (Exception e) {
        System.out.println("!! Failed to load orders: " + e.getMessage());
    }
}
   
  public void displayAllOrders() {
    if (Allorders.empty()) {
        System.out.println("No orders found");
        return;
    }

System.out.println("OrderID\tCustomerID\tProductIDs\tTotalPrice\tDate\tStatus");     Allorders.findFirst();
     while (!Allorders.last()) {
       Allorders.retrieve().display();
      Allorders.findNext();
    }
       Allorders.retrieve().display();
}


/*public static void test1() {
    Orders os = new Orders();

    os.addOrder(new Order(501, 101, "201;202;203", 4999.99, LocalDate.of(2024, 1, 1), "Delivered"));
    os.addOrder(new Order(502, 102, "301;302", 1899.50, LocalDate.of(2024, 1, 1), "Pending"));

    os.displayAllOrders();
}

public static void test2() {
    Orders os = new Orders();
    os.loadOrders("orders.csv");
    os.displayAllOrders();
}

public static void main(String[] args) {
    // test1();
    test2();
}*/
}
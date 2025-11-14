package datastructures;
import java.time.LocalDate;
import java.util.Scanner;

public class DataStructures {

    
private static LinkedList<Customer> customers_list;
private static LinkedList<Order> orders_list;
private static LinkedList<Product> products_list;
private static LinkedList<Review> reviews_list;

private static Reviews all_Reviews;
private static Customers all_Customers;
private static Orders all_Orders;
private static Products all_products;



public DataStructures()
{
    customers_list = new LinkedList<Customer>();
    orders_list = new LinkedList<Order>();
    products_list = new LinkedList<Product>();
    reviews_list = new LinkedList<Review>();

    all_products = new Products(products_list);
    all_Customers = new Customers(customers_list);
    all_Orders = new Orders(customers_list, orders_list);
    all_Reviews = new Reviews(reviews_list, products_list, customers_list);
}

public static void Load_all()
{
    all_products.load_products("prodcuts.csv");
    all_Customers.readAllCustomers("customers.csv");
    all_Orders.loadOrders("orders.csv");
    all_Reviews.load_reviews("reviews.csv");
}

    public static void add_Customer(Customer c)
    {
        if (all_Customers != null)
            all_Customers.addCustomer(c);
        else
            System.out.println("⚠️ Error: Customer list not initialized!");
    }

    public static void add_Product(Product p)
    {
        if (all_products != null)
            all_products.addProduct(p);
        else
            System.out.println("⚠️ Error: Product list not initialized!");
    }

    public static void add_Order(Order o)
    {
        if (all_Orders != null)
            all_Orders.addOrder(o);
        else
            System.out.println("⚠️ Error: Orders list not initialized!");
    }

    public static void add_Review(Review r)
    {
        all_Reviews.addReview(r);
    }


public static void displayAllOrders_between2dates(LocalDate d1, LocalDate d2) {
    if (orders_list.empty()) {
        System.out.println("⚠️ No all_orders found!");
        return;
    }
    //System.out.println("orderID\tCustomerID\tproductID\t\tTotalPrice\tDate\t\tStatus");
    System.out.println("----------------------------------------------------");

    // Move to the first order in the list
    orders_list.findFirst();

    while (true) {
        // Retrieve current order
        Order o = orders_list.retrieve();

        // Check if the order date is between d1 and d2
        if (o.getOrderDate().compareTo(d1) > 0 && o.getOrderDate().compareTo(d2) < 0) {
            System.out.println(o.getOrderId());
            // You could also call o.display() here to show full order info
            // o.display();
        }

        // Stop if the current order is the last one in the list
        if (orders_list.last())
            break;

        // Move to the next order
        orders_list.findNext();
    }

    System.out.println("----------------------------------------------------");
}
    
    
public void displayTop3Products() {

    if (products_list.empty()) {
        System.out.println("No products available.");
        return;
    }

    Product max1 = new Product(-1, -1, "no product", -1);
    Product max2 = new Product(-1, -1, "no product", -1);
    Product max3 = new Product(-1, -1, "no product", -1);

    products_list.findFirst();

    while (true) {
        Product cur = products_list.retrieve();

        if (cur.getAverageRating() > max1.getAverageRating()) {
            max3 = max2;
            max2 = max1;
            max1 = cur;

        } else if (cur.getAverageRating() > max2.getAverageRating()) {
            max3 = max2;
            max2 = cur;

        } else if (cur.getAverageRating() > max3.getAverageRating()) {
            max3 = cur;
        }

        if (products_list.last())
            break;
        products_list.findNext();
    }

    System.out.println("\n★ Top Products by Average Rating:\n");

    int rank = 1;

    if (max1 != null) {
        System.out.println(rank++ + ". Product ID: " + max1.getProductId()
                + " | Name: " + max1.getName()
                + " | Avg Rating: " + String.format("%.2f", max1.getAverageRating()));
    }

    if (max2!= null) {
        System.out.println(rank++ + ". Product ID: " + max2.getProductId()
                + " | Name: " + max2.getName()
                + " | Avg Rating: " + String.format("%.2f", max2.getAverageRating()));
    }

    if (max3!= null) {
        System.out.println(rank++ + ". Product ID: " + max3.getProductId()
                + " | Name: " + max3.getName()
                + " | Avg Rating: " + String.format("%.2f", max3.getAverageRating()));
    }

    System.out.println("--------------------------------------------");
}
public void showCommonHighRatedProducts(int c1, int c2) {

    LinkedList<Integer> product_Ids_cust1 = new LinkedList<>();
    LinkedList<Integer> product_Ids_cust2 = new LinkedList<>();

    if (!reviews_list.empty()) {
        reviews_list.findFirst();
        while (true) {
            Review r = reviews_list.retrieve();
            Product p = all_products.Search_Product_by_id(r.getProductID());
            if (p.getAverageRating() > 4) {
                if (r.getCustomerId() == c1) {product_Ids_cust1.insert(r.getProductID());
                }
                if (r.getCustomerId() == c2) {
                    product_Ids_cust2.insert(r.getProductID());
                }
            }
            if (reviews_list.last()) break;
            reviews_list.findNext();
        }
    }

    boolean found = false;
    System.out.println("\n=== Common Products Reviewed by Both Customers (Avg Rating > 4) ===");

    if (!product_Ids_cust1.empty()) {
        product_Ids_cust1.findFirst();
        while (true) {
            int pid = product_Ids_cust1.retrieve();
            // if (contains(product_Ids_cust2, pid))
            if (product_Ids_cust2.exists(pid)) {
                Product p = all_products.Search_Product_by_id(pid);
                if (p != null) {
                    System.out.println("Product ID: " + pid + " * " + p.getProductId() +
                            " * Avg = " + p.getAverageRating());
                    found = true;
                }
            }
            if (product_Ids_cust1.last()) break;
            product_Ids_cust1.findNext();
        }
    }

    if (!found)
        System.out.println("⚠️ No common high-rated products found.");
}

private boolean contains(LinkedList<Integer> list, int id) {
    if (list.empty()) return false;
    list.findFirst();
    while (true) {
        if (list.retrieve() == id) return true;
        if (list.last()) break;
        list.findNext();
    }
    return false;
}

public void testCommonHighRatedProducts() {
    all_products.addProduct(new Product(101, 1500,"Laptop", 10));
    all_products.addProduct(new Product(102, 50, "Mouse", 100));
    all_products.addProduct(new Product(103, 120, "Keyboard", 60));

    all_Reviews.addReview(new Review(1, 101, 201, 5, "Great!"));
    all_Reviews.addReview(new Review(2, 101, 206, 5, "Amazing!"));
    all_Reviews.addReview(new Review(3, 103, 201, 5, "Not good"));
    all_Reviews.addReview(new Review(4, 103, 206, 5, "Good but not perfect"));

    System.out.println("=== Running Test Case for Common High Rated Products ===");
    showCommonHighRatedProducts(201, 206);
}


//----------------------------------------------------------



    public static void main(String[] args) {
        
   
       DataStructures e1 = new DataStructures();
        Scanner input = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("1:Load all files");
        System.out.println("2:add_Product?");
        System.out.println("3:add_Customer?");
        System.out.println("4:add_Order?");
        System.out.println("5:add_Review?");
        System.out.println("6:all_Customers?");
        System.out.println("7:show Suggest top 3 products by average rating");
        System.out.println("8: Show Common High Rated Products (Avg > 4) for 2 Customers");
        System.out.println("9:display all orders between 2 dates");
        System.out.println("10: Exit");
        System.out.print("enter your choice: ");
        
        choice=input.nextInt();

            switch (choice) {
                case 1:
                    Load_all();
                    break;

                case 2:
                    System.out.print("Enter product ID: ");
                    int pid = input.nextInt();
                    input.nextLine(); // consume newline
                    System.out.print("Enter product name: ");
                    String pname = input.nextLine();
                    System.out.print("Enter price: ");
                    double price = input.nextDouble();
                    System.out.print("Enter quantity: ");
                    int quantity = input.nextInt();

                    Product p = new Product(pid, quantity, pname, price);
                    add_Product(p);
                    break;

                case 3:
                    System.out.print("Enter customer ID: ");
                    int cid = input.nextInt();
                    System.out.print("Enter customer name: ");
                    String cname = input.next();
                    System.out.print("Enter customer phone: ");
                    String cphone = input.next();

                    Customer c = new Customer(cid, cname, cphone);
                    add_Customer(c);
                    break;

                case 4:  
                    System.out.print("Enter order ID: ");
                    int oid = input.nextInt();
                    System.out.print("Enter customer ID: ");
                    int ocid = input.nextInt();
                    System.out.print("Enter product ID: ");
                    String opid = input.next();
                    System.out.print("Enter price: ");
                    double total_price = input.nextDouble();
                    System.out.print("Enter date: yyyy-MM-dd ");
                    LocalDate order_Date = LocalDate.parse(input.next());
                    System.out.print("Enter status: ");
                    String status = input.next();
          
                    Order o = new Order(oid, ocid, opid, total_price, order_Date,status );
                    add_Order(o);
                    break;

                case 5:  //need fixing
                    System.out.print("Enter review ID: ");
                    int rid = input.nextInt();
                    System.out.print("Enter customer ID: ");
                    int customerid = input.nextInt();
                    System.out.print("Enter product ID: ");
                    int prid = input.nextInt();
                    System.out.print("Enter rating: ");
                    int rating = input.nextInt();
                    System.out.print("Enter comment: ");
                    input.nextLine();
                    String comment = input.nextLine();
                    Review r = new Review(rid, customerid, prid, rating, comment);
                    add_Review(r);
                    break;

                case 6:
                    all_Customers.displayAllCustomer();
                    break;

                case 7:
                    e1.displayTop3Products();
                    break;
                    
                case 8:
                     System.out.print("Enter first customer id: ");
                    int c1 = input.nextInt();
                    System.out.print("Enter sec customer id: ");
                    int c2 = input.nextInt();
                    
                     e1.showCommonHighRatedProducts(c1,c2);
                     break;
                case 9:
                     displayAllOrders_between2dates(LocalDate.of(2025, 2, 1), LocalDate.of(2025, 2, 9));
                     break;

                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        } while (choice != 0);

        input.close();

    }
    

}

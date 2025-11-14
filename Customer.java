package draft;

import java.io.File;
import java.util.Scanner;

public class Customer {
    private int customerID;
    private String name;
    private String email;
    private LinkedList<Order> orders = new LinkedList<>();
    // Static list for the all customers
    private static LinkedList<Customer> allCustomers = new LinkedList<>();

    public Customer(int customerID, String name, String email) {
        this.customerID = customerID;
        this.name = name;
        this.email = email;
    }

    // geters 
    public int getCustomerID()  {
        return customerID; }
    
    public String getName()     { 
        return name; }
    
    public String getEmail()    { 
        return email; }
    
    public LinkedList<Order> getOrders() { 
        return orders; }
    

    //  setters 
    public void setName(String name)   { 
        this.name = name; }
    
    public void setEmail(String email) {
        this.email = email; }

    //  linked list for the Help 
    private static void insertAtEnd(Customer c) {
        if (allCustomers.empty())
            allCustomers.insert(c);
        
        else {
            allCustomers.findFirst();
            
            while (!allCustomers.last())
            allCustomers.findNext();
            
            allCustomers.insert(c);
        }
    }

    // register new customer 
    public static void registerCustomer(Customer c) {
        if (searchById(c.getCustomerID()) != null) {
           System.out.println("Customer with ID " + c.getCustomerID() + " already exists!");
            return;
        }
        insertAtEnd(c);
       System.out.println("Customer registered: " + c.getName());
    }

    //  search by ID 
    public static Customer searchById(int id) {
        if (allCustomers.empty()) return null;
        allCustomers.findFirst();
        while (true) {
            Customer cur = allCustomers.retrieve();
            if (cur.getCustomerID() == id) return cur;
            if (allCustomers.last()) break;
            allCustomers.findNext();
        }
        return null;
    }

    // Place a New Order 
    public void placeOrder(Order o) {
        // Add order to customer list because every customer has list of orders
        if (orders.empty()) orders.insert(o);
        else {
            orders.findFirst();
            while (!orders.last()) orders.findNext();
            orders.insert(o);
        }
        System.out.println("Order placed for customer " + customerID + ": Order " + o.getOrderID());
    }

    // view order history 
    public void viewOrders() {
        System.out.println("\n== Orders for " + name + " ==");
        if (orders.empty()) {
            System.out.println("No orders yet.");
            return;
        }
        orders.findFirst();
        while (true) {
            Order o = orders.retrieve();
            System.out.println(o);
            if (orders.last()) break;
            orders.findNext();
        }
    }

    // display all customers 
    public static void displayAllCustomers() {
        System.out.println("\n== All Customers ==");
        if (allCustomers.empty()) {
            System.out.println("No customers registered.");
            return;
        }
        allCustomers.findFirst();
        while (true) {
        Customer c = allCustomers.retrieve();
           System.out.println("ID: " + c.getCustomerID() + " | Name: " + c.getName() + " | Email: " + c.getEmail());
           if (allCustomers.last()) break;
               allCustomers.findNext();
        }
    }

    // Convert String to Customer 
    public static Customer convert_String_to_Customer(String line) {
        String[] a = line.split(",", 3);
        return new Customer(
            Integer.parseInt(a[0].trim()),
            a[1].trim(),
            a[2].trim()
        );
    }

    // load from CSV 
    public static void load_customers(String fileName) {
        try (Scanner sc = new Scanner(new File(fileName), "UTF-8")) {
            System.out.println("\n# Reading customers file: " + fileName);
            if (sc.hasNextLine()) sc.nextLine(); // skip header

            int count = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                Customer c = convert_String_to_Customer(line);
                registerCustomer(c);
                count++;
            }
            System.out.println("✓ Customers loaded: " + count);
        } catch (Exception e) {
            System.out.println("✗ Error reading file: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerID +
               " | Name: " + name +
               " | Email: " + email;
    }

   
}

package javaapplication22;
import java.io.File;
import java.util.Scanner;

public class Customer {
    private int customerID;
    private String name;
    private String email;
    private BST<Order> orders = new BST<>();
    private static BST<Customer> customersTree = new BST<>();
    
    //  Constructors
    public Customer(int customerID, String name, String email) {
        this.customerID = customerID;
        this.name = name;
        this.email = email;
    }

    //  Getters
    public int getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public BST<Order> getOrders() {
        return orders;
    }

    
    //  Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    //  5 Insert Customer
    public static void registerCustomer(Customer c) {
        int id = c.getCustomerID();
        if (customersTree.findKey(id)) {
            System.out.println("Customer with ID " + id + " already exists!");
            return;
        }
        
// key=id , c=data 
        customersTree.insert(id, c);
        System.out.println("Customer registered: " + c.getName());
    }

    
    // 5 Search Customer 
    public static Customer searchById(int id) {
        if (!customersTree.findKey(id)) {
            return null;
        }
        return customersTree.retrieve(); 
    }

    // Advanced Query Requirements  4. List All Customers Sorted Alphabetically.
    
 public static void displayAllCustomers() {
    if (customersTree.isEmpty()) {
        System.out.println("No customers available.");
        return;
    }
    BST<Customer> nameTree = new BST<>();
    
    insertCustomersByName(customersTree.getRoot(), nameTree);
    System.out.println("\n-- Customers Sorted Alphabetically --");
    printNameTreeInOrder(nameTree.getRoot());
}


 // this method for customers from A-Z
private static void insertCustomersByName(BSTNode<Customer> node, BST<Customer> tempTree) {
    if (node == null) {
        return;
    }
    
    insertCustomersByName(node.left, tempTree);
    Customer c = node.data;
    String name = c.getName();
    if (name != null && !name.isEmpty()) {
        name = name.toLowerCase();
       int first  = name.charAt(0);
       int second = 0;
       if (name.length() > 1) {
       second = name.charAt(1);
}
       
    int alphabeticalKey = first * 1000 + second;
    tempTree.insert(alphabeticalKey, c);
    }
    insertCustomersByName(node.right, tempTree);
}
    private static void printNameTreeInOrder(BSTNode<Customer> node) {
    if (node == null) {
        return;
    }
    printNameTreeInOrder(node.left);
    Customer c = node.data;
    System.out.println(c);   // use of toString()
    printNameTreeInOrder(node.right);
}

    
   

    // Place a New Order 
  public void placeOrder(Order o) {
    orders.insert(o.getOrderID(), o);
    System.out.println("Order placed for customer " + customerID +
                       ": Order " + o.getOrderID());
}


    // 6 Customer Order History 
  public void viewOrders() {
    System.out.println("\n== Orders for " + name + " ==");

    if (orders.isEmpty()) {
        System.out.println("No orders yet.");
        return;
    }

  
    printOrdersInOrder(orders.getRoot());
}

private void printOrdersInOrder(BSTNode<Order> node) {
    if (node == null) {
        return;
    }
    printOrdersInOrder(node.left);
    Order o = node.data;
    o.display();   
    printOrdersInOrder(node.right);
}



    // Convert String to Customer 
   public static Customer convert_String_to_Customer(String line) {
        String[] a = line.split(",", 3);
        return new Customer( Integer.parseInt(a[0].trim()), a[1].trim(),a[2].trim() );
    }
    
    @Override
    public String toString() {
        return "Customer ID: " + customerID +
               " | Name: " + name +
               " | Email: " + email;
    }
}



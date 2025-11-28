package javaapplication22;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

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

        // نتأكد ما فيه عميل بنفس الـ ID
        if (customersTree.findKey(id)) {
            System.out.println("Customer with ID " + id + " already exists!");
            return;
        }

        // نفترض أن عندك في BST دالة: insert(int key, T data)
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

    // شجرة مؤقتة نرتب فيها العملاء حسب الحرف الأول من الاسم
    BST<Customer> nameTree = new BST<>();

    // نعبّي شجرة الأسماء من الشجرة الأصلية
    insertCustomersByName(customersTree.getRoot(), nameTree);

    System.out.println("\n-- Customers Sorted Alphabetically --");

    // نطبع شجرة الأسماء In-order من A إلى Z
    printNameTreeInOrder(nameTree.getRoot());
}


 // this method for ranking customers from A-Z
private static void insertCustomersByName(BSTNode<Customer> node, BST<Customer> tempTree) {
    if (node == null) {
        return;
    }
    // نمر على الشجرة الأصلية In-order
    insertCustomersByName(node.left, tempTree);

    // العميل في هذه العقدة
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

// ندخل العميل في الشجرة المؤقتة
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
    System.out.println(c);   // هنا يستخدم toString()
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

    // نطبع الطلبات مباشرة باستخدام traversal على شجرة الطلبات
    printOrdersInOrder(orders.getRoot());
}

private void printOrdersInOrder(BSTNode<Order> node) {
    if (node == null) {
        return;
    }

    // الفرع الأيسر
    printOrdersInOrder(node.left);

    // العقدة الحالية
    Order o = node.data;
    o.display();   // تستعرض تفاصيل الطلب

    // الفرع الأيمن
    printOrdersInOrder(node.right);
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
                registerCustomer(c); // يسجل في الـ BST
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



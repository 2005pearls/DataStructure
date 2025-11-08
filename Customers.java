package javaapplication19;
import java.io.File;
import java.util.Scanner;
public class Customers {
private LinkedList<Customer> Customers
        

public Customers(){
    Customers=new LinkedList<>();   
}

Customers(LinkedList<Customer> Customers){
    this.Customers=Customers;
}

public LinkedList<Customer> get_Customers(){
    return Customers;
}


public Customer searchCustomerById(int id) {
    if (Customers.empty()) return null;
       Customers.findFirst();
       
       while (!Customers.last()) {
        Customer cur = Customers.retrieve();
        if (cur.getCustomerId() == id) {
            return cur;
         }
        Customers.findNext();
       }
    Customer last = Customers.retrieve();
    if (last.getCustomerId() == id) {
        return last;
    }
    return null;
}





public void addCustomer(Customer c) {
    if (searchCustomerById(c.getCustomerId()) == null) {
       Customers.addLast(c);
         System.out.println("Added customer: " + c.getName());
    } else {
        System.out.println("Customer with ID " + c.getCustomerId() + " already exists");
    }
}



 public void displayAllCustomer() {
    if (Customers.empty()) {
        System.out.println("No Customer found");
        return;
    }
     System.out.println("ALL Customers");
      Customers.findFirst();
      while (!Customers.last()) {
        Customers.retrieve().display();
       Customers.findNext();
    }
        Customers.retrieve().display();
}
 
 
 
 
public static Customer convert_String_to_Customer(String line) {
    String[] a = line.split(",");
    Customer p = new Customer(Integer.parseInt(a[0].trim()), a[1].trim(),a[2].trim());
    return p;
}



public void readAllCustomers(String fileName) {
    try {
        File file = new File(fileName);
        Scanner read = new Scanner(file);
        System.out.println(" Loading Customers from : " + fileName);
        System.out.println();

        if (read.hasNextLine()) {
            read.nextLine(); }

        while (read.hasNextLine()) {
            String line = read.nextLine().trim();
            if (line.isEmpty()) { 
                continue;
            }
            Customer cust = convert_String_to_Customer(line);
            Customers.addLast(cust);
        }
        read.close();
        System.out.println(" Customers loaded.");
        System.out.println("------------------------------");
    } catch (Exception e) {
        System.out.println("!! Failed to load customers: " + e.getMessage());
    }
}






















}

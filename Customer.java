package javaapplication19;
import java.io.File;
import java.util.Scanner;

public class Customer {
    private int customerID;
    private String name;
    private String email;
    private LinkedList<Order> orders;
    private LinkedList<Review> reviews = new LinkedList<>();
    
  public Customer(int id, String name, String email){
      this.name=name ;
      this.customerID=customerID  ;
      this.email=email  ;
      this.orders=new LinkedList<>();
    }
  
     public void display(){
        System.out.println("Customer ID : "+ customerID );
        System.out.println("Name : "+ name );
        System.out.println("Email: "+ email);

    }
   
   
   public void displayOrders() {
    if (orders.empty()) {
        System.out.println("No orders");
        return;
    }
     System.out.println("Customer Orders:");
     orders.findFirst();
     while (!orders.last()) {
       orders.retrieve().display();
      orders.findNext();
    }
    orders.retrieve().display();
}


   public void displayReviews() {
    System.out.println("Reviews for Customer" + name + ":");
    if (reviews.empty()) {
        System.out.println("No reviews found");
        return;
    }
    reviews.findFirst();
    while (!reviews.last()) {
        reviews.retrieve().display();
        reviews.findNext();
    }
    reviews.retrieve().display();
}
   
   
   public int getCustomerId(){
        return customerID;
    }
   
    public String getName(){
            return name ;
    }
    
 
    public String getEmail(){
        return email;
    }
    
    public void addOrder(Order o){
        orders.addLast(o);
    }
    
    public void addReview(Review r){
        reviews.addLast(r);
    }
    
 
    
}

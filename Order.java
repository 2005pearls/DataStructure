package javaapplication19;
import java.time.LocalDate;
import java.util.Date;

public class Order {
    private int orderId;
    private int customerId;
    private String productId;
    private LinkedList<Integer> productIds;
    private double total_Price;
    private LocalDate order_Date;
    private String status;

    public Order(int orderId, int customerId, String productId,
                 double total_Price, LocalDate order_Date, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.productId = productId;
        this.total_Price = total_Price;
        this.order_Date = order_Date;
        this.status = status;
        this.productIds = new LinkedList<Integer>();
    }

    public void addIds(String IDs){
        String a[]=IDs.split(":");
        for(int i=0;i<a.length;i++)
            productIds.addLast(Integer.parseInt(a[i].trim()));
    }
    
    public void addId(int pid) {
        productIds.addLast(pid);
    }

    public void UpdateOrder(Order src) {
        this.orderId = src.orderId;
        this.customerId = src.customerId;
        this.productId = src.productId;
        this.total_Price = src.total_Price;
        this.order_Date = src.order_Date;
        this.status = src.status;
        this.productIds = src.productIds;
    }

 

    public void display() {
        System.out.println("Order id:" + orderId);
        System.out.println("Customer id: " + customerId);
        System.out.print("Product id : ");
        //productIds.display();
        System.out.println();
        System.out.println("Total price :" + total_Price);
        System.out.println("Date: " + order_Date);
        System.out.println("Status: " + status);
        System.out.println("-----------------------------");
    }

/*    public static void main(String[] args) {
        Order o1 = new Order(501, 101, "201;202;203",
                4999.99, LocalDate.of(2024,1,1), "Delivered");
        Order o2 = new Order(502, 102, "301;302",
                1899.50, LocalDate.of(2024,1,1), "Pending");

        o1.addId(201); o1.addId(202); o1.addId(203);
        o2.addId(301); o2.addId(302);

        o1.display();
        o2.display();
    }*/
    
    
     public int getOrderId() {
        return orderId; }
    
    public int getCustomerId() {
        return customerId; }
    
    public String getProd_Ids() {
        return productId; }
    
    public LinkedList<Integer> getProductIds() { 
        return productIds; }
    
    public double getTotalPrice() {
        return total_Price; }
    
    public LocalDate getOrderDate() { 
        return order_Date; }
    
    public String getStatus() {
        return status; }

    public void setStatus(String s) { 
        status = s; }
}


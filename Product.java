/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package structurephase1;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author janaalmengash
 */
public class Product {
    private int productID;
    private int stock;
    private String name;
    private double price;
    private LinkedList<Review> reviews = new LinkedList<>(); 
    
    //constructor
    public Product(int productID, int stock, String name, double price) {
        this.productID = productID;
        this.stock = stock;
        this.name = name;
        this.price = price;
    }
    
    // updates the current product's information 
   public void updateProduct(Product pr) {
    this.productID = pr.productID;
    this.name = pr.name;
    this.price = pr.price;
    this.stock = pr.stock;
    this.reviews = pr.reviews; 
}
   
   //getters

    public int getProductID() {
        return productID;
    }

    public int getStock() {
        return stock;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public LinkedList<Review> getReviews() {
        return reviews;
    }
   
    //setters

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setReviews(LinkedList<Review> reviews) {
        this.reviews = reviews;
    }
    
  
    public void addReview(Review review) {
    if (reviews.empty()) {
        reviews.insert(review);          
    } else {
        reviews.findFirst();             
        while (!reviews.last()) {        
            reviews.findNext();
        }
        reviews.insert(review); 
    }
}
    
    //calculates avg rating for the product (required review operation)
    public double getAverageRating() {
        if (reviews.empty()) return 0.0;
          reviews.findFirst();
    double sum = 0;
    int count = 0;
    while (true) {
        sum += reviews.retrieve().getRating();
        count++;
        if (reviews.last()) break;
        reviews.findNext();
    }
    return sum / count;
}
    
    //show all reviews or say no reviews
    public void displayAllReviews() {
    if (reviews.empty()) {
        System.out.println("No reviews available for this product.");
        return;
    }

    reviews.findFirst();
    System.out.println("Reviews for product: " + name);

    while (true) {
        Review r = reviews.retrieve();
        System.out.println("Rating: " + r.getRating() + " | Comment: " + r.getComment());

        if (reviews.last()) break;
        reviews.findNext();
    }
}
    
    // returns product details 
    @Override
public String toString() {
    return "Product ID: " + productID +
           "\nName: " + name +
           "\nPrice: " + price + " SR" +
           "\nStock: " + stock +
           "\nAvg Rating: " + String.format("%.1f", getAverageRating()) +
           "\n----------------------";
}
/*public static void testMyProduct() {
    // make some reviews
    Review rv1 = new Review(1, 101, 201, 5, "Perfect laptop");
    Review rv2 = new Review(2, 101, 202, 3, "Average performance");
    Review rv3 = new Review(3, 101, 203, 4, "Good but a bit pricey");

    // make a product
    Product p1 = new Product(101, 20, "Laptop Pro 15", 1499.99);

    // add reviews
    p1.addReview(rv1);
    p1.addReview(rv2);
    p1.addReview(rv3);

    // show product and its reviews
    System.out.println(p1);
    p1.displayAllReviews();
}*/

// turn a csv line into a Review object
public static Review convert_String_to_Review(String line) {
    String[] a = line.split(",", 5);
    if (a.length < 5) throw new IllegalArgumentException("Bad CSV line: " + line);

    return new Review(
        Integer.parseInt(a[0].trim()),
        Integer.parseInt(a[1].trim()),
        Integer.parseInt(a[2].trim()),
        Integer.parseInt(a[3].trim()),
        a[4].trim()
    );
}

// read reviews from file
public void load(String fileName) {
    try {
        File f = new File(fileName);
        Scanner read = new Scanner(f);
        
        System.out.println("Reading file: " + fileName);
        read.nextLine(); // skip header
        
        while (read.hasNextLine()) {
            String line = read.nextLine().trim();
            if (!line.isEmpty()) {
                Review r = convert_String_to_Review(line);
                if (reviews.empty()) {
    reviews.insert(r);
} else {
    reviews.findFirst();
    while (!reviews.last()) {
        reviews.findNext();
    }
    reviews.insert(r);
}
            }
        }
        
        read.close();
        System.out.println("File loaded!");
    } catch (Exception e) {
        System.out.println("Error reading file: " + e.getMessage());
    }
}

// test loading reviews from file 
/*public static void testMyProduct2() {
    Product p1 = new Product(101, 20, "Laptop Pro 15", 1499.99);
   p1.load("reviews.csv");
    System.out.println(p1);
    p1.displayAllReviews();
}*/

/*public static void main(String[] args) {
       
    System.out.println("== START ==");
    //testMyProduct();
    System.out.println("after testMyProduct");
    testMyProduct2();
    System.out.println("== END ==");

}*/

}//end class

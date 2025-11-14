/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package draft;

import java.io.File;
import java.util.Scanner;

public class Product {
    // instance fields
    private int productID;
    private int stock;
    private String name;
    private double price;
    private LinkedList<Review> reviews = new LinkedList<>();
    // return invenury for main use
static LinkedList<Product> getInventoryForMainOnly() { return inventory; }
    // list that stores all pruducts in system
    private static LinkedList<Product> inventory = new LinkedList<>();

    // constructor
    public Product(int productID, int stock, String name, double price) {
        this.productID = productID;
        this.stock = stock;
        this.name = name;
        this.price = price;
    }

    // getters
    public int getProductID()   { return productID; }
    public int getStock()       { return stock; }
    public String getName()     { return name; }
    public double getPrice()    { return price; }
    public LinkedList<Review> getReviews() { return reviews; }

    // setters 
    public void setProductID(int productID) { this.productID = productID; }
    public void setStock(int stock)         { this.stock = stock; }
    public void setName(String name)        { this.name = name; }
    public void setPrice(double price)      { this.price = price; }

    // reviews
    public void addReview(Review r) {
        if (reviews.empty()) reviews.insert(r);
        else {
            reviews.findFirst();
            while (!reviews.last()) reviews.findNext();
            reviews.insert(r);
        }
    }

    public double getAverageRating() {
        if (reviews.empty()) return 0.0;
        reviews.findFirst();
        double sum = 0; int count = 0;
        while (true) {
            Review rv = reviews.retrieve();
            sum += rv.getRating(); count++;
            if (reviews.last()) break;
            reviews.findNext();
        }
        return sum / count;
    }

    public void displayAllReviews() {
        if (reviews.empty()) {
            System.out.println("No reviews available for this product.");
            return;
        }
        System.out.println("Reviews for product: " + name);
        reviews.findFirst();
        while (true) {
            Review r = reviews.retrieve();
            System.out.println("Rating: " + r.getRating() + " | Comment: " + r.getComment());
            if (reviews.last()) break;
            reviews.findNext();
        }
    }

    @Override
    public String toString() {
        return "Product ID: " + productID +
               "\nName: " + name +
               "\nPrice: " + price + " SR" +
               "\nStock: " + stock +
               "\nAvg Rating: " + String.format("%.1f", getAverageRating()) +
               "\n----------------------";
    }

    private static void insertAtEnd(Product p) {
        if (inventory.empty()) inventory.insert(p);
        else {
            inventory.findFirst();
            while (!inventory.last()) inventory.findNext();
            inventory.insert(p);
        }
    }

    public static Product searchById(int id) {
        if (inventory.empty()) return null;
        inventory.findFirst();
        while (true) {
            Product cur = inventory.retrieve();
            if (cur.getProductID() == id) return cur; 
            if (inventory.last()) break;
            inventory.findNext();
        }
        return null;
    }

    public static void addProduct(Product p) {
        if (searchById(p.getProductID()) == null) {
            insertAtEnd(p);
        } else {
            System.out.println("Product with ID " + p.getProductID() + " already exists!");
        }
    }

    public static void removeProduct(int id) {
        if (searchById(id) != null) {
            inventory.remove(); // current واقف على العنصر
            System.out.println("Product removed: " + id);
        } else {
            System.out.println("Product ID not found!");
        }
    }

    public static void updateProduct(int id, Product data) {
        Product old = searchById(id);
        if (old != null) {
            old.setName(data.getName());
            old.setPrice(data.getPrice());
            old.setStock(data.getStock());
            System.out.println("Product updated: " + id);
        } else {
            System.out.println("Product ID not found!");
        }
    }

    public static void displayAllProducts() {
        System.out.println("\n== All Products ==");
        if (inventory.empty()) {
            System.out.println("No products available.");
            return;
        }
        inventory.findFirst();
        while (true) {
            Product p = inventory.retrieve();
            System.out.println(p);
            p.displayAllReviews();
            System.out.println("***********************************");
            if (inventory.last()) break;
            inventory.findNext();
        }
    }

    public static void displayOutOfStock() {
        System.out.println("\n== Out-of-Stock products ==");
        if (inventory.empty()) {
            System.out.println("No products!");
            return;
        }
        boolean found = false;
        inventory.findFirst();
        while (true) {
            Product p = inventory.retrieve();
            if (p.getStock() == 0) { System.out.println(p); found = true; }
            if (inventory.last()) break;
            inventory.findNext();
        }
        if (!found) System.out.println("All products in stock!");
    }

    // reading the csv line andmaking it a product 
    // format: id,name,price,stock same as constructor
    public static Product convert_String_to_product(String line) {
        String[] a = line.split(",", 4);
        int id = Integer.parseInt(a[0].trim());
        String name = a[1].trim();
        double price = Double.parseDouble(a[2].trim());
        int stock = Integer.parseInt(a[3].trim());
        return new Product(id, stock, name, price);
    }

    public static void load_products(String fileName) {
        try (Scanner read = new Scanner(new File(fileName), "UTF-8")) {
            System.out.println("\n# Reading file: " + fileName);
            if (read.hasNextLine()) read.nextLine(); // skip header
            int count = 0;
            while (read.hasNextLine()) {
                String line = read.nextLine().trim();
                if (line.isEmpty()) continue;
                Product p = convert_String_to_product(line);
                addProduct(p);
                count++;
            }
            System.out.println("Products loaded: " + count);
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    //operations
    public static Product searchByName(String name) {
    if (inventory.empty()) return null;
    inventory.findFirst();
    while (true) {
        Product p = inventory.retrieve();
        if (p.getName().equalsIgnoreCase(name)) return p; // current يوقف هنا
        if (inventory.last()) break;
        inventory.findNext();
    }
    return null;
}
    
    public static void top3ByAverageRating() {
    if (inventory.empty()) {
        System.out.println("No products.");
        return;
    }
    Product best1=null, best2=null, best3=null;

    inventory.findFirst();
    while (true) {
        Product p = inventory.retrieve();
        if (best1 == null || p.getAverageRating() > best1.getAverageRating()) {
            best3 = best2; best2 = best1; best1 = p;
        } else if (best2 == null || p.getAverageRating() > best2.getAverageRating()) {
            best3 = best2; best2 = p;
        } else if (best3 == null || p.getAverageRating() > best3.getAverageRating()) {
            best3 = p;
        }

        if (inventory.last()) break;
        inventory.findNext();
    }

    System.out.println("\n== Top 3 Products by Avg Rating ==");
    if (best1 != null) System.out.println("#1\n" + best1);
    if (best2 != null) System.out.println("#2\n" + best2);
    if (best3 != null) System.out.println("#3\n" + best3);
}
    
    public static void printReviewsByCustomer(int customerId) {
    System.out.println("\n== Reviews by customer " + customerId + " ==");
    boolean found = false;
    if (inventory.empty()) { System.out.println("No products."); return; }

    inventory.findFirst();
    while (true) {
        Product p = inventory.retrieve();
        LinkedList<Review> rs = p.getReviews();
        if (!rs.empty()) {
            rs.findFirst();
            while (true) {
                Review r = rs.retrieve();
                if (r.getCustomerID() == customerId) {
                    System.out.println("Product: " + p.getName() +
                        " | rating: " + r.getRating() +
                        " | comment: " + r.getComment());
                    found = true;
                }
                if (rs.last()) break;
                rs.findNext();
            }
        }
        if (inventory.last()) break;
        inventory.findNext();
    }
    if (!found) System.out.println("No reviews from this customer.");
}
    
    public static void commonReviewedProductsAbove(int custA, int custB, double threshold) {
    System.out.println("\n== Common reviewed products by " + custA + " and " + custB +
                       " with avg >= " + threshold + " ==");

    boolean any = false;
    if (inventory.empty()) { System.out.println("No products."); return; }

    inventory.findFirst();
    while (true) {
        Product p = inventory.retrieve();
        boolean aReviewed = false, bReviewed = false;

        LinkedList<Review> rs = p.getReviews();
        if (!rs.empty()) {
            rs.findFirst();
            while (true) {
                Review r = rs.retrieve();
                int cid = r.getCustomerID(); 
                if (cid == custA) aReviewed = true;
                if (cid == custB) bReviewed = true;
                if (rs.last()) break;
                rs.findNext();
            }
        }

        if (aReviewed && bReviewed && p.getAverageRating() >= threshold) {
            System.out.println(p);
            any = true;
        }

        if (inventory.last()) break;
        inventory.findNext();
    }
    if (!any) System.out.println("None.");
}
}

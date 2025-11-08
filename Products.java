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
public class Products {
    private LinkedList<Product> products;
    
 public Products(LinkedList<Product> input_products) {
    this.products = input_products;
}
 
    public LinkedList<Product> getProducts() {
        return products;
    }
    

    public Products() {
        products =new LinkedList<>();
    }
    
   public Product Search_Product_by_id(int id) {
        if (products.empty()) {
            return null;
        } else {
            products.findFirst();
            while (true) {
                if (products.retrieve().getProductID() == id) {
                    return products.retrieve();
                }
                if (products.last())
                    break;
                else
                    products.findNext();
            }
            return null;
        }
    }

   public void addProduct(Product p) {
    if (Search_Product_by_id(p.getProductID()) == null) {  
        if (products.empty()) {
            products.insert(p);                             
        } else {
            products.findFirst();
            while (!products.last()) products.findNext();   
            products.insert(p);                             
        }
        System.out.println("Product added: " + p.getName());
    } else {
        System.out.println("Product with ID " + p.getProductID() + " already exists!");
    }
}
   
   public void removeProduct(int id) {
    if (Search_Product_by_id(id) != null) { 
        products.remove();
        System.out.println("Product removed: " + id);
    } else {
        System.out.println("Product ID not found!");
    }
}
   
   public void updateProduct(int id, Product p) {
    Product old = Search_Product_by_id(id);
    if (old != null) {
        old.setName(p.getName());
        old.setPrice(p.getPrice());
        old.setStock(p.getStock());
        System.out.println("Product updated: " + id);
    } else {
        System.out.println("Product ID not found!");
    }
}
   
public void displayOutOfStock() {
    System.out.println("\n== Out-of-Stock products ==");
    if (products.empty()) {
        System.out.println("No products!");
        return;
    }

    boolean found = false;
    products.findFirst();
    while (true) {
        Product p = products.retrieve();
        if (p.getStock() == 0) {
            System.out.println(p);
            found = true;
        }

        if (products.last()) break;
        products.findNext();
    }

    if (!found) {
        System.out.println("All products in stock!");
    }
}

public void displayAllProducts() {
    System.out.println("\n== All Products ==");
    if (products.empty()) {
        System.out.println("No products available.");
        return;
    }

    products.findFirst();
    while (true) {
        Product p = products.retrieve();
        System.out.println(p);          
        p.displayAllReviews();         
        System.out.println("***********************************");

        if (products.last()) break;
        products.findNext();
    }
}

public void assign2(Review r) {
    if (products.empty()) return;

    products.findFirst();
    while (true) {
        Product p1 = products.retrieve();
        if (p1.getProductID() == r.getProductID()) {
            p1.addReview(r);      
            break;               
        }
        if (products.last()) break;
        else products.findNext();
    }
}


public void assign(Review r) {
    Product p = Search_Product_by_id(r.getProductID());
    if (p == null) {
        System.out.println("not exist to assign review " + r.getReviewID() + " to it");
    } else {
        p.addReview(r);
    }
}

 // ==================== Tests ====================

    /*public void test1() {
        Product p1 = new Product(101, 20, "Laptop Pro 15", 1499.99);
        Review r1 = new Review(201, 101, 300, 5, "Excellent laptop");
        Review r2 = new Review(202, 101, 301, 4, "good");
        p1.addReview(r1);
        p1.addReview(r2);

        Product p2 = new Product(102, 15, "iPhone 15 Pro", 3999.99);
        Review r3 = new Review(203, 102, 302, 5, "best iPhone ever");
        Review r4 = new Review(204, 102, 303, 3, "expensive");
        p2.addReview(r3);
        p2.addReview(r4);

        Product p3 = new Product(103, 0, "Samsung Galaxy a15", 3499.99);
        Review r5 = new Review(205, 103, 304, 2, "bad battery");
        Review r6 = new Review(206, 103, 305, 4, "Excellent battery");
        p3.addReview(r5);
        p3.addReview(r6);

        addProduct(p1);
        addProduct(p2);
        addProduct(p3);

        displayAllProducts();
    }

    public void test2() {
        Product p1 = new Product(101, 20, "Laptop Pro 15", 1499.99);
        Product p2 = new Product(102, 15, "iPhone 15 Pro", 3999.99);
        Product p3 = new Product(103, 0,  "Samsung Galaxy a15", 3499.99); // out of stock

        addProduct(p1);
        addProduct(p2);
        addProduct(p3);

        Review r1 = new Review(201, 101, 300, 5, "Excellent laptop");
        Review r2 = new Review(202, 101, 301, 4, "good");
        Review r3 = new Review(203, 102, 302, 5, "best iPhone ever");
        Review r4 = new Review(204, 102, 303, 3, "expensive");
        Review r5 = new Review(205, 103, 304, 2, "bad battery");
        Review r6 = new Review(206, 103, 305, 4, "Excellent battery");

        assign(r1);
        assign(r2);
        assign(r3);
        assign(r4);
        assign(r5);
        assign(r6);

        displayAllProducts();
    }*/
    
    public static Product convert_String_to_product(String Line) {
    String[] a = Line.split(",");
    Product p = new Product(Integer.parseInt(a[0]), Integer.parseInt(a[3]), a[1], Double.parseDouble(a[2]));
    return p;
}

public void load_products(String fileName) {
    try {
        File f = new File(fileName);
        Scanner read = new Scanner(f);

        System.out.println("\n# Reading file: " + fileName);
        System.out.println("--------------------------------");

        // skip headeer
        read.nextLine().trim();

        while (read.hasNextLine()) {
            String line = read.nextLine().trim();
            if (!line.isEmpty()) {
                Product p = convert_String_to_product(line);
                products.addLast(p);
            }
        }

        read.close();
        System.out.println("\u2713 File loaded successfully!\n");
    } catch (Exception e) {
        System.out.println("✗ Error reading file: " + e.getMessage());
    }
}

/*public static void test3() {
    Products p = new Products();
    p.load_products("products.csv");
    p.displayAllProducts();
}
    
        public static void main(String[] args) {
        Products all = new Products();

        System.out.println("********* Test1 *********");
        all.test1();

        System.out.println("\n********* Test2 *********");
        all = new Products();  // قائمة جديدة نظيفة
        all.test2();

        // أمثلة للتحميل من ملفات:
        // all = new Products();
        // all.load_products("products.csv");
        // all.load_reviews("reviews.csv");
        // all.displayAllProducts();
        // all.displayOutOfStock();
    }*/

    
}

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
public class Reviews {
    private LinkedList<Review> reviews; // all reviews
    private Products all_products; // products list
    private Customers AllCustomers; // customers list

    // constructor with inputs
    public Reviews(LinkedList<Review> reviews,
                   LinkedList<Product> input_products,
                   LinkedList<Customer> input_customers) {
        this.reviews      = (reviews == null) ? new LinkedList<>() : reviews;
        this.all_products = (input_products == null) ? new Products(): new Products(input_products);
        this.AllCustomers = (input_customers == null) ? new Customers()
                            : new Customers(input_customers);
    }

    // default constructor
    public Reviews() {
        reviews = new LinkedList<>();
        all_products = new Products();
        AllCustomers = new Customers();
    }

    // ========= getters =========
    public LinkedList<Review> get_all_Reviews()   { return reviews; }
    public Products          get_all_Products()   { return all_products; }
    public Customers         get_all_Customers()  { return AllCustomers; }

    // add review at end of list
    private void insertReviewAtEnd(Review r) {
        if (reviews.empty()) {
            reviews.insert(r);
        } else {
            reviews.findFirst();
            while (!reviews.last()) reviews.findNext();
            reviews.insert(r);
        }
    }
    
        // connect review to product
    public void assign_to_product(Review r) {
    Product p = all_products.Search_Product_by_id(r.getProductID());
    
    if (p == null) {
        System.out.println("not exist to assign review " + r.getReviewID() + " to it");
    } else {
        p.addReview(r);
    }
}
    
        // add new review and assign it
    public void addReview(Review r) {
    if (Search_Review_by_id(r.getReviewID()) != null) {
        System.out.println("Review with ID " + r.getReviewID() + " already exists!");
        return;
    }
    if (reviews.empty()) {
        reviews.insert(r);
    } else {
        reviews.findFirst();
        while (!reviews.last()) reviews.findNext();
        reviews.insert(r);
    }
    assign_to_product(r);
    assign_to_customer(r);
    System.out.println("Review added: " + r.getReviewID());
}
    
        // connect review to customer
    public void assign_to_customer(Review r) {
    Customer c = AllCustomers.searchCustomerById(r.getCustomerId());
    if (c == null) {
        System.out.println("not exist to assign review " + r.getReviewID() + " to it");
    } else {
        c.addReview(r);
    }
}

        // find review by ID
    public Review Search_Review_by_id(int id) {
        if (reviews.empty()) return null;

        reviews.findFirst();
        while (true) {
            if (reviews.retrieve().getReviewID() == id) {
                return reviews.retrieve();       
            }
            if (reviews.last()) break;
            reviews.findNext();
        }
        return null;
    }


        // delete review
    public void removeReview(int id) {
        if (Search_Review_by_id(id) != null) {
            reviews.remove();
            System.out.println("Review removed: " + id);
        } else {
            System.out.println("Review ID not found!");
        }
    }

     // update rating/comment
    public void updateReview(int id, Review newData) {
    Review old = Search_Review_by_id(id);   
    if (old == null) {
        System.out.println("not exist to make update");
        return;
    }
    old.setRating(newData.getRating());
    old.setComment(newData.getComment());
    System.out.println("Review updated: " + id);
}

    // show all reviews
    public void displayAllReviews() {
    System.out.println("\n== All Reviews ==");
    if (reviews.empty()) {
        System.out.println("no reviews exist");
        return;
    }

    reviews.findFirst();
    while (true) {
        Review r = reviews.retrieve();
        // r.display();
        System.out.println("Review ID: " + r.getReviewID()
            + " | product: " + r.getProductID()
            + " | customer: " + r.getCustomerId()
            + " | rating: " + r.getRating()
            + " | comment: " + r.getComment());
        System.out.println("*******************************");

        if (reviews.last()) break;
        reviews.findNext();
    }
}

   
    // read reviews from csv
    public void load_reviews(String fileName) {
        try (Scanner sc = new Scanner(new File(fileName), "UTF-8")) {
            System.out.println("\n# Reading file: " + fileName);
            if (sc.hasNextLine()) sc.nextLine(); // skip header

            int cnt = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                Review r = Product.convert_String_to_Review(line); 
                addReview(r); 
                cnt++;
            }
            System.out.println("✓ Reviews loaded & assigned: " + cnt);
        } catch (Exception e) {
            System.out.println("✗ Error reading file: " + e.getMessage());
        }
    }

  
   /* public void test1() {
    Review r1 = new Review(401, 101, 201, 5, "Excellent Excellent laptop, super fast!");
    Review r2 = new Review(402, 102, 202, 4, "Nice");
    Review r3 = new Review(403, 103, 203, 5, "great keyboard for typing");
    Review r4 = new Review(203, 102, 302, 5, "best iPhone ever");
    Review r5 = new Review(204, 102, 303, 3, "expensive");

    addReview(r1);
    addReview(r2);
    addReview(r3);
    addReview(r4);
    addReview(r5);

    displayAllReviews();
}
    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    Reviews all = new Reviews();

    System.out.println("********** Test1 **********");
    all.test1();

    System.out.println("\n********** Test2 **********");
   // all.test2();

    System.out.println("\n********** Test3 **********");
    // إذا عندك test3() للقراءة من CSV فعّلي هذا السطر
    // all.test3();

    sc.close();
}*/
    
    // convert csv line to Review obj
    public static Review convert_String_to_Review(String Line) {
    String[] a = Line.split(",", 5);
    Review r = new Review(
        Integer.parseInt(a[0].trim()), // reviewID
        Integer.parseInt(a[1].trim()), // productID
        Integer.parseInt(a[2].trim()), // customerID
        Integer.parseInt(a[3].trim()), // rating
        a[4].trim()                    // comment
    );
    return r;
}
    
    
}

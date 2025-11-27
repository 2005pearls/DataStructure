package draft;

import java.io.File;
import java.util.Scanner;

public class Review {
    //attributes
    private int reviewID;
    private int productID;
    private int customerID;
    private int rating;      // 1–5
    private String comment;

    // bst for all reviews (key = reviewID)
    private static BST<Review> allReviews = new BST<>();

    public Review(int reviewID, int productID, int customerID, int rating, String comment) {
        this.reviewID = reviewID;
        this.productID = productID;
        this.customerID = customerID;
        this.rating = rating;
        this.comment = comment;
    }

    // getters & setters 
    public int getReviewID()   { return reviewID; }
    public int getProductID()  { return productID; }
    public int getCustomerID() { return customerID; }
    public int getRating()     { return rating; }
    public String getComment() { return comment; }

    public void setRating(int rating)      { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }

//bst operations
    
    public static Review searchByID(int id) {
        if (!allReviews.findKey(id)) {
            return null;
        }
        return allReviews.retrieve();
    }


    private static void registerReview(Review r) {
        int id = r.getReviewID();
        if (allReviews.findKey(id)) {
            // if already exists do nothing
            return;
        }
        allReviews.insert(id, r);
    }

    public static void addReview(Review r) {
        if (searchByID(r.getReviewID()) != null) {
            System.out.println("Review ID " + r.getReviewID() + " already exists!");
            return;
        }
        registerReview(r);
        Product p = Product.searchById(r.getProductID());
        if (p != null) {
            p.addReview(r);
        } else {
            System.out.println("Product " + r.getProductID()
                    + " not found for review " + r.getReviewID());
        }

        System.out.println("Review added: " + r.getReviewID());
    }

    // edit review rating and comment
    public static void editReview(int id, int newRating, String newComment) {
        Review r = searchByID(id);
        if (r == null) {
            System.out.println("Review ID not found!");
            return;
        }
        r.setRating(newRating);
        r.setComment(newComment);
        System.out.println("Review updated: " + id);
    }

//// traversing the bst
 
    public static void displayAllReviews() {
        System.out.println("\n== All Reviews ==");
        if (allReviews.isEmpty()) {
            System.out.println("No reviews.");
            return;
        }
        inOrder_all(allReviews.getRoot());
    }

    private static void inOrder_all(BSTNode<Review> p) {
        if (p == null) return;

        inOrder_all(p.left);

        Review r = p.data;
        System.out.println("Review ID: " + r.getReviewID() +
                           " | Product: " + r.getProductID() +
                           " | Customer: " + r.getCustomerID() +
                           " | Rating: " + r.getRating() +
                           " | Comment: " + r.getComment());

        inOrder_all(p.right);
    }

   //csv loading
    
    // format: reviewID,productID,customerID,rating,comment
    public static Review convert_String_to_Review(String line) {
        String[] a = line.split(",", 5);
        return new Review(
            Integer.parseInt(a[0].trim()),  // reviewID
            Integer.parseInt(a[1].trim()),  // productID
            Integer.parseInt(a[2].trim()),  // customerID
            Integer.parseInt(a[3].trim()),  // rating
            a[4].trim()                     // comment
        );
    }

    public static void load_reviews(String fileName) {
        try (Scanner sc = new Scanner(new File(fileName), "UTF-8")) {
            System.out.println("\n# Reading reviews file: " + fileName);
            if (sc.hasNextLine()) sc.nextLine(); // skip header

            int count = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                Review r = convert_String_to_Review(line);
                addReview(r);   
                count++;
            }
            System.out.println("✓ Reviews loaded: " + count);
        } catch (Exception e) {
            System.out.println("✗ Error reading file: " + e.getMessage());
        }
    }
      /*  // ======================= TEMP SELF-TEST (PHASE II) =======================
    public static void main(String[] args) {
        System.out.println("\n===== REVIEW BST SELF-TEST START =====\n");

        Product.addProduct(new Product(10, 5, "Bread", 5.0));
        Product.addProduct(new Product(20, 7, "Juice", 8.0));
        Product.addProduct(new Product(30, 3, "Milk", 10.5));

        Review r1 = new Review(100, 10, 1000, 5, "Excellent bread!");
        Review r2 = new Review(101, 10, 1001, 3, "It is ok.");
        Review r3 = new Review(102, 20, 1002, 4, "Nice juice.");
        Review r4 = new Review(103, 30, 1003, 2, "Not my favorite.");

        Review.addReview(r1);
        Review.addReview(r2);
        Review.addReview(r3);
        Review.addReview(r4);

        System.out.println("\n-- ALL REVIEWS (IN-ORDER) --");
        Review.displayAllReviews();

        System.out.println("\n-- SEARCH TEST --");
        Review t = Review.searchByID(101);
        System.out.println("Search ID=101 → " +
                (t != null ? t.getComment() : "Not found"));

        t = Review.searchByID(999);
        System.out.println("Search ID=999 → " +
                (t != null ? "ERROR (should not exist)" : "Correct: Not found"));

        System.out.println("\n-- EDIT TEST (ID=102) --");
        Review.editReview(102, 1, "Changed my mind, bad juice.");
        Review.displayAllReviews();

        System.out.println("\n-- PRODUCT AVERAGE RATINGS AFTER REVIEWS --");
        Product.displayAllProducts();

        System.out.println("\n===== REVIEW BST SELF-TEST END =====\n");
    }*/
}

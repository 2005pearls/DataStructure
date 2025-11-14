package draft;

import java.io.File;
import java.util.Scanner;

public class Review {
    // attributes
    private int reviewID;
    private int productID;
    private int customerID;
    private int rating;      // 1–5
    private String comment;

    // holds  all the reviews in the system
    private static LinkedList<Review> allReviews = new LinkedList<>();

    // Constructors
    public Review(int reviewID, int productID, int customerID, int rating, String comment) {
        this.reviewID = reviewID;
        this.productID = productID;
        this.customerID = customerID;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters & Setters
    public int getReviewID()    { return reviewID; }
    public int getProductID()   { return productID; }
    public int getCustomerID()  { return customerID; }
    public int getRating()      { return rating; }
    public String getComment()  { return comment; }

    public void setRating(int rating)     { this.rating = rating; }
    public void setComment(String comment){ this.comment = comment; }

    // Insert review at end 
    private static void insertAtEnd(Review r) {
        if (allReviews.empty()) allReviews.insert(r);
        else {
            allReviews.findFirst();
            while (!allReviews.last()) allReviews.findNext();
            allReviews.insert(r);
        }
    }

    // Add new review and assign it to its product 
    public static void addReview(Review r) {
        if (searchByID(r.getReviewID()) != null) {
            System.out.println("Review ID " + r.getReviewID() + " already exists!");
            return;
        }

        insertAtEnd(r);
        Product p = Product.searchById(r.getProductID());
        if (p != null) {
            p.addReview(r);
        } else {
            System.out.println("Product " + r.getProductID() + " not found for review " + r.getReviewID());
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

    // Search review by id
    public static Review searchByID(int id) {
        if (allReviews.empty()) return null;
        allReviews.findFirst();
        while (true) {
            Review cur = allReviews.retrieve();
            if (cur.getReviewID() == id) return cur;
            if (allReviews.last()) break;
            allReviews.findNext();
        }
        return null;
    }

    // display all reviews 
    public static void displayAllReviews() {
        System.out.println("\n== All Reviews ==");
        if (allReviews.empty()) {
            System.out.println("No reviews.");
            return;
        }
        allReviews.findFirst();
        while (true) {
            Review r = allReviews.retrieve();
            System.out.println("Review ID: " + r.getReviewID() +
                               " | Product: " + r.getProductID() +
                               " | Customer: " + r.getCustomerID() +
                               " | Rating: " + r.getRating() +
                               " | Comment: " + r.getComment());
            if (allReviews.last()) break;
            allReviews.findNext();
        }
    }

    // load reveiws from csv 
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

    // Convert CSV line to Review
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

}

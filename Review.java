/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package structurephase1;

/**
 *
 * @author janaalmengash
 */
public class Review {
    //attributes
private int reviewID;
private int customerID;
private int productID;
private int rating; //1-5
private String comment;

//constructor
    public Review(int reviewID, int customerID, int productID, int rating, String comment) { 
        this.reviewID = reviewID;
        this.customerID = customerID;
        this.productID = productID;
        this.rating = rating;
        this.comment = comment;
    }

    //getters
    public int getReviewID() {
        return reviewID;
    }

    public int getCustomerId() {
        return customerID;
    }

    public int getProductID() {
        return productID;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    //setters
    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public void setCustomerId(int customerID) {
        this.customerID = customerID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    //toSyting for printing
   @Override
public String toString() { 
    return "Review ID: " + reviewID +
           "\nProduct ID: " + productID +
           "\nCustomer ID: " + customerID +
           "\nRating: " + rating + "/5" +
           "\nComment: " + comment +
           "\n----------------------------------";
}

//main
/*public static void main(String[] args) {
        Review review1 = new Review(1, 2, 111, 3, "Good");
        Review review2 = new Review(5, 5, 222, 2, "Not bad");

        System.out.println(review1);
        System.out.println(review2);

    
}*/
    
}

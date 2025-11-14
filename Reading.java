package draft;

import java.io.File;
import java.util.Scanner;

public class Reading {

    // products.csv: id,name,price,stock 
    public static void loadProducts(String file){
        try (Scanner sc = new Scanner(new File(file), "UTF-8")){
            System.out.println("\n# Loading products from: " + file);
            if (sc.hasNextLine()) sc.nextLine(); // skip header يسويلها سكب
            int cnt = 0;
            while (sc.hasNextLine()){
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                Product p = Product.convert_String_to_product(line);
                Product.addProduct(p);
                cnt++;
            }
            System.out.println("✓ Products loaded: " + cnt);
        }catch(Exception e){
            System.out.println("✗ products load error: " + e.getMessage());
        }
    }

    // customers.csv: id,name,email
    public static void loadCustomers(String file){
        try (Scanner sc = new Scanner(new File(file), "UTF-8")){
            System.out.println("\n# Loading customers from: " + file);
            if (sc.hasNextLine()) sc.nextLine();
            int cnt = 0;
            while (sc.hasNextLine()){
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                Customer c = Customer.convert_String_to_Customer(line);
                Customer.registerCustomer(c);
                cnt++;
            }
            System.out.println("✓ Customers loaded: " + cnt);
        }catch(Exception e){
            System.out.println("✗ customers load error: " + e.getMessage());
        }
    }

    // reviews.csv: reviewId,productId,customerId,rating,comment
    public static void loadReviews(String file){
        try (Scanner sc = new Scanner(new File(file), "UTF-8")){
            System.out.println("\n# Loading reviews from: " + file);
            if (sc.hasNextLine()) sc.nextLine();
            int cnt = 0;
            while (sc.hasNextLine()){
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                Review r = Review.convert_String_to_Review(line);
                // نضيف اليفيو للمنتج الي نبيه
                Product p = Product.searchById(r.getProductID());
                if (p != null){
                    p.addReview(r);
                    cnt++;
                }else{
                    System.out.println("Skip review "+r.getReviewID()+": product "+r.getProductID()+" not found");
                }
            }
            System.out.println("✓ Reviews loaded: " + cnt);
        }catch(Exception e){
            System.out.println("✗ reviews load error: " + e.getMessage());
        }
    }

    // orders.csv: orderId,customerId,status,yyyy-MM-dd
    public static void loadOrders(String file){
        Order.load_orders(file);
    }
}

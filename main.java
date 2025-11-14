package draft;

import java.io.File;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

    // for inputs
    private static final Scanner in = new Scanner(System.in);
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final String PRODUCTS_CSV  = "prodcuts.csv";      
    private static final String CUSTOMERS_CSV = "customers.csv";      
    private static final String REVIEWS_CSV   = "reviews.csv";      
    private static final String ORDERS_CSV    = "orders.csv";      

    
    private static void loadProductsCSV(String file) {
        try (Scanner sc = new Scanner(new File(file), "UTF-8")) {
            if (sc.hasNextLine()) sc.nextLine();
            int cnt = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                Product.addProduct(Product.convert_String_to_product(line));
                cnt++;
            }
            
        } catch (Exception e) {
            System.out.println(" Skip products CSV: " + e.getMessage());
        }
    }
    private static void loadCustomersCSV(String file) {
        try (Scanner sc = new Scanner(new File(file), "UTF-8")) {
            if (sc.hasNextLine()) sc.nextLine();
            int cnt = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] a = line.split(",", 3);
                Customer.registerCustomer(new Customer(
                    Integer.parseInt(a[0].trim()),
                    a[1].trim(),
                    a[2].trim()
                ));
                cnt++;
            }
           
        } catch (Exception e) {
            System.out.println(" Skip customers CSV: " + e.getMessage());
        }
    }
    private static void loadReviewsCSV(String file) {
        try (Scanner sc = new Scanner(new File(file), "UTF-8")) {
            if (sc.hasNextLine()) sc.nextLine();
            int cnt = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] a = line.split(",", 5);
                Review r = new Review(
                    Integer.parseInt(a[0].trim()),
                    Integer.parseInt(a[1].trim()),
                    Integer.parseInt(a[2].trim()),
                    Integer.parseInt(a[3].trim()),
                    a[4].trim()
                );
                Product p = Product.searchById(r.getProductID());
                if (p != null) { p.addReview(r); cnt++; }
            }
        } catch (Exception e) {
            System.out.println(" Skip reviews CSV: " + e.getMessage());
        }
    }
    private static void loadOrdersCSV(String file) { Order.load_orders(file); }

    private static int promptInt(String msg) {
        System.out.print(msg);
        while (!in.hasNextInt()) { System.out.print("Enter integer: "); in.next(); }
        int x = in.nextInt(); in.nextLine();
        return x;
    }
    private static double promptDouble(String msg) {
        System.out.print(msg);
        while (!in.hasNextDouble()) { System.out.print("Enter number: "); in.next(); }
        double d = in.nextDouble(); in.nextLine();
        return d;
    }
    private static String promptLine(String msg) {
        System.out.print(msg);
        return in.nextLine().trim();
    }
    private static void pause() {
        System.out.print("\n[Enter] to continue...");
        in.nextLine();
    }

    // main menu
    
    private static void mainMenu() {
        while (true) {
            System.out.println("\n==========================");
            System.out.println("1) Products");
            System.out.println("2) Customers");
            System.out.println("3) Orders");
            System.out.println("4) Reviews");
            System.out.println("5) Exit");
            System.out.println("==========================");
            int c = promptInt("Choice: ");
            if      (c == 1) productsMenu();
            else if (c == 2) customersMenu();
            else if (c == 3) ordersMenu();
            else if (c == 4) reviewsMenu();
            else if (c == 5) break;
            else System.out.println("Invalid choice.");
        }
    }

    // Products menu
    private static void productsMenu() {
        while (true) {
            System.out.println("\n-- Products --");
            System.out.println("1) Add");
            System.out.println("2) Remove");
            System.out.println("3) Update (name/price/stock)");
            System.out.println("4) Search by ID (linear)");
            System.out.println("5) Search by name (linear)");
            System.out.println("6) Show out-of-stock");
            System.out.println("7) Top-3 by average rating");
            System.out.println("8) Show all");
            System.out.println("9) Back");
            int c = promptInt("Choice: ");

            if (c == 1) {
                int id = promptInt("id: ");
                int stock = promptInt("stock: ");
                String name = promptLine("name: ");
                double price = promptDouble("price: ");
                Product.addProduct(new Product(id, stock, name, price));
            } else if (c == 2) {
                int id = promptInt("id to remove: ");
                Product.removeProduct(id);
            } else if (c == 3) {
                int id = promptInt("id to update: ");
                String name = promptLine("new name: ");
                double price = promptDouble("new price: ");
                int stock = promptInt("new stock: ");
                Product.updateProduct(id, new Product(id, stock, name, price));
            } else if (c == 4) {
                int id = promptInt("id: ");
                Product p = Product.searchById(id);
                System.out.println(p == null ? "Not found" : p.toString());
            } else if (c == 5) {
                String name = promptLine("name: ");
                Product p = Product.searchByName(name);
                System.out.println(p == null ? "Not found" : p.toString());
            } else if (c == 6) {
                Product.displayOutOfStock();
            } else if (c == 7) {
                Product.top3ByAverageRating();
            } else if (c == 8) {
                Product.displayAllProducts();
            } else if (c == 9) {
                return;
            } else {
                System.out.println("Invalid.");
            }
            pause();
        }
    }

    //  Customers menu
    private static void customersMenu() {
        while (true) {
            System.out.println("\n-- Customers --");
            System.out.println("1) Register new customer");
            System.out.println("2) Place order for a customer");
            System.out.println("3) View order history");
            System.out.println("4) Reviews by a customer");
            System.out.println("5) Show all customers");
            System.out.println("6) Back");
            int c = promptInt("Choice: ");

            if (c == 1) {
                int id = promptInt("customer id: ");
                String name = promptLine("name: ");
                String email = promptLine("email: ");
                Customer.registerCustomer(new Customer(id, name, email));
            } else if (c == 2) {
                int oid = promptInt("new order id: ");
                int cid = promptInt("customer id: ");
                Order o = Order.create(oid, cid, "pending");
                if (o != null) {
                    while (true) {
                        int pid = promptInt("product id (0 to stop): ");
                        if (pid == 0) break;
                        int qty = promptInt("qty: ");
                        o.addItemByProductId(pid, qty);
                    }
                    String st = promptLine("status (pending/shipped/delivered/canceled): ");
                    o.updateStatus(st);
                    System.out.println("Order summary:");
                    o.display();
                }
            } else if (c == 3) {
                int cid = promptInt("customer id: ");
                Order.viewOrdersOfCustomer(cid);
            } else if (c == 4) {
                int cid = promptInt("customer id: ");
                Product.printReviewsByCustomer(cid);
            } else if (c == 5) {
                Customer.displayAllCustomers();
            } else if (c == 6) {
                return;
            } else {
                System.out.println("Invalid.");
            }
            pause();
        }
    }

    //  Orders menu
    private static void ordersMenu() {
        while (true) {
            System.out.println("\n-- Orders --");
            System.out.println("1) Create order");
            System.out.println("2) Cancel order");
            System.out.println("3) Update status");
            System.out.println("4) Search by ID");
            System.out.println("5) All orders between two dates (yyyy-MM-dd)");
            System.out.println("6) Back");
            int c = promptInt("Choice: ");

            if (c == 1) {
                int oid = promptInt("new order id: ");
                int cid = promptInt("customer id: ");
                Order o = Order.create(oid, cid, "pending");
                if (o != null) {
                    while (true) {
                        int pid = promptInt("product id (0 to stop): ");
                        if (pid == 0) break;
                        int qty = promptInt("qty: ");
                        o.addItemByProductId(pid, qty);
                    }
                    o.display();
                }
            } else if (c == 2) {
                int oid = promptInt("order id: ");
                Order o = Order.searchById(oid);
                if (o == null) System.out.println("Not found.");
                else o.cancel();
            } else if (c == 3) {
                int oid = promptInt("order id: ");
                Order o = Order.searchById(oid);
                if (o == null) System.out.println("Not found.");
                else {
                    String st = promptLine("new status: ");
                    o.updateStatus(st);
                }
            } else if (c == 4) {
                int oid = promptInt("order id: ");
                Order o = Order.searchById(oid);
                if (o == null) System.out.println("Not found.");
                else o.display();
            } else if (c == 5) {
                String a = promptLine("from (yyyy-MM-dd): ");
                String b = promptLine("to   (yyyy-MM-dd): ");
                // validate order of dates 
                try {
                    LocalDate d1 = LocalDate.parse(a, DF), d2 = LocalDate.parse(b, DF);
                    if (d1.isAfter(d2)) { String t = a; a = b; b = t; }
                } catch (Exception ignore) {}
                Order.ordersBetween(a, b);
            } else if (c == 6) {
                return;
            } else {
                System.out.println("Invalid.");
            }
            pause();
        }
    }

    // Reviews menu
    private static void reviewsMenu() {
        while (true) {
            System.out.println("\n-- Reviews --");
            System.out.println("1) Add review");
            System.out.println("2) Edit review (by reviewID: rating/comment)");
            System.out.println("3) Average rating for a product");
            System.out.println("4) Common products (two customers, avg >= 4)");
            System.out.println("5) Back");
            int c = promptInt("Choice: ");

            if (c == 1) {
                int rid = promptInt("review id: ");
                int pid = promptInt("product id: ");
                int cid = promptInt("customer id: ");
                int rating = promptInt("rating (1..5): ");
                String comment = promptLine("comment: ");
                Product p = Product.searchById(pid);
                if (p == null) {
                    System.out.println("Product not found.");
                } else {
                    p.addReview(new Review(rid, pid, cid, rating, comment));
                    System.out.println("Review added.");
                }
            } else if (c == 2) {
                
                int rid = promptInt("review id to edit: ");
                int newRating = promptInt("new rating (1..5): ");
                String newComment = promptLine("new comment: ");

                boolean updated = false;
                
                LinkedList<Product> inv = Product.getInventoryForMainOnly(); 
                if (!inv.empty()) {
                    inv.findFirst();
                    while (true) {
                        Product p = inv.retrieve();
                        LinkedList<Review> rs = p.getReviews();
                        if (!rs.empty()) {
                            rs.findFirst();
                            while (true) {
                                Review r = rs.retrieve();
                                if (r.getReviewID() == rid) {
                                    r.setRating(newRating);
                                    r.setComment(newComment);
                                    updated = true;
                                    break;
                                }
                                if (rs.last()) break;
                                rs.findNext();
                            }
                        }
                        if (updated || inv.last()) break;
                        inv.findNext();
                    }
                }
                System.out.println(updated ? "Review updated." : "Review not found.");
            } else if (c == 3) {
                int pid = promptInt("product id: ");
                Product p = Product.searchById(pid);
                if (p == null) System.out.println("Not found.");
                else System.out.println("Average = " + String.format("%.2f", p.getAverageRating()));
            } else if (c == 4) {
                int a = promptInt("customer A id: ");
                int b = promptInt("customer B id: ");
                Product.commonReviewedProductsAbove(a, b, 4.0);
            } else if (c == 5) {
                return;
            } else {
                System.out.println("Invalid.");
            }
            pause();
        }
                
    }

    // the beghing
    public static void main(String[] args) {
        System.out.println("== Inventory & Orders System ==");
        if (!PRODUCTS_CSV.isEmpty())  loadProductsCSV(PRODUCTS_CSV);
        if (!CUSTOMERS_CSV.isEmpty()) loadCustomersCSV(CUSTOMERS_CSV);
        if (!REVIEWS_CSV.isEmpty())   loadReviewsCSV(REVIEWS_CSV);
        if (!ORDERS_CSV.isEmpty())    loadOrdersCSV(ORDERS_CSV);

        mainMenu();
        System.out.println("\nBye!");
    }
}

//package Zadachi.Zad61;
//
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//enum COMPARATOR_TYPE {
//    NEWEST_FIRST,
//    OLDEST_FIRST,
//    LOWEST_PRICE_FIRST,
//    HIGHEST_PRICE_FIRST,
//    MOST_SOLD_FIRST,
//    LEAST_SOLD_FIRST
//}
//
//class ProductNotFoundException extends Exception {
//    ProductNotFoundException(String message) {
//        super(message);
//    }
//}
//
//
//class Product {
//
//    String id;
//    String name;
//    LocalDateTime createdAt;
//    double price;
//    double quantitySold;
//
//    public Product(String category, String id, String name, LocalDateTime createdAt, double price) {
//        this.id = id;
//        this.name = name;
//        this.createdAt = createdAt;
//        this.price = price;
//        this.quantitySold = 0;
//    }
//
//    @Override
//    public String toString() {
//        return "Product{" +
//                "id='" + id + '\'' +
//                ", name='" + name + '\'' +
//                ", createdAt=" + createdAt +
//                ", price=" + price +
//                ", quantitySold=" + quantitySold +
//                '}';
//    }
//
//}
//
//
//class OnlineShop {
//
//    Map<String, Product> productByID;
//    Map<String, List<Product>> categoryMap;
//
//    OnlineShop() {
//        productByID = new HashMap<>();
//        categoryMap = new HashMap<>();
//    }
//
//    void addProduct(String category, String id, String name, LocalDateTime createdAt, double price){
//        productByID.put(id, new Product(category, id, name, createdAt, price));
//    }
//
//    double buyProduct(String id, int quantity) throws ProductNotFoundException{
//        if (!productByID.containsKey(id))
//            throw new ProductNotFoundException("No product with ID: " + id);
//        productByID.get(id).quantitySold = quantity;
//        return quantity * productByID.get(id).price;
//        //return 0.0;
//    }
//
//    List<List<Product>> listProducts(String category, COMPARATOR_TYPE comparatorType, int pageSize) {
//        List<Product> productsInGivenCategory = productByID.values().stream().collect(Collectors.toList());
////        if (category != null) {
////            productsInGivenCategory = productsInGivenCategory.stream()
////                    .filter(product -> product.category.equals(category)).collect(Collectors.toList());
////        }
//        List<List<Product>> result = new ArrayList<>();
//        result.add(new ArrayList<>());
//        return result;
//    }
//
//}
//
//public class OnlineShopTest {
//
//    public static void main(String[] args) {
//        OnlineShop onlineShop = new OnlineShop();
//        double totalAmount = 0.0;
//        Scanner sc = new Scanner(System.in);
//        String line;
//        while (sc.hasNextLine()) {
//            line = sc.nextLine();
//            String[] parts = line.split("\\s+");
//            if (parts[0].equalsIgnoreCase("addproduct")) {
//                String category = parts[1];
//                String id = parts[2];
//                String name = parts[3];
//                LocalDateTime createdAt = LocalDateTime.parse(parts[4]);
//                double price = Double.parseDouble(parts[5]);
//                onlineShop.addProduct(category, id, name, createdAt, price);
//            } else if (parts[0].equalsIgnoreCase("buyproduct")) {
//                String id = parts[1];
//                int quantity = Integer.parseInt(parts[2]);
//                try {
//                    totalAmount += onlineShop.buyProduct(id, quantity);
//                } catch (ProductNotFoundException e) {
//                    System.out.println(e.getMessage());
//                }
//            } else {
//                String category = parts[1];
//                if (category.equalsIgnoreCase("null"))
//                    category=null;
//                String comparatorString = parts[2];
//                int pageSize = Integer.parseInt(parts[3]);
//                COMPARATOR_TYPE comparatorType = COMPARATOR_TYPE.valueOf(comparatorString);
//                printPages(onlineShop.listProducts(category, comparatorType, pageSize));
//            }
//        }
//        System.out.println("Total revenue of the online shop is: " + totalAmount);
//
//    }
//
//    private static void printPages(List<List<Product>> listProducts) {
//        for (int i = 0; i < listProducts.size(); i++) {
//            System.out.println("PAGE " + (i + 1));
//            listProducts.get(i).forEach(System.out::println);
//        }
//    }
//}
//

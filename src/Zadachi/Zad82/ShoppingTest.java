package Zadachi.Zad82;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ShoppingTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();

        int items = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < items; i++) {
            try {
                cart.addItem(sc.nextLine());
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        List<Integer> discountItems = new ArrayList<>();
        int discountItemsCount = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < discountItemsCount; i++) {
            discountItems.add(Integer.parseInt(sc.nextLine()));
        }

        int testCase = Integer.parseInt(sc.nextLine());
        if (testCase == 1) {
            cart.printShoppingCart(System.out);
        } else if (testCase == 2) {
            try {
                cart.blackFridayOffer(discountItems, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}

class InvalidOperationException extends Exception {

    private final String message;

    public InvalidOperationException() {
        message = "There are no products with discount.";
    }

    public InvalidOperationException(String ID) {
        message = String.format("The quantity of the product with id %s can not be 0.", ID);
    }

    @Override
    public String getMessage() {
        return message;
    }

}


class ShoppingCart {

    private List<Item> items;

    ShoppingCart() {
        items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(String itemData) throws InvalidOperationException {
        String[] parts = itemData.split(";");
        String type = parts[0];
        if (type.equals("WS")) {
            items.add(new WholeItem(parts[1], parts[2], parts[3], parts[4]));
        } else {
                items.add(new PerUnitItem(parts[1], parts[2], parts[3], parts[4]));
        }
    }

    public void printShoppingCart(OutputStream out) {
        PrintWriter printWriter = new PrintWriter(out);
        items.sort(Item.comparator);
        for (Item item : items) {
            printWriter.println(item);
            printWriter.flush();
        }
    }

    public void blackFridayOffer(List<Integer> discountItems, PrintStream out) throws InvalidOperationException {
        if (discountItems.isEmpty()) {
            throw new InvalidOperationException();
        }

        items.stream()
                .filter(i -> discountItems.contains(Integer.parseInt(i.getID())))
                .forEach(item -> item.setPrice(item.getPrice() * 9 / 10));

        PrintWriter printWriter = new PrintWriter(out);
        items.stream()
                .filter(i -> discountItems.contains(Integer.parseInt(i.getID())))
                .forEach(i -> {
                    printWriter.println(String.format("%s - %.2f", i.getID(), i.getTotalPrice() * 10 / 9 - i.getTotalPrice()));
                    printWriter.flush();
                } );
    }

}


abstract class Item implements Comparable<Item> {

    private String ID;
    private String name;
    private double price;
    static Comparator<Item> comparator = Comparator.comparing(Item::getTotalPrice, Comparator.reverseOrder());

    public Item(String ID, String name, String price) {
        this.ID = ID;
        this.name = name;
        this.price = Double.parseDouble(price);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    abstract public double getTotalPrice();

    @Override
    public String toString() {
        return String.format("%s - %.2f", ID, getTotalPrice());
    }

}


class WholeItem extends Item {

    private int quantity;

    public WholeItem(String productID, String name, String price, String quantity) throws InvalidOperationException {
        super(productID, name, price);
        this.quantity = Integer.parseInt(quantity);
        if (this.quantity == 0) {
            throw new InvalidOperationException(productID);
        }
    }

    @Override
    public double getTotalPrice() {
        return quantity * getPrice();
    }

    @Override
    public int compareTo(Item o) {
        return comparator.compare(this, o);
    }

}


class PerUnitItem extends Item {

    private double quantity;

    public PerUnitItem(String productID, String name, String price, String quantity) throws InvalidOperationException {
        super(productID, name, price);
        this.quantity = Double.parseDouble(quantity);
        if (this.quantity == 0) {
            throw new InvalidOperationException(productID);
        }
    }

    @Override
    public double getTotalPrice() {
        return quantity / 1000 * getPrice();
    }

    @Override
    public int compareTo(Item o) {
        return comparator.compare(this, o);
    }

}
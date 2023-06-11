package Zadachi.Zad88;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class OnlinePaymentsTest {

    public static void main(String[] args) {

        OnlinePayments onlinePayments = new OnlinePayments();

        onlinePayments.readItems(System.in);

        IntStream.range(151020, 151025)
                .mapToObj(String::valueOf)
                .forEach(id -> onlinePayments.printStudentReport(id, System.out));
    }
}

class OnlinePayments {

    HashMap<Integer, Student> students = new HashMap<>();

    OnlinePayments() {}

    public void readItems(InputStream in) {
        Scanner scanner = new Scanner(in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");

            int index = Integer.parseInt(parts[0]);
            String item_name = parts[1];
            int price = Integer.parseInt(parts[2]);

            students.putIfAbsent(index, new Student(index));
            students.get(index).add_item(item_name, price);
        }
    }


    public void printStudentReport(String id, PrintStream out) {
        int index = Integer.parseInt(id);
        if (!students.containsKey(index)) {
            // !!!
            // Always flush PrintWriter when PrintStream is used in different namespaces
            // Don't close PrintWriter, might close PrintStream...
            new PrintWriter(out).printf("Student %d not found!\n", index).flush();
            return;
        }
        students.get(index).print_report(out);
    }

}

class Student {
    // !!!
    // Don't use TreeSet when comparing prices, since it will remove duplicate prices for distinct items
    List<Item> items;
    int index;

    public Student(int index) {
        this.index = index;
        items = new ArrayList<>();
    }

    void add_item(String item_name, int price) {
        items.add(new Item(item_name, price));
    }

    public void print_report(PrintStream out) {
        PrintWriter printWriter = new PrintWriter(out);
        printWriter.printf("Student: %d Net: %d Fee: %d Total: %d\n",
                index, calculate_total_no_fee(), calculate_fee(), calculate_total());
        printWriter.printf("Items:\n");

        // !!!
        // Sorting lists is a thing :)
        items.sort(Comparator.comparing(Item::get_price).reversed());
        Iterator<Item> item_iterator = items.iterator();
        for (int i = 0; i < items.size(); ++i) {
            Item current_item = item_iterator.next();
            printWriter.printf("%d. %s %d\n", i+1, current_item.get_item_name(), current_item.get_price());
        }
        printWriter.flush();
    }

    int calculate_total_no_fee() {
        int total = 0;
        for (Item item : items) {
            total += item.get_price();
        }
        return total;
    }

    int calculate_fee() {
        double fee = 0.0114 * calculate_total_no_fee();
        if (fee < 3) {
            return 3;
        }
        if (fee > 300) {
            return 300;
        }
        return (int) Math.round(fee);
    }

    int calculate_total() {
        return calculate_total_no_fee() + calculate_fee();
    }

}

class Item {
    String item_name;
    int price;

    public Item(String item_name, int price) {
        this.item_name = item_name;
        this.price = price;
    }

    public String get_item_name() {
        return item_name;
    }

    public void set_item_name(String item_name) {
        this.item_name = item_name;
    }

    public int get_price() {
        return price;
    }

    public void set_price(int price) {
        this.price = price;
    }
}
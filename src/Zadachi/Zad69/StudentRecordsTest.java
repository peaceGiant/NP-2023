package Zadachi.Zad69;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

/**
 * January 2016 Exam problem 1
 */
public class StudentRecordsTest {
    public static void main(String[] args) {
        System.out.println("=== READING RECORDS ===");
        StudentRecords studentRecords = new StudentRecords();
        int total = studentRecords.readRecords(System.in);
        System.out.printf("Total records: %d\n", total);
        System.out.println("=== WRITING TABLE ===");
        studentRecords.writeTable(System.out);
        System.out.println("=== WRITING DISTRIBUTION ===");
        studentRecords.writeDistribution(System.out);
    }
}

// your code here
class StudentRecords {

    Map<String, List<Item>> smerMapa;

    public StudentRecords() {
        this.smerMapa = new HashMap<>();
    }

    public int readRecords(InputStream in) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        bufferedReader.lines().map(Item::new).forEach(item -> {
            smerMapa.putIfAbsent(item.nasoka, new ArrayList<>());
            smerMapa.get(item.nasoka).add(item);
        });
        return (int) smerMapa.values().stream().mapToLong(Collection::size).sum();
    }

    void writeTable(OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);
        smerMapa.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(tuple -> {
            printWriter.println(tuple.getKey()); printWriter.flush();
            tuple.getValue().stream().sorted(Comparator.comparing(Item::getAverage).reversed().thenComparing(Item::getID))
                    .forEach(item -> {printWriter.printf("%s %.2f%n", item.ID, item.getAverage()); printWriter.flush();});
        });
    }

    int getGradesByNasoka(List<Item> items, int grade) {
        return items.stream().mapToInt(i -> i.getGrade(grade)).sum();
    }

    public void writeDistribution(OutputStream out) {
        PrintWriter printWriter = new PrintWriter(out);
        smerMapa.entrySet().stream().sorted(Map.Entry.comparingByValue(
                (i, j) -> Integer.compare(getGradesByNasoka(j, 10), getGradesByNasoka(i, 10))))
                .forEach(entry -> {
                    printWriter.printf("%s%n", entry.getKey());
                    printWriter.flush();
                    IntStream.rangeClosed(6, 10).forEach(i -> {
                        printWriter.printf("%2d | %s(%d)%n",
                                i,
                                "*".repeat((int) Math.ceil(getGradesByNasoka(entry.getValue(), i) / 10f )),
                                getGradesByNasoka(entry.getValue(), i));
                        printWriter.flush();
                    });
                });
    }
}

class Item {

    String ID;
    String nasoka;
    List<Integer> grades;

    public Item(String data) {
        grades = new ArrayList<>();
        String[] parts = data.split("\\s+");
        this.ID = parts[0];
        this.nasoka = parts[1];
        IntStream.range(2, parts.length).forEach(i -> grades.add(Integer.parseInt(parts[i])));
    }

    double getAverage() {
        return 1f * grades.stream().mapToInt(i -> i).sum() / grades.size();
    }

    int getGrade(int grade) {
        return (int) grades.stream().filter(i -> i == grade).count();
    }

    public String getID() {
        return ID;
    }

}
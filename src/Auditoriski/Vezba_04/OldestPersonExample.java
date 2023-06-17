package Auditoriski.Vezba_04;

import java.io.*;
import java.util.List;

public class OldestPersonExample {

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/Auditoriski/Vezba_04/OldPeople.txt");

        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        List<String> lines = bufferedReader.lines().toList();

        int oldest_age = lines.stream()
                .mapToInt(i -> Integer.parseInt(i.split("\\s+")[1]))
                .max().orElse(0);
        System.out.println(oldest_age);

        String oldest_line = lines.stream()
                .reduce("None 0",
                        (i, j) -> i.split("\\s+")[1].compareTo(j.split("\\s+")[1]) > 0 ? i : j);
        System.out.println(oldest_line);
    }

}

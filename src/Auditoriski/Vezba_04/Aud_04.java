package Auditoriski.Vezba_04;

import java.io.*;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface Abubu {
    int test(int a);
}

public class Aud_04 {

    public static void main(String[] args) throws FileNotFoundException {

        Predicate<Integer> predicate = integer -> integer < 100;
        BiPredicate<Integer, Integer> biPredicate = (integer1, integer2) -> integer1 > integer2;

        Supplier<Integer> supplier = () -> 100;
        Consumer<Integer> consumer = i -> { String string = "Consume input"; return; };

        Function<Integer, Integer> function = i -> i+2;
        // !!!
        // function: i -> i+2
        // and then: i+2 -> (i+2) * 200 = 200*i + 400
        // and then: 200*i + 400 -> (200*i + 400 + 100) / 2 = 100*i + 250
        // apply: [i = 3] 100*3 + 250 = 550
        System.out.println(function.andThen(i -> i * 200).andThen(i -> (i + 100)/2).apply(3));

        BiFunction<Integer, Integer, Integer> biFunction = (i, j) -> i*j + 2;
        BiFunction<Integer, String, String> printByIndex =
                (index, string) -> String.format("%d. %s", index, string);
        System.out.println(printByIndex.andThen(
                i -> String.format("%s\n----------", i)).apply(100, "peaceGiant")
        );

        File file = new File("C:\\Users\\*****\\Desktop\\5tiSem.txt");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        List<String> list = bufferedReader.lines().toList();
        int numChars = list.stream()
                .map(String::length)
                .reduce(0, Integer::sum);
        int numLines = list.stream()
                .mapToInt(i -> 1)
                .sum();
        int numWords = list.stream()
                .map(i -> i.split("\\s+"))
                .map(i -> i.length)
                .reduce(0, Integer::sum);
        System.out.println(numChars + " " + numWords + " " + numLines);



    }

}

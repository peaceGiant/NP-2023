package Zadachi.Zad41;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;


public class Anagrams {

    public static void main(String[] args) {
        findAll(System.in);
    }

    public static String sortLetters(String string) {
        return string.chars()
                .mapToObj(i -> (char) i)
                .map(String::valueOf)
                .sorted()
                .collect(Collectors.joining());
    }

    public static void findAll(InputStream inputStream) {
        // Vasiod kod ovde
        Scanner scanner = new Scanner(inputStream);
        Map<String, List<String>> map = new HashMap<>();
        while (scanner.hasNext()) {
            String word = scanner.nextLine();
            String transformedWord = sortLetters(word);
            map.putIfAbsent(transformedWord, new ArrayList<>());
            map.get(transformedWord).add(word);
        }

        List<List<String>> result = new ArrayList<>();
        for (List<String> list : map.values()) {
            if (list.size() >= 5) {
                result.add(list.stream().sorted().collect(Collectors.toList()));
            }
        }
        result.sort(Comparator.comparing(l -> l.get(0)));

        for (List<String> list : result) {
            for (String string : list) {
                System.out.printf("%s ", string);
            }
            System.out.println();
        }
    }
}

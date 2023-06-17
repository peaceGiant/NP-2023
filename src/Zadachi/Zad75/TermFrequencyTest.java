package Zadachi.Zad75;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class TermFrequencyTest {
    public static void main(String[] args) throws FileNotFoundException {
        String[] stop = new String[] { "во", "и", "се", "за", "ќе", "да", "од",
                "ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
                "што", "на", "а", "но", "кој", "ја" };
        TermFrequency tf = new TermFrequency(System.in, stop);
        System.out.println(tf.countTotal());
        System.out.println(tf.countDistinct());
        System.out.println(tf.mostOften(10));
    }
}
// vasiot kod ovde
class TermFrequency {

    Map<String, Integer> frequencyMap;
    List<String> ignoreWords;

    public TermFrequency(InputStream inputStream, String[] stop) {
        frequencyMap = new HashMap<>();
        ignoreWords = Arrays.stream(stop).collect(Collectors.toList());
        processWords(inputStream);
    }

    private void processWords(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            String unprocessedWord = scanner.next();
            String word = transformWord(unprocessedWord);
            if (ignoreWords.contains(word) || Objects.equals(word, "") || word == null) continue;
            frequencyMap.putIfAbsent(word, 0);
            frequencyMap.compute(word, (i, j) -> j + 1);
        }
    }

    private String transformWord(String unprocessedWord) {
        return unprocessedWord.toLowerCase().chars()
                .mapToObj(i -> (char) i)
                .filter(i -> Character.isLetterOrDigit(i) || i == '%' || i == '-')
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    public int countTotal() {
        return frequencyMap.values().stream().mapToInt(Integer::intValue).sum();
    }

    public int countDistinct() {
        return frequencyMap.values().stream().mapToInt(i -> 1).sum();
    }

    public List<String> mostOften(int k) {
        Comparator<Entry<String, Integer>> comparator = (o1, o2) -> {
            if (o1.getValue() != o2.getValue())
                return Integer.compare(o2.getValue(), o1.getValue());
            return o1.getKey().compareTo(o2.getKey());
        };
        return frequencyMap.entrySet().stream().sorted(comparator)
                .limit(k).map(Entry::getKey).collect(Collectors.toList());
    }

}
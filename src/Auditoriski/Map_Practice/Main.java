package Auditoriski.Map_Practice;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Map<String, String> basicMap = new HashMap<>();
        HashMap<String, String> hashMap = new HashMap<>();
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        TreeMap<String, String> treeMap = new TreeMap<>();

            basicMap.   get(0);
            basicMap.   values();
            basicMap.   put("Hello", "There");
            basicMap.   putIfAbsent("Hello", "Bruh");
            basicMap.   remove("Hello");
            basicMap.   entrySet();
        List.of("1", "3", "5").forEach(i -> basicMap.put(i, String.valueOf(Integer.parseInt(i) + 3)));
            basicMap.   merge("1", "2", String::concat);
            basicMap.   computeIfPresent("1", (i, j) -> String.valueOf(j.contains("2")));
            basicMap.   compute("3", (key, value) -> String.valueOf(value.contains("2")));
            basicMap.   forEach((key, value) -> System.out.print(key + " " + value + " | "));
            basicMap.   computeIfAbsent("7", key -> key + "77");
        // basicMap.clear();
        // System.out.println(basicMap.getOrDefault("9", "L"));
        Map<String, String> newBasicMap = new HashMap<>(basicMap);
            newBasicMap.replace("1", "true", "FALSE"); // WON'T REPLACE IF OLDVALUE DOESN"T MATCH
            newBasicMap.replaceAll((i, j) -> j + "!");
        basicMap.   putAll(newBasicMap);
        // System.out.println(basicMap);
        System.out.println();

        hashMap.clone();
        linkedHashMap.clone();

        treeMap.putAll(basicMap);
        treeMap.put("34", "wr");
        System.out.println(treeMap.     pollFirstEntry());
        System.out.println(treeMap.     floorEntry("6"));
        System.out.println(treeMap.     headMap("5", true));
        System.out.println(treeMap.     higherEntry("34"));
        System.out.println(treeMap.subMap("34", true, "5", true));
        System.out.println(treeMap);
    }

}

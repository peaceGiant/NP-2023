package Auditoriski.Funky_Generics;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        Float result = new Calculator().roundThenSum(2, 0.339, 0.321f);
        System.out.println(result + "\n---------------------");

        new Calculator().printSortedArray(
                IntStream.rangeClosed(1, 9)
                .mapToObj(i -> new Calculator().roundThenSum(4, i + 0.3434323, 0.3242134d))
                .toArray(Float[]::new)
        );
    }

}

class Calculator {

    public <K1 extends Number, K2 extends Number> float roundThenSum(int decimalPlace, K1 number1, K2 number2) {
        String format = String.format("%%.%df%n", decimalPlace); // if decimalPlace = 2, format = "%.2f"
        float value1 = Float.parseFloat(String.format(format, number1.doubleValue()));
        float value2 = Float.parseFloat(String.format(format, number2.doubleValue()));

        System.out.println(number1 + " & " + number2 + " -> " + value1 + " " + value2);
        System.out.println("Original sum: " + (number1.doubleValue() + number2.doubleValue()));

        return value1 + value2;
    }

    public <T extends Comparable<T>> void printSortedArray(T[] array) {
        Arrays.stream(array).sorted(Comparator.reverseOrder()).forEach(System.out::println);
    }

}

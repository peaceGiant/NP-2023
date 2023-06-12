package Auditoriski.Vezba_05;

import java.util.Comparator;
import java.util.stream.DoubleStream;

public class Aud_05 {

    public static void main(String[] args) {
        // GENERIC PROGRAMMING
        // !!
        // You can't modify a value inside a stream.
        DoubleStream.of(1.2, 1.3434, 34/233d)
                .sorted()
                .forEach(i -> System.out.println(i));
    }

}


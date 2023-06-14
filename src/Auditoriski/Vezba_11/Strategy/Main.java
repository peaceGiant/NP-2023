package Auditoriski.Vezba_11.Strategy;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;


public class Main {

    public static void main(String[] args) {
        System.out.println("---------------------[>10]");
        Stream.of(1, 2, 3, 10, 12, 14, 203, -4, 34)
                .filter(new GreaterThan10Strategy().execute())
                .forEach(System.out::println);
        System.out.println("---------------------[%2==0]");
        Stream.of(1, 2, 3, 10, 12, 14, 203, -4, 34)
                .filter(new IsEvenStrategy().execute())
                .forEach(System.out::println);
    }

}

// !!
// Is implementing Context necessary?
interface Strategy {

    Predicate<Integer> execute();

}


class GreaterThan10Strategy implements Strategy {

    @Override
    public Predicate<Integer> execute() {
        return i -> i > 10;
    }

}


class IsEvenStrategy implements Strategy {

    @Override
    public Predicate<Integer> execute() {
        return i -> i % 2 == 0;
    }

}
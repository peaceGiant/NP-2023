package Auditoriski.Functional_Interface;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Student student1 = new Student("John", "223000");
        Student student2 = new Student("Mary", "223005");
        Student student3 = new Student("Ana", "226000");

        List<Student> studentList = List.of(student2, student3, student1);
        studentList.forEach(System.out::println);
        System.out.println();
        studentList.stream().sorted().forEach(System.out::println);
        System.out.println();

        Strategy strategy1 = new StrategyA();
        System.out.println(strategy1.getClass());

        Strategy strategy2 = new Strategy() {
            @Override
            public int increment(int number) {
                return number + 2;
            }
        };
        System.out.println(strategy2.getClass());

        Strategy strategy3 = i -> i+3;
        System.out.println(strategy3.getClass());

        // !!
        // Lambda can't be used if type parameters present, use anonymous class or (if possible) method reference
        FunctionalInterfaceExample example = new FunctionalInterfaceExample() {
            @Override
            public <T extends Number> boolean checkPentagonAngles(T a, T b, T c, T d, T e) {
                return a.doubleValue() + b.doubleValue() + c.doubleValue() + d.doubleValue() + e.doubleValue() == 360;
            }
        };
    }

}

interface Strategy {

    int increment(int number);

}

class StrategyA implements Strategy {

    @Override
    public int increment(int number) {
        return number + 1;
    }
}

interface FunctionalInterfaceExample {

    <T extends Number> boolean checkPentagonAngles(T a, T b, T c, T d, T e);

}

class Student implements Comparable<Student> {

    String name;
    String index;

    public Student(String name, String index) {
        this.name = name;
        this.index = index;
    }

    @Override
    public int compareTo(Student s) {
        return index.compareTo(s.index);
    }

    @Override
    public String toString() {
        return String.format("Name: %10s, Index: %10s", name, index);
    }
}

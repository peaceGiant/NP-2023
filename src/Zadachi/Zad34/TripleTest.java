package Zadachi.Zad34;

import java.util.Scanner;


public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.average());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.average());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.average());
        tDouble.sort();
        System.out.println(tDouble);
    }
}


class Triple<E extends Number> {

    E element_1;
    E element_2;
    E element_3;

    public Triple(E element_1, E element_2, E element_3) {
        this.element_1 = element_1;
        this.element_2 = element_2;
        this.element_3 = element_3;
    }

    public double max() {
        return Math.max(element_1.doubleValue(), Math.max(element_2.doubleValue(), element_3.doubleValue()));
    }

    public double average() {
        return (element_1.doubleValue() + element_2.doubleValue() + element_3.doubleValue()) / 3;
    }

    public void sort() {
        double a = element_1.doubleValue(), b = element_2.floatValue(), c = element_3.doubleValue();
        E temp_1 = element_1, temp_2 = element_2, temp_3 = element_3;

        if (a <= b && b <= c) {
            return;
        }
        if (a <= c && c <= b) {
            element_2 = temp_3;
            element_3 = temp_2;
            return;
        }
        if (b <= a && a <= c) {
            element_1 = temp_2;
            element_2 = temp_1;
            return;
        }
        if (b <= c) {
            element_1 = temp_2;
            element_2 = temp_3;
            element_3 = temp_1;
            return;
        }
        if (a <= b) {
            element_1 = temp_3;
            element_2 = temp_1;
            element_3 = temp_2;
            return;
        }
        element_1 = temp_3;
        element_3 = temp_1;
    }

    @Override
    public String toString() {
        return String.format("%.2f %.2f %.2f",
                element_1.doubleValue(), element_2.doubleValue(), element_3.doubleValue());
    }

}
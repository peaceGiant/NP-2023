package Auditoriski.Vezba_11.Factory;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String type = scanner.nextLine();
        String line = scanner.nextLine();

        Product product;
        switch (type) {
            case "a", "A":
                product = new FactoryA().createProduct(line);
                break;
            case "b", "B":
                product = new FactoryB().createProduct(line);
                break;
            default:
                throw new RuntimeException("Not a valid type.");
        }

        product.doStuff();
        System.out.println(product);
    }

}


abstract class Factory {

    abstract Product createProduct(String line);

}


class FactoryA extends Factory {

    @Override
    public Product createProduct(String line) {
        String[] parts = line.split(";");
        String name = parts[0];
        String attributes = parts[1];
        return new ProductA(name, attributes);
    }

}


class FactoryB extends Factory {

    @Override
    public Product createProduct(String line) {
        String[] parts = line.split(";");
        String name = parts[0];
        String attributes = parts[1];
        double price = Double.parseDouble(parts[2]);
        return new ProductB(name, attributes, price);
    }

}


interface Product {

    void doStuff();

}


class ProductA implements Product {

    private String name;
    private String attributes;

    public ProductA(String name, String attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    @Override
    public void doStuff() {
        System.out.println("ProductA is working...");
    }

    @Override
    public String toString() {
        return String.format("A: %s -- %s", name, attributes);
    }

}


class ProductB implements Product {

    private String name;
    private String attributes;
    private double price;

    public ProductB(String name, String attributes, double price) {
        this.name = name;
        this.attributes = attributes;
        this.price = price;
    }

    @Override
    public void doStuff() {
        System.out.println("ProductB is working...");
    }

    @Override
    public String toString() {
        return String.format("B: %s -- %s : %.2f", name, attributes, price);
    }

}
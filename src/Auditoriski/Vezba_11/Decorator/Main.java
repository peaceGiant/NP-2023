package Auditoriski.Vezba_11.Decorator;


public class Main {

    public static void main(String[] args) {
        Component product = new AttributeDecorator(
                new PriceDecorator(
                        new Product("Kafe"),
                        20.4d),
                "Milky");
        product.execute();
    }

}


interface Component {

    void execute();

}


class Product implements Component {

    String name;

    public Product(String name) {
        this.name = name;
    }

    @Override
    public void execute() {
        System.out.println(name);
    }

}


class BaseDecorator implements Component {

    Component wrappee;

    public BaseDecorator(Component wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public void execute() {
        wrappee.execute();
    }

}


class PriceDecorator extends BaseDecorator {

    double price;

    public PriceDecorator(Component wrappee, double price) {
        super(wrappee);
        this.price = price;
    }

    @Override
    public void execute() {
        System.out.println("Price: " + price);
        wrappee.execute();
    }

}


class AttributeDecorator extends BaseDecorator {

    String attribute;

    public AttributeDecorator(Component wrappee, String attribute) {
        super(wrappee);
        this.attribute = attribute;
    }

    @Override
    public void execute() {
        System.out.println("Attribute: " + attribute);
        wrappee.execute();
    }

}
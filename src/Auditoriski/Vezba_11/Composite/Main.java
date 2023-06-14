package Auditoriski.Vezba_11.Composite;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        Component obj1 = new Leaf("data1");
        Component obj2 = new Leaf("data2");
        Component obj3 = new Leaf("data3");
        Component obj4 = new Leaf("data4");
        Component obj5 = new Leaf("data5");
        Component obj6 = new Leaf("data6");

        Composite dad1 = new Composite();
        Composite dad2 = new Composite();
        Composite dad3 = new Composite();
        Composite dad4 = new Composite();

        dad1.addComponent(obj1);
        dad1.addComponent(obj2);
        dad2.addComponent(obj3);
        dad2.addComponent(obj4);
        dad2.addComponent(obj5);
        dad2.addComponent(obj6);
        dad3.addComponent(dad2);
        dad4.addComponent(dad1);
        dad4.addComponent(dad3);

        dad4.execute();
    }

}


interface Component {

    void execute();

}


class Leaf implements Component {

    String data;

    public Leaf(String data) {
        this.data = data;
    }

    @Override
    public void execute() {
        System.out.printf("Working on -- %s\n", data);
    }

    @Override
    public String toString() {
        return String.format("(O:%s)", data);
    }

}


class Composite implements Component {

    List<Component> children;

    public Composite() {
        children = new ArrayList<>();
    }

    public void addComponent(Component component) {
        children.add(component);
    }

    @Override
    public void execute() {
        System.out.printf("Here are my children: %s\n", children.toString());
        System.out.print("-----here is their data------\n");
        for (Component c : children) {
            c.execute();
        }
    }

    @Override
    public String toString() {
        return String.format("<O:%s>", children.toString());
    }

}

package Auditoriski.Vezba_11.Observer;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        Publisher publisher = new ConcretePublisher("Data...");
        Observer observer1 = new ConcreteObserver("");
        Observer observer2 = new ConcreteObserver("");
        Observer observer3 = new ConcreteObserver("");

        publisher.registerObserver(observer1);
        publisher.registerObserver(observer2);
        publisher.registerObserver(observer3);

        publisher.notifyObservers();
        publisher.removeObserver(observer1);
        publisher.print();
    }

}


interface Observer {

    void update(String data);

}


class ConcreteObserver implements Observer {

    String data;

    public ConcreteObserver(String data) {
        this.data = data;
    }

    @Override
    public void update(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("%s is data", data);
    }
}


interface Publisher {

    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
    void print();

}


class ConcretePublisher implements Publisher {

    List<Observer> observerList;
    String data;

    public ConcretePublisher(String data) {
        this.observerList = new ArrayList<>();
        this.data = data;
    }

    @Override
    public void registerObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observerList) {
            o.update(data);
        }
    }

    @Override
    public void print() {
        for (Observer o : observerList) {
            System.out.println(o);
        }
    }

}

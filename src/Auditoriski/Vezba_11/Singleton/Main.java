package Auditoriski.Vezba_11.Singleton;


public class Main {

    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        singleton.setData("some data");
        System.out.println(singleton.getData());
    }

}


class Singleton {

    String data;
    // !!
    // volatile: ensure instance is read from main memory, not thread cache
    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}

package Auditoriski.Vezba_11.State;


public class Main {

    public static void main(String[] args) {
        Context context = new Context();

        System.out.println(context.state.getClass());
        context.doThis();

        System.out.println(context.state.getClass());
        context.doThat();

        System.out.println(context.state.getClass());
        context.state.doThis();

        System.out.println(context.state.getClass());
        context.state.doThat();
    }

}


interface State {

    void doThis();
    void doThat();

}


class ThisState implements State {

    Context context;

    public ThisState(Context context) {
        this.context = context;
    }

    @Override
    public void doThis() {
        System.out.println("Already doing this...");
    }

    @Override
    public void doThat() {
        System.out.println("Stopping this -> Starting that...");
        context.changeState(context.thatState);
    }

}


class ThatState implements State {

    Context context;

    public ThatState(Context context) {
        this.context = context;
    }

    @Override
    public void doThis() {
        System.out.println("Stopping that -> Starting this...");
        context.changeState(context.thisState);
    }

    @Override
    public void doThat() {
        System.out.println("Already doing that...");
    }

}


class Context {

    // Implement getters and setters... Current state: lazy
    public State state;

    public State thisState;
    public State thatState;

    public Context() {
        thisState = new ThisState(this);
        thatState = new ThatState(this);
        this.state = thisState;
    }

    public void changeState(State state) {
        this.state = state;
    }

    public void doThis() {
        state.doThis();
    }

    public void doThat() {
        state.doThat();
    }

}




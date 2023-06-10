package Auditoriski.Vezba_02;

public class Aud_2 {

    public static void main(String[] args) {
        CombinationLock combinationLock;

        try {
            combinationLock = new CombinationLock("123");
            System.out.println(combinationLock);

            combinationLock.open("123");
            System.out.println(combinationLock);

            combinationLock.changeCombo("123", "444");
            System.out.println(combinationLock);

            combinationLock.changeCombo("123", "f");
            System.out.println("This line won't be printed");
        } catch (InvalidCombinationException e) {
            System.out.println(e.getMessage());
        }
    }

}

class InvalidCombinationException extends Exception {

    @Override
    public String getMessage() {
        return "INVALID INPUT COMBINATION -- COMBINATION MUST BE A SEQUENCE OF 3 DIGITS";
    }

}

class CombinationLock {

    private String combination;
    private boolean isLocked;

    CombinationLock(String combination) throws InvalidCombinationException {
        if (is_combination_valid(combination)) {
            this.combination = combination;
            isLocked = true;
        }
    }

    boolean is_combination_valid(String combination) throws InvalidCombinationException {
        if (combination.length() != 3 ||
                !Character.isDigit(combination.charAt(0)) ||
                !Character.isDigit(combination.charAt(1)) ||
                !Character.isDigit(combination.charAt(2))) {
            throw new InvalidCombinationException();
        }

        return true;
    }

    boolean open(String combination) {
        if (combination.equals(this.combination)) {
            this.isLocked = false;
            return true;
        }
        return false;
    }

    void changeCombo(String old_combination, String new_combination) throws InvalidCombinationException {
        if (is_combination_valid(new_combination) && open(old_combination)) {
            this.combination = new_combination;
        }
        isLocked = true;
    }

    @Override
    public String toString() {
        return String.format("Lock state: %s\tCombination: %s", this.isLocked, this.combination);
    }
}

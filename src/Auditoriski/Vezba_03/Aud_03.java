package Auditoriski.Vezba_03;

enum Day {
    Monday,
    Tuesday,
    Wednesday,
    Thrust_day {
        @Override
        String day_name() {
            return "Thursday";
        }
    },
    Friday,
    Saturday {
        @Override
        boolean is_weekend() {
            return true;
        }
    },
    Sunday {
        @Override
        boolean is_weekend() {
            return true;
        }
    };

    void print_all_days() {
        System.out.printf("%-15s %s\n", "Day", "Is Weekend");
        for (Day day : Day.values()) {
            System.out.printf("%-15s %s\n", day.day_name(), day.is_weekend());
        }
    }

    String day_name() {
        return this.name();
    }

    boolean is_weekend() {
        return false;
    }
}

public class Aud_03 {

    public static void main(String[] args) {
        Day current_day = Day.Sunday;
        current_day.print_all_days();
    }

}

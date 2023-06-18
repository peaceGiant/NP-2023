package Zadachi.Zad11;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * LocalTime API tests
 */
public class LocalTimeTest {
    public static void main(String[] args) {
        System.out.println(localTimeOfHourToMinute());
        System.out.println(localTimeOfHourToNanoSec());
        System.out.println(localTimeParse());
        System.out.println(localTimeWith());
        System.out.println(localTimePlus());
        System.out.println(localTimeMinus());
        System.out.println(localTimeMinusDuration());
        System.out.println(localDateIsBefore());
        System.out.println(localTimeTruncatedTo());
    }


    /**
     * Create a {@link LocalTime} of 23:07 by using {@link LocalTime#of}
     */
    static LocalTime localTimeOfHourToMinute() {
        return LocalTime.of(23, 7);
    }

    /**
     * Create a {@link LocalTime} of 23:07:03.1 by using {@link LocalTime#of}
     */
    static LocalTime localTimeOfHourToNanoSec() {
        return LocalTime.of(23, 7, 3, 100000000);
    }

    /**
     * Create a {@link LocalTime} of 23:07:03.1 from String by using {@link LocalTime#parse}
     */
    static LocalTime localTimeParse() {
        return LocalTime.parse("23:07:03.1");
    }

    /**
     * Create a {@link LocalTime} from {lt} with hour 21
     * by using {@link LocalTime#withHour} or {@link LocalTime#with}
     */
    static LocalTime localTimeWith() {

        LocalTime lt = DateAndTimes.LT_23073050;

        return lt.withHour(21);
    }

    /**
     * Create a {@link LocalTime} from lt with 30 minutes later
     * by using {@link LocalTime#plusMinutes} or {@link LocalTime#plus}
     */
    static LocalTime localTimePlus() {
        LocalTime lt = DateAndTimes.LT_23073050;

        return lt.plus(30, ChronoUnit.MINUTES);
    }

    /**
     * Create a {@link LocalTime} from { lt} with 3 hours before
     * by using {@link LocalTime#minusHours} or {@link LocalTime#minus}
     */
    static LocalTime localTimeMinus() {
        LocalTime lt = DateAndTimes.LT_23073050;

        return lt.minus(3, ChronoUnit.HOURS);
    }

    /**
     * Define a {@link Duration} of 3 hours 30 minutes and 20.2 seconds
     * Create a {@link LocalTime} subtracting the duration from {lt} by using {@link LocalTime#minus}
     */
    static LocalTime localTimeMinusDuration() {
        LocalTime lt = DateAndTimes.LT_23073050;

        return lt.minus(Duration.of(3, ChronoUnit.HOURS).plus(30, ChronoUnit.MINUTES)
                .plus(20, ChronoUnit.SECONDS).plus(200, ChronoUnit.MILLIS));
    }

    /**
     * Check whether {lt2} is before {lt} or not
     * by using {@link LocalTime#isAfter} or {@link LocalTime#isBefore}
     */
    static boolean localDateIsBefore() {
        LocalTime lt = DateAndTimes.LT_23073050;
        LocalTime lt2 = DateAndTimes.LT_12100000;

        return lt2.isBefore(lt);
    }

    /**
     * Create a {@link LocalTime} from {lt} truncated to minutes by using {@link LocalTime#truncatedTo}
     */
    static LocalTime localTimeTruncatedTo() {
        LocalTime lt = DateAndTimes.LT_23073050;

        return lt.truncatedTo(ChronoUnit.SECONDS);
    }

    static class DateAndTimes {
        public static final LocalTime LT_23073050 = LocalTime.of(23, 7, 30, 500000000);
        public static final LocalTime LT_12100000 = LocalTime.of(12, 10);
    }

}

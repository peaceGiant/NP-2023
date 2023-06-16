package Zadachi.Zad77; 

import java.util.*;
import java.util.stream.Collectors;

class DurationConverter {
    public static String convert(long duration) {
        long minutes = duration / 60;
        duration %= 60;
        return String.format("%02d:%02d", minutes, duration);
    }
}


public class TelcoTest2 {
    public static void main(String[] args) {
        TelcoApp app = new TelcoApp();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");
            String command = parts[0];

            if (command.equals("addCall")) {
                String uuid = parts[1];
                String dialer = parts[2];
                String receiver = parts[3];
                long timestamp = Long.parseLong(parts[4]);
                app.addCall(uuid, dialer, receiver, timestamp);
            } else if (command.equals("updateCall")) {
                String uuid = parts[1];
                long timestamp = Long.parseLong(parts[2]);
                String action = parts[3];
                app.updateCall(uuid, timestamp, action);
            } else if (command.equals("printChronologicalReport")) {
                String phoneNumber = parts[1];
                app.printChronologicalReport(phoneNumber);
            } else if (command.equals("printReportByDuration")) {
                String phoneNumber = parts[1];
                app.printReportByDuration(phoneNumber);
            } else {
                app.printCallsDuration();
            }
        }

    }
}


class TelcoApp {

    List<Call> calls;

    public TelcoApp() {
        this.calls = new ArrayList<>();
    }

    public void addCall(String uuid, String dialer, String receiver, long timestamp) {
        calls.add(new Call(uuid, dialer, receiver, timestamp));
    }

    public void updateCall(String uuid, long timestamp, String action) {
        Call c = calls.stream().filter(call -> call.uuid.equals(uuid)).findFirst().orElse(null);
        assert c != null;
        c.updateCall(timestamp, action);
    }

    public void printChronologicalReport(String phoneNumber) {
        List<Call> callList = new ArrayList<>();
        for (Call call : calls) {
            if (call.containsNumber(phoneNumber) != 0)
                callList.add(call);
        }
        callList.sort(Comparator.comparingLong(call -> call.answer));

        String type, otherNumber, start, end, duration;
        for (Call call : callList) {
            if (call.containsNumber(phoneNumber) == 1) {
                type = "D";
                otherNumber = call.receiver;
            } else {
                type = "R";
                otherNumber = call.dialer;
            }
            start = String.valueOf(call.answer);
            end = call.durationOfCall > 0 ? String.valueOf(call.end) : "MISSED CALL";
            duration = DurationConverter.convert(call.durationOfCall);
            System.out.printf("%s %s %s %s %s\n", type, otherNumber, start, end, duration);
        }
    }

    public void printReportByDuration(String phoneNumber) {
        List<Call> callList = new ArrayList<>();
        for (Call call : calls) {
            if (call.containsNumber(phoneNumber) != 0)
                callList.add(call);
        }
        callList = callList.stream().sorted(Comparator.comparing(Call::getDurationOfCall).thenComparing(c -> c.answer).reversed())
                .collect(Collectors.toList());
        String type, otherNumber, start, end, duration;
        for (Call call : callList) {
            if (call.containsNumber(phoneNumber) == 1) {
                type = "D";
                otherNumber = call.receiver;
            } else {
                type = "R";
                otherNumber = call.dialer;
            }
            start = String.valueOf(call.answer);
            end = call.durationOfCall > 0 ? String.valueOf(call.end) : "MISSED CALL";
            duration = DurationConverter.convert(call.durationOfCall);
            System.out.printf("%s %s %s %s %s\n", type, otherNumber, start, end, duration);
        }
    }

    public void printCallsDuration() {
        List<String> dialers = calls.stream().map(call -> call.dialer).distinct().collect(Collectors.toList());
        List<String> receivers = calls.stream().map(call -> call.receiver).distinct().collect(Collectors.toList());
        List<List<String>> results = new ArrayList<>();
        dialers.forEach(dialer -> {
            receivers.forEach(receiver -> {
                if (calls.stream().noneMatch(call -> call.containsNumber(dialer) == 1 && call.containsNumber(receiver) == -1))
                    return;

                int duration = (int) calls.stream().filter(call -> call.containsNumber(dialer) == 1)
                        .filter(call -> call.containsNumber(receiver) == -1)
                        .mapToLong(call -> call.durationOfCall)
                        .sum();
                results.add(List.of(dialer, receiver, DurationConverter.convert(duration)));
            });
        });
        results.sort((i, j) -> j.get(2).compareTo(i.get(2)));
        for (List<String> killMeAlready : results)
            System.out.printf("%s <-> %s : %s\n", killMeAlready.get(0), killMeAlready.get(1), killMeAlready.get(2));
    }

}


class Call {

    String uuid;
    String dialer;
    String receiver;
    long timestamp;

    long durationOfCall;
    long intervalBegin;
    long intervalEnd;
    long answer;
    long end;

    CallState currentState;

    CallIdle callIdle;
    CallPaused callPaused;
    CallRinging callRinging;
    CallInProgress callInProgress;

    public Call(String uuid, String dialer, String receiver, long timestamp) {
        this.uuid = uuid;
        this.dialer = dialer;
        this.receiver = receiver;
        this.timestamp = timestamp;

        durationOfCall = 0;
        intervalBegin = 0;
        intervalEnd = 0;
        answer = end = 0;

        callIdle = new CallIdle(this);
        callInProgress = new CallInProgress(this);
        callRinging = new CallRinging(this);
        callPaused = new CallPaused(this);

        currentState = callRinging;
    }

    public void updateCall(long timestamp, String action) {
        switch (action) {
            case "ANSWER":
                currentState.answerCall(timestamp); break;
            case "END":
                currentState.endCall(timestamp); break;
            case "HOLD":
                currentState.holdCall(timestamp); break;
            case "RESUME":
                currentState.resumeCall(timestamp); break;
            default: throw new RuntimeException("INVALID ACTION FOR CALL!!!");
        }
    }

    int containsNumber(String number) {
        if (dialer.equals(number))
            return 1;
        if (receiver.equals(number))
            return -1;
        return 0;
    }

    public long getDurationOfCall() {
        return durationOfCall;
    }

}


abstract class CallState {

    Call call;

    CallState(Call call) {
        this.call = call;
    }

    void createCall(long timestamp) {
        throw new RuntimeException("STATE PATTERN ERROR");
    }

    void endCall(long timestamp) {
        throw new RuntimeException("STATE PATTERN ERROR");
    }

    void holdCall(long timestamp) {
        throw new RuntimeException("STATE PATTERN ERROR");
    }

    void resumeCall(long timestamp) {
        throw new RuntimeException("STATE PATTERN ERROR");
    }

    void answerCall(long timestamp) {
        throw new RuntimeException("STATE PATTERN ERROR");
    }

}


class CallIdle extends CallState {

    CallIdle(Call call) {
        super(call);
    }

    @Override
    void createCall(long timestamp) {
        call.intervalBegin = call.intervalEnd = timestamp;
        call.currentState = call.callRinging;
    }

}

class CallPaused extends CallState {

    CallPaused(Call call) {
        super(call);
    }

    @Override
    void resumeCall(long timestamp) {
        call.intervalBegin = call.intervalEnd = timestamp;
        call.currentState = call.callInProgress;
    }

    @Override
    void endCall(long timestamp) {
        call.currentState = call.callIdle;
        call.end = timestamp;
    }
}

class CallRinging extends CallState {

    CallRinging(Call call) {
        super(call);
    }

    @Override
    void answerCall(long timestamp) {
        call.answer = timestamp;
        call.intervalBegin = timestamp;
        call.currentState = call.callInProgress;
    }

    @Override
    void endCall(long timestamp) {
        call.answer = timestamp;
        call.currentState = call.callIdle;
        call.end = timestamp;
    }

}

class CallInProgress extends CallState {

    CallInProgress(Call call) {
        super(call);
    }

    @Override
    void holdCall(long timestamp) {
        call.intervalEnd = timestamp;
        call.durationOfCall += (call.intervalEnd - call.intervalBegin);
        call.currentState = call.callPaused;
    }

    @Override
    void endCall(long timestamp) {
        call.intervalEnd = timestamp;
        call.durationOfCall += (call.intervalEnd - call.intervalBegin);
        call.currentState = call.callIdle;
        call.end = timestamp;
    }

}
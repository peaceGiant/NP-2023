package Zadachi.Zad80;

import java.util.*;
import java.util.stream.Collectors;

public class LogsTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LogCollector collector = new LogCollector();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.startsWith("addLog")) {
                collector.addLog(line.replace("addLog ", ""));
            } else if (line.startsWith("printServicesBySeverity")) {
                collector.printServicesBySeverity();
            } else if (line.startsWith("getSeverityDistribution")) {
                String[] parts = line.split("\\s+");
                String service = parts[1];
                String microservice = null;
                if (parts.length == 3) {
                    microservice = parts[2];
                }
                collector.getSeverityDistribution(service, microservice).forEach((k,v)-> System.out.printf("%d -> %d%n", k,v));
            } else if (line.startsWith("displayLogs")){
                String[] parts = line.split("\\s+");
                String service = parts[1];
                String microservice = null;
                String order = null;
                if (parts.length == 4) {
                    microservice = parts[2];
                    order = parts[3];
                } else {
                    order = parts[2];
                }
                System.out.println(line);

                collector.displayLogs(service, microservice, order);
            }
        }
    }
}


class LogCollector {

    private Map<String, Service> serviceMap;

    public LogCollector() {
        serviceMap = new HashMap<>();
    }

    public void addLog(String logData) {
        Log log = new LogFactory().build(logData);
        serviceMap.putIfAbsent(log.getServiceName(), new Service(log.getServiceName()));
        serviceMap.get(log.getServiceName()).addLog(log);
    }

    public void printServicesBySeverity() {
        Comparator<Service> comparator = Comparator.comparing(Service::getAverageSeverity).reversed();
        List<Service> tempService = serviceMap.values().stream().sorted(comparator).collect(Collectors.toList());
        for (Service service : tempService) {
            System.out.printf("Service name: %s Count of microservices: %d Total logs in service: %d Average severity for all logs: %.2f Average number of logs per microservice: %.2f\n",
                    service.getName(),
                    (int) service.getMicroserviceMap().values().stream().distinct().count(),
                    (int) service.getMicroserviceMap().values().stream().mapToLong(microservice -> microservice.getLogList().size()).sum(),
                    service.getAverageSeverity(),
                    service.getMicroserviceMap().values().stream().mapToLong(microservice -> microservice.getLogList().size()).average().orElse(0f));
        }
    }

    public Map<Integer, Integer> getSeverityDistribution(String service, String microservice) {
        if (microservice != null) {
            return serviceMap.get(service).getMicroserviceMap().get(microservice).getLogList().stream()
                    .collect(Collectors.groupingBy(
                            Log::getSeverityMeasure,
                            Collectors.summingInt(log -> 1)
                    ));
        }
        return serviceMap.get(service).getMicroserviceMap().values().stream()
                .flatMap(microservice_ -> microservice_.getLogList().stream())
                .collect(Collectors.groupingBy(
                        Log::getSeverityMeasure,
                        Collectors.summingInt(log -> 1)
                ));
    }

    public void displayLogs(String service, String microservice, String order) {
        List<Log> logs;
        if (microservice != null)
             logs = serviceMap.get(service).getMicroserviceMap().get(microservice).getLogList();
        else
            logs = serviceMap.get(service).getMicroserviceMap().values().stream()
                    .flatMap(microservice_ -> microservice_.getLogList().stream())
                    .collect(Collectors.toList());

        switch (order.toUpperCase()) {
            case "NEWEST_FIRST":
                logs.stream().sorted(Comparator.comparing(Log::getTimestamp).reversed())
                        .forEach(log -> System.out.printf("%s|%s [%s] %s T:%d\n",
                                log.getServiceName(),
                                log.getMicroserviceName(),
                                log.getSeverity(),
                                log.getMessage(),
                                log.getTimestamp()));
                break;
            case "OLDEST_FIRST":
                logs.stream().sorted(Comparator.comparing(Log::getTimestamp))
                        .forEach(log -> System.out.printf("%s|%s [%s] %s T:%d\n",
                                log.getServiceName(),
                                log.getMicroserviceName(),
                                log.getSeverity(),
                                log.getMessage(),
                                log.getTimestamp()));
                break;
            case "MOST_SEVERE_FIRST":
                logs.stream().sorted(Comparator.comparing(Log::getSeverityMeasure).reversed())
                        .forEach(log -> System.out.printf("%s|%s [%s] %s T:%d\n",
                                log.getServiceName(),
                                log.getMicroserviceName(),
                                log.getSeverity(),
                                log.getMessage(),
                                log.getTimestamp()));
                break;
            case "LEAST_SEVERE_FIRST":
                logs.stream().sorted(Comparator.comparing(Log::getSeverityMeasure))
                        .forEach(log -> System.out.printf("%s|%s [%s] %s T:%d\n",
                                log.getServiceName(),
                                log.getMicroserviceName(),
                                log.getSeverity(),
                                log.getMessage(),
                                log.getTimestamp()));
                break;
            default:
                throw new RuntimeException("I threw this error...");
        }
    }

}


class Service {

    private String name;
    private Map<String, Microservice> microserviceMap;

    public Service(String name) {
        this.name = name;
        microserviceMap = new HashMap<>();
    }

    public void addLog(Log log) {
        microserviceMap.putIfAbsent(log.getMicroserviceName(), new Microservice(log.getMicroserviceName()));
        microserviceMap.get(log.getMicroserviceName()).addLog(log);
    }

    public double getAverageSeverity() {
        return microserviceMap.values().stream()
                .flatMap(microservice -> microservice.getLogList().stream())
                .mapToInt(log -> log.getSeverityMeasure())
                .average().orElse(0f);
    }

    public String getName() {
        return name;
    }

    public Map<String, Microservice> getMicroserviceMap() {
        return microserviceMap;
    }

}


class Microservice {

    private String name;
    private List<Log> logList;

    public Microservice(String name) {
        this.name = name;
        logList = new ArrayList<>();
    }

    public void addLog(Log log) {
        logList.add(log);
    }

    public List<Log> getLogList() {
        return logList;
    }

}


class Log {

    private String serviceName;
    private String microserviceName;
    private Severity severity;
    private String message;
    private int timestamp;

    public Log(String serviceName, String microserviceName, Severity severity, String message, int timestamp) {
        this.serviceName = serviceName;
        this.microserviceName = microserviceName;
        this.severity = severity;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getMicroserviceName() {
        return microserviceName;
    }

    // !!
    // THIS SHOULD BE DONE SEPARATELY FOR EACH TYPE OF LOG
    public int getSeverityMeasure() {
        int tempMeasure = 0;
        if (severity == Severity.INFO) { return tempMeasure; }

        if (severity == Severity.WARN) {
            tempMeasure = 1;
            if (message.contains("might cause error")) {
                ++tempMeasure;
            }
        }

        if (severity == Severity.ERROR) {
            tempMeasure = 3;
            if (message.contains("fatal")) {
                tempMeasure += 2;
            }
            if (message.contains("exception")) {
                tempMeasure += 3;
            }
        }
        return tempMeasure;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getSeverity() {
        return severity.name();
    }

    public String getMessage() {
        return message;
    }
}

class LogFactory {

    public Log build(String logData) {
        String[] parts = logData.split("\\s+");
        String serviceName = parts[0];
        String microserviceName = parts[1];
        Severity severity;
        switch (parts[2].toUpperCase()) {
            case "INFO":
                severity = Severity.INFO; break;
            case "WARN":
                severity = Severity.WARN; break;
            case "ERROR":
                severity = Severity.ERROR; break;
            default:
                throw new RuntimeException("New Error");
        }
        int timestamp = Integer.parseInt(parts[parts.length - 1]);
        ArrayList<String> temp = new ArrayList<>(Arrays.stream(logData.split("\\s+")).collect(Collectors.toList()));
        temp.remove(temp.size() - 1);
        String message = temp.stream().skip(3).collect(Collectors.joining(" "));
        return new Log(serviceName, microserviceName, severity, message, timestamp);
    }
}

enum Severity {
    INFO, WARN, ERROR
}
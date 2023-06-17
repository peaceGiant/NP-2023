package Zadachi.Zad85;

import java.util.*;
import java.util.stream.Collectors;


public class CourseTest {

    public static void printStudents(List<Student> students) {
        students.forEach(System.out::println);
    }

    public static void printMap(Map<Integer, Integer> map) {
        map.forEach((k, v) -> System.out.printf("%d -> %d%n", k, v));
    }

    public static void main(String[] args) {
        AdvancedProgrammingCourse advancedProgrammingCourse = new AdvancedProgrammingCourse();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");

            String command = parts[0];

            if (command.equals("addStudent")) {
                String id = parts[1];
                String name = parts[2];
                advancedProgrammingCourse.addStudent(new Student(id, name));
            } else if (command.equals("updateStudent")) {
                String idNumber = parts[1];
                String activity = parts[2];
                int points = Integer.parseInt(parts[3]);
                try {
                    advancedProgrammingCourse.updateStudent(idNumber, activity, points);
                } catch (RuntimeException ignored) { }
            } else if (command.equals("getFirstNStudents")) {
                int n = Integer.parseInt(parts[1]);
                printStudents(advancedProgrammingCourse.getFirstNStudents(n));
            } else if (command.equals("getGradeDistribution")) {
                printMap(advancedProgrammingCourse.getGradeDistribution());
            } else {
                advancedProgrammingCourse.printStatistics();
            }
        }
    }
}

class Student {

    private String index;
    private String name;
    private int pointsFirstExam;
    private int pointsSecondExam;
    private int pointsLab;

    public Student(String index, String name) {
        this.index = index;
        this.name = name;
        pointsFirstExam = pointsSecondExam = pointsLab = 0;
    }

    public double getTotalPoints() {
        return pointsFirstExam * .45 + pointsSecondExam * .45 + pointsLab;
    }

    public int getGrade() {
        if (getTotalPoints() < 50) return 5;
        if (getTotalPoints() < 60) return 6;
        if (getTotalPoints() < 70) return 7;
        if (getTotalPoints() < 80) return 8;
        if (getTotalPoints() < 90) return 9;
        return 10;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPointsFirstExam() {
        return pointsFirstExam;
    }

    public void setPointsFirstExam(int pointsFirstExam) {
        this.pointsFirstExam = pointsFirstExam;
    }

    public int getPointsSecondExam() {
        return pointsSecondExam;
    }

    public void setPointsSecondExam(int pointsSecondExam) {
        this.pointsSecondExam = pointsSecondExam;
    }

    public int getPointsLab() {
        return pointsLab;
    }

    public void setPointsLab(int pointsLab) {
        this.pointsLab = pointsLab;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %s Name: %s First midterm: %d Second midterm %d Labs: %d Summary points: %.2f Grade: %d",
                index, name, pointsFirstExam, pointsSecondExam, pointsLab, getTotalPoints(), getGrade());
    }

}

class AdvancedProgrammingCourse {

    private Map<String, Student> studentsByIndex;

    public AdvancedProgrammingCourse() {
        this.studentsByIndex = new HashMap<>();
    }

    public void addStudent(Student s) {
        studentsByIndex.put(s.getIndex(), s);
    }

    public void updateStudent(String idNumber, String activity, int points) throws RuntimeException {
        Student student = studentsByIndex.get(idNumber);
        switch (activity.toUpperCase()) {
            case "MIDTERM1":
                if (points < 0 || points > 100)
                    throw new RuntimeException("updateStudent oopsie");
                student.setPointsFirstExam(points);
                break;
            case "MIDTERM2":
                if (points < 0 || points > 100)
                    throw new RuntimeException("updateStudent oopsie");
                student.setPointsSecondExam(points);
                break;
            case "LABS":
                if (points < 0 || points > 10)
                    throw new RuntimeException("updateStudent oopsie");
                student.setPointsLab(points);
                break;
            default:
                throw new RuntimeException("updateStudent oopsie");
        }
    }

    public List<Student> getFirstNStudents(int n) {
        return studentsByIndex.values().stream()
                .sorted(Comparator.comparingDouble(Student::getTotalPoints).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    public Map<Integer, Integer> getGradeDistribution() {
        Map<Integer, Integer> result = studentsByIndex.values().stream().collect(Collectors.groupingBy(
                Student::getGrade,
                Collectors.summingInt(s -> 1)
        ));
        result.putIfAbsent(5, 0);
        result.putIfAbsent(6, 0);
        result.putIfAbsent(7, 0);
        result.putIfAbsent(8, 0);
        result.putIfAbsent(9, 0);
        result.putIfAbsent(10, 0);
        return result;
    }

    public void printStatistics() {
        DoubleSummaryStatistics doubleSummaryStatistics = studentsByIndex.values().stream()
                .filter(s -> s.getTotalPoints() >= 50)
                .mapToDouble(Student::getTotalPoints)
                .summaryStatistics();
        System.out.printf("Count: %d Min: %.2f Average: %.2f Max: %.2f",
                doubleSummaryStatistics.getCount(),
                doubleSummaryStatistics.getMin(),
                doubleSummaryStatistics.getAverage(),
                doubleSummaryStatistics.getMax());
    }

}
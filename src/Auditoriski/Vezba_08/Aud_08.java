package Auditoriski.Vezba_08;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <pre>{@code
 * // Accumulate names into a List
 * List<String> list = people.stream()
 *   .map(Person::getName)
 *   .collect(Collectors.toList());
 *
 * // Accumulate names into a TreeSet
 * Set<String> set = people.stream()
 *   .map(Person::getName)
 *   .collect(Collectors.toCollection(TreeSet::new));
 *
 * // Convert elements to strings and concatenate them, separated by commas
 * String joined = things.stream()
 *   .map(Object::toString)
 *   .collect(Collectors.joining(", "));
 *
 * // Compute sum of salaries of employee
 * int total = employees.stream()
 *   .collect(Collectors.summingInt(Employee::getSalary));
 *
 * // Group employees by department
 * Map<Department, List<Employee>> byDept = employees.stream()
 *   .collect(Collectors.groupingBy(Employee::getDepartment));
 *
 * // Compute sum of salaries by department
 * Map<Department, Integer> totalByDept = employees.stream()
 *   .collect(Collectors.groupingBy(Employee::getDepartment,
 *                                  Collectors.summingInt(Employee::getSalary)));
 *
 * // Partition students into passing and failing
 * Map<Boolean, List<Student>> passingFailing = students.stream()
 *   .collect(Collectors.partitioningBy(s -> s.getGrade() >= PASS_THRESHOLD));
 *
 * }</pre>
 */

public class Aud_08 {

    public static boolean is_even(int integer) {
        return integer % 2 == 0;
    }

    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Ha");
        map.put(2, "He");
        map.put(3, "Hi");
        map.put(4, "Ho");

        Map<Boolean, HashSet<String>> a = map.entrySet().stream()
                .collect(Collectors.groupingBy(
                        i -> is_even(i.getKey()),
                        Collectors.mapping(Map.Entry::getValue, Collectors.toCollection(LinkedHashSet::new))
                ));

        System.out.println(a);
    }

}
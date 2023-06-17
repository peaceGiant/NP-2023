package Zadachi.Zad86;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RiskTester {
    public static void main(String[] args) {

        Risk risk = new Risk();

        System.out.println(risk.processAttacksData(System.in));

    }
}

class Risk {

    public int processAttacksData(InputStream is) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

        return bufferedReader.lines().map(RolledDice::new)
                .mapToInt(RolledDice::attackSuccess)
                .sum();
    }

}

class RolledDice {

    private List<Integer> attackRolls;
    private List<Integer> defendRolls;

    public RolledDice(String nonProcessedData) {
        String[] parts = nonProcessedData.split(";");
        attackRolls = Arrays.stream(parts[0].split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        defendRolls = Arrays.stream(parts[1].split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        attackRolls.sort(Comparator.reverseOrder());
        defendRolls.sort(Comparator.reverseOrder());
    }

    public int attackSuccess() {
        Map<Integer, Integer> map = IntStream.range(0, attackRolls.size())
                .boxed()
                .collect(Collectors.toMap(i -> attackRolls.get(i), j -> defendRolls.get(j), Math::max));
        return map.entrySet().stream().allMatch(i -> i.getKey() > i.getValue()) ? 1 : 0;
    }

}

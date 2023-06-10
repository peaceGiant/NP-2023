package Auditoriski.Vezba_01;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Aud_1 {

    public static void main(String[] args) {
        Aud_1 env = new Aud_1();
//        Testing using debugger
        env.isPrefix_stream_version("comp", "complicated");
        System.out.println();
    }

    void find_pairs_integers_eq_1(int upper_bound) {
        for (int i = 0; i <= upper_bound; ++i) {
            for (int j = i + 1; j <= upper_bound; ++j) {
                if ((Math.pow(i, 2) + Math.pow(j, 2) + 1) % (i * j) == 0) {
                    System.out.printf("(%d, %d)\n", i, j);
                    // Another way to format is using String.format
                }
            }
        }
    }

    void print_bracketed_numbers(int how_many, int line_length) {
        int current_line_length = line_length;

        for (int i = 1; i <= how_many; ++i) {
            int num_length = String.format("%d", i).length();

            if (num_length + 2 <= current_line_length) {
                System.out.printf("[%d]", i);
                current_line_length -= num_length + 2;
            } else {
                if (line_length < num_length + 2) {
                    System.err.println("Can't print remaining numbers -- Insufficient line length.");
                    return;
                }
                System.out.printf("\n[%d]", i);
                current_line_length = line_length - (num_length + 2);
            }
        }
    }

    boolean isPrefix(String str1, String str2) {
        if (str1.length() > str2.length()) {
            return false;
        }

        for (int i = 0; i < str1.length(); ++i) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    boolean isPrefix_stream_version(String str1, String str2) {
        if (str1.length() > str2.length()) {
            return false;
        }

//        return IntStream.range(0, str1.length())
//                .allMatch(i -> str1.charAt(i) == str2.charAt(i));

        return IntStream.range(0, str1.length())
                .map(i -> str1.charAt(i) - str2.charAt(i))
                .allMatch(i -> i == 0);
    }

    double sum(double[][] matrix) {
        return Arrays.stream(matrix)
                .map(i -> Arrays.stream(i).sum())
                .reduce((i, j) -> i+j)
                .orElse(0d);

//        sum = Arrays.stream(matrix)
//                .map(i -> Arrays.stream(i).sum())
//                .reduce(Double::sum)
//                .get();

//        for (int i = 0; i < matrix.length; ++i) {
//            sum += Arrays.stream(matrix[i])
//                    .sum();
//        }
    }

    double average(double[][] matrix) {
        return sum(matrix) / (matrix.length * matrix[0].length);
    }

}

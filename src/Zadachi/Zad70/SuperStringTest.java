package Zadachi.Zad70;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class SuperString{
    LinkedList<String> strings;
    LinkedList<String> inOrderAddedStrings;

    public SuperString() {
        this.strings = new LinkedList<>();
        this.inOrderAddedStrings = new LinkedList<>();
    }

    public void append(String s){
        strings.add(s);
        inOrderAddedStrings.add(0, s);
    }

    public void insert(String s){
        strings.add(0, s);
        inOrderAddedStrings.add(0, s);
    }

    public boolean contains(String s){
        return strings.stream().collect(Collectors.joining("")).contains(s);
    }

    public void reverse(){
        LinkedList<String> reversedSubstrings = strings.stream()
                .map(string -> reverseString(string))
                .collect(Collectors.toCollection(LinkedList::new));
        int n = reversedSubstrings.size();
        LinkedList<String> result = new LinkedList<>();
        IntStream.range(0, n).forEach(i -> {
            result.add(reversedSubstrings.get(n - 1 - i));
        });
        strings = result;
    }

    private String reverseString(String s){
//        StringBuilder sb = new StringBuilder();
//        int n = s.length();
//        for(int i = 0; i < n; i++){
//            sb.append(s.charAt(n - 1 - i));
//        }
//        return sb.toString();
        StringBuilder sb = new StringBuilder(s);
        sb.reverse();
        return sb.toString();
    }

    @Override
    public String toString() {
        return strings.stream().collect(Collectors.joining(""));
    }

    public void removeLast(int k){
        for(int i = 0; i < k; i++){
            String stringToRemove = inOrderAddedStrings.remove(0);
            if(strings.contains(stringToRemove))
                strings.remove(stringToRemove);
            else
                strings.remove(reverseString(stringToRemove));
        }
    }
}

public class SuperStringTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (  k == 0 ) {
            SuperString s = new SuperString();
            while ( true ) {
                int command = jin.nextInt();
                if ( command == 0 ) {//append(String s)
                    s.append(jin.next());
                }
                if ( command == 1 ) {//insert(String s)
                    s.insert(jin.next());
                }
                if ( command == 2 ) {//contains(String s)
                    System.out.println(s.contains(jin.next()));
                }
                if ( command == 3 ) {//reverse()
                    s.reverse();
                }
                if ( command == 4 ) {//toString()
                    System.out.println(s);
                }
                if ( command == 5 ) {//removeLast(int k)
                    s.removeLast(jin.nextInt());
                }
                if ( command == 6 ) {//end
                    break;
                }
            }
        }
    }

}
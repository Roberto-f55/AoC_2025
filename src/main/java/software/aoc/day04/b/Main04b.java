package software.aoc.day04.b;

import software.aoc.Reader;
import java.util.List;

public class Main04b {
    public static void main(String[] args) {
        List<String> aocMatrix = Reader.lines("day04/input.txt");
        System.out.println(Paper.create().add(aocMatrix.toArray(new String[0])).maxNumberOfPapers());
    }
}

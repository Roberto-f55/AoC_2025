package software.aoc.day04.a;

import software.aoc.Reader;
import software.aoc.day04.PapersManager;

import java.util.List;

public class Main04a {
    public static void main(String[] args) {
        List<String> aocMatrix = Reader.lines("day04/input.txt");
        System.out.println(PapersManager.create().add(aocMatrix.toArray(new String[0])).numberOfPapers());
    }
}

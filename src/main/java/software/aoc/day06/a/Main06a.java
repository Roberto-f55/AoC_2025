package software.aoc.day06.a;

import software.aoc.Reader;
import software.aoc.day05.a.Inventory;

import java.util.List;

public class Main06a {
    public static void main(String[] args) {
        List<String> aocProblem = Reader.lines("day06/input.txt");
        System.out.println(Problem.create().with(aocProblem).solution());
    }
}

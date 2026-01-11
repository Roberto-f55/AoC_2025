package software.aoc.day09.b;

import software.aoc.Reader;
import software.aoc.day09.RectangleSolver;

import java.util.List;

public class Main09b {
    public static void main(String[] args) {
        List<String> input = Reader.lines("day09/input.txt");
        System.out.println(RectangleSolver.create(true).with(input).solve());

    }
}
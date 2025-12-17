package software.aoc.day02.a;

import software.aoc.Reader;
import software.aoc.day02.Expert;

import java.util.List;

public class Main02a {
    public static void main(String[] args) {
        List<String> aocIds = Reader.lines("day02/input.txt");
        System.out.println(Expert.create().add(aocIds).sumA());
    }
}

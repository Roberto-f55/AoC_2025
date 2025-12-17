package software.aoc.day07.a;

import software.aoc.Reader;
import software.aoc.day06.a.Problem;

import java.util.List;

public class Main07a {
    public static void main(String[] args) {
        List<String> treeStructure = Reader.lines("day07/input.txt");
        System.out.println(TachyonTree.create().with(treeStructure).countTachyons());
    }
}

package software.aoc.day07.b;

import software.aoc.Reader;
import java.util.List;

public class Main07b {
    public static void main(String[] args) {
        List<String> treeStructure = Reader.lines("day07/input.txt");
        System.out.println(TachyonTree.create().with(treeStructure).countTachyonsB());
    }
}

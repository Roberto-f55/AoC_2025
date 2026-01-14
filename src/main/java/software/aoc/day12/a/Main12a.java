package software.aoc.day12.a;

import software.aoc.Reader;
import software.aoc.day12.TreeFarmSolver;

import java.util.List;

public class Main12a {
    public static void main(String[] args) {
        List<String> aLotOfPresents = Reader.lines("day12/input.txt");
        TreeFarmSolver treeFarmSolver = TreeFarmSolver.create().with(aLotOfPresents);
        System.out.println(treeFarmSolver.solve());
    }
}

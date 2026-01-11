package software.aoc.day08.a;

import software.aoc.Reader;
import software.aoc.day08.CircuitManager;

import java.util.List;

public class Main08a {
    public static void main(String[] args) {
        List<String> aLotOfBoxes = Reader.lines("day08/input.txt");
        System.out.println(CircuitManager.create().with(aLotOfBoxes).connectClosestPairs(1000).countBiggest3Circuits());
    }
}

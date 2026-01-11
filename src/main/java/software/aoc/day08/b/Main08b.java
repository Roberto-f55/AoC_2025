package software.aoc.day08.b;

import software.aoc.Reader;
import software.aoc.day08.CircuitManager;

import java.util.List;

public class Main08b {
    public static void main(String[] args) {
        List<String> aLotOfBoxes = Reader.lines("day08/input.txt");
        System.out.println(CircuitManager.create().with(aLotOfBoxes).connectClosestPairs((int) Double.POSITIVE_INFINITY).lastPairDistance());
    }
}

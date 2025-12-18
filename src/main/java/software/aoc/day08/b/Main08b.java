package software.aoc.day08.b;

import software.aoc.Reader;
import java.util.List;

public class Main08b {
    public static void main(String[] args) {
        List<String> aLotOfBoxes = Reader.lines("day08/input.txt");
        System.out.println(CircuitManager.create().with(aLotOfBoxes).divideBoxesV2((int) Double.POSITIVE_INFINITY).lastPairDistance());
    }
}

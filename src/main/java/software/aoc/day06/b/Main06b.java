package software.aoc.day06.b;

import software.aoc.Reader;
import software.aoc.day06.OperationsStore;

import java.util.List;

public class Main06b {
    public static void main(String[] args) {
        List<String> tokens = Reader.lines("day06/input.txt");
        OperationsStore opStore = new OperationsBuilderB().with(tokens).build();
        System.out.println(opStore.calculate());
    }
}

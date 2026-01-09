package software.aoc.day10.a;

import software.aoc.Reader;
import software.aoc.day10.MachineManager;

import java.util.List;

public class Main10a {
    public static void main(String[] args) {
        List<String> machines = Reader.lines("day10/input.txt");
        System.out.println(MachineManager.create().with(machines).solveA());
    }
}

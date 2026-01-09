package software.aoc.day10.b;

import software.aoc.Reader;
import software.aoc.day10.MachineManager;
import java.util.List;

public class Main10b {
    public static void main(String[] args) {
        List<String> machines = Reader.lines("day10/input.txt");
        System.out.println(MachineManager.create().with(machines).solveB());
    }
}

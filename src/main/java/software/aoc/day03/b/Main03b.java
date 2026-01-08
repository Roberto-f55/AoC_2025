package software.aoc.day03.b;

import software.aoc.Reader;
import java.util.List;

public class Main03b {
    public static void main(String[] args) {
        List<String> aocBatteries = Reader.lines("day03/input.txt");
        System.out.println(Voltage.create().batteries(aocBatteries.toArray(new String[0])).sum(12));
    }
}

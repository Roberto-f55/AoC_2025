package software.aoc.day03.a;

import software.aoc.Reader;
import software.aoc.day03.Voltage;

import java.util.List;

public class Main03a {
    public static void main(String[] args) {
        List<String> aocBatteries = Reader.lines("day03/input.txt");
        System.out.println(Voltage.create().batteries(aocBatteries.toArray(new String[0])).sum(2));
    }
}

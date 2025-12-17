package software.aoc.day01.b;

import software.aoc.Reader;
import java.util.List;

public class Main01b {
    public static void main(String[] args) {
        List<String> lines = Reader.lines("day01/input.txt");
        System.out.println(Dial.create().add(lines.toArray(new String[0])).countB());
    }
}

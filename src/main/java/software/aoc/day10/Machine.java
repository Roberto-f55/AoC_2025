package software.aoc.day10;

import java.util.List;

public record Machine(
        Indicator targetLights,
        long[] targetVoltage,
        List<Button> buttons) {

    public static Machine from(String line) {
        return MachineParser.parse(line);
    }
}
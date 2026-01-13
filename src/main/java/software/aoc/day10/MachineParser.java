package software.aoc.day10;

import java.util.Arrays;
import java.util.List;

public class MachineParser {

    public static Machine parse(String line) {
        String[] partes = Arrays.stream(line.split(" "))
                .filter(s -> !s.isBlank())
                .toArray(String[]::new);

        Indicator targetLights = Indicator.targetFrom(partes[0]);

        List<Button> buttons = Arrays.stream(partes)
                .filter(p -> p.startsWith("("))
                .map(Button::from)
                .toList();

        long[] targetVoltage = parseVoltage(partes[partes.length - 1]);

        return new Machine(targetLights, targetVoltage, buttons);
    }

    private static long[] parseVoltage(String rawJoltage) {
        String cleanJoltage = rawJoltage.substring(1, rawJoltage.length() - 1);
        if (cleanJoltage.isBlank()) {
            return new long[0];
        }
        String[] numParts = cleanJoltage.split(",");
        long[] targetVoltage = new long[numParts.length];
        for (int i = 0; i < numParts.length; i++) {
            targetVoltage[i] = Long.parseLong(numParts[i].trim());
        }
        return targetVoltage;
    }
}
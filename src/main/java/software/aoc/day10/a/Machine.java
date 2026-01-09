package software.aoc.day10.a;

import software.aoc.day10.Button;
import software.aoc.day10.Indicator;

import java.util.Arrays;
import java.util.List;

public class Machine {
    private final Indicator target;
    private final List<Button> buttons;

    public Machine(String line) {
        String[] partes = line.split(" ");
        this.target = Indicator.targetFrom(partes[0]);

        this.buttons = Arrays.stream(partes)
                .filter(part -> part.startsWith("("))
                .map(Button::from)
                .toList();
    }

    public int solve() {
        int result = solveRecursive(0, Indicator.initial());
        return result > 1000 ? -1 : result;
    }

    private int solveRecursive(int buttonIndex, Indicator currentLights) {
        if (buttonIndex == buttons.size()) {
            return currentLights.matches(target) ? 0 : 99999;
        }

        Button currentButton = buttons.get(buttonIndex);

        //RAMA A
        int costWithout = solveRecursive(buttonIndex + 1, currentLights);

        //RAMA B
        Indicator newLights = currentLights.toggle(currentButton);
        int costWith = 1 + solveRecursive(buttonIndex + 1, newLights);

        return Math.min(costWithout, costWith);
    }
}
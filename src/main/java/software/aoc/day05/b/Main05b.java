package software.aoc.day05.b;

import software.aoc.Reader;
import java.util.List;

public class Main05b {
    public static void main(String[] args) {
        List<String> aocInventory = Reader.lines("day05/input.txt");
        System.out.println(Inventory.create().add(aocInventory).countAllFreshIngredients());
    }
}

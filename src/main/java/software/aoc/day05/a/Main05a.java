package software.aoc.day05.a;

import software.aoc.Reader;
import java.util.List;

public class Main05a {
    public static void main(String[] args) {
        List<String> aocInventory = Reader.lines("day05/input.txt");
        System.out.println(Inventory.create().add(aocInventory).countFreshIngredients());
    }
}

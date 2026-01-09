package software.aoc.day05.b;

import software.aoc.Reader;
import software.aoc.day05.Inventory;
import software.aoc.day05.InventoryBuilder;

import java.util.List;

public class Main05b {
    public static void main(String[] args) {
        List<String> aocInventory = Reader.lines("day05/input.txt");
        Inventory inventory = InventoryBuilder.createFrom(aocInventory);
        System.out.println(inventory.countAllFreshIngredients());
    }
}

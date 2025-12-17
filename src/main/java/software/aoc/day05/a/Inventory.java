package software.aoc.day05.a;

import java.util.*;

public class Inventory {
    private List<Ranges> freshId;
    private List<String> allIngredients;
    private Set<String> allFreshIngredients;

    public static Inventory create() {
        return new Inventory();
    }

    private Inventory() {
        freshId = new ArrayList<>();
        allIngredients = new ArrayList<>();
    }

    public Inventory add(String ingredients) {
        System.out.println(ingredients);
        addRangesOrIngredients(ingredients.split("\n\n"));
        return this;
    }

    public Inventory add(List<String> list) {
        addRangesOrIngredients(separator(list));
        return this;
    }

    private String[] separator(List<String> list) {
        return (new String[] {part1(list), part2(list)});
    }

    private String part1(List<String> list) {
        return String.join("\n", list.subList(0, list.indexOf("")));
    }

    private String part2(List<String> list) {
        return String.join("\n", list.subList(list.indexOf("") + 1, list.size()));
    }

    private void addRangesOrIngredients(String[] rangesAndIngredients) {
        addRanges(rangesAndIngredients[0].split("\n")); addIngredients(rangesAndIngredients[1].split("\n"));
    }

    private void addRanges(String[] ranges) {
        Arrays.stream(ranges)
                .forEach(this::addRanges);
    }

    private void addRanges(String range) {
        freshId.add(Ranges.with(limiteInferior(range), limiteSuperior(range)));
    }

    private long limiteInferior(String range) {
        return Long.parseLong(range.split("-")[0]);
    }

    private long limiteSuperior(String range) {
        return Long.parseLong(range.split("-")[1]);
    }

    private void addIngredients(String[] ingredients) {
        Arrays.stream(ingredients)
                .forEach(allIngredients::add);
    }

    public int countFreshIngredients() {
        return (int) allIngredients.stream()
                    .filter(this::isFresh)
                    .count();
    }

    private boolean isFresh(String ingredient) {
        return freshId.stream().anyMatch(r -> r.contains(Long.parseLong(ingredient)));
    }
}

package software.aoc.day05;

import java.util.*;

public class Inventory {
    private final List<Ranges> freshRanges;
    private final List<Long> availableIngredients;

    Inventory(List<Ranges> freshRanges, List<Long> availableIngredients) {
        this.freshRanges = freshRanges;
        this.availableIngredients = availableIngredients;
    }

    public int countFreshIngredients() {
        return (int) availableIngredients.stream()
                .filter(this::isFresh)
                .count();
    }

    private boolean isFresh(Long ingredientId) {
        return freshRanges.stream().anyMatch(r -> r.contains(ingredientId));
    }

    public long countAllFreshIngredients() {
        if (freshRanges.isEmpty()) return 0;
        return fusionAllRanges(sortId());
    }

    private long fusionAllRanges(List<Ranges> sorted) {
        long total = 0;
        long currentLow = sorted.get(0).low();
        long currentHigh = sorted.get(0).high();

        for (int i = 1; i < sorted.size(); i++) {
            Ranges r = sorted.get(i);
            if (r.low() <= currentHigh + 1) {
                currentHigh = Math.max(currentHigh, r.high());
            } else {
                total += lengthOf(currentHigh, currentLow);
                currentLow = r.low();
                currentHigh = r.high();
            }
        }
        total += lengthOf(currentHigh, currentLow);
        return total;
    }

    private long lengthOf(long high, long low) {
        return high - low + 1;
    }

    private List<Ranges> sortId() {
        return freshRanges.stream()
                .sorted(Comparator.comparingLong(Ranges::low))
                .toList();
    }
}
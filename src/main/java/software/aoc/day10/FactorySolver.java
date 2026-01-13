package software.aoc.day10;

import org.jetbrains.annotations.Nullable;

import java.util.*;

public class FactorySolver {
    private final Machine machine;
    private static final long MAX_COST = Long.MAX_VALUE / 2;

    public FactorySolver(Machine machine) {
        this.machine = machine;
    }

    public int solveA() {
        List<Long> solutions = findAllLightSolutions(machine.targetLights().state());
        if (solutions.isEmpty()) return 0;

        return solutions.stream()
                .mapToInt(Long::bitCount)
                .min()
                .orElse(0);
    }

    public long solveB() {
        Map<String, Long> cache = new HashMap<>();
        long res = recursiveVoltage(machine.targetVoltage(), cache);
        return res >= MAX_COST ? 0 : res;
    }

    private long recursiveVoltage(long[] currentVoltages, Map<String, Long> cache) {
        Integer rest = theRestIsCero(currentVoltages);
        if (rest != null) return rest;

        String key = Arrays.toString(currentVoltages);
        if (cache.containsKey(key)) return cache.get(key);

        long parityMask = findAllPairParity(currentVoltages);
        List<Long> paritySolutions = findAllLightSolutions(parityMask);

        long minCost = getMinCost(currentVoltages, cache, paritySolutions);

        cache.put(key, minCost);
        return minCost;
    }

    private long getMinCost(long[] currentVoltages, Map<String, Long> cache, List<Long> paritySolutions) {
        long minCost = MAX_COST;

        for (long buttonComboMask : paritySolutions) {
            long[] nextTarget = applyAndDivide(currentVoltages, buttonComboMask);

            if (nextTarget != null) {
                long costRecursive = recursiveVoltage(nextTarget, cache);

                if (costRecursive < MAX_COST) {
                    long currentButtonsCount = Long.bitCount(buttonComboMask);
                    long totalCost = currentButtonsCount + (2 * costRecursive);
                    minCost = Math.min(minCost, totalCost);
                }
            }
        }
        return minCost;
    }

    private long[] applyAndDivide(long[] current, long buttonComboMask) {
        long[] next = new long[current.length];
        for (int currentVIndex = 0; currentVIndex < current.length; currentVIndex++) {
            int voltageDrop = 0;
            List<Button> buttons = machine.buttons();

            for (int buttonIndex = 0; buttonIndex < buttons.size(); buttonIndex++) {
                if ((buttonComboMask & (1L << buttonIndex)) != 0 &&
                        (buttons.get(buttonIndex).mask() & (1L << currentVIndex)) != 0) {
                    voltageDrop++;
                }
            }

            long reduced = current[currentVIndex] - voltageDrop;
            if (reduced < 0) return null;
            next[currentVIndex] = reduced / 2;
        }
        return next;
    }

    private List<Long> findAllLightSolutions(long targetState) {
        List<Long> solutions = new ArrayList<>();
        int nButtons = machine.buttons().size();
        long limit = 1L << nButtons; //PE: 1 << 3 = 1000

        for (long mask = 0; mask < limit; mask++) {
            if (calculateLights(mask) == targetState) {
                solutions.add(mask);
            }
        }
        return solutions;
    }

    private long calculateLights(long buttonsCombination) {
        long lights = 0L;
        List<Button> buttons = machine.buttons();
        for (int buttonIndex = 0; buttonIndex < buttons.size(); buttonIndex++) {
            if ((buttonsCombination & (1L << buttonIndex)) != 0) {
                lights ^= buttons.get(buttonIndex).mask();
            }
        }
        return lights;
    }

    @Nullable
    private static Integer theRestIsCero(long[] currentVoltages) {
        boolean allZero = true;
        for (long v : currentVoltages) if (v != 0) { allZero = false; break; }
        return (allZero) ? 0 : null;
    }

    private static long findAllPairParity(long[] currentVoltages) {
        long parityMask = 0L;
        for (int i = 0; i < currentVoltages.length; i++) {
            if (currentVoltages[i] % 2 != 0) parityMask |= (1L << i);
        }
        return parityMask;
    }
}
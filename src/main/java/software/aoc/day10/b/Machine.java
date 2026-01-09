package software.aoc.day10.b;

import software.aoc.day10.Button;
import software.aoc.day10.Indicator;

import java.util.*;

public class Machine {
    private final Indicator targetLights;

    private final long[] targetVoltage;
    private final List<Button> buttons;

    private static final long MAX_COST = Long.MAX_VALUE / 2;

    public Machine(String line) {
        String[] partes = Arrays.stream(line.split(" "))
                .filter(s -> !s.isBlank())
                .toArray(String[]::new);

        this.targetLights = Indicator.targetFrom(partes[0]);
        this.buttons = Arrays.stream(partes)
                .filter(p -> p.startsWith("("))
                .map(Button::from)
                .toList();

        String rawJoltage = partes[partes.length - 1];
        String cleanJoltage = rawJoltage.substring(1, rawJoltage.length() - 1);
        if (cleanJoltage.isBlank()) {
            this.targetVoltage = new long[0];
        } else {
            String[] numParts = cleanJoltage.split(",");
            this.targetVoltage = new long[numParts.length];
            for (int i = 0; i < numParts.length; i++) {

                this.targetVoltage[i] = Long.parseLong(numParts[i].trim());
            }
        }
    }

    public int solveA() {
        // Para la parte A, seguimos queriendo la solución más corta, ordenamos por bitCount
        List<Long> solutions = findAllLightSolutions(targetLights.state());
        if (solutions.isEmpty()) return 0;

        return solutions.stream()
                .mapToInt(Long::bitCount)
                .min()
                .orElse(0);
    }

    public long solveB() {
        Map<String, Long> cache = new HashMap<>();
        long res = recursiveVoltage(this.targetVoltage, cache);
        return res >= MAX_COST ? 0 : res;
    }

    private long recursiveVoltage(long[] currentVoltages, Map<String, Long> cache) {
        // 1. Check Ceros
        boolean allZero = true;
        for (long v : currentVoltages) if (v != 0) { allZero = false; break; }
        if (allZero) return 0;

        // 2. Cache
        String key = Arrays.toString(currentVoltages);
        if (cache.containsKey(key)) return cache.get(key);

        // 3. Paridad
        long parityMask = 0L;
        for (int i = 0; i < currentVoltages.length; i++) {
            if (currentVoltages[i] % 2 != 0) parityMask |= (1L << i);
        }

        // 4. Buscar TODAS las combinaciones de botones que cumplen la paridad
        // (No solo las mínimas, porque una combinación más larga podría ser necesaria para restar voltaje)
        List<Long> paritySolutions = findAllLightSolutions(parityMask);

        long minCost = MAX_COST;

        // 5. Iterar soluciones
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

        cache.put(key, minCost);
        return minCost;
    }

    // Dado que el número de botones suele ser pequeño (<20), es seguro iterar todas las máscaras.
    // Esto garantiza que no descartamos una solución "cara" en botones pero necesaria para el voltaje.
    private List<Long> findAllLightSolutions(long targetState) {
        List<Long> solutions = new ArrayList<>();
        int nButtons = buttons.size();
        // Límite de seguridad: si hay más de 20 botones, 2^20 es 1 millón, aceptable.
        // Si hay muchísimos más, habría que usar Gaussian Elimination, pero para AoC esto suele bastar.
        long limit = 1L << nButtons;

        for (long mask = 0; mask < limit; mask++) {
            if (calculateLights(mask) == targetState) {
                solutions.add(mask);
            }
        }
        return solutions;
    }

    private long calculateLights(long buttonsMask) {
        long lights = 0L;
        for (int i = 0; i < buttons.size(); i++) {
            if ((buttonsMask & (1L << i)) != 0) {
                lights ^= buttons.get(i).mask();
            }
        }
        return lights;
    }

    // Adaptado para long[]
    private long[] applyAndDivide(long[] current, long buttonComboMask) {
        long[] next = new long[current.length];
        for (int i = 0; i < current.length; i++) {
            int impact = 0;
            for (int b = 0; b < buttons.size(); b++) {
                if ((buttonComboMask & (1L << b)) != 0 && (buttons.get(b).mask() & (1L << i)) != 0) {
                    impact++;
                }
            }

            long reduced = current[i] - impact;
            if (reduced < 0) return null; // Invalid path
            next[i] = reduced / 2;
        }
        return next;
    }
}
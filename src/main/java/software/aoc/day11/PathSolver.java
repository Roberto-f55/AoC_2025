package software.aoc.day11;

import java.util.*;

public class PathSolver {
    private final Map<String, List<String>> devices;
    private final Map<State, Long> memo;

    public PathSolver(Map<String, List<String>> devices) {
        this.devices = devices;
        this.memo = new HashMap<>();
    }

    public long solve(String startNode, boolean initDac, boolean initFft) {
        return dfs(startNode, initDac, initFft);
    }

    private long dfs(String node, boolean foundDac, boolean foundFft) {

        if (node.equals("dac")) foundDac = true;
        if (node.equals("fft")) foundFft = true;

        if (node.equals("out")) {
            return (foundDac && foundFft) ? 1L : 0L;
        }

        State currentState = new State(node, foundDac, foundFft);
        if (memo.containsKey(currentState)) {
            return memo.get(currentState);
        }

        long count = 0L;
        for (String next : devices.getOrDefault(node, Collections.emptyList())) {
            count += dfs(next, foundDac, foundFft);
        }

        memo.put(currentState, count);
        return count;
    }

    private record State(String node, boolean hasDac, boolean hasFft) {}
}
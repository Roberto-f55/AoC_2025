package software.aoc.day06;

import java.util.List;

public record Operation(List<Long> numbers, Operator op) {
    public long calculate() {
            return numbers.stream().reduce(op.identity(), op);
        }
}


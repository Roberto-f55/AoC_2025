package software.aoc.day06.a;

import software.aoc.day06.Operation;
import software.aoc.day06.OperationsStore;
import software.aoc.day06.Operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class OperationsBuilderA {
    private final List<List<Long>> columns = new ArrayList<>();
    private final List<Operator> operators = new ArrayList<>();

    public OperationsBuilderA with(List<String> lines) {
        lines.stream()
                .forEach(l -> add(l));
        return this;
    }

    public OperationsBuilderA add(String line) {
        if (line.isBlank()) return this;

        String[] parts = Arrays.stream(line.split("\\s+"))
                .filter(s -> !s.isBlank())
                .toArray(String[]::new);

        if (parts.length == 0) return this;

        if (Operator.isSymbol(parts[0])) {
            parseOperators(parts);
        } else {
            parseNumbers(parts);
        }

        return this;
    }

    private void parseNumbers(String[] tokens) {
        if (columns.isEmpty()) {
            IntStream.range(0, tokens.length)
                    .<List<Long>>mapToObj(i -> new ArrayList<>())
                    .forEach(columns::add);
        }

        for (int i = 0; i < tokens.length; i++) {
            columns.get(i).add(Long.parseLong(tokens[i]));
        }
    }

    private void parseOperators(String[] tokens) {
        Arrays.stream(tokens)
                .map(Operator::from)
                .forEach(operators::add);
    }

    public OperationsStore build() {
        return new OperationsStore(IntStream.range(0, columns.size())
                .mapToObj(i -> new Operation(
                        List.copyOf(columns.get(i)),
                        operators.get(i))));
    }
}

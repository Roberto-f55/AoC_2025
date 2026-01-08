package software.aoc.day06.b;

import software.aoc.day06.Operation;
import software.aoc.day06.OperationsStore;
import software.aoc.day06.Operator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class OperationsBuilderB {
    private final List<String> rawLines = new ArrayList<>();
    private String operatorLine;
    private int maxLength = 0;

    public OperationsBuilderB with(List<String> lines) {
        lines.forEach(this::add);
        return this;
    }

    public OperationsBuilderB add(String line) {
        if (line == null || line.isBlank()) return this;

        if (Operator.isSymbol(line.substring(0, 1))) {
            parseOperators(line);
        } else {
            parseNumbers(line);
        }
        return this;
    }

    private void parseNumbers(String line) {
        rawLines.add(line);
        if (line.length() > maxLength) {
            maxLength = line.length();
        }
    }

    private void parseOperators(String line) {
        this.operatorLine = line;
    }

    public OperationsStore build() {
        List<String> paddedLines = rawLines.stream()
                .map(this::fill)
                .toList();

        List<Integer> opIndices = getOperatorIndices();
        List<Operation> ops = new ArrayList<>();

        for (int i = 0; i < opIndices.size(); i++) {
            int start = opIndices.get(i);
            int end = (i + 1 < opIndices.size()) ? opIndices.get(i + 1) - 1 : maxLength;

            Operator op = Operator.from(String.valueOf(operatorLine.charAt(start)));

            ops.add(createOperations(paddedLines, start, end, op));
        }

        return new OperationsStore(ops.stream());
    }

    private Operation createOperations(List<String> rows, int startCol, int endCol, Operator operator) {
        List<Long> operands = new ArrayList<>();

        for (int i = startCol; i < endCol; i++) {
            Long val = createOperation(rows, i);
            operands.add(val);
        }

        return new Operation(operands, operator);
    }

    private Long createOperation(List<String> rows, int colIndex) {
        String extractedNumber = "0";

        for (String row : rows) {
            extractedNumber = extractedNumber
                    .concat(String.valueOf(row.charAt(colIndex)))
                    .strip();
        }

        if (extractedNumber.isEmpty()) return 0L;

        return Long.parseLong(extractedNumber);
    }

    private String fill(String str) {
        return str.length() >= maxLength ? str : str.concat(" ".repeat(maxLength - str.length()));
    }

    private List<Integer> getOperatorIndices() {
        return IntStream.range(0, operatorLine.length())
                .filter(i -> Operator.isSymbol(operatorLine.substring(i, i + 1)))
                .boxed()
                .toList();
    }
}
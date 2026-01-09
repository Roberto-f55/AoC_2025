package software.aoc.day06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class OperationsBuilder {
    private final List<String> rawLines = new ArrayList<>();
    private String operatorLine;
    private int maxLength = 0;
    private final boolean rightToLeft;

    private OperationsBuilder(boolean rightToLeft) {
        this.rightToLeft = rightToLeft;
    }

    public static OperationsBuilder createA() {
        return new OperationsBuilder(false);
    }

    public static OperationsBuilder createB() {
        return new OperationsBuilder(true);
    }

    public OperationsBuilder with(List<String> lines) {
        lines.forEach(this::add);
        return this;
    }

    public OperationsBuilder with(String lines) {
        Arrays.stream(lines.split("\n")).forEach(this::add);
        return this;
    }

    public OperationsBuilder add(String line) {
        if (line == null || line.isBlank()) return this;
        if (Operator.isSymbol(line.substring(0, 1)) || Operator.isSymbol(line.trim())) {
            this.operatorLine = line;
        } else {
            rawLines.add(line);
            maxLength = Math.max(maxLength, line.length());
        }
        return this;
    }

    public OperationStore build() {
        List<String> paddedLines = rawLines.stream()
                .map(this::fill)
                .toList();

        List<Integer> opIndices = getOperatorIndices();
        List<Operation> ops = new ArrayList<>();

        for (int i = 0; i < opIndices.size(); i++) {
            int start = opIndices.get(i);
            int end = (i + 1 < opIndices.size()) ? opIndices.get(i + 1) - 1 : maxLength;

            Operator op = Operator.from(String.valueOf(operatorLine.charAt(start)));
            ops.add(createOperation(paddedLines, start, end, op));
        }
        return new OperationStore(ops.stream());
    }

    private Operation createOperation(List<String> rows, int startCol, int endCol, Operator operator) {
        List<Long> operands = new ArrayList<>();
        int startRow = 0;
        int endRow = rows.size();

        if (rightToLeft) {
            for (int i = endCol; i >= startCol; i--) {
                Long val = createOperationWithColumn(rows, i);
                if (val != 0) operands.add(val);
            }
        } else {
            for (int i = startRow; i < endRow; i++) {
                Long val = createOperationWithRow(rows, i, startCol, endCol);
                if (val != 0) operands.add(val);
            }
        }

        return new Operation(operands, operator);
    }

    private Long createOperationWithRow(List<String> rows, int i, int starCol, int endCol) {
        String currentRow = rows.get(i);
        return Long.parseLong(currentRow.substring(starCol, endCol).trim());
    }

    private Long createOperationWithColumn(List<String> rows, int colIndex) {
        String extractedNumber = "0";
        for (String row : rows) {
            if (colIndex < row.length()) {
                extractedNumber = extractedNumber
                        .concat(String.valueOf(row.charAt(colIndex)))
                        .strip();
            }
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
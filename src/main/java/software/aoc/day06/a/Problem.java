package software.aoc.day06.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem {
    private final List<Operation> operations;

    public static Problem create() {
        return new Problem();
    }

    private Problem() {
        this.operations = new ArrayList<>();
    }

    public Problem with(String operations) {
        String[] rest = (first(operations.split("\n")));
        Arrays.stream(rest)
                .map(line -> eliminarVacios(line))
                .forEach(this::separate);
        return this;
    }

    public Problem with(List<String> operations) {
        return with(String.join("\n", operations));
    }

    private String[] eliminarVacios(String line) {
        return Arrays.stream(line.split("\\s+"))
                .filter(s -> !s.isBlank())
                .toArray(String[]::new);
    }

    private String[] first(String[] opers) {

        baseCreate(opers[0].split("\\s+"));
        return Arrays.copyOfRange(opers, 1, opers.length);
    }

    private void baseCreate(String[] opers) {
        System.out.println(opers.length);
        Arrays.stream(opers)
                .forEach(o -> operations.add(new Operation(o)));
    }

    private void separate(String[] numberOrOperator) {
        if (isNumber(numberOrOperator[0])) addNumber(numberOrOperator);
        else addOperator(numberOrOperator);

    }

    private boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void addNumber(String[] numbers) {
        System.out.println(numbers.length);
        int index = 0;
        for (String number : numbers) {
            operations.get(index).add(Integer.parseInt(number));
            index++;
        }
    }

    //PODRÍA LLEGAR A FALLAR//
    private void addOperator(String[] operators) {
        int index = 0;
        for (String operator : operators) {
            if (!operator.equals("")) {
                operations.get(index).setOperator(operator);
                index++;
            }
        }
    }

    public int solution() {
        return operations.stream()
                .mapToInt(o -> o.oper())
                .sum();
    }
}

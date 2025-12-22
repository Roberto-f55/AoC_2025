package software.aoc.day06.a;

import java.util.ArrayList;
import java.util.List;

class Operation {
    List<Integer> numbers;
    private String operator;

    public Operation(String o) {
        this.numbers = new ArrayList<>();
        add(Integer.parseInt(o));
    }

    void add(Integer operand) {
        this.numbers.add(operand);
    }

    void setOperator(String operator) {
        this.operator = operator;
    }

    public int oper() {
        switch (operator) {
            case "+":
                return sumAll();
            case "*":
                return mulAll();
            default:
                throw new IllegalStateException("Operador inválido: " + operator);
        }
        //return 0;
    }

    private int sumAll() {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private int mulAll() {
        return numbers.stream()
                .reduce(1, (a, b) -> a * b);
    }
}

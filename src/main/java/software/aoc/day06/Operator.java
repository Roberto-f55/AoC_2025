package software.aoc.day06;

import java.util.function.BinaryOperator;

public enum Operator implements BinaryOperator<Long> {
    ADD, MULTIPLY;

    public static boolean isSymbol(String s) {
        return s.contains("+") || s.contains("*");
    }

    public static Operator from(String str) {
        return str.contains("+") ? ADD : MULTIPLY;
    }

    public long identity() {
        return this == ADD ? 0 : 1;
    }

    @Override
    public Long apply(Long a, Long b) {
        return this == ADD ? a + b : a * b;
    }
}

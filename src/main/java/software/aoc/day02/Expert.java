package software.aoc.day02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Expert {
    private final List<String> ranges;

    public static Expert create() {
        return new Expert();
    }

    private Expert() {
        this.ranges = new ArrayList();
    }

    public Expert execute(String orders){
        add(orders.split("\n"));
        return this;
    }

    public Expert add(String... cadena) {
        Arrays.stream(cadena)
                .forEach(this::add);
        return this;
    }

    public Expert add(List<String> cadena) {
        add(cadena.get(0).split(","));
        return this;
    }

    private void add(String cadena) {
        ranges.add(cadena);
    }

    public long sumA() {
        return calculateSum(this::isValidPartA);
    }

    public long sumB() {
        return calculateSum(this::isValidPartB);
    }

    private long calculateSum(LongPredicate validator) {
        return ranges.stream()
                .flatMapToLong(this::parseRange)
                .filter(validator)
                .sum();
    }

    private LongStream parseRange(String range) {
        String[] parts = range.split("-");
        long start = Long.parseLong(parts[0]);
        long end = Long.parseLong(parts[1]);
        return LongStream.rangeClosed(start, end);
    }

    private boolean isValidPartA(long id) {
        String cadena = String.valueOf(id);

        return (cadena.length() % 2 == 0) ? lowerHalf(cadena).equals(upperHalf(cadena)) : false;
    }

    private boolean isValidPartB(long id) {
        String cadena = String.valueOf(id);

        return IntStream.rangeClosed(1, cadena.length() / 2)
                .filter(k -> cadena.length() % k == 0)
                .anyMatch(k ->
                        cadena.substring(0, k)
                                .repeat(cadena.length() / k)
                                .equals(cadena)
                );
    }

    private String lowerHalf(String cadena) {
        return cadena.substring(0, cadena.length() / 2);
    }

    private String upperHalf(String cadena) {
        return cadena.substring(cadena.length() / 2);
    }
}

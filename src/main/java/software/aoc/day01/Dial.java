package software.aoc.day01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Dial {
    private final List<Order> orders;

    private Dial() {
        this.orders = new ArrayList<>();
    }

    public static Dial create() {
        return new Dial();
    }

    public Dial add(String... orders) {
        Arrays.stream(orders)
                .map(this::parse)
                .forEach(this::add);
        return this;
    }

    private void add(Order order) {
        orders.add(order);
    }

    private Order parse(String order) {
        return new Order(signOf(order) * valueOf(order));
    }

    private int signOf(String order) {
        return order.charAt(0) == 'L' ? -1 : 1;
    }

    private int valueOf(String order) {
        return Integer.parseInt(order.substring(1));
    }

    public int position() {
        return normalize(sumAll());
    }

    private int sumAll() {
        return sum(orders.stream());
    }

    public int countA() {
        return (int) iterate()
                .map(this::sumPartial)
                .filter(s -> s == 0)
                .count();
    }

    public int countB() {
        int pos = 50;
        int count = 0;

        for (Order order : orders) {
            int step = order.step();
            int d = Math.abs(step);

            if (step > 0) count += (pos + d) / 100;
            else count += (pos == 0) ? (d / 100) : (d >= pos ? 1 + (d - pos) / 100 : 0);

            pos = normalize(pos + step);
        }

        return count;
    }


    private IntStream iterate() {
        return IntStream.rangeClosed(1, orders.size()).parallel();
    }

    private int sumPartial(int size) {
        return normalize(sum(orders.stream().limit(size)));
    }

    private static int sum(Stream<Order> orders) {
        return orders.mapToInt(o -> o.step()).sum() + 50;
    }

    private int normalize(int value) {
        return ((value % 100) + 100) % 100;
    }


    public Dial execute(String orders) {
        return add(orders.split("\n"));
    }
}

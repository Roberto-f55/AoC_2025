package software.aoc.day09.a;

public record CoordinateBuilder(String coordinate) {

    public static Coordinate coordinateCreate(String coordinate) {
        String[] divisor = coordinate.split(",");
        return new Coordinate(x(divisor[0]), y(divisor[1]));
    }

    private static long x(String x) {
        return Long.parseLong(x);
    }

    private static long y(String y) {
        return Long.parseLong(y);
    }
}

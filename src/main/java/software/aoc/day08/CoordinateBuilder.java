package software.aoc.day08;

public record CoordinateBuilder(String coordinate) {

    Coordinate coordinateCreate() {
        String[] divisor = divide();
        return new Coordinate(x(divisor), y(divisor), z(divisor));
    }

    private double x(String[] divisor) {
        return Double.parseDouble(divisor[0]);
    }

    private double y(String[] divisor) {
        return Double.parseDouble(divisor[1]);
    }

    private double z(String[] divisor) {
        return Double.parseDouble(divisor[2]);
    }

    private String[] divide() {
        return coordinate.split(",");
    }
}

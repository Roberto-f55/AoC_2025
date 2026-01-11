package software.aoc.day08;

public record Coordinate(long x, long y, long z) {

    public static Coordinate from(String line) {
        String[] parts = line.split(",");
        return new Coordinate(
                Long.parseLong(parts[0]),
                Long.parseLong(parts[1]),
                Long.parseLong(parts[2])
        );
    }
}
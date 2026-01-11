package software.aoc.day09;

public record Coordinate(long x, long y) {
    public static Coordinate from(String line) {
        String[] parts = line.split(",");
        return new Coordinate(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }
}
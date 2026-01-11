package software.aoc.day08;

public record Pair(Coordinate low, Coordinate high) {

    public long distance() {
        return distance2();
    }

    private long distance2() {
        return x() + y() + z();
    }

    private long x() {
        return square(low.x() - high.x());
    }

    private long y() {
        return square(low.y() - high.y());
    }

    private long z() {
        return square(low.z() - high.z());
    }

    private long square(long v) {
        return v * v;
    }
}
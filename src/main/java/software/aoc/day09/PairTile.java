package software.aoc.day09;

public record PairTile(Coordinate first, Coordinate second) {

    public long area() {
        return width() * height();
    }

    private long width() {
        return Math.abs(first.x() - second.x()) + 1;
    }

    private long height() {
        return Math.abs(first.y() - second.y()) + 1;
    }
}
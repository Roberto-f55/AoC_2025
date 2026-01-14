package software.aoc.day12;

public record Point2D(int x, int y) {
    public Point2D offsetBy(Point2D other) {
        return new Point2D(this.x + other.x, this.y + other.y);
    }
}
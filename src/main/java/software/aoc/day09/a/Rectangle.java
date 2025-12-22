package software.aoc.day09.a;

public class Rectangle {
    private final PairTile pair;
    private final long area;

    public Rectangle(PairTile pair, long area) {
        this.pair = pair;
        this.area = area;
    }

    public long getArea() {
        return area;
    }
}

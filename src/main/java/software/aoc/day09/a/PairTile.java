package software.aoc.day09.a;

record PairTile(RedTile first, RedTile second) {

    public long area() {
        return x() * y();
    }

    private long x() {
        return Math.abs(first.coordinate.x() - second.coordinate.x()) + 1;
    }

    private long y() {
        return Math.abs(first.coordinate.y() - second.coordinate.y()) + 1;
    }
}

package software.aoc.day04;

record Position(int row, int column) {

    static Position with(int x, int y) {
        return new Position(x, y);
    }
}

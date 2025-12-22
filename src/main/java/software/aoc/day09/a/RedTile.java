package software.aoc.day09.a;

class RedTile {
    final Coordinate coordinate;

    public static RedTile create(String coordinate) {
        return new RedTile(coordinate);
    }

    private RedTile(String coordinate) {
        this.coordinate = createCoordinate(coordinate);
    }

    private Coordinate createCoordinate(String coordinate) {
        return CoordinateBuilder.coordinateCreate(coordinate);
    }
}

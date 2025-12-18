package software.aoc.day08;

public class BoxConnection {

    private final Coordinate coordinate;

    private BoxConnection(String coor) {
        this.coordinate = new CoordinateBuilder(coor).coordinateCreate();
    }

    public Coordinate getCoordinate() {return coordinate;}

    public static BoxConnection in(String coor) {
        return new BoxConnection(coor);
    }
}

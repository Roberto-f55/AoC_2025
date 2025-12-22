package software.aoc.day09.a;

import software.aoc.Reader;

import java.util.List;

public class Main09a {
    public static void main(String[] args) {
        List<String> aLotOfRedTiles = Reader.lines("day09/input.txt");
        Rectangle rectangle = RectangleBuilder.create().with(aLotOfRedTiles.toArray(new String[0])).buildRectangle();
        System.out.println(rectangle.getArea());
    }
}

package software.aoc.day09.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class RectangleBuilder {
    List<RedTile> redtiles;
    List<PairTile> possibleRectangles;

    public static RectangleBuilder create() {
        return new RectangleBuilder();
    }

    private RectangleBuilder() {
        this.redtiles = new ArrayList<>();
        this.possibleRectangles = new ArrayList<>();
    }

    public RectangleBuilder with(String tiles) {
        add(tiles.split("\n"));
        return this;
    }

    public RectangleBuilder with(String[] tiles) {
        add(tiles);
        return this;
    }

    private void add(String[] tiles) {
        Arrays.stream(tiles)
                .forEach(t -> redtiles.add(RedTile.create(t)));
    }

    public Rectangle buildRectangle() {
        for (int frs = 0; frs < redtiles.size(); frs++) {
            for (int scd = frs + 1; scd < redtiles.size(); scd++) {
                possibleRectangles.add(new PairTile(redtiles.get(frs), redtiles.get(scd)));
            }
        }
        possibleRectangles.sort(Comparator.comparingDouble(PairTile::area).reversed());
        return new Rectangle(definitiveRectangle(0), definitiveArea(0));
    }

    private PairTile definitiveRectangle(int index) {
        return possibleRectangles.get(index);
    }

    private long definitiveArea(int index) {
        return possibleRectangles.get(index).area();
    }
}

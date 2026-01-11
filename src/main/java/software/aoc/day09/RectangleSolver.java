package software.aoc.day09;

import java.util.*;

public class RectangleSolver {
    private final List<Coordinate> tiles;
    private final boolean enforcePolygonRules;

    public static RectangleSolver create(boolean enforceRules) {
        return new RectangleSolver(enforceRules);
    }

    private RectangleSolver(boolean enforcePolygonRules) {
        this.tiles = new ArrayList<>();
        this.enforcePolygonRules = enforcePolygonRules;
    }

    public RectangleSolver with(List<String> tls) {
        tls.stream().map(Coordinate::from).forEach(tiles::add);
        return this;
    }

    public RectangleSolver with(String tls) {
        with(Arrays.stream(tls.split("\n")).toList());
        return this;
    }

    public long solve() {
        Polygon polygon = enforcePolygonRules ? new Polygon(tiles) : null;
        long maxArea = 0;

        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                PairTile candidate = new PairTile(tiles.get(i), tiles.get(j));
                long area = candidate.area();
                if (area <= maxArea) continue;
                if (isValid(candidate, polygon)) {
                    maxArea = area;
                }
            }
        }
        return maxArea;
    }

    private boolean isValid(PairTile candidate, Polygon polygon) {
        return (!enforcePolygonRules) ? true : polygon.contains(candidate);
    }
}
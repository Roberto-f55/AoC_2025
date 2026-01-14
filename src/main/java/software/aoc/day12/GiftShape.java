package software.aoc.day12;

import java.util.ArrayList;
import java.util.List;

public record GiftShape(List<Point2D> geometry) {

    public static GiftShape parseShape(List<String> rawLines) {
        List<Point2D> points = new ArrayList<>();
        for (int row = 1; row < rawLines.size(); row++) {
            String line = rawLines.get(row);
            for (int col = 0; col < 3; col++) {
                if (line.charAt(col) == '#') {
                    points.add(new Point2D(col - 1, row - 2));
                }
            }
        }
        return new GiftShape(points);
    }

    public int blockSize() {
        return geometry.size();
    }

    public GiftShape mirror() {
        List<Point2D> newPoints = new ArrayList<>();
        for (Point2D p : geometry) {
            newPoints.add(new Point2D(-p.x(), p.y()));
        }
        return new GiftShape(newPoints);
    }

    public GiftShape turn90() {
        List<Point2D> newPoints = new ArrayList<>();
        for (Point2D p : geometry) {
            newPoints.add(new Point2D(-p.y(), p.x()));
        }
        return new GiftShape(newPoints);
    }

    public GiftShape translateTo(Point2D vector) {
        List<Point2D> shifted = new ArrayList<>();
        for (Point2D p : geometry) {
            shifted.add(p.offsetBy(vector));
        }
        return new GiftShape(shifted);
    }
}
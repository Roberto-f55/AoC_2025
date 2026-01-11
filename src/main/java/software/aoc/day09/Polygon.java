package software.aoc.day09;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private final List<Segment> edges = new ArrayList<>();

    public Polygon(List<Coordinate> tiles) {
        for (int i = 0; i < tiles.size(); i++) {
            Coordinate start = tiles.get(i);
            Coordinate end = tiles.get((i + 1) % tiles.size());
            edges.add(new Segment(start, end));
        }
    }

    public boolean contains(PairTile rect) {
        // Coordenadas del rectángulo normalizadas
        long rMinX = Math.min(rect.first().x(), rect.second().x());
        long rMaxX = Math.max(rect.first().x(), rect.second().x());
        long rMinY = Math.min(rect.first().y(), rect.second().y());
        long rMaxY = Math.max(rect.first().y(), rect.second().y());

        // 1. COMPROBACIÓN DE INTERSECCIÓN:
        // Ningún borde del polígono debe "cortar" el rectángulo por la mitad.
        if (rectangleTouchPolygon(rMinX, rMaxX, rMinY, rMaxY)) return false; // El borde atraviesa el rectángulo

        // 2. COMPROBACIÓN DE PUNTO INTERIOR (RAY CASTING):
        // Si ningún borde lo corta, verificamos si el centro del rectángulo está dentro.
        // Usamos doubles para el punto medio para evitar problemas de redondeo
        double midX = (rMinX + rMaxX) / 2.0;
        double midY = (rMinY + rMaxY) / 2.0;

        int intersections = rectangeIsIn(midY, midX);

        // Si el número de intersecciones es impar, estamos dentro.
        return (intersections % 2 != 0);
    }

    private int rectangeIsIn(double midY, double midX) {
        int intersections = 0;
        for (Segment edge : edges) {
            if (edge.isVertical()) {
                // ¿El borde vertical cruza la altura Y de nuestro punto?
                if ((edge.start().y() > midY) != (edge.end().y() > midY)) {
                    // ¿Está el borde a la derecha de nuestro punto?
                    if (edge.start().x() > midX) {
                        intersections++;
                    }
                }
            }
        }
        return intersections;
    }

    private boolean rectangleTouchPolygon(long rMinX, long rMaxX, long rMinY, long rMaxY) {
        for (Segment edge : edges) {
            if (edge.isVertical()) {
                // Si la X del borde está entre las X del rectángulo
                // Y el rango Y del borde se solapa con el rango Y del rectángulo
                if (edge.start().x() > rMinX && edge.start().x() < rMaxX) {
                    long edgeMinY = Math.min(edge.start().y(), edge.end().y());
                    long edgeMaxY = Math.max(edge.start().y(), edge.end().y());
                    // Ver si hay solapamiento estricto en Y
                    if (Math.max(rMinY, edgeMinY) < Math.min(rMaxY, edgeMaxY)) {
                        return true;
                    }
                }
            } else { // Si es horizontal
                if (edge.start().y() > rMinY && edge.start().y() < rMaxY) {
                    long edgeMinX = Math.min(edge.start().x(), edge.end().x());
                    long edgeMaxX = Math.max(edge.start().x(), edge.end().x());
                    if (Math.max(rMinX, edgeMinX) < Math.min(rMaxX, edgeMaxX)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public record Segment(Coordinate start, Coordinate end) {
        public boolean isVertical() {
            return start.x() == end.x();
        }
    }
}

package software.aoc.day12;

import java.util.*;

public record LogisticsEngine(Map<Integer, List<GiftShape>> shapeLibrary) {

    public static LogisticsEngine initialize(List<GiftShape> basicShapes) {
        Map<Integer, List<GiftShape>> library = new HashMap<>();

        for (int i = 0; i < basicShapes.size(); i++) {
            List<GiftShape> mutations = new ArrayList<>();
            GiftShape current = basicShapes.get(i);
            mutations.add(current);

            for (int r = 0; r < 3; r++) {
                current = current.turn90();
                mutations.add(current);
            }

            int currentSize = mutations.size();
            for (int m = 0; m < currentSize; m++) {
                mutations.add(mutations.get(m).mirror());
            }

            library.put(i, List.copyOf(mutations));
        }
        return new LogisticsEngine(Map.copyOf(library));
    }

    public boolean checkFit(TreeArea area) {
        if (area.totalGridArea() >= area.totalItemCount() * 9) return true;

        if (area.totalGridArea() < calculateRequiredBlocks(area.inventory())) return false;

        return attemptPlacement(area);
    }

    private boolean attemptPlacement(TreeArea area) {
        //Calculamos los centros posibles con getValidAnchors
        //Generamos todas las combinaciones de pocisiones de los regalos regalos en los puntos posibles
        for (List<Point2D> locations : PermutationUtils.getSubsets(area.getValidAnchors(), area.totalItemCount())) {
            if (canArrangeAtLocations(locations, area)) {
                return true;
            }
        }
        return false;
    }

    private boolean canArrangeAtLocations(List<Point2D> locations, TreeArea area) {
        List<Integer> variationsIndices = new ArrayList<>();
        //Creamos lista con las posibles rotaciones
        for(int i = 0; i < 8; i++) variationsIndices.add(i);
        //Elige las rotaciones
        for (List<Integer> orientationConfig : PermutationUtils.getSubsets(variationsIndices, area.totalItemCount())) {
            List<Point2D> placedBlocks = resolvePlacement(locations, area, orientationConfig);
            //Comprueba los choques
            if (areBlocksDistinct(placedBlocks)) {
                return true;
            }
        }
        return false;
    }

    private List<Point2D> resolvePlacement(List<Point2D> anchors, TreeArea area, List<Integer> orientations) {
        List<GiftShape> activeShapes = retrieveShapes(area, orientations);
        List<Point2D> finalGeometry = new ArrayList<>();

        //Añade los regalos
        for (int i = 0; i < anchors.size(); i++) {
            GiftShape movedShape = activeShapes.get(i).translateTo(anchors.get(i));
            finalGeometry.addAll(movedShape.geometry());
        }
        return finalGeometry;
    }

    private List<GiftShape> retrieveShapes(TreeArea area, List<Integer> orientations) {
        List<Integer> requiredIds = area.getExpandedInventoryList();
        List<GiftShape> result = new ArrayList<>();

        //Les da orientaciones a las formas
        for (int i = 0; i < requiredIds.size(); i++) {
            int shapeId = requiredIds.get(i);
            int orientationId = orientations.get(i);
            result.add(shapeLibrary.get(shapeId).get(orientationId));
        }
        return result;
    }

    private boolean areBlocksDistinct(List<Point2D> points) {
        Set<Point2D> uniqueChecker = new HashSet<>();
        for (Point2D p : points) {
            if (!uniqueChecker.add(p)) {
                return false;
            }
        }
        return true;
    }

    private int calculateRequiredBlocks(Map<Integer, Integer> requirements) {
        int total = 0;
        for (Map.Entry<Integer, Integer> entry : requirements.entrySet()) {
            int areaOfOne = shapeLibrary.get(entry.getKey()).get(0).blockSize();
            total += areaOfOne * entry.getValue();
        }
        return total;
    }
}
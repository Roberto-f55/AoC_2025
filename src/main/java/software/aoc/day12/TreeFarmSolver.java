package software.aoc.day12;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeFarmSolver {

    private final List<String> inputLines;

    public TreeFarmSolver() {
        this.inputLines = new ArrayList<>();
    }

    public static TreeFarmSolver create(){
        return new TreeFarmSolver();
    }

    public TreeFarmSolver with(List<String> lines){
        lines.forEach(inputLines::add);
        return this;
    }

    public TreeFarmSolver with(String lines){
        with(Arrays.asList(lines.split("\n")));
        return this;
    }

    public int solve() {
        ParsedData data = parseLines(inputLines);

        // 2. Inicializar el motor con las formas detectadas
        LogisticsEngine engine = LogisticsEngine.initialize(data.shapes);

        // 3. Resolver para cada región
        int validCount = 0;
        for (TreeArea region : data.regions) {
            if (engine.checkFit(region)) {
                validCount++;
            }
        }

        return validCount;
    }

    private ParsedData parseLines(List<String> lines) {
        List<List<String>> rawShapes = new ArrayList<>();
        List<TreeArea> regions = new ArrayList<>();

        List<String> currentShapeBuffer = null;

        currentShapeBuffer = addBuffersOrAreas(lines, regions, currentShapeBuffer, rawShapes);

        // Añadir la última forma pendiente
        if (currentShapeBuffer != null) {
            rawShapes.add(currentShapeBuffer);
        }

        // Convertir los buffers de texto a objetos GiftShape
        List<GiftShape> shapes = new ArrayList<>();
        for (List<String> shapeData : rawShapes) {
            shapes.add(GiftShape.parseShape(shapeData));
        }

        return new ParsedData(shapes, regions);
    }

    @Nullable
    private static List<String> addBuffersOrAreas(List<String> lines, List<TreeArea> regions, List<String> currentShapeBuffer, List<List<String>> rawShapes) {
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            // Detectar Región (ej: "12x5: ...")
            if (line.contains("x") && line.contains(":")) {
                regions.add(TreeArea.parse(line));
            }
            // Detectar Cabecera de Forma (ej: "0:")
            else if (line.matches("\\d+:")) {
                if (currentShapeBuffer != null) {
                    rawShapes.add(currentShapeBuffer);
                }
                currentShapeBuffer = new ArrayList<>();
                currentShapeBuffer.add(line);
            }
            // Detectar Cuerpo de Forma (ej: "###" o "..#")
            else if (currentShapeBuffer != null) {
                currentShapeBuffer.add(line);
            }
        }
        return currentShapeBuffer;
    }

    private record ParsedData(List<GiftShape> shapes, List<TreeArea> regions) {}
}
package software.aoc.day10;

public class BitUtils {

    // Convierte una lista de posiciones "0,2,3" en una máscara binaria (long)
    public static long createMask(String[] indices) {
        long mask = 0L;
        for (String indexStr : indices) {
            if (indexStr.isBlank()) continue;
            int index = Integer.parseInt(indexStr.trim());
            mask |= (1L << index);
        }
        return mask;
    }

    // Convierte el diagrama ".##." en una máscara binaria
    public static long fromDiagram(String diagram) {
        long mask = 0L;
        for (int i = 0; i < diagram.length(); i++) {
            if (diagram.charAt(i) == '#') {
                mask |= (1L << i);
            }
        }
        return mask;
    }
}

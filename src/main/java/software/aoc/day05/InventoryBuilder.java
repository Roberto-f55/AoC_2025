package software.aoc.day05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryBuilder {

    public static Inventory createFrom(String input) {
        String[] sections = input.split("\n");
        return createFrom(Arrays.stream(sections).toList());
    }

    public static Inventory createFrom(List<String> sections) {
        int separatorIndex = sections.indexOf("");
        List<Ranges> ranges = processRanges(sections.subList(0, separatorIndex));
        List<Long> ids = processIds(sections.subList(separatorIndex + 1, sections.size()));

        return new Inventory(ranges, ids);
    }

    private static List<Ranges> processRanges(List<String>  rgs) {
        return  rgs.stream()
                .filter(line -> !line.isBlank())
                .map(line -> line.split("-"))
                .map(p -> Ranges.with(Long.parseLong(p[0].trim()), Long.parseLong(p[1].trim())))
                .toList();
    }

    private static List<Long> processIds(List<String> lines) {
        return lines.stream()
                .filter(line -> !line.isBlank())
                .map(line -> Long.parseLong(line.trim()))
                .toList();
    }
}

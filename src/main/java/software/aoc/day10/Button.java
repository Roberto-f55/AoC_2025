package software.aoc.day10;

public record Button(long mask) {

    public static Button from(String text) {
        String content = text.substring(1, text.length() - 1);
        return new Button(BitUtils.createMask(content.split(",")));
    }

    public int size() { return Long.bitCount(mask); }
}
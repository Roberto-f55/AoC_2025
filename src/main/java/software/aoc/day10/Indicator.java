package software.aoc.day10;

public record Indicator(long state) {

    public static Indicator initial() {
        return new Indicator(0L);
    }

    public static Indicator targetFrom(String text) {
        String content = text.substring(1, text.length() - 1);
        return new Indicator(BitUtils.fromDiagram(content));
    }

    public Indicator toggle(Button button) {
        long newState = this.state ^ button.mask();
        return new Indicator(newState);
    }

    public boolean matches(Indicator other) {
        return this.state == other.state();
    }
}
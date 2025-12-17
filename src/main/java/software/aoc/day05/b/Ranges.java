package software.aoc.day05.b;

public class Ranges {
    private final long low;

    private final long high;

    public static Ranges with(long low, long high){
        return new Ranges(low, high);
    }

    private Ranges(long low, long high) {
        this.low = low;
        this.high = high;
    }

    boolean contains(long ingredient){
        return ingredient >= low && ingredient <= high;
    }

    public long getHigh() {
        return high;
    }

    public long getLow() {
        return low;
    }
}

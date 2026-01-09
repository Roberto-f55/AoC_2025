package software.aoc.day05;

public record Ranges(long low, long high) {

    public static Ranges with(long low, long high){
        return new Ranges(low, high);
    }

    public boolean contains(long ingredient){
        return ingredient >= low && ingredient <= high;
    }
}

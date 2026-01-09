package software.aoc.day03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Voltage {
    private final List<String> batteries;

    public static Voltage create(){
        return new Voltage();
    }

    private Voltage(){
        this.batteries = new ArrayList<>();
    }

    public Voltage batteries(String... bats) {
        Arrays.stream(bats)
                .forEach(this::addBats);
        return this;
    }

    public Voltage add(String bats) {
        batteries(bats.split("\n"));
        return this;
    }

    private void addBats(String s) {
        this.batteries.add(s);
    }

    public long sum(int maxBatts) {
        return batteries.stream()
                .map(bat -> maxVol(bat, maxBatts))
                .mapToLong(Long::parseLong)
                .sum();
    }

    private String maxVol(String bats, int maxBatts) {
        StringBuilder solution = new StringBuilder();
        int start = 0;

        for (int i = 0; i < maxBatts; i++){
            char best = '0';
            int bestIndex = start;

            int limit = bats.length() - (maxBatts - i);

            for (int j = start; j <= limit; j++){
                char c = bats.charAt(j);
                if (c > best){ best = c; bestIndex = j;}
            }
            start = bestIndex + 1;
            solution.append(best);
        }
        return String.valueOf(solution);
    }
}

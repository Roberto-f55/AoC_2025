package software.aoc.day10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MachineManager {
    private final List<Machine> machines;

    private MachineManager(){
        this.machines = new ArrayList<>();
    }

    public static MachineManager create(){
        return new MachineManager();
    }

    public MachineManager with(List<String> machins) {
        machins.stream().forEach(this::add);
        return this;
    }

    public MachineManager with(String m) {
        Arrays.stream(m.split("\n")).forEach(this::add);
        return this;
    }

    public MachineManager addMachines(String m) {
        this.with(m);
        return this;
    }

    private void add(String m) {
        machines.add(Machine.from(m));
    }

    public long solveA() {
        return machines.stream()
                .mapToLong(m -> new FactorySolver(m).solveA())
                .filter(l -> l!= -1)
                .sum();
    }

    public  long solveB() {
        return machines.stream()
                .mapToLong(m -> new FactorySolver(m).solveB())
                .filter(l -> l!= -1)
                .sum();
    }
}

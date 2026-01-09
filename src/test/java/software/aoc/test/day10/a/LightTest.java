package software.aoc.test.day10.a;

import org.junit.Test;
import software.aoc.day10.MachineManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class LightTest {

    private final String machines = """
            [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
            """;

    @Test
    public void one_machine_should_work_well() {
        MachineManager machine = MachineManager.create().with("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}");
        assertThat(machine.solveA()).isEqualTo(2);
    }

    @Test
    public void two_machines_should_work_well() {
        MachineManager machine = MachineManager.create().with("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}");
        machine.addMachines("[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}");
        assertThat(machine.solveA()).isEqualTo(5);
    }

    @Test
    public void some_machines_should_work_well() {
        MachineManager machine = MachineManager.create().with(machines);
        assertThat(machine.solveA()).isEqualTo(7);
    }
}
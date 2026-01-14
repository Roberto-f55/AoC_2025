package software.aoc.test.day12.a;

import org.junit.Test;
import software.aoc.day12.TreeFarmSolver;

import static org.assertj.core.api.Assertions.assertThat;

public class GiftTest {

    private final String onePresent = """
            0:
            ###
            ##.
            ##.
            
            12x5: 1 
            """;
    private final String presents = """
            0:
            ###
            ##.
            ##.
            
            1:
            ###
            ##.
            .##
            
            2:
            .##
            ###
            ##.
            
            3:
            ##.
            ###
            ##.
            
            4:
            ###
            #..
            ###
            
            5:
            ###
            .#.
            ###
            
            4x4: 0 0 0 0 2 0
            12x5: 1 0 1 0 2 2
            12x5: 1 0 1 0 3 2
            """;

    @Test
    public void one_present_should_fit() {
        TreeFarmSolver treeFarmSolver = TreeFarmSolver.create().with(onePresent);
        assertThat(treeFarmSolver.solve()).isEqualTo(1);
    }

    @Test
    public void some_presents_should_fit() {
        TreeFarmSolver treeFarmSolver = TreeFarmSolver.create().with(presents);
        assertThat(treeFarmSolver.solve()).isEqualTo(2);
    }

}

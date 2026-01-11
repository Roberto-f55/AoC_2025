package software.aoc.test.day08.a;

import org.junit.Test;
import software.aoc.day08.CircuitManager;
import software.aoc.day08.a.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ConnectionTest {

    private final String boxes = """
            162,817,812
            57,618,57
            906,360,560
            592,479,940
            352,342,300
            466,668,158
            542,29,236
            431,825,988
            739,650,466
            52,470,668
            216,146,977
            819,987,18
            117,168,530
            805,96,715
            346,949,466
            970,615,88
            941,993,340
            862,61,35
            984,92,344
            425,690,689
            """;

    @Test
    public void circuitManager_with_boxes_should_be_fine(){
        CircuitManager circuitManager = CircuitManager.create().with("162,817,812\n57,618,57").connectClosestPairs(10);
        assertThat(circuitManager.countBiggest3Circuits()).isEqualTo(2);
    }

    @Test
    public void circuitManager_with_a_lot_of_boxes_should_be_fine(){
        CircuitManager circuitManager = CircuitManager.create().with(boxes).connectClosestPairs(10);
        assertThat(circuitManager.countBiggest3Circuits()).isEqualTo(40);
    }
}

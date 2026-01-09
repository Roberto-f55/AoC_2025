package software.aoc.test.day01.a;

import org.junit.Test;
import software.aoc.day01.Dial;

import static org.assertj.core.api.Assertions.assertThat;

public class DialTest {

    private static final String orders = """
            L68
            L30
            R48
            L5
            R60
            L55
            L1
            L99
            R14
            L82
            """;


    @Test
    public void given_orders_should_account_the_final_position() {
        //Dial dial = Dial.create();
        assertThat(Dial.create().add("L1").position()).isEqualTo(49);
        assertThat(Dial.create().add("L1", "R1", "R50").position()).isEqualTo(0);
        assertThat(Dial.create().add("L51", "L500").position()).isEqualTo(99);
        assertThat(Dial.create().execute(orders).position()).isEqualTo(32);
    }

    @Test
    public void given_orders_should_account_the_times_that_position_is_zero() {
        assertThat(Dial.create().execute(orders).countA()).isEqualTo(3);
        assertThat(Dial.create().add("L1").countA()).isEqualTo(0);
        assertThat(Dial.create().add("L1", "R1", "R50").countA()).isEqualTo(1);
        assertThat(Dial.create().add("L51", "L500").countA()).isEqualTo(0);
    }
}

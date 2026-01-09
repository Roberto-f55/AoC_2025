package software.aoc.test.day03.a;

import org.junit.Test;
import software.aoc.day03.Voltage;

import static org.assertj.core.api.Assertions.assertThat;

public class BatteryTest {
    private static final String batteries = """
            987654321111111
            811111111111119
            234234234234278
            818181911112111
            """;

    @Test
    public void max_voltage_between_batteries(){
        assertThat(Voltage.create().batteries("987654321111111").sum(2)).isEqualTo(98L);
        assertThat(Voltage.create().batteries("811111111111119").sum(2)).isEqualTo(89L);
        assertThat(Voltage.create().batteries("234234234234278").sum(2)).isEqualTo(78L);
        assertThat(Voltage.create().batteries("818181911112111").sum(2)).isEqualTo(92L);
    }

    @Test
    public void sum_max_voltage_between_batteries(){
        assertThat(Voltage.create().add(batteries).sum(2)).isEqualTo(357L);
    }
}

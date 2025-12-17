package software.aoc.test.day03.a;

import org.junit.Test;
import software.aoc.day03.a.Voltage;

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
        assertThat(Voltage.create().batteries("987654321111111").max_vol()).isEqualTo("98");
        assertThat(Voltage.create().batteries("811111111111119").max_vol()).isEqualTo("89");
        assertThat(Voltage.create().batteries("234234234234278").max_vol()).isEqualTo("78");
        assertThat(Voltage.create().batteries("818181911112111").max_vol()).isEqualTo("92");
    }

    @Test
    public void sum_max_voltage_between_batteries(){
        assertThat(Voltage.create().add(batteries).sum()).isEqualTo(357);
    }
}

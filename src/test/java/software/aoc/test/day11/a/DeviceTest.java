package software.aoc.test.day11.a;

import org.junit.Test;
import software.aoc.day11.DeviceManager;

import static org.assertj.core.api.Assertions.assertThat;

public class DeviceTest {

    private final String devices = """
            aaa: you hhh
            you: bbb ccc
            bbb: ddd eee
            ccc: ddd eee fff
            ddd: ggg
            eee: out
            fff: out
            ggg: out
            hhh: ccc fff iii
            iii: out
            """;

    @Test
    public void some_devices_should_connect(){
        DeviceManager deviceManager = DeviceManager.create().with(devices);
        assertThat(deviceManager.solveA()).isEqualTo(5);
    }
}

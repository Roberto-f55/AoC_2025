package software.aoc.day11.a;

import software.aoc.Reader;
import java.util.List;

public class Main11a {
    public static void main(String[] args) {
        List<String> aLotOfDevices = Reader.lines("day11/input.txt");
        DeviceManager deviceManager = DeviceManager.create().with(aLotOfDevices);
        System.out.println(deviceManager.solve());
    }
}

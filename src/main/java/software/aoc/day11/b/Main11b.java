package software.aoc.day11.b;

import software.aoc.Reader;
import software.aoc.day11.DeviceManager;

import java.util.List;

public class Main11b {
    public static void main(String[] args) {
        List<String> aLotOfDevices = Reader.lines("day11/input.txt");
        DeviceManager deviceManager = DeviceManager.create().with(aLotOfDevices);
        System.out.println(deviceManager.solveB());
    }
}

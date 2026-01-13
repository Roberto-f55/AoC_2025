package software.aoc.day11;

import java.util.*;

public class DeviceManager {
    private final Map<String, List<String>> devices;

    private DeviceManager() {
        this.devices = new HashMap<>();
    }

    public static DeviceManager create() {
        return new DeviceManager();
    }

    public DeviceManager with(List<String> dvs) {
        buildDevices(dvs.toArray(new String[0]));
        return this;
    }

    public DeviceManager with(String dvs) {
        buildDevices(dvs.split("\n"));
        return this;
    }

    private void buildDevices(String[] devs) {
        Arrays.stream(devs)
                .map(line -> line.split(":"))
                .forEach(parts -> devices.put(parts[0], toArray(parts[1])));
    }

    private static List<String> toArray(String part) {
        return Arrays.asList(part.trim().split(" "));
    }

    public long solveA() {
        return new PathSolver(devices).solve("you", true, true);
    }

    public long solveB() {
        return new PathSolver(devices).solve("svr", false, false);
    }
}
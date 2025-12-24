package software.aoc.day11.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DeviceManager {
    private final HashMap<String, List<String>> devices;

    public static DeviceManager create() {
        return new DeviceManager();
    }

    private DeviceManager() {
        this.devices = new HashMap<>();
    }

    public DeviceManager with(List<String> dvs){
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

    public int solve() {
        List<String> temporal = new ArrayList<>();
        temporal.add("you");
        return dfsConnect(temporal, "you", 0);
    }

    private int dfsConnect(List<String> path, String node, int cuenta) {
        //CASO BASE
        if (node.equals("out")) {
            return cuenta + 1;
        }

        //MIRAMOS LAS OTRAS POSIBILIDADES
        List<String> posibilites = devices.get(node);
        for (String element : posibilites) {
            if (!path.contains(element)) {
                path.add(element);
                cuenta = dfsConnect(path, element, cuenta);
                path.remove(element);
            }
        }
        return cuenta;
    }
}

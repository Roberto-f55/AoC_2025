package software.aoc.day02.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Expert {
    private final List<String> ids;
    private final List<String> false_ids;

    public static Expert create() {
        return new Expert();
    }

    private Expert() {
        this.ids = new ArrayList();
        this.false_ids = new ArrayList<>();
    }

    public Expert execute(String orders){
        add(orders.split("\n"));
        return this;
    }

    public Expert add(String... cadena) {
        Arrays.stream(cadena)
                .forEach(this::add);
        return this;
    }

    private void add(String cadena) {
        ids.add(cadena);
    }

    private void add_falseid(String s) { false_ids.add(s);}

    public String[] find() {
        for (String cadena : ids)   find_false_id(cadena);
        return false_ids.toArray(new String[]{});
    }

    private void find_false_id(String range) {
        //return new String[]{"11", "22"};
        String[] parts = range.split("-");
        List<String> sol = new ArrayList<>();
        for (long cadena = Long.parseLong(parts[0]); cadena <= Long.parseLong(parts[1]); cadena++) {
            if (comprobar(String.valueOf(cadena))) sol.add(String.valueOf(cadena));
        }
        sol.stream().forEach(this::add_falseid);

    }

    private boolean comprobar(String cadena) {
        return (cadena.length() % 2 == 0) ? mitad_inferior(cadena).equals(mitad_superior(cadena)) : false;
    }

    private String mitad_inferior(String cadena) {
        return cadena.substring(0, cadena.length() / 2);
    }

    private String mitad_superior(String cadena) {
        return cadena.substring(cadena.length() / 2);
    }

    public long sum() {
        return Arrays.stream(find()).mapToLong(Long::parseLong).sum();
    }
}

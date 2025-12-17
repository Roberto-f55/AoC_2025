package software.aoc.day02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

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

    public Expert add(List<String> cadena) {
        add(cadena.get(0).split(","));
        return this;
    }

    private void add(String cadena) {
        ids.add(cadena);
    }

    private void add_falseid(String s) { false_ids.add(s);}

    public String[] find(String type) {
        Predicate<String> comprobar = selectFunction(type);

        for (String cadena : ids)   findFalseId(cadena, comprobar);
        return false_ids.toArray(new String[]{});
    }

    private Predicate<String> selectFunction(String type) {
        return type == "A" ? this::comprobarA : this::comprobarB;
    }

    private void findFalseId(String range, Predicate<String> comprobar) {
        String[] parts = range.split("-");
        List<String> sol = new ArrayList<>();
        for (long cadena = Long.parseLong(parts[0]); cadena <= Long.parseLong(parts[1]); cadena++) {
            if (comprobar.test(String.valueOf(cadena))) sol.add(String.valueOf(cadena));
        }
        sol.stream().forEach(this::add_falseid);
    }

    private boolean comprobarA(String cadena) {
        return (cadena.length() % 2 == 0) ? mitad_inferior(cadena).equals(mitad_superior(cadena)) : false;
    }

    private boolean comprobarB(String cadena) {
        return IntStream.rangeClosed(1, cadena.length() / 2)
                .filter(k -> cadena.length() % k == 0)
                .anyMatch(k ->
                        cadena.substring(0, k)
                        .repeat(cadena.length() / k)
                                .equals(cadena)
                );
    }

    private String mitad_inferior(String cadena) {
        return cadena.substring(0, cadena.length() / 2);
    }

    private String mitad_superior(String cadena) {
        return cadena.substring(cadena.length() / 2);
    }

    public long sumA() {
        return Arrays.stream(find("A")).mapToLong(Long::parseLong).sum();
    }

    public long sumB() {
        return Arrays.stream(find("B")).mapToLong(Long::parseLong).sum();
    }
}

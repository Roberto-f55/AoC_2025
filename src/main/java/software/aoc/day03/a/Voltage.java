package software.aoc.day03.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Voltage {
    private final List<String> batteries;

    public static Voltage create(){
        return new Voltage();
    }

    private Voltage(){
        this.batteries = new ArrayList<>();
    }

    public Voltage batteries(String... bats) {
        Arrays.stream(bats)
                .forEach(this::add_bats);
        return this;
    }

    public Voltage add(String bats) {
        batteries(bats.split("\n"));
        return this;
    }

    private void add_bats(String s) {
        this.batteries.add(s);
    }

    public String max_vol() {
        for (String value : batteries){
            return max_vol(value);

        }
        return "";
    }

    private String x_mayor_y(String first, String scnd) {
        return Integer.parseInt(first) > Integer.parseInt(scnd) ? first : scnd ;
    }

    public int sum() {
        return batteries.stream()
                .map(this::max_vol)
                .mapToInt(Integer::parseInt)
                .sum();
    }

    private String max_vol(String bats) {
        String max_value1 = "0";
        String max_value2 = "0";
        String max_value3 = "0";

        for (char value : bats.toCharArray()){
            if (!max_value1.equals(x_mayor_y(max_value1, String.valueOf(value)))) {
                ///MAXV3 ES EL VALOR MAXIMO MAS EL QUE TOCA, PARA QUE SI ES EL ÚLTIMO CASO
                       ///QUEDE REGISTRADO, Y SEA MAYOR QUE EL MAXIMO VALOR CON UN 0 COMO SEGUNDO
                max_value3 = max_value1 + String.valueOf(value);
                max_value1 = String.valueOf(value);
                max_value2 = "0";
            }
            else if (!max_value2.equals(x_mayor_y(max_value2, String.valueOf(value)))) {
                max_value2 = String.valueOf(value);
            }

        }
        return max_value2 != "0" ? x_mayor_y(max_value3, max_value1 + max_value2) : max_value3;
    }
}

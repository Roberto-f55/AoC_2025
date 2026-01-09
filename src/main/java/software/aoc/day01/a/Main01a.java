package software.aoc.day01.a;

import org.jetbrains.annotations.NotNull;
import software.aoc.Reader;
import software.aoc.day01.Dial;
import io.javalin.http.Context;
import java.util.List;

public class Main01a {
    public static void main(String[] args) {
        solveDay01a();
    }

    private static void solveDay01a() {
        List<String> lines = Reader.lines("day01/input.txt");
        System.out.println(Dial.create().add(lines.toArray(new String[0])).countA());
    }

    public static void solveDay01aAPI(Context context){
        List<String> lines = Reader.lines("day01/input.txt");
        long result = Dial.create().add(lines.toArray(new String[0])).countA();
        context.json("El resultado del dia uno version (fase1) es: " + result);
    }
}

package software.aoc;

import io.javalin.Javalin;
import software.aoc.day01.a.Main01a;

public class API {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);

        app.get("/Day01a", Main01a::solveDay01aAPI);
    }
}

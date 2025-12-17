package software.aoc;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class Reader {

    private Reader() {}

    public static List<String> lines(String path) {
        try (InputStream is = Reader.class
                .getClassLoader()
                .getResourceAsStream(path)) {

            if (is == null) {
                throw new IllegalArgumentException("Recurso no encontrado: " + path);
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8));

            return br.lines().toList();

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

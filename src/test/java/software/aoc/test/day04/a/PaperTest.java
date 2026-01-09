package software.aoc.test.day04.a;

import org.junit.Test;
import software.aoc.day04.PapersManager;

import static org.assertj.core.api.Assertions.assertThat;

public class PaperTest {

    private String matrixOfPapers = """
            ..@@.@@@@.
            @@@.@.@.@@
            @@@@@.@.@@
            @.@@@@..@.
            @@.@@@@.@@
            .@@@@@@@.@
            .@.@.@.@@@
            @.@@@.@@@@
            .@@@@@@@@.
            @.@.@@@.@.
            """;

    @Test
    public void papers_in_a_row(){
        assertThat(PapersManager.create().add("..@@.@@@@.").numberOfPapers()).isEqualTo(6);
        assertThat(PapersManager.create().add("@.@@@@..@.").numberOfPapers()).isEqualTo(6);
        assertThat(PapersManager.create().add(".@@@@@@@@.").numberOfPapers()).isEqualTo(8);
    }

    @Test
    public void papers_in_a_matrix(){
        assertThat(PapersManager.create().with(matrixOfPapers).numberOfPapers()).isEqualTo(13);
    }
}

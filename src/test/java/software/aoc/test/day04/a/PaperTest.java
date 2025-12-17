package software.aoc.test.day04.a;

import org.junit.Test;
import software.aoc.day04.a.*;

import static org.assertj.core.api.Assertions.assertThat;

public class PaperTest {

    private String matrix_of_papers = """
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
        //Paper paper = Paper.create();
        assertThat(Paper.create().add("..@@.@@@@.").numberOfPapers()).isEqualTo(6);
        assertThat(Paper.create().add("@.@@@@..@.").numberOfPapers()).isEqualTo(6);
        assertThat(Paper.create().add(".@@@@@@@@.").numberOfPapers()).isEqualTo(8);
    }

    @Test
    public void papers_in_a_matrix(){
        assertThat(Paper.create().put(matrix_of_papers).numberOfPapers()).isEqualTo(13);
    }
}

package software.aoc.test.day02.a;

import org.junit.Test;
import software.aoc.day02.Expert;

import static org.assertj.core.api.Assertions.assertThat;

public class IdTest {
    private static final String ids = """
            11-22
            95-115
            998-1012
            1188511880-1188511890
            222220-222224
            1698522-1698528
            446443-446449
            38593856-38593862
            565653-565659
            824824821-824824827
            2121212118-2121212124
            """;

    @Test
    public void find_falseid_between_2ids(){
        assertThat(Expert.create().add("11-22").sumA()).isEqualTo(33L);
        assertThat(Expert.create().add("95-115").sumA()).isEqualTo(99L);
        assertThat(Expert.create().add("446443-446449").sumA()).isEqualTo(446446L);
        assertThat(Expert.create().add("38593856-38593862").sumA()).isEqualTo(38593859L);

    }

    @Test
    public void find_falseid_between_more_than_2ids(){
        assertThat(Expert.create().add("11-22", "95-115", "446443-446449").sumA())
                .isEqualTo(446578L);
    }

    @Test
    public void count_false_id(){
        assertThat(Expert.create().execute(ids).sumA()).isEqualTo(1227775554L);
    }

}

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
        assertThat(Expert.create().add("11-22").find("A")).isEqualTo(new String[]{"11", "22"});
        assertThat(Expert.create().add("95-115").find("A")).isEqualTo(new String[]{"99"});
        assertThat(Expert.create().add("446443-446449").find("A")).isEqualTo(new String[]{"446446"});
        assertThat(Expert.create().add("38593856-38593862").find("A")).isEqualTo(new String[]{"38593859"});

    }

    @Test
    public void find_falseid_between_more_than_2ids(){
        assertThat(Expert.create().add("11-22", "95-115", "446443-446449").find("A"))
                .isEqualTo(new String[]{"11", "22", "99", "446446"});
    }

    @Test
    public void count_false_id(){
        assertThat(Expert.create().execute(ids).sumA()).isEqualTo(1227775554);
    }

}

package software.aoc.test.day02.a;

import org.junit.Test;
import software.aoc.day02.a.Expert;

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

    private static final String aoc_ids = """
            9595822750-9596086139
            1957-2424
            88663-137581
            48152-65638
            12354817-12385558
            435647-489419
            518494-609540
            2459-3699
            646671-688518
            195-245
            295420-352048
            346-514
            8686839668-8686892985
            51798991-51835611
            8766267-8977105
            2-17
            967351-995831
            6184891-6331321
            6161577722-6161678622
            912862710-913019953
            6550936-6625232
            4767634976-4767662856
            2122995-2257010
            1194-1754
            779-1160
            22-38
            4961-6948
            39-53
            102-120
            169741-245433
            92902394-92956787
            531-721
            64-101
            15596-20965
            774184-943987
            8395-11781
            30178-47948
            94338815-94398813
            """;

    @Test
    public void find_falseid_between_2ids(){
        assertThat(Expert.create().add("11-22").find()).isEqualTo(new String[]{"11", "22"});
        assertThat(Expert.create().add("95-115").find()).isEqualTo(new String[]{"99"});
        assertThat(Expert.create().add("446443-446449").find()).isEqualTo(new String[]{"446446"});
        assertThat(Expert.create().add("38593856-38593862").find()).isEqualTo(new String[]{"38593859"});

    }

    @Test
    public void find_falseid_between_more_than_2ids(){
        assertThat(Expert.create().add("11-22", "95-115", "446443-446449").find())
                .isEqualTo(new String[]{"11", "22", "99", "446446"});
    }

    @Test
    public void count_false_id(){
        assertThat(Expert.create().execute(ids).sum()).isEqualTo(1227775554);
    }

    @Test
    public void count_aoc_false_id(){
        assertThat(Expert.create().execute(aoc_ids).sum()).isEqualTo(40398804950L);
    }
}

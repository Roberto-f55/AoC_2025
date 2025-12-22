package software.aoc.test.day09.a;

import org.junit.Test;
import software.aoc.day09.a.Rectangle;
import software.aoc.day09.a.RectangleBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class AreaTest {

    private final String redTiles = """
            7,1
            11,1
            11,7
            9,7
            9,5
            2,5
            2,3
            7,3
            """;


    @Test
    public void two_redTiles_should_be_uniq_rectangle(){
        Rectangle rectangle = RectangleBuilder.create().with("7,1\n11,1").buildRectangle();
        assertThat(rectangle.getArea()).isEqualTo(5);
    }

    @Test
    public void a_lot_of_redTiles_should_be_uniq_rectangle(){
        Rectangle rectangle = RectangleBuilder.create().with(redTiles).buildRectangle();
        assertThat(rectangle.getArea()).isEqualTo(50);
    }

}

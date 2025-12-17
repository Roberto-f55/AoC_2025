package software.aoc.test.day05.a;

import org.junit.Test;
import software.aoc.day05.a.*;

import static org.assertj.core.api.Assertions.assertThat;

public class InventoryTest {
    private String mockInventory = """
            3-5
            
            1
            5
            """;

    private String mediumInventory = """
            3-5
            10-14
            16-20
            12-18
            
            1
            5
            8
            11
            17
            32
            """;

    @Test
    public void small_inventory_should_have_fresh_ingredients(){
        assertThat(Inventory.create().add(mockInventory).countFreshIngredients()).isEqualTo(1);
    }

    @Test
    public void medium_inventory_should_have_fresh_ingredients(){
        assertThat(Inventory.create().add(mediumInventory).countFreshIngredients()).isEqualTo(3);
    }
}

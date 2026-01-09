package software.aoc.test.day06.a;

import org.junit.Test;
import software.aoc.day06.*;

import static org.assertj.core.api.Assertions.assertThat;

public class OperationTest {

    private final String smallProblem =
            "123 328  51 64 \n" +
                    " 45 64  387 23 \n" +
                    "*   +   *   +  \n";

    private final String bigProblem =
            "123 328  51 64 \n" +
                    " 45 64  387 23 \n" +
                    "  6 98  215 314 \n" +
                    "*   +   *   +  \n";



    @Test
    public void small_problem_should_be_easy(){
        OperationStore store = OperationsBuilder.createA().with(smallProblem).build();
        assertThat(store.calculate()).isEqualTo(25751L);
    }

    @Test
    public void big_problem_should_be_easy(){
        OperationStore store = OperationsBuilder.createA().with(bigProblem).build();
        assertThat(store.calculate()).isEqualTo(4277556L);
    }
}

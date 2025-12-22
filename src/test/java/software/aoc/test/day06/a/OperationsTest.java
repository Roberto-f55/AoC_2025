package software.aoc.test.day06.a;

import org.junit.Test;
import software.aoc.day06.a.Problem;

import static org.assertj.core.api.Assertions.assertThat;

public class OperationsTest {

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
        Problem problem = Problem.create();
        assertThat(problem.with(smallProblem).solution()).isEqualTo(25751);
    }

    @Test
    public void big_problem_should_be_easy(){
        Problem problem = Problem.create();
        assertThat(problem.with(bigProblem).solution()).isEqualTo(4277556);
    }
}

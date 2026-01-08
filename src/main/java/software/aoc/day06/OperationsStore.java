package software.aoc.day06;

import java.util.stream.Stream;

public record OperationsStore(Stream<Operation> operations) {

    public long calculate(){
        return operations
                .mapToLong(Operation::calculate)
                .sum();
    }
}

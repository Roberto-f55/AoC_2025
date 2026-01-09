package software.aoc.day07;

import java.util.Collections;
import java.util.List;

public class TachyonsTreeBuilder {
    private List<String> rawInput;

    public static TachyonsTreeBuilder create() {
        return new TachyonsTreeBuilder();
    }

    private TachyonsTreeBuilder() {
        this.rawInput = Collections.emptyList();
    }

    public TachyonsTreeBuilder with(String treeStructure) {
        this.rawInput = List.of(treeStructure.split("\n"));
        return this;
    }

    public TachyonsTreeBuilder with(List<String> treeStructure) {
        this.rawInput = treeStructure;
        return this;
    }

    public TachyonsTreeManager build() {
        TachyonsTree tree = new TachyonsTree(rawInput);
        return new TachyonsTreeManager(tree);
    }
}
package software.aoc.day07.a;

import java.util.List;

public class TachyonTree {
    private final Tree tree;


    public static TachyonTree create() {
        return new TachyonTree();
    }

    private TachyonTree() {
        this.tree = Tree.create();
    }


    public TachyonTree with(String treeStructure) {
        tree.with(treeStructure);
        return this;
    }

    public TachyonTree with(List<String> treeStructure) {
        tree.with(treeStructure);
        return this;
    }

    public int countTachyons() {
        return tree.countTachyons();
    }
}

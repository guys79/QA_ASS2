package system;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
/**
 * This class will test the Node class
 */
public class NodeTest {
    @Test
    /**
     * This function will check of the path of the tree is correct
     */
    public void checkPath() {
        int depth = 8;
        //Establishing name and size of the leaf
        String name = "UniqueNodeName";

        //Creating tree
        Node testedTree = new Tree(name);

        //Creaing branch in size of 'depth'
        Tree tree;
        Node prev = testedTree;
        testedTree.depth = depth + 1;
        String treeName = "treeName";
        List<Tree> trees = new ArrayList<>();
        for (int i = 0; i < depth; i++) {
            tree = new Tree(treeName + "i");
            //tree.children.put(prev.name,prev);
            prev.parent = tree;
            tree.depth = prev.depth - 1;
            prev = tree;
            if (prev instanceof Tree)
                trees.add((Tree) prev);
        }

        //Getting the path
        String[] path = testedTree.getPath();
        //Check path size
        assertEquals(path.length, depth + 1);

        //Check path values
        for (int i = 0; i < trees.size(); i++) {
            assertEquals(trees.get(i).name, path[i]);
        }
        assertEquals(path[path.length - 1], name);

    }
}
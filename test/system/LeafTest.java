package system;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class will test the Tree class
 */
public class LeafTest {

    @Test
    /**
     * This function will test if the name of the leaf is the same as given
     */
    public void getName() {
        //Establishing name and size of the leaf
        String name = "LeafName";
        int size = 1;

        //Creating the FileSystem necessary for leaf to run
        new FileSystem(size*2);

        //Creating leaf legally
        Leaf leaf = null;
        try {
            leaf = new Leaf(name,size);
        } catch (OutOfSpaceException e) {
            e.printStackTrace();
        }
        //Check if the name is the same
        assertEquals(leaf.name,name);



    }

    @Test
    /**
     * This function will check if the allocat
     */
    public void sizeCheck() {
        //Establishing name and size of the leaf
        String name = "LeafName";
        int size = 6;

        //Creating the FileSystem necessary for leaf to run
        new FileSystem(size*2);

        //Creating leaf legally
        Leaf leaf = null;
        try {
            leaf = new Leaf(name,size);
        } catch (OutOfSpaceException e) {
            e.printStackTrace();
        }

        //Check allocation size
        assertEquals(leaf.allocations.length,size);

        int memory;
        for(int i=0;i<size;i++)
        {
            memory = leaf.allocations[i];
            assertEquals(FileSystem.fileStorage.getAlloc()[memory],leaf);
        }



    }

    @Test
    /**
     * This function will check if the invalid creation of a leaf actually throws OutOfSpaceException
     */
    public void invalidCreate()
    {
        //Establishing name and size of the leaf
        String name = "LeafName";
        int size = 1;

        //Creating the FileSystem necessary for leaf to run
        new FileSystem(size*2);

        //Creating leaf ilegally (size bigger then limit)
        Leaf leaf = null;
        boolean isRightException = false;
        try {
            leaf = new Leaf(name,size*4);
        } catch (Exception e) {
            //If the right exception was thrown
            isRightException = e instanceof OutOfSpaceException;
        }
// TODO: 13/12/2019 uncomment
//        assertTrue(isRightException);

    }

    @Test
    /**
     * This function will check of the path of the leaf is correct
     */
    public void checkPath()
    {
        int depth = 8;
        //Establishing name and size of the leaf
        String name = "LeafName";
        int size = 1;

        //Creating the FileSystem necessary for leaf to run
        new FileSystem(size*2);

        //Creating leaf legally
        Leaf leaf = null;
        try {
            leaf = new Leaf(name,size);
        } catch (OutOfSpaceException e) {
            e.printStackTrace();
        }

        //Creaing branch in size of 'depth'
        Tree tree;
        Node prev = leaf;
        leaf.depth = depth+1;
        String treeName = "treeName";
        List<Tree> trees = new ArrayList<>();
        for(int i=0;i<depth;i++)
        {
            tree = new Tree(treeName+"i");
            //tree.children.put(prev.name,prev);
            prev.parent = tree;
            tree.depth = prev.depth - 1;
            prev = tree;
            if(prev instanceof Tree)
                trees.add((Tree)prev);
        }

        //Getting the path
        String [] path = leaf.getPath();
        //Check path size
        assertEquals(path.length,depth+1);

        //Check path values
        for(int i=0;i<trees.size();i++)
        {
            assertEquals(trees.get(i).name,path[i]);
        }
        assertEquals(path[path.length-1],name);
        
    }





}
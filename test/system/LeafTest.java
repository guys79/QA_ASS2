package system;

import javafx.fxml.Initializable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import static org.junit.Assert.*;

/**
 * This class will test the Tree class
 */
public class LeafTest {

    private Random rand;//The random variable
    private final int BOUND = 100; //The maximal random number

    /**
     * This function will test if the name of the leaf is the same as given
     */
    @Test
    public void getName() {
        //Establishing name and size of the leaf
        String name = "LeafName";
        int size = rand.nextInt(BOUND);

        //Creating the FileSystem necessary for leaf to run
        FileSystem.fileStorage = new Space(size+1);

        //Creating leaf legally
        Leaf leaf = null;
        try {
            leaf = new Leaf(name,size);
        } catch (OutOfSpaceException e) {
            e.printStackTrace();
            assertTrue(false);

        }
        //Check if the name is the same
        assertEquals(leaf.name,name);

        FileSystem.fileStorage = null;

    }


    /**
     * This function will check if the allocation procedure behaves the way ot should
     */
    @Test
    public void sizeCheck() {
        //Establishing name and size of the leaf
        String name = "LeafName";
        int size = this.rand.nextInt(BOUND);


        //Creating the FileSystem necessary for leaf to run
        FileSystem.fileStorage = new Space(size+1);

        //Creating leaf legally
        Leaf leaf = null;
        try {
            leaf = new Leaf(name,size);
            assertEquals(size,leaf.size);
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

        FileSystem.fileStorage = null;


    }


    /**
     * This function will check if the invalid creation of a leaf actually throws OutOfSpaceException
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     */
    @Test(expected = OutOfSpaceException.class)
    public void invalidCreate() throws OutOfSpaceException {
        try {
            //Establishing name and size of the leaf
            String name = "LeafName";
            int size = this.rand.nextInt(this.BOUND) + 1;

            //Creating the FileSystem necessary for leaf to run
            FileSystem.fileStorage = new Space(size + 1);

            //Creating leaf ilegally (size bigger then limit)
            Leaf leaf = null;
            leaf = new Leaf(name, size * 4);
            FileSystem.fileStorage = null;
        }
        catch (Exception e)
        {

            if(e instanceof OutOfSpaceException)
                throw e;
            assertTrue(false);
        }
    }


    /**
     * This function will check of the path of the leaf is correct
     */
    @Test
    public void checkPath()
    {
        int depth = this.rand.nextInt(this.BOUND) + 1;
        //Establishing name and size of the leaf
        String name = "LeafName";
        int size = this.rand.nextInt(this.BOUND) + 1;

        //Creating the FileSystem necessary for leaf to run
        FileSystem.fileStorage = new Space(size+1);

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
            tree = new Tree(treeName+" "+i);
            //tree.children.put(prev.name,prev);
            prev.parent = tree;
            tree.depth = prev.depth - 1;
            prev = tree;
            if(prev instanceof Tree)
                trees.add(0,(Tree)prev);
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
        FileSystem.fileStorage = null;
    }


    /**
     * This function checks the creation of a leaf before the creation of the FileSystem
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     */
    @Test (expected = NullPointerException.class)
    public void prematureCreation() throws OutOfSpaceException {
        int size = this.rand.nextInt(this.BOUND) + 1;
        Leaf leaf = new Leaf("name",size);
    }



    /**
     * This function will initialize the Random variable
     */
    @Before
    public void initialize() {
        rand = new Random();
    }


    /**
     * This function will reset the space instance
     */
    @After
    public void clean()
    {
        FileSystem.fileStorage = null;
    }
}
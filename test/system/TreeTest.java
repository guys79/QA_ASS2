package system;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * This class will test the Tree class
 */
public class TreeTest {

    private Random rand;//The random variable
    private final int BOUND = 100; //The maximal random number


    @Before
    public void initialize() {
        rand = new Random();

    }

    @Test
    /**
     * This function will test if the name of the tree is the same as given
     */
    public void getName() {
        //Establishing name and size of the tree
        String name = "TreeName";

        Tree tree = new Tree(name);
        assertEquals(tree.name, name);

    }


    @Test
    /**
     * This function will check of the path of the tree is correct
     */
    public void checkPath() {
        int depth = rand.nextInt(BOUND);
        //Establishing name and size of the leaf
        String name = "UniqueTreeName";

        //Creating tree
        Tree testedTree = new Tree(name);

        //Creaing branch in size of 'depth'
        Tree tree;
        Node prev = testedTree;
        testedTree.depth = depth + 1;
        String treeName = "treeName";
        List<Tree> trees = new ArrayList<>();
        for (int i = 0; i < depth; i++) {
            tree = new Tree(treeName + " "+i);
            prev.parent = tree;
            tree.depth = prev.depth - 1;
            prev = tree;
            if (prev instanceof Tree)
                trees.add(0,(Tree) prev);
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
    
    @Test
    public void getChildByNameExistWhenLeaf() throws OutOfSpaceException {
        try {
            int fileSize = this.rand.nextInt(this.BOUND) + 1;
            int spaceSize = fileSize + 1 + this.rand.nextInt(BOUND);
            FileSystem.fileStorage = new Space(spaceSize);


            //Number of children in test
            int numOfChildren = rand.nextInt(this.BOUND) + 1;

            //Establishing name and size of the leaf
            String name = "FileName";

            //Creating tree
            Tree tree = new Tree(name);
            //Creting leaf
            Leaf leaf = new Leaf(name, fileSize);

            //Creating the list of children that we want to check
            List<Node> childrenToCheck = new ArrayList<>();

            //Creating children
            for (int i = 0; i < numOfChildren; i++) {
                childrenToCheck.add(new Tree(name + i));
            }
            childrenToCheck.add(leaf);

            //Setting the children as the children of the tree
            Node child;
            for (int i = 0; i < childrenToCheck.size(); i++) {
                child = childrenToCheck.get(i);
                tree.children.put(child.name, child);
            }


            //Searching for all of the children and assert the result
            Node resChild;
            for (int i = 0; i < childrenToCheck.size(); i++) {
                child = childrenToCheck.get(i);
                resChild = tree.GetChildByName(child.name);
                assertEquals(child, resChild);
            }
            FileSystem.fileStorage = null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            assertTrue(false);
        }
    }


    @Test
    /**
     * This function will check if the function 'getChildByName' works if the child exists
     */
    public void getChildByNameExist()
    {
        //Number of children in test
        int numOfChildren = rand.nextInt(this.BOUND) + 1;

        //Establishing name and size of the leaf
        String name = "TreeName";

        //Creating tree
        Tree tree = new Tree(name);

        //Creating the list of children that we want to check
        List<Tree> childrenToCheck = new ArrayList<>();

        //Creating children
        for(int i=0;i<numOfChildren;i++)
        {
            childrenToCheck.add(new Tree(name+i));
        }

        //Setting the children as the children of the tree
        Node child;
        for(int i=0;i<childrenToCheck.size();i++)
        {
            child = childrenToCheck.get(i);
            tree.children.put(child.name,child);
        }


        //Searching for all of the children and assert the result
        Node resChild;
        for(int i=0;i<childrenToCheck.size();i++)
        {
            child = childrenToCheck.get(i);
            resChild = tree.GetChildByName(child.name);
            assertEquals(child,resChild);
        }



    }


    @Test
    /**
     * This function will check if the function 'getChildByName' works if the child doesn't exist (and it's properties)
     */
    public void getChildByNameNotExist()
    {
        //Number of children in test
        int numOfChildren = rand.nextInt(this.BOUND) + 1;

        //Establishing name and size of the leaf
        String name = "TreeName";

        //Creating tree
        Tree tree = new Tree(name);

        //Creating the list of children names that we want to check

        List<String> childrenNameToCheck = new ArrayList<>();

        //Creating children
        for(int i=0;i<numOfChildren;i++)
        {
            childrenNameToCheck.add(name+i);
        }

        //Getting the new Trees from the function
        List<Tree> childrenToCheck = new ArrayList<>();
        Tree child;
        String cName;
        for(int i=0;i<childrenNameToCheck.size();i++)
        {
            cName = childrenNameToCheck.get(i);
            //Check that the child is not in the array
            assertTrue(tree.children.get(cName) == null);
            //Get the child
            child = (Tree)tree.GetChildByName(cName);
            //Check the depth of child
            assertEquals(child.depth,tree.depth+1);
            //Check child occurrence in map
            assertEquals(tree.children.get(cName),child);
            childrenToCheck.add(child);

        }


        //Searching for all of the children and assert the result
        Node resChild;
        for(int i=0;i<childrenToCheck.size();i++)
        {
            child = childrenToCheck.get(i);
            resChild = tree.GetChildByName(child.name);
            assertEquals(child,resChild);
        }

    }

    @After
    public void clean()
    {
        FileSystem.fileStorage = null;
    }
}






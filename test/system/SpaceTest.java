package system;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * This class is responsible to test the Space class
 */
public class SpaceTest {

    private Random rand;//The random variable
    private final int BOUND = 100; //The maximal random number


    /**
     * This function will initialize the Random variable
     */
    @Before
    public void initialize() {
        rand = new Random();
    }


    /**
     * This function checks the creation of a Space instance
     */
    @Test
    public void checkCreation()
    {
        int size = rand.nextInt(this.BOUND) + 1;
        Space space = new Space(size);
        assertEquals(size,space.countFreeSpace());
        Leaf [] blocks = space.getAlloc();
        assertEquals(size,blocks.length);

    }


    /**
     * This function will check the countFreeSpace function
     * This function will create multiple instances of files in variety of sizes
     * And will check the number of free space after each allocation
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     */
    @Test
    public void allocFreeSpace() throws OutOfSpaceException {
        //For a random number of iterations
        int iter = rand.nextInt(this.BOUND) + 1;
        int [] sizes = new int[iter];
        int spaceSize =0;
        //Create random file sizes
        for(int k= 0;k<sizes.length;k++) {
            sizes[k] = rand.nextInt(this.BOUND) + 1;
            spaceSize+=sizes[k];
        }
        Leaf leaf;

        String fileName = "fileName";

        FileSystem.fileStorage = new Space(spaceSize);
        Space space = FileSystem.fileStorage;
        int sum = 0;
        //Check the allocation of the random files
        for(int i=0;i<iter;i++) {
            leaf = new Leaf(fileName, sizes[i]);
            sum +=sizes[i];
            assertEquals(space.countFreeSpace(), spaceSize - sum);


        }


    }

    /**
     * This function will check the allocation procedure
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     */
    @Test
    public void allocCheck() throws OutOfSpaceException {
        //For a random number of iterations
        int iter = rand.nextInt(this.BOUND) + 1;
        int [] sizes = new int[iter];
        int spaceSize =0;
        //Create random file sizes
        for(int k= 0;k<sizes.length;k++) {
            sizes[k] = rand.nextInt(this.BOUND) + 1;
            spaceSize+=sizes[k];
        }
        Leaf leaf;

        String fileName = "fileName";
        spaceSize = spaceSize + 1 +rand.nextInt(BOUND);
        FileSystem.fileStorage = new Space(spaceSize);
        Space space = FileSystem.fileStorage;

        //Check the allocation of the random files
        for(int i=0;i<iter;i++) {
            leaf = new Leaf(fileName, sizes[i]);


            Leaf[] blocks = space.getAlloc();
            int count = 0;
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[j] == leaf)
                    count++;
            }

            assertEquals(count, sizes[i]);


        }


    }


    /**
     * This function will check the case when we will try to allocate space when there is no more
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     */
    @Test(expected = OutOfSpaceException.class)
    public void allocCheckWhenFull() throws OutOfSpaceException {
        try {
            int fileSize = this.rand.nextInt(this.BOUND) + 1;
            int spaceSize = 2 * fileSize - fileSize / 2 - 1;
            String fileName = "fileName";
            FileSystem.fileStorage = new Space(spaceSize);
            Space space = FileSystem.fileStorage;
            Leaf leaf;
            try {
                leaf = new Leaf(fileName, fileSize);
            } catch (Exception e) {
                assertTrue(false);
            }

            leaf = new Leaf(fileName, fileSize);
        }
        catch(Exception e)
        {
            if(e instanceof OutOfSpaceException)
                throw e;
            assertTrue(false);
        }


    }

    /**
     * This function will assignn the given root tree as the parent of all the random files this function will create
     * @param root - The root Tree
     * @return - The random files
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     */
    private RandomLeafCreation allocRandomFiles(Tree root) throws OutOfSpaceException {
        RandomLeafCreation randomLeafCreation = allocRandomFiles();
        Set<Leaf> files= randomLeafCreation.getFiles().keySet();
        for(Leaf file : files)
        {
            file.parent = root;
        }
        return randomLeafCreation;
    }

    /**
     * This function creates and returns files in different sizes
     * @return - The files
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     */
    private RandomLeafCreation allocRandomFiles() throws OutOfSpaceException {
        //For a random number of iterations
        int iter = rand.nextInt(this.BOUND) + 1;
        int [] sizes = new int[iter];
        Leaf [] files = new Leaf[iter];
        int spaceSize =0;
        //Create random file sizes
        for(int k= 0;k<sizes.length;k++) {
            sizes[k] = rand.nextInt(this.BOUND) + 1;
            spaceSize+=sizes[k];
        }
        Leaf leaf;

        String fileName = "fileName";
        spaceSize = spaceSize + 1 +rand.nextInt(BOUND);
        FileSystem.fileStorage = new Space(spaceSize);
        Space space = FileSystem.fileStorage;
        int sum = 0;
        //Check the allocation of the random files
        for(int i=0;i<iter;i++) {
            leaf = new Leaf(fileName, sizes[i]);
            files[i] = leaf;

            Leaf[] blocks = space.getAlloc();
            int count = 0;
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[j] == leaf)
                    count++;
            }

            assertEquals(count, sizes[i]);


        }
        return new RandomLeafCreation(files,sizes,space);
    }


    /**
     * This function will check the countFreeSpace function after the use of Dealloc
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     */
    @Test
    public void deallocFreeSpace() throws OutOfSpaceException {
        Tree root =  new Tree("root");
        RandomLeafCreation randomLeafCreation = allocRandomFiles(root);
        HashMap<Leaf,Integer> files = randomLeafCreation.getFiles();
        Space space = randomLeafCreation.getSpace();
        int freeSpace = space.countFreeSpace();
        int spaceSize;
        for(Map.Entry<Leaf,Integer> file : files.entrySet())
        {
            space.Dealloc(file.getKey());
            spaceSize = space.countFreeSpace();
            assertEquals(freeSpace + file.getValue(),spaceSize);
            freeSpace = spaceSize;
        }
        FileSystem.fileStorage = null;

    }


    /**
     * This function will check the dealloc function (normal behavior)
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     */
    @Test
    public void deallocCheck() throws OutOfSpaceException {
        Tree root =  new Tree("root");
        RandomLeafCreation randomLeafCreation = allocRandomFiles(root);
        HashMap<Leaf,Integer> files = randomLeafCreation.getFiles();
        Space space = randomLeafCreation.getSpace();
        Leaf [] allocs;
        for(Map.Entry<Leaf,Integer> file : files.entrySet())
        {
            space.Dealloc(file.getKey());
            allocs = space.getAlloc();
            for(int j=0;j<allocs.length;j++)
            {
                assertTrue(allocs[j] != file.getKey());
            }
        }
        FileSystem.fileStorage = null;
    }



    /**
     * This function checks the behavior of the dealloc function when empty
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     */
    @Test
    public void deallocCheckWhenEmpty() throws OutOfSpaceException {
        int fileSize = rand.nextInt(this.BOUND) + 1;
        Tree root =  new Tree("root");
        int spaceSize = fileSize;
        String fileName = "fileName";
        FileSystem.fileStorage = new Space(spaceSize);
        Space space = FileSystem.fileStorage;
        Leaf leaf = new Leaf(fileName,fileSize);
        leaf.parent = root;
        assertEquals(0,space.countFreeSpace());
        space.Dealloc(leaf);
        assertEquals(spaceSize,space.countFreeSpace());
        Leaf [] prev = space.getAlloc().clone();
        space.Dealloc(leaf);
        Leaf [] after = space.getAlloc().clone();
        assertEquals(spaceSize,space.countFreeSpace());
        for(int i=0;i<prev.length;i++)
        {
            assertEquals(after[i],prev[i]);
        }
        FileSystem.fileStorage = null;
    }


    /**
     * This function will check the behavior of the Alloc and Dealloc when used on after the other
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     */
    @Test
    public void allocAndDealloc() throws OutOfSpaceException {
        int fileSize = rand.nextInt(100);
        int spaceSize = fileSize+1;
        Tree root = new Tree("root");
        FileSystem.fileStorage = new Space(spaceSize);
        Space space = FileSystem.fileStorage;
        String name = "fileName";
        Leaf file1 = new Leaf(name,fileSize);
        file1.parent = root;
        space.Dealloc(file1);
        Leaf file2 = new Leaf(name+"2",fileSize);
        file2.parent = root;
        space.Dealloc(file1);

        Leaf [] allocs = space.getAlloc();
        int count =0;
        for(int i=0;i<allocs.length;i++) {
            if (allocs[i] == file2)
                count++;
        }
        assertEquals(count,fileSize);
        assertEquals(spaceSize-fileSize,space.countFreeSpace());
    }


    /**
     * This function will reset the space instance
     */
    @After
    public void clean()
    {
        FileSystem.fileStorage = null;
    }

    /**
     * This class contains the information of random files and their sizes (as well as the space)
     */
    private static class RandomLeafCreation
    {
        private HashMap<Leaf,Integer> files;//The files - sizes dictionary
        private Space space;//The space

        /**
         * The contructor of the class
         * @param files - The files
         * @param sizes - The sizes of the files
         * @param space - The space
         */
        public RandomLeafCreation(Leaf [] files,int []sizes, Space space)
        {
            this.files = new HashMap<>();
            for(int i=0;i<sizes.length;i++)
            {
                this.files.put(files[i],sizes[i]);
            }
            this.space = space;
        }

        /**
         * This function will return the file - size dictionary
         * @return - The file - size dictionary
         */
        public HashMap<Leaf, Integer> getFiles() {
            return files;
        }

        /**
         * This function will return the space
         * @return - The space
         */
        public Space getSpace() {
            return space;
        }
    }

}
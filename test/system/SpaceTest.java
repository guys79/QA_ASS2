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

public class SpaceTest {

    private Random rand;//The random variable
    private final int BOUND = 100; //The maximal random number
    @Before
    public void initialize() {
        rand = new Random();
    }

    @Test
    public void checkCreation()
    {
        int size = rand.nextInt(BOUND);
        Space space = new Space(size);
        assertEquals(size,space.countFreeSpace());
        Leaf [] blocks = space.getAlloc();
        assertEquals(size,blocks.length);

    }

    @Test
    public void allocFreeSpace() throws OutOfSpaceException {
        //For a random number of iterations
        int iter = rand.nextInt(BOUND);
        int [] sizes = new int[iter];
        int spaceSize =0;
        //Create random file sizes
        for(int k= 0;k<sizes.length;k++) {
            sizes[k] = rand.nextInt(BOUND);
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
    @Test
    public void allocCheck() throws OutOfSpaceException {
        //For a random number of iterations
        int iter = rand.nextInt(BOUND);
        int [] sizes = new int[iter];
        int spaceSize =0;
        //Create random file sizes
        for(int k= 0;k<sizes.length;k++) {
            sizes[k] = rand.nextInt(BOUND);
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


               Leaf[] blocks = space.getAlloc();
            int count = 0;
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[j] == leaf)
                    count++;
            }

            assertEquals(count, sizes[i]);


        }


    }
    @Test(expected = OutOfSpaceException.class)
    public void allocCheckWhenFull() throws OutOfSpaceException {
        int fileSize = this.rand.nextInt(this.BOUND);
        int spaceSize = 2*fileSize - fileSize/2 -1;
        String fileName = "fileName";
       FileSystem.fileStorage = new Space(spaceSize);
       Space space = FileSystem.fileStorage;
        Leaf leaf;
       try
       {
           leaf = new Leaf(fileName,fileSize);
       }
       catch (Exception e)
       {
           assertTrue(false);
       }

       leaf = new Leaf(fileName,fileSize);



    }

    private RandomLeafCreation allocRandomFiles(Tree root) throws OutOfSpaceException {
        RandomLeafCreation randomLeafCreation = allocRandomFiles();
        Set<Leaf> files= randomLeafCreation.getFiles().keySet();
        for(Leaf file : files)
        {
            file.parent = root;
        }
        return randomLeafCreation;
    }
    private RandomLeafCreation allocRandomFiles() throws OutOfSpaceException {
        //For a random number of iterations
        int iter = rand.nextInt(BOUND);
        int [] sizes = new int[iter];
        Leaf [] files = new Leaf[iter];
        int spaceSize =0;
        //Create random file sizes
        for(int k= 0;k<sizes.length;k++) {
            sizes[k] = rand.nextInt(BOUND);
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

    @Test
    public void deallocCheck() {
    }
    @Test
    public void deallocCheckWhenEmpty() {
    }
    @Test
    public void countFreeSpace() {
    }

    @Test
    public void getAlloc() {
    }
    @After
    public void clean()
    {
        FileSystem.fileStorage = null;
    }
    private static class RandomLeafCreation
    {
        private HashMap<Leaf,Integer> files;
        private Space space;
        public RandomLeafCreation(Leaf [] files,int []sizes, Space space)
        {
            this.files = new HashMap<>();
            for(int i=0;i<sizes.length;i++)
            {
                this.files.put(files[i],sizes[i]);
            }
            this.space = space;
        }

        public HashMap<Leaf, Integer> getFiles() {
            return files;
        }

        public Space getSpace() {
            return space;
        }
    }

}
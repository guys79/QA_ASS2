package system;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.nio.file.DirectoryNotEmptyException;
import java.util.*;
import static org.junit.Assert.*;

/**
 * This class is responsible for testing the FileSystem class
 */
public class FileSystemTest {

    private Random rand;//The Random variable
    private final int BOUND = 100;//The upper bound of the random variable



    /**
     * This function will initialize the Random variable
     */
    @Before
    public void Initialization()
    {
        this.rand = new Random();
    }


    /**
     * This function check the behavior of the dir function when creating same dir twice
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     */
    @Test
    public void creatingDirTwice() throws BadFileNameException{
        int parents = rand.nextInt(this.BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize + 1 + rand.nextInt(BOUND);
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 1];
        path[0] = "root";
        String name = "dirname";
        for(int i=1;i<path.length;i++)
        {
            path[i] = name + i;
        }

        fileSystem.dir(path);
        fileSystem.dir(path);

    }

    /**
     * This function will create a directory and then a file with the same path
     * The function should throw BadFileNameException
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown - In the process of creating the files an OutOfSpaceException might be thrown
     */
    @Test(expected = BadFileNameException.class)
    public void creatingDirAndThenFile() throws BadFileNameException, OutOfSpaceException {

        int parents = rand.nextInt(this.BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize + 1 + rand.nextInt(BOUND);
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 1];
        path[0] = "root";
        String name = "dirname";
        for(int i=1;i<path.length;i++)
        {
            path[i] = name + i;
        }


        fileSystem.dir(path);
        fileSystem.file(path,fileSize);
    }

    /**
     * This function will create a file and then will crate a directory with the same path
     * The function should throw BadFileNameException
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     */
    @Test(expected = BadFileNameException.class)
    public void creatingFileAndThenDir() throws BadFileNameException, OutOfSpaceException {

        int parents = rand.nextInt(this.BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize + 1 + rand.nextInt(BOUND);
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 1];
        path[0] = "root";
        String name = "dirname";
        for(int i=1;i<path.length;i++)
        {
            path[i] = name + i;
        }

        fileSystem.file(path,fileSize);
        fileSystem.dir(path);
    }

    /**
     * This function checks the behavior of a normal directory creation (dir function)
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     */
    @Test
    public void normalDirCreation() throws BadFileNameException {
        int parents = rand.nextInt(this.BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize + 1 + rand.nextInt(BOUND);
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 1];
        path[0] = "root";
        String name = "dirname";
        for(int i=1;i<path.length;i++)
        {
            path[i] = name + i;
        }

        fileSystem.dir(path);
        Tree dir = fileSystem.DirExists(path);
        assertTrue(dir!=null);
        assertEquals(dir.name,path[path.length-1]);
    }

    /**
     * This function will try to crate a directory with no "root" dir as the first directory in the path
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     */
    @Test(expected = BadFileNameException.class)
    public void noRootDirCreation() throws BadFileNameException {
        int parents = rand.nextInt(this.BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize + 1 + rand.nextInt(BOUND);
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 1];
        path[0] = "noRoot";
        String name = "dirname";
        for(int i=1;i<path.length;i++)
        {
            path[i] = name + i;
        }
        fileSystem.dir(path);
    }

    /**
     * This function will check a normal deletion case of a dir
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     * @throws DirectoryNotEmptyException - In the process of deleting directory, if the directory contains a child, a DirectoryNotEmptyException 
     */
    @Test
    public void checkDirDelete() throws BadFileNameException, DirectoryNotEmptyException {
        int parents = rand.nextInt(this.BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize + 1 + rand.nextInt(this.BOUND) + 1;
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 1];
        path[0] = "root";
        String name = "dirname";
        for(int i=1;i<path.length;i++)
        {
            path[i] = name + i;
        }

        fileSystem.dir(path);
        fileSystem.rmdir(path);
        Tree res = fileSystem.DirExists(path);
        assertTrue(res == null);
    }

    /**
     * This function will check the behavior of the deletion function when the disk is empty
     * @throws DirectoryNotEmptyException - In the process of deleting directory, if the directory contains a child, a DirectoryNotEmptyException
     */
    @Test
    public void checkDirDeleteWhenDiskEmpty() throws DirectoryNotEmptyException {
        int parents = rand.nextInt(this.BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND);
        int spaceSize = fileSize + 1 + rand.nextInt(this.BOUND) + 1;
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 1];
        path[0] = "root";
        String name = "dirname";
        for(int i=1;i<path.length;i++)
        {
            path[i] = name + i;
        }

        fileSystem.rmdir(path);

        Tree res = fileSystem.DirExists(path);
        assertTrue(res == null);
    }

    /**
     * This function will check the deletion of a directory when it contains at least one child
     * This function should throw DirectoryNotEmptyException
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     * @throws DirectoryNotEmptyException - In the process of deleting directory, if the directory contains a child, a DirectoryNotEmptyException
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     */
    @Test(expected = DirectoryNotEmptyException.class)
    public void checkDirDeleteWithChild() throws BadFileNameException, DirectoryNotEmptyException, OutOfSpaceException {
        int parents = rand.nextInt(this.BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND);
        int spaceSize = fileSize + 1 + rand.nextInt(BOUND);
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 2];

        path[0] = "root";
        String name = "dirname";
        for(int i=1;i<path.length - 1;i++)
        {
            path[i] = name + i;
        }
        path[path.length-1] = "fileName";


        fileSystem.file(path,fileSize);
        int index = rand.nextInt(path.length-1);
        String [] dirPath = new String[index+1];
        for(int i=0;i<dirPath.length;i++)
        {
            dirPath[i] = path[i];
        }
        fileSystem.rmdir(dirPath);
        Tree res = fileSystem.DirExists(path);
        assertTrue(res != null);
    }

    /**
     * This function will check the behavior of a deletion of a directory that doesn't exist
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     * @throws DirectoryNotEmptyException - In the process of deleting directory, if the directory contains a child, a DirectoryNotEmptyException
     */
    @Test
    public void checkDirDeleteForNonExistedDir() throws BadFileNameException, DirectoryNotEmptyException {
        int parents = rand.nextInt(BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize + 1 + rand.nextInt(BOUND);
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 1];
        path[0] = "root";
        String name = "dirname";
        for(int i=1;i<path.length;i++)
        {
            path[i] = name + i;
        }

        fileSystem.dir(path);
        String temp = path[1];
        path[1] = "test";
        fileSystem.rmdir(path);
        path[1] = temp;
        Tree res = fileSystem.DirExists(path);

        assertTrue(res != null);
        assertEquals(res.name,path[path.length-1]);
    }

    /**
     * This function will return the root node in a given FileSystem
     * @param fileSystem - The given FileSystem
     * @return - The root Node of the FileSystem
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     * @throws BadFileNameException - In the process of creating the file an BadFileNameException might be thrown
     */
    private Node getRoot(FileSystem fileSystem) throws OutOfSpaceException, BadFileNameException {

        String [] path = new String[3];
        path[0] = "root";
        path[1] = "uniquDirname";
        path[2] = "fileName";

        //Assuming file creation and fileExist work (there is a test for that)
        fileSystem.file(path,1);
        Leaf file = fileSystem.FileExists(path);
        int count =0;
        Node curr = file;
        while(curr.parent != null)
        {

            assertTrue(count<3);
            count++;
            curr  = curr.parent;

        }
        assertEquals(curr.name,path[0]);
        return curr;

    }

    /**
     * This function will check the dirExists function (when the directory exists)
     * @throws BadFileNameException
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     */
    @Test
    public void dirExists() throws BadFileNameException, OutOfSpaceException {
        int parents = rand.nextInt(this.BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize + 1 + rand.nextInt(BOUND);
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 2];
        path[0] = "root";
        String name = "dirname";
        for(int i=1;i<path.length-1;i++)
        {
            path[i] = name + i;
        }
        path[path.length-1] = "fileName";

        //Assuming file creation works (there is a test for that)
        fileSystem.dir(path);


        Node curr = getRoot(fileSystem);
        if(curr.name.equals("root") && curr instanceof Tree && ((Tree)curr).children.containsKey("root"))
            curr = ((Tree)curr).children.get("root");
        Tree workingTree;
        HashMap<String, Node> children;
        boolean found;
        for(int i=0;i<path.length - 1;i++){

            assertTrue(curr instanceof Tree);
            workingTree = (Tree) curr;
            children = workingTree.children;
            found  = false;
            for(Map.Entry<String,Node> child: children.entrySet())
            {
                if(child.getKey().equals(path[i+1]))
                {
                    curr = child.getValue();
                    found = true;
                    break;
                }
            }

            assertTrue(found);

        }

        assertTrue(curr instanceof Tree);
        assertEquals(curr.name,path[path.length-1]);

    }

    /**
     * This function will check the creation of the class
     */
    @Test
    public void creation()
    {
        int spaceSize = rand.nextInt(this.BOUND) + 1;
        FileSystem fileSystem = new FileSystem(spaceSize);
        assertEquals(FileSystem.fileStorage.countFreeSpace(),spaceSize);
    }

    /**
     * This function will check a normal creation of a file (file function)
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     */
    @Test
    public void correctFile() throws OutOfSpaceException, BadFileNameException {
        //Creating the file
        int parents = rand.nextInt(this.BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize + 1 + rand.nextInt(this.BOUND) + 1;
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 1];
        path[0] = "root";
        String name = "dirname";
        for(int i=1;i<path.length-1;i++)
        {
            path[i] = name + i;
        }
        path[path.length-1] = "fileName";
        fileSystem.file(path,fileSize);

        //Checking the allocations
        Space space = FileSystem.fileStorage;
        Leaf [] allocs = space.getAlloc();
        Leaf res = null;
        for(int i=0;i<allocs.length;i++)
        {
            if(allocs[i] != null && allocs[i].name.equals(path[path.length-1]))
                res = allocs[i];
        }
        assertTrue(res!=null);

        //Checking the path of the file
        String [] resPath = res.getPath();
        assertEquals(resPath.length,path.length);
        for(int i=0;i<path.length;i++)
        {
            assertEquals(resPath[i],path[i]);
        }

        //Checking the given path
        Node parent = res;
        int index = path.length -1;
        while(parent!=null)
        {
            if(index == -1)
            {
                assertEquals(parent.name,"root");
                assertEquals(parent.parent,null);
                break;
            }


            assertEquals(parent.name,path[index]);
            index -- ;
            parent = parent.parent;
        }
    }

    /**
     * This function will try to create a file with no root dir
     * the function should throw BadFileNameException
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     * @throws BadFileNameException - In the process of creating the files an BadFileNameException might be thrown
     */
    @Test (expected = BadFileNameException.class)
    public void noRootFile() throws OutOfSpaceException, BadFileNameException {
        int parents = rand.nextInt(this.BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize + 1 + rand.nextInt(BOUND);
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 1];
        path[0] = "root2";
        String name = "dirname";
        for(int i=1;i<path.length-1;i++)
        {
            path[i] = name + i;
        }
        path[path.length-1] = "fileName";
        fileSystem.file(path,fileSize);
    }

    /**
     * This function checks the attempt to create a file when there is no space left (and no file to delete)
     * The function should throw OutOfSpaceException
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     * @throws BadFileNameException - In the process of creating the files an BadFileNameException might be thrown
     */
    @Test(expected = OutOfSpaceException.class)
    public void noSpaceNoFileToReplace() throws OutOfSpaceException, BadFileNameException {

        int parents = rand.nextInt(this.BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize - 1;
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 1];
        path[0] = "root";
        String name = "dirname";
        for(int i=1;i<path.length-1;i++)
        {
            path[i] = name + i;
        }
        path[path.length-1] = "fileName";
        fileSystem.file(path,fileSize);
    }

    /**
     * This function will check a normal use of lsDir
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     */
    @Test
    public void normalLsDir() throws OutOfSpaceException, BadFileNameException {
        int numOfFiles = 1 + rand.nextInt(BOUND);
        int parents = rand.nextInt(this.BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize * numOfFiles + 1 + this.rand.nextInt(BOUND);
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 2];
        String [] dirPath = new String[parents + 1];
        path[0] = "root";
        dirPath[0] = "root";
        String name = "dirname";
        Set<String> names = new HashSet<>();
        for(int i=1;i<path.length-1;i++)
        {
            path[i] = name + i;
            dirPath[i] = path[i];
        }
        int coinFlip = 0;
        for(int i= 0;i<numOfFiles;i++)
        {

            coinFlip = rand.nextInt(2);
            if(coinFlip == 0) {
                path[path.length - 1] = "filename" + i;
                fileSystem.file(path, fileSize);

            }
            else
            {
                path[path.length - 1] = "dirname" + i;
                fileSystem.dir(path);
            }
            names.add(path[path.length - 1]);
        }

        String [] res = fileSystem.lsdir(dirPath);
        assertEquals(numOfFiles,res.length);
        for(int i=0;i<res.length;i++)
        {
            assertTrue(names.contains(res[i]));
        }

    }

    /**
     * This function will check the behavior of the lsDir function when the given path is to a file
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     * @throws BadFileNameException
     */
    @Test
    public void lsDirPathToFile() throws OutOfSpaceException, BadFileNameException {
        int numOfFiles = 1 + rand.nextInt(BOUND);
        int parents = rand.nextInt(this.BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize * numOfFiles + 1 + this.rand.nextInt(BOUND);
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 2];
        String [] dirPath = new String[parents + 1];
        path[0] = "root";
        dirPath[0] = "root";
        String name = "dirname";
        Set<String> names = new HashSet<>();
        for(int i=1;i<path.length-1;i++)
        {
            path[i] = name + i;
            dirPath[i] = path[i];
        }
        int coinFlip = 0;
        for(int i= 0;i<numOfFiles-1;i++)
        {

            coinFlip = rand.nextInt(2);
            if(coinFlip == 0) {
                path[path.length - 1] = "filename" + i;
                fileSystem.file(path, fileSize);

            }
            else
            {
                path[path.length - 1] = "dirname" + i;
                fileSystem.dir(path);
            }
            names.add(path[path.length - 1]);
        }
        path[path.length - 1] = "filename" + (numOfFiles - 1);
        fileSystem.file(path, fileSize);
        String [] res = fileSystem.lsdir(path);
        assertTrue(res == null);


    }

    /**
     * This function will check the behavior of lsDir before and after deletion
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     * @throws DirectoryNotEmptyException - In the process of deleting directory, if the directory contains a child, a DirectoryNotEmptyException
     */
    @Test
    public void lsDirAfterDelete() throws OutOfSpaceException, BadFileNameException, DirectoryNotEmptyException {
        int numOfFiles = 1 + rand.nextInt(BOUND);
        int parents = rand.nextInt(this.BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize * numOfFiles + 1 + this.rand.nextInt(BOUND);
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 2];
        String [] dirPath = new String[parents + 1];
        path[0] = "root";
        dirPath[0] = "root";
        String name = "dirname";
        Set<String> names = new HashSet<>();
        for(int i=1;i<path.length-1;i++)
        {
            path[i] = name + i;
            dirPath[i] = path[i];
        }
        int coinFlip = 0;
        int index = rand.nextInt(numOfFiles);
        String nameToDelete = "";
        for(int i= 0;i<numOfFiles;i++)
        {

            coinFlip = rand.nextInt(2);
            if(coinFlip == 0) {
                path[path.length - 1] = "filename" + i;
                fileSystem.file(path, fileSize);

            }
            else
            {
                path[path.length - 1] = "dirname" + i;
                fileSystem.dir(path);
            }
            names.add(path[path.length - 1]);
            if(index == i)
            {
                nameToDelete = path[path.length - 1];
            }
        }

        path[path.length-1] = nameToDelete;
        if(nameToDelete.contains("dir"))
            fileSystem.rmdir(path);
        else
            fileSystem.rmfile(path);
        String [] res = fileSystem.lsdir(dirPath);
        assertEquals(numOfFiles - 1,res.length);
        for(int i=0;i<res.length;i++)
        {
            assertTrue(names.contains(res[i]));
        }

    }

    /**
     * This function will check the behavior of the lsDir function when there are no children
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     */
    @Test
    public void lsDirNoChildren() throws BadFileNameException {
        int numOfFiles = 1 + rand.nextInt(BOUND);
        int parents = rand.nextInt(this.BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize * numOfFiles + 1 + this.rand.nextInt(BOUND);
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] dirPath = new String[parents + 1];
        dirPath[0] = "root";
        String name = "dirname";
        for(int i=1;i<dirPath.length;i++)
        {

            dirPath[i] = name + i;
        }

        fileSystem.dir(dirPath);

        String [] res = fileSystem.lsdir(dirPath);
        assertEquals(res.length,0);
    }

    /**
     * This function will check the creation of a file when there is not enough space, but there is another file to replace him with
     * @param fileSize - The size of the file
     * @param spaceSize - The size of the FileSystem space
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     */
    private void noSpaceExistFileToReplaceGeneric(int fileSize,int spaceSize) throws OutOfSpaceException, BadFileNameException {

        int parents = rand.nextInt(BOUND) + 2;
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 1];
        path[0] = "root";
        String name = "dirname";
        for(int i=1;i<path.length-1;i++)
        {
            path[i] = name + i;
        }
        path[path.length-1] = "fileName";
        fileSystem.file(path,fileSize);
        Leaf firstLeaf = fileSystem.FileExists(path);
        assertTrue(firstLeaf!=null);


        fileSystem.file(path,fileSize);
        Leaf secondLeaf = fileSystem.FileExists(path);
        assertEquals(secondLeaf.name,firstLeaf.name);
        assertTrue(secondLeaf!=firstLeaf);


    }

    /**
     * This function checks the case when there is not enough space,
     * And the space provided by deleting the existing file will allow the creation of the file
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     */
    @Test
    public void noSpaceExistFileToReplace() throws OutOfSpaceException, BadFileNameException {
        int fileSize = rand.nextInt(this.BOUND) + 2;
        int spaceSize = (int)(fileSize * 1.5);
        noSpaceExistFileToReplaceGeneric(fileSize,spaceSize);
    }

    /**
     * This function will check the creation of a file with the same path as another existing file
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     */
    @Test
    public void existFileToReplaceWithALotOfSpace() throws OutOfSpaceException, BadFileNameException {
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize * 10;
        noSpaceExistFileToReplaceGeneric(fileSize,spaceSize);
    }

    /**
     * This function checks the case when there is not enough space (spaceSize = fileSize),
     * And the space provided by deleting the existing file will allow the creation of the file
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     */
    @Test
    public void noSpaceExistFileSameSizeOfSpace() throws OutOfSpaceException, BadFileNameException {
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize;
        noSpaceExistFileToReplaceGeneric(fileSize,spaceSize);
    }

    /**
     * This function will check the case where we want to create a file when there
     * is not enough space, but a directory exists in that path (with the same name)
     * This function should throw OutOfSpaceException
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     */
    @Test(expected = OutOfSpaceException.class)
    public void noSpaceExistFileToReplaceButDirGiven() throws OutOfSpaceException, BadFileNameException {
        try {
            int parents = rand.nextInt(this.BOUND) + 1;
            int fileSize = rand.nextInt(this.BOUND) + 1;
            int spaceSize = (int) (fileSize * 1.5);
            if (spaceSize == fileSize) {
                fileSize = 2;
                spaceSize = 3;
            }
            FileSystem fileSystem = new FileSystem(spaceSize);
            String[] path = new String[parents + 1];
            String[] secondPath = new String[path.length - 1];
            path[0] = "root";
            secondPath[0] = "root";
            String name = "dirname";
            for (int i = 1; i < path.length - 1; i++) {
                path[i] = name + i;
                secondPath[i] = path[i];
            }
            path[path.length - 1] = "fileName";


            fileSystem.file(path, fileSize);
            Leaf firstLeaf = fileSystem.FileExists(path);
            assertTrue(firstLeaf != null);


            fileSystem.file(secondPath, fileSize);
        }
        catch (Exception e)
        {
            if(e instanceof OutOfSpaceException)
                throw e;
            assertTrue(false);
        }




    }

    /**
     * This function will check the normal creation of a directory
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     */
    @Test
    public void normalFileDelete() throws OutOfSpaceException, BadFileNameException {

        int parents = rand.nextInt(this.BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize + 1 + rand.nextInt(BOUND);
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 1];
        path[0] = "root";
        String name = "dirname";
        for(int i=1;i<path.length-1;i++)
        {
            path[i] = name + i;
        }
        path[path.length-1] = "fileName";
        fileSystem.file(path,fileSize);
        fileSystem.rmfile(path);
        Leaf res = fileSystem.FileExists(path);
        assertTrue(res == null);

    }

    /**
     * This function will check the behavior of the FileSystem when deleting a file
     * when the disk is empty
     */
    @Test
    public void fileDeleteWhenEmpty() {

        int parents = rand.nextInt(this.BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize + 1 + rand.nextInt(BOUND);
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 1];
        path[0] = "root";
        String name = "dirname";
        for(int i=1;i<path.length-1;i++)
        {
            path[i] = name + i;
        }
        path[path.length-1] = "fileName";
        fileSystem.rmfile(path);
        Leaf res = fileSystem.FileExists(path);
        assertTrue(res == null);
    }

    /**
     * This function will check the behavior of deleting a file that doesn't exists
     */
    @Test
    public void nonExistFileDelete() {

        int parents = rand.nextInt(this.BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize + 1 + rand.nextInt(BOUND);
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 1];
        path[0] = "root";
        String name = "dirname";
        for(int i=1;i<path.length-1;i++)
        {
            path[i] = name + i;
        }
        path[path.length-1] = "fileName";

        fileSystem.rmfile(path);
        Leaf res = fileSystem.FileExists(path);
        assertTrue(res == null);
        assertEquals(FileSystem.fileStorage.countFreeSpace(),spaceSize);

    }

    /**
     * This function will check the behavior of directory deletion when file path is given
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     * @throws DirectoryNotEmptyException - In the process of deleting directory, if the directory contains a child, a DirectoryNotEmptyException
     */
    @Test
    public void dirNameFileDelete() throws OutOfSpaceException, BadFileNameException, DirectoryNotEmptyException {

        int parents = rand.nextInt(BOUND)+2;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize + 1 + rand.nextInt(BOUND);
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 1];
        path[0] = "root";
        String name = "dirname";
        for(int i=1;i<path.length-1;i++)
        {
            path[i] = name + i;
        }
        path[path.length-1] = "fileName";

        fileSystem.file(path,fileSize);
        fileSystem.rmdir(path);
        Leaf file = fileSystem.FileExists(path);
        assertTrue(file!=null);
        assertEquals(file.name,path[path.length-1]);
    }

    /**
     * This function will check the behavior of file deletion when directory path is given
     */
    @Test
    public void fileNameDirDelete()  {
        try {
            int parents = rand.nextInt(BOUND);
            int fileSize = rand.nextInt(this.BOUND) + 1;
            int spaceSize = fileSize + 1 + rand.nextInt(BOUND);
            FileSystem fileSystem = new FileSystem(spaceSize);
            String[] path = new String[parents + 1];
            String[] toDeletePath = new String[parents];
            path[0] = "root";
            toDeletePath[0] = path[0];
            String name = "dirname";
            for (int i = 1; i < path.length - 1; i++) {
                path[i] = name + i;
                toDeletePath[i] = path[i];
            }

            path[path.length - 1] = "dirName";
            fileSystem.dir(path);
            fileSystem.rmfile(path);
            Tree file = fileSystem.DirExists(path);
            assertTrue(file != null);
            assertEquals(file.name, path[path.length - 1]);
        }
        catch (Exception e)
        {

            assertTrue(false);
        }


    }

    /**
     * This function will check the FileExists function
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     */
    @Test
    public void FileExists() throws OutOfSpaceException, BadFileNameException {

        int parents = rand.nextInt(BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize + 1 + rand.nextInt(BOUND);
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 1];
        path[0] = "root";
        String name = "dirname";
        for(int i=1;i<path.length-1;i++)
        {
            path[i] = name + i;
        }
        path[path.length-1] = "fileName";
        fileSystem.file(path,fileSize);

        Leaf res = fileSystem.FileExists(path);
        assertEquals(res.name,path[path.length-1]);
    }

    /**
     * This function will check the FileExists function when the file doesn't exists
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     */
    @Test
    public void fileDoesNotExist() throws OutOfSpaceException, BadFileNameException {

        int parents = rand.nextInt(BOUND) + 1;
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize + 1 + rand.nextInt(BOUND);
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 1];
        path[0] = "root";
        String name = "dirname";
        for(int i=1;i<path.length-1;i++)
        {
            path[i] = name + i;
        }
        path[path.length-1] = "fileName";
        fileSystem.file(path,fileSize);
        path[path.length-1] = "fileName2";
        Leaf res = fileSystem.FileExists(path);
        assertEquals(res,null);
    }

    /**
     * This function will check the FileExists function when the given path is of a directory
     */
    @Test
    public void fileDoesNotExistDirIs() {
        try {
            int parents = rand.nextInt(BOUND) + 1;
            int fileSize = rand.nextInt(this.BOUND) + 1;
            int spaceSize = fileSize + 1 + rand.nextInt(BOUND);
            FileSystem fileSystem = new FileSystem(spaceSize);
            String[] path = new String[parents + 1];
            path[0] = "root";
            path[1] = "test";
            String name = "dirname";
            for (int i = 2; i < path.length - 1; i++) {
                path[i] = name + i;
            }
            String[] newPath = new String[2];
            fileSystem.file(path, fileSize);
            newPath[0] = path[0];
            newPath[1] = path[1];
            Leaf res = fileSystem.FileExists(newPath);
            assertEquals(res, null);
        }
        catch (Exception e)
        {
            assertTrue(false);
        }
    }

    /**
     * This function will check the disk when it's empty
     */
    @Test
    public void emptyDiskCheck(){
        int spaceSize = this.rand.nextInt(BOUND) + 1;
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [][] blocks = fileSystem.disk();
        for(int i=0;i<blocks.length;i++)
        {
            assertTrue(blocks[i] == null);
        }
    }

    /**
     * This function will check the disk after creation and deletion of files and directories
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     */
    @Test
    public void checkDiskDeletionAndCreation() throws OutOfSpaceException, BadFileNameException {
        int fileSize1 = 3;
        int fileSize2 = 1;
        int fileSize3 = 2;
        int spaceSize = fileSize1 + fileSize2 + fileSize3;

        int parents = rand.nextInt(this.BOUND) + 2;
        FileSystem fileSystem = new FileSystem(spaceSize);
        String [] path = new String[parents + 1];
        path[0] = "root";
        String name = "dirname";
        for(int i=1;i<path.length-1;i++)
        {
            path[i] = name + i;
        }


        path[path.length-1] = "filename1";
        fileSystem.file(path,fileSize1);
        Leaf file1 = fileSystem.FileExists(path);

        path[path.length-1] = "filename2";
        fileSystem.file(path,fileSize2);

        path[path.length-1] = "filename3";
        fileSystem.file(path,fileSize3);
        Leaf file3 = fileSystem.FileExists(path);


        path[path.length-1] = "filename2";
        fileSystem.rmfile(path);


        String [][] blocks = fileSystem.disk();
        String fileName;
        for(int i=0;i<blocks.length;i++)
        {
            if(blocks[i]!=null)
            {
                fileName = blocks[i][blocks[i].length-1];
                if(fileName.equals("filename1"))
                {
                    assertTrue(findInArray(file1.allocations,i));
                }
                else
                {
                    if(fileName.equals("filename3"))
                    {
                        assertTrue(findInArray(file3.allocations,i));
                    }
                    else
                    {
                        assertTrue(false);
                    }
                }
            }
        }
    }

    /**
     * This function will return True IFF the number is in the array
     * @param array - The given array
     * @param num - The given number
     * @return - True IFF the number is in the array
     */
    private boolean findInArray(int [] array, int num)
    {
        for(int i=0;i<array.length;i++)
        {
            if(array[i] == num)
                return true;
        }
        return false;
    }

    /**
     * This function will create number of files and will check the disk state
     * @throws OutOfSpaceException - In the process of creating the files an OutOfSpaceException might be thrown
     * @throws BadFileNameException - In the process of creating the directories an BadFileNameException might be thrown
     */
    @Test
    public void diskCheck() throws OutOfSpaceException, BadFileNameException {
        int numberOfFiles = this.rand.nextInt(BOUND)+1;
        int numOfFreeSpace = this.rand.nextInt(BOUND)+1;
        int fileSize =  this.rand.nextInt(BOUND)+1;
        int spaceSize = fileSize*numberOfFiles +numOfFreeSpace;
        FileSystem fileSystem = new FileSystem(spaceSize);
        int parents;

        Map<String,String []> namesToPaths = new HashMap<>();
        String [] path;
        String fileName = "fileName";
        String dirName = "dirName";
        for(int i=0;i<numberOfFiles;i++)
        {
            parents = rand.nextInt(BOUND) + 1;
            path = new String[parents+2];
            path[0]= "root";
            for(int j=1;j<path.length-1;j++)
            {
                path[j] = dirName+"A"+i+"B"+j;
            }
            path[path.length-1] = fileName+i;

            fileSystem.file(path,fileSize);

            namesToPaths.put(path[path.length-1],path);
        }

        String [][] blocks = fileSystem.disk();
        int countFree  = 0;
        String [] actual;
        for(int i=0;i<blocks.length;i++)
        {
            if(blocks[i]!=null)
            {
                actual = namesToPaths.get(blocks[i][blocks[i].length-1]);
                assertEquals(actual.length,blocks[i].length);
                //The path
                for(int j=0;j<blocks[i].length;j++)
                {
                    assertEquals(blocks[i][j],actual[j]);
                }
            }
            else
            {
                countFree++;
            }
        }
        assertEquals(countFree,numOfFreeSpace);

    }
    @After
    /**
     * This function will reset the space instance
     */
    public void clean()
    {
        FileSystem.fileStorage = null;
    }

}
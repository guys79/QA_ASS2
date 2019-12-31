package system;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.DirectoryNotEmptyException;
import java.util.*;

import static org.junit.Assert.*;

public class FileSystemTest {

    private Random rand;
    private final int BOUND = 100;


    @Before
    public void Initialization()
    {
        this.rand = new Random();
    }

    @Test
    public void creatingDirTwice() throws BadFileNameException{
        // TODO: 30/12/2019 Remove
        //int parents = rand.nextInt(this.BOUND) + 1;
        int parents = 2;
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
    @Test(expected = BadFileNameException.class)
    public void creatingDirAndThenFile() throws BadFileNameException, OutOfSpaceException {
        // TODO: 30/12/2019 Remove
        //int parents = rand.nextInt(this.BOUND) + 1;
        int parents = 2;
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




    @Test
    public void creation()
    {
        int spaceSize = rand.nextInt(this.BOUND) + 1;
        FileSystem fileSystem = new FileSystem(spaceSize);
        assertEquals(FileSystem.fileStorage.countFreeSpace(),spaceSize);
    }

    @Test
    public void correctFile() throws OutOfSpaceException, BadFileNameException {
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


        Space space = FileSystem.fileStorage;
        Leaf [] allocs = space.getAlloc();
        Leaf res = null;
        for(int i=0;i<allocs.length;i++)
        {
            if(allocs[i] != null && allocs[i].name.equals(path[path.length-1]))
                res = allocs[i];
        }
        assertTrue(res!=null);

        String [] resPath = res.getPath();
        assertEquals(resPath.length,path.length);
        for(int i=0;i<path.length;i++)
        {
            assertEquals(resPath[i],path[i]);
        }

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
    private void noSpaceExistFileToReplaceGeneric(int fileSize,int spaceSize) throws OutOfSpaceException, BadFileNameException {

        int parents = rand.nextInt(BOUND) + 2;
        if(spaceSize == fileSize)
        {
            fileSize = 2;
            spaceSize = 3;
        }
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

    @Test
    public void noSpaceExistFileToReplace() throws OutOfSpaceException, BadFileNameException {
        int fileSize = rand.nextInt(this.BOUND) + 2;
        int spaceSize = (int)(fileSize * 1.5);
        noSpaceExistFileToReplaceGeneric(fileSize,spaceSize);
    }
    @Test
    public void noSpaceExistFileToReplaceWithALotOfSpace() throws OutOfSpaceException, BadFileNameException {
        int fileSize = rand.nextInt(this.BOUND) + 1;
        int spaceSize = fileSize * 10;
        noSpaceExistFileToReplaceGeneric(fileSize,spaceSize);
    }
    
    
    
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
    @Test
    public void fileDeleteWhenEmpty() throws OutOfSpaceException, BadFileNameException {

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

    @Test
    public void fileNameDirDelete() throws BadFileNameException {
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
    @Test
    public void fileDoesNotExistDirIs() throws OutOfSpaceException, BadFileNameException {
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
    public void clean()
    {
        FileSystem.fileStorage = null;
    }

}
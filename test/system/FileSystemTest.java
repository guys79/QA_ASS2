package system;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

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
    public void creation()
    {
        int spaceSize = rand.nextInt(BOUND);
        FileSystem fileSystem = new FileSystem(spaceSize);
        assertEquals(FileSystem.fileStorage.countFreeSpace(),spaceSize);
    }

}
package system;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

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
       // assertEquals(space.);
    }

    @Test
    public void alloc() {

    }

    @Test
    public void dealloc() {
    }

    @Test
    public void countFreeSpace() {
    }

    @Test
    public void getAlloc() {
    }
}
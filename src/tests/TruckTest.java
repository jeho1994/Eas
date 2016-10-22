/*
 * Unit test for Truck Class TCSS 305 Assignment 3 
 */
package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import model.Direction;
import model.Light;
import model.Terrain;
import model.Truck;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Louis Yang (jeho1994)
 * @version 1.0
 */
public class TruckTest {
    
    /**
     * The number of times to repeat a test to have a high probability that all
     * random possibilities have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 50;
    /**
     * random number to be used in the test.
     */
    private Random myRandomNumber;
    /**
     * New Truck to be used in the test.
     */
    private Truck myTestTruck;
    
    /** A method to initialize the test property before each test. */
    @Before
    // A method that runs before each test to create a new working environment
    public void setUp() {
        myTestTruck = new Truck(2, 2, Direction.NORTH);
        myRandomNumber = new Random();    
    }

    /**
     * Tests the x coordinate of the truck produced by the constructor.
     */
    @Test
    public void testConstructorXCoordinate() {
        assertEquals("Constructor has not produced the truck with the correct x coordinate",
                     2, myTestTruck.getX());
    }
    /** 
     * Tests the y coordinate of the truck produced by the constructor. 
     */
    @Test
    public void testConstructorCoordinate() {
        assertEquals("Constructor has not produced the truck with the correct y coordinate",
                     2, myTestTruck.getY());
    }
    
    /** 
     * Tests the direction of the truck produced by the constructor. 
     */
    @Test
    public void testConstructorDirection() {
        assertEquals("Constructor has not produced the truck with the correct direction",
                     Direction.NORTH, myTestTruck.getDirection());
    }
    
    /** 
     * Tests the default death time of the truck produced by the constructor. 
     */
    @Test
    public void testConstructorDefaultDeathTime() {
        assertEquals("Constructor has not produced the truck with the correct death time",
                     0, myTestTruck.getDeathTime());
    }
    
    /**
     * Test Method for the CanPass Method.
     */
    @Test
    public void testCanPass() {
        // Trucks should only be allowed to pass when the terrain is either STREET or LIGHT
        // It can pass thru any lights whether its GREEN, YELLOW, or RED
        
        // start from each terrain type except WALL
        for (final Terrain testTerrain : Terrain.values()) {
            //Trucks only move in STREET or LIGHT
            if (testTerrain == Terrain.STREET || testTerrain == Terrain.LIGHT) {
                final Truck truck1 = new Truck(0, 0, Direction.NORTH);
                // go to each terrain type
                for (final Terrain t : Terrain.values()) {
                    // try the test under each light condition
                    for (final Light l : Light.values()) {
                        if (t == Terrain.STREET || t == Terrain.LIGHT) {

                            assertTrue("Truck should be able to pass " + t
                                                       + ", with light " + l,
                                       truck1.canPass(t, l));
                        } else {

                            assertFalse("Truck should NOT be able to pass " + t
                                         + ", with light " + l, truck1.canPass(t, l));
                        }
                    }
                } 
            }
        }
    }

    /**
     * Test for the ChooseDirection where the truck has to move to the direction SOUTH.
     */
    @Test
    public void testChooseDirectionTestSouth() {
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        for (final Terrain truckCannotMove: Terrain.values()) {
            if (truckCannotMove == Terrain.LIGHT || truckCannotMove == Terrain.STREET) {
                continue;
            }
            final Truck truck2 = new Truck(0, 0, Direction.NORTH);
            neighbors.put(Direction.NORTH, truckCannotMove);
            neighbors.put(Direction.WEST, truckCannotMove);
            neighbors.put(Direction.EAST, truckCannotMove);
            
            /*   W
             * W 0 W
             *   |
             *   V
             * always go south
             */     
            neighbors.put(Direction.SOUTH, lightOrStreet());
            assertEquals("Truck has no option but to go south!", 
                         Direction.SOUTH, truck2.chooseDirection(neighbors));
        }  
    }
    
    /**
     * Test for the ChooseDirection where the truck has to move to the direction other than
     * South.
     */
    @Test
    public void testChooseDirectionTestNotSouth() {
        
        for (int i = 0; i < TRIES_FOR_RANDOMNESS; i++) {
            final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
            // assigns the terrains as either LIGHT or STREET
            neighbors.put(Direction.NORTH, lightOrStreet());
            neighbors.put(Direction.WEST, lightOrStreet());
            neighbors.put(Direction.EAST, lightOrStreet());
            // assigns the direction SOUTH as a WALL, TRAIL, or GRASS
            neighbors.put(Direction.SOUTH, notlightOrStreet());
            final Truck truck3 = new Truck(0, 0, Direction.NORTH);
            /*    
             * S/L = STREET/LIGHT
             * 
             *    S/L
             * S/L 0 S/L
             *     X
             * does not go back.
             * always go NORTH, WEST, or EAST
             * never go SOUTH
             */
            final Direction dir = truck3.chooseDirection(neighbors);
            assertNotSame("Truck cannot move south!", Direction.SOUTH, dir);
        }
    }
    
    /**
     * Test for the ChooseDirection where the truck has to move to the direction EAST.
     */
    @Test
    public void testChooseDirectionTestEAST() {
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        // NORTH and WEST are terrains that the Truck cannot move on
        neighbors.put(Direction.NORTH, notlightOrStreet());
        neighbors.put(Direction.WEST, notlightOrStreet());
        // only option for the Truck is to go either to the EAST or SOUTH
        neighbors.put(Direction.EAST, lightOrStreet());
        neighbors.put(Direction.SOUTH, lightOrStreet());
        final Truck truck3 = new Truck(0, 0, Direction.NORTH);
        /* 
         * S/L = STREET/LIGHT
         * 
         *   X
         * X 0 S/L ->
         *  S/L
         * has to move EAST
         * never go SOUTH
         */
        final Direction dir = truck3.chooseDirection(neighbors);
        assertEquals("Truck has to move East", Direction.EAST, dir);
    }
    
    /**
     * Randomly selects a random Terrain Street or Light.
     * 
     * @return a random  Terrain Street or Light
     */
    private Terrain lightOrStreet() {
        Terrain terrain = Terrain.STREET;
        final int number = myRandomNumber.nextInt(2);
        if (number == 1) {
            terrain = Terrain.LIGHT;
        }
        return terrain;
    }
    
    /**
     * Randomly selects a random Terrain WALL, TRAIL or GRASS.
     * 
     * @return a random  Terrain WALL, TRAIL, or STREET.
     */
    private Terrain notlightOrStreet() {
        Terrain terrain = Terrain.WALL;
        final int number = myRandomNumber.nextInt(3);
        if (number == 1) {
            terrain = Terrain.TRAIL;
        } else if (number == 2) {
            terrain = Terrain.GRASS;
        }
        return terrain;
    }
}

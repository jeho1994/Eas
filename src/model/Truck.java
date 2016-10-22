/*
 * Truck Class for Easy Street TCSS 305 Assignment 3  
 */
package model;

import java.util.ArrayList;
import java.util.Map;

/**
 * Vehicle Truck.
 * 
 * @author Louis Yang (jeho1994)
 * @version 1.0
 */
public class Truck extends AbstractVehicle {

    /**
     * Default death time for truck.
     */
    public static final int DEFAULT_DEATH_TIME = 0;
    
    
    /**
     * Constructor for the Truck.
     * 
     * @param theX the x coordinate of the vehicle
     * @param theY the y coordinate of the vehicle
     * @param theDir the direction of the vehicle
     */
    public Truck(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEFAULT_DEATH_TIME);
    }
    
    /**
     * Trucks can pass the streets and lights.
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean vehicleCanPass = false;
        // Truck can move only in streets or lights
        if (theTerrain == Terrain.STREET || theTerrain == Terrain.LIGHT) {
            vehicleCanPass = true;
        }
        return vehicleCanPass;
    }

    /**
     * Trucks can randomly go north, west, and east of its current direction when possible.
     * If not, it chooses to go south of its current direction.
     * 
     * {@inheritDoc}
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction newDirection = Direction.random();
        
        // collection of valid directions that the truck can move
        final ArrayList<Direction> validDirection = new ArrayList<Direction>();
        
        // sorts through all the trucks surroundings
        for (final Direction surrounding : theNeighbors.keySet()) {
            //code reuse to reduce redundancy, can pass in any lights (does not matter)
            if (this.canPass(theNeighbors.get(surrounding), Light.GREEN) 
                            && surrounding != getDirection().reverse()) {
                validDirection.add(surrounding);
            }
        }
        // no valid directions other than to turn around
        if (validDirection.isEmpty()) {
            newDirection = getDirection().reverse();
        } else {
            // sorting randomly until valid direction movement is achieved 
            while (!validDirection.contains(newDirection)) {
                newDirection = Direction.random();
            }
        }   
        return newDirection;
    }
}

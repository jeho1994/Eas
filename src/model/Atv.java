/*
 * ATV Class for Easy Street TCSS 305 Assignment 3  
 */
package model;

import java.util.ArrayList;
import java.util.Map;

/**
 * Vehicle ATV.
 * 
 * @author Louis Yang (jeho1994)
 * @version 2.0
 */
public class Atv extends AbstractVehicle {

    /**
     * Default death time for ATV.
     */
    public static final int DEFAULT_DEATH_TIME = 20;
    
    /**
     * Constructor for the ATV Class.
     * 
     * @param theX the x coordinate of the vehicle
     * @param theY the y coordinate of the vehicle
     * @param theDir the direction of the vehicle
     */
    public Atv(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEFAULT_DEATH_TIME);
    }

    /**
     * ATV can travel on any terrain except walls.
     * They randomly select to go straight, left or right and never goes in reverse direction.
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean vehicleCanPass = true;
        if (theTerrain == Terrain.WALL) {
            vehicleCanPass = false;
        }
        return vehicleCanPass;         
    }

    /**
     * ATV can travel on any terrain except walls.
     * They randomly select to go straight, left or right and never goes in reverse direction.
     * 
     * {@inheritDoc}
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction newDirection = Direction.random();
        final ArrayList<Direction> validDirection = new ArrayList<Direction>();
        // adds all valid directions that the ATV can go into the validDirection
        for (final Direction surrounding : theNeighbors.keySet()) {
            if (this.canPass(theNeighbors.get(surrounding), Light.GREEN) 
                            && surrounding != getDirection().reverse()) {
                validDirection.add(surrounding);
            }
        }   
        // chooses the valid direction
        while (!validDirection.contains(newDirection)) {
            newDirection = Direction.random();  
        }     
        return newDirection;
    }
}

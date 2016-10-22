/*
 * Human Class for Easy Street TCSS 305 Assignment 3  
 */
package model;

import java.util.Map;

/**
 * Vehicle Human.
 * 
 * @author Louis Yang (jeho1994)
 * @version 1.0
 */
public class Human extends AbstractVehicle {
    
    /**
     * Default death time for ATV.
     */
    public static final int DEFAULT_DEATH_TIME = 50;
    /**
     * Current Terrain of the Vehicle Human.
     */
    private final Terrain myTerrain;
    
    /**
     * Constructor for the Human Class.
     * 
     * @param theX the x coordinate of the vehicle
     * @param theY the y coordinate of the vehicle
     * @param theDir the direction of the vehicle
     * @param theTerrain the terrain of the vehicle
     */
    public Human(final int theX, final int theY, final Direction theDir, 
                 final Terrain theTerrain) {
        super(theX, theY, theDir, DEFAULT_DEATH_TIME);
        myTerrain = theTerrain;
    }
    
    
    /**
     * Human moves in a random direction in the terrain that they initially started on.
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean vehicleCanPass = false;
        if (myTerrain == theTerrain) {
            vehicleCanPass = true;
        } else if (terrainOrLight(myTerrain) && terrainOrLight(theTerrain)) {
            vehicleCanPass = true;
        }
        return vehicleCanPass;
    }

    /**
     * Chooses a random direction to move in.
     * 
     * {@inheritDoc}
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction newDirection = Direction.random();
        while (!canPass(theNeighbors.get(newDirection), Light.GREEN)) {
            newDirection = Direction.random();
        }
        return newDirection;
    }
    
    /**
     * Helper method to determine if the terrain is a street or light.
     *
     * @param theTerrain the terrain of the vehicles surroundings
     * @return if the terrain is a street or light
     */
    private boolean terrainOrLight(final Terrain theTerrain) {
        return theTerrain == Terrain.STREET || theTerrain == Terrain.LIGHT;
    }

}

/*
 * Bicycle Class for Easy Street   
 */
package model;


import java.util.ArrayList;
import java.util.Map;

/**
 * Vehicle Bicycle.
 * 
 * @author Louis Yang (jeho1994)
 * @version 1.0
 */
public class Bicycle extends AbstractVehicle {

    /**
     * Default death time for ATV.
     */
    public static final int DEFAULT_DEATH_TIME = 30;
    
    /**
     * Constructor for the Bicycle Class.
     * 
     * @param theX the x coordinate of the vehicle
     * @param theY the y coordinate of the vehicle
     * @param theDir the direction of the vehicle
     */
    public Bicycle(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEFAULT_DEATH_TIME);
    }

    /**
     * Bicycles can travel on streets and lights but they prefer to travel on trails.
     * Bicycle only ignores green light and append to the rest.
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean vehicleCanPass = false;
        if (theTerrain == Terrain.STREET || theTerrain == Terrain.TRAIL) {
            vehicleCanPass = true;
        } else if (theTerrain == Terrain.LIGHT && theLight == Light.GREEN) {
            vehicleCanPass = true;
        }
        return vehicleCanPass;
    }

    /**
     * If there are roads and no trails available, Bicycle takes the streets or lights by its
     * priority directions starting from straight ahead to left then to the right. If none
     * of these options are available it turns around.  
     * 
     * {@inheritDoc}
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction chooseDirection = getDirection();
        // the Bicycle prefers to move in the direction of straight, left, right and reverse
        final ArrayList<Direction> directionOrder = new ArrayList<Direction>();
        directionOrder.add(chooseDirection);
        directionOrder.add(chooseDirection.left());
        directionOrder.add(chooseDirection.right());
        directionOrder.add(chooseDirection.reverse());
        
        // if the current direction is not a trail and there is a trail present on the
        // right and left, choose to go that direction
        if (theNeighbors.get(chooseDirection) != Terrain.TRAIL) {
            if (theNeighbors.get(chooseDirection.left()) == Terrain.TRAIL) {
                chooseDirection = chooseDirection.left();
            } else if (theNeighbors.get(chooseDirection.right()) == Terrain.TRAIL) {
                chooseDirection = chooseDirection.right();
            }
        }
        
        // if the current direction is a trail, go straight if not, go to a street or light
        // if none of these options are available then go reverse
        int directionOrderIndex = 0;
        while (directionOrderIndex < directionOrder.size()
                        && !isStreetOrLight(theNeighbors.get(chooseDirection))) {
            chooseDirection = directionOrder.get(directionOrderIndex);
            directionOrderIndex++;
        }    
        return chooseDirection;
    }
    
    /**
     * Helper method to determine if the terrain is a street or light, or trail.
     *
     * @param theTerrain the terrain of the vehicles surroundings
     * @return if the terrain is a street or light
     */
    private boolean isStreetOrLight(final Terrain theTerrain) {
        return (theTerrain == Terrain.STREET) || (theTerrain == Terrain.LIGHT) 
                        || (theTerrain == Terrain.TRAIL);     
    }
}

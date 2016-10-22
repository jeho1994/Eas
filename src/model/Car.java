/*
 * Car Class for Easy Street 
 */
package model;

import java.util.ArrayList;
import java.util.Map;

/**
 * Vehicle Car.
 * 
 * @author Louis Yang (jeho1994)
 * @version 1.0
 */
public class Car extends AbstractVehicle {

    /**
     * Default death time for Car.
     */
    public static final int DEFAULT_DEATH_TIME = 10;
    /**
     * Default red light time until Car is allowed to pass.
     */
    public static final int DEFAULT_RED_LIGHT_COUNTER = 0;
    /**
     * Default red light counter before the Car can proceed forward.
     */
    private final int myDefaultRedLightCounter;
    /**
     * Current red light counter before the Car can proceed forward.
     */
    private int myRedLightCounter;
    
    /**
     * Constructor for the Car.
     * 
     * @param theX the x coordinate of the vehicle
     * @param theY the y coordinate of the vehicle
     * @param theDir the direction of the vehicle
     */
    public Car(final int theX, final int theY, final Direction theDir) {
        this(theX, theY, theDir, DEFAULT_RED_LIGHT_COUNTER);
    }
    
    /**
     * Constructor for the Car and its subclasses.
     * 
     * @param theX the x coordinate of the vehicle
     * @param theY the y coordinate of the vehicle
     * @param theDir the direction of the vehicle
     * @param theRedLightCount the red light count of the vehicle
     */
    public Car(final int theX, final int theY, 
                    final Direction theDir, final int theRedLightCount) {
        super(theX, theY, theDir, DEFAULT_DEATH_TIME);
        
        myDefaultRedLightCounter = theRedLightCount;
        myRedLightCounter = theRedLightCount;        
    }

    /**
     * Cars can pass the streets and lights.
     * Cars stop for red lights until certain waits however, 
     * it bypasses all yellow and green lights.
     * 
     * {@inheritDoc}
     */
    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean vehicleCanPass = false;
        // car prefers the street and light and stops at red lights
        if (theTerrain == Terrain.STREET) {
            vehicleCanPass = true;
        } else if (theTerrain == Terrain.LIGHT && theLight == Light.GREEN) {
            vehicleCanPass = true;
        } else if (theTerrain == Terrain.LIGHT && theLight == Light.YELLOW) {
            vehicleCanPass = true;
        } else if (theTerrain == Terrain.LIGHT && theLight == Light.RED 
                        && myRedLightCounter > 0) {
            //only for cars that can pass red lights
            vehicleCanPass = canPassRedLight();
        }     
        return vehicleCanPass;
    }
    
    /**
     * Helper method to determine if the vehicle is allowed to pass during red light.
     * 
     * @return if red light passing is available
     */
    public boolean canPassRedLight() {
        // only for cars that can pass red lights
        boolean vehicleCanPass = false;
        myRedLightCounter--;
        if (myRedLightCounter == 0) {
            myRedLightCounter = myDefaultRedLightCounter;
            vehicleCanPass = true;
        }
        return vehicleCanPass;
    }

    /**
     * Car chooses to move in the order straight, left, right then reverse.
     * 
     * {@inheritDoc}
     */
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        // current vehicle direction
        Direction chooseDirection = getDirection();
        // car's preferred direction in order
        final ArrayList<Direction> directionOrder = new ArrayList<Direction>();
        directionOrder.add(chooseDirection);
        directionOrder.add(chooseDirection.left());
        directionOrder.add(chooseDirection.right());
        directionOrder.add(chooseDirection.reverse());
        // car's preferred direction index
        int counter = 0;
        // chooses the first valid direction for the car 
        while (counter < directionOrder.size() 
                        && !canMoveForward(theNeighbors.get(chooseDirection))) {
            counter++;
            chooseDirection = directionOrder.get(counter);
        }
        return chooseDirection;   
    }
    
    /**
     * Helper method to determine if the vehicle is able to move forward.
     * 
     * @param theTerrain the terrain of the vehicles surroundings
     * @return if the vehicle is able to move forward
     */
    private boolean canMoveForward(final Terrain theTerrain) {
        return (theTerrain == Terrain.STREET) || (theTerrain == Terrain.LIGHT);       
    }
}

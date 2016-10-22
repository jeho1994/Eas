/*
 * Taxi Class for Easy Street TCSS 305 Assignment 3  
 */
package model;

/**
 * Vehicle Taxi.
 * 
 * @author Louis Yang (jeho1994)
 * @version 1.0
 */
public class Taxi extends Car {
    
    /**
     * Default red light time until Taxi is allowed to pass.
     */   
    public static final int DEFAULT_RED_LIGHT_TIME = 3;
    
    /**
     * Constructor for the Taxi Class.
     * 
     * @param theX the x coordinate of the vehicle
     * @param theY the y coordinate of the vehicle
     * @param theDir the direction of the vehicle
     */
    public Taxi(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEFAULT_RED_LIGHT_TIME);
    }
}

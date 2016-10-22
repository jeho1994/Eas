/*
 * Abstract AbstractVehicle for Easy Street
 */
package model;

import java.util.Map;

/**
 * Abstract for Vehicles.
 * 
 * @author Louis Yang (jeho1994)
 * @version 1.0
 */
public abstract class AbstractVehicle implements Vehicle {

    /** 
     * Current x coordinate.
     */
    private int myX;
    /** 
     * Current y coordinate.
     */
    private int myY;
    /** 
     * Original x coordinate.
     */
    private final int myOriginalX;
    /** 
     * Original y coordinate.
     */
    private final int myOriginalY;
    /** 
     * Current direction.
     */
    private Direction myDirection;
    /** 
     * Original direction.
     */
    private final Direction myOriginalDirection;
    /** 
     * Current death counter.
     */
    private int myDeathCounter;
    /** 
     * Original death counter.
     */
    private final int myDeathTime;
    
    /** 
     * Constructor for the AbstractVehicle.
     * 
     * @param theX the x coordinate of the vehicle
     * @param theY the y coordinate of the vehicle
     * @param theDir the direction of the vehicle
     * @param theDeathTime the death time of the vehicle
     */
    protected AbstractVehicle(final int theX, final int theY, 
                              final Direction theDir, final int theDeathTime) {
        // x and y coordinates
        myX = theX;
        myY = theY;
        // original x and y coordinates
        myOriginalX = theX;
        myOriginalY = theY;
        // current and original direction
        myDirection = theDir;
        myOriginalDirection = theDir;
        //current and original death time
        myDeathTime = theDeathTime;
        myDeathCounter = theDeathTime;
    }

    /** {@inheritDoc} */ 
    @Override
    public abstract boolean canPass(Terrain theTerrain, Light theLight);

    /** {@inheritDoc} */ 
    @Override
    public abstract Direction chooseDirection(Map<Direction, Terrain> theNeighbors);

    /** {@inheritDoc} */ 
    @Override
    public void collide(final Vehicle theOther) {
        // checks to see if both vehicle involved in collision is alive
        if (this.isAlive() && theOther.isAlive() 
                        && this.getDeathTime() > theOther.getDeathTime()) {
            myDeathCounter = 0; //dead
        }
    }

    /** {@inheritDoc} */ 
    @Override
    public int getDeathTime() {
        return myDeathTime;
    }

    /** {@inheritDoc} */ 
    @Override
    public String getImageFileName() {
        final StringBuilder builder = new StringBuilder(128);
        builder.append(toString());
        if (!isAlive()) {
            builder.append("_dead");
        }
        builder.append(".gif");
        return builder.toString();
    }

    /** {@inheritDoc} */ 
    @Override
    public Direction getDirection() {
        return myDirection;
    }

    /** {@inheritDoc} */ 
    @Override
    public int getX() {
        return myX;
    }

    /** {@inheritDoc} */ 
    @Override
    public int getY() {
        return myY;
    }

    /** {@inheritDoc} */ 
    @Override
    public boolean isAlive() {
        boolean alive = true;
        if (myDeathCounter < myDeathTime) {
            alive = false;
        }
        return alive;
    }

    /** {@inheritDoc} */ 
    @Override
    public void poke() {
        if (myDeathCounter < myDeathTime) {
            myDeathCounter++;
        } else if (myDeathCounter == myDeathTime) {
            myDirection = Direction.random();
        }
    }

    /** {@inheritDoc} */ 
    @Override
    public void reset() {
        myX = myOriginalX;
        myY = myOriginalY;
        myDirection = myOriginalDirection;
        myDeathCounter = myDeathTime;
    }

    /** {@inheritDoc} */ 
    @Override
    public void setDirection(final Direction theDir) {
        myDirection = theDir;
    }

    /** {@inheritDoc} */ 
    @Override
    public void setX(final int theX) {
        myX = theX;
    }

    /** {@inheritDoc} */ 
    @Override
    public void setY(final int theY) {
        myY = theY;
    }
    
    /**
     * Represent the Vehicle name.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName().toLowerCase();
    }
}

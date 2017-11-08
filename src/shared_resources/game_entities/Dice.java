/*
 * Risk Game Team 2
 * Dice.java
 * Version 1.0
 * Oct 18, 2017
 */
package shared_resources.game_entities;

import java.util.Random;
import java.util.Vector;

/**
 * Objects of this class are created by specifying the number of dice to roll
 * <ul>
 * <li> A pip is the index within array representing the number of a die face (it represents the 'up-facing' index of a die)
 * <li> Each pip is a index between 1 and MAX_PIPS (default is 1 to 6)
 * <li> The roll() action will return the 2 most largest values from dice array after randomizing.
 * </ul>
 *
 * @author Team 2
 * @version 1.0
 */
public class Dice {
    // region Attributes declaration
    private int rollsCount;
    private Vector<Integer> rollsResult;
    // endregion
    
    // region Constructors
    /**
     * Build an array of dice with default pips showing 1.
     *
     * @param rollsCount represents the number of dice to be rolled
     */
    public Dice(int rollsCount) {
        this.rollsCount = rollsCount;
        rollsResult = new Vector<>();
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Gets the number of dice rolled
     *
     * @return the rolls count
     */
    public int getRollsCount() {
        return rollsCount;
    }
    
    /**
     * Gets the rolls result
     *
     * @return the rolls result
     */
    public Vector<Integer> getRollsResult() {
        return rollsResult;
    }
    
    /**
     * Sets the roll result
     *
     * @param rollsResult the roll result
     */
    public void setRollsResult(Vector<Integer> rollsResult) {
        this.rollsResult = rollsResult;
    }
    
    // endregion
    
    // region Public methods
    
    /**
     * Depending on the number of dice, roll the dice
     */
    public void roll() {
        for (int i = 0; i < rollsCount; i++) {
            rollsResult.add(new Random().nextInt(6) + 1);
        }
    }
    
    /**
     * Gets the highest result out of all rolls
     *
     * @return the best result
     */
    public int getTheBestResult() {
        int max = Integer.MIN_VALUE;
        for (Integer result : rollsResult) {
            if (max < result) {
                max = result;
            }
        }
        return max;
    }
    
    /**
     * Gets the second highest result out of all rolls
     *
     * @return the second best result
     */
    public int getSecondBestResult() {
        int max = 1;
        int second_max = 1;
        for (Integer result : rollsResult) {
            int tmp_max = max;
            boolean isMaxChanged = false;
            if (max <= result) {
                max = result;
                isMaxChanged = true;
            }
            
            if (isMaxChanged) {
                second_max = tmp_max;
            } else if (second_max <= result) {
                second_max = result;
            }
        }
        return second_max;
    }
    // endregion
    
}
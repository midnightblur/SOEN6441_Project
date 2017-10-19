/* 
 * Risk Game Team 2
 * Dice.java
 * Version 1.0
 * Oct 18, 2017
 */
package shared_resources.game_entities;

import shared_resources.utilities.Config;

import java.util.Arrays;

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
    /** The dice object is represented as an array of integers. */
    private int[] dice;
    // endregion
    
    // region Constructors
    
    /**
     * Default constructor delegating to parametrized constructor.
     */
    public Dice() {
        this(1);
    }
    
    /**
     * Parameterized constructor
     * Build an array of dice with default pips showing 1.
     *
     * @param numberOfDice represents the number of dice to be rolled
     */
    public Dice(int numberOfDice) {
        /* enforce at least 1 die to be rolled */
        if (numberOfDice < 1) {
            numberOfDice = 1;
        }
        
        dice = new int[numberOfDice];

        /* set initial index of each die to a random number */
        for (int i : dice) {
            dice[i] = (int) (Math.random() * Config.MAX_PIPS) + 1;
        }
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Gets the max pips.
     *
     * @return the max pips
     */
    public int getMAX_PIPS() {
        return Config.MAX_PIPS;
    }
    
    /**
     * Gets the dice.
     *
     * @return the dice
     */
    public int[] getDice() {
        return dice;
    }
    
    /**
     * Sets the dice.
     *
     * @param dice the new dice
     */
    public void setDice(int[] dice) {
        this.dice = dice;
    }
    // endregion
    
    // region Public methods
    
    /**
     * Rolling the dice and returning the maximum 2 values obtained sorted in descending order.
     *
     * @return an array containing the maximum 2 values obtained sorted in descending order
     *
     * @throws Exception the exception
     */
    public int[] roll() throws Exception {

        /* validating the dice size */
        if (dice.length == 0) {
            throw new Exception("No dice to roll");
        }

        /* rolling the dice */
        for (int i : dice) {
            i = (int) (Math.random() * Config.MAX_PIPS) + 1;
        }

        /* sorting ascending */
        Arrays.sort(dice);

        /* defining a the returned result array of 1 or 2 entries and copy the largest values */
        if (dice.length == 1) {
            int[] result = new int[1];
            result[0] = dice[dice.length - 1];
            return result;
        } else {
            int[] result = new int[2];
            result[0] = dice[dice.length - 1];
            result[0] = dice[dice.length - 2];
            return result;
        }
    }
    // endregion
    
}

package model.game_entities;

import utilities.Config;

import java.util.Arrays;
import java.util.Observable;

/**
 * Objects of this class are created by specifying the number of dice to roll
 * A pip is the index within array representing the number of a die face (it represents the 'up-facing' index of a die)
 * Each pip is a index between 1 and MAX_PIPS (default is 1 to 6)
 * The roll() action will return the 2 most largest values from dice array after randomizing
 */
public class Dice extends Observable {
    
    /**
     * The dice object is represented as an array of integers
     */
    private int[] dice;
    
    /* Getters & Setters */
    public int getMAX_PIPS() {
        return Config.MAX_PIPS;
    }
    
    public int[] getDice() {
        return dice;
    }
    
    public void setDice(int[] dice) {
        this.dice = dice;
    }
    
    /**
     * Default constructor delegating to parametrized constructor
     */
    public Dice() {
        this(1);
    }
    
    /**
     * Parameterized constructor
     * Build an array of dice with default pips showing 1
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
    
    /**
     * Rolling the dice and returning the maximum 2 values obtained sorted in descending order
     *
     * @return an array containing the maximum 2 values obtained sorted in descending order
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
            /* specify that model state changed and notify observers */
            setChanged();
            notifyObservers();
            return result;
        } else {
            int[] result = new int[2];
            result[0] = dice[dice.length - 1];
            result[0] = dice[dice.length - 2];
            /* specify that model state changed and notify observers */
            setChanged();
            notifyObservers();
            return result;
        }
        
        
    }
}

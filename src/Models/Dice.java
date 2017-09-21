package Models;

import Utils.Config;

public class Dice {
    private int[] dice;

    /* Ctors & Dtors */

    /**
     * Default constructor delegating to parameterized constructor
     */
    public Dice() {
        this(1);
    }

    /**
     * Parameterized constructor
     * Build an array of dice with default pips showing 1
     *
     * @param numberOfDice
     */
    public Dice(int numberOfDice) {
        dice = new int[numberOfDice];
        for (int i : dice) {
            dice[i] = 1;
        }
    }

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
     * Rolling the dice and returning the sum of top pips
     *
     * @return sumPips
     */
    public int roll() {
        int sumPips = 0;
        for (int i : dice) {
            dice[i] = (int) Math.random() * Config.MAX_PIPS + 1;
            sumPips += dice[i];

        }
        return sumPips;
    }

}

/**  
 * @file  DiceTest.java 
 * @brief 
 * 
 * 
 * 
 * @author Team 2
 * @version 1.0
 * @since  Oct 18, 2017
 * @bug No known bugs.
 */
package model;

import model.game_entities.Dice;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The Class DiceTest.
 */
public class DiceTest {

    /**
     * This test checks roll of dice.
     */
    @Test
    public void rollForTreeDice() {
        int diceCount = 3;
        System.out.println("\nTesting with " + diceCount + " dice ...");
        Dice myDice = new Dice(diceCount);
        try {
            int resultingSize = myDice.roll().length;
            assertEquals(2, resultingSize);
            System.out.println("Expected size of rolled result:\t2\n" +
                    "Actual size of rolled result:\t" + resultingSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This test checks roll for one dice.
     */
    @Test
    public void rollForOneDice() {
        int diceCount = 1;
        System.out.println("\nTesting with " + diceCount + " dice ...");
        Dice myDice = new Dice(diceCount);
        try {
            int resultingSize = myDice.roll().length;
            assertEquals(1, resultingSize);
            System.out.println("Expected size of rolled result:\t1\n" +
                    "Actual size of rolled result:\t" + resultingSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This test checks the roll for zero dice.
     */
    @Test
    public void rollForZeroDice() {
        int diceCount = 0;
        System.out.println("\nTesting with " + diceCount + " dice ... \nWe expect that Dice constructor validates and makes one die array");
        Dice myDice = new Dice(diceCount);
        try {
            int resultingSize = myDice.roll().length;
            assertEquals(1, resultingSize);
            System.out.println("Expected size of rolled result:\t1\n" +
                    "Actual size of rolled result:\t" + resultingSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
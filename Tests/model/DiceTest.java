/* 
 * Risk Game Team 2
 * DiceTest.java
 * Version 1.0
 * Oct 18, 2017
 */
package model;

import org.junit.Test;
import shared_resources.game_entities.Dice;

import static org.junit.Assert.assertEquals;

/**
 * The Class DiceTest.
 */
public class DiceTest {
    
    /**
     * This test checks roll of dice.
     *
     * @throws Exception if there is no die
     */
    @Test
    public void rollForTreeDice() throws Exception {
        int diceCount = 3;
        System.out.println("\nTesting with " + diceCount + " dice ...");
        Dice myDice = new Dice(diceCount);
        int resultingSize = myDice.roll().length;
        assertEquals(2, resultingSize);
        System.out.println("Expected size of rolled result:\t2\n" +
                "Actual size of rolled result:\t" + resultingSize);
        
    }
    
    /**
     * This test checks roll for one dice.
     *
     * @throws Exception if there is no die
     */
    @Test
    public void rollForOneDice() throws Exception {
        int diceCount = 1;
        System.out.println("\nTesting with " + diceCount + " dice ...");
        Dice myDice = new Dice(diceCount);
        int resultingSize = myDice.roll().length;
        assertEquals(1, resultingSize);
        System.out.println("Expected size of rolled result:\t1\n" +
                "Actual size of rolled result:\t" + resultingSize);
    }
    
    /**
     * This test checks the roll for zero dice.
     *
     * @throws Exception if there is no die
     */
    @Test
    public void rollForZeroDice() throws Exception {
        int diceCount = 0;
        System.out.println("\nTesting with " + diceCount + " dice ... \nWe expect that Dice constructor validates and makes one die array");
        Dice myDice = new Dice(diceCount);
        int resultingSize = myDice.roll().length;
        assertEquals(1, resultingSize);
        System.out.println("Expected size of rolled result:\t1\n" +
                "Actual size of rolled result:\t" + resultingSize);
    }
    
}
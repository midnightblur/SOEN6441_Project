package model;

import model.game_entities.Dice;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiceTest {

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
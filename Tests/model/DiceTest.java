package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {

    @Test
    void rollForTreeDice() {
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
    void rollForOneDice() {
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
    void rollForZeroDice() {
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
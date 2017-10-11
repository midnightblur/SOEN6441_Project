package model.game_entities;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class tests if number of dice are more than 1
 *
 */
class DiceTest {
    int numOfDice = 3;
    Dice dice = new Dice(numOfDice);

    /**
     * the Test case
     */
    @Test
    void testDice() {

        assertTrue(numOfDice > 1);
    }
}
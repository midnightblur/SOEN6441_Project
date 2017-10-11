package model.game_entities;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


class DiceTest {
    int numOfDice = 3;
    Dice dice = new Dice(numOfDice);

    @Test
    void testDice() {

        assertTrue(numOfDice > 1);
    }
}
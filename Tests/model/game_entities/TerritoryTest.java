package model.game_entities;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This test case is used to test get function of the territory
 *
 * @author
 * @version 1.0
 */
class TerritoryTest {
    Territory territory = new Territory("Canada");

    /**
     * The test case
     */
    @Test
    void Territorytestcase()
    {
        String territoryCheck = "Canada";
        assertEquals(territory,territoryCheck);
    }
}
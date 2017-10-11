package model.game_entities;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TerritoryTest {
    Territory territory = new Territory("Canada");
    @Test
    void Territorytestcase()
    {
        String territoryCheck = "Canada";
        assertEquals(territory,territoryCheck);
    }
}
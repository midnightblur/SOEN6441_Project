package model;

import model.game_entities.Territory;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This test case is used to test getName function of the territory
 */
public class TerritoryTest {
    Territory territory = new Territory("Canada");
    
    @Test
    public void getName() throws Exception {
        assertEquals("Canada", territory.getName());
    }
    
}
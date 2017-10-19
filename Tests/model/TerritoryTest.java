/* 
 * Risk Game Team 2
 * TerritoryTest.java
 * Version 1.0
 * Oct 18, 2017
 */
package model;

import shared_resources.game_entities.Territory;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This test case is used to test getName function of the territory.
 */
public class TerritoryTest {
    
    /** The territory. */
    Territory territory = new Territory("Canada");
    
    /**
     * Gets the name.
     *
     * @return the name
     * @throws Exception the exception
     */
    @Test
    public void getName() throws Exception {
        assertEquals("Canada", territory.getName());
    }
    
}
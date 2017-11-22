/* 
 * Risk Game Team 2
 * TerritoryTest.java
 * Version 1.0
 * Oct 18, 2017
 */
package shared_resources.game_entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This test case is used to test getName function of the territory.
 *
 * @author Team 2
 * @version 1.0
 */
public class TerritoryTest {
    
    /** The territory. */
    Territory territory = new Territory("Canada");
    
    /**
     * Gets the name.
     *
     * @throws Exception the exception
     */
    @Test
    public void getName() throws Exception {
        assertEquals("Canada", territory.getName());
    }
    
}
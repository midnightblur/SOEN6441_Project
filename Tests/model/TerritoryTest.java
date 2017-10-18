/**  
 * @file  TerritoryTest.java 
 * @brief 
 * 
 * 
 * 
 * @author Team 2
 * @version 1.0
 * @since  Oct 18, 2017
 * @bug No known bugs.
 */
package model;

import model.game_entities.Territory;
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
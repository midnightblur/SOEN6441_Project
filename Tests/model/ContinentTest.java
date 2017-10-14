package model;

import model.game_entities.Continent;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This test case is used to test the continent class.
 */
public class ContinentTest {
    static String continentName = "Asia";
    static int controlValue = 5;
    static Continent continent = new Continent(continentName, controlValue);
    
    
    @Test
    public void getName() {
        System.out.println("\nTesting for the name of \"Asia\" continent");
        assertEquals(continentName, continent.getName());
    }
    
    @Test
    public void getControlValue() throws Exception {
        System.out.println("\nTesting for the control value of the continent. \nExpecting a value of " + controlValue);
        assertTrue(controlValue == continent.getControlValue());
    }
    
}
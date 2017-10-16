package model;

import model.game_entities.Continent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This test case is used to test the continent class.
 */
public class ContinentTest {
    private String continentName;
    private int controlValue;
    private Continent continent;
    
    @Before
    public void setUp() {
        continentName = "Asia";
        controlValue = 5;
        continent = new Continent(continentName, controlValue);
    }
    
    /**
     * This test checks the name of continent
     */
    @Test
    public void getName() {
        System.out.println("\nTesting for the name of \"Asia\" continent");
        assertEquals(continentName, continent.getName());
    }
    
    /**
     * This test checks the value of the control
     */
    @Test
    public void getControlValue() throws Exception {
        System.out.println("\nTesting for the control value of the continent. \nExpecting a value of " + controlValue);
        assertTrue(controlValue == continent.getControlValue());
    }
    
}
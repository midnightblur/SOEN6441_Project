/* 
 * Risk Game Team 2
 * ContinentTest.java
 * Version 1.0
 * Oct 18, 2017
 */
package shared_resources.game_entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This test case is used to test the continent class.
 *
 * @author Team 2
 * @version 1.0
 */
public class ContinentTest {
    
    /** The continent name. */
    private String continentName;
    
    /** The control value. */
    private int controlValue;
    
    /** The continent. */
    private Continent continent;
    
    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        continentName = "Asia";
        controlValue = 5;
        continent = new Continent(continentName, controlValue);
    }
    
    /**
     * This test checks the name of continent.
     */
    @Test
    public void getName() {
        System.out.println("\nTesting for the name of \"Asia\" continent");
        assertEquals(continentName, continent.getName());
    }
    
    /**
     * This test checks the value of the control.
     *
     * @throws Exception the exception
     */
    @Test
    public void getControlValue() throws Exception {
        System.out.println("\nTesting for the control value of the continent. \nExpecting a value of " + controlValue);
        assertTrue(controlValue == continent.getControlValue());
    }
    
}
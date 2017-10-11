package model;

import model.game_entities.Continent;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This test case is used to test the continent class.
 *
 * @author
 * @version 1.0
 */
public class ContinentTest {

    Continent continent = new Continent("Asia", 5);

    /**
     * The Test case
     */
    @Test
    public void getName() throws Exception {

        int x = 7;
        String continentCheck = "Asia";
        assertEquals(continent, continentCheck);
        assertTrue(x > 0);

    }

}
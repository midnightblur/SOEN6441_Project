
package shared_resources.game_entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This test case is used to test getName function of the territory.
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
/* 
 * Risk Game Team 2
 * MapValidationTest.java
 * Version 1.0
 * Oct 18, 2017
 */
package shared_resources.helper;

import org.junit.Before;
import org.junit.Test;
import shared_resources.game_entities.Continent;
import shared_resources.game_entities.GameMap;
import shared_resources.game_entities.Territory;
import shared_resources.utilities.Config;

import static org.junit.Assert.*;

/**
 * The Class GameMapHelperTest.
 */
public class GameMapHelperTest {
    /** The message. */
    private String message;
    
    /** The game map. */
    private GameMap gameMap;
    
    /**
     * This function setup the context for tests
     * Context include the GameMap object loaded from an arbitrary valid map file
     */
    @Before
    public void setup() {
        message = Config.MSG_MAPFILE_VALID;
        gameMap = null;
    }
    
    /**
     * Test map validation when adding a new continent without any territory belong to
     */
    @Test
    public void continent_has_no_territory() {
        try {
            gameMap = GameMapHelper.loadGameMap("World.map");
            message = Config.MSG_MAPFILE_VALID;
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(Config.MSG_MAPFILE_VALID, message);
        
        Continent newContinent = new Continent("example", 1);
        gameMap.addContinent(newContinent);
        
        message = GameMapHelper.validateMap(gameMap);
        
        assertEquals("The example continent has no territory", message);
    }
    
    /**
     * Test map validation when adding new territory without having any neighbor
     */
    @Test
    public void territory_has_no_neighbor() {
        try {
            gameMap = GameMapHelper.loadGameMap("World.map");
            message = Config.MSG_MAPFILE_VALID;
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(Config.MSG_MAPFILE_VALID, message);
        
        Territory territory = new Territory("example");
        gameMap.addTerritory(territory, false);
        
        message = GameMapHelper.validateMap(gameMap);
        
        assertEquals("example has 0 neighbors. Minimum number is 1, maximum number is 10", message);
    }
    
    /**
     * Test map validation when adding new territory not belong to any continent
     */
    @Test
    public void territory_has_no_continent() {
        try {
            gameMap = GameMapHelper.loadGameMap("World.map");
            message = Config.MSG_MAPFILE_VALID;
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(Config.MSG_MAPFILE_VALID, message);
        
        Territory territory = new Territory("example");
        for (Territory neighbour : gameMap.getTerritories().values()) {
            territory.addNeighbor(neighbour.getName());
            neighbour.addNeighbor(territory.getName());
            break;
        }
        gameMap.addTerritory(territory, false);
        
        message = GameMapHelper.validateMap(gameMap);
        
        assertEquals("example doesn't belong to any continent", message);
    }
    
    /**
     * Test map validation when deleting a continent, leaving its territories having no continent
     */
    @Test
    public void delete_continent() {
        try {
            gameMap = GameMapHelper.loadGameMap("World.map");
            message = Config.MSG_MAPFILE_VALID;
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(Config.MSG_MAPFILE_VALID, message);
        
        for (Continent continent : gameMap.getContinents().values()) {
            gameMap.removeContinent(continent.getName());
            break;
        }
        
        message = GameMapHelper.validateMap(gameMap);
        
        assertTrue(message.contains("doesn't belong to any continent"));
    }
    
    /**
     * Test map validation when a territory has too many neighbors
     */
    @Test
    public void territory_too_many_neighbors() {
        try {
            gameMap = GameMapHelper.loadGameMap("World.map");
            message = Config.MSG_MAPFILE_VALID;
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(Config.MSG_MAPFILE_VALID, message);
        
        Territory territory = new Territory("example");
        for (Territory neighbour : gameMap.getTerritories().values()) {
            territory.addNeighbor(neighbour.getName());
            neighbour.addNeighbor(territory.getName());
        }
        gameMap.addTerritory(territory, false);
        
        message = GameMapHelper.validateMap(gameMap);
        
        assertTrue(message.contains("Minimum number is 1, maximum number is 10"));
    }
    
    /**
     * Test map validation when a game map having no territory
     */
    @Test
    public void map_no_territory() {
        try {
            gameMap = GameMapHelper.loadGameMap("World.map");
            message = Config.MSG_MAPFILE_VALID;
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(Config.MSG_MAPFILE_VALID, message);
        
        for (int i = 0; i < gameMap.getTerritoriesCount(); i++) {
            gameMap.removeTerritory(gameMap.getArbitraryTerritory().getName());
        }
        
        message = GameMapHelper.validateMap(gameMap);
        
        assertNotEquals(Config.MSG_MAPFILE_VALID, message);
    }
    
    /**
     * This test checks the one way relationship.
     *
     * @throws Exception the exception
     */
    @Test
    public void one_way_relationship() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("Z__1_way_relationship.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_1_WAY_RELATIONSHIP, "kamchatka", "alaska"), message);
        assertNull(gameMap);
    }
    
    /**
     * This test checks if the continent has no control value.
     *
     * @throws Exception the exception
     */
    @Test
    public void continent_has_no_control_value() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("Z__continent_has_no_control_value.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, 9), message);
        assertNull(gameMap);
    }
    
    /**
     * This test checks the continent has no territory.
     *
     * @throws Exception the exception
     */
    @Test
    public void continent_has_no_territory_1() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("Z__continent_has_no_territory.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_CONTINENT_NO_TERRITORY, "asean"), message);
        assertNull(gameMap);
    }
    
    /**
     * This test checks if the continent has been duplicated.
     *
     * @throws Exception the exception
     */
    @Test
    public void duplicated_continent() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("Z__duplicated_continents.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_CONTINENT_DUPLICATED, 15), message);
        assertNull(gameMap);
    }
    
    /**
     * This test checks the if the territories has been duplicated.
     *
     * @throws Exception the exception
     */
    @Test
    public void duplicated_territories() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("Z__duplicated_territories.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_TERRITORY_DUPLICATED, 18), message);
        assertNull(gameMap);
    }
    
    /**
     * This test checks the if format is invalid.
     *
     * @throws Exception the exception
     */
    @Test
    public void invalid_format_1() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("Z__invalid_format_1.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_CONTINENT_NOT_DEFINED, 17), message);
        assertNull(gameMap);
    }
    
    /**
     * This test checks the if format is invalid test 2.
     *
     * @throws Exception the exception
     */
    @Test
    public void invalid_format_2() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("Z__invalid_format_2.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, 1), message);
        assertNull(gameMap);
    }
    
    /**
     * This test checks the if format is invalid test 3.
     *
     * @throws Exception the exception
     */
    @Test
    public void invalid_format_3() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("Z__invalid_format_3.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, 9), message);
        assertNull(gameMap);
    }
    
    /**
     * This test checks the if missing coordination.
     *
     * @throws Exception the exception
     */
    @Test
    public void missing_coordination() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("Z__missing_coordination.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, 17), message);
        assertNull(gameMap);
    }
    
    /**
     * This test checks the if there is no continent.
     *
     * @throws Exception the exception
     */
    @Test
    public void no_continent() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("Z__no_continent.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_CONTINENT_NOT_DEFINED, 11), message);
        assertNull(gameMap);
    }
    
    /**
     * This test checks the if there is no neighbor.
     *
     * @throws Exception the exception
     */
    @Test
    public void no_neighbor() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("Z__no_neighbor.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_INVALID_NEIGHBORS_COUNT, "alaska", 0), message);
        assertNull(gameMap);
    }
    
    /**
     * This test checks if there is no territory.
     *
     * @throws Exception the exception
     */
    @Test
    public void no_territory() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("Z__no_territory.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(Config.MSG_MAPFILE_INVALID_TERRITORIES_COUNT, message);
        assertNull(gameMap);
    }
    
    /**
     * This test checks the if the territory has no continent.
     *
     * @throws Exception the exception
     */
    @Test
    public void territory_has_no_continent_2() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("Z__territory_has_no_continent.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_CONTINENT_NOT_DEFINED, 17), message);
        assertNull(gameMap);
    }
    
    /**
     * This test checks the if there are too many neighbors.
     *
     * @throws Exception the exception
     */
    @Test
    public void too_many_neighbors() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("Z__too_many_neighbors.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_INVALID_NEIGHBORS_COUNT, "alaska", 11), message);
        assertNull(gameMap);
    }
    
    /**
     * This test checks the if there is undefined continent.
     *
     * @throws Exception the exception
     */
    @Test
    public void undefined_continent() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("Z__undefined_continent.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_CONTINENT_NOT_DEFINED, 16), message);
        assertNull(gameMap);
    }
    
    /**
     * This test checks the if there is undefined territory.
     *
     * @throws Exception the exception
     */
    @Test
    public void undefined_territory() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("Z__undefined_territory.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(Config.MSG_MAPFILE_TERRITORY_NOT_DEFINED, message);
        assertNull(gameMap);
    }
    
    /**
     * This test checks validation on a valid map
     */
    @Test
    public void valid_map() {
        try {
            gameMap = GameMapHelper.loadGameMap("World.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(Config.MSG_MAPFILE_VALID, message);
        assertNotNull(gameMap);
    }
}
/* 
 * Risk Game Team 2
 * MapValidationTest.java
 * Version 1.0
 * Oct 18, 2017
 */
package model;

import org.junit.Before;
import org.junit.Test;
import shared_resources.game_entities.Continent;
import shared_resources.game_entities.GameMap;
import shared_resources.game_entities.Territory;
import shared_resources.helper.GameMapHelper;
import shared_resources.utilities.Config;

import static org.junit.Assert.*;

/**
 * The Class MapValidationTest.
 */
public class MapValidationTest {
    private GameMap gameMap;
    private String message;
    
    /**
     * This function setup the context for tests
     * Context include the GameMap object loaded from an arbitrary valid map file
     */
    @Before
    public void setup() {
        try {
            gameMap = GameMapHelper.loadGameMap("World.map");
            message = Config.MSG_MAPFILE_VALID;
        } catch (Exception e) {
            message = e.getMessage();
        }
    }
    
    /**
     * Test map validation when adding a new continent without any territory belong to
     */
    @Test
    public void continent_has_no_territory() {
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
        assertEquals(Config.MSG_MAPFILE_VALID, message);
        
        for (int i = 0; i < gameMap.getTerritoriesCount(); i++) {
            gameMap.removeTerritory(gameMap.getArbitraryTerritory().getName());
        }
    
        message = GameMapHelper.validateMap(gameMap);
        
        assertNotEquals(Config.MSG_MAPFILE_VALID, message);
    }
}
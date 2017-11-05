/* 
 * Risk Game Team 2
 * ReadingInvalidMapFileTest.java
 * Version 1.0
 * Oct 18, 2017
 */
package map_editor.model;

import org.junit.Before;
import org.junit.Test;
import shared_resources.game_entities.GameMap;
import shared_resources.helper.GameMapHelper;
import shared_resources.utilities.Config;

import static org.junit.Assert.*;

/**
 * The Class ReadingInvalidMapFileTest.
 */
public class ReadingInvalidMapFileTest {
    
    /** The message. */
    private String message;
    
    /** The game map. */
    private GameMap gameMap;

    /**
     * This test runs before each testcase.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        message = Config.MSG_MAPFILE_VALID;
        gameMap = null;
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
    public void continent_has_no_territory() throws Exception {
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
    public void territory_has_no_continent() throws Exception {
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
package model;

import model.game_entities.GameMap;
import model.helpers.GameMapHelper;
import org.junit.Before;
import org.junit.Test;
import utilities.Config;

import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ReadingInvalidMapFileTest {
    private String message;
    private GameMap gameMap;

    /**
     *This test runs before each testcase
     */
    @Before
    public void setUp() throws Exception {
        message = Config.MSG_MAPFILE_VALID;
        gameMap = null;
    }

    /**
     *This test checks the one way relationship
     */
    @Test
    public void one_way_relationship() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("invalid_maps/1_way_relationship.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_1_WAY_RELATIONSHIP, "kamchatka", "alaska"), message);
        assertNull(gameMap);
    }

    /**
     *This test checks if the continent has no control value
     */
    @Test
    public void continent_has_no_control_value() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("invalid_maps/continent_has_no_control_value.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, 9), message);
        assertNull(gameMap);
    }

    /**
     *This test checks the continent has no territory
     */
    @Test
    public void continent_has_no_territory() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("invalid_maps/continent_has_no_territory.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_CONTINENT_NO_TERRITORY, "asean"), message);
        assertNull(gameMap);
    }

    /**
     *This test checks if the continent has been duplicated
     */
    @Test
    public void duplicated_continent() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("invalid_maps/duplicated_continents.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_CONTINENT_DUPLICATED, 15), message);
        assertNull(gameMap);
    }

    /**
     *This test checks the if the territories has been duplicated
     */
    @Test
    public void duplicated_territories() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("invalid_maps/duplicated_territories.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_TERRITORY_DUPLICATED, 18), message);
        assertNull(gameMap);
    }

    /**
     *This test checks the if format is invalid
     */
    @Test
    public void invalid_format_1() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("invalid_maps/invalid_format_1.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
    
        assertEquals(String.format(Config.MSG_MAPFILE_CONTINENT_NOT_DEFINED, 17), message);
        assertNull(gameMap);
    }

    /**
     *This test checks the if format is invalid test 2
     */
    @Test
    public void invalid_format_2() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("invalid_maps/invalid_format_2.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, 1), message);
        assertNull(gameMap);
    }

    /**
     *This test checks the if format is invalid test 3
     */
    @Test
    public void invalid_format_3() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("invalid_maps/invalid_format_3.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, 9), message);
        assertNull(gameMap);
    }

    /**
     *This test checks the if missing coordination
     */
    @Test
    public void missing_coordination() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("invalid_maps/missing_coordination.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, 17), message);
        assertNull(gameMap);
    }

    /**
     *This test checks the if there is no continent
     */
    @Test
    public void no_continent() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("invalid_maps/no_continent.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_CONTINENT_NOT_DEFINED, 11), message);
        assertNull(gameMap);
    }

    /**
     *This test checks the if there is no neighbor
     */
    @Test
    public void no_neighbor() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("invalid_maps/no_neighbor.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_INVALID_NEIGHBORS_COUNT, "alaska", 0), message);
        assertNull(gameMap);
    }

    /**
     *This test checks if there is no territory
     */
    @Test
    public void no_territory() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("invalid_maps/no_territory.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(Config.MSG_MAPFILE_INVALID_TERRITORIES_COUNT, message);
        assertNull(gameMap);
    }

    /**
     *This test checks the if the territory has no continent
     */
    @Test
    public void territory_has_no_continent() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("invalid_maps/territory_has_no_continent.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
    
        assertEquals(String.format(Config.MSG_MAPFILE_CONTINENT_NOT_DEFINED, 17), message);
        assertNull(gameMap);
    }

    /**
     *This test checks the if there are too many neighbors
     */
    @Test
    public void too_many_neighbors() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("invalid_maps/too_many_neighbors.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_INVALID_NEIGHBORS_COUNT, "alaska", 11), message);
        assertNull(gameMap);
    }

    /**
     *This test checks the if there is undefined continent
     */
    @Test
    public void undefined_continent() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("invalid_maps/undefined_continent.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
    
        assertEquals(String.format(Config.MSG_MAPFILE_CONTINENT_NOT_DEFINED, 16), message);
        assertNull(gameMap);
    }

    /**
     *This test checks the if there is undefined territory
     */
    @Test
    public void undefined_territory() throws Exception {
        try {
            gameMap = GameMapHelper.loadGameMap("invalid_maps/undefined_territory.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(Config.MSG_MAPFILE_TERRITORY_NOT_DEFINED, message);
        assertNull(gameMap);
    }
    
    /**
     * This test checks if the map file is actually a valid map
     */
    @Test
    public void valid_map() {
        Vector<String> mapsList = GameMapHelper.getMapsInFolder(Config.MAPS_FOLDER);
        int mapIndex = (int) Math.random() * mapsList.size();
        try {
            gameMap = GameMapHelper.loadGameMap(mapsList.elementAt(mapIndex));
        } catch (Exception e) {
            message = e.getMessage();
        }
    
        assertEquals(Config.MSG_MAPFILE_VALID, message);
        assertNotNull(gameMap);
    }
}
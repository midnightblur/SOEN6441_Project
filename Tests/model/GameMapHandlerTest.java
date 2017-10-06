package model;

import org.junit.Before;
import org.junit.Test;
import util.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GameMapHandlerTest {
    private String message;
    private GameMap gameMap;
    
    @Before
    public void setUp() throws Exception {
        message = Config.MSG_MAPFILE_VALID;;
        gameMap = null;
    }
    
    @Test
    public void one_way_relationship() throws Exception {
        try {
            gameMap = GameMapHandler.loadGameMap("Maps/invalid_maps/1_way_relationship.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_1_WAY_RELATIONSHIP, "Kamchatka", "Alaska"), message);
        assertNull(gameMap);
    }
    
    @Test
    public void continent_has_no_control_value() throws Exception {
        try {
            gameMap = GameMapHandler.loadGameMap("Maps/invalid_maps/continent_has_no_control_value.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, 9), message);
        assertNull(gameMap);
    }
    
    @Test
    public void continent_has_no_territory() throws Exception {
        try {
            gameMap = GameMapHandler.loadGameMap("Maps/invalid_maps/continent_has_no_territory.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_CONTINENT_NO_TERRITORY, "Asean"), message);
        assertNull(gameMap);
    }
    
    @Test
    public void duplicated_continent() throws Exception {
        try {
            gameMap = GameMapHandler.loadGameMap("Maps/invalid_maps/duplicated_continents.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_CONTINENT_DUPLICATED, 15), message);
        assertNull(gameMap);
    }
    
    @Test
    public void duplicated_territories() throws Exception {
        try {
            gameMap = GameMapHandler.loadGameMap("Maps/invalid_maps/duplicated_territories.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_TERRITORY_DUPLICATED, 18), message);
        assertNull(gameMap);
    }
    
    @Test
    public void invalid_format_1() throws Exception {
        try {
            gameMap = GameMapHandler.loadGameMap("Maps/invalid_maps/invalid_format_1.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, 8), message);
        assertNull(gameMap);
    }
    
    @Test
    public void invalid_format_2() throws Exception {
        try {
            gameMap = GameMapHandler.loadGameMap("Maps/invalid_maps/invalid_format_2.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, 1), message);
        assertNull(gameMap);
    }
    
    @Test
    public void invalid_format_3() throws Exception {
        try {
            gameMap = GameMapHandler.loadGameMap("Maps/invalid_maps/invalid_format_3.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, 9), message);
        assertNull(gameMap);
    }
    
    @Test
    public void invalid_format_4() throws Exception {
        try {
            gameMap = GameMapHandler.loadGameMap("Maps/invalid_maps/invalid_format_4.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, 14), message);
        assertNull(gameMap);
    }
    
    @Test
    public void missing_coordination() throws Exception {
        try {
            gameMap = GameMapHandler.loadGameMap("Maps/invalid_maps/missing_coordination.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, 17), message);
        assertNull(gameMap);
    }
    
    @Test
    public void no_continent() throws Exception {
        try {
            gameMap = GameMapHandler.loadGameMap("Maps/invalid_maps/no_continent.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_CONTINENT_NOT_DEFINED, 11), message);
        assertNull(gameMap);
    }
    
    @Test
    public void no_neighbour() throws Exception {
        try {
            gameMap = GameMapHandler.loadGameMap("Maps/invalid_maps/no_neighbour.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_1_WAY_RELATIONSHIP, "Alberta", "Alaska"), message);
        assertNull(gameMap);
    }
    
    @Test
    public void no_territory() throws Exception {
        try {
            gameMap = GameMapHandler.loadGameMap("Maps/invalid_maps/no_territory.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(Config.MSG_MAPFILE_INVALID_TERRITORIES_COUNT, message);
        assertNull(gameMap);
    }
    
    @Test
    public void territory_has_no_continent() throws Exception {
        try {
            gameMap = GameMapHandler.loadGameMap("Maps/invalid_maps/territory_has_no_continent.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
    
        assertEquals(String.format(Config.MSG_MAPFILE_CONTINENT_NOT_DEFINED, 17), message);
        assertNull(gameMap);
    }
    
    @Test
    public void too_many_neighbours() throws Exception {
        try {
            gameMap = GameMapHandler.loadGameMap("Maps/invalid_maps/too_many_neighbours.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(String.format(Config.MSG_MAPFILE_INVALID_NEIGHBORS_COUNT, "Alaska"), message);
        assertNull(gameMap);
    }
    
    @Test
    public void undefined_continent() throws Exception {
        try {
            gameMap = GameMapHandler.loadGameMap("Maps/invalid_maps/undefined_continent.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
    
        assertEquals(String.format(Config.MSG_MAPFILE_CONTINENT_NOT_DEFINED, 16), message);
        assertNull(gameMap);
    }
    
    @Test
    public void undefined_territory() throws Exception {
        try {
            gameMap = GameMapHandler.loadGameMap("Maps/invalid_maps/undefined_territory.map");
        } catch (Exception e) {
            message = e.getMessage();
        }
        
        assertEquals(Config.MSG_MAPFILE_TERRITORY_NOT_DEFINED, message);
        assertNull(gameMap);
    }
}
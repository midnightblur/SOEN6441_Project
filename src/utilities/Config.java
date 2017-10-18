/*
  @file  Config.java
 * @brief 
 * 
 * 
 * 
 * @author Team 2
 * @version 1.0
 * @since  Oct 18, 2017
 * @bug No known bugs.
 */
package utilities;

import java.awt.*;

/**
 * Configuration class providing static attributes for the application.
 */
public class Config {
    
    /**
     * The states of the game encapsulated in an enum
     * The value is used to determine the advancement of the game
     * and render various functionality as needed.
     */
    public enum GAME_STATES {
        
        /** The setup. */
        SETUP(0),
        
        /** The entry menu. */
        ENTRY_MENU(1),
        
        /** The map editor. */
        MAP_EDITOR(2),
        
        /** The startup. */
        STARTUP(3),   
   /** The reinforcement. */
   // distribute territories and armies
        REINFORCEMENT(4),
        
        /** The attacking. */
        ATTACKING(5),
        
        /** The fortification. */
        FORTIFICATION(6);
        
        /** The value. */
        int value;
        
        /**
         * Instantiates a new game states.
         *
         * @param value the value
         */
        GAME_STATES(int value) {
            this.value = value;
        }
        
        /**
         * Gets the value.
         *
         * @return the value
         */
        public int getValue() {
            return value;
        }
    }
    
    /** The Constant PLAYER_COLOR. */
    public static final Color[] PLAYER_COLOR = {
            Color.BLUE,
            Color.RED,
            Color.MAGENTA.darker(),
            Color.GREEN.darker(),
            Color.DARK_GRAY,
            Color.ORANGE.darker().darker()
    };
    
    /** The Constant MAPS_MIN_TERRITORIES. */
    // Constants for reading GameMap Text File
    public static final int MAPS_MIN_TERRITORIES = 1;
    
    /** The Constant MAPS_MAX_TERRITORIES. */
    public static final int MAPS_MAX_TERRITORIES = 255;
    
    /** The Constant MAPS_MIN_CONTINENTS. */
    public static final int MAPS_MIN_CONTINENTS = 1;
    
    /** The Constant MAPS_MAX_CONTINENTS. */
    public static final int MAPS_MAX_CONTINENTS = 32;
    
    /** The Constant MAPS_MIN_NEIGHBORS. */
    public static final int MAPS_MIN_NEIGHBORS = 1;
    
    /** The Constant MAPS_MAX_NEIGHBORS. */
    public static final int MAPS_MAX_NEIGHBORS = 10;
    
    /** The Constant MAPS_EXTENSION. */
    public static final String MAPS_EXTENSION = ".map";
    
    /** The Constant MAPS_AUTHOR. */
    public static final String MAPS_AUTHOR = "author";
    
    /** The Constant MAPS_IMAGE. */
    public static final String MAPS_IMAGE = "image";
    
    /** The Constant MAPS_WRAP. */
    public static final String MAPS_WRAP = "wrap";
    
    /** The Constant MAPS_SCROLL. */
    public static final String MAPS_SCROLL = "scroll";
    
    /** The Constant MAPS_WARN. */
    public static final String MAPS_WARN = "warn";
    
    /** The Constant MAPS_FLAG_MAP. */
    public static final String MAPS_FLAG_MAP = "[Map]";
    
    /** The Constant MAPS_FLAG_CONTINENTS. */
    public static final String MAPS_FLAG_CONTINENTS = "[Continents]";
    
    /** The Constant MAPS_FLAG_TERRITORIES. */
    public static final String MAPS_FLAG_TERRITORIES = "[Territories]";
    
    /** The Constant MAPS_DELIMETER_MAP. */
    public static final String MAPS_DELIMETER_MAP = "=";
    
    /** The Constant MAPS_DELIMETER_CONTINENTS. */
    public static final String MAPS_DELIMETER_CONTINENTS = "=";
    
    /** The Constant MAPS_DELIMETER_TERRITORIES. */
    public static final String MAPS_DELIMETER_TERRITORIES = ",";
    
    /** The Constant MAPS_DEFAULT_COORDINATION. */
    public static final String MAPS_DEFAULT_COORDINATION = "0,0";
    
    /** The Constant MAPS_FOLDER. */
    public static final String MAPS_FOLDER = "Maps/";
    
    /** The Constant DEFAULT_MAP. */
    public static final String DEFAULT_MAP = "World.map";
    
    /** The Constant MAX_PIPS. */
    //Constants for dice
    public static final int MAX_PIPS = 6;
    
    /** The Constant DEFAULT_NUM_OF_PLAYERS. */
    //Constants for army in RiskGame
    public static final int DEFAULT_NUM_OF_PLAYERS = 6;
    
    /** The Constant INITIAL_ARMY_RATIO. */
    public static final double INITIAL_ARMY_RATIO = 2.75;  // 2.75 for similar gameplay as official Risk Game
    
    /** The Constant MSG_MAPFILE_ERROR_TITLE. */
    //Messages' content for users
    public static final String MSG_MAPFILE_ERROR_TITLE = "The map file has error";
    
    /** The Constant MSG_MAPFILE_VALID. */
    public static final String MSG_MAPFILE_VALID = "The file map is valid";
    
    /** The Constant MSG_MAPFILE_INVALID_FORMAT. */
    public static final String MSG_MAPFILE_INVALID_FORMAT = "Invalid format text file: line %s";
    
    /** The Constant MSG_MAPFILE_INVALID_TERRITORIES_COUNT. */
    public static final String MSG_MAPFILE_INVALID_TERRITORIES_COUNT = "Invalid number of territories. Minimum number is " + MAPS_MIN_TERRITORIES + ", maximum number is " + MAPS_MAX_TERRITORIES;
    
    /** The Constant MSG_MAPFILE_INVALID_CONTINENTS_COUNT. */
    public static final String MSG_MAPFILE_INVALID_CONTINENTS_COUNT = "Invalid number of continents. Minimum number is " + MAPS_MIN_CONTINENTS + ", maximum number is " + MAPS_MAX_CONTINENTS;
    
    /** The Constant MSG_MAPFILE_INVALID_NEIGHBORS_COUNT. */
    public static final String MSG_MAPFILE_INVALID_NEIGHBORS_COUNT = "%s has %s neighbors. Minimum number is " + MAPS_MIN_NEIGHBORS + ", maximum number is " + MAPS_MAX_NEIGHBORS;
    
    /** The Constant MSG_MAPFILE_NO_NEIGHBORS. */
    public static final String MSG_MAPFILE_NO_NEIGHBORS = "%s has no neighbor";
    
    /** The Constant MSG_MAPFILE_NO_CONTINENT. */
    public static final String MSG_MAPFILE_NO_CONTINENT = "%s doesn't belong to any continent";
    
    /** The Constant MSG_MAPFILE_DISCONNECTED_GRAPH. */
    public static final String MSG_MAPFILE_DISCONNECTED_GRAPH = "The graph is not connected";
    
    /** The Constant MSG_MAPFILE_1_WAY_RELATIONSHIP. */
    public static final String MSG_MAPFILE_1_WAY_RELATIONSHIP = "%s - %s is not a 2 ways relationship";
    
    /** The Constant MSG_MAPFILE_NO_IMAGE. */
    public static final String MSG_MAPFILE_NO_IMAGE = "The map image is missing";
    
    /** The Constant MSG_MAPFILE_CONTINENT_NOT_DEFINED. */
    public static final String MSG_MAPFILE_CONTINENT_NOT_DEFINED = "The continent is not defined: line %s";
    
    /** The Constant MSG_MAPFILE_CONTINENT_DUPLICATED. */
    public static final String MSG_MAPFILE_CONTINENT_DUPLICATED = "The continent already exist: line %s";
    
    /** The Constant MSG_MAPFILE_TERRITORY_NOT_DEFINED. */
    public static final String MSG_MAPFILE_TERRITORY_NOT_DEFINED = "A territory is not defined";
    
    /** The Constant MSG_MAPFILE_TERRITORY_DUPLICATED. */
    public static final String MSG_MAPFILE_TERRITORY_DUPLICATED = "The territory already exist: line %s";
    
    /** The Constant MSG_MAPFILE_CONTINENT_NO_TERRITORY. */
    public static final String MSG_MAPFILE_CONTINENT_NO_TERRITORY = "The %s continent has no territory";
}

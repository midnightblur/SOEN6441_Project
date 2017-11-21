/* 
 * Risk Game Team 2
 * Config.java
 * Version 1.0
 * Oct 18, 2017
 */
package shared_resources.utilities;

import game_play.view.screens.LoggingFrame;

import java.awt.*;

/**
 * Configuration class providing static attributes for the application:
 * <ul>
 * <li> The states of the game
 * <li> The players' colors
 * <li> Constants for handling *.map files
 * <li> Constants and settings for other entities of the game
 * </ul>
 *
 * @author Team 2
 * @version 1.0
 */
public class Config {
    
    // region Enumeration for player color
    /**
     * The Constant players colors.
     */
    public static final Color[] PLAYER_COLOR = {
            Color.BLUE,
            Color.RED,
            Color.MAGENTA.darker(),
            Color.GREEN.darker(),
            Color.DARK_GRAY,
            Color.ORANGE.darker().darker()
    };
    // endregion
    
    // region Constants used to read game map text file
    public static final int DEFAULT_NUM_OF_PLAYERS = 6;
    public static final int MAPS_MAX_TERRITORIES = 255;
    public static final int MAPS_MIN_CONTINENTS = 1;
    public static final int MAPS_MAX_CONTINENTS = 32;
    public static final int MAPS_MIN_TERRITORIES = 1;
    public static final int MAPS_MIN_NEIGHBORS = 1;
    public static final int MAPS_MAX_NEIGHBORS = 10;
    public static final String MAPS_EXTENSION = ".map";
    public static final String MAPS_AUTHOR = "author";
    public static final String MAPS_IMAGE = "image";
    public static final String MAPS_WRAP = "wrap";
    public static final String MAPS_SCROLL = "scroll";
    public static final String MAPS_WARN = "warn";
    public static final String MAPS_FLAG_MAP = "[Map]";
    public static final String MAPS_FLAG_CONTINENTS = "[Continents]";
    public static final String MAPS_FLAG_TERRITORIES = "[Territories]";
    public static final String MAPS_DELIMITER_MAP = "=";
    public static final String MAPS_DELIMITER_CONTINENTS = "=";
    public static final String MAPS_DELIMITER_TERRITORIES = ",";
    public static final String MAPS_DEFAULT_COORDINATION = "0,0";
    public static final String MAPS_FOLDER = "Maps/";
    public static final String MSG_MAPFILE_VALID = "The file map is valid";
    public static final String MSG_MAPFILE_INVALID_FORMAT = "Invalid format text file: line %s";
    public static final String MSG_MAPFILE_INVALID_TERRITORIES_COUNT = "Invalid number of territories. Minimum number is " + MAPS_MIN_TERRITORIES + ", maximum number is " + MAPS_MAX_TERRITORIES;
    public static final String MSG_MAPFILE_INVALID_CONTINENTS_COUNT = "Invalid number of continents. Minimum number is " + MAPS_MIN_CONTINENTS + ", maximum number is " + MAPS_MAX_CONTINENTS;
    public static final String MSG_MAPFILE_INVALID_NEIGHBORS_COUNT = "%s has %s neighbors. Minimum number is " + MAPS_MIN_NEIGHBORS + ", maximum number is " + MAPS_MAX_NEIGHBORS;
    public static final String MSG_MAPFILE_NO_CONTINENT = "%s doesn't belong to any continent";
    public static final String MSG_MAPFILE_DISCONNECTED_GRAPH = "The graph is not connected";
    public static final String MSG_MAPFILE_1_WAY_RELATIONSHIP = "%s - %s is not a 2 ways relationship";
    public static final String MSG_MAPFILE_CONTINENT_NOT_DEFINED = "The continent is not defined: line %s";
    public static final String MSG_MAPFILE_CONTINENT_DUPLICATED = "The continent already exist: line %s";
    public static final String MSG_MAPFILE_TERRITORY_NOT_DEFINED = "A territory is not defined";
    public static final String MSG_MAPFILE_TERRITORY_DUPLICATED = "The territory already exist: line %s";
    public static final String MSG_MAPFILE_CONTINENT_NO_TERRITORY = "The %s continent has no territory";
    public static final String MSG_MAPFILE_DISCONNECTED_CONTINENT = "The %s continent is disconnected sub-graph";
    public static final double INITIAL_ARMY_RATIO = 2.75;  // 2.75 for similar gameplay as official Risk Game
    // endregion
    
    // region Constants used for game play
    public static final int MAX_PIPS = 6;   // the maximum dots on one side of the die
    public static final int MIN_ARMY_TO_ATTACK = 2;
    public static final int MAX_NUM_ATK_DICE = 3;
    public static final int MAX_NUM_DEF_DICE = 2;
    public static final int MIN_CARDS_TO_TRADE = 3;
    // endregion
    
    // region Constants used for AI strategies
    public static LoggingFrame log = LoggingFrame.getInstance();    // the logging window used in game play
    // endregion
    
    // region Enumeration for game states
    
    /**
     * The states of the game encapsulated in an enum
     * The value is used to determine the advancement of the game
     * and render various functionality as needed.
     */
    public enum GAME_STATES {
        SETUP(0),
        ENTRY_MENU(1),
        MAP_EDITOR(2),
        STARTUP(3),
        PLAY(4),
        REINFORCEMENT(5),
        TRADE_CARDS(6),
        ATTACK_PREPARE(7),
        ATTACK_BATTLE(8),
        FORTIFICATION(9),
        VICTORY(10);
        
        /** The value of the game state. */
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
         * Gets the value of the game state.
         *
         * @return the value
         */
        public int getValue() {
            return value;
        }
    }
    // endregion
}

package util;

import java.awt.*;

public class Config {
    // Constants for reading GameMap Text File
    public static final int MAPS_MAX_TERRITORIES = 255;
    public static final int MAPS_MAX_CONTINENTS = 32;
    public static final int MAPS_MAX_NEIGHBORS = 10;
    public static final String MAPS_AUTHOR = "author";
    public static final String MAPS_IMAGE = "image";
    public static final String MAPS_WRAP = "wrap";
    public static final String MAPS_SCROLL = "scroll";
    public static final String MAPS_WARN = "warn";
    public static final String MAPS_YES = "yes";
    public static final String MAPS_NO = "no";
    public static final String MAPS_FLAG_MAP = "[Map]";
    public static final String MAPS_FLAG_CONTINENTS = "[Continents]";
    public static final String MAPS_FLAG_TERRITORIES = "[Territories]";
    public static final String MAPS_DELIMETER_MAP = "=";
    public static final String MAPS_DELIMETER_CONTINENTS = "=";
    public static final String MAPS_DELIMETER_TERRITORIES = ",";
    public static final String MAPS_FOLDER = "Maps/";
    public static final String MAPS_PATH = "Maps/World.map";
//    public static final String MAPS_PATH = "Maps/001_I72_Ghtroc 720.map";
//    public static final String MAPS_PATH = "Maps/99 Mens Morris.map";

    // Constants for drawing graph
    public static final int GRAPH_NODE_WIDTH = 50;
    public static final int GRAPH_NODE_HEIGHT = 50;
    public static final Color GRAPH_NODE_COLOR = Color.YELLOW;
    public static final Color GRAPH_EDGE_COLOR = Color.BLACK;
    public static final Color GRAPH_BORDER_COLOR = Color.BLACK;
    public static final int UI_FRAME_WIDTH = 1440;
    public static final int UI_FRAME_HEIGHT = 850;
    public static final double GRAPH_WIDTH_SCALE = 1.5;
    public static final double GRAPH_HEIGHT_SCALE = 2;
    public static final Color GRAPH_COLOR_P1 = Color.GREEN;
    public static final Color GRAPH_COLOR_P2 = Color.BLUE;
    public static final Color GRAPH_COLOR_P3 = Color.RED;
    public static final Color GRAPH_COLOR_P4 = Color.ORANGE;
    public static final Color GRAPH_COLOR_P5 = Color.PINK;
    public static final Color GRAPH_COLOR_P6 = Color.MAGENTA;

    // Constants for dice
    public static final int MAX_PIPS = 6;

    // Messages' content for users
    public static final String MSG_MAPFILE_ERROR_TITLE = "The map file has error";
    public static final String MSG_MAPFILE_VALID = "The file map is valid";
    public static final String MSG_MAPFILE_INVALID_FORMAT = "Invalid format text file: line %s";
    public static final String MSG_MAPFILE_TOO_MANY_TERRITORIES = "Too many territories. Maximum number is " + MAPS_MAX_TERRITORIES;
    public static final String MSG_MAPFILE_TOO_MANY_CONTINENTS = "Too many continents. Maximum number is " + MAPS_MAX_CONTINENTS;
    public static final String MSG_MAPFILE_TOO_MANY_NEIGHBORS = "%s has too many neighbors. Maximum number is " + MAPS_MAX_NEIGHBORS;
    public static final String MSG_MAPFILE_NO_NEIGHBORS = "%s has no neighbor";
    public static final String MSG_MAPFILE_DISCONNECTED_GRAPH = "The graph is not connected";
    public static final String MSG_MAPFILE_1_WAY_RELATIONSHIP = "%s - %s is not a 2 ways relationship";
    public static final String MSG_MAPFILE_NO_IMAGE = "The map image is missing";
}

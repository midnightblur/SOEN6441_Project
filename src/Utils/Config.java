package Utils;

import java.awt.*;

public class Config {
    // Constants for reading Models.GameMap Text File
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
//    public static final String MAPS_PATH = "Maps/World.map";
//    public static final String MAPS_PATH = "Maps/001_I72_Ghtroc 720.map";
    public static final String MAPS_PATH = "Maps/99 Mens Morris.map";

    // Constants for drawing graph
    public static final int GRAPH_NODE_WIDTH = 50;
    public static final int GRAPH_NODE_HEIGHT = 50;
    public static final Color GRAPH_NODE_COLOR = Color.YELLOW;
    public static final Color GRAPH_EDGE_COLOR = Color.BLACK;
    public static final Color GRAPH_BORDER_COLOR = Color.BLACK;
    public static final int GRAPH_FRAME_WIDTH = 1440;
    public static final int GRAPH_FRAME_HEIGHT = 850;
    public static final double GRAPH_WIDTH_SCALE = 1.5;
    public static final double GRAPH_HEIGHT_SCALE = 2;

    // Messages' content for users
    public static final String MSG_INVALID_MAP_FILE = "Invalid map file";
}

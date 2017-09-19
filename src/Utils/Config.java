package Utils;

import java.awt.*;

public class Config {
    // Constants for reading Models.GameMap Text File
    public static final String MAPS_FLAG_MAP = "[Map]";
    public static final String MAPS_FLAG_CONTINENTS = "[Continents]";
    public static final String MAPS_FLAG_TERRITORIES = "[Territories]";
    public static final String MAPS_DELIMETER_CONTINENTS = "=";
    public static final String MAPS_DELIMETER_TERRITORIES = ",";
    public static final String MAPS_PATH = "Maps/World.map";

    // Constants for drawing graph
    public static final int GRAPH_NODE_WIDTH = 50;
    public static final int GRAPH_NODE_HEIGHT = 50;
    public static final Color GRAPH_NODE_COLOR = Color.YELLOW;
    public static final Color GRAPH_EDGE_COLOR = Color.BLACK;
    public static final Color GRAPH_BORDER_COLOR = Color.BLACK;

    // Messages' content for users
    public static final String MSG_INVALID_MAP_FILE = "Invalid map file";
}

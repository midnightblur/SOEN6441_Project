/* 
 * Risk Game Team 2
 * GameMapHelper.java
 * Version 1.0
 * Oct 18, 2017
 */
package shared_resources.helper;

import shared_resources.game_entities.Continent;
import shared_resources.game_entities.GameMap;
import shared_resources.game_entities.Territory;
import shared_resources.utilities.Config;

import java.io.*;
import java.util.*;

/**
 * GameMapHandler is responsible for reading the map text file from computer storage
 * <ul>
 * <li> Validate the correctness of the map file
 * <li> Then store all retrieved information to a GameMap object
 * <li> In case of editing an existing map or creating a brand new one
 * <li> This class helps write map's information to a text file.
 * </ul>
 *
 * @author Team 2
 * @version 1.0
 */
public class GameMapHelper {
    // region Attributes declaration
    
    /**
     * Instantiates a new game map helper.
     */
    private GameMapHelper() {    // Intentionally make ctor private
    }
    // endregion
    
    // region Constructors
    
    /**
     * Input: map text file name path
     * Output: A GameMap object containing map's info including territories, continents, adjacency
     * Operation: read the map text file content line by line to get map info.
     *
     * @param mapName the map name
     *
     * @return the game map
     *
     * @throws Exception the exception
     */
    public static GameMap loadGameMap(String mapName) throws Exception {
        GameMap gameMap;
        BufferedReader bufferedReader;
        String mapPath = Config.MAPS_FOLDER + mapName;
        File file = new File(mapPath);
        bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        int lineCounter = 0;
        
        gameMap = new GameMap(mapName);
        Map<String, Continent> continentsMap = new HashMap<>();
        Set<String> allNeighbors = new HashSet<>(); // Used to check Territories and Neighbors declaration match
        MAP_PARTS mapParts = null;
        while ((line = bufferedReader.readLine()) != null) {
            lineCounter++;
            switch (line = line.trim()) {
                case Config.MAPS_FLAG_MAP:
                    mapParts = MAP_PARTS.MAP;
                    continue;
                case Config.MAPS_FLAG_CONTINENTS:
                    mapParts = MAP_PARTS.CONTINENTS;
                    continue;
                case Config.MAPS_FLAG_TERRITORIES:
                    mapParts = MAP_PARTS.TERRITORIES;
                    continue;
            }
            
            if (mapParts != null) {
                if (line.trim().compareTo("") != 0) {
                    line = line.trim().toLowerCase();
                    switch (mapParts) {
                        case MAP:
                            String[] lineContent = line.split(Config.MAPS_DELIMITER_MAP);
                            switch (lineContent[0].trim()) {
                                case Config.MAPS_AUTHOR:
                                case Config.MAPS_IMAGE:
                                case Config.MAPS_WRAP:
                                case Config.MAPS_SCROLL:
                                case Config.MAPS_WARN:
                                    // Intentionally do nothing
                                    break;
                            }
                            break;
                        case CONTINENTS:
                            try {
                                String[] continentInfo = line.split(Config.MAPS_DELIMITER_CONTINENTS);
                            
                            /* Check if info is missing or redundant */
                                if (continentInfo.length != 2) {
                                    throw new IllegalArgumentException(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, lineCounter));
                                }
    
                            /* Check for duplicated continents declaration */
                                if (continentsMap.containsKey(continentInfo[0].trim())) {
                                    throw new IllegalArgumentException(String.format(Config.MSG_MAPFILE_CONTINENT_DUPLICATED, lineCounter));
                                }
                                
                                Continent continent = new Continent(continentInfo[0].trim(), Integer.parseInt(continentInfo[1].trim()));
                                continentsMap.put(continent.getName(), continent);
                                gameMap.addContinent(continent);
                            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                                throw new IllegalArgumentException(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, lineCounter));
                            }
                            break;
                        case TERRITORIES:
                            String[] territoryInfo = line.split(Config.MAPS_DELIMITER_TERRITORIES);
                            
                            /* Check if the territory has no neighbors or is missing some info */
                            if (territoryInfo.length < 4) {
                                throw new IllegalArgumentException(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, lineCounter));
                            }
                            
                            /* Check if coordinate info is missing */
                            try {
                                Integer.parseInt(territoryInfo[1].trim());
                                Integer.parseInt(territoryInfo[2].trim());
                            } catch (NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException e) {
                                throw new IllegalArgumentException(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, lineCounter));
                            }
                            
                            /* Check if the continent is declared in advanced */
                            Continent continent = continentsMap.getOrDefault(territoryInfo[3].trim(), null);
                            if (continent == null) {
                                throw new IllegalArgumentException(String.format(Config.MSG_MAPFILE_CONTINENT_NOT_DEFINED, lineCounter));
                            }
                            
                            /* Check if the territory is declared in advanced */
                            if (gameMap.getATerritory(territoryInfo[0].trim()) != null) {
                                throw new IllegalArgumentException(String.format(Config.MSG_MAPFILE_TERRITORY_DUPLICATED, lineCounter));
                            }
                            
                            /* If no problem, continue to process */
                            Territory territory = new Territory(territoryInfo[0].trim(), continent.getName());
                            for (int i = 4; i < territoryInfo.length; i++) {
                                territory.addNeighbor(territoryInfo[i].trim());
                                allNeighbors.add(territoryInfo[i].trim());
                            }
                            gameMap.addTerritory(territory, true);
                            break;
                        default:
                            break;
                    }
                }
            } else {
                throw new IllegalArgumentException(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, lineCounter));
            }
        }
        
        /* Check if territories and neighbors declaration match */
        if (allNeighbors.size() != gameMap.getTerritoriesCount()) {
            throw new IllegalArgumentException(Config.MSG_MAPFILE_TERRITORY_NOT_DEFINED);
        }
        
        String validateMsg = validateMap(gameMap);
        if (validateMsg.compareTo(Config.MSG_MAPFILE_VALID) != 0) {
            throw new IllegalArgumentException(validateMsg);
        }
        
        bufferedReader.close();
        return gameMap;
    }
    // endregion
    
    // region Public methods
    
    /**
     * Validate the Map file. Check if
     * <ol>
     * <li>The map has no more than 255 territories
     * <li>The map has no more than 32 continents
     * <li>Each and every territory has the number of neighbors from 1 to 10
     * <li>Each territory has a continent
     * <li>Every relationship between territories is 2-ways
     * <li>Each continent has at least one territory
     * <li>Each and every continent is connected sub-graph
     * <li>The whole map is a connected graph
     * </ol>
     *
     * @param gameMap the game map
     *
     * @return the string
     */
    public static String validateMap(GameMap gameMap) {
        /* 1. 0 < number of territories <= 255 */
        if (gameMap.getTerritoriesCount() > Config.MAPS_MAX_TERRITORIES || gameMap.getTerritoriesCount() < Config.MAPS_MIN_TERRITORIES) {
            return Config.MSG_MAPFILE_INVALID_TERRITORIES_COUNT;
        }
        /* 2. 0 < number of continents <= 32 */
        if (gameMap.getContinentsCount() > Config.MAPS_MAX_CONTINENTS || gameMap.getContinentsCount() < Config.MAPS_MIN_CONTINENTS) {
            return Config.MSG_MAPFILE_INVALID_CONTINENTS_COUNT;
        }
        
        for (Territory territory : gameMap.getTerritories().values()) {
            /* 3. Each and every territory has the number of neighbors from 1 to 10 */
            if (territory.getNeighborsCount() > Config.MAPS_MAX_NEIGHBORS || territory.getNeighborsCount() < Config.MAPS_MIN_NEIGHBORS) {
                return String.format(Config.MSG_MAPFILE_INVALID_NEIGHBORS_COUNT, territory.getName(), territory.getNeighborsCount());
            }
            
            /* 4. Each territory has a continent */
            if (territory.getContinent().compareTo("") == 0) {
                return String.format(Config.MSG_MAPFILE_NO_CONTINENT, territory.getName());
            }
        }
        
        /* 5. Each continent has at least one territory */
        /* 6. Each and every continent is a connected sub-graph */
        for (Continent continent : gameMap.getContinents().values()) {
            if (continent.getTerritoriesCount() == 0) {
                return String.format(Config.MSG_MAPFILE_CONTINENT_NO_TERRITORY, continent.getName());
            } else if (!isConnectedGraph(gameMap, continent)) {
                return String.format(Config.MSG_MAPFILE_DISCONNECTED_CONTINENT, continent.getName());
            }
        }
        
        /* 7. The whole map is a connected graph */
        if (!isConnectedGraph(gameMap)) {
            return Config.MSG_MAPFILE_DISCONNECTED_GRAPH;
        }
        
        return Config.MSG_MAPFILE_VALID;
    }
    
    /**
     * Write a game map info to a .map text file
     *
     * @param gameMap the game map
     * @param path    the path
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writeToFile(GameMap gameMap, String path) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(path, false));
            
            /* Write Continents */
            writer.append(Config.MAPS_FLAG_CONTINENTS).append(System.lineSeparator());
            for (Continent continent : gameMap.getContinents().values()) {
                writer.append(continent.getName());
                writer.append(Config.MAPS_DELIMITER_CONTINENTS);
                writer.append(String.valueOf(continent.getControlValue()));
                writer.append(System.lineSeparator());
            }
            writer.append(System.lineSeparator());
            
            /* Write Territories */
            writer.append(Config.MAPS_FLAG_TERRITORIES).append(System.lineSeparator());
            
            for (Continent continent : gameMap.getContinents().values()) {
                for (String territoryName : continent.getTerritories()) {
                    Territory territory = gameMap.getTerritories().get(territoryName);
                    // Write Territory name
                    writer.append(territoryName);
                    
                    // Write coordination
                    writer.append(Config.MAPS_DELIMITER_TERRITORIES);
                    writer.append(Config.MAPS_DEFAULT_COORDINATION);
                    writer.append(Config.MAPS_DELIMITER_TERRITORIES);
                    
                    // Write Continent name
                    writer.append(territory.getContinent());
                    
                    // Write Neighbors name
                    for (String neighborName : territory.getNeighbors()) {
                        writer.append(Config.MAPS_DELIMITER_TERRITORIES);
                        writer.append(neighborName);
                    }
                    
                    writer.append(System.lineSeparator());
                }
                writer.append(System.lineSeparator());
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
    
    /**
     * Find the files with *.map extension in given folder
     *
     * @param directory the directory
     *
     * @return the maps in folder
     */
    public static Vector<String> getMapsInFolder(String directory) {
        Vector<String> mapFiles = new Vector<>();
        File dir = new File(directory);
        for (File file : dir.listFiles()) {
            if (file.getName().toLowerCase().endsWith((Config.MAPS_EXTENSION))) {
                mapFiles.add(file.getName());
            }
        }
        return mapFiles;
    }
    
    /**
     * The game map is supposed to be a strongly connected graph
     * Meaning there is a path from any territory to any another territory in the map
     * A path is a collection of 1-way relationship from one territory to another
     * Using Depth-First-Search algorithm to check if there is a path from one territory to all others
     * Run the algorithm with all the territory in the map
     * If all territories can use DFS to reach all other nodes, the map is strongly connected
     *
     * @param gameMap the game map
     *
     * @return true if the graph is connected, false if it is not
     */
    private static boolean isConnectedGraph(GameMap gameMap) {
        int totalNodesCount = gameMap.getTerritoriesCount();
        boolean isConnected = true;
        
        /* Run DFS on each & every node (territory) of the graph (map) */
        for (Territory territory : gameMap.getTerritories().values()) {
            Set<String> visitedNodesSet = new HashSet<>();
            DFSVisit(gameMap, visitedNodesSet, territory);
            if (visitedNodesSet.size() != totalNodesCount) {
                isConnected = false;
                break;
            }
        }
        
        return isConnected;
    }
    // endregion
    
    // region Private methods
    
    /**
     * Actual functions to run DFS recursively on nodes of graphs
     *
     * @param gameMap         the game map
     * @param visitedNodesSet the visited nodes set
     * @param territory       the territory to be visited
     */
    private static void DFSVisit(GameMap gameMap, Set<String> visitedNodesSet, Territory territory) {
        visitedNodesSet.add(territory.getName());
        for (String neighborName : territory.getNeighbors()) {
            if (!visitedNodesSet.contains(neighborName)) {
                DFSVisit(gameMap, visitedNodesSet, gameMap.getATerritory(neighborName));
            }
        }
    }
    
    /**
     * Continents are supposed to be strongly connected graphs
     * Meaning there is a path from any territory to any another territory in the continent
     * A path is a collection of 1-way relationships from one territory to another
     * Using Depth-First-Search algorithm to check if there is a path from one territory to all others
     * Run the algorithm with all the territories in the continent
     * If all territories can use DFS to reach all other nodes, the continent is strongly connected
     *
     * @param gameMap   the game map
     * @param continent the continent
     *
     * @return true if the continent is connected, false if it is not
     */
    private static boolean isConnectedGraph(GameMap gameMap, Continent continent) {
        int totalNodesCount = continent.getTerritoriesCount();
        boolean isConnected = true;
        
        /* Run DFS on each & every node (territory) of the graph (map) */
        for (String territoryName : continent.getTerritories()) {
            Set<String> visitedNodesSet = new HashSet<>();
            Territory territory = gameMap.getATerritory(territoryName);
            DFSVisit(gameMap, continent, visitedNodesSet, territory);
            if (visitedNodesSet.size() != totalNodesCount) {
                isConnected = false;
                break;
            }
        }
        
        return isConnected;
    }
    
    /**
     * Actual functions to run DFS recursively on nodes of graphs
     *
     * @param gameMap         the game map
     * @param continent       the continent
     * @param visitedNodesSet the visited nodes set
     * @param territory       the territory to be visited
     */
    private static void DFSVisit(GameMap gameMap, Continent continent, Set<String> visitedNodesSet, Territory territory) {
        visitedNodesSet.add(territory.getName());
        for (String neighborName : territory.getNeighbors()) {
            if (continent.isContain(neighborName) && !visitedNodesSet.contains(neighborName)) {
                DFSVisit(gameMap, continent, visitedNodesSet, gameMap.getATerritory(neighborName));
            }
        }
    }
    
    /**
     * The map entities
     */
    private enum MAP_PARTS {
        MAP,
        CONTINENTS,
        TERRITORIES
    }
    // endregion
}

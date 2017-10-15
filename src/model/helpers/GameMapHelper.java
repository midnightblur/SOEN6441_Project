package model.helpers;

import model.game_entities.Continent;
import model.game_entities.GameMap;
import model.game_entities.Territory;
import utilities.Config;

import java.io.*;
import java.util.*;

/**
 * GameMapHandler is responsible for reading the map text file from computer storage
 * Validate the validity of the map file
 * Then store all retrieved information to a GameMap object
 * In case of editing an existing map or creating a brand new one
 * This class helps write map's information to a text file
 */
public class GameMapHelper {
    public static Vector<String> getContinentsCountries(GameMap gameMap) {
        Vector<String> continentsCountries = new Vector<>();
        continentsCountries.add("SELECT TO EDIT/DELETE");
        continentsCountries.add("--- CONTINENTS ---");
        for (Continent continent : gameMap.getContinents().values()) {
            continentsCountries.add(continent.getName());
        }
        continentsCountries.add("--- COUNTRIES ---");
        for (Territory territory : gameMap.getTerritories().values()) {
            continentsCountries.add(territory.getName());
        }
        return continentsCountries;
    }
    
    private enum MAP_PARTS {MAP, CONTINENTS, TERRITORIES}
    
    /* Constructors */
    // Intentionally make ctor private
    private GameMapHelper() {
    }

    /* Public methods */
    
    /**
     * Input: map text file name path
     * Output: A GameMap object containing map's info including territories, continents, adjacency
     * Operation: read the map text file content line by line to get map info
     *
     * @param mapName
     *
     * @return
     *
     * @throws Exception
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
                            String[] lineContent = line.split(Config.MAPS_DELIMETER_MAP);
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
                                String[] continentInfo = line.split(Config.MAPS_DELIMETER_CONTINENTS);
                            
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
                            String[] territoryInfo = line.split(Config.MAPS_DELIMETER_TERRITORIES);
                            
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
                            gameMap.addTerritory(territory);
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
    
    /**
     * Validate the Map file. Check if
     * 1. The map has no more than 255 territories
     * 2. The map has no more than 32 continents
     * 3. Each and every territory has the number of neighbors from 1 to 10
     * 4. Each territory has a continent
     * 5. Every relationship between territories is 2-ways
     * 6. Each continent has at least one territory
     * 7. The whole map is a connected graph
     *
     *
     * @param
     *
     * @return
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
            
            /* 5. Every relationship between territories is 2-ways */
            for (String neighborName : territory.getNeighbors()) {
                Territory neighbor = gameMap.getATerritory(neighborName);
                if (!neighbor.isNeighbor(territory.getName())) {
                    return String.format(Config.MSG_MAPFILE_1_WAY_RELATIONSHIP, territory.getName(), neighborName);
                }
            }
        }
        
        /* 6. Each continent has at least one territory */
        for (Continent continent : gameMap.getContinents().values()) {
            if (continent.getTerritoriesCount() == 0) {
                return String.format(Config.MSG_MAPFILE_CONTINENT_NO_TERRITORY, continent.getName());
            }
        }
        
        /* 7. The whole map is a connected graph */
        if (!isConnectedGraph(gameMap)) {
            return Config.MSG_MAPFILE_DISCONNECTED_GRAPH;
        }
        
        return Config.MSG_MAPFILE_VALID;
    }
    
    /**
     * Write a gamemap info to a .map text file
     */
    public static void writeToFile(GameMap gameMap, String path) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(path, false));
            
            /* Write Continents */
            writer.append(Config.MAPS_FLAG_CONTINENTS + System.lineSeparator());
            for (Continent continent : gameMap.getContinents().values()) {
                writer.append(continent.getName());
                writer.append(Config.MAPS_DELIMETER_CONTINENTS);
                writer.append(String.valueOf(continent.getControlValue()));
                writer.append(System.lineSeparator());
            }
            writer.append(System.lineSeparator());
            
            /* Write Territories */
            writer.append(Config.MAPS_FLAG_TERRITORIES + System.lineSeparator());
            
            for (Continent continent : gameMap.getContinents().values()) {
                for (String territoryName : continent.getTerritories()) {
                    Territory territory = gameMap.getTerritories().get(territoryName);
                    // Write Territory name
                    writer.append(territoryName);
                    
                    // Write coordination
                    writer.append(Config.MAPS_DELIMETER_TERRITORIES);
                    writer.append(Config.MAPS_DEFAULT_COORDINATION);
                    writer.append(Config.MAPS_DELIMETER_TERRITORIES);
                    
                    // Write Continent name
                    writer.append(territory.getContinent());
                    
                    // Write Neighbors name
                    for (String neighborName : territory.getNeighbors()) {
                        writer.append(Config.MAPS_DELIMETER_TERRITORIES);
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
     * @param directory
     *
     * @return
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
    
    /* Private methods */
    
    /**
     * The game map is supposed to be a connected graph
     * Meaning there is a path between any two territories in the map
     * A path is a collection of 2-ways relationships between two neighbors
     * Using Breadth-First-Search algorithm to check if the graph is connected
     * BFS will create a new graph from any arbitrary node (territory) in the graph (map)
     * If the newly created graph has the same number of nodes as in the original graph
     * Then the original graph is connected
     *
     * @param gameMap: the GameMap object needs to be validated
     *
     * @return true if the gameMap is a connected graph, false if it is not
     */
    private static boolean isConnectedGraph(GameMap gameMap) {
        Set<String> visitedNodesSet = new HashSet<>();
        Queue<String> nodesQueue = new LinkedList<>();
        
        /* Get an arbitrary node (territory) from the graph (map) to start making a new graph using BFS */
        String arbitraryNode = gameMap.getArbitraryTerritory().getName();
        visitedNodesSet.add(arbitraryNode);
        nodesQueue.add(arbitraryNode);

        /* Runs BFS */
        while (!nodesQueue.isEmpty()) {
            Territory currentNode = gameMap.getATerritory(nodesQueue.poll());
            Iterator<String> neighborsIter = currentNode.getNeighbors().iterator();
            while (neighborsIter.hasNext()) {
                String neighborName = neighborsIter.next();
                
                if (!visitedNodesSet.contains(neighborName)) {
                    visitedNodesSet.add(neighborName);
                    nodesQueue.add(neighborName);
                }
            }
        }
        
        if (visitedNodesSet.size() == gameMap.getTerritoriesCount())
            return true;
        return false;
    }
}

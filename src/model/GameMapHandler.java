package model;

import util.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * GameMapHandler is responsible for reading the map text file from computer storage
 * Validate the validity of the map file
 * Then store all retrieved information to a GameMap object
 * In case of editing an existing map or creating a brand new one
 * This class helps write map's information to a text file
 */
public class GameMapHandler {
    /**
     * Initial instance of the map handler
     */
    private static GameMapHandler instance = null;
    
    /* Constructors */
    // Intentionally make ctor private
    private GameMapHandler() {
    }

    /* Public methods */
    
    /**
     * Static instance method to determine if an object of GameMapHandler already exists
     *
     * @return instance of the singleton object
     */
    public static GameMapHandler getInstance() {
        if (instance == null) {
            instance = new GameMapHandler();
        }
        return instance;
    }
    
    
    /**
     * Input: map text file name path
     * Output: A GameMap object containing map's info including territories, continents, adjacency
     * Operation: read the map text file content line by line to get map info
     *
     * @param filePath
     *
     * @return
     */
    public static GameMap loadGameMap(String filePath) throws Exception {
        GameMap gameMap;
        BufferedReader bufferedReader;
        File file = new File(filePath);
        bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        int lineCounter = 0;
        
        gameMap = new GameMap(filePath);
        Map<String, Continent> continentsMap = new HashMap<>();
        Set<String> allNeighbours = new HashSet<>(); // Used to check Territories and Neighbours declaration match
        while ((line = bufferedReader.readLine()) != null) {
            lineCounter++;
            switch (line.trim()) {
                case Config.MAPS_FLAG_MAP:
                    while ((line = bufferedReader.readLine()).compareTo("") != 0) {
                        lineCounter++;
                        String[] lineContent = line.split(Config.MAPS_DELIMETER_MAP);
                        switch (lineContent[0].trim()) {
                            case Config.MAPS_AUTHOR:
                            case Config.MAPS_IMAGE:
                            case Config.MAPS_WRAP:
                            case Config.MAPS_SCROLL:
                            case Config.MAPS_WARN:
                                // Intentionally do nothing
                                break;
                            default:
                                throw new IllegalArgumentException(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, lineCounter));
                        }
                    }
                    lineCounter++; // Increase line counter by 1 since it's empty line
                    break;
                case Config.MAPS_FLAG_CONTINENTS:
                    while ((line = bufferedReader.readLine()).compareTo("") != 0) {
                        lineCounter++;
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
                    }
                    lineCounter++; // Increase line counter by 1 since it's empty line
                    break;
                case Config.MAPS_FLAG_TERRITORIES:
                    while ((line = bufferedReader.readLine()) != null) {
                        lineCounter++;
                        if (line.compareTo("") != 0) {
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
                            
                            Territory territory = new Territory(territoryInfo[0].trim(), continent);
                            for (int i = 4; i < territoryInfo.length; i++) {
                                territory.addNeighbor(territoryInfo[i].trim());
                                allNeighbours.add(territoryInfo[i].trim());
                            }
                            gameMap.addTerritory(territory);
                        }
                    }
                    lineCounter++; // Increase line counter by 1 since it's empty line
                    break;
                default:
                    throw new IllegalArgumentException(String.format(Config.MSG_MAPFILE_INVALID_FORMAT, lineCounter));
            }
        }
        
        /* Check if territories and neighbours declaration match */
        if (allNeighbours.size() != gameMap.getTerritoriesCount()) {
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
     * 4. The whole map is a connected graph
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
        /* 3. Each and every territory has the number of neighbors from 1 to 10 */
        for (Territory territory : gameMap.getTerritories().values()) {
            if (territory.getNeighborsNumber() > Config.MAPS_MAX_NEIGHBORS || territory.getNeighborsNumber() < Config.MAPS_MIN_NEIGHBORS) {
                return Config.MSG_MAPFILE_INVALID_NEIGHBORS_COUNT;
            }
        }
        /* 4. The whole map is a connected graph */
        if (!isConnectedGraph(gameMap)) {
            return Config.MSG_MAPFILE_DISCONNECTED_GRAPH;
        }
        
        return Config.MSG_MAPFILE_VALID;
    }
    
    /**
     * The game map is supposed to be a connected graph
     * Meaning there is a path between any two territories in the map
     * A path is a collection of 2-ways relationships between two neighbours
     * Using Breadth-First-Search algorithm to check if the graph is connected
     * BFS will create a new graph from any arbitrary node (territory) in the graph (map)
     * If the newly created graph has the same number of nodes as in the original graph
     * Then the original graph is connected
     * @param gameMap: the GameMap object needs to be validated
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
                
                /* Check if it is a 2 ways relationship */
                Territory neighbor = gameMap.getATerritory(neighborName);
                if (!neighbor.getNeighbors().contains(currentNode.getName())) {
                    System.err.println(String.format(Config.MSG_MAPFILE_1_WAY_RELATIONSHIP, currentNode.getName(), neighborName));
                    return false;
                }
                
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

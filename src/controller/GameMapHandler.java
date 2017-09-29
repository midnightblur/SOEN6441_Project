package controller;

import model.Continent;
import model.GameMap;
import model.Territory;
import util.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GameMapHandler {
    private String validateMsg = "";
    private GameMap gameMap;

    /* Ctors & Dtors */
    public GameMapHandler(String filePath) {
        readMapFile(filePath);
    }

    /* Getters & Setters */
    public String getValidateMsg() {
        return validateMsg;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    /* Private methods */

    /**
     * Input: map text file name path
     * Output: A GameMap object containing map's info including territories, continents, adjacency
     * Operation: read the map text file content line by line to get map info
     *
     * @param filePath
     * @return
     */
    private GameMap readMapFile(String filePath) {
        BufferedReader bufferedReader = null;
        try {
            gameMap = new GameMap(filePath);
            File file = new File(filePath);
            bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            Map<String, Continent> continentsMap = new HashMap<>();
            while ((line = bufferedReader.readLine()) != null) {
                if (line.compareTo(Config.MAPS_FLAG_MAP) == 0) {
                    while ((line = bufferedReader.readLine()).compareTo("") != 0) {
                        String[] lineContent = line.split(Config.MAPS_DELIMETER_MAP);
                        switch (lineContent[0]) {
                            case Config.MAPS_AUTHOR:
                                gameMap.setAuthor(lineContent[1]);
                                break;
                            case Config.MAPS_IMAGE:
                                gameMap.setImage(lineContent[1]);
                                break;
                            case Config.MAPS_WRAP:
                                if (lineContent[1].compareTo(Config.MAPS_NO) == 0)
                                    gameMap.setWrapping(false);
                                else
                                    gameMap.setWrapping(true);
                                break;
                            case Config.MAPS_SCROLL:
                                gameMap.setScroll(lineContent[1]);
                                break;
                            case Config.MAPS_WARN:
                                if (lineContent[1].compareTo(Config.MAPS_NO) == 0)
                                    gameMap.setWarning(false);
                                else
                                    gameMap.setWarning(true);
                                break;
                            default:
                                throw new IllegalArgumentException(Config.MSG_MAPFILE_INVALID_FORMAT);
                        }
                    }
                } else if (line.compareTo(Config.MAPS_FLAG_CONTINENTS) == 0) {
                    while ((line = bufferedReader.readLine()).compareTo("") != 0) {
                        String[] continentInfo = line.split(Config.MAPS_DELIMETER_CONTINENTS);
                        Continent continent = new Continent(continentInfo[0], Integer.parseInt(continentInfo[1]));
                        continentsMap.putIfAbsent(continent.getName(), continent);
                        gameMap.addContinent(continent);
                    }
                } else if (line.compareTo(Config.MAPS_FLAG_TERRITORIES) == 0) {
                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.compareTo("") != 0) {
                            String[] territoryInfo = line.split(Config.MAPS_DELIMETER_TERRITORIES);
                            Territory territory = new Territory(territoryInfo[0], Integer.parseInt(territoryInfo[1]), Integer.parseInt(territoryInfo[2]), continentsMap.get(territoryInfo[3]));
                            for (int i = 4; i < territoryInfo.length; i++)
                                territory.addNeighbor(territoryInfo[i]);
                            gameMap.addTerritory(territory);
                        }
                    }
                }
            }

            validateMsg = validateMap();
            if (validateMsg.compareTo(Config.MSG_MAPFILE_VALID) != 0)
                throw new IllegalArgumentException(validateMsg);
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return null;
        } catch (IllegalArgumentException e) {
//            e.printStackTrace(System.err);
//            System.err.println(e.getMessage());
            return null;
        } finally {
            if (bufferedReader != null)
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
        }
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
     * @return
     */
    private String validateMap() {
        /* 1. The map has no more than 255 territories */
        if (gameMap.getTerritoriesNumber() > Config.MAPS_MAX_TERRITORIES)
            return Config.MSG_MAPFILE_TOO_MANY_TERRITORIES;
        /* 2. The map has no more than 32 continents */
        if (gameMap.getContinentsNumber() > Config.MAPS_MAX_CONTINENTS)
            return Config.MSG_MAPFILE_TOO_MANY_CONTINENTS;
        /* 3. Each and every territory has the number of neighbors from 1 to 10 */
        for (Territory territory : gameMap.getTerritories().values()) {
            if (territory.getNeighborsNumber() == 0)
                return String.format(Config.MSG_MAPFILE_NO_NEIGHBORS, territory.getName());
            if (territory.getNeighborsNumber() > Config.MAPS_MAX_NEIGHBORS)
                return Config.MSG_MAPFILE_TOO_MANY_NEIGHBORS;
        }
        /* 4. The whole map is a connected graph */
        if (!isGraphConnected())
            return Config.MSG_MAPFILE_DISCONNECTED_GRAPH;

        return Config.MSG_MAPFILE_VALID;
    }

    /**
     * Using Breadth-First-Search algorithm to check if the graph is connected
     *
     * @param
     * @return
     */
    private boolean isGraphConnected() {
        Set<String> nodeSet = new HashSet<>();
        Queue<String> nodeQueue = new LinkedList<>();

        nodeSet.add(gameMap.getArbitraryTerritory().getName());
        nodeQueue.add(gameMap.getArbitraryTerritory().getName());

        while (!nodeQueue.isEmpty()) {
            String currentNode = nodeQueue.poll();
            Territory territory = gameMap.getATerritory(currentNode);
            for (Iterator<String> iterator = territory.getNeighbors().iterator(); iterator.hasNext(); ) {
                String neighborName = iterator.next();
                if (!nodeSet.contains(neighborName)) {
                    nodeSet.add(neighborName);
                    nodeQueue.add(neighborName);
                }
            }
        }

        if (nodeSet.size() == gameMap.getTerritoriesNumber())
            return true;
        return false;
    }
}

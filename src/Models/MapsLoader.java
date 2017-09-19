package Models;

import Utils.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapsLoader {
    /**
     * Input: map text file name path
     * Output: A GameMap object containing map's info including territories, continents, adjacency
     * Operation: validate and read the text file content line by line to get map info
     * @param filePath
     * @return
     */
    public GameMap readMapFile(String filePath) {
        GameMap gameMap;
        try {
            gameMap = new GameMap(filePath);
            File file = new File(filePath);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            Map<String, Continent> continentsMap = new HashMap<>();
            while ((line = bufferedReader.readLine()) != null) {
//                if (line.compareTo(Config.MAPS_FLAG_MAP) == 0) {
//                    while ((line = bufferedReader.readLine()).compareTo("") != 0) {
//
//                    }
//                }
                if (line.compareTo(Config.MAPS_FLAG_CONTINENTS) == 0) {
                    while ((line = bufferedReader.readLine()).compareTo("") != 0) {
                        String[] continentInfo = line.split(Config.MAPS_DELIMETER_CONTINENTS);
                        Continent continent = new Continent(continentInfo[0], Integer.parseInt(continentInfo[1]));
                        continentsMap.putIfAbsent(continent.getName(), continent);
                        gameMap.addContinent(continent);
                    }
                }
                else if (line.compareTo(Config.MAPS_FLAG_TERRITORIES) == 0) {
                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.compareTo("") != 0) {
                            String[] territoryInfo = line.split(Config.MAPS_DELIMETER_TERRITORIES);
                            Territory territory = new Territory(territoryInfo[0], Integer.parseInt(territoryInfo[1]), Integer.parseInt(territoryInfo[2]), continentsMap.get(territoryInfo[3]));
                            for (int i = 4; i < territoryInfo.length; i++) {
                                territory.addNeighbor(territoryInfo[i]);
                                gameMap.addEdge(new Adjacency(territoryInfo[0], territoryInfo[i]));
                            }
                            gameMap.addTerritory(territory);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
            System.err.println(Config.MSG_INVALID_MAP_FILE);
            return null;
        }
        return gameMap;
    }
}

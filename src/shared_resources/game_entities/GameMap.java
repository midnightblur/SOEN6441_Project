/* 
 * Risk Game Team 2
 * GameMap.java
 * Version 1.0
 * Oct 18, 2017
 */
package shared_resources.game_entities;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import static shared_resources.utilities.Config.DEFAULT_NUM_OF_PLAYERS;

/**
 * GameMap class is used to store a map information read from or to write to a map text file.
 *
 * @author Team 2
 * @version 1.0
 */
public class GameMap implements Serializable {
    // region Attributes declaration
    private static final String MSG_CONTINENT_EXIST = "The %s continent already exists! No change has been made to the map";
    private static final String MSG_TERRITORY_EXIST = "The %s territory already exists! No change has been made to the map";
    private static final String MSG_TERRITORY_NOT_EXIST = "The %s territory doesn't exist!. No change has been made to the map";
    private static final String MSG_CONTINENT_NOT_EXIST = "The %s continent doesn't exist!. No change has been made to the map";
    private static final String MSG_CONTINENT_EDIT_SUCCESS = "The %s continent has been edited successfully";
    private static final String MSG_CONTINENT_ADD_SUCCESS = "The %s continent has been added successfully";
    private static final String MSG_CONTINENT_REMOVE_SUCCESS = "The %s continent has been removed successfully";
    private static final String MSG_TERRITORY_EDIT_SUCCESS = "The %s territory has been edited successfully";
    private static final String MSG_TERRITORY_ADD_SUCCESS = "The %s territory has been added successfully";
    private static final String MSG_TERRITORY_REMOVE_SUCCESS = "The %s territory has been removed successfully";
    private String mapName;
    private Map<String, Territory> territories;
    private Map<String, Continent> continents;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiates a new game map.
     *
     * @param mapName the name of the map
     */
    public GameMap(String mapName) {
        this.mapName = mapName;
        this.territories = new TreeMap<>();
        this.continents = new TreeMap<>();
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Gets the msg continent edit success.
     *
     * @return the msg continent edit success
     */
    public static String getMsgContinentEditSuccess() {
        return MSG_CONTINENT_EDIT_SUCCESS;
    }
    
    /**
     * Gets the msg continent add success.
     *
     * @return the msg continent add success
     */
    public static String getMsgContinentAddSuccess() {
        return MSG_CONTINENT_ADD_SUCCESS;
    }
    
    /**
     * Gets the msg continent remove success.
     *
     * @return the msg continent remove success
     */
    public static String getMsgContinentRemoveSuccess() {
        return MSG_CONTINENT_REMOVE_SUCCESS;
    }
    
    /**
     * Gets the msg territory edit success.
     *
     * @return the msg territory edit success
     */
    public static String getMsgTerritoryEditSuccess() {
        return MSG_TERRITORY_EDIT_SUCCESS;
    }
    
    /**
     * Gets the msg territory add success.
     *
     * @return the msg territory add success
     */
    public static String getMsgTerritoryAddSuccess() {
        return MSG_TERRITORY_ADD_SUCCESS;
    }
    
    /**
     * Gets the msg territory remove success.
     *
     * @return the msg territory remove success
     */
    public static String getMsgTerritoryRemoveSuccess() {
        return MSG_TERRITORY_REMOVE_SUCCESS;
    }
    
    /**
     * Get the game map name
     *
     * @return the game map name
     */
    public String getMapName() {
        return mapName;
    }
    
    /**
     * Takes a player object as a parameter and returns a hash map of the territories
     * that belong to that player object.
     *
     * @param player the player
     *
     * @return {@literal Map<String, Territory>}
     */
    public Map<String, Territory> getTerritoriesOfPlayer(Player player) {
        Map<String, Territory> playersTerritories = new TreeMap<>();
        for (Map.Entry<String, Territory> entry : territories.entrySet()) {
            if (player.equals(entry.getValue().getOwner())) {
                playersTerritories.put(entry.getKey(), entry.getValue());
            }
        }
        return playersTerritories;
    }
    
    /**
     * Add a new territory.
     *
     * @param territory     the territory
     * @param isReadingFile the is reading file
     *
     * @return the string
     */
    public String addTerritory(Territory territory, boolean isReadingFile) {
        /* Check if the territory already exists */
        if (territories.containsKey(territory.getName())) {
            return String.format(MSG_TERRITORY_EXIST, territory.getName());
        }
        
        /* Add the territory to its continent */
        if (territory.getContinent().compareTo("") != 0) {
            Continent continent = continents.get(territory.getContinent());
            continent.addTerritory(territory.getName());
        }
        
        /* Add the territory to be neighbor of its neighbors */
        if (!isReadingFile) {
            for (String neighborName : territory.getNeighbors()) {
                Territory neighbor = getATerritory(neighborName);
                if (neighbor != null) { // neighbor will be null in case of building new game map from a text file
                    neighbor.addNeighbor(territory.getName());
                }
            }
        }
        
        /* Add the territory to the territories list */
        territories.put(territory.getName(), territory);
        
        return String.format(MSG_TERRITORY_ADD_SUCCESS, territory.getName());
    }
    // endregion
    
    // region Public methods
    
    /**
     * Get a territory object from the game map given the territory name.
     *
     * @param territoryName String value for the name of territory.
     *
     * @return the a territory
     */
    public Territory getATerritory(String territoryName) {
        return territories.getOrDefault(territoryName, null);
    }
    
    /**
     * Update an existing territory info.
     *
     * @param oldTerritoryName the old territory name
     * @param newTerritory     the new territory
     *
     * @return the string
     */
    public String updateTerritory(String oldTerritoryName, Territory newTerritory) {
        /* Check if the territory already exists */
        if (!territories.containsKey(oldTerritoryName)) {
            return String.format(MSG_TERRITORY_NOT_EXIST, oldTerritoryName);
        }
        
        Territory oldTerritory = getATerritory(oldTerritoryName);
        
        /* Update territory from its oldContinent */
        Continent oldContinent = getAContinent(oldTerritory.getContinent());
        if (oldContinent != null) { // oldContinent might be null in case of adding new territory
            oldContinent.removeTerritory(oldTerritory.getName());
        }
        Continent newContinent = getAContinent(newTerritory.getContinent());
        newContinent.addTerritory(newTerritory.getName());
        
        /* Remove territory from its old neighbors */
        for (String neighborName : oldTerritory.getNeighbors()) {
            Territory neighbor = getATerritory(neighborName);
            neighbor.removeNeighbor(oldTerritory.getName());
        }
        
        /* Add new territory to its new neighbors */
        for (String neighborName : newTerritory.getNeighbors()) {
            Territory neighbor = getATerritory(neighborName);
            neighbor.addNeighbor(newTerritory.getName());
        }
        
        /* Update the territory in the territories list */
        territories.remove(oldTerritoryName);
        territories.put(newTerritory.getName(), newTerritory);
        
        return String.format(MSG_TERRITORY_EDIT_SUCCESS, newTerritory.getName());
    }
    
    /**
     * Get a continent object from the game map given the continent name.
     *
     * @param continentName String type value of continent name.
     *
     * @return the a continent
     */
    public Continent getAContinent(String continentName) {
        for (Continent continent : continents.values()) {
            if (continent.getName().compareTo(continentName) == 0) {
                return continent;
            }
        }
        return null;
    }
    
    /**
     * Remove a territoryName from the GameMap.
     *
     * @param territoryName the territory name
     *
     * @return the string
     */
    public String removeTerritory(String territoryName) {
        /* Check if the territoryName exists */
        if (!territories.containsKey(territoryName)) {
            return String.format(MSG_TERRITORY_NOT_EXIST, territoryName);
        }
        
        Territory territory = getATerritory(territoryName);
        /* Remove the territoryName from its continent */
        if (territory.getContinent().compareTo("") != 0) {
            Continent continent = getAContinent(territory.getContinent());
            continent.removeTerritory(territoryName);
        }
        
        /* Remove the territory from being neighbor to other territories */
        for (String neighborName : territory.getNeighbors()) {
            Territory neighbor = getATerritory(neighborName);
            neighbor.removeNeighbor(territoryName);
        }
        
        /* Remove the territory from territories list */
        territories.remove(territoryName);
        
        return String.format(MSG_TERRITORY_REMOVE_SUCCESS, territoryName);
    }
    
    /**
     * Add a new newContinent to the game map.
     *
     * @param newContinent the new continent
     *
     * @return the string
     */
    public String addContinent(Continent newContinent) {
        /* Check if the continent already exist */
        if (continents.containsKey(newContinent.getName())) {
            return String.format(MSG_CONTINENT_EXIST, newContinent.getName());
        }
        
        /* Add the continent to contain its territories */
        for (String territoryName : newContinent.getTerritories()) {
            Territory territory = territories.get(territoryName);
            Continent oldContinent = getAContinent(territory.getContinent());
            if (oldContinent != null) {
                oldContinent.removeTerritory(territory.getName());
            }
            territory.setContinent(newContinent.getName());
        }
        
        /* Add the continent to the continents list */
        continents.put(newContinent.getName(), newContinent);
        
        return String.format(MSG_CONTINENT_ADD_SUCCESS, newContinent.getName());
    }
    
    /**
     * Update info of an existing continent.
     *
     * @param oldContinentName the old continent name
     * @param newContinent     the new continent
     *
     * @return the string
     */
    public String updateContinent(String oldContinentName, Continent newContinent) {
        if (!continents.containsKey(oldContinentName)) {
            return String.format(MSG_CONTINENT_NOT_EXIST, oldContinentName);
        }
        
        Continent oldContinent = getAContinent(oldContinentName);
        
        /* Remove the continent from its current territories */
        for (String territoryName : oldContinent.getTerritories()) {
            Territory territory = getATerritory(territoryName);
            territory.removeContinent();
        }
        
        /* Set the new continent to contain its territories */
        for (String territoryName : newContinent.getTerritories()) {
            Territory territory = getATerritory(territoryName);
            Continent currentContinent = getAContinent(territory.getContinent());
            if (currentContinent != null) {
                currentContinent.removeTerritory(territoryName);
            }
            territory.setContinent(newContinent.getName());
        }
        
        continents.remove(oldContinent.getName());
        continents.put(newContinent.getName(), newContinent);
        
        return String.format(MSG_CONTINENT_EDIT_SUCCESS, newContinent.getName());
    }
    
    /**
     * Remove a continent from the game map given the continent's name.
     *
     * @param continentName the continent name
     *
     * @return the string
     */
    public String removeContinent(String continentName) {
        /* Check if the continent exist */
        if (!continents.containsKey(continentName)) {
            return String.format(MSG_CONTINENT_NOT_EXIST, continentName);
        }
        
        Continent continent = getAContinent(continentName);
        /* Remove the continent from containing its territories */
        for (String territoryName : continent.getTerritories()) {
            Territory territory = getATerritory(territoryName);
            territory.removeContinent();
        }
        
        /* Remove the continent from the continents list */
        continents.remove(continentName);
        
        return String.format(MSG_CONTINENT_REMOVE_SUCCESS, continentName);
    }
    
    /**
     * Get the number of continent in the game map.
     *
     * @return returns int value for the size of continents.
     */
    public int getContinentsCount() {
        return continents.size();
    }
    
    /**
     * Get an arbitrary territory object (the first one in the territories list).
     *
     * @return the arbitrary territory
     */
    public Territory getArbitraryTerritory() {
        return territories.values().iterator().next();
    }
    
    /**
     * Get the names of all continents in this map.
     *
     * @return a vector holding the continent names
     */
    public Vector<String> getContinentsNames() {
        Vector<String> result = new Vector<>();
        for (Continent continent : getContinents().values()) {
            result.add(continent.getName());
        }
        return result;
    }
    
    /**
     * Gets the continents.
     *
     * @return the continents
     */
    public Map<String, Continent> getContinents() {
        return continents;
    }
    
    /**
     * Get the names of all territories in this map.
     *
     * @return a vector holding the territory names
     */
    public Vector<String> getTerritoriesNames() {
        Vector<String> result = new Vector<>();
        for (Territory territory : getTerritories().values()) {
            result.add(territory.getName());
        }
        return result;
    }
    
    /**
     * Gets the territories.
     *
     * @return the territories
     */
    public Map<String, Territory> getTerritories() {
        return territories;
    }
    
    /**
     * Get the maximum number of players allowed for this map object
     * <ul>
     * <li> Maximum number of players must be the one in the configuration file
     * <li> In case there are more territories than player we reduce the players to territories count
     * <li> There should be at least one player in the game
     * </ul>.
     *
     * @return the maximum number of players allowed for this map
     */
    public int getMaxPlayers() {
        int players = DEFAULT_NUM_OF_PLAYERS;       // the maximum number of players
        int territoryCount = this.getTerritoriesCount();
        if (players > territoryCount) {             // if there are more players than territories
            players = territoryCount;               // there should be a maximum of player equal to territories or at least one player
        }
        return players;
    }
    
    /**
     * Get the number of territories in the game map.
     *
     * @return returns int value for the size of territories.
     */
    public int getTerritoriesCount() {
        return territories.size();
    }
    
    // endregion
}

package model.game_entities;

import java.util.*;

/**
 * GameMap class is used to store a map information read from or to write to a map text file
 */
public class GameMap {
    private static final String MSG_CONTINENT_EXIST = "The %s continent already exists! No change has been made to the map";
    private static final String MSG_TERRITORY_EXIST = "The %s territory already exists! No change has been made to the map";
    private static final String MSG_TERRITORY_NOT_EXIST = "The %s territory doesn't exist!. No change has been made to the map";
    private static final String MSG_CONTINENT_NOT_EXIST = "The %s continent doesn't exist!. No change has been made to the map";
    private static final String MSG_CONTINENT_EDIT_SUCCESS = "The %s continent has been edited successfully";
    private static final String MSG_CONTINENT_ADD_SUCCESS = "The %s continent has been added successfully";
    private static final String MSG_CONTINENT_REMOVE_SUCCESS = "The %s continent has been removed successfully";
    private static final String MSG_TERRITORY_ADD_SUCCESS = "The %s territory has been added successfully";
    private static final String MSG_TERRITORY_REMOVE_SUCCESS = "The %s territory has been removed successfully";
    
    /* Private data member of model.game_entities.GameMap class */
    private String mapName;
    private Map<String, Territory> territories;
    private Map<String, Continent> continents;
    
    /* Constructors */
    public GameMap(String mapName) {
        this.mapName = mapName;
        this.territories = new TreeMap<>();
        this.continents = new TreeMap<>();
    }
    
    /* Getters & Setters */
    public String getMapName() {
        return mapName;
    }
    
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
    
    public Map<String, Territory> getTerritories() {
        return territories;
    }
    
    public Map<String, Continent> getContinents() {
        return continents;
    }
    
    /* Public methods */
    
    /**
     * Takes a player object as a parameter and returns a hash map of the territories
     * that belong to that player object.
     * @param player
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
     * Add a new territory
     * @param territory
     */
    public String addTerritory(Territory territory) {
        /* Check if the territory already exists */
        if (territories.containsKey(territory.getName())) {
            return String.format(MSG_TERRITORY_EXIST, territory.getName());
        }
        
        /* Add the territory to its continent */
        if (territory.getContinent().compareTo("") != 0) {
            Continent continent = continents.get(territory.getContinent());
            continent.addTerritory(territory.getName());
        }
        
        /* Add the territory to the territories list */
        territories.put(territory.getName(), territory);
        
        return String.format(MSG_TERRITORY_ADD_SUCCESS, territory.getName());
    }
    
    /**
     * Remove a territoryName from the GameMap
     * @param territoryName
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
        
        /* Remove the territory from being neighbour to other territories */
        for (String neighbourName : territory.getNeighbors()) {
            Territory neighbour = getATerritory(neighbourName);
            neighbour.removeNeighbour(territoryName);
        }
        
        /* Remove the territory from territories list */
        territories.remove(territoryName);
        
        return String.format(MSG_TERRITORY_REMOVE_SUCCESS, territoryName);
    }
    
    /**
     * Add a new newContinent to the game map
     * @param newContinent
     */
    public String addContinent(Continent newContinent) {
        /* Check if the continent already exist */
        if (continents.containsKey(newContinent.getName())) {
            return String.format(MSG_CONTINENT_EXIST, newContinent.getName());
        }
        
        /* Add the continent to contain its territories */
        for (String territoryName : newContinent.getTerritories()) {
            Territory territory = territories.get(territoryName);
            territory.setContinent(newContinent.getName());
        }
        
        /* Add the continent to the continents list */
        continents.put(newContinent.getName(), newContinent);
        
        return String.format(MSG_CONTINENT_ADD_SUCCESS, newContinent.getName());
    }
    
    /**
     * Remove a continent from the game map given the continent's name
     * @param continentName
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
            territory.setContinent("");
        }
        
        /* Remove the continent from the continents list */
        continents.remove(continentName);
        
        return String.format(MSG_CONTINENT_REMOVE_SUCCESS, continentName);
    }
    
    /**
     * Get the number of territories in the game map
     * @return
     */
    public int getTerritoriesCount() {
        return territories.size();
    }
    
    /**
     * Get the number of continent in the game map
     * @return
     */
    public int getContinentsCount() {
        return continents.size();
    }
    
    /**
     * Get a territory object from the game map given the territory name
     * @param territoryName
     * @return
     */
    public Territory getATerritory(String territoryName) {
        return territories.getOrDefault(territoryName, null);
    }
    
    /**
     * Get a continent object from the game map given the continent name
     * @param continentName
     * @return
     */
    public Continent getAContinent(String continentName) {
        Iterator<Continent> iterator = continents.values().iterator();
        while (iterator.hasNext()) {
            Continent continent = iterator.next();
            if (continent.getName().compareTo(continentName) == 0) {
                return continent;
            }
        }
        return null;
    }
    
    /**
     * Get an arbitrary territory object (the first one in the territories list)
     * @return
     */
    public Territory getArbitraryTerritory() {
        return territories.values().iterator().next();
    }
    
    public Vector<String> getContinentsNames() {
        Vector<String> result = new Vector<>();
        for (Continent continent : getContinents().values()) {
            result.add(continent.getName());
        }
        return result;
    }
    
    public Vector<String> getTerritoriesNames() {
        Vector<String> result = new Vector<>();
        for (Territory territory : getTerritories().values()) {
            result.add(territory.getName());
        }
        return result;
    }
    
    public Vector<String> getTerritoriesByContinent(String continentName) {
        Continent continent = getAContinent(continentName);
        return continent.getTerritories();
    }
    
    public String updateContinent(String oldContinentName, Continent newContinent) {
        if (continents.containsKey(newContinent.getName())) {
            return String.format(MSG_CONTINENT_EXIST, newContinent.getName());
        } else {
            Continent oldContinent = getAContinent(oldContinentName);
            continents.put(newContinent.getName(), newContinent);
            
            for (String territoryName : oldContinent.getTerritories()) {
                Territory territory = getATerritory(territoryName);
                territory.setContinent(newContinent.getName());
            }
            
            continents.remove(oldContinent.getName());
            
            return String.format(MSG_CONTINENT_EDIT_SUCCESS, newContinent.getName());
        }
    }

}

package model.game_entities;

import java.util.*;

/**
 * GameMap class is used to store a map information read from or to write to a map text file
 */
public class GameMap {
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
        Map<String, Territory> playersTerritories = new HashMap<>();
        for (Map.Entry<String, Territory> entry : territories.entrySet())
        {
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
    public void addTerritory(Territory territory) {
        if (!territories.containsKey(territory.getName())) {
            /* Add the territory to its continent */
            Continent continent = getAContinent(territory.getContinent().getName());
            continent.addTerritory(territory);
            
            /* Add the territory to the territories list */
            territories.put(territory.getName(), territory);
        }
    }
    
    /**
     * Remove a territory from the GameMap
     * @param territoryName
     */
    public void removeTerritory(String territoryName) {
        if (territories.containsKey(territoryName)) {
            Territory territory = territories.get(territoryName);
            
            for (Continent continent : continents.values()) {
                if (continent.isContain(territory)) {
                    continent.removeTerritory(territory.getName());
                    break;
                }
            }
    
            territories.remove(territory.getName());
        }
    }
    
    /**
     * Add a new continent to the game map
     * @param continent
     */
    public void addContinent(Continent continent) {
        continents.putIfAbsent(continent.getName(), continent);
    }
    
    /**
     * Remove a continent from the game map given the continent's name
     * @param continentName
     */
    public void removeContinent(String continentName) {
        Iterator<Continent> iterator = continents.values().iterator();
        while (iterator.hasNext()) {
            Continent continent = iterator.next();
            if (continent.getName().compareTo(continentName) == 0) {
                continents.remove(continent);
            }
        }
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
    
    public Vector<Territory> getTerritoriesByContinent(String continentName) {
        Continent continent = getAContinent(continentName);
        return continent.getTerritories();
    }
    
    public void updateAContinent(String continentName) {
        Continent continent = getAContinent(continentName);
        
    }
}

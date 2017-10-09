package model.game_entities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * GameMap class is used to store a map information read from or to write to a map text file
 */
public class GameMap {
    /* Private data member of model.game_entities.GameMap class */
    private String mapName;
    private Map<String, Territory> territories;
    private Vector<Continent> continents;
    
    /* Constructors */
    public GameMap(String mapName) {
        this.mapName = mapName;
        this.territories = new HashMap<>();
        this.continents = new Vector<>();
    }
    
    /* Getters & Setters */
    public String getMapName() {
        return mapName;
    }
    
    public Map<String, Territory> getTerritories() {
        return territories;
    }
    
    public Vector<Continent> getContinents() {
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
            continent.addTerritory(territory.getName());
            
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
            territories.remove(territoryName);
            
            for (Continent continent : continents) {
                if (continent.isContain(territoryName)) {
                    continent.removeTerritory(territoryName);
                }
            }
        }
    }
    
    /**
     * Add a new continent to the game map
     * @param continent
     */
    public void addContinent(Continent continent) {
        if (!continents.contains(continent))
            continents.add(continent);
    }
    
    /**
     * Remove a continent from the game map given the continent's name
     * @param continentName
     */
    public void removeContinent(String continentName) {
        for (Iterator<Continent> iterator = continents.iterator(); iterator.hasNext(); ) {
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
        for (Iterator<Continent> iterator = continents.iterator(); iterator.hasNext(); ) {
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
}

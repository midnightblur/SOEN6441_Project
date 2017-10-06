package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * GameMap class is used to store a map information read from or to write to a map text file
 */
public class GameMap {
    /* Private data member of model.GameMap class */
    private String mapName;
    private String author;
    private boolean wrapping;
    private String scroll;
    private boolean warning;
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
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public boolean isWrapping() {
        return wrapping;
    }
    
    public void setWrapping(boolean wrapping) {
        this.wrapping = wrapping;
    }
    
    public String getScroll() {
        return scroll;
    }
    
    public void setScroll(String scroll) {
        this.scroll = scroll;
    }
    
    public boolean isWarning() {
        return warning;
    }
    
    public void setWarning(boolean warning) {
        this.warning = warning;
    }
    
    public Map<String, Territory> getTerritories() {
        return territories;
    }
    
    public Vector<Continent> getContinents() {
        return continents;
    }
    
    /* Public methods */
    public void addTerritory(Territory territory) {
        if (!territories.containsKey(territory.getName())) {
            territories.put(territory.getName(), territory);
        }
    }
    
    public void addContinent(Continent continent) {
        if (!continents.contains(continent))
            continents.add(continent);
    }
    
    public int getTerritoriesCount() {
        return territories.size();
    }
    
    public int getContinentsCount() {
        return continents.size();
    }
    
    public Territory getATerritory(String territoryName) {
        return territories.getOrDefault(territoryName, null);
    }
    
    public Continent getAContinent(String continentName) {
        for (Iterator<Continent> iterator = continents.iterator(); iterator.hasNext(); ) {
            Continent continent = iterator.next();
            if (continent.getName().compareTo(continentName) == 0)
                return continent;
        }
        return null;
    }
    
    public Territory getArbitraryTerritory() {
        return territories.values().iterator().next();
    }
}

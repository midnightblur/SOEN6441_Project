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
    private String mapPath;
    private String author;
    private boolean wrapping;
    private String scroll;
    private boolean warning;
    private Map<String, Territory> territoriesMap;
    private Vector<Continent> continentsVector;
    
    /* Constructors */
    public GameMap(String mapPath) {
        this.mapPath = mapPath;
        this.territoriesMap = new HashMap<>();
        this.continentsVector = new Vector<>();
    }
    
    /* Getters & Setters */
    public String getMapPath() {
        return mapPath;
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
    
    public Map<String, Territory> getTerritoriesMap() {
        return territoriesMap;
    }
    
    public Map<String, Territory> getTerritories() {
        return territories;
    }
    
    public Vector<Continent> getContinentsVector() {
        return continentsVector;
    }
    
    /* Public methods */
    public void addTerritory(Territory territory) {
        if (!territoriesMap.containsKey(territory.getName())) {
            territoriesMap.put(territory.getName(), territory);
        }
        
    }
    
    public void addContinent(Continent continent) {
        if (!continentsVector.contains(continent))
            continentsVector.add(continent);
    }
    
    public int getTerritoriesCount() {
        return territoriesMap.size();
    }
    
    public int getContinentsCount() {
        return continentsVector.size();
    }
    
    public Territory getATerritory(String territoryName) {
        return territoriesMap.getOrDefault(territoryName, null);
    }
    
    public Continent getAContinent(String continentName) {
        for (Iterator<Continent> iterator = continentsVector.iterator(); iterator.hasNext(); ) {
            Continent continent = iterator.next();
            if (continent.getName().compareTo(continentName) == 0)
                return continent;
        }
        return null;
    }
    
    public Territory getArbitraryTerritory() {
        return territoriesMap.values().iterator().next();
    }
}

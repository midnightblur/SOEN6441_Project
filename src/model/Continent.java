package model;

import java.util.Objects;
import java.util.Vector;

/**
 * Continent is a connected subgraph of the whole map
 * One continent contains several territories
 * Continents don't share mutual territory
 * Each continent has a control value as an integer
 */
public class Continent {
    /* Private data member of model.Continent class */
    private String name;
    private Vector<String> territories;
    private int controlValue;

    /* Constructors */
    
    /**
     * Instantiate new continent object given its name and its control value
     *
     * @param name
     * @param controlValue
     */
    public Continent(String name, int controlValue) {
        this.name = name;
        this.controlValue = controlValue;
        this.territories = new Vector<>();
    }
    
    /* Getters & Setters */
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getControlValue() {
        return controlValue;
    }
    
    public void setControlValue(int controlValue) {
        this.controlValue = controlValue;
    }
    
    public Vector<String> getTerritories() {
        return territories;
    }
    
    public void setTerritories(Vector<String> territories) {
        this.territories = territories;
    }
    
    /* Public methods */
    
    /**
     * Add a territory to the list of territories belonging to the continent
     *
     * @param territoryName
     */
    public void addTerritory(String territoryName) {
        if (!isContain(territoryName))
            territories.add(territoryName);
    }
    
    /**
     * Remove a territory from the list of territories belong to the continent
     *
     * @param territoryName
     */
    public void removeTerritory(String territoryName) {
        int i = 0;
        for (String name : territories) {
            if (name.compareTo(territoryName) == 0) {
                territories.remove(i);
            }
            i++;
        }
    }
    
    /**
     * Check if the continent contain a given territory
     *
     * @param territoryName
     *
     * @return
     */
    public boolean isContain(String territoryName) {
        return (territories.contains(territoryName));
    }
    
    /**
     * Get the number of territories belong to the continent
     *
     * @return
     */
    public int getTerritoriesCount() {
        return territories.size();
    }
    
    /**
     * Calculate the number of armies within the territory
     *
     * @return
     */
    public int getContinentArmies() {
        int count = 0;
        for (String t : territories) {
            count += RiskGame.getInstance().getGameMap().getATerritory(t).getArmies();
        }
        return count;
    }
    
    /**
     * Determine if a continent was fully conquered and return the id of the owner if so
     *
     * @return the id of the owner having conquered all territories within this continent
     */
    public String getContinentOwner() {
        String continentOwner = "Player " + RiskGame.getInstance().getGameMap().getATerritory(territories.firstElement()).getOwner().getPlayerID();
        for (String t : territories) {
            if (!Objects.equals(continentOwner, "Player " + RiskGame.getInstance().getGameMap().getATerritory(t).getOwner().getPlayerID()))
                return "nobody owns it yet";
        }
        return continentOwner;
        
    }
    
    /**
     * Facilitate the comparision between two continent objects
     *
     * @param other
     *
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Continent))
            return false;
        
        Continent continent = (Continent) other;
        if (this.name.compareTo(continent.name) == 0)
            return true;
        
        return false;
    }
}

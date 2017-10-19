/* 
 * Risk Game Team 2
 * Continent.java
 * Version 1.0
 * Oct 18, 2017
 */
package model.game_entities;

import model.ui_models.GamePlayModel;

import java.util.Objects;
import java.util.Vector;

/**
 * Continent is a connected subgraph of the whole map
 * One continent contains several territories
 * Continents don't share mutual territory
 * Each continent has a control index as an integer.
 *
 * @author Team 2
 * @version 1.0
 */
public class Continent {
    
    //region Attributes declaration
    /** The name. */
    private String name;
    
    /** The territories. */
    private Vector<String> territories;
    
    /** The control value. */
    private int controlValue;
    //endregion

    /**
     * Instantiate new continent object given its name and its control index.
     *
     * @param name the name
     * @param controlValue the control value
     */
    public Continent(String name, int controlValue) {
        this.name = name;
        this.controlValue = controlValue;
        this.territories = new Vector<>();
    }
    //endregion
    
    //region Getters & Setters
    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the control value.
     *
     * @return the control value
     */
    public int getControlValue() {
        return controlValue;
    }
    
    /**
     * Sets the control value.
     *
     * @param controlValue the new control value
     */
    public void setControlValue(int controlValue) {
        this.controlValue = controlValue;
    }
    
    /**
     * Gets the territories.
     *
     * @return the territories
     */
    public Vector<String> getTerritories() {
        return territories;
    }
    
    /**
     * Sets the territories.
     *
     * @param territories the new territories
     */
    public void setTerritories(Vector<String> territories) {
        this.territories = territories;
    }
    //endregion
    
    //region Public methods
    /**
     * Add a territoryName to the list of territories belonging to the continent.
     *
     * @param territoryName name of the territory
     */
    public void addTerritory(String territoryName) {
        if (!territories.contains(territoryName)) {
            territories.add(territoryName);
        }
    }
    
    /**
     * Remove a territory from the list of territories belong to the continent.
     *
     * @param territoryName the territory name
     */
    public void removeTerritory(String territoryName) {
        for (String territory : territories) {
            if (territory.compareTo(territoryName) == 0) {
                territories.remove(territory);
                return;
            }
        }
    }
    
    /**
     * Check if the continent contain a given territoryName.
     *
     * @param territoryName the territory name
     * @return true, if is contain
     */
    public boolean isContain(String territoryName) {
        return (territories.contains(territoryName));
    }
    
    /**
     * Get the number of territories belong to the continent.
     *
     * @return the territories count
     */
    public int getTerritoriesCount() {
        return territories.size();
    }
    
    /**
     * Calculate the number of armies within the continent.
     *
     * @return the count of all armies within a given continent
     * @throws NullPointerException if territories are not yet allocated to players
     */
    public int getContinentArmies() throws NullPointerException {
        int count = 0;
        for (String territoryName : territories) {
            count += GamePlayModel.getInstance().getGameMap().getATerritory(territoryName).getArmies();
        }
        return count;
    }
    
    /**
     * Determine if a continent was fully conquered and return the id of the owner if so.
     *
     * @return the int value of the id of the owner having conquered all territories within this continent, 0  if none
     * @throws NullPointerException if territories are not yet allocated to players
     */
    public String getContinentOwner() throws NullPointerException {
        String continentOwner;
        if (GamePlayModel.getInstance().getGameMap().getATerritory(territories.get(0)).isOwned()) {
            continentOwner = GamePlayModel.getInstance().getGameMap().getATerritory(territories.get(0)).getOwner().getPlayerName();
            for (String territoryName : territories) {
                if (!Objects.equals(continentOwner, GamePlayModel.getInstance().getGameMap().getATerritory(territoryName).getOwner().getPlayerName())) {
                    return "";
                }
            }
        }
        return  GamePlayModel.getInstance().getGameMap().getATerritory(territories.get(0)).getOwner().getPlayerName();
    }
    
    /**
     * Facilitate the comparision between two continent objects.
     *
     * @param other the object of the type Continent.
     * @return true, if successful
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
    //endregion
}

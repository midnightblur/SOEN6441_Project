package model;

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
    private Vector<String> territories; // might have many territories inside, use integer id to save memory
    private int controlValue;

    /* Constructors */
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

    /* Public methods */
    public void addTerritory(String territoryName) {
        if (!isContain(territoryName))
            territories.add(territoryName);
    }

    public boolean isContain(String territoryName) {
        return (territories.contains(territoryName));
    }
    
    public int getTerritoriesCount() {
        return territories.size();
    }

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

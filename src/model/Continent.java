package model;

import java.util.Vector;

/**
 *
 */
public class Continent {
    // Private data member of model.Continent class
    private String name;
    private Vector<String> territories; // might have many territories inside, use integer id to save memory
    private int controlValue;

    // Getters & Setters
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

    // Public methods
    public Continent(String name, int controlValue) {
        this.name = name;
        this.controlValue = controlValue;
        this.territories = new Vector<>();
    }

    public void addTerritory(String territoryName) {
        if (!territories.contains(territoryName))
            territories.add(territoryName);
    }

    public boolean isContain(String territoryName) {
        return (territories.contains(territoryName));
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

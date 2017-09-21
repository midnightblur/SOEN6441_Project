package Models;

import java.util.Vector;

/**
 * A territory/country in the map
 * Represented by a node in the graph
 * A territory must belong to one and only one continent
 * A territory must be owned by one and only one player at a time
 * A territory must have at least one neighbor/adjacent territory
 * A territory must contain one or more armies
 */
public class Territory {
    /**
     * The coordinate of the node representing the territory
     * Helps locate the position of the node in the graph
     */
    private class Coordinate {
        private int x, y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    // Private data members of Models.Territory class
    private String name;
    private Coordinate coordinate;
    private Continent continent;
    private Player owner;
    private int armies;
    private Vector<String> neighbors; // might have many neighbors, use integer id to save memory

    // Getters & Setters
    public String getName() {
        return name;
    }

    public int getX() {
        return coordinate.getX();
    }

    public int getY() {
        return coordinate.getY();
    }

    public Continent getContinent() {
        return continent;
    }

    public Player getOwner() {
        return owner;
    }

    public int getArmies() {
        return armies;
    }

    public Vector<String> getNeighbors() {
        return neighbors;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setArmies(int armies) {
        this.armies = armies;
    }

    // Public methods
    public Territory(String name, int xLoc, int yLoc, Continent continent) {
        this.name = name;
        this.coordinate = new Coordinate(xLoc, yLoc);
        this.continent = continent;
        this.neighbors = new Vector<>();
        this.owner = new Player();
    }

    public void addNeighbor(String territoryID) {
        if (!neighbors.contains(territoryID)) neighbors.add(territoryID);
    }

    public boolean isNeighbor(int territoryID) {
        return (neighbors.contains(territoryID));
    }

    public boolean belongToContinent(Continent continent) {
        return (this.continent == continent);
    }

    public boolean isOwnedBy(int playerID) {
        return (owner.getID() == playerID);
    }

    public void addArmies(int addedArmies) throws IllegalArgumentException {
        if (addedArmies > 0)
            armies += addedArmies;
        else
            throw new IllegalArgumentException();
    }

    public void reduceArmies(int deductedArmies) throws IllegalArgumentException {
        if (armies > 0)
            armies -= deductedArmies;
        else
            throw new IllegalArgumentException();
    }

    public int getNeighborsNumber() {
        return neighbors.size();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Territory))
            return false;

        Territory territory = (Territory) other;
        if (this.name.compareTo(territory.name) == 0)
            return true;

        return false;
    }
}

package Models;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class GameMap {
    // Private data member of Models.GameMap class
    private String mapName;
    private Map<String, Territory> territories;
    private Vector<Adjacency> adjacencies;
    private Vector<Continent> continents;

    // Public methods
    public GameMap(String mapName) {
        this.mapName = mapName;
        this.territories = new HashMap<>();
        this.adjacencies = new Vector<>();
        this.continents = new Vector<>();
    }

    public Map<String, Territory> getTerritories() {
        return territories;
    }

    public Vector<Adjacency> getAdjacencies() {
        return adjacencies;
    }

    public Vector<Continent> getContinents() {
        return continents;
    }

    public String getMapName() {
        return mapName;
    }

    public void addEdge(Adjacency adjacency) {
        if (!adjacencies.contains(adjacency))
            adjacencies.add(adjacency);
    }

    public void removeEdge(Adjacency adjacency) {
        if (adjacencies.contains(adjacency))
            adjacencies.remove(adjacency);
    }

    public void addTerritory(Territory territory) {
        territories.putIfAbsent(territory.getName(), territory);
    }

    public void removeTerritory(Territory territory) {
        if (territories.containsKey(territory.getName()))
            territories.remove(territory);
    }

    public void addContinent(Continent continent) {
        if (!continents.contains(continent))
            continents.add(continent);
    }
}

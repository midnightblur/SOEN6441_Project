/* 
 * Risk Game Team 2
 * MapEditorModel.java
 * Version 1.0
 * Oct 18, 2017
 */
package map_editor.model;

import shared_resources.game_entities.Continent;
import shared_resources.game_entities.GameMap;
import shared_resources.game_entities.Territory;
import shared_resources.helper.GameMapHelper;
import game_play.model.DropDownModel;
import game_play.model.MapTableModel;
import shared_resources.utilities.Config;

import java.util.Observable;
import java.util.Vector;

/**
 * The Map Editor game_entities class encapsulating the logic for editing *.map files
 */
public class MapEditorModel extends Observable {
    
    /** The Constant CREATE_NEW_CONTINENT_ITEM. */
    private static final String CREATE_NEW_CONTINENT_ITEM = "<Create New Continent>";
    
    /** The Constant CREATE_NEW_TERRITORY_ITEM. */
    private static final String CREATE_NEW_TERRITORY_ITEM = "<Create New Territory>";
    
    /** The game map. */
    private GameMap gameMap;
    
    /** The map table game_entities. */
    private MapTableModel mapTableModel;
    
    /** The map dropdown game_entities. */
    private DropDownModel mapDropdownModel;
    
    /** The continents dropdown game_entities. */
    private DropDownModel continentsDropdownModel;
    
    /** The territories dropdown game_entities. */
    private DropDownModel territoriesDropdownModel;
    
    /* Constructors */
    
    /**
     * Constructor instantiating a MapEditorModel object.
     */
    public MapEditorModel() {
        mapTableModel = new MapTableModel();
    }
    
    /**
     * Load new game map.
     *
     * @param mapName the map name
     * @throws Exception the exception
     */
    public void loadNewGameMap(String mapName) throws Exception {
        gameMap = GameMapHelper.loadGameMap(mapName);
        updateModels();
        broadcastGameMapChanges();
    }
    
    /**
     * Gets the game map.
     *
     * @return the game map
     */
    /* Getters & Setters */
    public GameMap getGameMap() {
        return gameMap;
    }
    
    /**
     * Gets the map table game_entities.
     *
     * @return the map table game_entities
     */
    public MapTableModel getMapTableModel() {
        return mapTableModel;
    }
    
    /**
     * Gets the map dropdown game_entities.
     *
     * @return the map dropdown game_entities
     */
    public DropDownModel getMapDropdownModel() {
        return mapDropdownModel;
    }
    
    /**
     * Gets the continents dropdown game_entities.
     *
     * @return the continents dropdown game_entities
     */
    public DropDownModel getContinentsDropdownModel() {
        return continentsDropdownModel;
    }
    
    /**
     * Gets the territories dropdown game_entities.
     *
     * @return the territories dropdown game_entities
     */
    public DropDownModel getTerritoriesDropdownModel() {
        return territoriesDropdownModel;
    }
    
    /**
     * Gets the creates the new continent item.
     *
     * @return the creates the new continent item
     */
    public static String getCreateNewContinentItem() {
        return CREATE_NEW_CONTINENT_ITEM;
    }
    
    /**
     * Gets the creates the new territory item.
     *
     * @return the creates the new territory item
     */
    public static String getCreateNewTerritoryItem() {
        return CREATE_NEW_TERRITORY_ITEM;
    }
    
    /**
     * Adds the new continent.
     *
     * @param continent the continent
     * @return the string
     */
    /* Public methods */
    public String addNewContinent(Continent continent) {
        String result = gameMap.addContinent(continent);
        if (result.compareTo(String.format(GameMap.getMsgContinentAddSuccess(), continent.getName())) == 0) {
            updateModels();
            broadcastGameMapChanges();
        }
        return result;
    }
    
    /**
     * Update continent.
     *
     * @param oldContinentName the old continent name
     * @param newContinent the new continent
     * @return the string
     */
    public String updateContinent(String oldContinentName, Continent newContinent) {
        String result = gameMap.updateContinent(oldContinentName, newContinent);
        if (result.compareTo(String.format(GameMap.getMsgContinentEditSuccess(), newContinent.getName())) == 0) {
            updateModels();
            broadcastGameMapChanges();
        }
        return result;
    }
    
    /**
     * Removes the continent.
     *
     * @param continentName the continent name
     * @return the string
     */
    public String removeContinent(String continentName) {
        String result = gameMap.removeContinent(continentName);
        if (result.compareTo(String.format(GameMap.getMsgContinentRemoveSuccess(), continentName)) == 0) {
            updateModels();
            broadcastGameMapChanges();
        }
        return result;
    }
    
    /**
     * Adds the new territory.
     *
     * @param territory the territory
     * @return the string
     */
    public String addNewTerritory(Territory territory) {
        String result = gameMap.addTerritory(territory, false);
        if (result.compareTo(String.format(GameMap.getMsgTerritoryAddSuccess(), territory.getName())) == 0) {
            updateModels();
            broadcastGameMapChanges();
        }
        return result;
    }
    
    /**
     * Update territory.
     *
     * @param oldTerritoryName the old territory name
     * @param newTerritory the new territory
     * @return the string
     */
    public String updateTerritory(String oldTerritoryName, Territory newTerritory) {
        String result = gameMap.updateTerritory(oldTerritoryName, newTerritory);
        if (result.compareTo(String.format(GameMap.getMsgTerritoryEditSuccess(), newTerritory.getName())) == 0) {
            updateModels();
            broadcastGameMapChanges();
        }
        return result;
    }
    
    /**
     * Removes the territory.
     *
     * @param territoryName the territory name
     * @return the string
     */
    public String removeTerritory(String territoryName) {
        String result = gameMap.removeTerritory(territoryName);
        if (result.compareTo(String.format(GameMap.getMsgTerritoryRemoveSuccess(), territoryName)) == 0) {
            updateModels();
            broadcastGameMapChanges();
        }
        return result;
    }
    
    /**
     * Inits the new map.
     */
    public void initNewMap() {
        gameMap = new GameMap();
        updateModels();
        broadcastGameMapChanges();
    }
    
    /* Private methods */
    
    /**
     * This method Updates the MapTableModel and notifies the Observer.
     */
    private void broadcastGameMapChanges() {
        setChanged();
        notifyObservers();
    }
    
    /**
     * Update models.
     */
    private void updateModels() {
        mapTableModel.updateMapTableModel(gameMap, Config.GAME_STATES.MAP_EDITOR);
        updateListOfMaps();
        updateListOfContinents();
        updateListOfTerritories();
    }
    
    /**
     * Update list of maps.
     */
    public void updateListOfMaps() {
        Vector<String> mapList = new Vector<>();
        mapList.addAll(GameMapHelper.getMapsInFolder(Config.MAPS_FOLDER));
        mapDropdownModel = new DropDownModel(mapList);
    }
    
    /**
     * Update list of continents.
     */
    private void updateListOfContinents() {
        Vector<String> continentsList = new Vector<>();
        continentsList.add(CREATE_NEW_CONTINENT_ITEM);
        continentsList.addAll(gameMap.getContinentsNames());
        continentsDropdownModel = new DropDownModel(continentsList);
    }
    
    /**
     * Update list of territories.
     */
    private void updateListOfTerritories() {
        Vector<String> territoriesList = new Vector<>();
        territoriesList.add(CREATE_NEW_TERRITORY_ITEM);
        territoriesList.addAll(gameMap.getTerritoriesNames());
        territoriesDropdownModel = new DropDownModel(territoriesList);
    }
    
}

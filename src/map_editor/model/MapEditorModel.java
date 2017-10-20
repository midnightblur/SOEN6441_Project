/* 
 * Risk Game Team 2
 * MapEditorModel.java
 * Version 1.0
 * Oct 18, 2017
 */
package map_editor.model;

import game_play.model.DropDownModel;
import game_play.model.MapTableModel;
import shared_resources.game_entities.Continent;
import shared_resources.game_entities.GameMap;
import shared_resources.game_entities.Territory;
import shared_resources.helper.GameMapHelper;
import shared_resources.utilities.Config;

import java.util.Observable;
import java.util.Vector;

/**
 * The Map Editor Model encapsulates the logic for editing *.map files
 *
 * @author Team 2
 * @version 1.0
 */
public class MapEditorModel extends Observable {
    // region Attributes declaration
    private static final String CREATE_NEW_CONTINENT_ITEM = "<Create New Continent>";
    private static final String CREATE_NEW_TERRITORY_ITEM = "<Create New Territory>";
    private GameMap gameMap;
    private MapTableModel mapTableModel;
    private DropDownModel mapDropdownModel;
    private DropDownModel continentsDropdownModel;
    private DropDownModel territoriesDropdownModel;
    // endregion
    
    // region Constructors
    
    /**
     * Constructor instantiating a MapEditorModel object.
     *
     * @see MapTableModel
     */
    public MapEditorModel() {
        mapTableModel = new MapTableModel();
    }
    // endregion
    
    // region Getters & Setters
    
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
     * Gets the game map.
     *
     * @return the game map
     */
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
    // endregion
    
    // region Public methods
    
    public void loadNewGameMap(String mapName) throws Exception {
        gameMap = GameMapHelper.loadGameMap(mapName);
        updateModels();
        broadcastGameMapChanges();
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
     * This method Updates the MapTableModel and notifies the Observer.
     */
    private void broadcastGameMapChanges() {
        setChanged();
        notifyObservers();
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
    
    /**
     * Adds the new continent.
     *
     * @param continent the continent
     *
     * @return the string
     */
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
     * @param newContinent     the new continent
     *
     * @return result the string
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
     *
     * @return result the string
     */
    public String removeContinent(String continentName) {
        String result = gameMap.removeContinent(continentName);
        if (result.compareTo(String.format(GameMap.getMsgContinentRemoveSuccess(), continentName)) == 0) {
            updateModels();
            broadcastGameMapChanges();
        }
        return result;
    }
    // endregion
    
    // region Private methods
    
    /**
     * Adds the new territory.
     *
     * @param territory the territory
     *
     * @return result the string
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
     * @param newTerritory     the new territory
     *
     * @return result the string
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
     *
     * @return result the string
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
     * Initialize a new Game Map, update the models and broadcast the changes to observers.
     */
    public void initNewMap() {
        gameMap = new GameMap("");
        updateModels();
        broadcastGameMapChanges();
    }
    // endregion
}

package model.ui_models;

import model.game_entities.Continent;
import model.game_entities.GameMap;
import model.game_entities.Territory;
import model.helpers.GameMapHelper;
import utilities.Config;

import java.util.Observable;
import java.util.Vector;

/**
 * The Map Editor model class encapsulating the logic for editing *.map files
 */
public class MapEditorModel extends Observable {
    private static final String CREATE_NEW_CONTINENT_ITEM = "<Create New Continent>";
    private static final String CREATE_NEW_TERRITORY_ITEM = "<Create New Territory>";
    private GameMap gameMap;
    private MapTableModel mapTableModel;
    private DropDownModel mapDropdownModel;
    private DropDownModel continentsDropdownModel;
    private DropDownModel territoriesDropdownModel;
    
    /* Constructors */
    
    /**
     * Constructor instantiating a MapEditorModel object
     */
    public MapEditorModel() {
        mapTableModel = new MapTableModel();
    }
    
    public void loadNewGameMap(String mapName) throws Exception {
        gameMap = GameMapHelper.loadGameMap(mapName);
        updateModels();
        broadcastGameMapChanges();
    }
    
    /* Getters & Setters */
    public GameMap getGameMap() {
        return gameMap;
    }
    
    public MapTableModel getMapTableModel() {
        return mapTableModel;
    }
    
    public DropDownModel getMapDropdownModel() {
        return mapDropdownModel;
    }
    
    public DropDownModel getContinentsDropdownModel() {
        return continentsDropdownModel;
    }
    
    public DropDownModel getTerritoriesDropdownModel() {
        return territoriesDropdownModel;
    }
    
    public static String getCreateNewContinentItem() {
        return CREATE_NEW_CONTINENT_ITEM;
    }
    
    public static String getCreateNewTerritoryItem() {
        return CREATE_NEW_TERRITORY_ITEM;
    }
    
    /* Public methods */
    public String addNewContinent(Continent continent) {
        String result = gameMap.addContinent(continent);
        if (result.compareTo(String.format(GameMap.getMsgContinentAddSuccess(), continent.getName())) == 0) {
            updateModels();
            broadcastGameMapChanges();
        }
        return result;
    }
    
    public String updateContinent(String oldContinentName, Continent newContinent) {
        String result = gameMap.updateContinent(oldContinentName, newContinent);
        if (result.compareTo(String.format(GameMap.getMsgContinentEditSuccess(), newContinent.getName())) == 0) {
            updateModels();
            broadcastGameMapChanges();
        }
        return result;
    }
    
    public String removeContinent(String continentName) {
        String result = gameMap.removeContinent(continentName);
        if (result.compareTo(String.format(GameMap.getMsgContinentRemoveSuccess(), continentName)) == 0) {
            updateModels();
            broadcastGameMapChanges();
        }
        return result;
    }
    
    public String addNewTerritory(Territory territory) {
        String result = gameMap.addTerritory(territory, false);
        if (result.compareTo(String.format(GameMap.getMsgTerritoryAddSuccess(), territory.getName())) == 0) {
            updateModels();
            broadcastGameMapChanges();
        }
        return result;
    }
    
    public String updateTerritory(String oldTerritoryName, Territory newTerritory) {
        String result = gameMap.updateTerritory(oldTerritoryName, newTerritory);
        if (result.compareTo(String.format(GameMap.getMsgTerritoryEditSuccess(), newTerritory.getName())) == 0) {
            updateModels();
            broadcastGameMapChanges();
        }
        return result;
    }
    
    public String removeTerritory(String territoryName) {
        String result = gameMap.removeTerritory(territoryName);
        if (result.compareTo(String.format(GameMap.getMsgTerritoryRemoveSuccess(), territoryName)) == 0) {
            updateModels();
            broadcastGameMapChanges();
        }
        return result;
    }
    
    public void initNewMap() {
        gameMap = new GameMap();
        updateModels();
        broadcastGameMapChanges();
    }
    
    /* Private methods */
    
    /**
     * Update the MapTableModel and notify the Observer
     */
    private void broadcastGameMapChanges() {
        setChanged();
        notifyObservers();
    }
    
    private void updateModels() {
        mapTableModel.updateMapTableModel(gameMap);
        updateListOfMaps();
        updateListOfContinents();
        updateListOfTerritories();
    }
    
    public void updateListOfMaps() {
        Vector<String> mapList = new Vector<>();
        mapList.addAll(GameMapHelper.getMapsInFolder(Config.MAPS_FOLDER));
        mapDropdownModel = new DropDownModel(mapList);
    }
    
    private void updateListOfContinents() {
        Vector<String> continentsList = new Vector<>();
        continentsList.add(CREATE_NEW_CONTINENT_ITEM);
        continentsList.addAll(gameMap.getContinentsNames());
        continentsDropdownModel = new DropDownModel(continentsList);
    }
    
    private void updateListOfTerritories() {
        Vector<String> territoriesList = new Vector<>();
        territoriesList.add(CREATE_NEW_TERRITORY_ITEM);
        territoriesList.addAll(gameMap.getTerritoriesNames());
        territoriesDropdownModel = new DropDownModel(territoriesList);
    }
    
}

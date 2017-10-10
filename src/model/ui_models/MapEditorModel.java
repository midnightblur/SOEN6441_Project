package model.ui_models;

import model.game_entities.Continent;
import model.game_entities.GameMap;
import model.game_entities.Territory;
import model.helpers.GameMapHelper;

import java.util.Observable;
import java.util.Vector;

public class MapEditorModel extends Observable {
    private static final String CREATE_NEW_CONTINENT_ITEM = "<Create New Continent>";
    private static final String CREATE_NEW_TERRITORY_ITEM = "<Create New Territory>";
    private GameMap gameMap;
    private MapTableModel mapTableModel;
    private DropDownModel continentsDropdownModel;
    private DropDownModel territoriesDropdownModel;
    
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
    
    public String addNewTerritory(Territory territory) {
        String result = gameMap.addTerritory(territory);
        if (result.compareTo(String.format(GameMap.getMsgTerritoryAddSuccess(), territory.getName())) == 0) {
            updateModels();
            broadcastGameMapChanges();
        }
        return result;
    }
    
    /* Private methods */
    /**
     * Update the MapTableModel and notify the Observer
     */
    private void broadcastGameMapChanges() {
        setChanged();
        notifyObservers();
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
    
    private void updateModels() {
        mapTableModel.updateMapTableModel(gameMap);
        updateListOfContinents();
        updateListOfTerritories();
    }
}

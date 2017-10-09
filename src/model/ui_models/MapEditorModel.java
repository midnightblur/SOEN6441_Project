package model.ui_models;

import model.game_entities.GameMap;
import model.helpers.GameMapHandler;

import java.util.Observable;

public class MapEditorModel extends Observable {
    /* Models */
    private GameMap gameMap;
    private MapTableModel mapTableModel;
    private DropDownModel continentsDropdownModel;
    
    public MapEditorModel() {
        this.mapTableModel = new MapTableModel();
    }
    
    public void loadNewGameMap(String mapName) throws Exception {
        gameMap = GameMapHandler.loadGameMap(mapName);
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
    
    /**
     * Update the MapTableModel and notify the Observer
     */
    private void broadcastGameMapChanges() {
        mapTableModel.updateMapTableModel(gameMap);
        setChanged();
        notifyObservers();
    }
}

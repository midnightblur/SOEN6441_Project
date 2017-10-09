package model;

import model.game_entities.GameMap;
import model.helpers.GameMapHandler;

import java.util.Observable;

public class MapEditorModel extends Observable {
    /* Models */
    private GameMap gameMap;
    private MapTableModel mapTableModel;
    
    public MapEditorModel() {
        this.mapTableModel = new MapTableModel();
    }
    
    public void updateGameMap(String mapName) throws Exception {
        gameMap = GameMapHandler.loadGameMap(mapName);
        mapTableModel.updateMapTableModel(gameMap);
        setChanged();
        notifyObservers();
    }
    
    public GameMap getGameMap() {
        return gameMap;
    }
    
    public MapTableModel getMapTableModel() {
        return mapTableModel;
    }
}

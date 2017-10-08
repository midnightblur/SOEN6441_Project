package model;

import java.util.Observable;

public class MapEditorModel extends Observable {
    /* Models */
    private GameMap gameMap;
    private MapSelectionModel mapSelectionModel;
    private MapTableModel mapTableModel;
    
    public MapEditorModel() {
        this.mapTableModel = new MapTableModel();
        this.mapSelectionModel = new MapSelectionModel();
    }
    
    public void updateGameMap(String mapName) throws Exception {
        this.gameMap = GameMapHandler.loadGameMap(mapName);
        mapTableModel = new MapTableModel(gameMap);
        setChanged();
        notifyObservers();
    }
    
    public MapSelectionModel getMapSelectionModel() {
        return mapSelectionModel;
    }
    
    public GameMap getGameMap() {
        return gameMap;
    }
    
    public MapTableModel getMapTableModel() {
        return mapTableModel;
    }
}

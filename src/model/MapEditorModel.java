package model;

import java.util.Observable;

public class MapEditorModel extends Observable {
    /* Models */
    private GameMap gameMap;
    private MapSelectionModel mapSelectionModel;
    
    public MapEditorModel() {
        mapSelectionModel = new MapSelectionModel();
    }
    
    public MapEditorModel(GameMap gameMap) {
        this.gameMap = gameMap;
    }
    
    public void updateGameMap(String mapName) throws Exception {
        this.gameMap = GameMapHandler.loadGameMap(mapName);
        setChanged();
        notifyObservers();
    }
    
    public MapSelectionModel getMapSelectionModel() {
        return mapSelectionModel;
    }
}

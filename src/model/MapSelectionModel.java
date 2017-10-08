package model;

import util.Config;

import java.util.Observable;

public class MapSelectionModel extends Observable {
    private DropDownModel dropDownModel;
    
    public MapSelectionModel() {}
    
    public DropDownModel getDropDownModel() {
        return dropDownModel;
    }
    
    public void updateDropDownModel() {
        this.dropDownModel = new DropDownModel(GameMapHandler.getMapsInFolder(Config.MAPS_FOLDER));
        setChanged();
        notifyObservers();
    }
}

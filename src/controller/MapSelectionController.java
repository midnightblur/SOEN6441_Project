package controller;

import model.MapSelectionModel;
import view.Panels.MapSelectionPanel;

public class MapSelectionController {
    private MapSelectionPanel mapSelectionPanel;
    private MapSelectionModel mapSelectionModel;
    
    /* Constructors */
    public MapSelectionController(MapSelectionPanel mapSelectionPanel, MapSelectionModel mapSelectionModel) {
        this.mapSelectionPanel = mapSelectionPanel;
        this.mapSelectionModel = mapSelectionModel;
        this.mapSelectionModel.addObserver(this.mapSelectionPanel);
        this.mapSelectionModel.updateDropDownModel();
    }
}

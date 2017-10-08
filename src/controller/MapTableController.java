package controller;

import model.MapTableModel;
import view.Panels.MapTablePanel;

public class MapTableController {
    private MapTablePanel mapTablePanel;
    private MapTableModel mapTableModel;
    
    public MapTableController(MapTablePanel mapTablePanel, MapTableModel mapTableModel) {
        this.mapTablePanel = mapTablePanel;
        this.mapTableModel = mapTableModel;
        this.mapTableModel.addObserver(this.mapTablePanel);
    }
}

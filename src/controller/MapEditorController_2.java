package controller;

import model.MapEditorModel;
import view.Panels.MapTablePanel;
import view.Screens.MapEditorFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapEditorController_2 implements ActionListener {
    /* Views */
    private MapEditorFrame mapEditorFrame;
    
    /* Models */
    private MapEditorModel mapEditorModel;
    
    /* Controllers */
    private MapSelectionController mapSelectionController;
    private MapTableController mapTableController;
    
    /* Constructors */
    public MapEditorController_2(MapEditorFrame mapEditorFrame, MapEditorModel mapEditorModel) {
        this.mapEditorFrame = mapEditorFrame;
        this.mapEditorModel = mapEditorModel;
        this.mapSelectionController = new MapSelectionController(mapEditorFrame.getGeneralLayoutPanel().getMapEditControlPanel().getMapSelectionPanel(), mapEditorModel.getMapSelectionModel());
        this.mapTableController = new MapTableController((MapTablePanel) mapEditorFrame.getGeneralLayoutPanel().getTablePanel(), mapEditorModel.getMapTableModel());
    
        this.mapEditorFrame.getGeneralLayoutPanel().getTablePanel().updateTable(mapEditorModel.getMapTableModel().getModel());
        
        
        this.mapEditorModel.addObserver(mapEditorFrame.getGeneralLayoutPanel().getTablePanel());

        this.mapEditorFrame.addLoadMapButtonListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String newMapName = mapSelectionController.getSelectedMap();
            mapEditorModel.updateGameMap(newMapName);
        } catch (Exception e1) {
            e1.printStackTrace(System.err);
        }
    }
}
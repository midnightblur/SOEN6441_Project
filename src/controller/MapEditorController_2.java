package controller;

import model.MapEditorModel;
import view.Screens.MapEditorFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapEditorController_2 {
    /* Views */
    private MapEditorFrame mapEditorFrame;
    
    /* Models */
    private MapEditorModel mapEditorModel;
    
    /* Controllers */
    private MapSelectionController mapSelectionController;
    private MapTableController mapTableController;
    
    /* Constructors */
    public MapEditorController_2() {
        this.mapEditorFrame = new MapEditorFrame();
        this.mapEditorModel = new MapEditorModel();
        this.mapSelectionController = new MapSelectionController(mapEditorFrame.getGeneralLayoutPanel().getMapEditControlPanel().getMapSelectionPanel(), mapEditorModel.getMapSelectionModel());
        
        this.mapEditorModel.addObserver(mapEditorFrame.getGeneralLayoutPanel().getTablePanel());
        
        this.mapEditorFrame.addLoadMapButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String newMapName = mapSelectionController.getSelectedMap();
                    mapEditorModel.updateGameMap(newMapName);
                } catch (Exception e1) {
                    e1.printStackTrace(System.err);
                }
            }
        });
    }
}
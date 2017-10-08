package controller;

import model.*;
import view.Panels.MapEditorControlPanel;
import view.Screens.MapEditorFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapEditorController_2 {
    /* Views */
    private MapEditorFrame mapEditorFrame;
    private GameMap gameMap;
    
    /* Models */
    private MapEditorModel mapEditorModel;
    
    /* Controllers */
    private MapSelectionController mapSelectionController;
    
    /* Constructors */
    public MapEditorController_2() {
        this.mapEditorFrame = new MapEditorFrame();
        this.mapEditorModel = new MapEditorModel();
        this.mapSelectionController = new MapSelectionController(mapEditorFrame.getGeneralLayoutPanel().getMapEditorControlPanel().getMapSelectionPanel(), mapEditorModel.getMapSelectionModel());
        ((MapEditorControlPanel) this.mapEditorFrame.getGeneralLayoutPanel().getControlPanel()).addLoadMapButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String newMapName = ((MapEditorControlPanel) mapEditorFrame.getGeneralLayoutPanel().getControlPanel()).getMapSelectionPanel().getSelectedItem();
                    mapEditorModel.updateGameMap(newMapName);
                } catch (Exception e1) {
                    e1.printStackTrace(System.err);
                }
            }
        });
    }
}
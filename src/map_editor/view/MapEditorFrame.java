/* 
 * Risk Game Team 2
 * MapEditorFrame.java
 * Version 1.0
 * Oct 18, 2017
 */
package map_editor.view;

import game_play.view.ui_components.GameMapTable;
import map_editor.model.MapEditorModel;
import shared_resources.helper.UIHelper;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * The Class MapEditorFrame is the main screen frame for map editor component.
 *
 * @author Team 2
 * @version 1.0
 */
public class MapEditorFrame extends JFrame implements Observer {
    // region Attributes declaration
    private static final String TITLE = "Map Editor";
    private static final int WIDTH = 1366;
    private static final int HEIGHT = 700;
    private JSplitPane contentPane;
    private GameMapTable gameMapTable;
    private EditMapPanel editMapPanel;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiates a new map editor frame.
     */
    public MapEditorFrame() {
        /* Setup main container */
        setupContentPaneLayout();
        setContentPane(contentPane);
        
        /* Setup table area */
        gameMapTable = new GameMapTable();
        contentPane.setLeftComponent(new JScrollPane(gameMapTable));
        
        /* Setup control panel area */
        editMapPanel = new EditMapPanel();
        contentPane.setRightComponent(editMapPanel);
        
        /* Setup & Display frame */
        UIHelper.displayJFrame(this, TITLE, WIDTH, HEIGHT, false);
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Setup content pane layout.
     */
    /* Private methods */
    private void setupContentPaneLayout() {
        contentPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT) {
            private final int location = 850;
            
            {
                setDividerLocation(location);
            }
            
            @Override
            public int getLastDividerLocation() {
                return location;
            }
            
            @Override
            public int getDividerLocation() {
                return location;
            }
        };
    }
    
    /**
     * @see javax.swing.JFrame#getContentPane()
     */
    @Override
    public JSplitPane getContentPane() {
        return contentPane;
    }
    
    /**
     * Gets the game map table.
     *
     * @return the game map table
     */
    public GameMapTable getGameMapTable() {
        return gameMapTable;
    }
    // endregion
    
    // region Private methods
    
    /**
     * Gets the edits the map panel.
     *
     * @return the edits the map panel
     */
    public EditMapPanel getEditMapPanel() {
        return editMapPanel;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MapEditorModel) {
            MapEditorModel mapEditorModel = (MapEditorModel) o;
            setTitle(String.valueOf(mapEditorModel.getGameMap().getMapName()));
        }
    }
    // endregion
}

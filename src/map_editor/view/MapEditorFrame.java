/* 
 * Risk Game Team 2
 * MapEditorFrame.java
 * Version 1.0
 * Oct 18, 2017
 */
package map_editor.view;

import game_play.view.ui_components.GameMapTable;
import shared_resources.helper.UIHelper;

import javax.swing.*;

/**
 * The Class MapEditorFrame is the main screen frame for map editor component.
 */
public class MapEditorFrame extends JFrame {
    // region Attributes declaration
    /** The Constant TITLE. */
    private static final String TITLE = "Map Editor";
    
    /** The Constant WIDTH. */
    private static final int WIDTH = 1366;
    
    /** The Constant HEIGHT. */
    private static final int HEIGHT = 700;
    
    /** The content pane. */
    private JSplitPane contentPane;
    
    /** The game map table. */
    private GameMapTable gameMapTable;
    
    /** The edit map panel. */
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
    
    /**
     * Gets the edits the map panel.
     *
     * @return the edits the map panel
     */
    public EditMapPanel getEditMapPanel() {
        return editMapPanel;
    }
    // endregion
    
    // region Private methods
    
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
            public int getDividerLocation() {
                return location;
            }
            
            @Override
            public int getLastDividerLocation() {
                return location;
            }
        };
    }
    // endregion
}

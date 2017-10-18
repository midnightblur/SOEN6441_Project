/*
  @file  MapEditorFrame.java
 * @brief 
 * 
 * 
 * 
 * @author Team 2
 * @version 1.0
 * @since  Oct 18, 2017
 * @bug No known bugs.
 */
package view.screens;

import view.helpers.UIHelper;
import view.ui_components.EditMapPanel;
import view.ui_components.GameMapTable;

import javax.swing.*;

import static view.helpers.UIHelper.setDivider;

/**
 * The Class MapEditorFrame.
 */
public class MapEditorFrame extends JFrame {
    
    /** The Constant TITLE. */
    private static final String TITLE = "Map Editor";
    
    /** The Constant WIDTH. */
    private static final int WIDTH = 1600;
    
    /** The Constant HEIGHT. */
    private static final int HEIGHT = 800;
    
    /** The content pane. */
    private JSplitPane contentPane;
    
    /** The game map table. */
    private GameMapTable gameMapTable;
    
    /** The edit map panel. */
    private EditMapPanel editMapPanel;
    
    /**
     * Instantiates a new map editor frame.
     */
    /* Constructors */
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
    
    /* (non-Javadoc)
     * @see javax.swing.JFrame#getContentPane()
     */
    /* Getters & Setters */
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
    
    /* Public methods */
    
    /**
     * Setup content pane layout.
     */
    /* Private methods */
    private void setupContentPaneLayout() {
        contentPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT) {
            private final int location = 1100;
            
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
        setDivider(contentPane);
    }
}

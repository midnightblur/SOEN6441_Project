/* 
 * Risk Game Team 2
 * GamePlayFrame.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.view.screens;

import game_play.model.GamePlayModel;
import game_play.view.ui_components.*;
import shared_resources.helper.UIHelper;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * The Class GamePlayFrame represents the main frame for UI while playing the game.
 *
 * It is composed of a left side that renders the map as a table, and
 * an interchangeable right side for various control panels.
 */
public class GamePlayFrame extends JFrame implements Observer {
    
    // region Attributes declaration
    /** The Constant TITLE. */
    private static final String TITLE = "Game Play";
    
    /** The Constant WIDTH. */
    private static final int WIDTH = 1366;
    
    /** The Constant HEIGHT. */
    private static final int HEIGHT = 700;
    
    /** The content pane. */
    private JSplitPane contentPane;
    
    /** The game map table. */
    private GameMapTable gameMapTable;
    
    /** The control area. */
    private JPanel controlArea;
    
    /** The game setup panel. */
    private GameSetupPanel gameSetupPanel;
    
    /** The startup panel. */
    private StartupPanel startupPanel;
    
    /** The reinforcement panel. */
    private ReinforcementPanel reinforcementPanel;
    
    /** The fortification panel. */
    private FortificationPanel fortificationPanel;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiates a new game play frame.
     */
    public GamePlayFrame() {
        /* Setup main container */
        setupContentPaneLayout();
        setContentPane(contentPane);
        
        /* Setup table area */
        gameMapTable = new GameMapTable();
        contentPane.setLeftComponent(new JScrollPane(gameMapTable));
        
        /* Setup control area */
        setupControlArea();
        
        /* Setup & Display frame */
        UIHelper.displayJFrame(this, TITLE, WIDTH, HEIGHT, false);
    }
    // endregion
    
    /**
     * Setup content pane layout.
     */
    private void setupContentPaneLayout() {
        contentPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT) {
            private final int location = 950;
            
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
     * Setup control area.
     */
    private void setupControlArea() {
        /* Use CardLayout to let different panels share the same space */
        controlArea = new JPanel(new CardLayout());
        
        gameSetupPanel = new GameSetupPanel();
        controlArea.add(gameSetupPanel, GameSetupPanel.class.getName());
        startupPanel = new StartupPanel();
        controlArea.add(startupPanel, StartupPanel.class.getName());
        reinforcementPanel = new ReinforcementPanel();
        controlArea.add(reinforcementPanel, ReinforcementPanel.class.getName());
        fortificationPanel = new FortificationPanel();
        controlArea.add(fortificationPanel, FortificationPanel.class.getName());
        
        contentPane.setRightComponent(controlArea);
    }
    
    // region Getters & Setters
    /* (non-Javadoc)
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
     * Gets the game setup panel.
     *
     * @return the game setup panel
     */
    public GameSetupPanel getGameSetupPanel() {
        return gameSetupPanel;
    }
    
    /**
     * Gets the startup panel.
     *
     * @return the startup panel
     */
    public StartupPanel getStartupPanel() {
        return startupPanel;
    }
    
    // endregion
    
    // region Public methods
    
    /**
     * Gets the reinforcement panel.
     *
     * @return the reinforcement panel
     */
    public ReinforcementPanel getReinforcementPanel() {
        return reinforcementPanel;
    }
    // endregion
    
    // region Private methods
    
    /**
     * Gets the fortification panel.
     *
     * @return the fortification panel
     */
    public FortificationPanel getFortificationPanel() {
        return fortificationPanel;
    }
    
    /* (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GamePlayModel) {
            GamePlayModel gamePlayModel = (GamePlayModel) o;
            gameMapTable.setModel(gamePlayModel.getMapTableModel().getModel());
            
            CardLayout cardLayout = (CardLayout) controlArea.getLayout();
            switch (gamePlayModel.getGameState()) {
                case SETUP:
                    cardLayout.show(controlArea, GameSetupPanel.class.getName());
                    break;
                case STARTUP:
                    cardLayout.show(controlArea, StartupPanel.class.getName());
                    break;
                case REINFORCEMENT:
                    cardLayout.show(controlArea, ReinforcementPanel.class.getName());
                    break;
                case ATTACKING:
                    // TODO: add the attack panel cardLayout.show(controlArea, AttackPanel.class.getName());
                    break;
                case FORTIFICATION:
                    cardLayout.show(controlArea, FortificationPanel.class.getName());
                    break;
                default:
                    break;
            }
        }
    }
    // endregion
}

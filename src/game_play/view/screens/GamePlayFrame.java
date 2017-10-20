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
 * GamePlayFrame is responsible for displaying all UI components to play the game.
 *
 * It is composed of a left side that renders the map as a table, and
 * an interchangeable right side for various control panels.
 *
 * @author Team 2
 * @version 1.0
 */
public class GamePlayFrame extends JFrame implements Observer {
    // region Attributes declaration
    private static final String TITLE = "Game Play";
    private static final int WIDTH = 1366;
    private static final int HEIGHT = 700;
    private JSplitPane contentPane;
    private GameMapTable gameMapTable;
    private JPanel controlArea;
    private GameSetupPanel gameSetupPanel;
    private StartupPanel startupPanel;
    private ReinforcementPanel reinforcementPanel;
    private FortificationPanel fortificationPanel;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiates and show the GamePlayFrame.
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
        UIHelper.displayJFrame(this, TITLE, WIDTH, HEIGHT, true);
    }
    // endregion
    
    // region Getters & Setters
    /**
     * Gets the fortification panel.
     *
     * @return the fortification panel
     */
    public FortificationPanel getFortificationPanel() {
        return fortificationPanel;
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
     * Setup the layout for the main screen.
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
     * Setup ui components in control area.
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
    // endregion
    
    // region MVC & Observer pattern methods
    /**
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
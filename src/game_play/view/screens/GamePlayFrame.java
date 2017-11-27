/* 
 * Risk Game Team 2
 * GamePlayFrame.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.view.screens;

import game_play.controller.MainMenuController;
import game_play.model.GamePlayModel;
import game_play.view.ui_components.*;
import shared_resources.game_entities.Player;
import shared_resources.helper.UIHelper;
import shared_resources.utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

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
    private static final int HEIGHT = 800;
    private JMenuItem save;
    private JMenuItem load;
    private JMenuItem strategy;
    private JSplitPane topArea;
    private WorldDominationPanel worldDominationPanel;
    private PhaseViewPanel phaseViewPanel;
    private GameMapTable gameMapTable;
    private JPanel controlArea;
    private GameSetupPanel gameSetupPanel;
    private StartupPanel startupPanel;
    private ReinforcementPanel reinforcementPanel;
    private FortificationPanel fortificationPanel;
    private AttackingPanel attackingPanel;
    private Vector<Player> playersList;
    private MainMenuController callerController;
    private JButton openDefendingDialogButton; // invisible button to activate DefendingDialog for human player
    private JButton letBotPlayButton; // invisible button to activate a bot player's turn
    private JButton popupVictoryDialogButton; // invisible button to activate victory dialog
    // endregion
    
    // region Constructors
    
    /**
     * Instantiates and show the GamePlayFrame.
     *
     * @param callerController the main menu controller
     */
    public GamePlayFrame(MainMenuController callerController) {
        /* Setup the menu */
        JMenuBar menu = new JMenuBar();
        
        JMenu game = new JMenu("Game");
        save = new JMenuItem("Save game...", KeyEvent.VK_T);
        load = new JMenuItem("Load game...", KeyEvent.VK_T);
        game.add(save);
        game.add(load);
        menu.add(game);
        
        JMenu player = new JMenu("Player");
        strategy = new JMenuItem("Set Strategy...", KeyEvent.VK_T);
        strategy.setEnabled(false);
        player.add(strategy);
        menu.add(player);
        
        setJMenuBar(menu);
        
        /* Setup main container */
        this.callerController = callerController;
        playersList = new Vector<>();
        setupContentPaneLayout();
        
        /* Setup world domination view & table area */
        worldDominationPanel = new WorldDominationPanel();
        gameMapTable = new GameMapTable();
        JPanel leftComponent = new JPanel();
        leftComponent.setLayout(new BoxLayout(leftComponent, BoxLayout.Y_AXIS));
        leftComponent.add(worldDominationPanel);
        leftComponent.add(new JScrollPane(gameMapTable));
        topArea.setLeftComponent(leftComponent);
        
        /* Setup Phase View */
        phaseViewPanel = new PhaseViewPanel();
        
        /* Setup main content pane */
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(topArea, BorderLayout.CENTER);
        contentPane.add(phaseViewPanel, BorderLayout.PAGE_END);
        setContentPane(contentPane);
        
        /* Setup control area */
        setupControlArea();
        
        /* Setup invisible components */
        openDefendingDialogButton = new JButton();
        letBotPlayButton = new JButton();
        popupVictoryDialogButton = new JButton();
        
        /* Setup & Display frame */
        UIHelper.displayJFrame(this, TITLE, WIDTH, HEIGHT, true);
    }
    // endregion
    
    // region Private methods
    /**
     * Setup the layout for the main screen.
     */
    private void setupContentPaneLayout() {
        topArea = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT) {
            private final int location = 1000;
            
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
        attackingPanel = new AttackingPanel();
        controlArea.add(attackingPanel, AttackingPanel.class.getName());
        
        topArea.setRightComponent(controlArea);
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
        return topArea;
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
     * Adds save menu item listener
     *
     * @param listenerForLoadMenu the listener for the load menu item
     */
    public void addLoadMenuListener(ActionListener listenerForLoadMenu) {
        load.addActionListener(listenerForLoadMenu);
    }
    
    /**
     * Adds strategy menu item listener
     *
     * @param listenerForStrategyMenu the listener for the strategy menu item
     */
    public void addStrategyMenuListener(ActionListener listenerForStrategyMenu) {
        strategy.addActionListener(listenerForStrategyMenu);
    }
    
    /**
     * Adds save menu item listener
     *
     * @param listenerForSaveMenu the listener for the save menu item
     */
    public void addSaveMenuListener(ActionListener listenerForSaveMenu) {
        save.addActionListener(listenerForSaveMenu);
    }
    
    /**
     * Adds open defending dialog button listener
     *
     * @param listenerForOpenDefendingDialogButton the listener for open defending dialog button
     */
    public void addOpenDefendingDialogButtonListener(ActionListener listenerForOpenDefendingDialogButton) {
        openDefendingDialogButton.addActionListener(listenerForOpenDefendingDialogButton);
    }
    
    /**
     * Adds let bot player button listener
     *
     * @param listenerForLetBotPlayButton the listener for let bot play button
     */
    public void addLetBotPlayButtonListener(ActionListener listenerForLetBotPlayButton) {
        letBotPlayButton.addActionListener(listenerForLetBotPlayButton);
    }
    
    /**
     * Adds popup victory dialog button listener
     *
     * @param listenerForPopupVictoryDialogButton the listener for popup victory dialog button
     */
    public void addPopupVictoryDialogButtonListener(ActionListener listenerForPopupVictoryDialogButton) {
        popupVictoryDialogButton.addActionListener(listenerForPopupVictoryDialogButton);
    }
    // endregion
    
    // region Public methods
    
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
    
    /**
     * Gets the reinforcement panel.
     *
     * @return the reinforcement panel
     */
    public ReinforcementPanel getReinforcementPanel() {
        return reinforcementPanel;
    }
    
    /**
     * Gets the world domination panel
     *
     * @return the world domination panel
     */
    public WorldDominationPanel getWorldDominationPanel() {
        return worldDominationPanel;
    }
    
    /**
     * Gets the phase view panel
     *
     * @return the phase view panel
     */
    public PhaseViewPanel getPhaseViewPanel() {
        return phaseViewPanel;
    }
    
    /**
     * Gets the strategy game menu item
     *
     * @return the strategy game menu item
     */
    public JMenuItem getStrategy() {
        return strategy;
    }
    
    /**
     * Gets the save game menu item
     *
     * @return the save game menu item
     */
    public JMenuItem getSave() {
        return save;
    }
    
    /**
     * Gets the load game menu item
     *
     * @return the load game menu item
     */
    public JMenuItem getLoad() {
        return load;
    }
    
    /**
     * Gets the attacking panel
     *
     * @return the attacking panel
     */
    public AttackingPanel getAttackingPanel() {
        return attackingPanel;
    }
    // endregion
    
    // region MVC & Observer pattern methods
    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GamePlayModel) {
            GamePlayModel gamePlayModel = (GamePlayModel) o;
            if (gamePlayModel.getGameState() == Config.GAME_STATES.VICTORY) {
                popupVictoryDialogButton.doClick();
            } else if (gamePlayModel.getCurrentPlayer() != null) {
                if (gamePlayModel.isNeedDefenderReaction() && !gamePlayModel.getCurrentPlayer().isHuman()) {
                    gamePlayModel.setNeedDefenderReaction(false);
                    openDefendingDialogButton.doClick();
                } else if (!gamePlayModel.getCurrentPlayer().isHuman() &&
                        gamePlayModel.getCurrentPlayer().getGameState() == Config.GAME_STATES.REINFORCEMENT) {
                    letBotPlayButton.doClick();
                } else if (gamePlayModel.getCurrentPlayer().isHuman()) {
                    gameMapTable.setModel(gamePlayModel.getMapTableModel().getModel());
            
                /* Enable strategy menu item if players are set */
                    if (gamePlayModel.getPlayers().size() > 0) {
                        strategy.setEnabled(true);
                    }
        
                    CardLayout cardLayout = (CardLayout) controlArea.getLayout();
                    switch (gamePlayModel.getGameState()) {
                        case SETUP:
                            cardLayout.show(controlArea, GameSetupPanel.class.getName());
                            break;
                        case STARTUP:
                            cardLayout.show(controlArea, StartupPanel.class.getName());
                            break;
                        case PLAY:
                            switch (gamePlayModel.getCurrentPlayer().getGameState()) {
                                case REINFORCEMENT:
                                case TRADE_CARDS:
                                    cardLayout.show(controlArea, ReinforcementPanel.class.getName());
                                    break;
                                case ATTACK_PREPARE:
                                case ATTACK_BATTLE:
                                    cardLayout.show(controlArea, AttackingPanel.class.getName());
                                    break;
                                case FORTIFICATION:
                                    cardLayout.show(controlArea, FortificationPanel.class.getName());
                                    break;
                                default:
                                    break;
                            }
                            break;
                        default:
                            break;
                    }
        
                    Vector<Player> oldPlayersList = new Vector<>();
                    for (Player player : playersList) {
                        if (player.getPlayerStatus() == GamePlayModel.PLAYER_STATUS.IN_GAME) {
                            oldPlayersList.add(player);
                        }
                    }
        
                    playersList.clear();
                    for (Player player : gamePlayModel.getPlayers()) {
                        if (player.getPlayerStatus() == GamePlayModel.PLAYER_STATUS.IN_GAME) {
                            playersList.add(player);
                        }
                    }
        
                    if (oldPlayersList.size() > playersList.size()) {
                        if (playersList.size() != 1) {
                            for (Player player : oldPlayersList) {
                                if (!playersList.contains(player)) {
                                    UIHelper.displayMessage(this, String.format("%s has been eliminated", player.getPlayerName()));
                                }
                            }
                        } else {
                            Player player = playersList.firstElement();
                            UIHelper.displayMessage(this, String.format("%s is the winner", player.getPlayerName()));
                            backToMainMenu();
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Close GamePlayFrame, open MainMenuFrame
     */
    private void backToMainMenu() {
        this.dispose();
        UIHelper.invokeFrame(callerController.getMainMenuFrame());
    }
    // endregion
}

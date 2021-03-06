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
    private JButton popupVictoryDialogButton; // invisible button to activate victory dialog
    private JButton turnCounterReachedMaxButton; // invisible button to activate a bot player's turn
    private JButton attackCounterReachedMaxButton; // invisible button to activate a new attack turn of current player
    private JButton startBotTurnButton; // invisible button to activate a new bot turn
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
        popupVictoryDialogButton = new JButton();
        turnCounterReachedMaxButton = new JButton();
        attackCounterReachedMaxButton = new JButton();
        startBotTurnButton = new JButton();
        
        /* Setup & Display frame */
        UIHelper.displayJFrame(this, TITLE, WIDTH, HEIGHT, true);
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                backToMainMenu();
            }
        });
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
     * Close GamePlayFrame, open MainMenuFrame
     */
    private void backToMainMenu() {
        this.dispose();
        UIHelper.invokeFrame(callerController.getMainMenuFrame());
    }
    
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
     * Adds popup victory dialog button listener
     *
     * @param listenerForPopupVictoryDialogButton the listener for popup victory dialog button
     */
    public void addPopupVictoryDialogButtonListener(ActionListener listenerForPopupVictoryDialogButton) {
        popupVictoryDialogButton.addActionListener(listenerForPopupVictoryDialogButton);
    }
    
    /**
     * Adds turn counter at maximum button listener
     *
     * @param listenerForTurnCounterReachedMaxButton the listener TurnCounterReachedMax button
     */
    public void addTurnCounterReachedMaxButtonListener(ActionListener listenerForTurnCounterReachedMaxButton) {
        turnCounterReachedMaxButton.addActionListener(listenerForTurnCounterReachedMaxButton);
    }
    
    /**
     * Adds attack counter at maximum button listener
     *
     * @param listenerForAttackCounterReachedMaxButton the listener AttackCounterReachedMax button
     */
    public void addAttackCounterReachedMaxButtonListener(ActionListener listenerForAttackCounterReachedMaxButton) {
        attackCounterReachedMaxButton.addActionListener(listenerForAttackCounterReachedMaxButton);
    }
    // endregion
    
    // region Public methods
    
    /**
     * Adds start bot turn button listener
     *
     * @param listenerForStartBotTurnButton the listener for start bot turn button
     */
    public void addStartBotTurnButtonListener(ActionListener listenerForStartBotTurnButton) {
        startBotTurnButton.addActionListener(listenerForStartBotTurnButton);
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
    // endregion
    
    // region MVC & Observer pattern methods
    
    /**
     * Gets the attacking panel
     *
     * @return the attacking panel
     */
    public AttackingPanel getAttackingPanel() {
        return attackingPanel;
    }
    
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
            if (gamePlayModel.getGameState() == Config.GAME_STATES.VICTORY) { // If the game has a winner
                popupVictoryDialogButton.doClick();
            } else if (gamePlayModel.getCurrentPlayer() != null) { // If the game is still running
                if (gamePlayModel.getAttackCounter() >= gamePlayModel.getMaxAttackTurn() &&
                        gamePlayModel.getCurrentPlayer().getGameState() == Config.GAME_STATES.ATTACK_PREPARE) { // If a player reach allotted attacking turns
                    attackCounterReachedMaxButton.doClick();
                } else if (gamePlayModel.getTurnCounter() >= gamePlayModel.getMaxTurns() &&
                        gamePlayModel.getCurrentPlayer().getGameState() == Config.GAME_STATES.REINFORCEMENT) { // If a player reached allotted turns
                    turnCounterReachedMaxButton.doClick();
                } else if (gamePlayModel.isNeedDefenderReaction() && !gamePlayModel.getCurrentPlayer().isHuman()) { // A human attacked by a bot
                    gamePlayModel.setNeedDefenderReaction(false);
                    openDefendingDialogButton.doClick();
                } else if (!gamePlayModel.getCurrentPlayer().isHuman() &&
                        gamePlayModel.getCurrentPlayer().getGameState() == Config.GAME_STATES.REINFORCEMENT) { // If a bot takes new turn
                    startBotTurnButton.doClick();
                } else if (gamePlayModel.getCurrentPlayer().isHuman()) { // Current player is human
                    gameMapTable.setModel(gamePlayModel.getMapTableModel().getModel());
            
                    /* Enable strategy menu item if players are set */
                    if (gamePlayModel.getPlayers().size() > 0 &&
                            (gamePlayModel.getGameState() == Config.GAME_STATES.STARTUP ||
                                    gamePlayModel.getCurrentPlayer().getGameState() == Config.GAME_STATES.REINFORCEMENT)) {
                        strategy.setEnabled(true);
                    } else {
                        strategy.setEnabled(false);
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
    // endregion
}

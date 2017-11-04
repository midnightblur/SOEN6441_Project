/* 
 * Risk Game Team 2
 * ReinforcementPanel.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.view.ui_components;

import game_play.model.GamePlayModel;
import shared_resources.utilities.Config.GAME_STATES;
import shared_resources.utilities.IntegerEditor;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Observable;
import java.util.Observer;

import static shared_resources.helper.UIHelper.addVerticalSpacing;

/**
 * ReinforcementPanel is responsible for representing the ui components for Reinforcement phase of the game.
 *
 * @author Team 2
 * @version 1.0
 */
public class ReinforcementPanel extends JPanel implements Observer {
    // region Attributes declaration
    private static final String CONTROL_WRAPPER_PANEL_NAME = "ControlWrapper";
    private static final String TRADE_CARDS_PANEL_NAME = "TradeCards";
    private static final String PLACE_ARMIES_BUTTON = "Place armies";
    private static final String TOTAL_ARMIES_TO_PLACE_LABEL = "Armies to be placed: ";
    private static final String GO_TO_FORTIFICATION_BUTTON = "Go to Fortification";
    private static final String TRADE_CARDS_BUTTON = "Trade Cards";
    private static final String ATTACK_BUTTON = "Attack!";
    private static final String ARMIES_TO_PLACE_LABEL = "Use table to place armies:";
    private JPanel cardsPanel;
    private TradeCardsPanel tradeCardsPanel;
    private JButton tradeCardsButton;
    private JButton goToFortificationButton;
    private JButton placeArmiesButton;
    private JTable playerTerritoryTable;
    private JLabel playerName;
    private JLabel totalArmiesToPlace;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiates a new reinforcement panel.
     */
    public ReinforcementPanel() {
        cardsPanel = new JPanel(new CardLayout());
        JLabel gameState = new JLabel();
        gameState.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        gameState.setForeground(Color.BLUE);
        gameState.setText(GAME_STATES.PLAYER_REINFORCEMENT.name());
        playerName = new JLabel();
        playerName.setFont(new Font("Sans Serif", Font.BOLD, 20));
        tradeCardsButton = new JButton(TRADE_CARDS_BUTTON);
        tradeCardsButton.setForeground(Color.BLUE);
        totalArmiesToPlace = new JLabel();
        totalArmiesToPlace.setFont(new Font("Sans Serif", Font.BOLD, 16));
        JLabel howManyArmiesToPlace = new JLabel(ARMIES_TO_PLACE_LABEL);
        
        constructTerritoryTable();
        playerTerritoryTable.setDefaultEditor(Integer.class, new IntegerEditor());
        playerTerritoryTable.getTableHeader().setReorderingAllowed(false);
        
        placeArmiesButton = new JButton(PLACE_ARMIES_BUTTON);
        placeArmiesButton.setForeground(Color.BLUE);
        goToFortificationButton = new JButton(GO_TO_FORTIFICATION_BUTTON);
        JButton attackButton = new JButton(ATTACK_BUTTON);
        attackButton.setEnabled(false);
        attackButton.setForeground(Color.RED);

        /* Set layout */
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        /* The control wrapper. */
        JPanel controlWrapper = new JPanel();
        controlWrapper.setLayout(new BoxLayout(controlWrapper, BoxLayout.PAGE_AXIS));
        JPanel topGrid = new JPanel(new GridLayout(7, 1));
        JPanel bottomGrid = new JPanel(new GridLayout(7, 1));
        
        /* Add the elements to the panel */
        topGrid.add(gameState);
        topGrid.add(playerName);
        addVerticalSpacing(topGrid);
        topGrid.add(tradeCardsButton);
        addVerticalSpacing(topGrid);
        topGrid.add(totalArmiesToPlace);
        topGrid.add(howManyArmiesToPlace);
        controlWrapper.add(topGrid);
        
        controlWrapper.add(new JScrollPane(playerTerritoryTable));
        
        addVerticalSpacing(bottomGrid);
        bottomGrid.add(placeArmiesButton);
        addVerticalSpacing(bottomGrid);
        bottomGrid.add(goToFortificationButton);
        addVerticalSpacing(bottomGrid);
        bottomGrid.add(attackButton);
        addVerticalSpacing(bottomGrid);
        controlWrapper.add(bottomGrid);
    
        /* Card Layout elements */
        cardsPanel.add(controlWrapper, CONTROL_WRAPPER_PANEL_NAME);
        tradeCardsPanel = new TradeCardsPanel();
        cardsPanel.add(tradeCardsPanel, TRADE_CARDS_PANEL_NAME);
        add(cardsPanel);
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Gets the player territory table.
     *
     * @return the player territory table
     */
    public JTable getPlayerTerritoryTable() {
        return playerTerritoryTable;
    }
    
    /**
     * Gets the cards panel.
     *
     * @return the cards panel
     */
    public JPanel getCardsPanel() {
        return cardsPanel;
    }
    
    /**
     * Gets the trade cards panel.
     *
     * @return the trade cards panel
     */
    public TradeCardsPanel getTradeCardsPanel() {
        return tradeCardsPanel;
    }
    
    /**
     * Gets the control wrapper panel name.
     *
     * @return the control wrapper panel name
     */
    public static String getControlWrapperPanelName() {
        return CONTROL_WRAPPER_PANEL_NAME;
    }
    
    /**
     * Gets the trade cards panel name.
     *
     * @return the trade cards panel name
     */
    public static String getTradeCardsPanelName() {
        return TRADE_CARDS_PANEL_NAME;
    }
    
    // endregion
    
    // region MVC & Observer pattern methods
    
    /**
     * Adds the place armies button listener.
     *
     * @param listenerForPlaceArmiesButton the listener for place armies button
     */
    public void addPlaceArmiesButtonListener(ActionListener listenerForPlaceArmiesButton) {
        placeArmiesButton.addActionListener(listenerForPlaceArmiesButton);
    }
    
    /**
     * Adding Listener for fortification phase.
     *
     * @param listenerForGoToFortificationButton the listener for go to fortification button
     */
    public void addGoToFortificationButtonListener(ActionListener listenerForGoToFortificationButton) {
        goToFortificationButton.addActionListener(listenerForGoToFortificationButton);
    }
    
    /**
     * Adds the trade cards button listener.
     *
     * @param listenerForTradeCardsButton the listener for trade cards button
     */
    public void addTradeCardsButtonListener(ActionListener listenerForTradeCardsButton) {
        tradeCardsButton.addActionListener(listenerForTradeCardsButton);
    }
    
    /**
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GamePlayModel) {
            GamePlayModel gamePlayModel = (GamePlayModel) o;
            if (gamePlayModel.getGameState() == GAME_STATES.PLAY &&
                    gamePlayModel.getCurrentPlayer().getGameState() == GAME_STATES.PLAYER_REINFORCEMENT) {
                playerName.setForeground(gamePlayModel.getCurrentPlayer().getColor());
                playerName.setText(gamePlayModel.getCurrentPlayer().getPlayerName());
                totalArmiesToPlace.setText(TOTAL_ARMIES_TO_PLACE_LABEL + Integer.toString(gamePlayModel.getCurrentPlayer().getUnallocatedArmies()));
                playerTerritoryTable.setModel(gamePlayModel.getPlayerTerritoriesModel().getModel());
            }
        }
    }
    // endregion
    
    // region Private methods
    
    /**
     * The territory table is constructor in this method.
     */
    private void constructTerritoryTable() {
        playerTerritoryTable = new JTable() {
            @Override   // set the data type for each column
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Integer.class;
                }
                return getValueAt(0, column).getClass();
            }
            
            @Override   // only allow editing in second column
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }
            
            @Override   // set font
            public void setFont(Font font) {
                super.setFont(new Font("Sans Serif", Font.PLAIN, 16));
            }
            
            @Override   // set the row height
            public void setRowHeight(int rowHeight) {
                super.setRowHeight(25);
            }
            
            @Override // select all
            public boolean editCellAt(int row, int column, EventObject e) {
                boolean result = super.editCellAt(row, column, e);
                final Component editor = getEditorComponent();
                if (editor == null || !(editor instanceof JTextComponent)) {
                    return result;
                }
                if (e instanceof MouseEvent) {
                    EventQueue.invokeLater(((JTextComponent) editor)::selectAll);
                } else {
                    ((JTextComponent) editor).selectAll();
                }
                return result;
            }
        };
    }
    // endregion
}
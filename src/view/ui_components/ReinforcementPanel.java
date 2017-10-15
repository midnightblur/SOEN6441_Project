package view.ui_components;

import model.game_entities.Player;
import model.ui_models.PlayerTerritoriesModel;
import utilities.Config.GAME_STATES;
import view.helpers.IntegerEditor;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Observable;
import java.util.Observer;

import static view.helpers.UIHelper.addVerticalSpacing;

/**
 * Reinforcement Panel representing the controls for Reinforcement phase of the game
 */
public class ReinforcementPanel extends JPanel implements Observer {
    private static final String PLACE_ARMIES_BUTTON = "Place armies";
    private static final String TOTAL_ARMIES_TO_PLACE_LABEL = "Armies to be placed: ";
    private static final String GO_TO_FORTIFICATION_BUTTON = "Go to Fortification";
    private static final String TRADE_CARDS_BUTTON = "Trade Cards";
    private static final String ATTACK_BUTTON = "Attack!";
    private static final String ARMIES_TO_PLACE_LABEL = "Use table to place armies:";
    
    private JButton tradeCardsButton;
    private JButton goToFortificationButton;
    private JButton attackButton;
    private JButton placeArmiesButton;
    private JLabel gameState;
    private JLabel playerID;
    private JLabel totalArmiesToPlace;
    private JLabel howManyArmiesToPlace;
    private JTable playerTerritoryTable;
    
    /* Constructors */
    public ReinforcementPanel() {
        gameState = new JLabel();
        gameState.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        playerID = new JLabel();
        playerID.setFont(new Font("Sans Serif", Font.BOLD, 20));
        tradeCardsButton = new JButton(TRADE_CARDS_BUTTON);
        tradeCardsButton.setForeground(Color.BLUE);
        totalArmiesToPlace = new JLabel();
        totalArmiesToPlace.setFont(new Font("Sans Serif", Font.BOLD, 16));
        howManyArmiesToPlace = new JLabel(ARMIES_TO_PLACE_LABEL);
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
        playerTerritoryTable.setDefaultEditor(Integer.class, new IntegerEditor());
        placeArmiesButton = new JButton(PLACE_ARMIES_BUTTON);
        placeArmiesButton.setForeground(Color.BLUE);
        goToFortificationButton = new JButton(GO_TO_FORTIFICATION_BUTTON);
        attackButton = new JButton(ATTACK_BUTTON);
        attackButton.setEnabled(false);
        attackButton.setForeground(Color.RED);

        /* Set layout */
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
        
        /* Add the elements to the panel */
        controlPanel.add(gameState);
        addVerticalSpacing(controlPanel);
        controlPanel.add(playerID);
        addVerticalSpacing(controlPanel);
        controlPanel.add(tradeCardsButton);
        addVerticalSpacing(controlPanel);
        controlPanel.add(totalArmiesToPlace);
        controlPanel.add(howManyArmiesToPlace);
        addVerticalSpacing(controlPanel);
        controlPanel.add(new JScrollPane(playerTerritoryTable));
        addVerticalSpacing(controlPanel);
        controlPanel.add(placeArmiesButton);
        addVerticalSpacing(controlPanel);
        controlPanel.add(goToFortificationButton);
        addVerticalSpacing(controlPanel);
        controlPanel.add(attackButton);
        addVerticalSpacing(controlPanel);
        
        add(controlPanel);
    }
    
    /* Getters & Setters */
    public JTable getPlayerTerritoryTable() {
        return playerTerritoryTable;
    }
    
    public void setGameState(GAME_STATES gameState) {
        this.gameState.setText("<html><p style=\"color:blue;\">" + gameState.toString() + "</html>");
    }
    
    public void setTotalArmiesToPlace(int totalArmiesToPlace) {
        this.totalArmiesToPlace.setText(TOTAL_ARMIES_TO_PLACE_LABEL + Integer.toString(totalArmiesToPlace));
    }
    
    public void setPlayerID(int playerID) {
        this.playerID.setText("Player " + playerID);
    }
    
    /* MVC & Observer pattern methods */
    public void addPlaceArmiesButtonListener(ActionListener listenerForPlaceArmiesButton) {
        placeArmiesButton.addActionListener(listenerForPlaceArmiesButton);
    }
    
    public void addGoToFortificationButtonListener(ActionListener listenerForGoToFortificationButton) {
        goToFortificationButton.addActionListener(listenerForGoToFortificationButton);
    }
    
    public void addTradeCardsButtonListener(ActionListener listenerForTradeCardsButton) {
        tradeCardsButton.addActionListener(listenerForTradeCardsButton);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof PlayerTerritoriesModel) {
            playerTerritoryTable.setModel(((PlayerTerritoriesModel) o).getModel());
        }
        if (o instanceof Player) {
            setPlayerID(((Player) o).getPlayerID());
            setTotalArmiesToPlace(((Player) o).getUnallocatedArmies());
        }
    }
}
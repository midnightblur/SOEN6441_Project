package view.ui_components;

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
 *
 * @author
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
    private JPanel controlWrapper;
    private TradeCardsPanel tradeCardsPanel;
    private JButton tradeCardsButton;
    private JButton goToFortificationButton;
    private JButton placeArmiesButton;
    private JTable playerTerritoryTable;
    // endregion
    
    // region Constructors
    public ReinforcementPanel() {
        cardsPanel = new JPanel(new CardLayout());
        JLabel gameState = new JLabel();
        gameState.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        gameState.setForeground(Color.BLUE);
        gameState.setText(GAME_STATES.REINFORCEMENT_PHASE.name());
        JLabel playerID = new JLabel();
        playerID.setFont(new Font("Sans Serif", Font.BOLD, 20));
        tradeCardsButton = new JButton(TRADE_CARDS_BUTTON);
        tradeCardsButton.setForeground(Color.BLUE);
        JLabel totalArmiesToPlace = new JLabel();
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
        setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        controlWrapper = new JPanel();
        controlWrapper.setLayout(new BoxLayout(controlWrapper, BoxLayout.PAGE_AXIS));
        JPanel topGrid = new JPanel(new GridLayout(7, 1));
        JPanel bottomGrid = new JPanel(new GridLayout(7, 1));
        
        /* Add the elements to the panel */
        topGrid.add(gameState);
        topGrid.add(playerID);
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
    public JTable getPlayerTerritoryTable() {
        return playerTerritoryTable;
    }

    public JButton getGoToFortificationButton() {
        return goToFortificationButton;
    }
    
    public JPanel getCardsPanel() {
        return cardsPanel;
    }
    
    public TradeCardsPanel getTradeCardsPanel() {
        return tradeCardsPanel;
    }
    
    public JPanel getControlWrapper() {
        return controlWrapper;
    }
    
    public static String getControlWrapperPanelName() {
        return CONTROL_WRAPPER_PANEL_NAME;
    }
    
    public static String getTradeCardsPanelName() {
        return TRADE_CARDS_PANEL_NAME;
    }
    
    // endregion
    
    // region MVC & Observer pattern methods
    public void addPlaceArmiesButtonListener(ActionListener listenerForPlaceArmiesButton) {
        placeArmiesButton.addActionListener(listenerForPlaceArmiesButton);
    }

    /**
     * Adding Listener for fortification phase.
     *
     * @param listenerForGoToFortificationButton
     */
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
    }
    // endregion
    
    // region Private methods
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
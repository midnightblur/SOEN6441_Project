package view.ui_components;

import model.RiskGame;
import model.ui_models.PlayerTerritoriesModel;
import utilities.Config;
import view.helpers.IntegerEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class FortificationControlPanel extends JPanel implements Observer {

    private static final String BACK_BUTTON_LABEL = "Back";
    private static final String MOVE_ARMIES_BUTTON_LABEL = "Move Armies";
    private static final String DONE_BUTTON_LABEL = "Done";
    private static final String TERRITORY_FROM_LABEL = "Move from: ";
    private static final String TERRITORY_TO_LABEL = "Move to: ";
    private static final String ARMIES_TO_MOVE_LABEL = "Armies to move: ";

    private JButton moveArmiesButton;
    private JButton doneButton;
    private JButton backButton;
    private JLabel gameState;
    private JLabel playerID;
    private JLabel territoryFrom;
    private JLabel territoryTo;
    private JLabel howManyArmiesToMove;
    private JTable territoryFromTable;
    private JTable territoryToTable;

    /* Constructors */
    public FortificationControlPanel() {
        gameState = new JLabel();
        gameState.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        playerID = new JLabel();
        playerID.setFont(new Font("Sans Serif", Font.BOLD, 20));
        territoryFrom = new JLabel(TERRITORY_FROM_LABEL);
        territoryFromTable = new JTable() {
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
        };
        territoryTo = new JLabel(TERRITORY_TO_LABEL);
        territoryToTable = new JTable() {
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
        };
        
        
       
        doneButton = new JButton(DONE_BUTTON_LABEL);
       
        backButton = new JButton(BACK_BUTTON_LABEL);

//        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
//        setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
//
//        /* Control panel */
//        JPanel controlPanel = new JPanel();
//        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
//
//        controlPanel.add(gameState);
//        controlPanel.add(Box.createRigidArea(new Dimension(0, 30)));
//        controlPanel.add(playerID);
//        controlPanel.add(Box.createRigidArea(new Dimension(0, 30)));
//        controlPanel.add(tradeCardsButton);
//        controlPanel.add(Box.createRigidArea(new Dimension(0, 30)));
//        controlPanel.add(totalArmiesToPlace);
//        controlPanel.add(howManyArmiesToPlace);
//        controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
//        controlPanel.add(new JScrollPane(playerTerritoryTable));
//        controlPanel.add(Box.createRigidArea(new Dimension(0, 30)));
//
//        JPanel panel_1 = new JPanel();
//        panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.PAGE_AXIS));
//        panel_1.add(placeArmiesButton);
//        panel_1.add(Box.createRigidArea(new Dimension(0, 20)));
//        panel_1.add(doneButton);
//
//        controlPanel.add(panel_1);
//        controlPanel.add(Box.createRigidArea(new Dimension(0, 50)));
//
//        /* Navigation buttons */
//        JPanel navigationPanel = new JPanel();
//        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.PAGE_AXIS));
//
//        navigationPanel.add(backButton);
//
//        add(controlPanel);
//        add(navigationPanel);
//
//    }

//    /* Getters & Setters */
//
//    public void setGameState(Config.GAME_STATES gameState) {
//        this.gameState.setText(gameState.toString());
//    }
//
//    public void setTotalArmiesToPlace(int totalArmiesToPlace) {
//        this.totalArmiesToPlace.setText(TOTAL_ARMIES_TO_PLACE_LABEL + Integer.toString(totalArmiesToPlace));
//    }
//
//    public void setPlayerID(int playerID) {
//        this.playerID.setText("Player " + playerID);
//    }
//
//    public JTable getPlayerTerritoryTable() {
//        return playerTerritoryTable;
//    }
//
//
//    /* MVC & Observer pattern methods */
//    public void addBackButtonListener(ActionListener listenerForBackButton) {
//        backButton.addActionListener(listenerForBackButton);
//    }
//
//    public void addPlaceArmiesButtonListener(ActionListener listenerForPlaceArmiesButton) {
//        placeArmiesButton.addActionListener(listenerForPlaceArmiesButton);
//    }
//
//    public void addDoneButtonListener(ActionListener listenerForDoneButton) {
//        doneButton.addActionListener(listenerForDoneButton);
//    }
//
//    public void addTradeCardsButtonListener(ActionListener listenerForTradeCardsButton) {
//        tradeCardsButton.addActionListener(listenerForTradeCardsButton);
//    }
//
//    @Override
//    public void update(Observable o, Object arg) {
//        playerTerritoryTable.setModel(((PlayerTerritoriesModel) o).getModel());
//        playerID.setText(Integer.toString(((RiskGame) o).getCurrPlayer().getPlayerID()));
//        totalArmiesToPlace.setText(Integer.toString(((RiskGame) o).getCurrPlayer().getUnallocatedArmies()));
//    }
//
//    /* Public methods */
//
//    /* Private methods */

}

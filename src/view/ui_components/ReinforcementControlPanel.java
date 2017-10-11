package view.ui_components;

import model.RiskGame;
import model.ui_models.PlayerTerritoriesModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class ReinforcementControlPanel extends JPanel implements Observer {
    private static final String GAME_STAGE_LABEL = "Game state: " + RiskGame.getInstance().getGameState().name();
    private static final String BACK_BUTTON_LABEL = "Back";
    private static final String PLACE_ARMIES_BUTTON_LABEL = "Place armies";
    private static final String TOTAL_ARMIES_TO_PLACE_LABEL = "Armies to be placed: ";
    private static final String DONE_BUTTON_LABEL = "Done";
    private static final String TRADE_CARDS_BUTTON_LABEL = "Trade Cards";
    private static final String ARMIES_TO_PLACE_LABEL = "How many armies you want to place in each territory?";
    
    private JButton tradeCardsButton;
    private JButton doneButton;
    private JButton placeArmiesButton;
    private JButton backButton;
    private JLabel gameState;
    private JLabel playerID;
    private JLabel totalArmiesToPlace;
    private JLabel howManyArmiesToPlace;
    private JTable playerTerritoryTable;
    
    /* Constructors */
    public ReinforcementControlPanel() {
        gameState = new JLabel(GAME_STAGE_LABEL);
        gameState.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        playerID = new JLabel();
        playerID.setFont(new Font("Sans Serif", Font.BOLD, 20));
        tradeCardsButton = new JButton(TRADE_CARDS_BUTTON_LABEL);
        totalArmiesToPlace = new JLabel();
        totalArmiesToPlace.setFont(new Font("Sans Serif", Font.BOLD, 16));
        howManyArmiesToPlace = new JLabel(ARMIES_TO_PLACE_LABEL);
        playerTerritoryTable = new JTable();
        doneButton = new JButton(DONE_BUTTON_LABEL);
        placeArmiesButton = new JButton(PLACE_ARMIES_BUTTON_LABEL);
        backButton = new JButton(BACK_BUTTON_LABEL);
        
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        
        /* Control panel */
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
        
        controlPanel.add(gameState);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        controlPanel.add(playerID);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        controlPanel.add(tradeCardsButton);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        controlPanel.add(totalArmiesToPlace);
        controlPanel.add(howManyArmiesToPlace);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        controlPanel.add(new JScrollPane(playerTerritoryTable));
        controlPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        JPanel panel_1 = new JPanel();
        panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.PAGE_AXIS));
        panel_1.add(placeArmiesButton);
        panel_1.add(Box.createRigidArea(new Dimension(0, 20)));
        panel_1.add(doneButton);
        controlPanel.add(panel_1);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        
        /* Navigation buttons */
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.PAGE_AXIS));
        
        navigationPanel.add(backButton);
        
        add(controlPanel);
        add(navigationPanel);
    }
    
    /* Getters & Setters */
    
    public void setTotalArmiesToPlace(int totalArmiesToPlace) {
        this.totalArmiesToPlace.setText(TOTAL_ARMIES_TO_PLACE_LABEL + Integer.toString(totalArmiesToPlace));
    }
    
    public void setPlayerID(int playerID) {
        this.playerID.setText("Player " + playerID);
    }
    
    public JTable getPlayerTerritoryTable() {
        return playerTerritoryTable;
    }
    
    /* MVC & Observer pattern methods */
    public void addBackButtonListener(ActionListener listenerForBackButton) {
        backButton.addActionListener(listenerForBackButton);
    }
    
    public void addPlaceArmiesButtonListener(ActionListener listenerForPlaceArmiesButton) {
        backButton.addActionListener(listenerForPlaceArmiesButton);
    }
    
    public void addDoneButtonListener(ActionListener listenerForDoneButton) {
        doneButton.addActionListener(listenerForDoneButton);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        playerTerritoryTable.setModel(((PlayerTerritoriesModel) o).getModel());
    }
    
    /* Public methods */
    
    /* Private methods */
    
}

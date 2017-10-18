package view.ui_components;

import model.game_entities.Territory;
import model.ui_models.DropDownModel;
import model.ui_models.GamePlayModel;
import utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import static view.helpers.UIHelper.addVerticalSpacing;

/**
 * Fortification Panel representing the controls for Fortification phase of the game
 */
public class FortificationPanel extends JPanel implements Observer {
    
    private static final String MOVE_ARMIES_BUTTON = "Move Armies";
    private static final String DONE_BUTTON = "Done (next player)";
    private static final String TERRITORY_FROM_LABEL = "Move from: ";
    private static final String TERRITORY_TO_LABEL = "Move to: ";
    private static final String ARMIES_TO_MOVE_LABEL = "Armies to move: ";
    
    private JButton moveArmiesButton;
    private JButton doneButton;
    private JTextField armiesToMoveField;
    private JLabel playerName;
    private JComboBox<String> sourceTerritoryDropdown;
    private JComboBox<String> targetTerritoryDropdown;
    
    /* Constructors */
    public FortificationPanel() {
        JLabel gameState = new JLabel();
        gameState.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        gameState.setForeground(Color.BLUE);
        gameState.setText(Config.GAME_STATES.FORTIFICATION.name());
        playerName = new JLabel();
        playerName.setFont(new Font("Sans Serif", Font.BOLD, 20));
        armiesToMoveField = new JTextField();
        JLabel sourceTerritoryLabel = new JLabel(TERRITORY_FROM_LABEL);
        JLabel targetTerritoryLabel = new JLabel(TERRITORY_TO_LABEL);
        JLabel howManyArmiesToMoveLabel = new JLabel(ARMIES_TO_MOVE_LABEL);
        sourceTerritoryDropdown = new JComboBox<>();
        targetTerritoryDropdown = new JComboBox<>();
        moveArmiesButton = new JButton(MOVE_ARMIES_BUTTON);
        moveArmiesButton.setForeground(Color.BLUE);
        doneButton = new JButton(DONE_BUTTON);

        /* Set layout */
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        JPanel controlWrapper = new JPanel();
        controlWrapper.setLayout(new GridLayout(17, 1));
        
        /* Add the elements to the panel */
        controlWrapper.add(gameState);
        controlWrapper.add(playerName);
        addVerticalSpacing(controlWrapper);
        controlWrapper.add(sourceTerritoryLabel);
        controlWrapper.add(sourceTerritoryDropdown);
        addVerticalSpacing(controlWrapper);
        controlWrapper.add(targetTerritoryLabel);
        controlWrapper.add(targetTerritoryDropdown);
        addVerticalSpacing(controlWrapper);
        controlWrapper.add(howManyArmiesToMoveLabel);
        controlWrapper.add(armiesToMoveField);
        addVerticalSpacing(controlWrapper);
        controlWrapper.add(moveArmiesButton);
        addVerticalSpacing(controlWrapper);
        addVerticalSpacing(controlWrapper);
        controlWrapper.add(doneButton);
        addVerticalSpacing(controlWrapper);
        
        add(controlWrapper);
    }
    
    /* Getters & Setters */
    public JButton getMoveArmiesButton() {
        return moveArmiesButton;
    }
    
    public JTextField getArmiesToMoveField() {
        return armiesToMoveField;
    }
    
    public JComboBox getSourceTerritoryDropdown() {
        return sourceTerritoryDropdown;
    }
    
    public JComboBox getTargetTerritoryDropdown() {
        return targetTerritoryDropdown;
    }
    
    /* MVC & Observer pattern methods */
    public void addMoveArmiesButtonListener(ActionListener listenerForMoveArmiesButton) {
        moveArmiesButton.addActionListener(listenerForMoveArmiesButton);
    }
    
    public void addSourceTerritoryDropdownListener(ActionListener listenerForSourceTerritoryDropdown) {
        sourceTerritoryDropdown.addActionListener(listenerForSourceTerritoryDropdown);
    }
    
    public void addDoneButtonListener(ActionListener listenerForDoneButton) {
        doneButton.addActionListener(listenerForDoneButton);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GamePlayModel) {
            GamePlayModel gamePlayModel = (GamePlayModel) o;
            if (gamePlayModel.getGameState() == Config.GAME_STATES.FORTIFICATION) {
                playerName.setForeground(gamePlayModel.getCurrentPlayer().getColor());
                playerName.setText(gamePlayModel.getCurrentPlayer().getPlayerName());
                
                /* Set source territories dropdown model */
                Vector<String> sourceTerritoriesList = new Vector<>();
                for (Territory territory : gamePlayModel.getCurrentPlayer().getTerritories()) {
                    sourceTerritoriesList.add(territory.getName());
                }
                DropDownModel sourceTerritoriesModel = new DropDownModel(sourceTerritoriesList);
                sourceTerritoryDropdown.setModel(sourceTerritoriesModel);
                sourceTerritoryDropdown.setSelectedIndex(0);
            }
        }
    }
}

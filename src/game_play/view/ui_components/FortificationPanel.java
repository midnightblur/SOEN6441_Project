/* 
 * Risk Game Team 2
 * FortificationPanel.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.view.ui_components;

import shared_resources.game_entities.Territory;
import game_play.model.DropDownModel;
import game_play.model.GamePlayModel;
import shared_resources.utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import static shared_resources.helper.UIHelper.addVerticalSpacing;

/**
 * FortificationPanel is responsible for representing the ui components for Fortification phase of the game.
 *
 * @author Team 2
 * @version 1.0
 */
public class FortificationPanel extends JPanel implements Observer {
    // region Attributes declaration
    private static final String MOVE_ARMIES_BUTTON = "Move Armies";
    private static final String DONE_BUTTON = "Done (next player)";
    private static final String TERRITORY_FROM_LABEL = "Move from: ";
    private static final String TERRITORY_TO_LABEL = "Move to: ";
    private static final String ARMIES_TO_MOVE_LABEL = "Armies to move: ";
    private JButton moveArmiesButton;
    private JTextField armiesToMoveField;
    private JLabel playerName;
    private JComboBox<String> sourceTerritoryDropdown;
    private JComboBox<String> targetTerritoryDropdown;
    private JButton nextPlayerButton;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiates and shows a new fortification panel.
     */
    public FortificationPanel() {
        JLabel gameState = new JLabel();
        gameState.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        gameState.setForeground(Color.BLUE);
        gameState.setText(Config.GAME_STATES.PLAYER_FORTIFICATION.name());
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
        nextPlayerButton = new JButton((DONE_BUTTON));

        /* Set layout */
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
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
        controlWrapper.add(nextPlayerButton);
        addVerticalSpacing(controlWrapper);
        
        add(controlWrapper);
    }
    // endregion
    
    // region Getters & Setters
    /**
     * Gets the armies to move field.
     *
     * @return the armies to move field
     */
    public JTextField getArmiesToMoveField() {
        return armiesToMoveField;
    }
    
    /**
     * Gets the source territory dropdown.
     *
     * @return the source territory dropdown
     */
    public JComboBox getSourceTerritoryDropdown() {
        return sourceTerritoryDropdown;
    }
    
    /**
     * Gets the target territory dropdown.
     *
     * @return the target territory dropdown
     */
    public JComboBox getTargetTerritoryDropdown() {
        return targetTerritoryDropdown;
    }
    
    // endregion
    
    // region MVC & Observer pattern methods
    /**
     * Adds the move armies button listener.
     *
     * @param listenerForMoveArmiesButton the listener for move armies button
     */
    public void addMoveArmiesButtonListener(ActionListener listenerForMoveArmiesButton) {
        moveArmiesButton.addActionListener(listenerForMoveArmiesButton);
    }
    
    /**
     * Adds the source territory dropdown listener.
     *
     * @param listenerForSourceTerritoryDropdown the listener for source territory dropdown
     */
    public void addSourceTerritoryDropdownListener(ActionListener listenerForSourceTerritoryDropdown) {
        sourceTerritoryDropdown.addActionListener(listenerForSourceTerritoryDropdown);
    }
    
    /**
     * Adds the next player button listener.
     *
     * @param listenerForNextPlayerButton the listener for next player button
     */
    public void addNextPlayerButtonListener(ActionListener listenerForNextPlayerButton) {
        nextPlayerButton.addActionListener(listenerForNextPlayerButton);
    }
    
    /**
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof GamePlayModel) {
            GamePlayModel gamePlayModel = (GamePlayModel) o;
            if (gamePlayModel.getGameState() == Config.GAME_STATES.PLAY &&
                    gamePlayModel.getCurrentPlayer().getGameState() == Config.GAME_STATES.PLAYER_FORTIFICATION) {
                playerName.setForeground(gamePlayModel.getCurrentPlayer().getColor());
                playerName.setText(gamePlayModel.getCurrentPlayer().getPlayerName());
                
                /* Set source territories dropdown game_entities */
                Vector<String> sourceTerritoriesList = new Vector<>();
                for (Territory territory : gamePlayModel.getCurrentPlayer().getTerritories()) {
                    sourceTerritoriesList.add(territory.getName());
                }
                DropDownModel sourceTerritoriesModel = new DropDownModel(sourceTerritoriesList);
                sourceTerritoryDropdown.setModel(sourceTerritoriesModel);
                sourceTerritoryDropdown.setSelectedIndex(0);
    
                armiesToMoveField.setText("");
            }
        }
    }
    // endregion
}

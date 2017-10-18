/*
 * @file  FortificationPanel.java
 * @brief 
 * 
 * 
 * 
 * @author Team 2
 * @version 1.0
 * @since  Oct 18, 2017
 * @bug No known bugs.
 */
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
 * Fortification Panel representing the controls for Fortification phase of the game.
 */
public class FortificationPanel extends JPanel implements Observer {
    
    /** The Constant MOVE_ARMIES_BUTTON. */
    private static final String MOVE_ARMIES_BUTTON = "Move Armies";
    
    /** The Constant DONE_BUTTON. */
    private static final String DONE_BUTTON = "Done (next player)";
    
    /** The Constant TERRITORY_FROM_LABEL. */
    private static final String TERRITORY_FROM_LABEL = "Move from: ";
    
    /** The Constant TERRITORY_TO_LABEL. */
    private static final String TERRITORY_TO_LABEL = "Move to: ";
    
    /** The Constant ARMIES_TO_MOVE_LABEL. */
    private static final String ARMIES_TO_MOVE_LABEL = "Armies to move: ";
    
    /** The move armies button. */
    private JButton moveArmiesButton;
    
    /** The armies to move field. */
    private JTextField armiesToMoveField;
    
    /** The player name. */
    private JLabel playerName;
    
    /** The source territory dropdown. */
    private JComboBox<String> sourceTerritoryDropdown;
    
    /** The target territory dropdown. */
    private JComboBox<String> targetTerritoryDropdown;
    
    /** The next player button. */
    private JButton nextPlayerButton;
    
    /**
     * Instantiates a new fortification panel.
     */
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
        nextPlayerButton = new JButton((DONE_BUTTON));

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
        controlWrapper.add(nextPlayerButton);
        addVerticalSpacing(controlWrapper);
        
        add(controlWrapper);
    }
    
    /**
     * Gets the armies to move field.
     *
     * @return the armies to move field
     */
    /* Getters & Setters */
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
    
    /**
     * Adds the move armies button listener.
     *
     * @param listenerForMoveArmiesButton the listener for move armies button
     */
    /* MVC & Observer pattern methods */
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
    
    /* (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
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

package view.ui_components;

import model.ui_models.FortificationModel;
import utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

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
    private JLabel gameState;
    private JLabel playerID;
    private JLabel sourceTerritoryLabel;
    private JLabel targetTerritoryLabel;
    private JLabel howManyArmiesToMoveLabel;
    private JComboBox<String> sourceTerritoryDropdown;
    private JComboBox<String> targetTerritoryDropdown;
    
    /* Constructors */
    public FortificationPanel() {
        gameState = new JLabel();
        gameState.setFont(new Font("Sans Serif", Font.ITALIC, 20));
        playerID = new JLabel();
        playerID.setFont(new Font("Sans Serif", Font.BOLD, 20));
        armiesToMoveField = new JTextField();
        sourceTerritoryLabel = new JLabel(TERRITORY_FROM_LABEL);
        targetTerritoryLabel = new JLabel(TERRITORY_TO_LABEL);
        howManyArmiesToMoveLabel = new JLabel(ARMIES_TO_MOVE_LABEL);
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
        controlWrapper.add(playerID);
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
    public void setGameState(Config.GAME_STATES gameState) {
        this.gameState.setText("<html><p style=\"color:blue;\">" + gameState.toString() + "</p></html>");
    }
    
    public void setPlayerID(int playerID) {
        this.playerID.setText("Player " + playerID);
    }
    
    public JButton getMoveArmiesButton() {
        return moveArmiesButton;
    }
    
    public JTextField getArmiesToMoveField() {
        return armiesToMoveField;
    }
    
    public void setArmiesToMoveField(JTextField armiesToMoveField) {
        this.armiesToMoveField = armiesToMoveField;
    }
    
    public JComboBox getSourceTerritoryDropdown() {
        return sourceTerritoryDropdown;
    }
    
    public void setSourceTerritoryDropdown(JComboBox sourceTerritoryDropdown) {
        this.sourceTerritoryDropdown = sourceTerritoryDropdown;
    }
    
    public JComboBox getTargetTerritoryDropdown() {
        return targetTerritoryDropdown;
    }
    
    public void setTargetTerritoryDropdown(JComboBox targetTerritoryDropdown) {
        this.targetTerritoryDropdown = targetTerritoryDropdown;
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
        if (o instanceof FortificationModel) {
            targetTerritoryDropdown.setModel(((FortificationModel) o).getTargetTerritoriesList());
        }
    }
}

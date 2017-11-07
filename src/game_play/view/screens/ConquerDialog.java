package game_play.view.screens;

import shared_resources.game_entities.Battle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Vector;

public class ConquerDialog extends JDialog {
    // region Attributes declaration
    private static final String INSTRUCTION = "Select the number of armies %s want to move from %s to %s?";
    private static final String MOVE_ARMIES_BUTTON = "Move armies";
    
    private JFrame owner;
    private JComboBox<Integer> moveArmiesDropdown;
    private JButton moveArmiesButton;
    // endregion
    
    // region Constructors
    public ConquerDialog(JFrame owner, Battle currentBattle) {
        super(owner);
        this.owner = owner;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    
        JPanel mainPanel = new JPanel(new GridLayout(0, 1   ));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        JLabel instructionLabel = new JLabel(
                String.format(INSTRUCTION, currentBattle.getAttacker().getPlayerName(),
                        currentBattle.getAttackingTerritory().getName(),
                        currentBattle.getDefendingTerritory().getName())
        );
        mainPanel.add(instructionLabel);
    
        moveArmiesDropdown = new JComboBox<>();
        int maxMovableArmies = currentBattle.getAttackingTerritory().getArmies() - 1;
        Vector<Integer> moveArmiesChoices = new Vector<>();
        for (int i = 1; i <= maxMovableArmies; i++) {
            moveArmiesChoices.add(i);
        }
        moveArmiesDropdown.setModel(new DefaultComboBoxModel<>(moveArmiesChoices.toArray(new Integer[moveArmiesChoices.size()])));
        moveArmiesDropdown.setSelectedIndex(moveArmiesDropdown.getItemCount() - 1);
        mainPanel.add(moveArmiesDropdown);
    
        moveArmiesButton = new JButton(MOVE_ARMIES_BUTTON);
        mainPanel.add(moveArmiesButton);
        
        setContentPane(mainPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
        setVisible(true);
    }
    // endregion
    
    // region Getters & Setters
    /**
     * @see JFrame#getOwner()
     */
    @Override
    public JFrame getOwner() {
        return owner;
    }
    
    public JComboBox<Integer> getMoveArmiesDropdown() {
        return moveArmiesDropdown;
    }
    
    // endregion
    
    // region MVC & Observer pattern methods
    public void addMoveArmiesButtonListener(ActionListener listenerForMoveArmiesButton) {
        moveArmiesButton.addActionListener(listenerForMoveArmiesButton);
    }
    // endregion
}

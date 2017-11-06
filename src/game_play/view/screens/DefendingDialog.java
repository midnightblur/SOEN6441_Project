package game_play.view.screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Vector;

import static shared_resources.helper.UIHelper.addVerticalSpacing;

/**
 * DefendingDialog is responsible for displaying a frame letting defender choosing how many dice to defend his territory
 */
public class DefendingDialog extends JDialog {
    // region Attribute declaration
    private static final String TITLE = "Defender choice";
    private static final String INSTRUCTION = "Choose the Number of Dice for defence:";
    private static final String DONE_BUTTON = "Done";
    
    private JComboBox<Integer> defendingDiceDropdown;
    private JButton doneButton;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiate a new Defending dialog
     *
     * @param owner             the frame containing the dialog
     * @param situation         the string informing the situation to defender
     * @param maxDefendingDice  the maximum number of dice the defender might use
     */
    public DefendingDialog(JFrame owner, String situation, int maxDefendingDice) {
        super(owner, TITLE);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        JPanel mainPanel = new JPanel(new GridLayout(0, 1   ));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
    
        JLabel situationLabel = new JLabel(situation);
        situationLabel.setForeground(Color.RED);
        mainPanel.add(situationLabel);
        addVerticalSpacing(mainPanel);
        
        JLabel instructionLabel = new JLabel(INSTRUCTION);
        mainPanel.add(instructionLabel);
        defendingDiceDropdown = new JComboBox<>();
        Vector<Integer> diceChoices = new Vector<>();
        for (int i = 1; i <= maxDefendingDice; i++) {
            diceChoices.add(i);
        }
        defendingDiceDropdown.setModel(new DefaultComboBoxModel<>(diceChoices.toArray(new Integer[diceChoices.size()])));
        defendingDiceDropdown.setSelectedIndex(defendingDiceDropdown.getItemCount() - 1);
        mainPanel.add(defendingDiceDropdown);
        doneButton = new JButton(DONE_BUTTON);
        mainPanel.add(doneButton);
        
        setContentPane(mainPanel);
    
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
        setVisible(true);
    }
    // endregion
    
    // region MVC & Observer pattern methods
    
    /**
     * Add listener for done button
     *
     * @param listenerForDoneButton the listener for the done button
     */
    public void addDoneButtonListener(ActionListener listenerForDoneButton) {
        doneButton.addActionListener(listenerForDoneButton);
    }
    // endregion
}

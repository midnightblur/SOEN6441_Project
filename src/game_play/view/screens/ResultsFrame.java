/* 
 * Risk Game Team 2
 * ResultsFrame.java
 * Version 1.0
 * Nov 22, 2017
 */
package game_play.view.screens;

import game_play.model.TournamentResultsModel;
import shared_resources.helper.UIHelper;

import javax.swing.*;
import java.awt.event.ActionListener;

import static shared_resources.helper.UIHelper.addVerticalSpacing;

/**
 * The results frame to hold the tournament reports
 *
 * @author Team 2
 * @version 3.0
 */
public class ResultsFrame extends JFrame {
    // region Attributes declaration
    private static final String TITLE = "Results";
    private static final String BACK_BUTTON = "Return to main menu";
    private static final int WIDTH = 1100;
    private static final int HEIGHT = 250;
    private JTable resultsTable;
    private JButton returnToMainButton;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiates a new TournamentFrame.
     */
    public ResultsFrame(TournamentResultsModel tournamentResultsModel) {
        setupContentPane(tournamentResultsModel);
        UIHelper.displayJFrame(this, TITLE, WIDTH, HEIGHT, true);
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Setup ui components in the content pane.
     */
    private void setupContentPane(TournamentResultsModel tournamentResultsModel) {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        resultsTable = new JTable();
        resultsTable.setModel(tournamentResultsModel.getResultsModel());
        returnToMainButton = new JButton(BACK_BUTTON);
        
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        contentPane.add(scrollPane);
        addVerticalSpacing(contentPane);
        
        contentPane.add(returnToMainButton);
        
        this.setContentPane(contentPane);
    }
    
    // endregion
    
    // region MVC & Observer pattern methods
    
    /**
     * Adds the OK button listener.
     *
     * @param listenerForOKButton the listener for OK button
     */
    public void addOKButtonListener(ActionListener listenerForOKButton) {
        returnToMainButton.addActionListener(listenerForOKButton);
    }
    // endregion
}

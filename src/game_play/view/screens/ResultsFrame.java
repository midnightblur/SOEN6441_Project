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
import java.util.Observable;
import java.util.Observer;

import static shared_resources.helper.UIHelper.addVerticalSpacing;

/**
 * The results frame to hold the tournament reports
 *
 * @author Team 2
 * @version 3.0
 */
public class ResultsFrame extends JFrame implements Observer {
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
    public ResultsFrame() {
        setupContentPane();
        UIHelper.displayJFrame(this, TITLE, WIDTH, HEIGHT, true);
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Setup ui components in the content pane.
     */
    private void setupContentPane() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        resultsTable = new JTable();
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
    
    
    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof TournamentResultsModel) {
            resultsTable.setModel(((TournamentResultsModel) o).getResultsModel());
        }
    }
    // endregion
}
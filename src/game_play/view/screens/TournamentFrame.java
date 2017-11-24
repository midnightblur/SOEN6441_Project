/* 
 * Risk Game Team 2
 * TournamentFrame.java
 * Version 1.0
 * Nov 22, 2017
 */
package game_play.view.screens;

import map_editor.model.MapEditorModel;
import shared_resources.helper.UIHelper;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import static shared_resources.helper.UIHelper.addVerticalSpacing;

/**
 * TODO
 *
 * @author Team 2
 * @version 3.0
 */
public class TournamentFrame extends JFrame implements Observer {
    // region Attributes declaration
    private static final String TITLE = "Tournament settings";
    private static final String PLAY_GAME_BUTTON = "Play Game";
    private static final String BACK_BUTTON = "Back";
    private static final String MAP_LIST_LABEL = "Select maps (1 - 5):";
    private static final String PLAYERS_LABEL = "Players (2 - 4):";
    private static final String GAME_COUNT_LABEL = "Games (1 - 5): ";
    private static final String MAX_TURNS_LABEL = "Turns (10 - 50):";
    private static final int WIDTH = 300;
    private static final int HEIGHT = 550;
    private JList<String> mapList;
    private JButton playGameBtn;
    private JButton backBtn;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiates a new TournamentFrame.
     */
    public TournamentFrame() {
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
        
        JLabel selectMap = new JLabel(MAP_LIST_LABEL);
        mapList = new JList<>();
        
        JLabel playersLabel = new JLabel(PLAYERS_LABEL);
        JTextField players = new JTextField();
        JLabel gameCountLabel = new JLabel(GAME_COUNT_LABEL);
        JTextField gameCount = new JTextField();
        JLabel maxTurnsLabel = new JLabel(MAX_TURNS_LABEL);
        JTextField maxTurns = new JTextField();
        
        backBtn = new JButton(BACK_BUTTON);
        playGameBtn = new JButton(PLAY_GAME_BUTTON);
        
        contentPane.add(selectMap);
        JScrollPane scrollPane = new JScrollPane(mapList);
        contentPane.add(scrollPane);
        addVerticalSpacing(contentPane);
    
        contentPane.add(playersLabel);
        contentPane.add(players);
        contentPane.add(gameCountLabel);
        contentPane.add(gameCount);
        contentPane.add(maxTurnsLabel);
        contentPane.add(maxTurns);
        addVerticalSpacing(contentPane);
    
        contentPane.add(playGameBtn);
        addVerticalSpacing(contentPane);
        contentPane.add(backBtn);
        
        this.setContentPane(contentPane);
    }
    
    /**
     * Gets the map dropdown.
     *
     * @return the map dropdown
     */
    public JList<String> getMapList() {
        return mapList;
    }
    // endregion
    
    // region MVC & Observer pattern methods
    
    /**
     * Adds the play game button listener.
     *
     * @param listenerForPlayGameButton the listener for play game button
     */
    public void addPlayGameButtonListener(ActionListener listenerForPlayGameButton) {
        playGameBtn.addActionListener(listenerForPlayGameButton);
    }
    
    /**
     * Adds the back button listener.
     *
     * @param listenerForBackButton the listener for back button
     */
    public void addBackButtonListener(ActionListener listenerForBackButton) {
        backBtn.addActionListener(listenerForBackButton);
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
        if (o instanceof MapEditorModel) {
            mapList.setModel(((MapEditorModel) o).getMapDropdownModel());
        }
    }
    // endregion
}

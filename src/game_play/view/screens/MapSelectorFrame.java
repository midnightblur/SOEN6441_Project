/* 
 * Risk Game Team 2
 * MapSelectorFrame.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.view.screens;

import map_editor.model.MapEditorModel;
import shared_resources.helper.UIHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import static shared_resources.helper.UIHelper.addVerticalSpacing;

/**
 * MapSelectorFrame is responsible for displaying a screen allowing users to see a list of maps and choose one of them
 *
 * @author Team 2
 * @version 1.0
 */
public class MapSelectorFrame extends JFrame implements Observer {
    // region Attributes declaration
    private static final String TITLE = "Selecting a map to play";
    private static final String PLAY_GAME_BUTTON = "Play Game";
    private static final String BACK_BUTTON = "Back";
    private static final String SELECT_MAP_LABEL = "Select a map:";
    private static final int WIDTH = 500;
    private static final int HEIGHT = 230;
    private JComboBox<String> mapDropdown;
    private JButton playGameBtn;
    private JButton backBtn;
    // endregion
    
    // region Constructors
    
    /**
     * Instantiates a new MapSelectorFrame.
     */
    public MapSelectorFrame() {
        setupContentPane();
        UIHelper.displayJFrame(this, TITLE, WIDTH, HEIGHT, true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    // endregion
    
    // region Getters & Setters
    
    /**
     * Setup ui components in the content pane.
     */
    private void setupContentPane() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        JLabel selectMap = new JLabel(SELECT_MAP_LABEL);
        selectMap.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(selectMap);
        addVerticalSpacing(contentPane);
        mapDropdown = new JComboBox<>();
        mapDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(mapDropdown);
        addVerticalSpacing(contentPane);
        
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        backBtn = new JButton(BACK_BUTTON);
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.add(backBtn);
        
        playGameBtn = new JButton(PLAY_GAME_BUTTON);
        playGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.add(playGameBtn);
        contentPane.add(buttonsPanel);
        addVerticalSpacing(contentPane);
        
        this.setContentPane(contentPane);
    }
    
    /**
     * Gets the map dropdown.
     *
     * @return the map dropdown
     */
    public JComboBox<String> getMapDropdown() {
        return mapDropdown;
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
            mapDropdown.setModel(((MapEditorModel) o).getMapDropdownModel());
        }
    }
    // endregion
}

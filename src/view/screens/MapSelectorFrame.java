/**  
 * @file  MapSelectorFrame.java 
 * @brief 
 * 
 * 
 * 
 * @author Team 2
 * @version 1.0
 * @since  Oct 18, 2017
 * @bug No known bugs.
 */
package view.screens;

import model.ui_models.MapEditorModel;
import view.helpers.UIHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import static view.helpers.UIHelper.addVerticalSpacing;

/**
 * The Class MapSelectorFrame.
 */
public class MapSelectorFrame extends JFrame implements Observer {
    
    /** The Constant TITLE. */
    // region Attributes declaration
    private static final String TITLE = "Selecting a map to play";
    
    /** The Constant PLAY_GAME_BUTTON. */
    private static final String PLAY_GAME_BUTTON = "Play Game";
    
    /** The Constant BACK_BUTTON. */
    private static final String BACK_BUTTON = "Back";
    
    /** The Constant SELECT_MAP_LABEL. */
    private static final String SELECT_MAP_LABEL = "Select a map:";
    
    /** The Constant WIDTH. */
    private static final int WIDTH = 500;
    
    /** The Constant HEIGHT. */
    private static final int HEIGHT = 230;
    
    /** The map dropdown. */
    private JComboBox<String> mapDropdown;
    
    /** The play game btn. */
    private JButton playGameBtn;
    
    /** The back btn. */
    private JButton backBtn;
    // endregion
    
    /**
     * Instantiates a new map selector frame.
     */
    // region Constructors
    public MapSelectorFrame() {
        setupContentPane();
        UIHelper.displayJFrame(this, TITLE, WIDTH, HEIGHT, false);
    }
    // endregion
    
    /**
     * Gets the map dropdown.
     *
     * @return the map dropdown
     */
    // region Getters & Setters
    public JComboBox<String> getMapDropdown() {
        return mapDropdown;
    }
    // endregion
    
    /**
     * Adds the play game button listener.
     *
     * @param listenerForPlayGameButton the listener for play game button
     */
    // region MVC & Observer pattern methods
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
    // endregion
    
    /* (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    // region Public methods
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MapEditorModel) {
            mapDropdown.setModel(((MapEditorModel) o).getMapDropdownModel());
        }
    }
    // endregion
    
    /**
     * Setup content pane.
     */
    // region Private methods
    private void setupContentPane() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
    
        JLabel selectMap = new JLabel(SELECT_MAP_LABEL);
        selectMap.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(selectMap);
        addVerticalSpacing(contentPane);
        mapDropdown = new JComboBox<>();
        mapDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(mapDropdown);
        addVerticalSpacing(contentPane);
    
        JPanel btnsPanel = new JPanel(new FlowLayout());
        backBtn = new JButton(BACK_BUTTON);
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnsPanel.add(backBtn);
        
        playGameBtn = new JButton(PLAY_GAME_BUTTON);
        playGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnsPanel.add(playGameBtn);
        contentPane.add(btnsPanel);
        addVerticalSpacing(contentPane);
        
        this.setContentPane(contentPane);
    }
    // endregion
}

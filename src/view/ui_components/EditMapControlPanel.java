package view.ui_components;

import utilities.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EditMapControlPanel extends JPanel {
    private static final String NEW_BUTTON_LABEL = "New Map";
    private static final String SAVE_BUTTON_LABEL = "Save Map";
    private static final String BACK_BUTTON_LABEL = "Back";
    private static final String ADD_CONTINENT_LABEL = "Add/Edit Continent";
    private static final String ADD_COUNTRY_LABEL = "Add/Edit Country";
    private static final String PLAY_GAME_BUTTON = "Play Game";
    
    private JButton loadMapButton;
    private JComboBox<String> chooseMapDropdown;
    private JButton saveMapButton;
    private JButton newButton;
    private JButton continentButton;
    private JButton countryButton;
    private JButton backButton;
    private JButton playButton;
    
    /* Constructors */
    public EditMapControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /* Setup map selection area */
        JPanel panel_1 = new JPanel(new FlowLayout());
        JLabel chooseMapLabel = new JLabel(Config.UI_LABEL_CHOOSE_MAP);
        chooseMapDropdown = new JComboBox<>();
        loadMapButton = new JButton(Config.UI_BUTTON_LOAD_MAP);
        panel_1.add(chooseMapLabel);
        panel_1.add(chooseMapDropdown);
        panel_1.add(loadMapButton);
        add(panel_1);
        
        /* Setup buttons */
        JPanel panel_2 = new JPanel(new FlowLayout());
        newButton = new JButton(NEW_BUTTON_LABEL);
        panel_2.add(newButton);
        add(panel_2);
        
        /* Editing buttons */
        // TODO: set their visibility (in controller) when a map is loaded
        JPanel panel_3 = new JPanel(new FlowLayout());
        continentButton = new JButton(ADD_CONTINENT_LABEL);
        countryButton = new JButton(ADD_COUNTRY_LABEL);
        saveMapButton = new JButton(SAVE_BUTTON_LABEL);
        panel_3.add(continentButton);
        panel_3.add(countryButton);
        panel_3.add(saveMapButton);
        //panel_3.setVisible(false);
        add(panel_3);
        
        /* Navigation buttons */
        JPanel panel_4 = new JPanel(new FlowLayout());
        backButton = new JButton(BACK_BUTTON_LABEL);
        playButton = new JButton(PLAY_GAME_BUTTON);
        panel_4.add(backButton);
        panel_4.add(playButton);
        add(panel_4);
        
    }
    
    /* Getters & Setters */
    public JComboBox<String> getChooseMapDropdown() {
        return chooseMapDropdown;
    }
    
    /* MVC & Observer pattern methods */
    public void addLoadMapButtonListener(ActionListener listenerForLoadMapButton) {
        loadMapButton.addActionListener(listenerForLoadMapButton);
    }
    
    public void addBackButtonListener(ActionListener listenerForBackButton) {
        backButton.addActionListener(listenerForBackButton);
    }
    
    public void addSaveMapButtonListener(ActionListener listenerSaveMapButton) {
        saveMapButton.addActionListener(listenerSaveMapButton);
    }
    
    // TODO: can we have listeners only for main frame and reuse the buttons in sub pannels?
    public void addPlayGameButtonListener(ActionListener listenerForPlayGameButton) {
        playButton.addActionListener(listenerForPlayGameButton);
    }
    
    /* Public methods */
}

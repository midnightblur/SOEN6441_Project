package view.screens;

import model.ui_models.MapEditorModel;
import view.helpers.UIHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import static view.helpers.UIHelper.addVerticalSpacing;

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
    public MapSelectorFrame() {
        setupContentPane();
        UIHelper.displayJFrame(this, TITLE, WIDTH, HEIGHT, false);
    }
    // endregion
    
    // region Getters & Setters
    public JComboBox<String> getMapDropdown() {
        return mapDropdown;
    }
    // endregion
    
    // region MVC & Observer pattern methods
    public void addPlayGameButtonListener(ActionListener listenerForPlayGameButton) {
        playGameBtn.addActionListener(listenerForPlayGameButton);
    }
    
    public void addBackButtonListener(ActionListener listenerForBackButton) {
        backBtn.addActionListener(listenerForBackButton);
    }
    // endregion
    
    // region Public methods
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MapEditorModel) {
            mapDropdown.setModel(((MapEditorModel) o).getMapDropdownModel());
        }
    }
    // endregion
    
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

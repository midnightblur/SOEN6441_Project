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
    private static final String TITLE = "Selecting a map to play";
    private static final String PLAY_GAME_BUTTON = "Play Game";
    private static final String SELECT_MAP_LABEL = "Select a map:";
    private static final int WIDTH = 500;
    private static final int HEIGHT = 230;
    
    private JPanel contentPane;
    private JButton playGameBtn;
    private JLabel selectMap;
    private JComboBox<String> mapDropdown;
    
    public MapSelectorFrame() {
        setupContentPane();
        UIHelper.displayJFrame(this, TITLE, WIDTH, HEIGHT, true);
    }
    
    /* Getters & Setters */
    public JComboBox<String> getMapDropdown() {
        return mapDropdown;
    }
    
    /* MVC methods */
    public void addPlayGameButtonListener(ActionListener listenerForPlayGameButton) {
        playGameBtn.addActionListener(listenerForPlayGameButton);
    }
    
    /* Private methods */
    private void setupContentPane() {
        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        
        selectMap = new JLabel(SELECT_MAP_LABEL);
        selectMap.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(selectMap);
        addVerticalSpacing(contentPane);
        mapDropdown = new JComboBox<>();
        mapDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(mapDropdown);
        addVerticalSpacing(contentPane);
        
        playGameBtn = new JButton(PLAY_GAME_BUTTON);
        playGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(playGameBtn);
        addVerticalSpacing(contentPane);
        
        this.setContentPane(contentPane);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MapEditorModel) {
            mapDropdown.setModel(((MapEditorModel) o).getMapDropdownModel());
        }
    }
}

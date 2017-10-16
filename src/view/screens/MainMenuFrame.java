package view.screens;

import view.helpers.UIHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static view.helpers.UIHelper.addVerticalSpacing;

public class MainMenuFrame extends JFrame {
    private static final String TITLE = "RISK game by TEAM 2";
    private static final String UI_MAP_EDITOR_BUTTON = "Map Editor";
    private static final String UI_PLAY_GAME_BUTTON = "Play Game";
    private static final String UI_QUIT_BUTTON = "Quit";
    private static final int WIDTH = 500;
    private static final int HEIGHT = 230;
    
    private JPanel contentPane;
    private JButton mapEditorBtn;
    private JButton playGameBtn;
    private JButton quitBtn;
    
    public MainMenuFrame() {
        setupContentPane();
        UIHelper.displayJFrame(this, TITLE, WIDTH, HEIGHT, true);
    }
    
    /* MVC methods */
    public void addMapEditorButtonListener(ActionListener listenerForMapEditorButton) {
        mapEditorBtn.addActionListener(listenerForMapEditorButton);
    }
    
    public void addPlayGameButtonListener(ActionListener listenerForPlayGameButton) {
        playGameBtn.addActionListener(listenerForPlayGameButton);
    }
    
    public void addQuitButtonListener(ActionListener listenerForQuitButton) {
        quitBtn.addActionListener(listenerForQuitButton);
    }
    
    /* Private methods */
    private void setupContentPane() {
        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        addVerticalSpacing(contentPane);
    
        mapEditorBtn = new JButton(UI_MAP_EDITOR_BUTTON);
        mapEditorBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(mapEditorBtn);
        addVerticalSpacing(contentPane);
    
        playGameBtn = new JButton(UI_PLAY_GAME_BUTTON);
        playGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(playGameBtn);
        addVerticalSpacing(contentPane);
    
        quitBtn = new JButton(UI_QUIT_BUTTON);
        quitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(quitBtn);
        
        this.setContentPane(contentPane);
    }
}

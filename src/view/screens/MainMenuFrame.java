package view.screens;

import view.helpers.JFrameHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuFrame extends JFrame {
    private static final String TITLE = "RISK game by TEAM 2";
    private static final String UI_MAP_EDITOR_BUTTON = "Map Editor";
    private static final String UI_PLAY_GAME_BUTTON = "Play Game";
    private static final String UI_QUIT_BUTTON = "Quit";
    private static final int WIDTH = 300;
    private static final int HEIGHT = 150;
    
    private JPanel contentPane;
    private JButton mapEditorBtn;
    private JButton playGameBtn;
    private JButton quitBtn;
    
    public MainMenuFrame() {
        setupContentPane();
        JFrameHelper.display(this, TITLE, WIDTH, HEIGHT);
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
        
        mapEditorBtn = new JButton(UI_MAP_EDITOR_BUTTON);
        mapEditorBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(mapEditorBtn);
        
        playGameBtn = new JButton(UI_PLAY_GAME_BUTTON);
        playGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(playGameBtn);
    
        quitBtn = new JButton(UI_QUIT_BUTTON);
        quitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(quitBtn);
        
        this.setContentPane(contentPane);
    }
}

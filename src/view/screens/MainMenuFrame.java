/*
 * @file  MainMenuFrame.java
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

import view.helpers.UIHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static view.helpers.UIHelper.addVerticalSpacing;

/**
 * The Class MainMenuFrame.
 */
public class MainMenuFrame extends JFrame {
    
    /** The Constant TITLE. */
    private static final String TITLE = "RISK game by TEAM 2";
    
    /** The Constant UI_MAP_EDITOR_BUTTON. */
    private static final String UI_MAP_EDITOR_BUTTON = "Map Editor";
    
    /** The Constant UI_PLAY_GAME_BUTTON. */
    private static final String UI_PLAY_GAME_BUTTON = "Play Game";
    
    /** The Constant UI_QUIT_BUTTON. */
    private static final String UI_QUIT_BUTTON = "Quit";
    
    /** The Constant WIDTH. */
    private static final int WIDTH = 500;
    
    /** The Constant HEIGHT. */
    private static final int HEIGHT = 230;
    
    /** The content pane. */
    private JPanel contentPane;
    
    /** The map editor btn. */
    private JButton mapEditorBtn;
    
    /** The play game btn. */
    private JButton playGameBtn;
    
    /** The quit btn. */
    private JButton quitBtn;
    
    /**
     * Instantiates a new main menu frame.
     */
    public MainMenuFrame() {
        setupContentPane();
        UIHelper.displayJFrame(this, TITLE, WIDTH, HEIGHT, true);
    }
    
    /**
     * Adds the map editor button listener.
     *
     * @param listenerForMapEditorButton the listener for map editor button
     */
    /* MVC methods */
    public void addMapEditorButtonListener(ActionListener listenerForMapEditorButton) {
        mapEditorBtn.addActionListener(listenerForMapEditorButton);
    }
    
    /**
     * Adds the play game button listener.
     *
     * @param listenerForPlayGameButton the listener for play game button
     */
    public void addPlayGameButtonListener(ActionListener listenerForPlayGameButton) {
        playGameBtn.addActionListener(listenerForPlayGameButton);
    }
    
    /**
     * Adds the quit button listener.
     *
     * @param listenerForQuitButton the listener for quit button
     */
    public void addQuitButtonListener(ActionListener listenerForQuitButton) {
        quitBtn.addActionListener(listenerForQuitButton);
    }
    
    /**
     * Setup content pane.
     */
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

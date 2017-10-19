/* 
 * Risk Game Team 2
 * MainMenuFrame.java
 * Version 1.0
 * Oct 18, 2017
 */
package game_play.view.screens;

import shared_resources.helper.UIHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static shared_resources.helper.UIHelper.addVerticalSpacing;

/**
 * The Class MainMenuFrame is responsible for displaying buttons allowing users to go to different screens of the application.
 * Allows the user to select from Edit Map, Play Game or Quit.
 *
 * @author Team 2
 * @version 1.0
 */
public class MainMenuFrame extends JFrame {
    // region Attributes declaration
    private static final String TITLE = "RISK game by TEAM 2";
    private static final String UI_MAP_EDITOR_BUTTON = "Map Editor";
    private static final String UI_PLAY_GAME_BUTTON = "Play Game";
    private static final String UI_QUIT_BUTTON = "Quit";
    private static final int WIDTH = 500;
    private static final int HEIGHT = 230;
    private JButton mapEditorBtn;
    private JButton playGameBtn;
    private JButton quitBtn;
    // endregion
    
    // region Constructors
    /**
     * Instantiates a new MainMenuFrame.
     */
    public MainMenuFrame() {
        setupContentPane();
        UIHelper.displayJFrame(this, TITLE, WIDTH, HEIGHT, true);
    }
    // endregion
    
    // region MVC & Observer pattern methods
    /**
     * Adds the map editor button listener.
     *
     * @param listenerForMapEditorButton the listener for map editor button
     */
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
    // endregion
    
    // region Private methods
    /**
     * Setup ui components in the content pane.
     */
    private void setupContentPane() {
        /* The content pane. */
        JPanel contentPane = new JPanel();
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
    // endregion
}

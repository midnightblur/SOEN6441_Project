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

import static shared_resources.helper.UIHelper.addHorizontalSpacing;
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
    private static final String UI_PLAY_GAME_BUTTON = "Play New Game";
    private static final String UI_LOAD_GAME_BUTTON = "Load Saved Game";
    private static final String UI_TOURNAMENT_BUTTON = "Tournament Mode";
    private static final String UI_QUIT_BUTTON = "Quit";
    private static final int WIDTH = 500;
    private static final int HEIGHT = 250;
    private JButton mapEditorBtn;
    private JButton playGameBtn;
    private JButton loadGameBtn;
    private JButton tournamentBtn;
    private JButton quitBtn;
    private JPanel playArea;
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
        
        playGameBtn = new JButton(UI_PLAY_GAME_BUTTON);
        playGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGameBtn = new JButton(UI_LOAD_GAME_BUTTON);
        loadGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        tournamentBtn = new JButton(UI_TOURNAMENT_BUTTON);
        tournamentBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        playArea = new JPanel(new FlowLayout());
        playArea.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        playArea.add(playGameBtn);
        addHorizontalSpacing(playArea);
        playArea.add(loadGameBtn);
        addHorizontalSpacing(playArea);
        playArea.add(tournamentBtn);
        contentPane.add(playArea);
        
        quitBtn = new JButton(UI_QUIT_BUTTON);
        quitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(quitBtn);
        addVerticalSpacing(contentPane);
        
        this.setContentPane(contentPane);
    }
    
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
     * Adds the load saved game button listener.
     *
     * @param listenerForLoadGameButton the listener for play game button
     */
    public void addLoadGameButtonListener(ActionListener listenerForLoadGameButton) {
        loadGameBtn.addActionListener(listenerForLoadGameButton);
    }
    
    /**
     * Adds the tournament mode button listener.
     *
     * @param listenerForTournamentButton the listener for tournament mode button
     */
    public void addTournamentButtonListener(ActionListener listenerForTournamentButton) {
        tournamentBtn.addActionListener(listenerForTournamentButton);
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
    
    
    // endregion
}

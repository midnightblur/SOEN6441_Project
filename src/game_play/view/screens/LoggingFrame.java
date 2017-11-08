/*
 * Risk Game Team 2
 * LoggingFrame.java
 * Version 1.0
 * Nov 6, 2017
 */
package game_play.view.screens;

import javax.swing.*;
import java.awt.*;

/**
 * The window used to display the game progression
 *
 * @author Team 2
 * @version 2.0
 */
public class LoggingFrame {
    private static LoggingFrame instance = null;
    private JTextArea logArea;
    
    // region Constructors
    
    /**
     * Private constructor for the Logging Window
     */
    private LoggingFrame() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenHeight = gd.getDisplayMode().getHeight();
        JFrame frame = new JFrame();
        frame.setTitle("Game Logging");
        logArea = new JTextArea();
        logArea.setMargin( new Insets(10,10,10,10) );
        logArea.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.add(new JScrollPane(logArea));
        frame.pack();
        frame.setVisible(true);
        frame.setSize(500, screenHeight - 50);
    }
    
    /**
     * Static instance method to determine if an object of LoggingWindow already exists.
     *
     * @return instance of the singleton LoggingWindow object
     */
    public static LoggingFrame getInstance() {
        if (instance == null) {
            instance = new LoggingFrame();
        }
        return instance;
    }
    // endregion
    
    /**
     * Appends text to logging text area
     *
     * @param text the text to be appended on the logging area
     */
    public void append(String text) {
        logArea.append("\n" + text);
    }
    
}

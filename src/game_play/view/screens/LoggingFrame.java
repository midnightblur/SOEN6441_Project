/*
 * Risk Game Team 2
 * LoggingFrame.java
 * Version 1.0
 * Nov 6, 2017
 */
package game_play.view.screens;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static shared_resources.utilities.Config.LOG_FILE_NAME;

/**
 * The window used to display the game progression
 *
 * @author Team 2
 * @version 2.0
 */
public class LoggingFrame extends JFrame {
    private static Charset charset = Charset.forName("UTF-8");
    private static LoggingFrame instance = null;
    private JTextArea logArea;
    private Path logPath = Paths.get(LOG_FILE_NAME);
    private BufferedWriter bufferedWriter;
    
    // region Constructors
    
    /**
     * Private constructor for the Logging Window
     */
    private LoggingFrame() {
        JFrame frame = new JFrame();
        frame.setTitle("Game Logging");
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setMargin(new Insets(10, 10, 10, 10));
        logArea.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.add(new JScrollPane(logArea));
        frame.pack();
        frame.setVisible(true);
        frame.setSize(500, 1000);
        // delete log file if exists at beginning of game session
        new File(LOG_FILE_NAME).delete();
        
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
    
    // region Public Methods
    
    /**
     * Gets the text area for logging
     *
     * @return the text area
     */
    public JTextArea getLogArea() {
        return logArea;
    }
    
    /**
     * Appends text to logging text area
     *
     * @param text the text to be appended on the logging area
     */
    public void append(String text) {
        dumpLog(text);
        logArea.append("\n" + text);
        logArea.repaint();
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    /**
     * Dumps the log to file if too large
     *
     * @param text the text string to be appended to file
     */
    private void dumpLog(String text) {
        try {
            bufferedWriter = Files.newBufferedWriter(logPath, charset, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            bufferedWriter.append(text);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // endregion
}

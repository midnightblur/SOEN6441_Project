package view.helpers;

import javax.swing.*;

public class JFrameHelper {
    private JFrameHelper() {}
    
    /**
     * Display a frame to screen
     * @param frame the frame to be displayed
     * @param title windows's title for the frame
     * @param width width of the frame
     * @param height height of the frame
     */
    public static void display(JFrame frame, String title, int width, int height) {
        frame.setTitle(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

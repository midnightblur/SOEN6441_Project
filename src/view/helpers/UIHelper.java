/**  
 * @file  UIHelper.java 
 * @brief 
 * 
 * 
 * 
 * @author Team 2
 * @version 1.0
 * @since  Oct 18, 2017
 * @bug No known bugs.
 */
package view.helpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * The Class UIHelper.
 */
public class UIHelper {
    
    /**
     * Instantiates a new UI helper.
     */
    private UIHelper() {}
    
    /**
     * Display a frame to screen.
     *
     * @param frame  the frame to be displayed
     * @param title  windows's title for the frame
     * @param width  width of the frame
     * @param height height of the frame
     * @param defaultCloseOperation the default close operation
     */
    public static void displayJFrame(JFrame frame, String title, int width, int height, boolean defaultCloseOperation) {
        frame.setTitle(title);
        if (defaultCloseOperation) {
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
        frame.pack();
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    /**
     * Set the divider position in a split frame
     * It is used when various control panels are sprung.
     *
     * @param splitPane the component subject to this method
     */
    public static void setDivider(JSplitPane splitPane) {
        splitPane.setDividerLocation(1100);
        splitPane.setResizeWeight(.75d);
    }
    
    /**
     * Ads vertical spacing within a panel
     * It is used withing various control panels.
     *
     * @param panel the component subject to this method
     */
    public static void addVerticalSpacing(JPanel panel) {
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
    }
    
    /**
     * Displays messages on UI.
     *
     * @param frame the frame
     * @param message message string
     */
    public static void displayMessage(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message);
    }
    
    /**
     * Call an inactive frame to be active again.
     *
     * @param frame the frame
     */
    public static void invokeFrame(JFrame frame) {
        frame.setVisible(true);
        frame.setEnabled(true);
    }
    
    /**
     * Disable & hide a frame which might be invoked later.
     *
     * @param frame the frame
     */
    public static void disableFrame(JFrame frame) {
        frame.setVisible(false);
        frame.setEnabled(false);
    }
    
    /**
     * Close a frame completely.
     *
     * @param frame the frame
     */
    public static void closeFrame(JFrame frame) {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}

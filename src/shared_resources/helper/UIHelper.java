/* 
 * Risk Game Team 2
 * UIHelper.java
 * Version 1.0
 * Oct 18, 2017
 */
package shared_resources.helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * The Class UIHelper with the role of:
 * <ul>
 * <li> rendering main screen frames
 * <li> setting divider location
 * <li> displaying messages to user
 * <li> providing means of navigating between frames
 * <li> providing common elements like spacing
 * </ul>
 *
 * @author Team 2
 * @version 1.0
 */
public class UIHelper {
    
    // region Constructors
    /**
     * Instantiates a new UI helper.
     */
    private UIHelper() {
    }
    // endregion
    
    // region Public methods
    /**
     * Display a frame to screen.
     *
     * @param frame                 the frame to be displayed
     * @param title                 windows's title for the frame
     * @param width                 width of the frame
     * @param height                height of the frame
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
     * @param frame   the frame
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
     * Disable and hide a frame which might be invoked later.
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
    // endregion
}

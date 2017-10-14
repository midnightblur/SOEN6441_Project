package view.helpers;

import javax.swing.*;
import java.awt.*;

public class UIHelper {
    private UIHelper() {
    }
    
    /**
     * Display a frame to screen
     *
     * @param frame  the frame to be displayed
     * @param title  windows's title for the frame
     * @param width  width of the frame
     * @param height height of the frame
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
     * It is used when various control panels are sprung
     *
     * @param splitPane the component subject to this method
     */
    public static void setDivider(JSplitPane splitPane) {
        splitPane.setDividerLocation(1100);
        splitPane.setResizeWeight(.75d);
    }
    
    /**
     * Ads vertical spacing within a panel
     * It is used withing various control panels
     *
     * @param panel the component subject to this method
     */
    public static void addVerticalSpacing(JPanel panel) {
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
    }
}

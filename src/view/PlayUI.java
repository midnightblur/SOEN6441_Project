package view;

import javax.swing.*;
import java.awt.*;

public class PlayUI extends JFrame {
    private JPanel mapDisplay;
    private JPanel mainArea;
    private JPanel controlArea;
    
    public PlayUI() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(this.mainArea);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        mainArea.setSize(new Dimension(mapDisplay.getWidth() + controlArea.getWidth() + 20, Math.max(mapDisplay.getHeight(), controlArea.getHeight()) + 40));
        this.setSize(mainArea.getSize());
    }
}

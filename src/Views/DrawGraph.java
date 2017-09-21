package Views;

import Models.GameMap;
import Models.Territory;
import Utils.Config;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class DrawGraph extends JPanel {
    private GameMap gameMap;
    private BufferedImage image;
    private FontMetrics fontMetrics;
    private Map<String, Territory> territoryMap;

    public DrawGraph(GameMap gameMap) {
        if (gameMap != null) {
            this.gameMap = gameMap;
            try {
                territoryMap = gameMap.getTerritories();
                image = ImageIO.read(new File(Config.MAPS_FOLDER + gameMap.getImage()));
                this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
            } catch (IOException e) {
                e.printStackTrace(System.err);
                JOptionPane.showMessageDialog(this, Config.MSG_MAPFILE_NO_IMAGE, Config.MSG_MAPFILE_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void paintComponent(Graphics g) {
        if (gameMap != null) {
            fontMetrics = g.getFontMetrics();

            /* draw the image */
            g.drawImage(image, 0, 0, this);

            /* draw the nodes */
            for (Map.Entry<String, Territory> entry : territoryMap.entrySet()) {
                Territory territory = entry.getValue();
                int xString = territory.getX() - fontMetrics.stringWidth(String.valueOf(territory.getArmies())) / 2;
                int yString = territory.getY() + fontMetrics.getHeight() / 2;
                g.drawString(String.valueOf(territory.getArmies()), xString, yString);
            }

        }
    }
}

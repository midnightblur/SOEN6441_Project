package Views;

import Controllers.GameMapHandler;
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

public class DrawGraph extends JFrame {
    private int width;
    private int height;
    private GameMap gameMap;

    public DrawGraph(GameMap gameMap) {
        if (gameMap != null) {
            this.setTitle(gameMap.getMapPath());
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            this.width = Config.GRAPH_NODE_WIDTH;
            this.height = Config.GRAPH_NODE_HEIGHT;
            this.gameMap = gameMap;
        }
    }

    public void paint(Graphics g) {
        if (gameMap != null) {
            try {
                FontMetrics fontMetrics = g.getFontMetrics();
                int nodeHeight = Math.max(height, fontMetrics.getHeight());
                Map<String, Territory> territoryMap = gameMap.getTerritories();

            /* draw the image */
                BufferedImage image = ImageIO.read(new File(Config.MAPS_FOLDER + gameMap.getImage()));
                g.drawImage(image, 0, 0, this);

            /* draw the nodes */
                for (Map.Entry<String, Territory> entry : territoryMap.entrySet()) {
                    Territory territory = entry.getValue();
                    int nodeWidth = Math.max(width, fontMetrics.stringWidth(String.valueOf(territory.getArmies())) + width / 2);
                    int xOval = territory.getX() - nodeWidth / 2;
                    int yOval = territory.getY() - nodeHeight / 2;
                    int xString = xOval + width / 2 - fontMetrics.stringWidth(String.valueOf(territory.getArmies())) / 2;
                    int yString = yOval + height / 2 + fontMetrics.getHeight() / 2;
                    g.drawString(String.valueOf(territory.getArmies()), xString, yString);
                }
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }
    }

    public void draw() {
        this.repaint();
    }

    public static void main(String[] args) {
        GameMapHandler gameMapHandler = new GameMapHandler();
        GameMap gameMap = gameMapHandler.readMapFile(Config.MAPS_PATH);
        DrawGraph frame = new DrawGraph(gameMap);
        if (gameMap != null) {
            frame.setSize(Config.GRAPH_FRAME_WIDTH, Config.GRAPH_FRAME_HEIGHT);
            frame.setVisible(true);
            frame.draw();
        }
        else {
            JOptionPane.showMessageDialog(frame, gameMapHandler.getValidateMsg());
        }
    }
}

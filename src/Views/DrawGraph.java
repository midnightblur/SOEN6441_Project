package Views;

import Controllers.MapsLoader;
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
        this.setTitle(gameMap.getMapPath());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.width = Config.GRAPH_NODE_WIDTH;
        this.height = Config.GRAPH_NODE_HEIGHT;
        this.gameMap = gameMap;
    }

    public void paint(Graphics g) {
        try {
            FontMetrics fontMetrics = g.getFontMetrics();
            int nodeHeight = Math.max(height, fontMetrics.getHeight());
            Map<String, Territory> territoryMap = gameMap.getTerritories();

            /* draw the image */

            BufferedImage image = ImageIO.read(new File(Config.MAPS_FOLDER + gameMap.getImage()));
            g.drawImage(image, 0, 0, this);

            /* draw the edges */
//            g.setColor(Config.GRAPH_EDGE_COLOR);
//            for (Iterator<Adjacency> iterator = gameMap.getAdjacencies().iterator(); iterator.hasNext(); ) {
//                Adjacency adjacency = iterator.next();
//                Territory t1 = territoryMap.get(adjacency.getTerritory1());
//                Territory t2 = territoryMap.get(adjacency.getTerritory2());
//
//            int xEnd1 = (int)(t1.getX() * Config.GRAPH_WIDTH_SCALE);
//            int yEnd1 = (int)(t1.getY() * Config.GRAPH_HEIGHT_SCALE);
//            int xEnd2 = (int)(t2.getX() * Config.GRAPH_WIDTH_SCALE);
//            int yEnd2 = (int)(t2.getY() * Config.GRAPH_HEIGHT_SCALE);
//
//            g.drawLine(xEnd1, yEnd1, xEnd2, yEnd2);
//            g.drawLine(t1.getX(), t1.getY(), t2.getX(), t2.getY());
//            }

            /* draw the nodes */
            for (Map.Entry<String, Territory> entry : territoryMap.entrySet()) {
                Territory territory = entry.getValue();
                int nodeWidth = Math.max(width, fontMetrics.stringWidth(String.valueOf(territory.getArmies())) + width / 2);

//            int xOval = (int)((territory.getX() - nodeWidth / 2) * Config.GRAPH_WIDTH_SCALE);
//            double heightScaleFactor = (double)(territory.getY()) / (double)(Config.GRAPH_FRAME_HEIGHT) + 1;
//            int yOval = (int)((territory.getY() - nodeHeight / 2) * heightScaleFactor);
                int xOval = territory.getX() - nodeWidth / 2;
                int yOval = territory.getY() - nodeHeight / 2;

//            g.setColor(Config.GRAPH_NODE_COLOR);
//            g.fillOval(xOval, yOval, nodeWidth, nodeHeight);
//            g.setColor(Config.GRAPH_BORDER_COLOR);
//            g.drawOval(xOval, yOval, nodeWidth, nodeHeight);

                int xString = xOval + width / 2 - fontMetrics.stringWidth(String.valueOf(territory.getArmies())) / 2;
                int yString = yOval + height / 2 + fontMetrics.getHeight() / 2;
                g.drawString(String.valueOf(territory.getArmies()), xString, yString);
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public void draw() {
        this.repaint();
    }

    public static void main(String[] args) {
        MapsLoader mapsLoader = new MapsLoader();
        GameMap gameMap = mapsLoader.readMapFile(Config.MAPS_PATH);
        DrawGraph frame = new DrawGraph(gameMap);
        frame.setSize(Config.GRAPH_FRAME_WIDTH, Config.GRAPH_FRAME_HEIGHT);
        frame.setVisible(true);
        frame.draw();
    }
}

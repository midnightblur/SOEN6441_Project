package model;

import model.GameMap;
import model.Territory;
import util.Config;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class MapPanel extends JPanel {
    private GameMap gameMap;
    private BufferedImage image;
    private FontMetrics fontMetrics;
    private Map<String, Territory> territoryMap;

    public MapPanel(GameMap gameMap) {
        if (gameMap != null) {
            this.gameMap = gameMap;
            try {
                territoryMap = gameMap.getTerritories();
                image = ImageIO.read(new File(Config.MAPS_FOLDER + gameMap.getImage()));
                this.setPreferredSize(new Dimension(image.getWidth() + 20, image.getHeight()));
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
            int i = 0;
            for (Territory territory : territoryMap.values()) {
                if (i % 6 == 0)
                    fillColorWhiteArea(territory.getX(), territory.getY(), Config.GRAPH_COLOR_P1);
                else if (i % 6 == 1)
                    fillColorWhiteArea(territory.getX(), territory.getY(), Config.GRAPH_COLOR_P2);
                else if (i % 6 == 2)
                    fillColorWhiteArea(territory.getX(), territory.getY(), Config.GRAPH_COLOR_P3);
                else if (i % 6 == 3)
                    fillColorWhiteArea(territory.getX(), territory.getY(), Config.GRAPH_COLOR_P4);
                else if (i % 6 == 4)
                    fillColorWhiteArea(territory.getX(), territory.getY(), Config.GRAPH_COLOR_P5);
                else if (i % 6 == 5)
                    fillColorWhiteArea(territory.getX(), territory.getY(), Config.GRAPH_COLOR_P6);
                i++;
            }
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

    private void fillColorWhiteArea(int x, int y, Color color) {
        Queue<Point> pointQueue = new LinkedList<>();

        Point currentPoint = new Point(x, y);
        image.setRGB(x, y, color.getRGB());
        pointQueue.add(currentPoint);

        while (!pointQueue.isEmpty()) {
            Point current = pointQueue.poll();

            ArrayList<Point> neighborPoints = new ArrayList<>();
            neighborPoints.add(new Point(current.x - 1, current.y));
            neighborPoints.add(new Point(current.x, current.y - 1));
            neighborPoints.add(new Point(current.x, current.y + 1));
            neighborPoints.add(new Point(current.x + 1, current.y));

            if (isWhitePixel(x, y - 1) || isWhitePixel(x - 1, y))
                neighborPoints.add(new Point(current.x - 1, current.y - 1));
            if (isWhitePixel(x - 1, y) || isWhitePixel(x, y + 1))
                neighborPoints.add(new Point(current.x - 1, current.y + 1));
            if (isWhitePixel(x, y - 1) || isWhitePixel(x + 1, y))
                neighborPoints.add(new Point(current.x + 1, current.y - 1));
            if (isWhitePixel(x + 1, y) || isWhitePixel(x, y + 1))
                neighborPoints.add(new Point(current.x + 1, current.y + 1));

            for (Point point : neighborPoints) {
                if (isWhitePixel(point.x, point.y)) {
                    image.setRGB(point.x, point.y, color.getRGB());
                    pointQueue.add(point);
                }
            }
        }
    }

    /**
     * Check if a pixel of a buffered image is white
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isWhitePixel(int x, int y) {
        int clr = image.getRGB(x, y);
        int red = (clr & 0x00ff0000) >> 16;
        int green = (clr & 0x0000ff00) >> 8;
        int blue = clr & 0x000000ff;
        if (red == 255 & green == 255 && blue == 255)
            return true;
        return false;
    }
}

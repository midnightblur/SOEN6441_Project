/*
http://www1.cs.columbia.edu/~bert/courses/3137/hw3_files/
Simple graph drawing class
Bert Huang
COMS 3137 Data Structures and Algorithms, Spring 2009

This class is really elementary, but lets you draw 
reasonably nice graphs/trees/diagrams. Feel free to 
improve upon it!
 */

import java.util.*;
import java.awt.*;
import javax.swing.*;

public class GraphDraw extends JFrame {

    /* node size */
    int width;
    int height;

    /* lists for nodes and edges */
    ArrayList<Node> nodes;
    ArrayList<Edge> edges;

    /**
     * Parameterized contructor
     *
     * @param windowName
     */
    public GraphDraw(String windowName) {
        this.setTitle(windowName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
        width = 50;
        height = 50;
    }

    /**
     * The node
     */
    class Node {
        int x, y;
        String label;

        public Node(String myLabel, int x, int y) {
            this.x = x;
            this.y = y;
            label = myLabel;
        }
    }

    /**
     * The edge
     */
    class Edge {
        int i, j;

        public Edge(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    /**
     * add a node in the node array list at coordinates (x,y)
     *
     * @param label
     * @param x
     * @param y
     */
    public void addNode(String label, int x, int y) {
        nodes.add(new Node(label, x, y));
        this.repaint();  // this is reducing performance, need to be removed from here and called only once
    }

    /**
     * add an edge in edges array list between nodes i and j
     *
     * @param i
     * @param j
     */
    public void addEdge(int i, int j) {
        edges.add(new Edge(i, j));
        this.repaint(); // this is reducing performance, need to be removed from here and called only once
    }

    /**
     * draw the nodes and edges
     *
     * @param g
     */
    public void paint(Graphics g) {
        // adapt height of node based on font
        FontMetrics f = g.getFontMetrics();
        int nodeHeight = Math.max(height, f.getHeight());

        /* draw the edges */
        g.setColor(Color.black);
        for (Edge e : edges) {
            g.drawLine(nodes.get(e.i).x, nodes.get(e.i).y, nodes.get(e.j).x, nodes.get(e.j).y);
        }

        /* draw the nodes */
        for (Node n : nodes) {
            int nodeWidth = Math.max(width, f.stringWidth(n.label) + width / 2);
            g.setColor(Color.yellow);
            g.fillOval(n.x - nodeWidth / 2, n.y - nodeHeight / 2, nodeWidth, nodeHeight);
            g.setColor(Color.black);
            g.drawOval(n.x - nodeWidth / 2, n.y - nodeHeight / 2, nodeWidth, nodeHeight);
            g.drawString(n.label, n.x - f.stringWidth(n.label) / 2, n.y + f.getHeight() / 2);
        }
    }
}

/**
 * Testing class
 */
class testGraphDraw {
    public static void main(String[] args) {
        GraphDraw frame = new GraphDraw("Graph Drawing");

        frame.setSize(1000, 800);
        frame.setVisible(true);

        frame.addNode("a", 150, 250);
        frame.addNode("b", 400, 300);
        frame.addNode("c", 350, 400);
        frame.addEdge(0, 1);
        frame.addEdge(0, 2);
        frame.addEdge(1, 2);
    }
}
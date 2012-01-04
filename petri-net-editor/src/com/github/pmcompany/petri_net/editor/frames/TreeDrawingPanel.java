package com.github.pmcompany.petri_net.editor.frames;

import com.github.pmcompany.petri_net.model.util.AttainabilityTreeNode;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * User: vitaliy
 * Date: 19.12.11
 * Time: 19:30
 */
public class TreeDrawingPanel extends JPanel {
    public static final int CIRCLE_RADIUS = 10;
    public static final int WIDTH = 500;
    public static final int LEVEl_H = 30;
    private AttainabilityTreeNode root;

    public TreeDrawingPanel(AttainabilityTreeNode root) {
        this.root = root;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setBackground(Color.white);

        LinkedList<GraphicsNode> currentQueue = new LinkedList<GraphicsNode>();
        LinkedList<GraphicsNode> childQueue = new LinkedList<GraphicsNode>();
        currentQueue.add(new GraphicsNode(root, false, 0));
        int level = 0;
        while (!currentQueue.isEmpty()) {
            int distance = WIDTH / (currentQueue.size() + 1);
            childQueue = new LinkedList<GraphicsNode>();
            level++;
            int i = 0;
            while (!currentQueue.isEmpty()) {
                GraphicsNode currentNode = currentQueue.removeFirst();
                Color c;
                switch (currentNode.treeNode.getType()) {
                    case INTERNAL:
                        c = Color.gray;
                        break;
                    case TERMINAL:
                        c = Color.black;
                        break;
                    case DUPLICATE:
                        c = Color.yellow;
                        break;
                    default:
                        c = Color.red;
                        break;
                }
                i++;
                drawCircle(g, distance * (i), level * LEVEl_H, c);

                g.setColor(Color.black);
                g.drawString(currentNode.toString(), distance * (i), level * LEVEl_H);

                if (currentNode.hasParent) {
                    int x1 = distance * (i);
                    int y1 = level * LEVEl_H;
                    int x2 = currentNode.parrentOffset;
                    int y2 = (level - 1) * LEVEl_H;

                    g.setColor(Color.black);
                    g.drawLine(x1, y1, x2, y2);
                    g.drawString(currentNode.pathToThisLife(), x1 + (x2-x1)/2, y1 + (y2-y1)/2);
                }
                for (AttainabilityTreeNode chNode : currentNode.treeNode.getChildren()) {
                    childQueue.add(new GraphicsNode(chNode, true, i * distance));
                }
            }
            currentQueue = childQueue;
        }
    }

    private void drawCircle(Graphics2D g, int x, int y, Color color) {
        g.setColor(color);
        g.fillOval(x - CIRCLE_RADIUS, y - CIRCLE_RADIUS, 2 * CIRCLE_RADIUS, 2 * CIRCLE_RADIUS);
    }
}

class GraphicsNode {
    public AttainabilityTreeNode treeNode;
    public boolean hasParent;
    public int parrentOffset;

    GraphicsNode(AttainabilityTreeNode treeNode, boolean hasParent, int parrentOffset) {
        this.treeNode = treeNode;
        this.hasParent = hasParent;
        this.parrentOffset = parrentOffset;
    }

    public String toString() {
        return treeNode.getName();
    }

    public String pathToThisLife() {
        return treeNode.getPath();
    }
}

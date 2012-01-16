package com.github.pmcompany.petri_net.editor.frames;

import com.github.pmcompany.petri_net.model.util.AttainabilityTreeNode;
import com.github.pmcompany.petri_net.model.util.AttainabilityTreeNodeType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * User: vitaliy
 * Date: 19.12.11
 * Time: 19:30
 */
public class MDrawingPanel extends JPanel {
    public static final int CIRCLE_RADIUS = 10;
    public static final int RADIUS = 300;
    private AttainabilityTreeNode root;

    public MDrawingPanel(AttainabilityTreeNode root) {
        this.root = root;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.paint(graphics);
    }

    public void paint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setBackground(Color.white);

        int n = countNonDuplicative(root);
        double f = 2 * Math.PI / n;
        ArrayList<AttainabilityTreeNode> nodes = new ArrayList<AttainabilityTreeNode>();
        nodes.add(root);

        int c = 0;
        int i = 0;

        while (i < nodes.size()) {
            AttainabilityTreeNode node = nodes.get(i);
            if (node.getType() != AttainabilityTreeNodeType.DUPLICATE) {
                Color color;
                switch (node.getType()) {
                    case INTERNAL:
                        color = Color.gray;
                        break;
                    case TERMINAL:
                        color = Color.black;
                        break;
                    default:
                        color = Color.red;
                        break;
                }
                drawCircle(g, RADIUS, c * f, color, node.toString());
                int pc = nodes.indexOf(node.getParent());
                if (pc != -1)
                    connect(g, RADIUS, c * f, RADIUS, pc * f);
                c++;
            } else {
                int pc = nodes.indexOf((node.getParent()));
                if (nodes.contains(node.getDuplicate())) {
                    int cc = nodes.indexOf(node.getDuplicate());
                    connect(g, RADIUS, pc * f, RADIUS, cc * f);
                }
            }
            for (AttainabilityTreeNode child : node.getChildren()) {
                nodes.add(child);
            }
            i++;
        }
    }

    private void drawCircle(Graphics2D g, double r, double f, Color color, String s) {
        g.setColor(color);
        g.fillOval((int) (this.getWidth() / 2 + r * Math.cos(f) - CIRCLE_RADIUS), (int) (this.getHeight() / 2 - r * Math.sin(f) - CIRCLE_RADIUS), 2 * CIRCLE_RADIUS, 2 * CIRCLE_RADIUS);
        g.drawString(s, (int) (this.getWidth() / 2 + r * Math.cos(f) + CIRCLE_RADIUS + 3), (int) (this.getHeight() / 2 - r * Math.sin(f) - CIRCLE_RADIUS));
    }

    private void connect(Graphics2D g, double r1, double f1, double r2, double f2) {
        g.setColor(Color.BLACK);
        g.drawLine((int) (this.getWidth() / 2 + r1 * Math.cos(f1)), (int) (this.getHeight() / 2 - r1 * Math.sin(f1)), (int) (this.getWidth() / 2 + r2 * Math.cos(f2)), (int) (this.getHeight() / 2 - r2 * Math.sin(f2)));
    }

    private static int countNonDuplicative(AttainabilityTreeNode root) {
        int n = 1;
        for (AttainabilityTreeNode child : root.getChildren()) {
            if (child.getType() != AttainabilityTreeNodeType.DUPLICATE) {
                n += countNonDuplicative(child);
            }
        }
        return n;
    }


    class MarkGraphicsNode {
        public AttainabilityTreeNode treeNode;
        public MarkGraphicsNode parent;
        public boolean hasParent;
        public double radius;
        public double angle;

        MarkGraphicsNode(AttainabilityTreeNode treeNode, MarkGraphicsNode parent, boolean hasParent, double radius, double angle) {
            this.treeNode = treeNode;
            this.parent = parent;
            this.hasParent = hasParent;
            this.radius = radius;
            this.angle = angle;
        }

        public String toString() {
            return treeNode.getName();
        }

        public String pathToThisLife() {
            return treeNode.getPath();
        }

        public boolean getHasParent() {
            return hasParent;
        }

        public void setHasParent(boolean b) {
            hasParent = b;
        }
    }
}

package com.github.pmcompany.petri_net.editor.elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dector (dector9@gmail.com)
 */
public class GraphicsElement {
    public static final int PLACE_SIZE = 50;

    public static final int TRANSITION_WIDTH = 30;
    public static final int TRANSITION_HEIGHT = 50;

    public static final int MOMENTAL_TRANSITION_WIDTH = 15;
    public static final int MOMENTAL_TRANSITION_HEIGHT = 50;

    private PTNetElements type;
    private Point position;

    private List<Connection> outputConnections;
    private List<Connection> inputConnections;

    public GraphicsElement(PTNetElements type, int x, int y) {
        this.type = type;

        position = new Point(x, y);

        outputConnections = new ArrayList<Connection>();
        inputConnections = new ArrayList<Connection>();
    }

    public int getX() {
        return position.getX();
    }

    public void setX(int x) {
        position.setX(x);
    }

    public int getY() {
        return position.getY();
    }

    public void setY(int y) {
        position.setY(y);
    }

    public PTNetElements getType() {
        return type;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getWidth() {
        int width = 0;

        switch (type) {
            case PLACE: width = PLACE_SIZE; break;
            case TRANSITION: width = TRANSITION_WIDTH; break;
            case MOMENTAL_TRANSITION: width = MOMENTAL_TRANSITION_WIDTH; break;
        }

        return width;
    }

    public int getHeight() {
        int height = 0;

        switch (type) {
            case PLACE: height = PLACE_SIZE; break;
            case TRANSITION: height = TRANSITION_HEIGHT; break;
            case MOMENTAL_TRANSITION: height = MOMENTAL_TRANSITION_HEIGHT; break;
        }

        return height;
    }

    public Point getOuterPoint(double sinTheta, double cosTheta, boolean right) {
        Point p = null;

        Point pos = getPosition();

        int sign;
        if (right) {
            sign = 1;
        } else {
            sign = -1;
        }

        switch (getType()) {
            case PLACE: {
                p = new Point((int) (pos.getX() + sign * getWidth()/2 * cosTheta),
                        (int) (pos.getY() + sign * getWidth()/2 * sinTheta));
            } break;

            case TRANSITION:
            case MOMENTAL_TRANSITION: {
                p = new Point(pos.getX() + sign * getWidth()/2, (int) (pos.getY() + sign * getWidth()/2 * sinTheta));
            }
        }

        return p;
    }

    public void addInputConnection(Connection connection) {
        if (connection.getTo() == this) {
            inputConnections.add(connection);
        }
    }

    public void addOutputConnection(Connection connection) {
        if (connection.getFrom() == this) {
            outputConnections.add(connection);
        }
    }

    public List<Connection> getInputConnections() {
        return inputConnections;
    }

    public List<Connection> getOutputConnections() {
        return outputConnections;
    }
}

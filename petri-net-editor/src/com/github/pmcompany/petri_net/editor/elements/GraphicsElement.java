package com.github.pmcompany.petri_net.editor.elements;

/**
 * @author dector (dector9@gmail.com)
 */
public class GraphicsElement {
    public static final int PLACE_SIZE = 50;

    public static final int TRANSITION_WIDTH = 30;
    public static final int TRANSITION_HEIGHT = 50;

    public static final int MOMENTAL_TRANSITION_WIDTH = 15;
    public static final int MOMENTAL_TRANSITION_HEIGHT = 50;

    public static int SELECTION_BORDER_WIDTH = PLACE_SIZE / 5;

    private PTNetElements type;
    private int x;
    private int y;

    private int prevX;
    private int prevY;

    public GraphicsElement(PTNetElements type, int x, int y) {
        this.type = type;

        this.x = x;
        this.prevX = x;
        this.y = y;
        this.prevY = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public PTNetElements getType() {
        return type;
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

    public int getPrevX() {
        return prevX;
    }

    public void setPrevX(int prevX) {
        this.prevX = prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    public void setPrevY(int prevY) {
        this.prevY = prevY;
    }
}

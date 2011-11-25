package com.github.pmcompany.petri_net.editor.elements;

/**
 * @author dector (dector9@gmail.com)
 */
public class GraphicsElement {
    public static final int PLACE_SIZE = 50;
    public static final float TRANSACTION_PROPORTION = 0.75f;

    private PTNetElements type;
    private int x;
    private int y;

    private boolean dragging;

    public GraphicsElement(PTNetElements type, int x, int y, boolean dragging) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.dragging = dragging;
    }

    public GraphicsElement(PTNetElements type, int x, int y) {
        this(type, x, y, false);
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

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }
}

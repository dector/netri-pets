package com.github.pmcompany.petri_net.editor.elements;

/**
 * @author dector (dector9@gmail.com)
 */
public class Point {
    private int x;
    private int y;

    public Point() {}

    public Point(Point p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
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

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return x + ":" + y;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;

        equals |= ((Point)obj).getX() == getX();
        equals |= ((Point)obj).getY() == getY();

        return equals;
    }
}

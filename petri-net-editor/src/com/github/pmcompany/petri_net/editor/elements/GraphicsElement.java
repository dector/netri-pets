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

    private PTNetElements type;
    private Point position;

    public GraphicsElement(PTNetElements type, int x, int y) {
        this.type = type;

        position = new Point(x, y);
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

    public Point[] getConnectionPointsWith(Point p1, int r1) {
        Point[] points = new Point[2];

        int x0 = getX();
        int y0 = getY();
        int r0 = getWidth()/2;

        int x1 = p1.getX();
        int y1 = p1.getY();

        double a = x1 - x0;
        double b = y1 - y0;
        double c = Math.sqrt(a*a + b*b);

        double cosFi = a/c;
        double sinFi = b/c;

        switch (getType()) {
            case PLACE: {
                points[0] = new Point((int) (x0 + r0 * cosFi), (int) (y0 + r0 * sinFi));
                points[1] = new Point(x1 - r1, (int) (y1 - r1 * sinFi));
            } break;

            case TRANSITION:
            case MOMENTAL_TRANSITION: {
                points[0] = new Point(x0 + r0, (int) (y0 + r0 * sinFi));
                points[1] = new Point((int) (x1 - r1 * cosFi), (int) (y1 - r1 * sinFi));
            }
        }

        return points;
    }
}

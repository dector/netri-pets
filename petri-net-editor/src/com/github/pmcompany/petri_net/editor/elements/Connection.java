package com.github.pmcompany.petri_net.editor.elements;

import com.github.pmcompany.petri_net.model.Arc;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author dector (dector9@gmail.com)
 */
public abstract class Connection {
    public static final int ARROW_OUTER_LENGTH = 12;
    public static final int ARROW_INNER_LENGTH = 8;
    public static final float ARROW_ANGLE = 30 * (float)Math.PI/180;
    public static final float ARROW_ANGLE_TAN = (float)Math.tan(ARROW_ANGLE);

    private GraphicsElement from;
    private GraphicsElement to;

    private Arc arc;

    public Connection(GraphicsElement from, GraphicsElement to) {
        this.from = from;
        this.to = to;
    }

    public Connection(GraphicsElement from) {
        this.from = from;
    }

    public GraphicsElement getFrom() {
        return from;
    }

    public void setFrom(GraphicsElement from) {
        this.from = from;
    }

    public GraphicsElement getTo() {
        return to;
    }

    public void setTo(GraphicsElement to) {
        this.to = to;
    }

    public Point getStart() {
        return from.getPosition();
    }

    public Point getEnd() {
        return (to != null) ? to.getPosition() : null;
    }

    public List<Point> getMiddlePoints() {
        return null;
    }

    public void addMiddlePoint(Point point) {}

    /*public void countOptimaCoord() {
        if (isInsideFromElement()) {
            return;
        }

        int x0 = from.getX();
        int y0 = from.getY();

        double a = end.getX() - x0;
        double b = end.getY() - y0;
        double c = Math.sqrt(a*a + b*b);

        double cosFi = a/c;
        double sinFi = b/c;

        switch (from.getType()) {
            case PLACE: {
                int r = from.getWidth()/2;
                setStart((int)(x0 + r*cosFi), (int)(y0 + r*sinFi));
            } break;
        }

        System.out.printf("END Counting optima coords from (%d:%d) -> (%d:%d) to %d:%d%n",
                x0, y0, start.getX(), start.getY(), end.getX(), end.getY());

        // Count arrow pointers
        double fi = (Math.acos(cosFi) + Math.asin(sinFi)) / 2;
        double cosAlpha = Math.cos(Connection.ARROW_ANGLE);
        double cos2Alpha = Math.cos(2*Connection.ARROW_ANGLE);
        double sinAlpha = Math.sin(Connection.ARROW_ANGLE);
        double sin2Alpha = Math.sin(2 * Connection.ARROW_ANGLE);

        double arrowPoint1X = getEnd().getX() + Connection.ARROW_LENGTH * (cosAlpha*cosFi + sinAlpha*sinFi);
        double arrowPoint1Y = getEnd().getY() + Connection.ARROW_LENGTH * (sinAlpha*cosFi + cosAlpha*sinFi);
        double arrowPoint2X = getEnd().getX() + Connection.ARROW_LENGTH * (cos2Alpha*cosFi + sin2Alpha*sinFi);
        double arrowPoint2Y = getEnd().getY() + Connection.ARROW_LENGTH * (sin2Alpha*cosFi + cos2Alpha*sinFi);

        setArrowPointOne((int)arrowPoint1X, (int)arrowPoint1Y);
        setArrowPointTwo((int) arrowPoint2X, (int) arrowPoint2Y);
    }*/

    public boolean isInsideFromElement(Point end) {
        boolean inside = false;

        int x0 = from.getX();
        int y0 = from.getY();

        int x = end.getX();
        int y = end.getY();

        switch (from.getType()) {
            case PLACE: {
                int r = GraphicsElement.PLACE_SIZE/2;

                inside = (Math.pow(x - x0, 2) + Math.pow(y - y0, 2) <= Math.pow(r, 2));
            } break;

            case TRANSITION: {
                int w = GraphicsElement.TRANSITION_WIDTH/2;
                int h = GraphicsElement.TRANSITION_HEIGHT/2;

                inside = (Math.abs(x - x0) <= w) && (Math.abs(y - y0) <= h);
            } break;

            case MOMENTAL_TRANSITION: {
                int w = GraphicsElement.MOMENTAL_TRANSITION_WIDTH/2;
                int h = GraphicsElement.MOMENTAL_TRANSITION_HEIGHT/2;

                inside = (Math.abs(x - x0) <= w) && (Math.abs(y - y0) <= h);
            } break;
        }

        return inside;
    }

    public boolean hasMiddlepoints() {
        return false;
    }

    public int countMiddlepoints() {
        return 0;
    }

    public Point getFirstMiddlepoint() {
        return null;
    }

    public Point getLastMiddlepoint() {
        return null;
    }

    public boolean hasOneMiddlepoint() {
        return false;
    }

    public Arc getArc() {
        return arc;
    }

    public void setArc(Arc arc) {
        this.arc = arc;
    }
}

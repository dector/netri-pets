package com.github.pmcompany.petri_net.editor.elements;

import java.util.List;

/**
 * @author dector (dector9@gmail.com)
 */
public class BreakedConnection extends Connection {
    private List<Point> middlePoints;

    public BreakedConnection(GraphicsElement from, GraphicsElement to) {
        super(from, to);
    }

    public BreakedConnection(GraphicsElement from) {
        super(from);
    }

    @Override
    public List<Point> getMiddlePoints() {
        return middlePoints;
    }

    @Override
    public void addMiddlePoint(Point point) {
        middlePoints.add(point);
    }
}

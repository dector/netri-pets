package com.github.pmcompany.petri_net.editor.elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dector (dector9@gmail.com)
 */
public class BreakedConnection extends Connection {
    private List<Point> middlePoints;

    public BreakedConnection(GraphicsElement from, GraphicsElement to) {
        super(from, to);
        init();
    }

    public BreakedConnection(GraphicsElement from) {
        super(from);
        init();
    }

    private void init() {
        middlePoints = new ArrayList<Point>();
    }

    @Override
    public List<Point> getMiddlePoints() {
        return middlePoints;
    }

    @Override
    public void addMiddlePoint(Point point) {
        middlePoints.add(point);
        System.out.println("Middlepoint added");
    }

    @Override
    public int countMiddlepoints() {
        return middlePoints.size();
    }

    @Override
    public boolean hasMiddlepoints() {
        return ! middlePoints.isEmpty();
    }

    @Override
    public boolean hasOneMiddlepoint() {
        return countMiddlepoints() == 1;
    }

    @Override
    public Point getLastMiddlepoint() {
        if (hasMiddlepoints()) {
            return middlePoints.get(countMiddlepoints()  - 1);
        } else {
            return null;
        }
    }

    @Override
    public Point getFirstMiddlepoint() {
        if (hasMiddlepoints()) {
            return middlePoints.get(0);
        } else {
            return null;
        }
    }
}

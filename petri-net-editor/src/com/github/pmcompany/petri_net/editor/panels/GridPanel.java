/**
 * Copyright (c) 2011, PM Company (dector, vitalyp) All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  - Neither the name of the nor the names of its
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.pmcompany.petri_net.editor.panels;

import com.github.pmcompany.petri_net.editor.Grid;
import com.github.pmcompany.petri_net.editor.Settings;
import com.github.pmcompany.petri_net.editor.elements.GraphicsElement;
import com.github.pmcompany.petri_net.editor.elements.PTNetElements;
import com.github.pmcompany.petri_net.editor.listeners.GridPanelListener;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * JPanel which draws P/T net components
 *
 * @author dector (dector9@gmail.com)
 * @version 0.1
 */
public class GridPanel extends JPanel {
    /** Grid parameters */
    private Grid grid;

    private List<GraphicsElement> addedElements;

    private List<GraphicsElement> draggedElements;
    private List<GraphicsElement> selectedElements;

    private Stroke elementStroke;

    /**
     * Create new instance with determined size
     *
     * @param dimension panels's size
     */
    public GridPanel(Dimension dimension) {
        addedElements = new ArrayList<GraphicsElement>();
        draggedElements = new ArrayList<GraphicsElement>();
        selectedElements = new ArrayList<GraphicsElement>();

        grid = new Grid();
        setSize(dimension);

        elementStroke = new BasicStroke(Settings.ELEMENT_BORDER_WIDTH);

        GridPanelListener gpl = new GridPanelListener(this);
        addMouseListener(gpl);
        addMouseMotionListener(gpl);
    }

    /**
     * Draw P/T net
     *
     * @param graphics Graphics2D component to draw
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;

        //drawGrid
        g.setColor(Settings.GRID_COLOR);
        for (int x = grid.getLeftCoord(); x < getWidth(); x += grid.getStepX()) {
            for (int y = grid.getTopCoord(); y < getHeight(); y += grid.getStepY()) {
                g.drawRect(x, y, Settings.GRID_WIDTH, Settings.GRID_WIDTH);
            }
        }

        // ========== [BEGIN] DRAW P/T NET
        g.setStroke(elementStroke);

        int elementX;
        int elementY;
        boolean dragging;
        boolean selected;
        for (GraphicsElement currElement : addedElements) {
            elementX = currElement.getX();
            elementY = currElement.getY();
            dragging = isDragging(currElement);
            selected = isSelected(currElement);

            switch (currElement.getType()) {
                case PLACE: {
                    elementX -= GraphicsElement.PLACE_SIZE/2;
                    elementY -= GraphicsElement.PLACE_SIZE/2;

                    if (selected) {
                        g.setColor(Settings.SELECTION_FILL_COLOR);
                    } else {
                        g.setColor(Settings.PLACE_FILL_COLOR);
                    }

                    g.fillOval(elementX, elementY, GraphicsElement.PLACE_SIZE, GraphicsElement.PLACE_SIZE);

                    if (dragging) {
                        g.setColor(Settings.DRAGGING_PLACE_BORDER_COLOR);
                    } else {
                        g.setColor(Settings.PLACE_BORDER_COLOR);
                    }

                    g.drawOval(elementX, elementY, GraphicsElement.PLACE_SIZE, GraphicsElement.PLACE_SIZE);
                } break;
                case TRANSITION: {
                    elementX -= GraphicsElement.TRANSITION_WIDTH/2;
                    elementY -= GraphicsElement.TRANSITION_HEIGHT/2;

                    if (selected) {
                        g.setColor(Settings.SELECTION_FILL_COLOR);
                    } else {
                        g.setColor(Settings.TRANSITION_FILL_COLOR);
                    }
                    g.fillRect(elementX, elementY, GraphicsElement.TRANSITION_WIDTH, GraphicsElement.TRANSITION_HEIGHT);

                    if (dragging) {
                        g.setColor(Settings.DRAGGING_TRANSITION_BORDER_COLOR);
                    } else {
                        g.setColor(Settings.TRANSITION_BORDER_COLOR);
                    }

                    g.drawRect(elementX, elementY, GraphicsElement.TRANSITION_WIDTH, GraphicsElement.TRANSITION_HEIGHT);
                } break;
                case MOMENTAL_TRANSITION: {
                    elementX -= GraphicsElement.MOMENTAL_TRANSITION_WIDTH/2;
                    elementY -= GraphicsElement.MOMENTAL_TRANSITION_HEIGHT/2;

                    if (selected) {
                        g.setColor(Settings.SELECTION_FILL_COLOR);
                    } else {
                        g.setColor(Settings.MOMENTAL_TRANSITION_FILL_COLOR);
                    }
                    g.fillRect(elementX, elementY, GraphicsElement.MOMENTAL_TRANSITION_WIDTH, GraphicsElement.MOMENTAL_TRANSITION_HEIGHT);

                    if (dragging) {
                        g.setColor(Settings.DRAGGING_MOMENTAL_TRANSITION_BORDER_COLOR);
                    } else {
                        g.setColor(Settings.MOMENTAL_TRANSITION_BORDER_COLOR);
                    }

                    g.drawRect(elementX, elementY, GraphicsElement.MOMENTAL_TRANSITION_WIDTH, GraphicsElement.MOMENTAL_TRANSITION_HEIGHT);
                } break;
            }

        }
        // ========== [END] DRAW P/T NET

    }

    /**
     * Returns preffered component size
     *
     * @return preffered component size
     */
    @Override
    public Dimension getPreferredSize() {
        return getSize();
    }

    public void addElement(GraphicsElement element) {
        addedElements.add(element);
    }

    public void addAndSelectElement(PTNetElements type, int x, int y, boolean multiselect) {
        System.out.printf("Adding and selecting %s at %d:%d%n", type, x, y);
        GraphicsElement element = new GraphicsElement(type, x, y);
        addElement(element);
        selectElement(element, multiselect);
    }

    public void addElement(PTNetElements type, int x, int y) {
        addedElements.add(new GraphicsElement(type, x, y));
    }

    public GraphicsElement getElementAt(int x, int y) {
        GraphicsElement element = null;

        ListIterator<GraphicsElement> iterator = addedElements.listIterator(addedElements.size());

        GraphicsElement currElement;
        int borderLeft;
        int borderRight;
        int borderTop;
        int borderBottom;

        while (element == null && iterator.hasPrevious()) {
            currElement = iterator.previous();

            borderLeft = currElement.getX() - currElement.getWidth()/2;
            borderRight = currElement.getX() + currElement.getWidth()/2;
            borderBottom = currElement.getY() - currElement.getHeight()/2;
            borderTop = currElement.getY() + currElement.getHeight()/2;

            if (borderLeft <= x && x <= borderRight && borderBottom <= y && y <= borderTop) {
                element = currElement;
            }
        }

        return element;
    }

    public void startDragElements() {
        draggedElements.addAll(selectedElements);
    }

    public void stopDragElements() {
        draggedElements.clear();
    }

    public void dragElements(int dx, int dy) {
        for (GraphicsElement element : draggedElements) {
            element.setX(element.getX() + dx);
            element.setY(element.getY() + dy);
        }
    }

    private void selectOneElement(GraphicsElement element) {
        unselectAllElements();
        selectAnotherElement(element);
    }

    public void selectElement(GraphicsElement element, boolean multiselect) {
        if (element != null) {
            if (multiselect) {
                if (isSelected(element)) {
                    unselectElement(element);
                } else {
                    selectAnotherElement(element);
                }
            } else {
                selectOneElement(element);
            }
        } else {
            if (! multiselect) {
                unselectAllElements();
            }
        }
    }

    private void selectAnotherElement(GraphicsElement element) {
        selectedElements.add(element);
    }

    private void unselectElement(GraphicsElement element) {
        selectedElements.remove(element);
    }

    private void unselectAllElements() {
        selectedElements.clear();
    }

    public boolean isAdded(GraphicsElement element) {
        return addedElements.contains(element);
    }

    public boolean isSelected(GraphicsElement element) {
        return selectedElements.contains(element);
    }

    public boolean isDragging(GraphicsElement element) {
        return draggedElements.contains(element);
    }
}

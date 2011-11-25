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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.ArrayList;

/**
 * JPanel which draws P/T net components
 *
 * @author dector (dector9@gmail.com)
 * @version 0.1
 */
public class GridPanel extends JPanel {
    /** Grid parameters */
    private Grid grid;

    private List<GraphicsElement> elements;

    private List<GraphicsElement> draggedElements;

    /**
     * Create new instance with determined size
     *
     * @param dimension panels's size
     */
    public GridPanel(Dimension dimension) {
        elements = new ArrayList<GraphicsElement>();
        draggedElements = new ArrayList<GraphicsElement>();

        grid = new Grid();
        setSize(dimension);

        addMouseListener(new GridPanelListener(this));
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

        int elementX;
        int elementY;
        for (GraphicsElement currElement : elements) {
            switch (currElement.getType()) {
                case PLACE: {

                    elementX = currElement.getX() - GraphicsElement.PLACE_SIZE/2;
                    elementY = currElement.getY() - GraphicsElement.PLACE_SIZE/2;

                    g.setColor(Settings.PLACE_FILL_COLOR);
                    g.fillOval(elementX, elementY, GraphicsElement.PLACE_SIZE, GraphicsElement.PLACE_SIZE);
                    g.setColor(Settings.PLACE_BORDER_COLOR);
                    g.drawOval(elementX, elementY, GraphicsElement.PLACE_SIZE, GraphicsElement.PLACE_SIZE);
                }
            }
        }
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

    public void addElement(PTNetElements type, int x, int y) {
        elements.add(new GraphicsElement(type, x, y));
    }
}

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

import javax.swing.*;
import java.awt.*;

/**
 * JPanel which draws P/T net components
 *
 * @author dector (dector9@gmail.com)
 * @version 0.1
 */
public class GridPanel extends JPanel {
    /** Grid parameters */
    Grid grid;

    /**
     * Create new instance with determined size
     *
     * @param dimension panels's size
     */
    public GridPanel(Dimension dimension) {
        grid = new Grid();
        setSize(dimension);
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

        g.setColor(Settings.GRID_COLOR);
        for (int x = grid.getLeftCoord(); x < getWidth(); x += grid.getStepX()) {
            for (int y = grid.getTopCoord(); y < getHeight(); y += grid.getStepY()) {
                g.drawRect(x, y, Settings.GRID_WIDTH, Settings.GRID_WIDTH);
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
}

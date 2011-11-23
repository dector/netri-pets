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

package com.github.pmcompany.petri_net.editor;

import com.github.pmcompany.petri_net.editor.panels.GridPanel;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Editor Controllel
 *
 * @author dector (dector9@gmail.com)
 * @version 0.1
 */
public class EditorController {
    /** Singletone instance */
    private static EditorController instance;

    /** Managed frame */
    private JFrame frame;
    /** List of opened P/T nets */
    private List<GridPanel> gridPanelsList;

    /**
     * Create new instance
     */
    private EditorController() {
        gridPanelsList = new ArrayList<GridPanel>();
    }

    /**
     * Create new instance and connect it with frame
     *
     * @param frame managed frame
     */
    private EditorController(JFrame frame) {
        this();
        this.frame = frame;
    }

    /**
     * Returns single instance
     *
     * @return single new instance
     */
    public static EditorController getInstance() {
        if (instance == null) {
            instance = new EditorController();
        }

        return instance;
    }

    /**
     * Creates new controller instance and connect it with frame
     *
     * @param frame managed frame
     * @return new instance
     */
    public static EditorController newInstance(JFrame frame) {
        instance = new EditorController(frame);

        return instance;
    }

    /**
     * Create new P/T net
     */
    public void createNewFile() {
        GridPanel gp = new GridPanel(frame.getSize());
        gridPanelsList.add(gp);

        //todo: Use Tab Panel, Luke!
        frame.add(gp);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                for (GridPanel panel : gridPanelsList) {
                    panel.setSize(frame.getSize());
                    panel.repaint();
                }
            }
        });
        frame.pack();
    }
}

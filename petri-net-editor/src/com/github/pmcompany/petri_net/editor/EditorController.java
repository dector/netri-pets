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

import com.github.pmcompany.petri_net.editor.frames.MatrixesDialog;
import com.github.pmcompany.petri_net.editor.frames.TreeDialog;
import com.github.pmcompany.petri_net.editor.panels.GridPanel;
import com.github.pmcompany.petri_net.model.PetriNet;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static com.github.pmcompany.petri_net.common.UILabels.*;

/**
 * Editor Controllel
 *
 * @author dector (dector9@gmail.com)
 * @version 0.1
 */
public class EditorController {
    /**
     * Singletone instance
     */
    private static EditorController instance;

    /**
     * Managed tabbed pane
     */
    private JTabbedPane pane;

    private JFrame frame;

    /**
     * List of opened P/T nets
     */
    private List<GridPanel> gridPanelsList;

    private EditorTool tool;

    /**
     * Create new instance
     */
    private EditorController() {
        gridPanelsList = new ArrayList<GridPanel>();
        tool = EditorTool.POINTER;
    }

    /**
     * Create new instance and connect it with tabbed pane
     *
     * @param pane managed tabbed pane
     */
    private EditorController(JTabbedPane pane, JFrame frame) {
        this();

        this.pane = pane;

        this.frame = frame;

        pane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EditorController.getInstance().updateFrameTitle();
            }
        });

        updateFrameTitle();
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
     * Creates new controller instance and connect it with tabbed pane
     *
     * @param pane managed tabbed pane
     * @return new instance
     */
    public static EditorController newInstance(JTabbedPane pane, JFrame frame) {
        instance = new EditorController(pane, frame);

        return instance;
    }

    /**
     * Create new P/T net
     */
    public void createNewFile() {
        GridPanel gp = new GridPanel(pane.getSize());

        gridPanelsList.add(gp);

        pane.addTab(DEFAULT_FILENAME, gp);
        pane.setSelectedIndex(pane.getTabCount() - 1);
        pane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                for (GridPanel panel : gridPanelsList) {
                    panel.setSize(pane.getSize());
                    panel.repaint();
                }
            }
        });

        updateFrameTitle();
    }

    public void switchTool(EditorTool tool) {
        this.tool = tool;
    }

    public EditorTool getSelectedTool() {
        return tool;
    }

    public void updateView() {
        int index = pane.getSelectedIndex();

        pane.getComponentAt(index).repaint();
    }

    public void saveFile() {
        throw new NotImplementedException();
    }

    public void saveFileAs() {
        throw new NotImplementedException();
    }

    public void closeFile() {
        int index = pane.getSelectedIndex();

        if (!gridPanelsList.get(index).isSaved()) {
            int choose =
                    JOptionPane.showOptionDialog(frame, MESSAGE_FILE_WAS_NOT_SAVED_REALLY_CLOSE,
                            MESSAGE_TITLE_FILE_NOT_SAVED, JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, null, null);

            switch (choose) {
                case 0: {
                    pane.removeTabAt(index);
                    updateFrameTitle();
                }
                break;
            }
        }
    }

    public void updateFrameTitle() {
        if (pane.getTabCount() != 0) {
            frame.setTitle(TITLE + TITLE_SEPARATOR
                    + pane.getTitleAt(pane.getSelectedIndex()));
        } else {
            frame.setTitle(TITLE);
        }
    }

    public void showMatrixes() {
        MatrixesDialog matrixesDialog = new MatrixesDialog(new Dimension(500, 400));
        matrixesDialog.setModal(true);
        matrixesDialog.setVisible(true);
    }

    public void simulate() {
        gridPanelsList.get(pane.getSelectedIndex()).simulate();
    }

    public void step() {
        gridPanelsList.get(pane.getSelectedIndex()).step();
    }

    public void printStatistics() {
        gridPanelsList.get(pane.getSelectedIndex()).printStatistics();
    }

    /**
     * Shows attainability tree dialog.
     */
    public void showTree() {
        TreeDialog treeDialog = new TreeDialog(new Dimension(400, 600), gridPanelsList.get(pane.getSelectedIndex()).getPetriNet());
        treeDialog.setModal(true);
        treeDialog.setVisible(true);
    }

    public PetriNet getPertriNext() {
        return gridPanelsList.get(pane.getSelectedIndex()).getPetriNet();        // Be aware!!!
    }
}

package com.github.pmcompany.petri_net.editor.listeners;

import com.github.pmcompany.petri_net.editor.EditorController;
import com.github.pmcompany.petri_net.editor.EditorTool;
import com.github.pmcompany.petri_net.editor.elements.PTNetElements;
import com.github.pmcompany.petri_net.editor.panels.GridPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author dector (dector9@gmail.com)
 */
public class GridPanelListener implements MouseListener, MouseMotionListener {
    private GridPanel gridPanel;
    private static EditorController controller;

    private boolean dragging;

    public GridPanelListener(GridPanel gridPanel) {
        this.gridPanel = gridPanel;

        controller = EditorController.getInstance();
        dragging = false;
    }

    public void mouseClicked(MouseEvent e) {
        switch (controller.getSelectedTool()) {
            case POINTER: {

            } break;
            case PLACE: {
                gridPanel.addElement(PTNetElements.PLACE, e.getX(), e.getY());
            } break;
        }

        controller.updateView();
    }

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseDragged(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) {
        switch (controller.getSelectedTool()) {
            case POINTER: {

            } break;
            case PLACE: {
                if (dragging) {
//                    gridPanel.positionPlace(e.getX(), e.getY());
                }
            } break;
        }

        controller.updateView();
    }
}

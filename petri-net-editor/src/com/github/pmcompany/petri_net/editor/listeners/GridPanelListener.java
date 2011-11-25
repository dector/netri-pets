package com.github.pmcompany.petri_net.editor.listeners;

import com.github.pmcompany.petri_net.editor.EditorController;
import com.github.pmcompany.petri_net.editor.elements.GraphicsElement;
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

    private int x0;
    private int y0;
    private boolean dragging;

    public GridPanelListener(GridPanel gridPanel) {
        this.gridPanel = gridPanel;

        controller = EditorController.getInstance();
        dragging = false;
    }

    public void mouseClicked(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {
        boolean multiselectEnabled = isMultiselectEnabled(e);

        switch (controller.getSelectedTool()) {
            case POINTER: {
                GraphicsElement element = gridPanel.getElementAt(e.getX(), e.getY());

                gridPanel.selectElement(element, multiselectEnabled);       // element == null is NORMAL !
            } break;

            case PLACE: {
                gridPanel.addAndSelectElement(PTNetElements.PLACE, e.getX(), e.getY(), multiselectEnabled);
            } break;

            case TRANSITION: {
                gridPanel.addAndSelectElement(PTNetElements.TRANSITION, e.getX(), e.getY(), multiselectEnabled);
            } break;

            case MOMENTAL_TRANSITION: {
                gridPanel.addAndSelectElement(PTNetElements.MOMENTAL_TRANSITION, e.getX(), e.getY(), multiselectEnabled);
            } break;
        }

        x0 = e.getX();
        y0 = e.getY();

        controller.updateView();
    }

    public void mouseReleased(MouseEvent e) {
                if (dragging) {
                    gridPanel.stopDragElements();
                    dragging = false;
                }
        controller.updateView();
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        GraphicsElement element = gridPanel.getElementAt(e.getX(), e.getY());
        boolean selected = gridPanel.isSelected(element);

        if (selected && ! dragging) {
            gridPanel.startDragElements();
            dragging = true;
        }

        int dx = x - x0;
        x0 = x;
        int dy = y - y0;
        y0 = y;

        gridPanel.dragElements(dx, dy);

        controller.updateView();
    }

    public void mouseMoved(MouseEvent e) {}

    private boolean isMultiselectEnabled(MouseEvent e) {
        return e.isShiftDown();
    }
}

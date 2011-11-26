package com.github.pmcompany.petri_net.editor.listeners;

import com.github.pmcompany.petri_net.editor.EditorController;
import com.github.pmcompany.petri_net.editor.EditorTool;
import com.github.pmcompany.petri_net.editor.elements.GraphicsElement;
import com.github.pmcompany.petri_net.editor.elements.PTNetElements;
import com.github.pmcompany.petri_net.editor.panels.GridPanel;

import java.awt.*;
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

        int x = e.getX();
        int y = e.getY();

        switch (controller.getSelectedTool()) {
            case POINTER: {
                GraphicsElement element = gridPanel.getElementAt(x, y);

                gridPanel.selectElement(element, multiselectEnabled);       // element == null is NORMAL !
            } break;

            case PLACE: {
                gridPanel.addAndSelectElement(PTNetElements.PLACE, x, y, multiselectEnabled);
            } break;

            case TRANSITION: {
                gridPanel.addAndSelectElement(PTNetElements.TRANSITION, x, y, multiselectEnabled);
            } break;

            case MOMENTAL_TRANSITION: {
                gridPanel.addAndSelectElement(PTNetElements.MOMENTAL_TRANSITION, x, y, multiselectEnabled);
            } break;

            case STRAIGHT_CONNECTION: {
                GraphicsElement element = gridPanel.getElementAt(x, y);

                if (element != null) {
                    gridPanel.startConnection(element, false);
                }
            } break;
        }

        x0 = e.getX();
        y0 = e.getY();

        controller.updateView();
    }

    public void mouseReleased(MouseEvent e) {
        EditorTool tool = EditorController.getInstance().getSelectedTool();

        if (tool == EditorTool.STRAIGHT_CONNECTION || tool == EditorTool.BREAKED_CONNECTION) {
            gridPanel.endConnection(gridPanel.getElementAt(e.getX(), e.getY()));
        }
        else if (dragging) {
            gridPanel.stopDragElements();
            dragging = false;
        }

        controller.updateView();
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseDragged(MouseEvent e) {
        EditorController controller = EditorController.getInstance();

        int x = e.getX();
        int y = e.getY();

        boolean changed = false;

        if (controller.getSelectedTool() != EditorTool.STRAIGHT_CONNECTION &&
                controller.getSelectedTool() != EditorTool.BREAKED_CONNECTION) {

            GraphicsElement element = gridPanel.getElementAt(e.getX(), e.getY());
            boolean selected = gridPanel.isSelected(element);

            if (selected && ! dragging) {
                gridPanel.startDragElements();
                dragging = true;
            }

            int[] newCoords = gridPanel.dragElements(x, y, x0, y0);
            if (x0 != newCoords[0]) {
                x0 = newCoords[0];
                changed = true;
            }
            if (y0 != newCoords[1]) {
                y0 = newCoords[1];
                changed = true;
            }
        } else {
            gridPanel.updateConnection(x, y);
            changed = true;
        }

        if (changed) {
            controller.updateView();
        }
    }

    public void mouseMoved(MouseEvent e) {}

    private boolean isMultiselectEnabled(MouseEvent e) {
        return e.isShiftDown();
    }
}

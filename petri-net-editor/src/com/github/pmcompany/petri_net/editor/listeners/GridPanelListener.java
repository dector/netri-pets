package com.github.pmcompany.petri_net.editor.listeners;

import com.github.pmcompany.petri_net.editor.EditorController;
import com.github.pmcompany.petri_net.editor.elements.GraphicsElement;
import com.github.pmcompany.petri_net.editor.elements.PTNetElements;
import com.github.pmcompany.petri_net.editor.panels.GridPanel;

import java.awt.event.InputEvent;
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

    public void mouseClicked(MouseEvent e) {
        switch (controller.getSelectedTool()) {
            case POINTER: {
                GraphicsElement element = gridPanel.getElementAt(e.getX(), e.getY());

                boolean modifierPressed = e.isShiftDown();

                if (element != null) {
                    gridPanel.selectElement(element, modifierPressed);
                } else {
                    gridPanel.unselectAllElements();
                }
            } break;
            case PLACE: {
                gridPanel.addElement(PTNetElements.PLACE, e.getX(), e.getY());
            } break;
            case TRANSITION: {
                gridPanel.addElement(PTNetElements.TRANSITION, e.getX(), e.getY());
            } break;
            case MOMENTAL_TRANSITION: {
                gridPanel.addElement(PTNetElements.MOMENTAL_TRANSITION, e.getX(), e.getY());
            } break;
        }

        controller.updateView();
    }

    public void mousePressed(MouseEvent e) {
//        switch (controller.getSelectedTool()) {
//            case POINTER: {
//                GraphicsElement element = gridPanel.getElementAt(e.getX(), e.getY());
//
//                if (element != null) {
//                    gridPanel.startDragElements();
//                    dragging = true;
//
//                    x0 = e.getX();
//                    y0 = e.getY();
//
//                    //todo: debug
//                    System.out.printf("Start dragging %s%n", element.getType());
//                }
//            } break;
//            case PLACE: {
//                gridPanel.addElement(PTNetElements.PLACE, e.getX(), e.getY());
//            } break;
//            case TRANSITION: {
//                gridPanel.addElement(PTNetElements.TRANSITION, e.getX(), e.getY());
//            } break;
//            case MOMENTAL_TRANSITION: {
//                gridPanel.addElement(PTNetElements.MOMENTAL_TRANSITION, e.getX(), e.getY());
//            } break;
//        }
//
//        controller.updateView();
    }

    public void mouseReleased(MouseEvent e) {
        if (dragging) {
            gridPanel.stopDragElements();
            dragging = false;

            System.out.printf("Stop dragging%n");
        }

        controller.updateView();
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseDragged(MouseEvent e) {
        switch (controller.getSelectedTool()) {
            case POINTER: {
                if (dragging) {
                    int dx = e.getX() - x0;
                    int dy = e.getY() - y0;

                    System.out.printf("Dragging %d, %d%n", dx, dy);

                    gridPanel.dragElements(dx, dy);
                } else {
                    System.out.printf("NOT Dragging%n");

                }
            } break;
        }

        controller.updateView();
    }

    public void mouseMoved(MouseEvent e) {
        switch (controller.getSelectedTool()) {
            case POINTER: {
            } break;
            case PLACE: {
                if (! dragging) {
//                    gridPanel.positionPlace(e.getX(), e.getY());
                }
            } break;
        }

        controller.updateView();
    }
}

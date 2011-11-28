package com.github.pmcompany.petri_net.editor.listeners;

import com.github.pmcompany.petri_net.editor.EditorController;
import com.github.pmcompany.petri_net.editor.EditorTool;
import com.github.pmcompany.petri_net.editor.elements.Connection;
import com.github.pmcompany.petri_net.editor.elements.GraphicsElement;
import com.github.pmcompany.petri_net.editor.elements.PTNetElements;
import com.github.pmcompany.petri_net.editor.panels.GridPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author dector (dector9@gmail.com)
 */
public class GridPanelMouseListener implements MouseListener, MouseMotionListener {
    private GridPanel gridPanel;
    private static EditorController controller;

    private int x0;
    private int y0;
    private boolean dragging;

    public GridPanelMouseListener(GridPanel gridPanel) {
        this.gridPanel = gridPanel;

        controller = EditorController.getInstance();
        dragging = false;
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            switch (controller.getSelectedTool()) {
                case POINTER: {
                    int x = e.getX();
                    int y = e.getY();

                    boolean multiselectEnabled = isShiftPressed(e);

                    Connection connectionSelected;
                    connectionSelected = gridPanel.tryToSelectConnectionAt(x, y, multiselectEnabled);

                    if (connectionSelected == null) {
                        GraphicsElement element = gridPanel.getElementAt(x, y);
                        if (element != null) {
                            gridPanel.editElement(element);       // element == null is NORMAL !
                        }
                    } else {
                        gridPanel.editConnection(connectionSelected);
                    }
                } break;
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        gridPanel.requestFocusInWindow();

        boolean multiselectEnabled = isShiftPressed(e);

        int x = e.getX();
        int y = e.getY();

        switch (controller.getSelectedTool()) {
            case POINTER: {
                Connection connectionSelected;
                connectionSelected = gridPanel.tryToSelectConnectionAt(x, y, multiselectEnabled);

                if (connectionSelected == null) {
                    GraphicsElement element = gridPanel.getElementAt(x, y);
                    gridPanel.selectElement(element, multiselectEnabled);       // element == null is NORMAL !
                } else {
                    gridPanel.selectConnection(connectionSelected, multiselectEnabled);
                }
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
                    gridPanel.startNewConnection(element, true);
                }
            } break;

            case BREAKED_CONNECTION: {
                GraphicsElement element = gridPanel.getElementAt(x, y);

                if (element != null) {
                    gridPanel.addNewMiddlepoint(element);
                } else {
                    gridPanel.addNewMiddlepoint(x, y, isShiftPressed(e));
                }
            }
        }

        x0 = e.getX();
        y0 = e.getY();

        controller.updateView();
    }

    public void mouseReleased(MouseEvent e) {
        EditorTool tool = EditorController.getInstance().getSelectedTool();

        switch (tool) {
            case STRAIGHT_CONNECTION: {
                // fix flat connection end
                gridPanel.endConnection(gridPanel.getElementAt(e.getX(), e.getY()));
            } break;

            case BREAKED_CONNECTION: break;

            default: {
                if (dragging) {
                    gridPanel.stopDragElements();
                    dragging = false;
                }
            }
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

        switch (controller.getSelectedTool()) {
            case STRAIGHT_CONNECTION: {
                gridPanel.updateConnection(x, y, isShiftPressed(e));
                changed = true;
            } break;

            case BREAKED_CONNECTION: break;

            default: {
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
            }
        }

        if (changed) {
            controller.updateView();
        }
    }

    public void mouseMoved(MouseEvent e) {
        switch (controller.getSelectedTool()) {
            case BREAKED_CONNECTION: {
                gridPanel.updateConnection(e.getX(), e.getY(), isShiftPressed(e));
            }
        }

        controller.updateView();
    }



    private boolean isShiftPressed(MouseEvent e) {
        return e.isShiftDown();
    }
}

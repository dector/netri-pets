package com.github.pmcompany.petri_net.editor.listeners;

import com.github.pmcompany.petri_net.editor.EditorController;
import com.github.pmcompany.petri_net.editor.panels.GridPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author dector (dector9@gmail.com)
 */
public class GridPanelKeyListener implements KeyListener {
    private GridPanel gridPanel;
    private static EditorController controller;

    public GridPanelKeyListener(GridPanel gridPanel) {
        this.gridPanel = gridPanel;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DELETE: {
                gridPanel.deleteSelected();
            } break;
        }
    }

    public void keyReleased(KeyEvent e) {}
}
package com.github.pmcompany.petri_net.editor.bars;

import com.github.pmcompany.petri_net.editor.EditorController;
import com.github.pmcompany.petri_net.editor.EditorTool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * @author dector (dector9@gmail.com)
 */
public class ElementsBar extends PalleteToolBar {
    public ElementsBar() {
        JToggleButton button;

        button = new JToggleButton("*<");
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EditorController.getInstance().switchTool(EditorTool.POINTER);
                setActiveButton((JToggleButton)e.getSource());
            }
        });
        add(button);
        setActiveButton(button);

        button = new JToggleButton("P");
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EditorController.getInstance().switchTool(EditorTool.PLACE);
                setActiveButton((JToggleButton)e.getSource());
            }
        });
        add(button);

        button = new JToggleButton("T");
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EditorController.getInstance().switchTool(EditorTool.TRANSITION);
                setActiveButton((JToggleButton)e.getSource());
            }
        });
        add(button);

        button = new JToggleButton("t=0");
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EditorController.getInstance().switchTool(EditorTool.MOMENTAL_TRANSITION);
                setActiveButton((JToggleButton)e.getSource());
            }
        });
        add(button);

        button = new JToggleButton("-->");
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EditorController.getInstance().switchTool(EditorTool.CONNECTION);
                setActiveButton((JToggleButton)e.getSource());
            }
        });
        add(button);

        button = new JToggleButton("|_>");
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EditorController.getInstance().switchTool(EditorTool.BREAKED_CONNECTION);
                setActiveButton((JToggleButton)e.getSource());
            }
        });
        add(button);
    }
}
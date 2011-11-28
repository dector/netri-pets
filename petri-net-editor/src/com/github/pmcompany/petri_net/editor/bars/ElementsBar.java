package com.github.pmcompany.petri_net.editor.bars;

import com.github.pmcompany.petri_net.editor.EditorController;
import com.github.pmcompany.petri_net.editor.EditorTool;
import static com.github.pmcompany.petri_net.editor.Settings.*;
import com.sun.deploy.ui.ImageLoader;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author dector (dector9@gmail.com)
 */
public class ElementsBar extends PalleteToolBar {
    public ElementsBar() {
        JToggleButton button;

        String imagesDir = DIR_DATA + DIR_IMAGES;

        button = new JToggleButton(new ImageIcon(imagesDir + IMAGE_POINTER));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EditorController.getInstance().switchTool(EditorTool.POINTER);
                setActiveButton((JToggleButton)e.getSource());
            }
        });
        add(button);
        setActiveButton(button);

        button = new JToggleButton(new ImageIcon(imagesDir + IMAGE_PLACE));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EditorController.getInstance().switchTool(EditorTool.PLACE);
                setActiveButton((JToggleButton)e.getSource());
            }
        });
        add(button);

        button = new JToggleButton(new ImageIcon(imagesDir + IMAGE_TRANSITION));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EditorController.getInstance().switchTool(EditorTool.TRANSITION);
                setActiveButton((JToggleButton)e.getSource());
            }
        });
        add(button);

        button = new JToggleButton(new ImageIcon(imagesDir + IMAGE_MTRANSITION));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EditorController.getInstance().switchTool(EditorTool.MOMENTAL_TRANSITION);
                setActiveButton((JToggleButton)e.getSource());
            }
        });
        add(button);

        button = new JToggleButton(new ImageIcon(imagesDir + IMAGE_STRAIGHT_CONNECTION));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EditorController.getInstance().switchTool(EditorTool.STRAIGHT_CONNECTION);
                setActiveButton((JToggleButton)e.getSource());
            }
        });
        add(button);

        button = new JToggleButton(new ImageIcon(imagesDir + IMAGE_BREAKED_CONNECTION));
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
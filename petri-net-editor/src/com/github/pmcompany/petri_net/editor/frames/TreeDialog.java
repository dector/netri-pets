package com.github.pmcompany.petri_net.editor.frames;

import com.github.pmcompany.petri_net.model.PetriNet;
import com.github.pmcompany.petri_net.model.util.AttainabilityTree;

import javax.swing.*;
import java.awt.*;

/**
 * User: vitaliy
 * Date: 12.12.11
 * Time: 17:12
 */
public class TreeDialog extends JDialog {
    private JPanel mainPanel = null;

    public TreeDialog(Dimension size, PetriNet net) {
        setSize(size);
        AttainabilityTree tree = new AttainabilityTree();
        tree.build(net);
        mainPanel = new TreeDrawingPanel(tree.getRootNode());
        this.add(mainPanel);
        System.out.println(tree);
    }
}

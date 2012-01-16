package com.github.pmcompany.petri_net.editor.frames;

import com.github.pmcompany.petri_net.model.PetriNet;
import com.github.pmcompany.petri_net.model.util.AttainabilityTree;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * User: vitaliy
 * Date: 12.12.11
 * Time: 17:12
 */
public class MarkDialog extends JDialog {
    private JMenuBar mainMenu = null;
    private JMenu fileMenu = null;
    private JMenuItem exportItem = null;
    private MDrawingPanel drawingPanel = null;
    private AttainabilityTree tree = new AttainabilityTree();

    private JMenuBar getMainMenu() {
        if (mainMenu == null) {
            mainMenu = new JMenuBar();
            mainMenu.add(getFileMenu());
        }
        return mainMenu;
    }

    private JMenu getFileMenu() {
        if (fileMenu == null) {
            fileMenu = new JMenu("File");
            fileMenu.add(getExportItem());
        }
        return fileMenu;
    }

    private JMenuItem getExportItem() {
        if (exportItem == null) {
            exportItem = new JMenuItem("Export as PNG...");
            exportItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    exportJpeg();
                }
            });
        }
        return exportItem;
    }

    private MDrawingPanel getDrawingPanel() {
        if (drawingPanel == null) {
            drawingPanel = new MDrawingPanel(tree.getRootNode());
        }
        return drawingPanel;
    }

    public void exportJpeg() {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            BufferedImage bi = new BufferedImage(this.getSize().width, this.getSize().height, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            drawingPanel.paint(g);  //this == JComponent
            g.dispose();
            try {
                ImageIO.write(bi, "png", fc.getSelectedFile());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public MarkDialog(Dimension size, PetriNet net) {
        setSize(size);
        tree.build(net);
        this.add(getDrawingPanel());
        this.setJMenuBar(getMainMenu());
        System.out.println(tree);
    }
}

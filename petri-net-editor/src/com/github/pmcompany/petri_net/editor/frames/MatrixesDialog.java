package com.github.pmcompany.petri_net.editor.frames;

import com.github.pmcompany.petri_net.editor.EditorController;
import com.github.pmcompany.petri_net.model.PetriNet;

import javax.sound.midi.ControllerEventListener;
import javax.swing.*;
import java.awt.*;

/**
 * @author dector (dector9@gmail.com)
 */
public class MatrixesDialog extends JDialog {
    private JTextArea textArea;

    public MatrixesDialog(Dimension size) {
        setSize(size);

        textArea = new JTextArea();
        add(textArea);

        EditorController controller = EditorController.getInstance();

        PetriNet net = controller.getPertriNext();

        textArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));

        textArea.append("DI:\n");
        textArea.append(Matrix.print(net.getTransitionsTitlesVector(), net.getPlacesTitlesVector(), net.getDI()));
        textArea.append("\n");

        textArea.append("============================\n");

        textArea.append("DQ:\n");
        textArea.append(Matrix.print(net.getTransitionsTitlesVector(), net.getPlacesTitlesVector(), net.getDQ()));
        textArea.append("\n");

        textArea.append("============================\n");

        textArea.append("DR:\n");
        textArea.append(Matrix.print(net.getTransitionsTitlesVector(), net.getPlacesTitlesVector(), net.getDR()));
        textArea.append("\n");

        textArea.append("============================\n");

        textArea.append("Places:\n");
        textArea.append(Vector.print(net.getPlacesTitlesVector()));
        textArea.append(Vector.print(net.getPlacesVector()));
        textArea.append("\n");

        textArea.append("============================\n");

        textArea.append("Transitions:\n");
        textArea.append(Vector.print(net.getTransitionsTitlesVector()));
        textArea.append(Vector.print(net.getTransitionsVector()));
        textArea.append("\n");
    }
}

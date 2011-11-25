package com.github.pmcompany.petri_net.editor.bars;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author dector (dector9@gmail.com)
 */
public class PalleteToolBar extends JToolBar {
    private List<JToggleButton> buttons;
    private int activeButtonIndex;

    public PalleteToolBar() {
        buttons = new ArrayList<JToggleButton>();
    }

    @Override
    public Component add(Component comp) {
        if (comp instanceof JToggleButton) {
            buttons.add((JToggleButton)comp);
        }

        return super.add(comp);
    }

    public int getActiveButtonIndex() {
        return activeButtonIndex;
    }

    private void setActiveButtonIndex(int activeButtonIndex) {
        this.activeButtonIndex = activeButtonIndex;
    }

    public void setActiveButton(JToggleButton newActiveButton) {
        JToggleButton oldActiveButton = buttons.get(activeButtonIndex);
        oldActiveButton.setSelected(false);
        newActiveButton.setSelected(true);
        activeButtonIndex = buttons.indexOf(newActiveButton);
    }
}

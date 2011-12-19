/**
 * Copyright (c) 2011, PM Company (dector, vitalyp) All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  - Neither the name of the nor the names of its
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.pmcompany.petri_net.editor.menu;

import com.github.pmcompany.petri_net.editor.EditorController;

import javax.swing.*;
import java.awt.event.*;

import static com.github.pmcompany.petri_net.common.UILabels.*;

/**
 * Main editor's menu
 *
 * @author dector (dector9@gmail.com)
 * @version 0.0.1
 */
public class MainMenuBar extends JMenuBar {
    /**
     * Create new instance
     */
    public MainMenuBar() {
        JMenu menu;
        JMenuItem item;

        menu = new JMenu(MENU_FILE);
        item = new JMenuItem(MENU_CREATE);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.VK_CONTROL));
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditorController.getInstance().createNewFile();
            }
        });
        menu.add(item);

        item = new JMenuItem(MENU_SAVE);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.VK_CONTROL));
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditorController.getInstance().saveFile();
            }
        });
        menu.add(item);

        item = new JMenuItem(MENU_SAVEAS);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditorController.getInstance().saveFileAs();
            }
        });
        menu.add(item);

        item = new JMenuItem(MENU_CLOSE);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditorController.getInstance().closeFile();
            }
        });
        menu.add(item);

        menu.addSeparator();

        item = new JMenuItem(MENU_QUIT);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.VK_CONTROL));
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(item);

        add(menu);

        menu = new JMenu(MENU_SIMULATION);

        item = new JMenuItem(MENU_MATRIXES);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.VK_CONTROL));
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditorController.getInstance().showMatrixes();
            }
        });
        menu.add(item);

        item = new JMenuItem(MENU_TREE);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditorController.getInstance().showTree();
            }
        });
        menu.add(item);
        
        add(menu);
    }
}

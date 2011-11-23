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

package com.github.pmcompany.petri_net.editor.frames;

import com.github.pmcompany.petri_net.editor.EditorController;
import com.github.pmcompany.petri_net.editor.menu.MainMenuBar;
import com.github.pmcompany.petri_net.editor.panels.GridPanel;

import javax.swing.*;
import static com.github.pmcompany.petri_net.common.UILabels.*;

/**
 * Main editor frame
 *
 * @author dector (dector9@gmail.com)
 * @version 0.1
 */
public class EditorFrame extends JFrame {
    /**
     * Controller for editor
     */
    EditorController controller;

    /**
     * Create new instance
     */
    public EditorFrame() {
        controller = EditorController.newInstance(this);

        buildGUI();
    }

    /**
     * Create GUI components and place them to frame
     */
    private void buildGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //todo: fixme
        setSize(640, 480);
        setTitle(PROJECT_NAME);

        MainMenuBar menuBar = new MainMenuBar();
        setJMenuBar(menuBar);
    }


    /**
     * Set frame visible
     */
    public void showGUI() {
        setVisible(true);
    }
}

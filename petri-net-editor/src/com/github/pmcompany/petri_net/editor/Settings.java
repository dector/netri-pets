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

package com.github.pmcompany.petri_net.editor;

import java.awt.*;

/**
 * Editor view settings
 *
 * @author dector (dector9@gmail.com)
 * @version 0.1
 */
public class Settings {
    /** Grid color */
    public static Color GRID_COLOR = Color.GRAY;
    /** Grid line width */
    public static int GRID_WIDTH = 1;

    public static float ELEMENT_BORDER_WIDTH = 2.5f;

    public static float CONNECTION_WIDTH = 2.5f;

    public static int ELEMENT_TITLE_PADDING = 3;
    public static int CONNECTION_TITLE_SIZE = 15;
    public static int ELEMENT_TITLE_SIZE = 22;
    public static Color ELEMENT_TITLE_COLOR = Color.BLACK;

//    public static Color SELECTION_FILL_COLOR = new Color(51, 204, 255, 220);
//    public static Color SELECTION_FILL_COLOR = new Color(0, 255, 0, 70);
    public static Color SELECTION_FILL_COLOR = Color.ORANGE;
//    public static Color ELEMENT_DRAGGING_BORDER_COLOR = Color.ORANGE;
    public static Color ELEMENT_DRAGGING_BORDER_COLOR = Color.ORANGE;

    public static Color PLACE_FILL_COLOR = Color.WHITE;
    public static Color PLACE_BORDER_COLOR = Color.BLACK;

    public static Color TRANSITION_FILL_COLOR = Color.WHITE;
    public static Color TRANSITION_BORDER_COLOR = Color.BLACK;

    public static Color MOMENTAL_TRANSITION_FILL_COLOR = Color.BLACK;
    public static Color MOMENTAL_TRANSITION_BORDER_COLOR = Color.BLACK;

    public static Color SELECTED_CONNECTION_COLOR = Color.ORANGE;
    public static Color PLACE_TRANSITION_CONNECTION_COLOR = Color.RED;
    public static Color TRANSITION_PLACE_CONNECTION_COLOR = Color.BLUE;
}

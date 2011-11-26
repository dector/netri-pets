package com.github.pmcompany.petri_net.editor.elements;

import java.util.List;

/**
 * @author dector (dector9@gmail.com)
 */
public class StraightConnection extends Connection {
    public StraightConnection(GraphicsElement from, GraphicsElement to) {
        super(from, to);
    }

    public StraightConnection(GraphicsElement from) {
        super(from);
    }

}

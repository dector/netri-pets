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

package com.github.pmcompany.petri_net.elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract entity, that describes graph node (P/T net's Place/Transition)
 * which has input and output Arcs
 *
 * @author dector (dector9@gmail.com)
 * @version 1.0
 *
 * @see Place
 * @see Transition
 * @see Arc
 */
public abstract class Node {
    /** List of input arcs */
    private List<Arc> inputArcs;
    /** List of output arcs */
    private List<Arc> outputArcs;

    /** Name value */
    private String name;

    /**
     * Create new instance with selected name
     *
     * @param name node name
     */
    Node(String name) {
        this.name = name;

        inputArcs = new ArrayList<Arc>();
        outputArcs = new ArrayList<Arc>();
    }

    /**
     * Returns node name
     *
     * @return node name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets node name
     *
     * @param name node name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Attach Arc to Node's input
     *
     * @param arc   Arc to attach
     * @return      <b>true</b> if Arc was attaches, else <b>false</b>
     */
    public boolean addInputArc(Arc arc) {
        return inputArcs.add(arc);
    }

    /**
     * Return input Arc by specified index
     *
     * @param index input Arc index
     * @return      input Arc
     */
    public Arc getInputArc(int index) {
        return inputArcs.get(index);
    }

    /**
     * Returns Iterator to iterate all input Arcs
     *
     * @return  input Arcs Iterator
     */
    public Iterator<Arc> getInputArcsIterator() {
        return inputArcs.iterator();
    }

    /**
     * Attach Arc to Node's output
     *
     * @param arc   Arc to attach
     * @return      <b>true</b> if Arc was attaches, else <b>false</b>
     */
    public boolean addOutputArc(Arc arc) {
        return outputArcs.add(arc);
    }

    /**
     * Return output Arc by specified index
     *
     * @param index output Arc index
     * @return      output Arc
     */
    public Arc getOutputArc(int index) {
        return outputArcs.get(index);
    }

    /**
     * Returns Iterator to iterate all output Arcs
     *
     * @return  output Arcs Iterator
     */
    public Iterator<Arc> getOutputArcsIterator() {
        return outputArcs.iterator();
    }

    /**
     * Returns number of input Arcs, attached to this Node
     *
     * @return number of input Arcs
     */
    public int countInputArcs() {
        return inputArcs.size();
    }

    /**
     * Returns number of output Arcs, attached to this Node
     *
     * @return number of output Arcs
     */
    public int countOutputArcs() {
        return outputArcs.size();
    }
}

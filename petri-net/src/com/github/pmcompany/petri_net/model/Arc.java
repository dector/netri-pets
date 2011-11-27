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

package com.github.pmcompany.petri_net.model;

/**
 * Describes graph edge between P/T net's Places and Transitions<br /><br />
 *
 * <pre>
 * +--------+                      +-------+
 * | Output |--------------------->| Input |
 * |  Node  |                      |  Node |
 * +--------+                      +-------+
 * </pre>
 *
 * @author dector (dector9@gmail.com)
 * @version 1.0
 *
 * @see Node
 * @see Place
 * @see Transition
 */
public class Arc<NodeFrom extends Node, NodeTo extends Node> {
    private static final String TEXT_REPRESENTATION = "-->";

    /** Left attached Node (comes from) */
    private NodeFrom outputNode;
    /** Right attached Node (comes to) */
    private NodeTo inputNode;

    private int count;

    /**
     * Create new instance with setted input and output Nodes
     *
     * @param outputNode    Node from which Arc comes from
     * @param inputNode     Node to which Arc comes to
     */
    private Arc(NodeFrom outputNode, NodeTo inputNode) {
        outputNode.addOutputArc(this);
        inputNode.addInputArc(this);

        this.outputNode = outputNode;
        this.inputNode = inputNode;

        count = 1;
    }

    public int getCount() {
        return count;
    }

    void setCount(int count) {
        this.count = count;
    }

    void incrementCount() {
        count++;
    }

    /**
     * Returns Node to which Arc comes to
     *
     * @return  input Node (to which comes to)
     */
    public NodeTo getInputNode() {
        return inputNode;
    }

    /**
     * Sets Node to which Arc comes to
     *
     * @param inputNode new input Node (to which comes to)
     */
    void setInputNode(NodeTo inputNode) {
        this.inputNode = inputNode;
    }

    /**
     * Returns Node from which Arc comes from
     *
     * @return  output Node (from which comes from)
     */
    public NodeFrom getOutputNode() {   /**
     * Sets Node, to which Arc comes to
     *
     * @param inputNode new input Node (to which comes to)
     */
        return outputNode;
    }

    /**
     * Sets Node from which Arc comes from
     *
     * @param outputNode new output Node (from which comes from)
     */
    void setOutputNode(NodeFrom outputNode) {
        this.outputNode = outputNode;
    }

    /**
     * Create new Arc instance, which connects Place with Transition
     *
     * @param from  Place from which Arc comes from
     * @param to    Transition to which Arc comes to
     * @return      new Arc instance
     */
    static Arc<Place, Transition> newInstance(Place from, Transition to) {
        return new Arc<Place, Transition>(from, to);
    }

//    public Arc<Place, Transition> newPlaceToTransition() {
//        // Yep, it looks funny
//        return newInstance((Place)null, (Transition)null);
//    }

    /**
     * Create new Arc instance, which connects Transition with Place
     *
     * @param from  Transition to which Arc comes to
     * @param to    Place from which Arc comes from
     * @return      new Arc instance
     */
    static Arc<Transition, Place> newInstance(Transition from, Place to) {
        return new Arc<Transition, Place>(from, to);
    }

//    public Arc<Transition, Place> newTransitionToPlace() {
//        // Yep, it looks funny too
//        return newInstance((Transition)null, (Place)null);
//    }

    public boolean isInput() {
        return inputNode.isTransition();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(outputNode).append(TEXT_REPRESENTATION).append(inputNode);

        return sb.toString();
    }

    public void clearConnection() {
        inputNode = null;
        outputNode = null;
    }
}

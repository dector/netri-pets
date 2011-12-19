package com.github.pmcompany.petri_net.model.util;

import com.github.pmcompany.petri_net.model.PetriNet;
import com.github.pmcompany.petri_net.model.Transition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * User: vitaliy
 * Date: 19.12.11
 * Time: 12:16
 */
public class AttainabilityTree {
    private AttainabilityTreeNode root;
    private LinkedList<AttainabilityTreeNode> queue;
    private ArrayList<AttainabilityTreeNode> nodes;

    public void build(PetriNet net) {
        nodes = new ArrayList<AttainabilityTreeNode>();
        queue = new LinkedList<AttainabilityTreeNode>();
        root = new AttainabilityTreeNode(net.getState());
        root.setType(AttainabilityTreeNodeType.BOUNDARY);
        queue.add(root);

        PetriNetState initialState = net.getState();

        while (!queue.isEmpty()) {
            AttainabilityTreeNode current = queue.removeFirst();
            //Check the node for doublicating
            for (AttainabilityTreeNode node : nodes) {
                if (current.duplicates(node)) {
                    current.setType(AttainabilityTreeNodeType.DUPLICATE);
                    break;
                }
                if (current.getState().covers(node.getState())) {
                    current.setType(AttainabilityTreeNodeType.INFINITE);
                    break;
                }
            }


            if (current.getType() != AttainabilityTreeNodeType.DUPLICATE
                    && current.getType() != AttainabilityTreeNodeType.INFINITE) {
                //Let's search derriviative states
                net.setState(current.getState());
                Collection<Transition> enabledTransitions = net.getEnabledTransitions();
                if (!enabledTransitions.isEmpty()) {
                    for (Transition transition : enabledTransitions) {
                        net.setState(current.getState());
                        transition.Execute();
                        //Create new tree node for this state
                        AttainabilityTreeNode nv = new AttainabilityTreeNode(net.getState());
                        nv.setType(AttainabilityTreeNodeType.BOUNDARY);
                        current.addChild(nv);
                        queue.add(nv);
                    }
                } else {
                    current.setType(AttainabilityTreeNodeType.TERMINAL);
                }
            }

            nodes.add(current);
        }

        net.setState(initialState);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (AttainabilityTreeNode node : nodes) {
            sb.append(node.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}

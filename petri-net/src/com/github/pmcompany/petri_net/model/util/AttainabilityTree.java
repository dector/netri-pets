package com.github.pmcompany.petri_net.model.util;

import com.github.pmcompany.petri_net.model.PetriNet;
import com.github.pmcompany.petri_net.model.Transition;

import java.util.*;

/**
 * User: vitaliy
 * Date: 19.12.11
 * Time: 12:16
 */
public class AttainabilityTree {
    private AttainabilityTreeNode root;
    private ArrayList<AttainabilityTreeNode> nodes;

    public void build(PetriNet net) {
        int lastId = 0;

        nodes = new ArrayList<AttainabilityTreeNode>();
        LinkedList<AttainabilityTreeNode> queue = new LinkedList<AttainabilityTreeNode>();
        root = new AttainabilityTreeNode(net.getState());
        root.setType(AttainabilityTreeNodeType.BOUNDARY);
        queue.add(root);

        PetriNetState initialState = net.getState();

        while (!queue.isEmpty()) {
            AttainabilityTreeNode current = queue.removeFirst();
            net.setState(current.getState());
            if (!current.getType().equals(AttainabilityTreeNodeType.TEMPORARY)) {
                //Check the node for doublicating
                for (AttainabilityTreeNode node : nodes) {
                    if (current.getState().covers(node.getState())) {
                        boolean[] coverageMap = current.getState().getCoverageMap(node.getState());
                        for (int i = 0; i < coverageMap.length; i++) {
                            if (coverageMap[i]) {
                                current.setInfinite(i);
                            }
                        }
                        current.setType(AttainabilityTreeNodeType.INFINITE);
                    }
                    if (current.duplicates(node)) {
                        current.setType(AttainabilityTreeNodeType.DUPLICATE);
                        current.setId(node.getId());
                        break;
                    }
                }

                if (current.getType() != AttainabilityTreeNodeType.DUPLICATE
                    /* && current.getType() != AttainabilityTreeNodeType.INFINITE*/) {
                    //Let's search derriviative states
                    Collection<Transition> enabledTimeTransition = net.getEnabledTimeTransitions();
                    if (!enabledTimeTransition.isEmpty()) {
                        for (Transition transition : enabledTimeTransition) {
                            net.setState(current.getState());
                            transition.execute();
                            //Create new tree node for this state
                            AttainabilityTreeNode nv = new AttainabilityTreeNode(net.getState());
                            if (net.isTemporary()) {
                                nv.setType(AttainabilityTreeNodeType.TEMPORARY);
                            } else {
                                nv.setType(AttainabilityTreeNodeType.BOUNDARY);
                                nv.setId(++lastId);
                            }
                            nv.addTransition(transition);  // Add transition into path
                            current.setType(AttainabilityTreeNodeType.INTERNAL);
                            current.addChild(nv);
                            queue.add(nv);
                        }
                    } else {
                        current.setType(AttainabilityTreeNodeType.TERMINAL);
                    }
                }

                if (current.getType() != AttainabilityTreeNodeType.TEMPORARY) {
                    nodes.add(current);
                }
            } else { //If current state is temporary
                ArrayList<Transition> immTrans = net.getEnabledImmediateTransitions();
                for (Transition transition : immTrans) {
                    net.setState(current.getState());
                    transition.execute();
                    //Create new tree node for this state
                    AttainabilityTreeNode nv = new AttainabilityTreeNode(net.getState());
                    if (net.isTemporary()) {
                        nv.setType(AttainabilityTreeNodeType.TEMPORARY);
                    } else {
                        nv.setType(AttainabilityTreeNodeType.BOUNDARY);
                        nv.setId(++lastId);
                    }
                    current.setType(AttainabilityTreeNodeType.INTERNAL);
                    current.getParent().removeChild(current);
                    nv.addTransition(transition);  // Add transition into path
                    current.getParent().addChild(nv);
                    queue.add(nv);
                }
            }
        }

        net.setState(initialState);
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();

//        int level = 1;
//        int branch = 1;
//        boolean printBranch = true;

//        List<AttainabilityTreeNode> levelNodes = new LinkedList<AttainabilityTreeNode>();
//        List<AttainabilityTreeNode> nextLevelNodes = new LinkedList<AttainabilityTreeNode>();
//        levelNodes.add(root);
//
//        Iterator<AttainabilityTreeNode> iter;
//        iter = levelNodes.iterator();
//
//        AttainabilityTreeNode node;
//        while (iter.hasNext()) {
//            node = iter.next();
//
//            if (printBranch) {
//                sb.append(branch++);
//                sb.append("\t");
//            }
//
//            for (AttainabilityTreeNode child : node.getChildren()) {
//                new
//            }
//        }

        for (AttainabilityTreeNode node : nodes) {
            sb.append(node.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public AttainabilityTreeNode getRootNode() {
        return root;
    }
}

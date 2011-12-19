package com.github.pmcompany.petri_net.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * User: vitaliy
 * Date: 19.12.11
 * Time: 12:21
 */
public class AttainabilityTreeNode {
    private PetriNetState state;
    private AttainabilityTreeNodeType type;
    private AttainabilityTreeNode parent;
    private Collection<AttainabilityTreeNode> children;
    private boolean[] infinities;

    public AttainabilityTreeNode(PetriNetState state) {
        setState(state);
        children = new ArrayList<AttainabilityTreeNode>();
        infinities = new boolean[state.getPlacesCount()];
        Arrays.fill(infinities, false);
    }

    public AttainabilityTreeNode getParent() {
        return parent;
    }

    public void addChild(AttainabilityTreeNode node) {
        children.add(node);
        node.setParent(this);
    }

    public void setParent(AttainabilityTreeNode parent) {
        this.parent = parent;
    }

    public PetriNetState getState() {
        return state;
    }

    public void setState(PetriNetState state) {
        this.state = state;
    }

    public AttainabilityTreeNodeType getType() {
        return type;
    }

    public void setType(AttainabilityTreeNodeType type) {
        this.type = type;
    }

    public void setInfinite(int i) {
        infinities[i] = true;
    }

    public boolean isInfinite(int i) {
        return infinities[i];
    }


    public boolean duplicates(AttainabilityTreeNode o) {
        for (int i = 0; i < this.state.getPlacesCount(); i++) {
            if (!(infinities[i] || o.infinities[i]) && state.getTokens(i) != o.state.getTokens(i)) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.hashCode());
        sb.append("\t");
        sb.append(state.toString());
        sb.append("\t");
        sb.append(Arrays.toString(infinities));
        sb.append("\t");
        sb.append((parent != null) ? parent.hashCode() : "root");
        sb.append("\t");
        sb.append(type.toString());
        return sb.toString();
    }

    public Collection<AttainabilityTreeNode> getChildren() {
        return children;
    }

    public void removeChild(AttainabilityTreeNode node) {
        children.remove(node);
    }
}

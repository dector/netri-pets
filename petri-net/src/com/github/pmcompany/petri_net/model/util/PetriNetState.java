package com.github.pmcompany.petri_net.model.util;

/**
 * User: vitaliy
 * Date: 16.12.11
 * Time: 19:12
 */

import java.util.Arrays;

/**
 * Represents the state of the Petri net.
 */
public class PetriNetState implements Comparable<PetriNetState> {
    private int[] state;
    private int n;

    /**
     * Creates new state with given state :)
     *
     * @param state array, which contains number of tokens in each place.
     */
    public PetriNetState(int[] state) {
        this.state = state;
        this.n = state.length;
    }

    public PetriNetState(int n) {
        this.state = new int[n];
        this.n = n;
    }

    /**
     * Set the number of tokens in specific place
     *
     * @param i the number of place to set the tokens in.
     * @param t the number of tokens to set
     */
    public void setTokens(int i, int t) {
        state[i] = t;
    }

    /**
     * Get the number of tokens in a specified place
     *
     * @param i the number of position
     * @return
     */
    public int getTokens(int i) {
        return state[i];
    }

    public int compareTo(PetriNetState o) {
        for (int i = 0; i < state.length; i++) {
            if (this.state[i] != o.state[i]) {
                return this.state[i] - o.state[i];
            }
        }
        return 0;
    }

    public boolean covers(PetriNetState s) {
        for (int i = 0; i < n; i++) {
            if (this.state[i] < s.state[i]) {
                return false;
            }
        }
        return true;
    }

    public int getPlacesCount() {
        return n;
    }

    public PetriNetState clone() {
        PetriNetState v = null;
        try {
            v = (PetriNetState) super.clone();
            v.state = Arrays.copyOf(this.state, this.state.length);
            v.n = this.n;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
        return v;
    }

    public String toString() {
        return Arrays.toString(state);
    }

    public boolean[] getCoverageMap(PetriNetState state) {
        boolean[] res = new boolean[this.state.length];
        Arrays.fill(res, false);
        for (int i = 0; i < n; i++) {
            if (this.state[i] > state.state[i]) {
                res[i] = true;
            }
        }
        return res;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }
}

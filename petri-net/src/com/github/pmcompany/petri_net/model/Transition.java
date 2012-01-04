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

import java.util.ArrayList;
import java.util.Collection;

/**
 * Entity that describes Time transition in P/T net
 *
 * @author dector (dector9@gmail.com)
 * @version 1.0
 * @see Node
 * @see Place
 * @see Arc
 */
public class Transition extends Node {
    public static final String TRANSITION_NAME = "T";

    private static final double DEFAULT_TIME = 0d;

    /**
     * Transition time
     */
    private double time;

    /**
     * Create new instance with specified parameters
     *
     * @param id   Transition id
     * @param time Transition time
     */
    Transition(int id, double time) {
        super(id);

        this.time = time;
    }

    /**
     * Create new instance with specified id.<br />
     * Transition will be momentary by default
     *
     * @param id Transition id
     */
    Transition(int id) {
        this(id, DEFAULT_TIME);
    }

    /**
     * Return Transition time
     *
     * @return Transition time
     */
    public double getTime() {
        return time;
    }

    /**
     * Sets new Transition time
     *
     * @param time new Transition time
     */
    void setTime(double time) {
        this.time = time;
    }

    /**
     * Returns <b>true</b> if Transition is momentary
     * (has zero-time transition), <b>false</b> if not.
     *
     * @return <b>true</b> if Transition is momentary, else <b>false</b>
     */
    public boolean isMomentary() {
        return (time == 0);
    }

    @Override
    public boolean isTransition() {
        return true;
    }

    @Override
    public String toString() {
        return TRANSITION_NAME + getId();
    }

    public Collection<Place> getInputPlaces() {
        ArrayList<Place> res = new ArrayList<Place>();
        for (Arc connection : getInputArcs()) {
            //This is a really bad thing
            int n = connection.getCount();
            for (int i = 0; i < n; i++) res.add((Place) connection.getOutputNode());
        }
        return res;
    }

    public Collection<Place> getOutputPlaces() {
        ArrayList<Place> res = new ArrayList<Place>();
        for (Arc connection : getOutputArcs()) {
            //This is a really bad thing
            int n = connection.getCount();
            for (int i = 0; i < n; i++) res.add((Place) connection.getInputNode());
        }
        return res;
    }

    public boolean isEnabled() {
        for (Arc connection : getInputArcs()) {
            //This is a really bad thing
            int n = connection.getCount();
            if (((Place) connection.getOutputNode()).countTokens() < n) {
                return false;
            }
        }
        return true;
    }

    public void execute() {
        if (isEnabled()) {
            Collection<Place> places = getInputPlaces();
            for (Place p : places) {
                p.pickTokens(1);
            }
            places = getOutputPlaces();
            for (Place p : places) {
                p.putTokens(1);
            }
        }
    }
}

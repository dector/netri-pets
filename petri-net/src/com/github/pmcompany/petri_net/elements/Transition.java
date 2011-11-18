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

/**
 * Entity that describes Time transition in P/T net
 *
 * @author dector (dector9@gmail.com)
 * @version 1.0
 *
 * @see Node
 * @see Place
 * @see Arc
 */
public class Transition extends Node {
    private static final String DEFAULT_NAME = "";
    private static final long DEFAULT_TIME = 0;

    /** Transition time */
    private long time;

    /**
     * Create new instance with specified parameters
     *
     * @param name  Transition name
     * @param time  Transition time
     */
    public Transition(String name, long time) {
        super(name);

        this.time = time;
    }

    /**
     * Create new instance with specified name.<br />
     * Transition will be momentary by default
     *
     * @param name  Transition name
     */
    public Transition(String name) {
        this(name, DEFAULT_TIME);
    }

    /**
     * Create new instance with specified Transition time
     *
     * @param time  Transition time
     */
    public Transition(long time) {
        this(DEFAULT_NAME, time);
    }

    /**
     * Create new instance with default parameters.<br />
     * Transition will be momentary by default
     */
    public Transition() {
        this(DEFAULT_NAME, DEFAULT_TIME);
    }

    /**
     * Return Transition time
     *
     * @return  Transition time
     */
    public long getTime() {
        return time;
    }

    /**
     * Sets new Transition time
     *
     * @param time  new Transition time
     */
    public void setTime(long time) {
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
}

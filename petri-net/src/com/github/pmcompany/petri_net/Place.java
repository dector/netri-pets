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

package com.github.pmcompany.petri_net;

/**
 * Entity that describes Place in P/T net
 *
 * @author dector (dector9@gmail.com)
 * @version 1.0
 *
 * @see Node
 * @see Transition
 * @see Arc
 */
public class Place extends Node {
    public static final String PLACE_NAME = "P";

    private static final int DEFAULT_TOKENS_NUMBER = 0;

    /** Number of tokens in current Place */
    private int tokens;

    /**
     * Create new instance with specified parameters
     *
     * @param id        Place's id
     * @param tokens    number of tokens inside
     */
    Place(int id, int tokens) {
        super(id);

        this.tokens = tokens;
    }

    /**
     * Create new instance with specified number.<br />
     * This Place <b>has no tokes</b>!
     *
     * @param id        Place's id
     */
    Place(int id) {
        this(id, DEFAULT_TOKENS_NUMBER);
    }

    /**
     * Returns number of tokens in Place
     *
     * @return number of tokens
     */
    public int countTokens() {
        return tokens;
    }

    /**
     * Pick-up few tokens from this Place.<br />
     * Number of tokens to pick-up must be positive number
     * and less or equals, than number of tokens which remains
     * on Place
     *
     * @param tokensCount   number of tokens to pick-up
     * @return              <b>true</b> if tokens where picked-up, else <b>false</b>
     */
    boolean pickTokens(int tokensCount) {
        boolean success = false;

        if (0 < tokensCount && tokensCount <= tokens) {
            tokens -= tokensCount;
            success = true;
        }

        return success;
    }

    /**
     * Put-on few tokens onto this Place.<br />
     * Number of tokens to put-on must be positive number
     *
     * @param tokensCount   number of tokens to put-on
     * @return              <b>true</b> if tokens where putted-on, else <b>false</b>
     */
    boolean putTokens(int tokensCount) {
        boolean success = false;

        if (0 <= tokensCount) {
            tokens += tokensCount;
        }

        return success;
    }

    @Override
    public boolean isTransition() {
        return false;
    }

    @Override
    public String toString() {
        return PLACE_NAME + getId();
    }
}

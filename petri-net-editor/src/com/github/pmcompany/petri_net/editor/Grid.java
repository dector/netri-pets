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

package com.github.pmcompany.petri_net.editor;

/**
 * Background grid model
 *
 * @author dector (dector9@gmail.com)
 * @version 0.1
 */
public class Grid {
    /** Default distance between grid nodes */
    public static final int DEFAULT_STEP = 10;
    /** Default padding for grid */
    public static final int DEFAULT_COORD = DEFAULT_STEP/2;

    /** X-step width */
    private int stepX;
    /** Y-step width */
    private int stepY;
    /** Left padding value */
    private int leftCoord;
    /** Top padding value */
    private int topCoord;

    /**
     * Create new Grid instance with default parameters
     */
    public Grid() {
        stepX = DEFAULT_STEP;
        stepY = DEFAULT_STEP;
        leftCoord = DEFAULT_COORD;
        topCoord = DEFAULT_COORD;
    }

    /**
     * Returns X-step width
     *
     * @return X-step width
     */
    public int getStepX() {
        return stepX;
    }

    /**
     * Sets X-step width
     *
     * @param stepX X-step width
     */
    public void setStepX(int stepX) {
        this.stepX = stepX;
    }

    /**
     * Returns Y-step width
     *
     * @return Y-step width
     */
    public int getStepY() {
        return stepY;
    }

    /**
     * Sets Y-step width
     *
     * @param stepY Y-step width
     */
    public void setStepY(int stepY) {
        this.stepY = stepY;
    }

    /**
     * Returns left padding value
     *
     * @return left padding value
     */
    public int getLeftCoord() {
        return leftCoord;
    }

    /**
     * Sets left padding value
     *
     * @param leftCoord left padding value
     */
    public void setLeftCoord(int leftCoord) {
        this.leftCoord = leftCoord;
    }

    /**
     * Returns top padding value
     *
     * @return top padding value
     */
    public int getTopCoord() {
        return topCoord;
    }

    /**
     * Sets top padding value
     * @param topCoord top padding value
     */
    public void setTopCoord(int topCoord) {
        this.topCoord = topCoord;
    }
}

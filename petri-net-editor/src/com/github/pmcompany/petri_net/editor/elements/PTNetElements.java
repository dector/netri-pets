package com.github.pmcompany.petri_net.editor.elements;

/**
 * @author dector (dector9@gmail.com)
 */
public enum PTNetElements {
    PLACE, TRANSITION, MOMENTAL_TRANSITION;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        switch (this) {
            case PLACE: sb.append("Place"); break;
            case TRANSITION: sb.append("Transition"); break;
            case MOMENTAL_TRANSITION: sb.append("Momental Transition"); break;
        }

        return sb.toString();
    }
}

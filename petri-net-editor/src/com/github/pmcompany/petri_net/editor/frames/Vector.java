package com.github.pmcompany.petri_net.editor.frames;

/**
 * @author dector (dector9@gmail.com)
 */
public class Vector {
    public static String print(int[] v) {
        StringBuilder sb = new StringBuilder();

        if (v != null && v.length != 0) {
            sb.append(String.format("[%d ", v[0]));
            for (int i = 1; i < v.length; i++) {
                sb.append(String.format("%4d ", v[i]));
            }
            sb.append("]\n");
        } else {
            sb.append("N/a\n");
        }


        return sb.toString();
    }

    public static String print(double[] v) {
        StringBuilder sb = new StringBuilder();

        if (v != null && v.length != 0) {
            sb.append(String.format("[%.2f ", v[0]));
            for (int i = 1; i < v.length; i++) {
                sb.append(String.format("%3.2f ", v[i]));
            }
            sb.append("]\n");
        } else {
            sb.append("N/a\n");
        }


        return sb.toString();
    }

    public static String print(String[] v) {
        StringBuilder sb = new StringBuilder();

        if (v != null && v.length != 0) {
            sb.append(String.format("%s ", v[0]));
            for (int i = 1; i < v.length; i++) {
                sb.append(String.format("%4s ", v[i]));
            }
            sb.append("\n");
        } else {
            sb.append("N/a\n");
        }


        return sb.toString();
    }
}

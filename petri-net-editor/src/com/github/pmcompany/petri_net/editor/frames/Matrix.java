package com.github.pmcompany.petri_net.editor.frames;

/**
 * @author dector (dector9@gmail.com)
 */
public class Matrix {
    public static String print(int[][] m) {
        StringBuilder sb = new StringBuilder();
        
        if (m != null && m.length != 0) {
            for (int i = 0; i < m.length; i++) {
                sb.append("[");

                sb.append(m[i][0]);
                for (int j = 1; j < m[0].length; j++) {
                    sb.append(String.format("%4d ", m[i][j]));
                }

                sb.append("]\n");
            }
        } else {
            sb.append("N/a\n");
        }
        
        return sb.toString();
    }

    public static String print(String[] lt, String[] ct, int[][] m) {
        StringBuilder sb = new StringBuilder();

        if (m != null && m.length != 0) {
            sb.append("    ").append(Vector.print(ct));

            for (int i = 0; i < m.length; i++) {
                sb.append(String.format("%s  [", lt[i]));

                sb.append(m[i][0]);
                for (int j = 1; j < m[0].length; j++) {
                    sb.append(String.format("%4d ", m[i][j]));
                }

                sb.append("]\n");
            }
        } else {
            sb.append("N/a\n");
        }

        return sb.toString();
    }
}

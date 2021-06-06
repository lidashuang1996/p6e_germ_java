package com.p6e.germ.oauth2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Test2 {
    public static void main(String[] args) {
        System.out.println(spiralOrder(new int[][]{
                new int[] {1,2,3},
                new int[] {4,5,6},
                new int[] {7,8,9},
        }));
        System.out.println(spiralOrder(new int[][]{
                new int[] {7},
                new int[] {9},
                new int[] {6},
        }));
    }

    public static List<Integer> spiralOrder(int[][] matrix) {
        final List<Integer> r = new LinkedList<>();
        final int m = matrix.length; // 高度
        final int n = matrix[0].length; // 宽度
        int index = 0;
        while (index <= (m / 2)) {
            for (int i = index; i < m - index; i++) {
                if (i == index) {
                    for (int j = index; j < n - index; j++) {
                        r.add(matrix[i][j]);
                    }
                } else if (i + 1 == m - index) {
                    for (int j = n - index - 1; j >= index; j--) {
                        r.add(matrix[i][j]);
                    }
                } else {
                    if (n - index * 2 >= 1) {
                        r.add(matrix[i][n - index - 1]);
                    }
                }
            }
            if (n - index * 2 > 1) {
                for (int i = (m - index - 2); i > index; i--) {
                    r.add(matrix[i][index]);
                }
            }
            index++;
        }
        return r;
    }
}

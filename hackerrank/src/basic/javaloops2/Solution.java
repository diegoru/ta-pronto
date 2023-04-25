package basic.javaloops2;

import java.util.*;

class Solution {
    public static void main(String[] argh) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for (int i = 0; i < t; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int n = in.nextInt();
            int res = a + b;
            int controle = 1;

            System.out.print(res);

            for (int j = 1; j < n; j++) {
                res = res + ((2 * controle) * b);
                controle *= 2;
                System.out.print(" " + res);
            }
            System.out.println();
        }
        in.close();
    }
}
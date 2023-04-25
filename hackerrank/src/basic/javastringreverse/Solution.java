package basic.javastringreverse;

import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String A = sc.next();
        /* Enter your code here. Print output to STDOUT. */
        String textoReverso = "";
        boolean isLower = true;

        for (int i = A.length() - 1; i >= 0; i--) {
            if (Character.isUpperCase(A.charAt(i))) {
                isLower = false;
            }
            textoReverso = textoReverso.concat(String.valueOf(A.charAt(i)));
        }
        if (isLower && A.length() <= 50 && A.equals(textoReverso)) {
            System.out.println("Yes");
        } else if (isLower && A.length() <= 50) {
            System.out.println("No");
        }

    }
}
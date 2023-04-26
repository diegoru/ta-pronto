package basic.javaanagrams;

import java.util.Scanner;

public class Solution {

    static boolean isAnagram(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }
        String[] arrayA = ordemAlfabetica(a.toUpperCase().split(""));
        String[] arrayB = ordemAlfabetica(b.toUpperCase().split(""));
        for (int i = 0; i < arrayA.length; i++) {
            if (!arrayA[i].equals(arrayB[i])) {
                return false;
            }
        }
        return true;
    }

    static String[] ordemAlfabetica(String[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i].compareTo(array[j]) > 0) {
                    String temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
        return array;
    }


    public static void main(String[] args) {
    
        Scanner scan = new Scanner(System.in);
        String a = scan.next();
        String b = scan.next();
        scan.close();
        boolean ret = isAnagram(a, b);
        System.out.println( (ret) ? "Anagrams" : "Not Anagrams" );
    }
}
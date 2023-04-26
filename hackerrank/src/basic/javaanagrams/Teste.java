package basic.javaanagrams;

import java.util.Arrays;

public class Teste {
    public static void main(String[] args) {

        String a = "anaGrAm";
        String b = "margana";
        boolean isAnagram = false;


        /*String[] splitA = a.toLowerCase().split("");
        String[] splitB = b.toLowerCase().split("");
        Arrays.sort(splitA);
        Arrays.sort(splitB);
        isAnagram = Arrays.equals(splitA, splitB);*/

        String[] splitA = a.toLowerCase().split("");
        String[] splitB = b.toLowerCase().split("");

        for (int i = 0; i < splitA.length - 1; i++) {
            for (int j = i + 1; j < splitA.length; j++) {
                if (splitA[i].compareTo(splitA[j]) > 0) {
                    String temp = splitA[i];
                    splitA[i] = splitA[j];
                    splitA[j] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(splitA));
    }
}


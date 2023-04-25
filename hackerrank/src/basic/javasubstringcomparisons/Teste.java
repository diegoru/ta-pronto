package basic.javasubstringcomparisons;

import java.util.Arrays;

public class Teste {
    public static void main(String[] args) {
        String texto = "welcometojava";
        int tamanho = 3;

        String smallest = texto.substring(0, tamanho);
        String largest = texto.substring(0, tamanho);

        for (int i = 0; i < texto.length(); i++) {
            /* System.out.println(i + " " + (i + 3));
            System.out.println(texto.substring(i, (i+3)));*/

            if ((i + 3) <= texto.length()) {
                //System.out.println(texto.substring(i, (i + 3)));
                if (texto.substring(i, (i + tamanho)).compareTo(smallest) < 0) {
                    smallest = texto.substring(i, (i + tamanho));
                } else if (texto.substring(i, (i + tamanho)).compareTo(largest) > 0) {
                    largest = texto.substring(i, (i + tamanho));
                }
            }
        }

        System.out.println("Small: " + smallest);
        System.out.println("Larg: " + largest);

    }
}

/*
welcometojava
["ava", "com", "elc", "eto", "jav", "lco", "met", "oja", "ome", "toj", "wel"]


["ava", "com", "elc", "eto", "jav", "lco", "met", "oja", "ome", "toj", "wel"]

wel, com, eto, jav, ava
elc, ome, toj
lco, met, oja

 */

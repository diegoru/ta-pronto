package basic.javacurrencyformatter;

import java.text.NumberFormat;
import java.util.Locale;

public class Teste {
    public static void main(String[] args) {


        double quantia = 99.90;
        System.out.println("US: " + NumberFormat.getCurrencyInstance(Locale.US).format(quantia));
        System.out.println("India: " + NumberFormat.getCurrencyInstance(new Locale("en", "IN")).format(quantia));
        System.out.println("China: " + NumberFormat.getCurrencyInstance(Locale.CHINA).format(quantia));
        System.out.println("France: " + NumberFormat.getCurrencyInstance(Locale.FRANCE).format(quantia));
    }
}

package introducion;

import java.util.Locale;
import java.util.Scanner;

public class Exercicio4 {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        Scanner scan = new Scanner(System.in);
        int i = scan.nextInt();
        Double d = scan.nextDouble();
        scan.nextLine();
        String s = scan.nextLine();

        System.out.println("String: " + s);
        System.out.println("Double: " + d);
        System.out.println("Int: " + i);
    }
}

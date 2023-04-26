package basic.javastringtokens;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Teste {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);
        String pattern = entrada.nextLine();
        try {
            Pattern.compile(pattern);
            System.out.println("Valid");
        } catch (Exception e) {
            System.out.println("Invalid");
        }

    }
}

/*
([A-Z])(.+)
[AZ[a-z](a-z)
batcatpat(nat*/

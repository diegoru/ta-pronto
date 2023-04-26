package basic.javastringtokens;

import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        if (!scan.hasNext()) {
            System.out.println(0);
        } else {
            String s = scan.nextLine();
            String[] token = s.trim().split("[ !,?.*_'@]+");
            System.out.println(token.length);
            for (String str : token) {
                System.out.println(str);
            }
        }

        scan.close();
    }
}
//He is a very very good boy, isn't he?
package basic.staticinitializerblock;

import java.util.Scanner;

public class Solution {

    private static boolean flag;

    static {
        try {
            flag = isValid();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static int B;
    private static int H;

    public static boolean isValid() throws Exception {
        Scanner scanner = new Scanner(System.in);
        B = scanner.nextInt();
        H = scanner.nextInt();
        if (B <= 0 || H <= 0) throw new Exception("java.lang.Exception: Breadth and height must be positive");
        return true;
    }

    public static void main(String[] args){
        if(flag){
            int area=B*H;
            System.out.print(area);
        }

    }//end of main

}//end of class


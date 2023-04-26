package basic.javaregex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Teste {

    public static void main(String[] args) {

//        String ip = "000.12.12.034";
//        String ip = "00.12.123.123123.123";
        String ip = "122.23";
        String pattern = "([01]?\\d{1,2}|2[0-4]\\d|25[0-5])";
        Pattern val = Pattern.compile(pattern);
        Matcher matcher = val.matcher(ip);
        System.out.println(matcher.matches());

    }
}

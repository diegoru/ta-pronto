package introducion.javadateandtime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Teste {
    public static void main(String[] args) {
        /*LocalDate date = LocalDate.of(2023,4,25 );
        System.out.println(date.getDayOfWeek());*/
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, 03, 25);
        int numberDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        DateFormat formatter = new SimpleDateFormat("EEEE");
        System.out.println(formatter.format(calendar.getTime()));


    }
}

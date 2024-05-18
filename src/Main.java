import com.vacationcalendar.Classes.HolidayUtils;
import com.vacationcalendar.Classes.MonthUtils;
import com.vacationcalendar.Models.Months;

import java.time.LocalDateTime;
import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
        LocalDateTime date = LocalDateTime.now();

        HolidayUtils hol = new HolidayUtils(true, true, true);
        hol.GetHolidayList(date, "SP");

        Months month1 = new Months(Calendar.JANUARY, 2024);
        Months month2 = new Months(Calendar.FEBRUARY, 2024);
        Months month3 = new Months(Calendar.MARCH, 2024);
        Months month4 = new Months(Calendar.APRIL, 2024);
        Months month5 = new Months(Calendar.MAY, 2024);
        Months month6 = new Months(Calendar.JUNE, 2024);
        Months month7 = new Months(Calendar.JULY, 2024);
        Months month8 = new Months(Calendar.AUGUST, 2024);
        Months month9 = new Months(Calendar.SEPTEMBER, 2024);
        Months month10 = new Months(Calendar.OCTOBER, 2024);
        Months month11 = new Months(Calendar.NOVEMBER, 2024);
        Months month12 = new Months(Calendar.DECEMBER, 2024);

        System.out.println(month1.toString());
        System.out.println(month2.toString());
        System.out.println(month3.toString());
        System.out.println(month4.toString());
        System.out.println(month5.toString());
        System.out.println(month6.toString());
        System.out.println(month7.toString());
        System.out.println(month8.toString());
        System.out.println(month9.toString());
        System.out.println(month10.toString());
        System.out.println(month11.toString());
        System.out.println(month12.toString());
    }
}
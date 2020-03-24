import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main (String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        String dateString = ("2020-03-23 14:12:32.123");

        double timedifference = 1.421;
        double millis = timedifference * 1000;

        long timeInLong = (long) millis;
        System.out.println(timeInLong);

        try {
            Date date = sdf.parse(dateString);

            long timeInMillis = date.getTime();
           // System.out.println(timeInMillis);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}

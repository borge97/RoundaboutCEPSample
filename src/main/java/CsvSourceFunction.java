import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.watermark.Watermark;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CsvSourceFunction implements SourceFunction<Record> {

    private volatile boolean isRunning = true;

    public void run(SourceContext<Record> ctx) throws IOException {

        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader("C:/Users/borge/Desktop/Landstraße(Deggendorf-Zwiesel).csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String row;

            while ((row = csvReader.readLine()) != null) {
                try {
                    row = replaceCommata(row);
                    String[] numbersAsString = row.split(";");

                    double lat = Double.parseDouble(numbersAsString[0]);
                    double lng = Double.parseDouble(numbersAsString[1]);
                    double accpitch = Double.parseDouble(numbersAsString[2]);
                    double accyaw = Double.parseDouble(numbersAsString[3]);
                    double accroll = Double.parseDouble(numbersAsString[4]);
                    double gyropitch = Double.parseDouble(numbersAsString[5]);
                    double gyroyaw = Double.parseDouble(numbersAsString[6]);
                    double gyroroll = Double.parseDouble(numbersAsString[7]);
                    double speed = Double.parseDouble(numbersAsString[8]);
                    double timedifference = Double.parseDouble(numbersAsString[9]);
                    Record record = new Record(lat, lng, accpitch, accyaw, accroll, gyropitch, gyroyaw, gyroroll, speed, timedifference);

                    ctx.collectWithTimestamp(record, getTimestamp(timedifference));
                    ctx.emitWatermark(new Watermark(getTimestamp(timedifference)));


                }

                catch (Exception e) {
                    Record record = new Record(0,0,0,0,0,0,0,0,0,0);
                    ctx.collect(record);
                }

        }



    }

    private long getTimestamp (double timedifference) {
        double millisAsDouble = timedifference * 1000;
        long csvTime = (long) millisAsDouble;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String dateString = ("2020-03-23 14:00:00.000");

        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long fixedTimeInMillis = date.getTime();
        long timestamp = fixedTimeInMillis + csvTime;

        return timestamp;

    }

    public String replaceCommata (String stringWithCommata) {
        String newString = stringWithCommata.replace(',', '.');
        return newString;
    }



    public void cancel() {
        isRunning = false;
    }
}

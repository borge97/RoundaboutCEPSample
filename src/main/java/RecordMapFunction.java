import org.apache.flink.api.common.functions.MapFunction;

public class RecordMapFunction implements MapFunction<String, Record> {


    public Record map(String csvValues) {
        return getNewRecord(csvValues);

    }

    public Record getNewRecord (String csvValues) {
        csvValues = replaceCommata(csvValues);
        String[] numbersAsString = csvValues.split(";");


        Record record = null;
        try {
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
            record = new Record(lat, lng, accpitch, accyaw, accroll, gyropitch, gyroyaw, gyroroll, speed, timedifference);

        }
        catch (NumberFormatException e) {
            record = new Record(0,0,0,0,0,0,0,0,0,0);
        }

        return record;


    }

    public String replaceCommata (String stringWithCommata) {
       String newString = stringWithCommata.replace(',', '.');
       return newString;
    }




}

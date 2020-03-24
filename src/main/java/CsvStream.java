import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.functions.PatternProcessFunction;
import org.apache.flink.cep.nfa.aftermatch.AfterMatchSkipStrategy;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.util.List;
import java.util.Map;

public class CsvStream {

    public static void main (String[] args)  {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        //DataStream<String> csvStream = env.readTextFile("C:/Users/borge/Desktop/Burgweinting(normal).csv");
        //DataStream<Record> recordStream = csvStream.map(new RecordMapFunction());

        DataStream<Record> recordStream = env.addSource(new CsvSourceFunction());

        AfterMatchSkipStrategy skipStrategy = AfterMatchSkipStrategy.skipPastLastEvent();
        Pattern<Record, ?> pattern = Pattern.<Record>begin("start", skipStrategy).where(new SimpleCondition<Record>() {
            public boolean filter(Record record) throws Exception {
                return record.getGyroroll() < -0.2;
            }
        }).followedBy("middle").subtype(Record.class).where(new SimpleCondition<Record>() {
            public boolean filter(Record record) throws Exception {
                return record.getGyroroll() > 0.2;
            }
        }).followedBy("end").subtype(Record.class).where(new IterativeCondition<Record>() {
            @Override
            public boolean filter(Record record, Context<Record> context) throws Exception {
                return record.getGyroroll() < -0.2;
            }
        }).within(Time.seconds(20));


        PatternStream<Record> patternStream = CEP.pattern(recordStream, pattern);

        DataStream<String> result = patternStream.process(new PatternProcessFunction<Record, String>() {
            @Override
            public void processMatch(Map<String, List<Record>> map, Context context, Collector<String> collector) throws Exception {
                double start = map.get("start").get(0).getTimedifference();
                double middle = map.get("middle").get(0).getTimedifference();
                double end = map.get("end").get(0).getTimedifference();


                //System.out.println(time);

                System.out.println("Roundabout: " + start + " --> " + middle + " --> " + end);
            }
        });

        result.print();







        //recordStream.print();






        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

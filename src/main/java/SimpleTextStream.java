import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.functions.PatternProcessFunction;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.core.execution.JobClient;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.List;
import java.util.Map;


public class SimpleTextStream {
    public static void main (String[] args)  {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> text = env.readTextFile("C:/Users/borge/Desktop/test.txt");

        DataStream<Integer> parsed = text.map(new MapFunction<String, Integer>() {

            public Integer map(String value) {
                return Integer.parseInt(value);
            }
        });

        Pattern<Integer, ?> pattern = Pattern.<Integer>begin("start").where(new SimpleCondition<Integer>() {
            public boolean filter(Integer integer) throws Exception {
                return integer == 1;
            }
        }).next("middle").where(new SimpleCondition<Integer>() {
            public boolean filter(Integer integer) throws Exception {
                return integer == 2;
            }
        }).next("end").where(new SimpleCondition<Integer>() {
            public boolean filter(Integer integer) throws Exception {
                return integer == 4;
            }
        });

        PatternStream<Integer>  patternStream = CEP.pattern(parsed, pattern);

        DataStream<String> result = patternStream.process(new PatternProcessFunction<Integer, String>() {
            @Override
            public void processMatch(Map<String, List<Integer>> map, Context context, Collector<String> collector) throws Exception {
                System.out.println("Match!");
            }
        });

        result.print();




        try {
           env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package analyze.fromHBToMys.teleDuration;

import analyze.fromHBToMys.entity.Statistics;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class HBMyReducer extends Reducer<Text,IntWritable,Statistics,NullWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        int totalDuration = 0;
        for(IntWritable i:values){
            totalDuration += i.get();
        }
        /*1.context.write(key,new IntWritable(totalDuration))写出去的是一个key，但是我想在mysql中接收到的却非一个字段，是没有问题的么？
         */
        String tempValue = key.toString();
        System.out.println("tempValue is "+tempValue);
        //String teleNumber = tempValue.substring(0,11);
        String teleNumber = tempValue;

        //String yearMonth = tempValue.substring(11);
        //System.out.println("teleNumber :"+teleNumber+"\nyearMonth :"+yearMonth+"\ntotalDuration :"+totalDuration);
        System.out.println("teleNumber :"+teleNumber+"\ntotalDuration :"+totalDuration);

        //get a new instance of MonthStat
        Statistics statistics = new Statistics(teleNumber,totalDuration);
        context.write(statistics,null);
    }
}
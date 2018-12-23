package analyze.fromHBToMys.teleIntimacy;

import analyze.fromHBToMys.entity.Intimacy;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class HBMyReducer extends Reducer<Text,IntWritable,Intimacy,NullWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        int totalDuration = 0;
        for(IntWritable i:values){
            totalDuration += i.get();
        }
        String tempValue = key.toString();
        System.out.println("tempValue is "+tempValue);
        String call1 = tempValue.substring(0,11);//get call1

        String call2 = tempValue.substring(11,22);//get call2

        String year = tempValue.substring(22);
        System.out.println("call1 :"+call1+"\nyear: "+year +"\ncall2 :"+call2+"\ntotalDuration :"+totalDuration);
        //get a new instance of MonthStat
        Intimacy intimacy = new Intimacy(call1,call2,year,totalDuration);
        context.write(intimacy,null);
    }
}
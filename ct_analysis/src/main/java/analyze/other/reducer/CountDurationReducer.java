package analyze.other.reducer;

import analyze.other.kv.key.ComDimension;
import analyze.other.kv.value.CountDurationValue;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 01.Reducer 类，实现最简单的Reducer
 * 02.ComDimension
 *
 */

public class CountDurationReducer extends Reducer<ComDimension, Text, ComDimension, CountDurationValue>{
    CountDurationValue countDurationValue = new CountDurationValue();

    //重写reduce方法，但是这个reduce 方法为啥只有三个参数？
    //
    @Override
    protected void reduce(ComDimension key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        int callSum = 0;
        int callDurationSum = 0;

        //下面实现的功能应该就是将某个人的通话累加
        for(Text t : values){
            callSum++;
            callDurationSum += Integer.valueOf(t.toString());
        }

        countDurationValue.setCallSum(String.valueOf(callSum));
        countDurationValue.setCallDurationSum(String.valueOf(callDurationSum));

        context.write(key, countDurationValue);
    }
}

package analyze.fromHBToMys.teleDuration;

import analyze.fromHBToMys.util.HBaseUtil;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.HashMap;

public class HBMyMapper extends TableMapper<Text,IntWritable> {
    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context)
            throws IOException, InterruptedException {
        HashMap<String, String> cfValueMap = new HashMap<String, String>();

        //the value is a scan of every row of HBase table's
        for(Cell cell :value.rawCells()){
            HBaseUtil.getValue(cell,cfValueMap);
        }

        //将caller+year+month作为输出的key
        //先尝试将call1 作为输出的key
        String realKey = cfValueMap.get("call1");// + cfValueMap.get("build_time").substring(0,6);
        System.out.println("realKey "+realKey);
        String tempValue = cfValueMap.get("duration");
        System.out.println("duration "+tempValue);

        //排除一些其它原因导致的数据不匹配 => 程序健壮性
        if(realKey!=null && tempValue!=null)
        {
            Integer realValue = Integer.valueOf(tempValue);//transform to Integer
            System.out.println("realKey:"+realKey+"\ntempValue:"+tempValue);
            context.write(new Text(realKey),new IntWritable(realValue));
        }
    }
}
/*
1.for(Cell cell:values )这里的value是HBAse表中scan操作得到的一行。即rowkey相同的，都会被获取到。
 */

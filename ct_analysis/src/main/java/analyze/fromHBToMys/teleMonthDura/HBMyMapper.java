package analyze.fromHBToMys.teleMonthDura;

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
        //将caller+year+month 作为输出的key
        String realKey = this.getMapOutkey(cfValueMap.get("call1"), cfValueMap.get("build_time"));

        String duration = cfValueMap.get("duration");
        System.out.println("duration "+duration);

        //排除一些其它原因导致的数据不匹配 => 程序健壮性
        if(realKey!=null && duration!=null)
        {
            Integer realValue = Integer.valueOf(duration);//transform to Integer
            System.out.println("realKey:"+realKey+"\nduration:"+duration);
            context.write(new Text(realKey),new IntWritable(realValue));
        }
    }

    public String getMapOutkey(String caller,String date) {
        String outKey = null;
        String yearMonth[] = date.split("-");
        if(yearMonth.length<2)
        {
            System.out.println("Out of boundary of Array");
            return null;
        }
        outKey = caller+yearMonth[0]+yearMonth[1];
        return outKey;
    }
}
/*
1.for(Cell cell:values )这里的value是HBAse表中scan操作得到的一行。即rowkey相同的，都会被获取到。
 */

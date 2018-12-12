package mapper;

import kv.key.ComDimension;
import kv.key.ContactDimension;
import kv.key.DateDimension;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CountDurationMapper extends TableMapper<ComDimension, Text>{
    private ComDimension comDimension = new ComDimension();
    private Text durationText = new Text();
    private Map<String, String> phoneNameMap;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
        phoneNameMap = new HashMap<>();
        phoneNameMap.put("17078388295", "李雁");
        phoneNameMap.put("13980337439", "卫艺");
        phoneNameMap.put("14575535933", "仰莉");
        phoneNameMap.put("19902496992", "陶欣悦");
        phoneNameMap.put("18549641558", "施梅梅");
        phoneNameMap.put("17005930322", "金虹霖");
        phoneNameMap.put("18468618874", "魏明艳");
        phoneNameMap.put("18576581848", "华贞");
        phoneNameMap.put("15978226424", "华啟倩");
        phoneNameMap.put("15542823911", "仲采绿");
        phoneNameMap.put("17526304161", "卫丹");
        phoneNameMap.put("15422018558", "戚丽红");
        phoneNameMap.put("17269452013", "何翠柔");
        phoneNameMap.put("17764278604", "钱溶艳");
        phoneNameMap.put("15711910344", "钱琳");
        phoneNameMap.put("15714728273", "缪静欣");
        phoneNameMap.put("16061028454", "焦秋菊");
        phoneNameMap.put("16264433631", "吕访琴");
        phoneNameMap.put("17601615878", "沈丹");
        phoneNameMap.put("15897468949", "褚美丽");
    }

    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
        //05_19902496992_20170312154840_15542823911_1_1288
        String rowKey = Bytes.toString(key.get());
        String[] splits = rowKey.split("_");
        if(splits[4].equals("0")) return;

        //以下数据全部是主叫数据，但是也包含了被叫电话的数据
        String caller = splits[1];
        String callee = splits[3];
        String buildTime = splits[2];
        String duration = splits[5];
        durationText.set(duration);

        String year = buildTime.substring(0, 4);
        String month = buildTime.substring(4, 6);
        String day = buildTime.substring(6, 8);

        //组装ComDimension
        //组装DateDimension
        ////05_19902496992_20170312154840_15542823911_1_1288
        DateDimension yearDimension = new DateDimension(year, "-1", "-1");
        DateDimension monthDimension = new DateDimension(year, month, "-1");
        DateDimension dayDimension = new DateDimension(year, month, day);

        //组装ContactDimension
        ContactDimension callerContactDimension = new ContactDimension(caller, phoneNameMap.get(caller));

        //开始聚合主叫数据
        comDimension.setContactDimension(callerContactDimension);
        //年
        comDimension.setDateDimension(yearDimension);
        context.write(comDimension, durationText);
        //月
        comDimension.setDateDimension(monthDimension);
        context.write(comDimension, durationText);
        //日
        comDimension.setDateDimension(dayDimension);
        context.write(comDimension, durationText);

        //开始聚合被叫数据
        ContactDimension calleeContactDimension = new ContactDimension(callee, phoneNameMap.get(callee));
        comDimension.setContactDimension(calleeContactDimension);
        //年
        comDimension.setDateDimension(yearDimension);
        context.write(comDimension, durationText);
        //月
        comDimension.setDateDimension(monthDimension);
        context.write(comDimension, durationText);
        //日
        comDimension.setDateDimension(dayDimension);
        context.write(comDimension, durationText);
    }
}
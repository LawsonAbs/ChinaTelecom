package hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import utils.HBaseUtil;
import utils.PropertiesUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class HBaseDAO {
    //set some basic variables
    private int regions;//regions' number
    private String namespace;//table's namespace
    private String tableName;//table's name
    public static final Configuration conf;//set static and final
    private Table table;
    private Connection connection;//hadoop.hbase.client
    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");

    static {
        conf = HBaseConfiguration.create();
    }

    public HBaseDAO() {
        try {
            /*
            1.valueOf的作用相当于：new Integer(Integer.parseInt(s))
             */
            regions = Integer.valueOf(PropertiesUtil.getProperty("hbase.calllog.regions"));
            namespace = PropertiesUtil.getProperty("hbase.calllog.namespace");
            tableName = PropertiesUtil.getProperty("hbase.calllog.tablename");

            //get an connection
            connection = ConnectionFactory.createConnection(conf);
            //
            table = connection.getTable(TableName.valueOf(tableName));
            //if table don't exist,create!
            if (!HBaseUtil.isExistTable(conf, tableName)) {
                HBaseUtil.initNamespace(conf, namespace);
                HBaseUtil.createTable(conf, tableName, regions, "f1", "f2");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ori数据样式： 18576581848 17269452013 2017-08-14 13:38:31 1761
     * rowkey样式：01_18576581848_20170814133831_17269452013_1_1761
     * HBase表的列：call1  call2   build_time   build_time_ts   flag   duration
     * @param ori
     */
    public void put(String ori) {
        try {
            String[] splitOri = ori.split(" ");//split ori(gin Value) with space

            String caller = splitOri[0];
            String callee = splitOri[1];
            String buildTime = splitOri[2];
            String spcificTime = splitOri[3];
            String duration = splitOri[4];

            buildTime += spcificTime;
            String regionCode = HBaseUtil.genRegionCode(caller, buildTime, regions);

            String buildTimeReplace = sdf2.format(sdf1.parse(buildTime));
            String buildTimeTs = String.valueOf(sdf1.parse(buildTime).getTime());//get a timeStamp

            //generate a rowkey
            String rowKey = HBaseUtil.genRowKey(regionCode, caller, buildTimeReplace, callee, "1", duration);

            //write data into particular table
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("call1"), Bytes.toBytes(caller));//the voluntary number
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("call2"), Bytes.toBytes(callee));//the passive number
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("build_time"), Bytes.toBytes(buildTime));
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("build_time_ts"), Bytes.toBytes(buildTimeTs));
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("flag"), Bytes.toBytes("1"));
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("duration"), Bytes.toBytes(duration));

            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

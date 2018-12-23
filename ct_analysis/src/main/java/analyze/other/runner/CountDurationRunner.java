package analyze.other.runner;


import analyze.other.kv.key.ComDimension;
import analyze.other.kv.value.CountDurationValue;
import analyze.other.mapper.CountDurationMapper;
import analyze.other.outputformat.MysqlOutputFormat;
import analyze.other.reducer.CountDurationReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;

/*
1.装载 Mapper 和 Reducer 的总Job
 */
public class CountDurationRunner implements Tool{
    private Configuration conf = null;

    @Override
    public void setConf(Configuration conf) {
        this.conf = HBaseConfiguration.create(conf);
        //可以结合源码理解->     //底层是将conf 和Hbase-_.xml文件中的配置融合成一个conf
    }

    @Override
    public Configuration getConf() {
        return this.conf;
    }

    //run: Execute the command with the given arguments.
    @Override
    public int run(String[] args) throws Exception {
        //get configuration
        Configuration conf = this.getConf();
        //instance the Job
        Job job = Job.getInstance(conf);
        job.setJarByClass(CountDurationRunner.class);

        //组装Mapper InputForamt
        initHBaseInputConfig(job);

        //组装Reducer Outputformat
        initReducerOutputConfig(job);
        return job.waitForCompletion(true) ? 0 : 1;
    }

    private void initHBaseInputConfig(Job job) {
        Connection connection = null;
        Admin admin = null;
        try {
            String tableName = "ns_ct:calllog";
            connection = ConnectionFactory.createConnection(job.getConfiguration());
            admin = connection.getAdmin();
            if(!admin.tableExists(TableName.valueOf(tableName))) throw new RuntimeException("无法找到目标表.");
            Scan scan = new Scan();

            //可以优化            //初始化Mapper
            TableMapReduceUtil.initTableMapperJob(
                    tableName,
                    scan,
                    CountDurationMapper.class,
                    ComDimension.class,
                    Text.class,
                    job,
                    true);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(admin != null){
                    admin.close();
                }
                if(connection != null && !connection.isClosed()){
                    connection.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initReducerOutputConfig(Job job) {
        //为什么不设置全？job.setMapperClass()
        job.setReducerClass(CountDurationReducer.class);
        job.setOutputKeyClass(ComDimension.class);
        job.setOutputValueClass(CountDurationValue.class);
        job.setOutputFormatClass(MysqlOutputFormat.class);
    }

    public static void main(String[] args) {
        try {
            //ToolRunner: A utility to help run Tools. 一个帮助运行Tools的实用类
            //注意，其参数是一个Tool类，（因为CountDurationRunner实现了Tool类），所以可以作为参数传递
            int status = ToolRunner.run(new CountDurationRunner(), args);
            System.exit(status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

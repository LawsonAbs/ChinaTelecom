package analyze.fromHBToMys.teleIntimacy;


import analyze.fromHBToMys.entity.Intimacy;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;

import java.io.IOException;

public class HBIntimacyJob {
    /**
     * fieldNames: table's field Name
     */
    public static String hbaseTableName = "ns_ct:calllog";
    public static String familyName = "f1" ;
    public static String mysqlTableName = "intimacy";
    public static String[] fieldNames = {"call1","call2","year","callDuration"};

    public static String driverClass = "com.mysql.jdbc.Driver";
    public static String dbUrl = "jdbc:mysql://192.168.211.4:3306/mydatabase";
    public static String userName = "root";
    public static String passwd = "root";

    public static Configuration conf = new Configuration();
    static {
        //this configuration is very important
        conf.set("hbase.master", "192.168.211.4:60000");
        conf.set("hbase.zookeeper.quorum", "192.168.211.4");
        conf.set("hbase.zookeeper.property.clientPort","2181");
    }
    public static void main(String[] args) {
        try {
            DBConfiguration.configureDB(conf,driverClass,dbUrl,userName,passwd);
            Job job = Job.getInstance(conf);
            Scan scan = new Scan();

            //add HBase Family
            scan.addFamily(Bytes.toBytes(familyName));

            TableMapReduceUtil.initTableMapperJob(
                    hbaseTableName,
                    scan,
                    HBMyMapper.class,
                    Intimacy.class,
                    NullWritable.class,
                    job
            );
            job.setJarByClass(HBIntimacyJob.class);
            job.setJobName("HBaseToMysql");

            job.setInputFormatClass(TableInputFormat.class);
            job.setOutputFormatClass(DBOutputFormat.class);

            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);

            job.setMapperClass(HBMyMapper.class);
            job.setReducerClass(HBMyReducer.class);

            DBOutputFormat.setOutput(job,mysqlTableName,fieldNames);
            job.waitForCompletion(true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

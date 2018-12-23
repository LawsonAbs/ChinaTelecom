package analyze.other.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.TreeSet;

public class HBaseUtil {
    /**
     * 判断表是否存在
     * @param conf HBaseConfiguration
     * @param tableName
     * @return
     */
    public static boolean isExistTable(Configuration conf, String tableName) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();
        boolean result = admin.tableExists(TableName.valueOf(tableName));

        admin.close();
        connection.close();

        return result;
    }


    //TODO : judge the tableSpace if not exist
    public static boolean isExistTableSpace(Configuration conf, String tableSpace) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();
        boolean result = false;
        return result;
    }


    /**
     * 初始化命名空间
     * @param conf
     * @param namespace
     */
    public static void initNamespace(Configuration conf, String namespace) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();

        NamespaceDescriptor nd = NamespaceDescriptor
                .create(namespace)
                .addConfiguration("CREATE_TIME", String.valueOf(System.currentTimeMillis()))
                .addConfiguration("AUTHOR", "Lawson")
                .build();

        //Create a new namespace
        admin.createNamespace(nd);

        admin.close();
        connection.close();
    }

    /**
     * 创建表：协处理器
     * @param conf
     * @param tableName
     * @param columnFamily
     * @throws IOException
     */
    //String... columnFamily must put in last
    public static void createTable(Configuration conf, String tableName, int regions, String... columnFamily) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();

        if(isExistTable(conf, tableName)) return;

        HTableDescriptor htd = new HTableDescriptor(TableName.valueOf(tableName));
        for(String cf: columnFamily){
            htd.addFamily(new HColumnDescriptor(cf));
        }

        //add a table coprocessor to this table.
        //htd.addCoprocessor("hbase.CalleeWriteObserver");
        byte [][] temp = genSplitKeys(regions);
        //System.out.println(temp);

        //admin.createTable(htd, genSplitKeys(regions));
        admin.createTable(htd, genSplitKeys(regions));
        admin.close();
        connection.close();
    }

    private static byte[][] genSplitKeys(int regions){
        //定义一个存放分区键的数组
        String[] keys = new String[regions];

        //目前推算，region个数不会超过2位数，所以region分区键格式化为两位数字所代表的字符串
        DecimalFormat df = new DecimalFormat("00");

        for(int i = 0; i < regions; i ++){
            keys[i] = df.format(i) + "|";
        }

        byte[][] splitKeys = new byte[regions][];

        //生成byte[][]类型的分区键的时候，一定要保证分区键是有序的
        TreeSet<byte[]> treeSet = new TreeSet<>(Bytes.BYTES_COMPARATOR);
        for(int i = 0; i < regions; i++){
            treeSet.add(Bytes.toBytes(keys[i]));
        }

        Iterator<byte[]> splitKeysIterator = treeSet.iterator();
        int index = 0;
        while(splitKeysIterator.hasNext()){
            byte[] b = splitKeysIterator.next();
            splitKeys[index ++] = b;
        }
        return splitKeys;
    }

    /**
     * generate a rowkey
     * regionCode_call1_buildTime_call2_flag_duration
     * @return
     */
    public static String genRowKey(String regionCode, String call1, String buildTime, String call2, String flag, String duration){
        StringBuilder sb = new StringBuilder();//keep high efficiency rather than String
        sb.append(regionCode + "_")
                .append(call1 + "_")
                .append(buildTime + "_")
                .append(call2 + "_")
                .append(flag + "_")
                .append(duration);
        return sb.toString();
    }

    /**
     * 手机号：15837312345
     * 通话建立时间：2017-01-10 11:20:30 -> 20170110112030
     * @param call1
     * @param buildTime
     * @param regions
     * @return
     */
    /*
    1.you should keep same number in same year month are stored in same partition
    2.so the regionCode are depended on call1(this is a number),buildTime(they are year and month that call were build),
     */
    public static String genRegionCode(String call1, String buildTime, int regions){
        int len = call1.length();
        //get Last quartile in number
        String lastPhone = call1.substring(len - 4);

        //format and get year and month
        String ym = buildTime
                .replaceAll("-", "")
                .replaceAll(":", "")
                .replaceAll(" ", "")
                .substring(0, 6);

        //first scatter operation
        Integer x = Integer.valueOf(lastPhone) ^ Integer.valueOf(ym);
        int a = 10;
        int b = 20;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;//second

        //second scatter operation
        int y = x.hashCode();

        //generate regionCode
        int regionCode = y % regions;

        //format regionCode
        DecimalFormat df = new DecimalFormat("00");
        return  df.format(regionCode);
    }
}

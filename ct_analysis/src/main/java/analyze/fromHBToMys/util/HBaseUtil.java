package analyze.fromHBToMys.util;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;

import java.util.HashMap;

public class HBaseUtil {
    //get value(qualifier and value) from cell
    public static void getValue(Cell cell,HashMap<String, String> cfValueMap ){
        //the value is a scan of every row of HBase table's
        /*1.这里的每个cell是什么意思？
        如果一个列族family里面有很多qualifier，那么这个该怎么表示？=> 每个qualifier将得到顺序的表示*/
        //String qualifier =  new String(cell.getQualifierArray());
        //call1 = new String(cell.getValueArray());
        String qualifier = new String(CellUtil.cloneQualifier(cell));
        //如果是call1，则获取其 value 字段

        String quaValue = new String(CellUtil.cloneValue(cell));
        System.out.println("qualifier: "+qualifier+" quaValue: "+quaValue);
        cfValueMap.put(qualifier,quaValue);
    }
}

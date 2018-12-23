package kafka;

import hbase.HBaseDAO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import analyze.other.utils.PropertiesUtil;

import java.util.Arrays;


/**
 * 01.consume the topic in kafka
 * 02.
 */
public class HBaseConsumer extends Consumer{

    /**
     * 01. get data from kafka and store in hbase
     */
    public void getDataFromKafka() {
        //initial the kafkaConsumer by properties
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(PropertiesUtil.properties);

        //subscribe topics. But in this code, only one topic to get
        kafkaConsumer.subscribe(Arrays.asList(PropertiesUtil.getProperty("kafka.topics")));

        HBaseDAO hd = new HBaseDAO();
        // consume the data circularly
        while(true){
            //ConsumerRecords is a container of

            //poll(): 获取使用订阅/分配api指定的主题或分区的数据。
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for(ConsumerRecord<String, String> cr : records){
                String oriValue = cr.value();//get value
                System.out.println(oriValue);//print and test
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hd.put(oriValue);//
            }
        }
    }
}

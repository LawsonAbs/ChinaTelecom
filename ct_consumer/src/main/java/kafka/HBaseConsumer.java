package kafka;

import hbase.HBaseDAO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import utils.PropertiesUtil;

import java.util.Arrays;

public class HBaseConsumer extends Consumer{
    public static void main(String[] args) {
        //kafkaConsumer
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(PropertiesUtil.properties);

        //subscribe one topic
        kafkaConsumer.subscribe(Arrays.asList(PropertiesUtil.getProperty("kafka.topics")));

        HBaseDAO hd = new HBaseDAO();
        while(true){
            //ConsumerRecords is a container of
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for(ConsumerRecord<String, String> cr : records){
                String oriValue = cr.value();//get value
                System.out.println(oriValue);//print and test
                hd.put(oriValue);//
            }
        }
    }
}

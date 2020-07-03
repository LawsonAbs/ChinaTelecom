package com.lawson.data;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;

import java.util.*;
import java.util.concurrent.TimeUnit;

/*
1.implement Kafka client,in order to write data into Kafka Broker
 */
public class KafkaUtils {
    private static ArrayList<String> teleNumber = new ArrayList<String >();//store telephone number;
    private static HashMap<String,String> numberAndName = new HashMap<String, String>();//telephone number --> name
    private static DataProducer dataProducer = new DataProducer();

    //specific function to write data into kafka
    //The function in Utils is static
    //This function is a simple demo of write data into kafka
    public static void writeDataIntoKafka()  {
        //step 1.get a new configuration
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.211.4:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //step 2.get a specified topic that the producer produce to
        String topic = "dblab";
        Producer<String, String> procuder = new KafkaProducer<String,String>(props);
        for (int i = 1; i <= 10; i++) {
            String value = "value: " + i;
            try {
                Thread.sleep(2000);//sleep 1000ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Create a record with no key[]
            ProducerRecord<String, String> msg = new ProducerRecord<String, String>(topic, value);
            System.out.println(msg);
            procuder.send(msg);
        }

        //step 3.list the information of topic in kafka
        List<PartitionInfo> partitions = new ArrayList<PartitionInfo>() ;

        //partitionsFor(): Get the partition metadata for the given topic. This can be used for custom partitioning.
        partitions = procuder.partitionsFor(topic);
        System.out.println(topic+"的分区信息如下：");
        for(PartitionInfo p:partitions)
        {
            System.out.println(p);
        }

        System.out.println("send message over.");
        procuder.close(100, TimeUnit.MILLISECONDS);
    }

    /*
    1.write call record into kafka through extends Kafka.Client.Producer

     */
    public static void writeCallRecordIntoKafka() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.211.4:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //get an instance of kafkaProducer by props
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        //specific topic been written
        String topic = "dblab";

        for (int i = 0; i < 1000; i++) {
            String value = dataProducer.produce(
                    teleNumber,numberAndName,
                    "2018-01-01","2018-12-12");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(value);
            //don't have key
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic,value);

            //send record
            producer.send(record);
        }
        System.out.println("produce and write are over");
        producer.close();
    }
}

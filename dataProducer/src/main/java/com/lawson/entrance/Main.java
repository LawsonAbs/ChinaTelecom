package com.lawson.entrance;

import com.lawson.data.DataProducer;
import com.lawson.data.KafkaUtils;

import java.util.ArrayList;
import java.util.HashMap;

/*
01. The entrance of data producer
 */
public class Main {
    public static ArrayList<String> teleNumber = new ArrayList<String>();
    public static HashMap<String, String> numberToName = new HashMap<String, String>();
    public static DataProducer dataProducer = new DataProducer();

    public static void main(String[] args) {
        dataProducer.initMetadata(teleNumber,numberToName);
        KafkaUtils.writeCallRecordIntoKafka();
    }

    public static void one(String args[]){
        if(args[0]==null || args[0]==""){
            return;
        }
        DataProducer dataProducer = new DataProducer();
        dataProducer.initMetadata(teleNumber,numberToName);

        //produce the one million data log
        for(int i = 0;i<1000;i++){
            String result = dataProducer.produce(teleNumber,numberToName,"2017-01-01","2017-12-12");

            //you should not use the sleep.
            //but if you use kafka ,you should simulate the produce the call log per second.[But the current project is a
            // OLAP, not a OLTP]
//            try {
//                Thread.sleep(100);//sleep 1500ms
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println(result);
            //the result of output is in args[0]
            dataProducer.writeLog(args[0],result+"\n");
        }
    }
}

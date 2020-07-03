package com.lawson.data;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
1.this class is in order to get the data (analog the data source)
2.the right format is following:
    telephone1 telephone2 date specificTime duration flag
01.telephone1 and telephone2: the telephone number
02.date : the call date
03.specificTime : the call specific time
04.duration : the call duration
05.flag: the flag of first telephone is caller or not

3.the teleNumber is static,so how to avoid using  'static' identifier?
 */
public class DataProducer {
    //teleNumber ： store telephone number;
    //numberToName：telephone number --> name
    //init the metadata
    public void initMetadata(ArrayList<String> teleNumber,HashMap<String,String> numberToName){
        teleNumber.add("17802596779");
        teleNumber.add("18907263863");
        teleNumber.add("19188980695");
        teleNumber.add("13320266126");
        teleNumber.add("19048828124");
        teleNumber.add("13653454072");
        teleNumber.add("13135279938");
        teleNumber.add("18281704261");
        teleNumber.add("17035534749");
        teleNumber.add("19834669675");
        teleNumber.add("19417467461");
        teleNumber.add("19772366326");
        teleNumber.add("18283449398");
        teleNumber.add("16005439091");
        teleNumber.add("14924565399");
        teleNumber.add("14218140347");
        teleNumber.add("17782151215");
        teleNumber.add("17340248510");
        teleNumber.add("19961057493");
        teleNumber.add("19724655139");
        numberToName.put("17802596779","李雁");
        numberToName.put("18907263863","卫艺");
        numberToName.put("19188980695","仰莉");
        numberToName.put("13320266126","陶欣悦");
        numberToName.put("19048828124","施梅梅");
        numberToName.put("13653454072","金虹霖");
        numberToName.put("13135279938","魏明艳");
        numberToName.put("18281704261","华贞");
        numberToName.put("17035534749","华啟倩");
        numberToName.put("19834669675","仲采绿");
        numberToName.put("19417467461","卫丹");
        numberToName.put("19772366326","戚丽红");
        numberToName.put("18283449398","何翠柔");
        numberToName.put("16005439091","钱溶艳");
        numberToName.put("14924565399","钱琳");
        numberToName.put("14218140347","缪静欣");
        numberToName.put("17782151215","焦秋菊");
        numberToName.put("17340248510","吕访琴");
        numberToName.put("19961057493","沈丹");
        numberToName.put("19724655139","褚美丽");
    }

    //produce access log
    public String produce(ArrayList<String> teleNum,HashMap<String,String> numberToName,String startTime,String endTime){
        initMetadata(teleNum,numberToName);
        int callerIndex = (int)(Math.random()*teleNum.size() );//get a random number in order to get a random telephone number
        String callerName = teleNum.get(callerIndex);//get the caller Name
        int calleeIndex;
        do{
            calleeIndex = (int)(Math.random()*teleNum.size());//get a random number
        }
        while(callerIndex == calleeIndex);//if get the same index ,continue
        String calleeName = teleNum.get(calleeIndex);//get the calleeName

        //for example,you could use yyyy-mm-dd,but this pattern represent year-minute-day
        //so the right pattern should be yyyy-MM-dd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//should care the format,or you could meet an error
        //the startTime and endTime only a string,so you should transform them to a variables of Date
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse(startTime);
            endDate = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //get a random distance from start_date to form a date of call.
        //the startDate and endDate are parameters that you ensure the all calls range.
        long randomTs = startDate.getTime() + (long)((endDate.getTime() - startDate.getTime()) * Math.random());
        Date resultDate = new Date(randomTs);
        String resultTimeString = sdf.format(resultDate);

        //the following is in order to get a specific time of call
        int hour = (int) (Math.random() * 24);//get hour
        int minute = (int) (Math.random() * 60);
        int second = (int) (Math.random() * 60);
        //String specificTime = Integer.toString(hour)+":"+Integer.toString(minute)+":"+Integer.toString(second);
        String specificTime = String.format("%02d", hour) +":"+ String.format("%02d", minute)
                +":"+ String.format("%02d", second);

        //The following procedure is to generate a duration of communication
        //the unit is second
        int duration = (int) (Math.random()*3600);//Units are seconds

        //the final call information
        String result = callerName+" "+calleeName+" "+resultTimeString+" "+specificTime+" "+duration ;
        return result;
    }

    //write result to text file
    public void writeLog(String filePath,String result){
        OutputStreamWriter osw = null;
        try {
            //you should use append not override,so you should use FileOutputStream(filePath,true),the true denote append
            osw = new OutputStreamWriter(new FileOutputStream(filePath,true), "utf-8");
            osw.write(result);
            osw.flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

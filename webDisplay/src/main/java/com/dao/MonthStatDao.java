package com.dao;

import com.bean.MonthStat;
import com.utils.MysqlUtils;
import com.utils.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MonthStatDao {
    public List<MonthStat> query(String phoneNumber,String startMonth,String endMonth) {

        String thisMonth = null;
        String firstMonth = null;
        //if startMonth is null,use the first month in this year
       if (startMonth == null) {
        Calendar cale = null;
        cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        firstMonth = Integer.toString(year)+"01";
        startMonth = firstMonth;
         }else{
           startMonth = StringUtils.getYearMonth(startMonth);
       }

         //if endMonth is null,use current month
        if (endMonth == null) {
            Date today = new Date();
            //get the format date => year_month
            SimpleDateFormat sdf = new SimpleDateFormat("yyyymm");
            thisMonth = sdf.format(today);
        } else {
            endMonth = StringUtils.getYearMonth(endMonth);
        }

        String sql = "select yearMonth,callDuration " +
            "from mydatabase.monthStat " +
            "where teleNumber = '"+ phoneNumber +
            "' and yearMonth <'"+endMonth +
            "' and yearMonth >= '"+startMonth +"'";

        System.out.println(sql);
        List<MonthStat> monthStatList = new ArrayList<MonthStat>();
        PreparedStatement stat = MysqlUtils.getPreparedStatement(sql);
        try {
            ResultSet rs = stat.executeQuery();
            MonthStat monthStat;
            while(rs.next()){
                monthStat = new MonthStat();
                monthStat.setYearMonth(rs.getString("yearMonth"));
                monthStat.setCallDuration(rs.getInt("callDuration"));
                monthStatList.add(monthStat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthStatList;
}
}

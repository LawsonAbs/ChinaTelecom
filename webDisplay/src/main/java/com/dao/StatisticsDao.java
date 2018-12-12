package com.dao;

import com.bean.Statistics;
import com.utils.MysqlUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatisticsDao {
    public List<Statistics> query() {
        String sql = "select yearMonth,callDuration from mydatabase.statistics where teleNumber = '18907263863'";
        List<Statistics> statisticsList = new ArrayList<Statistics>();
        PreparedStatement stat = MysqlUtils.getPreparedStatement(sql);
        try {
            ResultSet rs = stat.executeQuery();
            Statistics statistics;
            while(rs.next()){
                statistics = new Statistics();
                statistics.setYearMonth(rs.getString("yearMonth"));
                statistics.setCallDuration(rs.getInt("callDuration"));
                statisticsList.add(statistics);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statisticsList;
    }
}

package com.dao;

import com.bean.Intimacy;
import com.utils.MysqlUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IntimacyDao {
    public List<Intimacy> query(){
        String sql = "select callee ,totalTime from mydatabase.intimacy";
        Intimacy inti =null;
        List<Intimacy> intimacyList = new ArrayList<Intimacy>();
        PreparedStatement pre = MysqlUtils.getPreparedStatement(sql);
        try {
            ResultSet rs = pre.executeQuery();
            while(rs.next()){
                inti = new Intimacy();
                inti.setIntimcayFriend(rs.getString("callee"));
                inti.setTotalTime(rs.getInt("totalTime"));
                intimacyList.add(inti);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return intimacyList;
    }
}

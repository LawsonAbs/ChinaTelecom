package com.dao;

import com.bean.Bar;
import com.utils.MysqlUtils;

import java.sql.*;
import java.util.ArrayList;

public class BarDao {
    public static String sql = "select * from mydatabase.bar";

    public ArrayList<Bar> query() {
        ArrayList<Bar> bars = new ArrayList<Bar>();
        try {
            PreparedStatement stmt = MysqlUtils.getPreparedStatement(sql);
            ResultSet rs = stmt.executeQuery();
            Bar bar;
            while (rs.next()) {
                bar = new Bar();
                /*System.out.println(rs.getString("name"));
                System.out.println(rs.getInt("num"));*/
                bar.setName(rs.getString(1));
                bar.setNum(rs.getInt(2));
                bars.add(bar);
            }
            MysqlUtils.closeResource(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bars;
    }
}

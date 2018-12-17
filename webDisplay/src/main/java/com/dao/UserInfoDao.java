package com.dao;

import com.utils.MysqlUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoDao {

    public String getNameByTeleNumber(String teleNumber ) {
        String tempSql = "select name from user where teleNumber =" + teleNumber;
        PreparedStatement pre = MysqlUtils.getPreparedStatement(tempSql);
        String name = null;
        try {
            ResultSet resultSet = pre.executeQuery();
            while(resultSet.next()){
                name = resultSet.getString("name");
                //System.out.println(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }
}

package com.servlet;


import com.bean.Bar;
import com.dao.BarDao;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class BarService extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BarDao bardao = new BarDao();
        ArrayList<Bar> barList = bardao.query();
        resp.setContentType("text/html;charset=utf-8");
        //ArrayList对象转化为JSON对象
        JSONArray json = JSONArray.fromObject(barList);
        //控制台显示JSON   ->
        System.out.println(json.toString());
        //[{"name":"衬衫","num":5},{"name":"羊毛衫","num":20},{"name":"雪纺衫","num":40}....json数据类型
        //返回到JSP
        PrintWriter writer = resp.getWriter();
        //why following statement is necessary? please read source code!!
        writer.println(json);
        writer.flush();
        //关闭输出流
        writer.close();
    }
}

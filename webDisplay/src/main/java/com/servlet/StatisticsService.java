package com.servlet;


import com.bean.Statistics;
import com.dao.StatisticsDao;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

public class StatisticsService extends HttpServlet{

    /**
     * function:将值传入到jsp页面
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("This is a function named doGet");
        req.setCharacterEncoding("utf-8");
        //切记这里是name属性，id属性是取不到的。
        String  phoneNumber= req.getParameter("phoneNumber");
        String startMonth = req.getParameter("startMonth");
        String endMonth = req.getParameter("endMonth");
        System.out.println("phoneNumber: "+phoneNumber+" startMonth:"+startMonth+" endMonth:"+endMonth);

        StatisticsDao staDao = new StatisticsDao();
        List<Statistics> statisticList =  staDao.query();
        resp.setContentType("text/html;charset=utf-8");
        Collections.sort(statisticList);

        System.out.println("statisticsList.size = "+statisticList.size());
        JSONArray jsonArray = JSONArray.fromObject(statisticList);
        System.out.println(jsonArray.toString());
        System.out.println("----------------");
        PrintWriter writer = resp.getWriter();
        //why following statement is necessary? please read source code!!
        writer.println(jsonArray);
        writer.flush();
        //关闭输出流
        writer.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("This is a function named doGet");
        req.setCharacterEncoding("utf-8");
        //切记这里是name属性，id属性是取不到的。
        String  phoneNumber= req.getParameter("phoneNumber");
        String startMonth = req.getParameter("startMonth");
        String endMonth = req.getParameter("endMonth");
        System.out.println("phoneNumber: "+phoneNumber+" startMonth:"+startMonth+" endMonth:"+endMonth);

        //----------下面这个就是回复点击submit之后的操作----------------------
        //resp.setContentType("text/html;charset=utf-8");
    }
}

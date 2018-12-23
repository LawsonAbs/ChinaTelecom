package com.servlet;


import com.bean.MonthStat;
import com.bean.User;
import com.dao.MonthStatDao;
import com.dao.UserInfoDao;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

public class MonthStatService extends HttpServlet{

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
        /*
        01.A.jsp 页面的变量 -> B.jsp  -> 此servlet。整个过程的实现是怎么样的？
        我这里尝试使用Session 实现。直接使用
        String  phoneNumber= req.getParameter("phoneNumber");
        ...
        String endMonth = req.getParameter("endMonth");
        这种方式是行不通的。
         */
        //切记这里是name属性，id属性是取不到的。

        String  phoneNumber= (String)req.getSession().getAttribute("phoneNumber");
        String startMonth = (String)req.getSession().getAttribute("startMonth");
        String endMonth = (String)req.getSession().getAttribute("endMonth");
        System.out.println("phoneNumber: "+phoneNumber+" startMonth:"+startMonth+" endMonth:"+endMonth +"------MonthStatService");

        //用于计算每个月的通话记录
        MonthStatDao staDao = new MonthStatDao();

        //用于获取用户的信息
        UserInfoDao userInfoDao = new UserInfoDao();
        String name = userInfoDao.getNameByTeleNumber(phoneNumber);
        User user = new User();
        user.setUserName(name);
        req.getSession().setAttribute("user",user);
        System.out.println("userName: "+user.getUserName());

        List<MonthStat> statisticList =  staDao.query(phoneNumber,startMonth,endMonth);
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

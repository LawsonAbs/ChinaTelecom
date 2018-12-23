package com.servlet;

import com.bean.User;
import com.dao.UserInfoDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserInfoService extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("处理get 请求...");
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=utf-8");
        out.println("<strong>Login Servlet</strong>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("处理 post 请求...");
        String phoneNumber = request.getParameter("phoneNumber");
        System.out.println("UserInfoService ...phoneNumber: "+phoneNumber);
        request.setCharacterEncoding("utf-8");
        UserInfoDao userInfoDao = new UserInfoDao();
        String name = userInfoDao.getNameByTeleNumber(phoneNumber);

        User user = new User();
        user.setUserName(name);
        request.getSession().setAttribute("user",user);
        response.setContentType("text/html;charset=utf-8");

        //跳转到注册成功页面-> statisticOne.jsp，同时传递这request 和 response 对象
        System.out.println(request.getPathInfo()+request.getPathTranslated());
        request.getRequestDispatcher("../jsp/statisticOne.jsp").forward(request,response);
        //request.getRequestDispatcher("../monthStat.display").forward(request,response); => 将转发递交给/monthStat.display来处理
        //服务器跳转，也称作请求转发。
    }
}
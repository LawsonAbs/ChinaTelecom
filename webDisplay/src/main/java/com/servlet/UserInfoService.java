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
        String telephone = request.getParameter("phoneNumber");
        request.setCharacterEncoding("utf-8");
        UserInfoDao userInfoDao = new UserInfoDao();
        String name = userInfoDao.getNameByTeleNumber(telephone);

        User user = new User();
        user.setUserName(name);
        response.setContentType("text/html;charset=utf-8");

        //把注册成功的用户对象保存在session中
        request.getSession().setAttribute("user",user);

        //跳转到注册成功页面-> statisticOne.jsp，同时传递这request 和 response 对象
        System.out.println(request.getPathInfo()+request.getPathTranslated());
        request.getRequestDispatcher("../jsp/statisticOne.jsp").forward(request,response);

    }
}
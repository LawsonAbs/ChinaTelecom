package com.servlet;

import com.bean.Intimacy;
import com.dao.IntimacyDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

public class IntimacyService extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf8");
        IntimacyDao intimacyDao = new IntimacyDao();
        List<Intimacy> intimacyList = intimacyDao.query();
        Collections.sort(intimacyList);
        System.out.println("intimacyList.size="+intimacyList.size());
        JSONArray jsonArray = JSONArray.fromObject(intimacyList);
        System.out.println(jsonArray.toString());
        PrintWriter writer = resp.getWriter();
        //why following statement is necessary? please read source code!!
        writer.println(jsonArray);
        writer.flush();
        //关闭输出流
        writer.close();
    }
}

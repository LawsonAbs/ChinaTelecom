<%@ page contentType="text/html;charset=UTF-8"  %>
<html>
<head >
    <meta charset="en">
    <style>
        <!--html, body {. .}这个是设置html, body的样式 -->
        html, body {
            width: 100%;
            height: 100%;
            padding: 0;
            margin: 0;
        }
    </style>

    <style>
        table,td,th
        {
            border:1px solid black;
            text-align:left
        }
        table
        {
            width:35%;
        }
        th
        {
            height:50px;
            text-align:center
        }
    </style>
</head>
<body style=" background:url(../resources/image/backgroud.jpg)" >
<%--  background: #99B898; --%>
<%--超链接 <a href="welcome.jsp">shenliu#ahnu.edu.cn</a>--%>

<div id="content"; style="height:100%; width:100%;text-align: left;">
    <div id = "title">
        <p style="font-weight:600;font-size: 30px; color: #000000;">The Profile of Author</p>
    </div>

    <div id="information">
        The following is my project in this years. I will update this project continually.
        <table style="margin-top: -20px">
            <tr>
                <th colspan="2">Project</th>
            </tr>
            <tr>
                <td rowspan="5">2018</td>
            </tr>
            <tr>
                <td><a style="font-size: 15px;" href="https://github.com/LittleLawson/learnGit">1. Simple Demp of Git </a></td></br>
            </tr>
            <tr>
                <td><a style="font-size: 15px" href="https://github.com/LittleLawson/JavaWebDemo">2. Simple demp of Echart in JSP</a></td></br>
            </tr>
            <tr>
                <td><a style="font-size: 15px" href="https://github.com/LittleLawson/opentsdb-lawson">3. Source code analysis of openTSDB</a></td></br>
            </tr>
            <tr>
                <td><a style="font-size: 15px" href="https://github.com/LittleLawson/ChinaTelecom">4. Bigdata in action</a></td></br>
            </tr>
        </table>
    </div>

    <div id="BigDataInAction" style="margin-top:20px">
        <input style="text-align: center;font-weight: bold;"
               type = "button" value = "Enter the Project" onclick = "window.location.href = '/jsp/callStatistic.jsp'">
    </div>

    <div id ="footer" style="width: 100%;height: 20%;position: absolute;bottom: 10px">
        <ul>
            <li><strong>email:</strong> shenliu#ahnu.edu.cn &nbsp;&nbsp; replace # with @ </li>
            <li><strong>csdn:</strong><a href="https://blog.csdn.net/liu16659" target="_blank">https://blog.csdn.net/liu16659</a></li>
            <li><strong>github:</strong><a href="https://github.com/LittleLawson" target="_blank">https://github.com/LittleLawson</a></li>
        </ul>
    </div>
</div>
</body>
</html>

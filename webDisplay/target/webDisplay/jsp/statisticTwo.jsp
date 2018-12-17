<%--
  Created by IntelliJ IDEA.
  User: enmonster
  Date: 2018/7/3
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="tf8">
    <style>
        <!--html, body {. .}这个是设置html, body的样式-- >
        html, body {
            width: 100%;
            height: 100%;
            padding: 0;
            margin: 0;
        }
    </style>
    <script src="../resources/js/echarts.min.js"></script>
    <script src="../resources/js/jquery-3.3.1.min.js"></script>
</head>
<body>
<div id="content" style="height:100%; width:100%;display: table;">

    <div id="left" style="background: #99B898; width:20%; height:100%; position:relative;top:0;left: 0; display:table-cell;">
        <p style="text-align: center; font-weight: 400;font-size: 30px">The Statistics of Call</p>
        <div id ="footer" style="width: 100%;height: 20%;position: absolute;bottom: 10px">
            <ul style="">
                <li><strong>email:</strong>shenliu@ahnu.edu.cn</li>
                <li><strong>csdn:</strong><a href="https://blog.csdn.net/liu16659" target="_blank">https://blog.csdn.net/liu16659</a></li>
                <li><strong>github:</strong><a href="https://github.com/LittleLawson" target="_blank">https://github.com/LittleLawson</a></li>
            </ul>
        </div>
    </div>

    <div id="right" style="height:100%; width:70%; position: relative;display: table-cell;">
        <div id="top" style="background: #FFFFFF; height: 420px; position: absolute; top:0;left: 0;width: 60%">
            <div id="topLeft" style="position:absolute; height:100%;width: 90%">
            </div>
            <div id="topRight" style="position:absolute;left:500px; height:100%;width: 120%">
            </div>
        </div>
    </div>

</div>


<script type="text/javascript">
    /**
     * loadData()
     * @param option
     * AJAX
     */
    function loadData(option) {
        $.ajax({
            type: 'post',	//传输类型
            async: false,	//同步执行
            url: '/statistics.display',	//web.xml中注册的Servlet的url-pattern
            data: {},
            dataType: 'json', //返回数据形式为json
            success: function (result) {
                if (result) {
                    option.xAxis[0].data = [];//初始化xAxis[0]的data
                    option.series[0].data = [];//初始化series[0]的data
                    for (var i = 0; i < result.length; i++) {
                        option.xAxis[0].data.push(result[i].yearMonth);
                        option.series[0].data.push(result[i].callDuration);
                    }
                }
            },
            error: function (errorMsg) {
                alert("加载数据失败");
            }
        });
    }
/*
01. 根据topLeft 的id，找到这个元素
02. 其后的方法则是 echart 生成图形所必须的
 */
    var myChart = echarts.init(document.getElementById('topLeft'));
    var option = {
        tooltip: [{show: true}],
        legend: {data: ['通话详情']},
        //如下两行是不可改变的。即x轴是种类；y轴是值
        xAxis: [{type: 'category'}],
        yAxis: [{type: 'value'}],
        //you can set this type => line ,bar ,pie, scatter
        series: [{name: '通话详情',type: 'bar'}]
    };
    //加载数据到option  your defined function
    loadData(option);
    //设置option
    myChart.setOption(option);
</script>


<%-- topRight --%>
<script type="text/javascript">

    function loadData(option) {
        $.ajax({
            type: 'post',	//传输类型
            async: false,	//同步执行
            url: 'intimacy.display',	//web.xml中注册的Servlet的url-pattern
            data: {},
            dataType: 'json', //返回数据形式为json
            success: function (result) {
                if (result) {
                    //初始化xAxis[0]的data
                    option.xAxis[0].data = [];
                    option.series[0].data = [];
                    for (var i = 0; i < result.length; i++) {
                        //out.print(result[i]+" ");
                        option.xAxis[0].data.push(result[i].callee);
                        option.series[0].data.push(result[i].totalTime);
                    }
                }
            },
            error: function (errorMsg) {
                alert("加载数据失败");
            }
        });
    }
    var myChart = echarts.init(document.getElementById('topRight'));
    var option = {
        tooltip: [{show: true}],
        legend: [{data: ['亲密度']}],
        //如下两行是不可改变的。即x轴是种类；y轴是值
        xAxis: [{type: 'category'}],
        yAxis: [{type: 'value'}],
        //you can set this type => line ,bar ,pie, scatter
        series: [{name: '亲密度',type: 'bar'}]
    };
    myChart.showLoading();//show Loading...
    loadData(option);//加载数据到option  your defined function
    myChart.hideLoading();//hide Loading...
    myChart.setOption(option);//设置option
</script>

</body>
</html>

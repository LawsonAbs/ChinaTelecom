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
    <meta charset="en">
    <style>
        <!--html, body {. .}这个是设置html, body的样式-- >
        html, body {
            width: 100%;
            height: 100%;
            padding: 0;
            margin: 0;
        }
    </style>
    <script src="resources/js/echarts.min.js"></script>
    <script src="resources/js/jquery-3.3.1.min.js"></script>
    <script src="resources/js/DatePicker.js"></script>
</head>
<body>
<div id="content" style="height:100%; width:100%;display: table;">

    <div id="left" style="background: #99B898; width:40%; height:100%;
            position:relative;top:0;left: 0px; display:table-cell;">
        <div id = "notation" style="left: 3%; position: absolute">
            <p style="text-align: center; font-weight:600;font-size: 30px;color: #000000;">The Statistics of Call</p>
            <p style="background-color: darkcyan;width: 120px; font-size: 18px ">1.Annotation</p>

            <ol class="notation">
                <li>Picture in upper left corner: His/Her  specific call information.</li>
                <li>Picture in upper right corner: His/Her best friend.</li>
                <li>Picture in bottom Left Corner: His/Her best friend</li>
            </ol>

            <p style="background-color: darkcyan;width: 90px; font-size: 18px">2.Options</p>
            <!--
            1.实现点击点击submit不跳转的功能
            2.但是必须满足width=0,height=0这个条件，因为iframe这是一个内联窗口的属性。
            -->
            <iframe width="0px" height="0px" name="actionframe" style="border: none"></iframe>
            <form style=""
                  action="/statistics.getPhoneNumber" method="get" onsubmit="return sumbit_sure()" target="actionframe">
                <br>
                Phone&nbsp;Number:<input name="phoneNumber" style="font-size: 15px" type="text" value="18907263863">
                <br><br>
                Start&nbsp;&nbsp;Month:<input name="startMonth" style="font-size: 15px" type="text" onclick="setmonth(this)">
                <br><br>
                End&nbsp;&nbsp;&nbsp;&nbsp;Month:<input name="endMonth" style="font-size: 15px" type="text" onclick="setmonth(this)">
                <input style="right: 10%;background: #12aff0;color: #000000;font-size: 15px;border: none;cursor:pointer;height: 22px"
                       type="submit" value="Submit">
                <br>
            </form>
        </div>
        <div id ="footer" style="width: 100%;height: 20%;position: absolute;bottom: 10px">
            <ul style="">
                <li><strong>email:</strong>shenliu@ahnu.edu.cn</li>
                <li><strong>csdn:</strong><a href="https://blog.csdn.net/liu16659" target="_blank">https://blog.csdn.net/liu16659</a></li>
                <li><strong>github:</strong><a href="https://github.com/LittleLawson" target="_blank">https://github.com/LittleLawson</a></li>
            </ul>
        </div>
    </div>

    <div id="right" style="height:100%; width:60%; position: relative;display: table-cell;">
        <div id="top" style="background: #FFFFFF; height: 420px; position: absolute; top:0;left: 0;width: 50%">
            <div id="topLeft" style="position:absolute; height:100%;width: 90%">
            </div>
            <div id="topRight" style="position:absolute; left: 552px; width: 90%;height:100%;"><!--#FFA500-->
            </div>
        </div>

        <div id="bottom" style="background: #FFFFFF; height: 420px; position: absolute;top:490px;width: 50%;">
            <div id="bottomLeft" style="position: absolute;height:100%;width:90% ">
            </div>
            <!--width:90%  指的是在父框架的基础上的90%-->
            <div id="bottomRight" style="position: absolute; left: 552px; height:100%;width: 90%;">
            </div>
        </div>
    </div>
</div>

<!--submit sure?-->
<script language="javascript">
    function sumbit_sure(){
        var gnl=confirm("确定要提交?");
        if (gnl==true){
            return true;
        }else{
            return false;
        }
    }
</script>

<!--每月通话详情-->
<script type="text/javascript">
    function loadData(option) {
        $.ajax({
            type: 'post',	//传输类型
            async: false,	//同步执行
            url: 'statistics.display',	//web.xml中注册的Servlet的url-pattern
            data: {},
            dataType: 'json', //返回数据形式为json
            success: function (result) {
                if (result) {
                    //初始化xAxis[0]的data
                    option.xAxis[0].data = [];
                    for (var i = 0; i < result.length; i++) {
                        option.xAxis[0].data.push(result[i].yearMonth);
                    }
                    //初始化series[0]的data
                    option.series[0].data = [];
                    for (var i = 0; i < result.length; i++) {
                        option.series[0].data.push(result[i].callDuration);
                    }
                }
            },
            error: function (errorMsg) {
                alert("加载数据失败");
            }
        });
    }
    var myChart = echarts.init(document.getElementById('topLeft'));
    myChart.showLoading();
    var option = {
        tooltip: {show: true},
        legend: {data: ['每月通话详情']},
        //如下两行是不可改变的。即x轴是种类；y轴是值
        xAxis: [{type: 'category'}],
        yAxis: [{type: 'value'}],
        //you can set this type => line ,bar ,pie, scatter
        series: [{name: '每月通话详情',type: 'bar'}],
        textStyle: {
            fontSize: 18,
            fontWeight: 'bolder',
            color: '#333'          // 主标题文字颜色
        }
    };
    //加载数据到option  your defined function
    loadData(option);
    myChart.hideLoading();
    //设置option
    myChart.setOption(option);
</script>

<!--好友亲密度-->
<script type="text/javascript">
    function loadOneColumn() {
        var myChart = echarts.init(document.getElementById('topRight'));
        // 显示标题，图例和空的坐标轴
        myChart.setOption({
            color: ['#ff7d27', '#47b73d', '#fcc36e', '#57a2fd', "#228b22"],//饼图颜色
            title: {
                text: '好友亲密度',
                //subtext: '纯属虚构',  //子标题，这里使用不到
                x:'center',
                textStyle: {
                    fontSize: 18,
                    fontWeight: 'bolder',
                    color: '#333'          // 主标题文字颜色
                },
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                x: 'left'
            },
            toolbox: {
                show: true,
                feature: {
                    mark: { show: true },
                    dataView: { show: true, readOnly: false },
                    magicType: {
                        show: true,
                        type: ['pie', 'funnel'],
                        option: {
                            funnel: {
                                x: '25%',
                                width: '50%',
                                funnelAlign: 'left',
                                max: 1548
                            }
                        }
                    },
                    restore: { show: true },
                    saveAsImage: { show: true }
                }
            },
            series: [{
                name: '好友亲密度',
                type: 'pie',
                radius: '55%',
                center: ['50%','50%'],
            }]
        });
        myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
        var names = [];    //类别数组（用于存放饼图的类别）
        var value = [];
        $.ajax({
            type: 'post',
            url: 'intimacy.display',//请求数据的地址
            dataType: "json",        //返回数据形式为json
            success: function (result) {
                if (result) {//循环取出result中的值
                    for (var i = 0; i < result.length; i++) {
                        names.push(result[i].intimcayFriend);//挨个取出类别并填入类别数组
                        value.push({
                            name: result[i].intimcayFriend,
                            value: result[i].totalTime
                        });
                    }
                }
                myChart.hideLoading();    //隐藏加载动画
                myChart.setOption({        //加载数据图表
                    legend: {data: names},//设置图例  -->就是解释图的每一块是啥
                    series: [{data: value}]
                });
            },
            error: function (errorMsg) {
                //请求失败时执行该函数
                alert("加载数据失败!");
            }
        });
    };
loadOneColumn();
</script>


<script type="text/javascript">
    function loadData(option) {
        $.ajax({
            type: 'post',	//传输类型
            async: false,	//同步执行
            url: 'bar.display',	//web.xml中注册的Servlet的url-pattern
            data: {},
            dataType: 'json', //返回数据形式为json
            success: function (result) {
                if (result) {
                    //初始化xAxis[0]的data
                    option.xAxis[0].data = [];
                    for (var i = 0; i < result.length; i++) {
                        option.xAxis[0].data.push(result[i].name);
                    }
                    //初始化series[0]的data
                    option.series[0].data = [];
                    for (var i = 0; i < result.length; i++) {
                        option.series[0].data.push(result[i].num);
                    }
                }
            },
            error: function (errorMsg) {
                alert("加载数据失败");
            }
        });
    }
    var myChart = echarts.init(document.getElementById('bottomLeft'));
    myChart.showLoading();
    var option = {
        tooltip: {show: true},
        legend: {data: ['销量']},
        xAxis: [{type: 'category'}],
        yAxis: [{type: 'value'}],
        series: [{name: '销量', type: 'bar'}],
        textStyle: {
            fontSize: 18,
            fontWeight: 'bolder',
        }
    };
    myChart.hideLoading();
    loadData(option);
    myChart.setOption(option);
</script>
<script type="text/javascript">
    function loadData(option) {
        $.ajax({
            type: 'post',	//传输类型
            async: false,	//同步执行
            url: 'bar.display',	//web.xml中注册的Servlet的url-pattern
            data: {},
            dataType: 'json', //返回数据形式为json
            success: function (result) {
                if (result) {
                    //初始化xAxis[0]的data
                    option.xAxis[0].data = [];
                    for (var i = 0; i < result.length; i++) {
                        option.xAxis[0].data.push(result[i].name);
                    }
                    //初始化series[0]的data
                    option.series[0].data = [];
                    for (var i = 0; i < result.length; i++) {
                        option.series[0].data.push(result[i].num);
                    }
                }
            },
            error: function (errorMsg) {
                alert("加载数据失败");
            }
        });
    }
    var myChart = echarts.init(document.getElementById('bottomRight'));
    myChart.showLoading();
    var option = {
        tooltip: {show: true},
        legend: {data: ['销量']},
        xAxis: [{type: 'category'}],
        yAxis: [{type: 'value'}],
        series: [{name: '销量',type: 'line'}],
        textStyle: {
            fontSize: 18,
            fontWeight: 'bolder',
            color: '#008'          // 主标题文字颜色
        },
    };
    //hideLoading must set before your loadData
    myChart.hideLoading();
    loadData(option);
    myChart.setOption(option);
</script>
</body>
</html>

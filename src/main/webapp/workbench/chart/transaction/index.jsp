<%--
  Created by IntelliJ IDEA.
  User: 11025
  Date: 2021/4/10
  Time: 23:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
    需求：
        根据交易列表中的不同的阶段的数量进行一个统计，最终形成一个漏斗图
--%>
<%
    String basePath=request.getScheme()+"://"+
            request.getServerName()+":"+request.getServerPort()+
            request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
    <script type="text/javascript" src="ECharts/echarts.min.js"></script>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>

    <script>

        $(function(){

            //页面加载完毕后绘制统计图表
            getCharts();
        })

        function getCharts(){

            $.ajax({
                url:"workbench/transaction/getCharts.do",
                type:"get",
                dataType: "json",
                success:function(data){

                    /*
                        data
                            {total:总条数,dataList:[{stage分组1（map）},{stage分组2}]}
                     */
                    var stages=[];
                    $.each(data.dataList,function(i,n){
                        stages.push(n.name);
                    })

                    // 基于准备好的dom，初始化echarts实例
                    var myChart = echarts.init(document.getElementById('main'));

                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: '交易漏斗图',
                            subtext: '统计交易各阶段的数量'
                        },
                        legend: {
                            data: stages
                        },

                        series: [
                            {
                                name:'漏斗图',
                                type:'funnel',
                                left: '10%',
                                top: 60,
                                //x2: 80,
                                bottom: 60,
                                width: '80%',
                                // height: {totalHeight} - y - y2,
                                min: 0,
                                max: data.total,
                                minSize: '0%',
                                maxSize: '100%',
                                sort: 'descending',
                                gap: 2,
                                label: {
                                    show: true,
                                    position: 'inside'
                                },
                                labelLine: {
                                    length: 10,
                                    lineStyle: {
                                        width: 1,
                                        type: 'solid'
                                    }
                                },
                                itemStyle: {
                                    borderColor: '#fff',
                                    borderWidth: 1
                                },
                                emphasis: {
                                    label: {
                                        fontSize: 20
                                    }
                                },
                                data: data.dataList
                            }
                        ]
                    };

                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            })



        }
    </script>
</head>
<body>
    <div id="main" style="width: 1000px;height:400px;"></div>
</body>
</html>

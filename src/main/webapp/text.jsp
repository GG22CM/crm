<%--
  Created by IntelliJ IDEA.
  User: 11025
  Date: 2021/3/27
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+
            request.getServerName()+":"+request.getServerPort()+
            request.getContextPath()+"/";
%>
<html>
<head>

    <title>Title</title>
    <base href="<%=basePath%>"/>
</head>
<body>

//创建时间
String createTime= DateTimeUtil.getSysTime();
//创建人
String createBy=((User) request.getSession().getAttribute("user")).getName();


$.ajax({
url:"",
data:{

},
type:"post",
dataType: "json",
success:function(data){

}
})


//日历控件--》下次联系时间
$(".time").datetimepicker({
minView: "month",
language:  'zh-CN',
format: 'yyyy-mm-dd',
autoclose: true,
todayBtn: true,
pickerPosition: "top-left"
});



</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String basePath=request.getScheme()+"://"+
	request.getServerName()+":"+request.getServerPort()+
	request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>"/>
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script>
		$(function (){

			//重启服务器时将可能出现的登陆页放置到顶层窗口
			if(window.top!=window){
				window.top.location=window.location;
			}
			//页面加载完毕后，将用户文本框中的内容清空
			$("#loginAct").val("");

		//	在页面加载完毕后让用户文本框获得焦点
			$("#loginAct").focus();

			//为登陆按钮绑定事件执行登陆操作
			$("#submitBtn").click(function(){
				//	alert();
				login()
			})

			//为当前登陆也窗口绑定敲键盘事件
			$(window).keydown(function(event){
				//alert(event.keyCode);
				if(event.keyCode==13){
					//执行登陆验证操作
					login()
				}
			})
		})

		//验证登陆操作使用的方法
		function login(){
			//alert();

			//验证账号密码不能为空
			//取得账号密码
			var loginAct=$.trim($("#loginAct").val());
			var loginPwd=$.trim($("#loginPwd").val());

			if(loginAct=="" || loginPwd==""){
				$("#msg").html("账号密码不能为空");
				//用高级的方法阻止方法继续
				return false;
			}

			//去后台验证登陆相关操作
			$.ajax({
				url:"settings/user/login.do",
				data:{
					"loginAct":loginAct,
					"loginPwd":loginPwd
				},
				type:"post",
				dataType:"json",
				success:function(data){
					/*

						data
							{"success":true/false,"msg":"错误信息"}
					 */
					if(data.success){
						//登陆成功，跳转到工作台初始页
						window.location.href="workbench/index.jsp";
					}else{
						//登陆失败提示信息
						$("#msg").html(data.msg);
					}
				}
			})



		}
	</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.jsp" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" type="text" placeholder="用户名" id="loginAct">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" type="password" placeholder="密码" id="loginPwd">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						
							<span id="msg" style="color: red"></span>
						
					</div>
					<button type="button" id="submitBtn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
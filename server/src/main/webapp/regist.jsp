<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/common/jstl.jsp" %> 
<%@ include file="/common/basePath.jsp" %>    
<!DOCTYPE html>
<html style="height:100%;">
    <head>
    	<base href="<%=basePath%>">
        <meta charset="UTF-8">
        <title>注册</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <link href="assets/adminlte/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/adminlte/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/adminlte/css/AdminLTE.css" rel="stylesheet" type="text/css" />
        <style type="text/css">
        	.background{
        		margin: 0px; padding: 0px; height: 100%; 
        		background: url(https://dn-coding-net-production-static.qbox.me/9b8e717d-9795-4d54-b687-931aea15afb6.jpg) 50% 50% / cover no-repeat fixed;
        	}
         </style>
    </head>
    <body class="background">
        <div class="form-box" id="login-box">
            <div class="header">注册</div>
            <form action="doRegist" method="post" onsubmit="return doreg(this);">
                <div class="body bg-gray">
                	<c:if test="${!empty msg }">
                	<p class="alert-danger" id="msg" style="padding:10px;">${msg}</p>
                    </c:if>
                    <c:if test="${empty msg }">
                    	<p class="alert-danger" id="msg" style="padding:10px;display: none"></p>
                   	</c:if>
                    <div class="form-group">
                        <input type="text" id="username" name="username" value="${username}" class="form-control" placeholder="请输入用户名"/>
                    </div>
                    <div class="form-group">
                        <input type="password" id="password" name="password" class="form-control" placeholder="请输入密码"/>
                    </div>          
                    <div class="form-group">
                        <input type="password" id="confirmPassword" class="form-control" placeholder="请再次输入密码"/>
                    </div>          
                </div>
                <div class="footer">                                                               
                    <button type="submit" id="regBtn" class="btn bg-olive btn-block">注册</button>  
                    <p class="text-center">
                    	<a href="login">已有账号，立即登录</a>
                    </p>
                </div>
            </form>
        </div>
        <script src="assets/adminlte/js/jquery.min.js"></script>
        <script src="assets/adminlte/js/bootstrap.min.js" type="text/javascript"></script>   
        <script src="http://apps.bdimg.com/libs/crypto-js/3.1.2/rollups/sha1.js"></script>
        <script type="text/javascript">
        function doreg(form){
    		var username = $.trim($("#username").val());
    		var password = $.trim($("#password").val());
    		var confirmPassword = $.trim($("#confirmPassword").val());
    		if(username == ''){
    			$("#msg").html("请输入用户名").show();
    			$("#username").focus();
    			return false;
    		}
    		if(password == ''){
    			$("#msg").html("请输入密码").show();
    			$("#password").focus();
    			return false;
    		}
    		if(confirmPassword == ''){
    			$("#msg").html("请输入密码").show();
    			$("#confirmPassword").focus();
    			return false;
    		}
    		if(confirmPassword != password){
    			$("#msg").html("两次输入密码不一致").show();
    			$("#confirmPassword").focus();
    			return false;
    		}
    		var pwd = CryptoJS.SHA1(password).toString();
    		$("#regBtn").button('loading');  
    		$.ajax({
   			   type:'post',
   			   url:'doRegist',
   			   data:{username:username,password:pwd},
   			   dataType:'json',
   			   success:function(json){
   				   if(json.code == 200){
   					   alert("注册成功");
   					   window.location.href="login";
   					   return;
   				   }
   				   $("#msg").html(json.msg).show();
   				   $("#regBtn").button('reset'); 
   			   },
   			   error:function(e){
   				   $("#regBtn").button('reset'); 
   				   alert('网络出现异常了哦，请重试.');
   			   }
   		   });
    		return false;
    	}
        </script>     
    </body>
</html>
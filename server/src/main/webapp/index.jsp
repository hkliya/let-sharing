<%@page import="org.junit.experimental.categories.Categories.IncludeCategory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/jstl.jsp" %>      
<%@ include file="/common/basePath.jsp" %>      
<!DOCTYPE html>
<html>
    <head>
    	<base href="<%=basePath%>">
        <meta charset="UTF-8">
        <title>乐享-管理台</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        
        <link href="assets/adminlte/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/adminlte/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/adminlte/css/AdminLTE.css" rel="stylesheet" type="text/css" />
        <link href="assets/adminlte/css/ionicons.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/adminlte/css/_all-skins.min.css" rel="stylesheet" type="text/css" />
        
        <link href="assets/adminlte/css/datepicker/datepicker3.css" rel="stylesheet" type="text/css" />
        <link href="assets/adminlte/css/timepicker/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/adminlte/css/dialog/bootstrap-dialog.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/ajax/nprogress.css" rel="stylesheet" type="text/css" />

        <style type="text/css">
        .modal-lg{width: 800px;}
        .modal-dialog-85 .modal-content{height: 500px;}
        .q-tree .panel-body{height: 450px !important;}
        .loading {
		  width: 60px;
		  height: 60px;
		  background-color: #00c0ef;
		 
		  margin: 100px auto;
		  -webkit-animation: rotateplane 1.2s infinite ease-in-out;
		  animation: rotateplane 1.2s infinite ease-in-out;
		}
		 
		@-webkit-keyframes rotateplane {
		  0% { -webkit-transform: perspective(120px) }
		  50% { -webkit-transform: perspective(120px) rotateY(180deg) }
		  100% { -webkit-transform: perspective(120px) rotateY(180deg)  rotateX(180deg) }
		}
		 
		@keyframes rotateplane {
		  0% {
		    transform: perspective(120px) rotateX(0deg) rotateY(0deg);
		    -webkit-transform: perspective(120px) rotateX(0deg) rotateY(0deg)
		  } 50% {
		    transform: perspective(120px) rotateX(-180.1deg) rotateY(0deg);
		    -webkit-transform: perspective(120px) rotateX(-180.1deg) rotateY(0deg)
		  } 100% {
		    transform: perspective(120px) rotateX(-180deg) rotateY(-179.9deg);
		    -webkit-transform: perspective(120px) rotateX(-180deg) rotateY(-179.9deg);
		  }
		}
		
		/*=====*/
		.skin-blue .sidebar {
  border-bottom: 1px solid #333;
}
.skin-blue .sidebar > .sidebar-menu > li {
  border-top: 1px solid #333;
  border-bottom: 0px solid #444;
}
.skin-blue .sidebar > .sidebar-menu > li:first-of-type {
  border-top: 1px solid #444;
}
.skin-blue .sidebar > .sidebar-menu > li:first-of-type > a {
  border-top: 0px solid #333;
}
.skin-blue .sidebar > .sidebar-menu > li > a {
  margin-right: 1px;
}
.skin-blue .sidebar > .sidebar-menu > li > a:hover,
.skin-blue .sidebar > .sidebar-menu > li.active > a {
  color: #f6f6f6;
  background: #3c763d;
}
.skin-blue .sidebar > .sidebar-menu > li > .treeview-menu {
  margin: 0 1px;
  background: #444;
}
.skin-blue .left-side {
  background: #333;
}
.skin-blue .sidebar a {
  color: #eee;
}
.skin-blue .sidebar a:hover {
  text-decoration: none;
}
.skin-blue .treeview-menu > li > a {
  color: #ccc;
}
.skin-blue .treeview-menu > li.active > a,
.skin-blue .treeview-menu > li > a:hover {
  color: #444;
}
.skin-blue .sidebar-form {
  -webkit-border-radius: 2px;
  -moz-border-radius: 2px;
  border-radius: 2px;
  border: 0px solid #555;
  margin: 10px 10px;
}
.skin-blue .sidebar-form input[type="text"],
.skin-blue .sidebar-form .btn {
  box-shadow: none;
  background-color: rgba(255, 255, 255, 0.1);
  border: 0 solid rgba(255, 255, 255, 0.1);
  height: 35px;
  outline: none;
}
.skin-blue .sidebar-form input[type="text"] {
  color: #666;
  -webkit-border-top-left-radius: 2px !important;
  -webkit-border-top-right-radius: 0 !important;
  -webkit-border-bottom-right-radius: 0 !important;
  -webkit-border-bottom-left-radius: 2px !important;
  -moz-border-radius-topleft: 2px !important;
  -moz-border-radius-topright: 0 !important;
  -moz-border-radius-bottomright: 0 !important;
  -moz-border-radius-bottomleft: 2px !important;
  border-top-left-radius: 2px !important;
  border-top-right-radius: 0 !important;
  border-bottom-right-radius: 0 !important;
  border-bottom-left-radius: 2px !important;
}
.skin-blue .sidebar-form input[type="text"]:focus,
.skin-blue .sidebar-form input[type="text"]:focus + .input-group-btn .btn {
  background-color: #444;
  border: 0;
}
.skin-blue .sidebar-form input[type="text"]:focus + .input-group-btn .btn {
  border-left: 0;
}
.skin-blue .sidebar-form .btn {
  color: #999;
  -webkit-border-top-left-radius: 0 !important;
  -webkit-border-top-right-radius: 2px !important;
  -webkit-border-bottom-right-radius: 2px !important;
  -webkit-border-bottom-left-radius: 0 !important;
  -moz-border-radius-topleft: 0 !important;
  -moz-border-radius-topright: 2px !important;
  -moz-border-radius-bottomright: 2px !important;
  -moz-border-radius-bottomleft: 0 !important;
  border-top-left-radius: 0 !important;
  border-top-right-radius: 2px !important;
  border-bottom-right-radius: 2px !important;
  border-bottom-left-radius: 0 !important;
  border-left: 0;
}
		
        </style>
        
        <script src="assets/adminlte/js/jquery.min.js"></script>
    </head>
    <body class="skin-blue">
        <!-- header logo: style can be found in header.less -->
        <header class="header">
            <a href="javascript:void(0);" class="logo">
               乐享
            </a>
            <!-- Header Navbar: style can be found in header.less -->
            <nav class="navbar navbar-static-top" role="navigation">
                <!-- Sidebar toggle button-->
                <a href="javascript:void(0);" class="navbar-btn sidebar-toggle" data-toggle="offcanvas" role="button">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </a>
                <div class="navbar-right">
                    <ul class="nav navbar-nav">
                        <!-- User Account: style can be found in dropdown.less -->
                        <li class="dropdown user user-menu">
                            <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="glyphicon glyphicon-user"></i>
                                <span><shiro:principal property="username"/><i class="caret"></i></span>
                            </a>
                            <ul class="dropdown-menu">
                                <!-- User image -->
                                <li class="user-header bg-light-blue">
                                    <img src="<shiro:principal property="avatar"/>" class="img-circle" alt="User Image" />
                                    <p><shiro:principal property="username"/></p>
                                </li>
                                <li><a href="logout" class="btn btn-default btn-flat">退出</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>
        
        <div class="wrapper row-offcanvas row-offcanvas-left">
            <!-- Left side column. contains the logo and sidebar -->
            <aside class="left-side sidebar-offcanvas">                
                <!-- sidebar: style can be found in sidebar.less -->
                <section class="sidebar">
                    <!-- Sidebar user panel -->
                    <div class="user-panel">
                        <div class="pull-left image">
                            <img src="<shiro:principal property="avatar"/>" class="img-circle" alt="User Image" />
                        </div>
                        <div class="pull-left info">
                            <p style="color:white"><shiro:principal property="username"/></p>
                            <small style="color:white">欢迎回来</small>
                        </div>
                    </div>
                    <!-- sidebar menu: : style can be found in sidebar.less -->
                    <ul class="sidebar-menu">
	                        <li>
	                            <a href="javascript:void(0);" onclick="javascript:changeMain('buyList','main')">
	                                <i class="fa fa-info"></i> <span>我的宝贝</span>
	                            </a>
	                        </li>
                        
                    </ul>
                </section>
                <!-- /.sidebar -->
            </aside>
            
            
            <!-- Right side column. Contains the navbar and content of the page -->
            <aside class="right-side" id="main">                
                
            </aside>
            
        </div><!-- ./wrapper -->
        
        <script src="assets/adminlte/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="assets/adminlte/js/AdminLTE/app.js" type="text/javascript"></script>
        
        <script src="assets/ajax/jquery.ajaxsubmit.js" type="text/javascript"></script>
        <script src="assets/ajax/nprogress.js" type="text/javascript"></script>
        <script src="assets/ajax/Ajax.js" type="text/javascript"></script>
        
        
        <script type="text/javascript">
        function changeMain(url,rel){
        	$(".sidebar-menu > li").removeClass("active");
            $(this).parent().addClass("active");
        	Ajax.getHtml(url, rel);
        }    
        $(function(){
	        $(".sidebar-menu > li").click(function(){
	            $(".sidebar-menu > li").removeClass("active");
	            $(this).addClass("active");
	        });
        });
        
        
        UI.init();
    	UI.initLoadingBar();
    	changeMain('buyList','main');
        </script>
        
        <script src="assets/adminlte/js/plugins/datepicker/bootstrap-datepicker.js" type="text/javascript"></script>
        <script src="assets/adminlte/js/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js" type="text/javascript"></script>
        <script src="assets/adminlte/js/plugins/timepicker/bootstrap-timepicker.min.js" type="text/javascript"></script>
        <script src="assets/adminlte/js/plugins/dialog/bootstrap-dialog.min.js" type="text/javascript"></script>
        <script src="assets/adminlte/js/plugins/tree/bootstrapQ.js" type="text/javascript"></script>
        <script src="assets/ajax/validator.min.js" type="text/javascript"></script>
    </body>
</html>
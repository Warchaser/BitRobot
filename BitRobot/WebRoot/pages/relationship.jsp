<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>关系视图</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
	<script type="text/javascript" src="js/3d_miaov.js"></script>
	<link rel="stylesheet" type="text/css" href="css/3d_miaov_style.css"/>
  </head>
  
  <body>
    <div id="main" class="main">
	<div id="div2"> <img style=" width:100px; height:100px; border-radius:50px; margin-top:20%;  margin-left:40%; "  src="images/mengxiaofeng.jpg"/></div>
	<div id="div1">
	</div>
	</div>
  </body>
</html>

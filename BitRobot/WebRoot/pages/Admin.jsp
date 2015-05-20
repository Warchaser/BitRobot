<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>管理员视图</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  <body>
    <a href="pages/AdminAddExpertInfo.jsp" target="_blank">添加专家</a><br>
    <a href="pages/AdminAddExpertPatent.jsp" target="_blank">添加专家专利</a><br>
    <a href="pages/AdminAddExpertReward.jsp" target="_blank">添加专家获奖</a><br>
    <a href="pages/AdminAddExpertRelationship.jsp" target="_blank">添加专家关系</a><br>
  </body>
</html>

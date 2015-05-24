<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" import="java.util.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page import = "bean.ExpertInfoBean"%>
<% 	
  	ArrayList<ExpertInfoBean> expertList = (ArrayList<ExpertInfoBean>)application.getAttribute("expertInfoList");
	int expertListSize = expertList.size();
	String name = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


  	<script language="javascript" type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
	<link href="../js/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css"> 
    <base href="<%=basePath%>">
    <title>添加专家成果</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
</head>
  
<body>
  	<form action="servlet/AdminAddExpertAchive" method="post">
  		<table border="1" align="center">
  			<tr>
  				<th align="center" style="font-size:150%;color:red" colspan="4">添加专家研究成果</th>
  			</tr>
  			<tr>
  				<td align="center">选择专家</td>
  				<td>
	  				<select id="id_expertOptions" name="expertOptions">
					     <% for(int i = 0;i < expertListSize;i++){%>
					     <% name = expertList.get(i).getExpertName();%>
					     	<option value="<%=name%>"><%=name%></option>
						 <%} %>
					</select>
				</td>
             </tr>
             <tr>   
				<td align="center">研发日期</td>
				<td>
					<input type="text"  style="width:30%" id="id_firstdate" name="firstdate" "> --  <input type="text"   style="width:30%"id="id_seconddate" name="seconddate" ">
				</td>
			</tr>
			<tr>
				<td align="center">成果名称</td>
				<td colspan="3"><input type="text" style="width:100%" id="id_achive_name" name="achive_name"></td>
			</tr>
			<tr height="30px">
	  			<td align="center" colspan="4">
	  				<input type="submit" id="id_submit" style="width:67px;height:30px" value="提交">
	  				<input type="reset" id="id_reset" style="width:67px;height:30px" value="重置">
	  			</td>
	  		</tr>
  		</table>
  	</form>
  </body>
</html>

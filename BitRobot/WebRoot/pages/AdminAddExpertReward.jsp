<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<script language="javascript" type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
	<link href="../js/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css"> 
    <base href="<%=basePath%>">
    <title>添加专家获奖</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
	<script type="text/javascript" src="js/admin.js"></script>
  </head>
  
  <body>
  	<form action="servlet/AdminAddExpertReward" method="post" onsubmit="return isNull()">
  		<table border="1" align="center">
  			<tr>
  				<th align="center" style="font-size:150%;color:red" colspan="4">添加专家专利</th>
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
				<td align="center">获奖时间</td>
				<td>
					<input type="text" id="id_date" name="date" class="Wdate" onclick="WdatePicker()">
				</td>
			</tr>
			<tr>
				<td align="center">获奖名称</td>
				<td colspan="3"><input type="text" id="id_reward_name" name="reward_name" style="width:100%" class="cant_be_null"></td>
			</tr>
			<tr>
				<td align="center">颁发机构</td>
				<td colspan="3"><input type="text" id="id_org" name="org" style="width:100%"></td>
			</tr>
			<tr>
				<td align="center">合作伙伴</td>
				<td colspan="3"><input type="text" id="id_co_partner" name="co_partner" style="width:100%"></td>
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

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
    <base href="<%=basePath%>">
    <title>添加专家论文</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
	<script type="text/javascript" src="js/admin.js"></script>
  <body>
  	<form action="servlet/AdminAddExpertPaper" method="post" onsubmit="return isNull()">
  		<table border="1" align="center">
  			<tr>
  				<th align="center" style="font-size:150%;color:red" colspan="4">添加专家论文</th>
  			</tr>
  			<tr>
  				<td align="center">选择专家</td>
  				<td colspan="3">
	  				<select id="id_expertOptions" name="expertOptions" style="width:100%">
					     <% for(int i = 0;i < expertListSize;i++){%>
					     <% name = expertList.get(i).getExpertName();%>
					     	<option value="<%=name%>"><%=name%></option>
						 <%} %>
					</select>
				</td>
  			</tr>
  			<tr>
  				<td align="center">论文题目</td>
  				<td><input type="text" id="id_paper_name" name="paper_name" class="cant_be_null" style="width:100%"></td>
  				<td align="center">专家所属机构</td>
  				<td><input type="text" id="id_org" name="org" style="width:100%"></td>
  			</tr>
  			<tr>
  				<td align="center">论文摘要</td>
  				<td colspan="3"><textarea id="id_abs" name="abs" style="width:100%"></textarea></td>
  			</tr>
  			<tr>
  				<td align="center">关键词</td>
  				<td><input type="text" id="id_guanjianci" name="guanjianci" style="width:100%"></td>
  				<td align="center">项目基金</td>
  				<td><input type="text" id="id_fundation" name="fundation" style="width:100%"></td>
  			</tr>
  			<tr>
  				<td align="center">论文链接</td>
  				<td colspan="3"><input type="text" id="id_url" name="url" style="width:100%"></td>
  			</tr>
  			<tr>
				<td align="center">论文相关作者</td>
				<td colspan="3"><input type="text" id="id_author_cn" name="author_cn" style="width:100%"></td>
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

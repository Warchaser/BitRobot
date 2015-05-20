<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>添加专家</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
	<script type="text/javascript" src="js/admin.js"></script>
  </head>
  <body>
    <form action="servlet/AdminAddExpertInfo" method="post" onsubmit="return isNull()">
    	<table border="1" align="center">
    		<tr>
    			<th align="center" style="font-size:150%;color:red" colspan="4">添加专家</th>
    		</tr>
    		<tr>
    			<td align="center">专家姓名</td>
    			<td><input type="text" id="id_expert_name" name="expert_name" class="cant_be_null"></td>
    			<td align="center">性别</td>
    			<td>
	    			<input type="radio" id="id_gender_male" name="gender" value="男" checked>男
	    			<input type="radio" id="id_gender_female" name="gender" value="女">女
    			</td>
    		</tr>
    		<tr>
    			<td align="center">职务</td>
    			<td colspan="3"><input type="text" style="width:100%" id="id_job_position" name="job_position"></td>
    		</tr>
    		<tr>
    			<td align="center">参与过的项目</td>
    			<td colspan="3"><input type="text" style="width:100%" id="id_project" name="project"></td>
    		</tr>
    		<tr>
    			<td align="center">从业方向</td>
    			<td colspan="3"><input type="text" style="width:100%" id="id_employment_direction" name="employment_direction"></td>
    		</tr>
    		<tr>
    			<td align="center">现任所在机构</td>
    			<td colspan="3"><input type="text" style="width:100%" id="id_org" name="org"></td>
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

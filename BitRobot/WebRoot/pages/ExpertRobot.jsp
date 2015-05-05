<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; utf-8">
		<title>Bit Robot</title>
		<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
		<script type="text/javascript" src="js/message.js"></script>
		<link rel="stylesheet" type="text/css" href="css/message.css"/>
	</head>
	<body onLoad="loadExpertList()">
		<div class="mainDiv">
			<div class="leftDiv">
				<span>
					<h3>专家列表</h3>
				</span>
				<div class="showDiv" id="expertDiv">
	                <ul>
	                </ul>
				</div>
			</div>
			<div class="rightDiv">
				<div class="righttopDiv">
					<span>
						<h3>机器人<%=request.getRemoteAddr()%></h3>
						<input type="button" value="X" class="exitButton" id="exitButton"/>
					</span>
					<div class="showDiv" id="messageDiv"></div>
				</div>
				<div class="rightbottomDiv">
       		 		 <textarea name="contentText" id="contentText" cols="64" rows="3" class="contentText"></textarea>
        			 <input type="button" value="发送" class="sendButton" id="sendButton"/>
        			 <span id="messageSpan" class="tipSpan"></span>
				</div>
			</div>
			
		</div>
	</body>
</html>
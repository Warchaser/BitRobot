/**
 * Created by GearBox on 2015/03/25.
 */
$(function () {
    $("#sendButton").click(function () {  //绑定发送按钮的点击事件
        var content =  $.trim($("#contentText").val());//获取输入框的内容
        if ("" == content || "\n" == content) {
            $("#messageSpan").show().html("空消息").fadeOut(2000);
            $("#contentText").val('');
            return;
        }

//        var preContent = "<pre>" + content + "</pre>";
        sendMessage(content);
    });

    $(document).on("click", ".mainDiv .rightDiv .righttopDiv #onlineDiv ul li div", function () {
        $(".mainDiv .rightDiv .righttopDiv #onlineDiv ul li div:contains('聊天对象') label:last").remove();
        $(this).append("<label>  (聊天对象)</label>");
    });

   
    $("#contentText").keydown(function(e){
        if (13 == e.which)
            $("#sendButton").trigger("click");
    });

    
});

function loadExpertList() {   //像服务器发送新的消息
	
	console.log("loadExpertList");
	
    $.ajax({
        type: "post",
        cache: false,
        url: "servlet/LoadExpertListOnInit",
//        data: "content=" + content + "sender=" + sender,
        data: "",
        dataType:"json",
        success: function (data) {
//            if (1 == data.sendResult) {
//            	addNewMsgByServer(data.sendTime,data.content);
//                $("#messageSpan").html("已发送").fadeOut(2000);
//            }
//            else {
//                $("#messageSpan").html("发送失败").fadeOut(2000);
//            }
        },
        error: function () {
//            alert("服务器连接错误");
//            $("#messageSpan").html("发送失败").fadeOut(2000);
        }
    });
}

function sendMessage(content) {   //像服务器发送新的消息
    $("#messageSpan").show().html("发送中");
    $("#contentText").val("");
    addNewMsgRightByUser(content);
    $.ajax({
        type: "post",
        cache: false,
        url: "servlet/SendQuery",
//        data: "content=" + content + "sender=" + sender,
        data: "content=" + content,
        dataType:"json",
        success: function (data) {
            if (1 == data.sendResult) {
            	addNewMsgByServer(data.sendTime,data.content);
                $("#messageSpan").html("已发送").fadeOut(2000);
            }
            else {
                $("#messageSpan").html("发送失败").fadeOut(2000);
            }
        },
        error: function () {
            alert("服务器连接错误");
            $("#messageSpan").html("发送失败").fadeOut(2000);
        }
    });
}

function addNewMsgByServer(sendTime,content) {
    var messageDiv = $("#messageDiv");
    var newDiv = "<div style=\"float: left; display: block; clear:both;\"> <img style=\" float:left; width:50px; height:50px;   border-radius:50px; clear:both;\"  src=\"images/avatar.jpg\" /> </div>" +
     			   "<div class = \"send\"  style = \"float:left;  margin-left:20px;\">"+"<div class=\"arrowleft\"></div>" + "<h6>" + "</h6>";
    newDiv += content + "</div>";
    messageDiv.append(newDiv);
    var newMsgDiv =  $(".mainDiv .send:last");
    newMsgDiv.hide().fadeIn(1000);
    $(messageDiv).scrollTop($(messageDiv)[0].scrollHeight);
};

function addNewMsgRightByUser(content) {
    var messageDiv = $("#messageDiv");
    var newDiv = "<div style=\"float: right; display: block;clear:both;\"> <img style=\" float:right;  width:50px; height:50px;   border-radius:50px; \"  src=\"images/avatar.jpg\" /> </div>"+
    			 "<div class = \"send\" style = \"float:right; margin-right:20px;\" >"+"<div class=\"arrowright\"></div>" + "<h6>" + "</h6>";
    newDiv += content + "</div>";
    messageDiv.append(newDiv);
    var newMsgDiv =  $(".mainDiv .send:last");
    newMsgDiv.hide().fadeIn(1000);
    $(messageDiv).scrollTop($(messageDiv)[0].scrollHeight);
};

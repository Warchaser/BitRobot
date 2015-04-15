/**
 * Created by huang_000 on 2014/12/30.
 */
$(function () {
    $("#sendButton").click(function () {  //绑定发送按钮的点击事件
        var content = $("#contentText").val();  //获取输入框的内容
        if ("" == content) {
            $("#messageSpan").show().html("空消息").fadeOut(2000);
            return;
        }

        var preContent = "<pre>" + content + "</pre>";
        sendMessage(preContent);
    });

    $(document).on("click", ".mainDiv .topDiv .rightDiv #onlineDiv ul li div", function () {
        $(".mainDiv .topDiv .rightDiv #onlineDiv ul li div:contains('聊天对象') label:last").remove();
        $(this).append("<label>  (聊天对象)</label>")
    });

    $("#exitButton").click(function () {  //绑定退出按钮的点击事件
        var isExit = confirm("确定要退出吗?");
        if (!isExit)
            return;

        $.ajax({
            type: "post",
            cache: false,
            url: "UserLogout.do",
            success: function () {
                window.location = "_Login.jsp";//成功退出则将页面转向登陆界面
            },
            error: function () {
                alert("服务器连接错误");
            }
        });
    });

    $("#contentText").keydown(function(e){
        if (13 == e.which && e.ctrlKey)
            $("#sendButton").trigger("click");
    });

    refreshUserList();
    refreshMessageList();

    setInterval("refreshUserList()", 5000); //每五秒刷新一次用户列表
    setInterval("refreshMessageList()", 1300); //每秒刷新一次新的信息
});

function sendMessage(content) {   //像服务器发送新的消息
    var receiver = $(".mainDiv .topDiv .rightDiv #onlineDiv ul li div:contains('聊天对象') label:first").text().trim();

    $("#messageSpan").show().html("发送中");
    $.ajax({
        type: "post",
        cache: false,
        url: "SendMsg.do",
        data: "content=" + content + "&receiver=" + receiver,
        dataType:"json",
        success: function (data) {
            if (1 == data.sendResult) {
                $("#contentText").val("");
                    addNewMsg(data.sender, receiver, data.sendTime, content);
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

function refreshUserList() {
    $.ajax({
        type: "post",
        cache: false,
        url: "OnlineUser.do",
        success: function (data) {
            var users = data.substring(1, data.length - 1).split(",");
            var selectedDiv = $(".mainDiv .topDiv .rightDiv #onlineDiv ul li div:contains('聊天对象') ");
            var selected = selectedDiv.text().split("  ")[0];

            $(".mainDiv .topDiv .rightDiv #onlineDiv ul li").remove();
            var ul = $(".mainDiv .topDiv .rightDiv #onlineDiv ul");
            ul.append("<li><div><label>所有人</label></div></li>");

            for (var index = 0; users.length > index; index++) {
                ul.append("<li><div><label>" + users[index] + "</label></div></li>");
            }

            var selectDiv = $(".mainDiv .topDiv .rightDiv #onlineDiv ul li div:contains('" + selected + "') ");
            if (0 != selectDiv.length)
                selectDiv.append("<label>  (聊天对象)</label>");
        },
        error: function () {
            alert("服务器连接错误");
            window.location = "_Login.jsp";
        }
    });
}

function refreshMessageList() {
    var lastMsgTime = $(".mainDiv .topDiv .leftDiv #messageDiv div:last h6").html();
    //alert(null == lastMsgTime);
    $.ajax({
        type: "post",
        cache: false,
        url: "NewMsg.do",
        data: "lastMsgTime=" + lastMsgTime,
        dataType: "json",
        success: function (data) {
            var msgCount = parseInt(data.msgCount);

            if (-1 != msgCount)
                for (var index = 0; msgCount > index; index++) {
                    //alert("sender: " + (data[(index + 1)])[0].content);

                    var msgJson = (data[(index + 1)])[0];
                    var sender = msgJson.sender;
                    var receiver = msgJson.receiver;
                    var sendTime = msgJson.sendTime;
                    var content = msgJson.content;

                    addNewMsg(sender, receiver, sendTime, content);
                }
            else{
                var messageDiv = $("#messageDiv");
                var msgJson = (data[1])[0];
                var newDiv = "<div><h5 style = 'color:red;'>" + msgJson.sender + "</h5>&nbsp;&nbsp;&nbsp;&nbsp;<h6>" + msgJson.sendTime + "</h6><br />Welcome!";

                messageDiv.append(newDiv);
                $(".mainDiv .topDiv .leftDiv #messageDiv div:last").hide().fadeIn("slow");
            }
        },
        error: function () {
            alert("服务器连接错误");
            window.location = "_Login.jsp";
        }
    });
}

function addNewMsg(sender, receiver, sendTime, content) {
    var messageDiv = $("#messageDiv");
    var newDiv = "<div><h5>" + sender + "</h5>&nbsp;在&nbsp;<h6>" + sendTime + "</h6>&nbsp;";

    if (null != receiver && "所有人" != receiver)
        newDiv += "对&nbsp;<h5>" + receiver + "</h5>&nbsp;说:<br />";
    else
        newDiv += "说:<br />";

    newDiv += content + "</div>";
    messageDiv.append(newDiv);
    var newMsgDiv =  $(".mainDiv .topDiv .leftDiv #messageDiv div:last");
    newMsgDiv.hide().fadeIn(1000);
    $(messageDiv).scrollTop($(messageDiv)[0].scrollHeight);
}
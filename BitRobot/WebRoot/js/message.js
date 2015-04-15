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
        $(this).append("<label>  (聊天对象)</label>");
    });

   
    $("#contentText").keydown(function(e){
        if (13 == e.which && e.ctrlKey)
            $("#sendButton").trigger("click");
    });

    
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
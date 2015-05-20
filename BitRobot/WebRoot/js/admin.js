//自动在3秒之后关闭网页
var CTime = 3;
function eventClose(){
	setTimeout('eventClose()',1000);
	if(CTime <= 0)
		windowClose();
	this.ShowTime.innerHTML = "网页将在" + CTime + "后关闭！";
	CTime--; 
}

//直接关闭网页
function windowClose(){
	open(location,'_self').close();
}
//验证不能为空
function isNull(){
	var flag = 0;
	$(".cant_be_null").each(function(){
		if($(this).val() == ''){
			alert("光标所在的输入框不能为空!");
			$(this).focus();
			$(this).css("background-color","#E0FFFF");
			flag = 1;
			return false;
		}
		else{
			$(this).css("background-color","white");
		}
	});
	if(flag == 0)
		return true;
	else
		return false;
}
/*function windowClose(){
	var browserName = navigator.appName;
	var browserVer = parseInt(navaigator.appVersion);
	if(browserName == "Microsoft Internet Explorer"){
		var ie7 = (document.all && ! window.opera && window.XMLHttpRequest) ? true : false;
		if(ie7){
			window.open('','_patent','');
			window.close();
		}else{
			this.focus();
			self.opener = this;
			self.close();
		}
	}
	else{
		try{
			this.focus();
			self.opener = this;
			self.close();
		}catch(e){
			
		}
		try{
			window.open('','_self','');
		}
		catch(e){
			
		}
	}
}*/



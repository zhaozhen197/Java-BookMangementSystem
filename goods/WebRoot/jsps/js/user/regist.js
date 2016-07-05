$(function(){
	/**
	 * 得到所有的错误信息循环遍历之。调用一个方法去显示长
	 * 
	 */
	$(".errorClass").each(function(){//遍历每个元素，用showerror输出
	 showerror($(this));
	});
	
	/**
	 * 鼠标进入时和离开时图片切换
	 */
	$("#submit").hover(
			function(){
				$("#submitBtn").attr("src","/goods/images/regist2.jpg");
				
			},
			
			function(){
				$("#submitBtn").attr("src","/goods/images/regist1.jpg");
			}
	);
	
/**
 * 输入块得到焦点，隐藏错误信息
 */
	$(".inputClass").focus(//找到获得焦点的输入 框
	function(){
		var labelId = $(this).attr("id")+"Error";//通过输入框得到label的id
		$("#"+labelId).text("");//将得到焦点的输入框的错误信息清空
		showerror($("#"+labelId));//隐藏没有信息的label
	}
);
	/**
	 * 输入框失去焦点时调用对应的方法输出对应的错误信息
	 */
	$(".inputClass").blur(function(){
		 	var id = $(this).attr("id");
		 	var funName= "checkout" + id.substring(0,1).toUpperCase()+id.substring(1)+"()";
			eval(funName)
	});
/**
 * 提交时校验
 * @param {} ele
 */
	$("#registForm").submit(function(){
		var bool = true;
	
		if(!checkoutLoginname()){
			bool =  false;
		}
		if(!checkoutLoginpass()){
			bool =  false;
		}
		if(!checkoutReloginpass()){
			bool = false;
		}
		if(!checkoutEmail()){
			bool = false;
		}
		if(!checkoutVerifyCode()){
			bool = false;
		}
		return bool;
		
	});


});
/**
 * 用户名校验 
 */
function checkoutLoginname(){
	var id="loginname";
	var text = $("#"+id).val();
	
	/**
	 * 非空校验 
	 */
	if(!text){
		$("#"+id+"Error").text("用户名不能为空！");
		showerror($("#"+id+"Error"));
		return false;
	}
	/**
	 * 长度校验
	 */
	if(text.length>20 || text.length<3){
		$("#"+id+"Error").text("用户名的长度必须在3~20之间！");
		showerror($("#"+id+"Error"));
				return false;

	}
	/*
	 *是否已经注册
	 */
	$.ajax(
	{
		url:"/goods/UserServlet",//要请求的servlet
		data:{method:"ajaxCheckoutLoginname",loginname:text},//给servlet传递的参数
		type:"POST",
		dataType:"json",
		async:false,//是否是异步请求，如是的话不会等待服务器返回结果，直接向下执行
		cache:false,
		success:function(result){
		if(!result){//如果校验失败
			$("#"+id+"Error").text("该用户名已被注册！");
			showerror($("#"+id+"Error"));
			return false;
			
			}
	}});
	return true;
}
/**
 * 密码校验 
 */
function checkoutLoginpass(){
	var id="loginpass";
	var text = $("#"+id).val();
	
	/**
	 * 非空校验 
	 */
	if(!text){
		$("#"+id+"Error").text("密码不能为空！");
		showerror($("#"+id+"Error"));
		return false;

	}
	/**
	 * 长度校验
	 */
	if(text.length>20 || text.length<6){
		$("#"+id+"Error").text("密码的长度必须在6~20之间！");
		showerror($("#"+id+"Error"));
		return false;
	}
	return true;
}
/**
 * 确认密码校对
 */
function checkoutReloginpass(){
	var id="reloginpass";
	var text = $("#"+id).val();
	
	/**
	 * 非空校验 
	 */
	if(!text){
		$("#"+id+"Error").text("确认密码不能为空！");
		showerror($("#"+id+"Error"));
		return false;
	}
	/**
	 * 长度校验
	 */
	if(text.length>20 || text.length<6){
		$("#"+id+"Error").text("密码长度必须在6~20之间！");
		showerror($("#"+id+"Error"));
		return false;
	}
	/**
	 * 两次密码是否一致
	 */
	if(text != $("#loginpass").val())
	{
		$("#"+id+"Error").text("两次输入 的密码不相同！");
		showerror($("#"+id+"Error"));
		return false;
	}
	
	return true;
}
/**
 * 邮箱校验
 */
function checkoutEmail(){
	var id="email";
	var text = $("#"+id).val();
	
	/**
	 * 非空校验 
	 */
	if(!text){
		$("#"+id+"Error").text("Email不能为空！");
		showerror($("#"+id+"Error"));
		return false;

	}
	if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(text))
	{$("#"+id+"Error").text("Email格式不符合要求！");
		showerror($("#"+id+"Error"));
		return false;
	}
	
	/*
	 * 邮箱校对
	 */
	$.ajax(
	{
		url:"/goods/UserServlet",//要请求的servlet
		data:{method:"ajaxCheckoutEmail",email:text},//给servlet传递的参数
		type:"POST",
		dataType:"json",
		async:false,//是否是异步请求，如是的话不会等待服务器返回结果，直接向下执行
		cache:false,
		success:function(result){
		if(!result){//如果校验失败
			$("#"+id+"Error").text("该邮箱已被注册！");
			showerror($("#"+id+"Error"));
			return false;
			
			}
	}});
	return true;
}

/**
 * 验证码校验
 */
function checkoutVerifyCode(){
	var id="verifyCode";
	var text = $("#"+id).val();
	
	/**
	 * 非空校验 
	 */
	if(!text){
		$("#"+id+"Error").text("验证码不能为空！");
		showerror($("#"+id+"Error"));
		return false;

	}
	/**
	 * 长度校验
	 */
	if(text.length>4 || text.length<4){
		$("#"+id+"Error").text("验证码错误");
		showerror($("#"+id+"Error"));
		return false;
	}
	/*
	 * 检查验证码是否一致
	 */
	$.ajax(
	{
		url:"/goods/UserServlet",//要请求的servlet
		data:{method:"ajaxCheckoutVerifyCode", verifyCode:text},//给servlet传递的参数
		type:"POST",
		dataType:"json",
		async:false,//是否是异步请求，如是的话不会等待服务器返回结果，直接向下执行
		cache:false,
		success:function(result){
		if(!result){//如果校验失败
			$("#"+id+"Error").text("验证码错误！");
			showerror($("#"+id+"Error"));
			return false;
			
			}
	}});
	return true;
}



/**
 * 如果没有内容就不显示有内容就显示
 * @param ele
 */
function  showerror(ele){
	var text = ele.text();//获取元素的内容
	if(!text)//	如果没有内容 
		{
			 ele.css("display","none");//隐藏内容
		}else{
			ele.css("display" , "");
		}
	
}

/**
 * 验证码换一张
 */
function _hyz(){
	
	/**
	 * 获取img元素
	 * 重新设置src
	 * 使用日期时间添加参数
	 */
	$("#imgVerifyCode").attr("src","/goods/VerifyCodeServlet?a="+ new Date().getTime());
	
}



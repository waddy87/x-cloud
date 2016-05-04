<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="description" content="">
<meta name="author" content="">
<title>登录-Cloudview 2.0 sp1</title>
<script type="text/javascript" src="${ctx}/resources/lib/jquery/jquery.min.js"></script>
<script src="${ctx}/resources/lib/jquery/jquery.validate.min.js"></script>
<link href="${ctx}/resources/lib/fontawesome/css/font-awesome.min.css" rel="stylesheet" />
<link href="${ctx}/resources/css/animate.css" rel="stylesheet">
<link href="${ctx}/resources/css/login.css" rel="stylesheet">
<link rel="apple-touch-icon" href="${ctx}/resources/image/apple-touch-icon.png">
<link rel="icon" href="${ctx}/resources/image/favicon.ico">
<script type="text/javascript">
$(function(){
	var tag=false;
	$("#validateCode").bind("keyup",function(){
		var validateCode=$("#validateCode").val()
		if(validateCode.length>3){
			$.ajax({
				url : '${ctx}' + '/checkValidateCode',
				type : 'POST',
				data:{
					validateCode:validateCode
					},
				dataType : 'json',
				success : function(result) {
					if(result.success){
						tag=true;
						$('#validateCodeIcon').css('display','inline-block').attr('class','fa fa-check-circle');
					}else{
						$('#validateCodeIcon').css('display','inline-block').attr('class','fa fa-times-circle');
					}
				}
			});
		}else{
			$('#validateCodeIcon').css('display','inline-block').attr('class','fa fa-times-circle');
		}
	});
    var validate = $("#userLoginFormID").validate({
        submitHandler: function(form){   //表单提交句柄,为一回调函数，带一个参数：form
        	if(tag){
            	form.submit();   //提交表单
            	$('.login-btn').attr('disabled','disabled');
            	$('.login-btn i').hide();
            	$('.login-btn .loader').show();
        	}
        },
        invalidHandler: function(event, validator) {
        	$('.login-shadow').removeClass('bounceInUp').addClass('shake');
        	$('.login-box').removeClass('bounceInDown').addClass('wobble');
        },
        errorPlacement:function(error,element) {  
			error.appendTo(element.parent().find("div.errordiv"));
	   	},
        rules:{
        	username:{
                required:true
            },
            password:{
                required:true,
            },
            validateCode:{
            	required:true,
            }                    
        },
        messages:{
        	username:{
                required:"请输入用户名"
            },
            password:{
                required:"请输入密码",
            },
            validateCode:{
                required:"请输入验证码",
            }
        }
    });    
	if('${message}'!==''){
		$('.login-shadow').removeClass('bounceInUp').addClass('shake');
    	$('.login-box').removeClass('bounceInDown').addClass('wobble');
	}
});
function reloadValidateCode(){
    $("#validateCodeImg").attr("src","${ctx}/validateCode?data=" + new Date() + Math.floor(Math.random()*24));
}
</script>
</head>
<body>
<div class="login-body">
	<div class="login-shadow animated bounceInUp"></div>
	<div class="login-box animated bounceInDown">
		<div class="login-logo"><img src="${ctx}/resources/image/login-logo.svg" width="180" height="54" alt="cloudview" /></div>
		<div class="login-form">
			<form method="post" action="${ctx}/login" id="userLoginFormID" >
				<div class="form-group">
				    <input type="text" class="form-control" name="username" id="username" placeholder="用户名" value="${username}">
				    <label for="username"><i class="fa fa-user"></i></label>
				    <div class="errordiv"></div>
				  </div>
				  <div class="form-group">
				    <input type="password" class="form-control" name="password" id="password" placeholder="密码" value="">
				    <label for="password"><i class="fa fa-key"></i></label>
				    <div class="errordiv"></div>
				  </div>
				  <div class="form-group">
				    <input type="text" placeholder="验证码" class="form-validate" id="validateCode" name="validateCode" />
				  	<div class="errordiv"></div>
				  	<img id="validateCodeImg" src="${ctx}/validateCode" />
				  	<a href="#" onclick="javascript:reloadValidateCode();"><i id="validateCodeIcon" class="fa fa-check-circle"></i><i class="fa fa-repeat"></i></a>
				  </div>
				<c:if test="${message!=null}">
					<p style="color:#FF9331;"><i class="fa fa-exclamation-triangle" style="margin-right:10px;"></i>${message }</p>
				</c:if>
				<c:if test="${not empty sessionScope.kickoutmes}">
					<p style="color:#FF9331;"><i class="fa fa-exclamation-triangle" style="margin-right:10px;"></i>您被踢出登录。</p>
				</c:if>
				<button type="submit" class="login-btn">
					<i class="fa fa-arrow-right"></i>
					<div class="loader">
						<div class="loader-inner line-scale-party">
					    	<div></div>
					    	<div></div>
					    	<div></div>
					    	<div></div>
						</div>
					</div>
				</button>
			</form>
		</div>
	</div>
</div>
</body>
</html>

cloudmanager.namespace("userPassword");
cloudmanager.userPassword = {
	initUpdatePassword:function(){
		var _this=this;
		$("#updateUserPasswordForm").validate({
			errorPlacement:function(error,element) {  
				error.appendTo(element.parent().next("div"));
		   	},
			submitHandler:function(form){  
				var params={
						oldPassword:$("#oldPassword").val(),
						newPassword:$("#newPassword").val(),
						//confirmNewPassword:$("#confirmNewPassword").val(),
					};
				_this.updateUserPassword(params);
			},
			rules: {
				oldPassword: {
					required: true,
				},
				newPassword: {
					required: true,
					rangelength:[3,10]
				},
				confirmNewPassword: {
					equalTo:"#newPassword"
				},
			},
			messages : {
                confirmNewPassword:{
                    equalTo:"两次密码输入不一致！"
                } 
		    },
		});
	},
	updateUserPassword:function(params){
		sugon.load({
			type : 'POST',
			action : sugon.rootURL + '/userMgmt/updateUserPassword',
			dataType : 'json',
			data:params,
			callback : function(result) {
				if(result.success){
					layer.closeAll();
					toastr.success('修改成功！');
				}else{
					toastr.error(result.message);
				}
				
			}
		});
	},
	openUpdateUserPasswordPage:function(){
		$.get(sugon.rootURL+'/userMgmt/openUpdateUserPasswordPage', {}, function(str){
			layer.open({
				  type: 1,
				  title:'修改密码',
				  //skin: 'layui-layer-rim', //加上边框
				  area: ['600px', ''], //宽高
				  content: str,
				  btn:['确定','取消'],
				  yes:function(index){
					  $('#updateUserPasswordForm').submit();
				  }
			});
		});
	}
}
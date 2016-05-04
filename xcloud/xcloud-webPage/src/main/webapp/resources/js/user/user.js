cloudmanager.namespace("userMgmt");
cloudmanager.userMgmt = {
	init : function() {
		$('#userTableId').datagrid({
			url : sugon.rootURL + '/userMgmt/queryUserTable',
			method : 'post',
			striped : true,
			fitColumns : true,
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			pageNumber : 1,
			nowrap : false,
			pageSize : 10,
			toolbar : '#usertb',
			pageList : [ 5, 10, 20, 50, 100],
			queryParams : {},
			loadMsg : "加载中.....",
			showFooter : true,
			columns : [ [ {
				field : "username",
				title : "用户名",
				align : "left",
				width : '7%',
				//formatter : cloudmanager.userMgmt.formatName
			},{
				field : "realname",
				title : "真实姓名",
				align : "center",
				width : '8%'
			},{
				field : "roles",
				title : "角色",
				align : "center",
				width : '8%',
				formatter : cloudmanager.userMgmt.formatRole
			},{
				field : "orgName",
				title : "所属组织",
				align : "center",
				width : '10%',
			},{
				field : "locked",
				title : "状态",
				align : "center",
				width : '4%',
				formatter : cloudmanager.userMgmt.formatLocked
			}, {
				field : "isDelete",
				title : "是否注销",
				align : "center",
				width : '6%',
				formatter : cloudmanager.userMgmt.formatIsDelete
			},{
				field : "email",
				title : "电子邮箱",
				align : "center",
				width : '15%'
			}, {
				field : "telephone",
				title : "移动电话",
				align : "center",
				width : '8%'
			}, {
				field : "isOnline",
				title : "在线状态",
				align : "center",
				width : '5%',
				formatter :cloudmanager.userMgmt.isOnline
			},  {
				field : "createDate",
				title : "创建时间",
				align : "center",
				width : '12%'
			}, {
				field : "operate",
				title : "操作",
				align : "center",
				width : '15%',
				formatter : cloudmanager.userMgmt.formatOper
			} ] ],
			onBeforeLoad : function(param) {
			},
			onLoadSuccess : function(data) {
			},
			onLoadError : function() {
			},
			onClickCell : function(rowIndex, field, value) {
			}
		});
		var p = $('#userTableId').datagrid('getPager');
		$(p).pagination({
			layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
			displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
			beforePageText:'跳转到第 ',
			afterPageText:' 页'
		});
		$('#userSearchInputId').searchbox({
			searcher:function(value,name){
				var queryParams = $('#userTableId').datagrid('options').queryParams;
				queryParams.name = value;
				$("#userTableId").datagrid('reload');
			},
		    menu:'#tableSearch',
		    prompt:'请输入查询内容'
		});
		$('#userSearchInputId').searchbox('addClearBtn', 'icon-clear');
	},
	doSearch : function(value, name) {
		var queryParams = $('#userTableId').datagrid('options').queryParams;
		queryParams.name = value;
		$("#userTableId").datagrid('reload');
	},
	formatOper : function(val, row, index) {
		var displayName="冻结";
		if(row.locked){
			displayName="解冻";
		}
		return '<a href="#" onclick="cloudmanager.userMgmt.openUpdateUserPage(\''+ row.id + '\')">修改</a>'
				+'&nbsp;&nbsp;&nbsp;&nbsp;'
				+'<a href="#" onclick="cloudmanager.userMgmt.openChangeAccountStatusPage(\''+row.id + '\','+row.locked+')">'+displayName+'</a>'
				+'&nbsp;&nbsp;&nbsp;&nbsp;'
				+'<a href="#" onclick="cloudmanager.userMgmt.openResetPasswordPage(\''+row.id + '\')">重置密码</a>'
	},
	formatName : function(val, row, index) {
		return '<a href="#" onclick="cloudmanager.userMgmt.userDetail(\''
				+ row.id + '\')">' + row.username + '</a>';
	},
	formatIsDelete : function(val, row, index) {
		if(val){
			return '是';
		}else{
			return '否';
		}
		
	},
	formatLocked : function(val, row, index) {
		if(val){
			return '冻结';
		}else{
			return '正常';
		}
		
	},
	isOnline : function(val, row, index) {
		if(val){
			return '在线';
		}else{
			return '离线';
		}
		
	},
	formatRole: function(val, row, index) {
		return row.roles[0].roleName;
	},
	openUserCreatePage:function(){
		$.get(sugon.rootURL+'/userMgmt/openUserCreatePage', {}, function(str){
			layer.open({
				  type: 1,
				  title:'创建用户',
				  //skin: 'layui-layer-rim', //加上边框
				  area: ['900px', '600'], //宽高
				  content: str,
				  btn:['确认','取消'],
				  yes: function(index,layero){
					  $("#createUserForm").submit();
				  },
				  cancel: function(index){ 
				  },
			});
		});
	},
	initCreateUser:function(){
		var _this=this;
		// 手机号码验证
		$.validator.addMethod("isMobile", function(value, element) {
		    var length = value.length;
		    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
		    return this.optional(element) || (length == 11 && mobile.test(value));
		}, "请正确填写您的手机号码");
		$.validator.addMethod("username", function(value, element){
			return this.optional(element) ||/^[a-zA-Z0-9_]+$/.test(value);
		}, "只能输入字母、数字、下划线！");
		$("#createUserForm").validate({
			errorPlacement:function(error,element) {  
				error.appendTo(element.parent().next("div"));
		   	},
			submitHandler:function(form){
				sugon.load({
					type : 'POST',
					action : sugon.rootURL + '/userMgmt/createUser',
					dataType : 'json',
					data:{
						username:$("#username").val(),
						password:$("#password").val(),
						realname:$("#realname").val(),
						telephone:$("#telephone").val(),
						email:$("#email").val(),
					},
					callback : function(result) {
						if(result.success){
							//$('#userDialogDivId').dialog('close');
							layer.closeAll();
							$("#userTableId").datagrid('reload');
							toastr.success('创建成功！');
						}else{
							toastr.error(result.message);
						}
						
					}
				});
			},
			rules: {
				username: {
					required: true,
					rangelength:[3,20],
					username:true,
				},
				password: {
					required: true,
					rangelength:[3,10]
				},
				confirmPassword: {
					equalTo:"#password"
				},
				realname: {
					required: true,
					rangelength:[1,20]
				},
				telephone: {
					required : true,
		            minlength : 11,
		            isMobile : true
				},
				email: {
					required: true,
					email: true
				},
			},
			messages : {
				confirmNewPassword:{
                    equalTo:"两次密码输入不一致！"
                },
		        phone : {
		            required : "请输入手机号",
		            minlength : "确认手机不能小于11个字符",
		            isMobile : "请正确填写您的手机号码"
		        }
		    },
		});
	},
	initUpdateUser:function(){
		var _this=this;
		// 手机号码验证
		jQuery.validator.addMethod("isMobile", function(value, element) {
		    var length = value.length;
		    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
		    return this.optional(element) || (length == 11 && mobile.test(value));
		}, "请正确填写您的手机号码");
		
		$("#updateUserForm").validate({
//			debug:false,
//			focusInvalid: true, //当为false时，验证无效时，没有焦点响应  
//			onkeyup: true,
			errorPlacement:function(error,element) {  
				error.appendTo(element.parent().next("div"));
		   	},
			submitHandler:function(form){  
				var params={
						userId:$("#userId").val(),
						realname:$("#realname").val(),
						telephone:$("#telephone").val(),
						email:$("#email").val(),
					};
				_this.updateUser(params);
			},
			rules: {
				realname: {
					required: true,
					rangelength:[1,20]
				},
				telephone: {
					required : true,
		            minlength : 11,
		            isMobile : true
				},
				email: {
					required: true,
					email: true
				},
			},
			messages : {
		        phone : {
		            required : "请输入手机号",
		            minlength : "确认手机不能小于11个字符",
		            isMobile : "请正确填写您的手机号码"
		        }
		    },
		});
	},
	updateUser:function(params){
		//params=JSON.stringify(params);
		sugon.load({
			type : 'POST',
			action : sugon.rootURL + '/userMgmt/updateUser',
			dataType : 'json',
			data:params,
			callback : function(result) {
				if(result.success){
					layer.closeAll();
					$("#userTableId").datagrid('reload');
					toastr.success('修改成功！');
				}else{
					toastr.error(result.message);
				}
				
			}
		});
	},
	openUpdateUserPage:function(id){
		$.get(sugon.rootURL+'/userMgmt/openUpdateUserPage?id='+id, {}, function(str){
			layer.open({
				  type: 1,
				  title:'修改用户',
				  //skin: 'layui-layer-rim', //加上边框
				  area: ['700px', ''], //宽高
				  content: str,
				  btn:['确认','取消'],
				  yes: function(index,layero){
					  $("#updateUserForm").submit();
				  },
				  cancel: function(index){ 
				  },
			});
		});
	},
	openResetPasswordPage:function(userId){
		layer.confirm('确认重置密码？', {
			title:'重置密码',
			icon: 3,
			btn: ['确认','取消'] //按钮
		}, function(index){
			sugon.load({
				dataType : 'json',
				type : 'POST',
				action : sugon.rootURL + '/userMgmt/resetPassword',
				data : {
					userId : userId,
				},
				callback : function(result) {
					if(result.success){
						layer.close(index);
						toastr.success('重置成功！');
						$("#userTableId").datagrid('reload');
					}else{
						layer.close(index);
						toastr.error(result.message);
					}
				}
			});
		});
	},
	openChangeAccountStatusPage:function(userId,locked){
		var displayName="冻结";
		if(locked){
			displayName="解冻";
		}
		layer.confirm('确认'+displayName+'用户？', {
			title: displayName,
			icon: 3,
			btn: ['确认','取消'] //按钮
		}, function(index){
			sugon.load({
				dataType : 'json',
				type : 'POST',
				action : sugon.rootURL + '/userMgmt/changeAccountStatus',
				data : {
					userId : userId,
					locked:!locked
				},
				callback : function(result) {
					if(result.success){
						layer.close(index);
						toastr.success(displayName+'成功！');
						$("#userTableId").datagrid('reload');
					}else{
						layer.close(index);
						toastr.error(result.message);
					}
				}
			});
		});
	},
	
}
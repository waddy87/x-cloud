cloudmanager.namespace("orgManagement");
cloudmanager.orgManagement={
		initList:function(){
			var myview = $.extend({},$.fn.datagrid.defaults.view,{
			    onAfterRender:function(target){
			        $.fn.datagrid.defaults.view.onAfterRender.call(this,target);
			        var opts = $(target).datagrid('options');
			        var vc = $(target).datagrid('getPanel').children('div.datagrid-view');
			        vc.children('div.datagrid-empty').remove();
			        if (!$(target).datagrid('getRows').length){
			            var d = $('<div class="datagrid-empty"></div>').html(opts.emptyMsg || 'no records').appendTo(vc);
			            d.css({
			                position:'absolute',
			                left:0,
			                top:40,
			                width:'100%',
			                textAlign:'center'
			            });
			        }
			    }
			});
			$('#orgTable').datagrid({
				view:myview,
				toolbar:"#toolbar",
				emptyMsg: '无数据',
				url: sugon.rootURL+'/orgs/list',//请求路径
				method: 'get',
				/*     queryParams: { 'id': id }, */
				idField: 'id', 
				queryParams:{
					name:""
				},
				loadMsg:"加载组织列表数据,请稍后",
				striped : true,
				fitColumns : true,
				singleSelect : true,
				rownumbers : false,
				pagination : true,
				pageNumber : 1,
				nowrap : false,
				pageSize : 10,
				pageList: [5, 10, 20, 50, 100],
				/*  showFooter: false, */
				columns: [[	
				           	/*{field:"id",checkbox:true,width:50},      */
				           	{field:"name",title:"组织名称",align:"center",formatter:cloudmanager.orgManagement.formatName,sortable:true,resizable:true,width:250},
				           	{field:"address",title:"单位名称",align:"center",width:250},
				           	{field:"create_time",title:"创建时间",align:"center",width:250},
				           	{field:"operate",title:"操作",align:"center",formatter:cloudmanager.orgManagement.formatOper,width:330}
				           	]], 
				           	onBeforeLoad: function (param) {
				           	},
				           	onLoadSuccess: function (data) {
				           	},
				           	onLoadError: function () {

				           	},
				           	onClickCell: function (rowIndex, field, value) {

				           	}
			});	
			var p = $('#orgTable').datagrid('getPager');
			$(p).pagination({
				layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
				displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
				beforePageText:'跳转到第 ',
				afterPageText:' 页'
			});
			$('#orgSearchInput').searchbox({
				searcher:function(value,name){
					var queryParams = $('#orgTable').datagrid('options').queryParams;
					queryParams.name = value;
					$("#orgTable").datagrid('reload');
				    },
				    menu:'#tableSearch',
				    prompt:'请输入查询内容'
			});
		/*	$.extend($.fn.searchbox.methods, {
				addClearBtn : function(jq, iconCls) {
					return jq.each(function() {
						var t = $(this);
						var opts = t.searchbox('options');
						console.info(opts);
						opts.icons = opts.icons || [];
						opts.icons.unshift({
							iconCls : iconCls,
							handler : function(e) {
								$(e.data.target).searchbox('clear').searchbox(
										'textbox').focus();
								$(this).css('visibility', 'hidden');
								console.info("进入unshift方法");
								console.info(e);
								cloudmanager.orgManagement.doSearch();
							}
						});
						t.searchbox();
						if (!t.searchbox('getText')) {
							t.searchbox('getIcon', 0).css('visibility', 'hidden');
						}
						t.searchbox('textbox').bind('keyup', function() {
							var icon = t.searchbox('getIcon', 0);
							if ($(this).val()) {
								icon.css('visibility', 'visible');
							} else {
								icon.css('visibility', 'hidden');
							}
						});
					});
				}
			});*/
			$('#orgSearchInput').searchbox('addClearBtn', 'icon-clear');
		},
		initDetail:function (id){
			var _this=this;
			$("#editBtn").bind("click",function(){
				if($("#btnDiv").hasClass("orgEdit")){
					$("#orgName-error").show();
					$("#address-error").show();
					$("#updateOrgForm input").removeClass("orgEdit");
					$("#updateOrgForm p").addClass("orgEdit");	
					$("#btnDiv").removeClass("orgEdit");
					$("#editBtn").text("取消编辑");
				}
				else{
					$("#orgName-error").hide();
					$("#address-error").hide();
					$("#updateOrgForm input").addClass("orgEdit");
					$("#updateOrgForm p").removeClass("orgEdit");	
					$("#btnDiv").addClass("orgEdit");
					$("#editBtn").text("编辑");
				}
			});
			$("#updateOrgForm").validate({
/*				debug:false,*/
				focusInvalid: true, //当为false时，验证无效时，没有焦点响应  
				onkeyup: true,
				submitHandler:function(form){  
					console.info("------");
		/*			 {"owner":"8a809e50536435960153643b33c30003","address":"sssss","create_time":{"date":23,"hours":9,"seconds":42,"month":2,"timezoneOffset":-480,"year":116,"minutes":58,"time":1458698322000,"day":3},"creater":"zhang3","name":"sdasdas","id":"402881f553a131340153a13202ca0000","remarks":"sssss","status":"A"} */	
					var params={id:id,address:$("#address").val(),remarks:$("#remarks").val(),name:$("#orgName").val()};
					console.info(params);
					_this.confirmUpdateOrg(params);
				},
				rules: {
					orgName: "required",
					address: {
						required: true,
						rangelength:[3,10],
					}
				},
				messages: {
					orgName: "请输入组织名称",
					address: {
						required: "请输入单位",
						rangelength: "地址要在3-10位之间"
					}
				}
			});
		},
		doSearch: function (name,value){
			console.info(name);
			console.info(value);
			var queryParams = $('#orgTable').datagrid('options').queryParams;
			queryParams.name = name;
			$("#orgTable").datagrid('reload');
		},
		addOrg:function(){
			var _this=this;
			$.get(sugon.rootURL+'/orgs/toAddOrg', {}, function(str){
				var index = layer.open({
					  type: 1,
					  title:'增加组织',
					  //skin: 'layui-layer-rim', //加上边框
					  area: ['600px', '600px'], //宽高
					  content: str,
					  btn:['确认','取消'],
					  yes:function(){
						 /* $('#addOrgForm').submit();*/
						  //layer.close(index);
						  console.info($("#addOrgForm").valid());
						  console.info($("#orgName-error"));
						  console.info($("#account-error"));
						  
					
						setTimeout(function(){
							if($("#addOrgForm").valid()){
								var params={organization:{name:$("#orgName").val(),address:$("#address").val(),remarks:$("#remarks").val(),creater:'admin'},user:{realname:$("#name").val(),telephone:$("#phone").val(),username:$("#account").val(),password:$("#password").val(),email:$("#email").val(),orgName:$("#orgName").val()}};
								_this.confirmAddOrg(params);
							}
/*							if($("#orgName-error").is(":hidden")&&$("#account-error").is(":hidden")){
								var params={organization:{name:$("#orgName").val(),address:$("#address").val(),remarks:$("#remarks").val(),creater:'admin'},user:{realname:$("#name").val(),telephone:$("#phone").val(),username:$("#account").val(),password:$("#password").val(),email:$("#email").val(),orgName:$("#orgName").val()}};
								_this.confirmAddOrg(params);
							}*/		
							 console.info($("#addOrgForm").valid());
						},100);
								
						

					  },
					  cannel:function(){
						  
					  }
				});
			});
		},
		deleteOrg:function (id,name) {
			/*  var row = $("#tt").datagrid("getSelected"); */
			/*console.info(params);*/
			var param={orgId:id,orgName:name};
			/*param=JSON.stringify(param);*/
			layer.confirm('是否删除组织？', {
				title:'删除组织',
				icon: 3,
				btn: ['确认','取消'] //按钮
			}, function(index){
				$.ajax({
					url : sugon.rootURL + "/orgs/deleteOrg",
					type : 'POST',
					data:param,
					dataType : 'json',
					/*contentType: "application/json; charset=utf-8",*/
					success : function(result) {
						console.info(result);
						if(result.flag){
							layer.close(index);
							toastr.success('删除组织成功！');
							$("#orgTable").datagrid('reload');
						}
						else{
							layer.close(index);
							toastr.error(result.message);
						}
					}
				});
			});
		},
		formatOper:function (val,row,index){  
			console.info(row);
			return '<a href="#" onclick="cloudmanager.orgManagement.deleteOrg(\''+row.id+'\',\''+row.name+'\')">删除</a>';  
		}  ,
		formatName:function (val,row,index){
			return '<a href="#" onclick="cloudmanager.orgManagement.goToDetail(\''+row.id+'\')">'+row.name+'</a>';  
		},
		initAdd:function(){
			var _this=this;
			console.info("开始执行增加组织初始化操作");
			jQuery.validator.addMethod("phoneNumber", function(value, element) {   
			    var tel = /^[1][358][0-9]{9}$/;
			    return this.optional(element) || (tel.test(value));
			}, "请输入正确的手机号码");
			$("#addOrgForm").validate({
/*				debug:false,
				focusInvalid: true, //当为false时，验证无效时，没有焦点响应  
				onkeyup: true,
				submitHandler:function(form){  
				},*/
				rules: {
					orgName: {
						remote:{
							type:'POST',
							url:sugon.rootURL + "/orgs/validOrgName",
							data:{name:function(){return $("#orgName").val()}}
						}
						,required :true

					},
					email: {
						required: true,
						email: true
					},
					account:{
						remote:{
							type:'POST',
							url:sugon.rootURL + "/orgs/validAccount",
							data:{account:function(){return $("#account").val()}}
						},
						required: true
					},
					name:{
						required: true,
					},
					address:{
						required: true
					},
					phone:{
						required: true,	
						phoneNumber:true
					},
					password: {
						required: true,
						rangelength:[3,10]
					},
					passwordConfirm: {
						required: true,
						rangelength:[3,10],
						equalTo: "#password"
					}
				},
				messages: {
					orgName: {required:"请输入组织名称"
					,remote:"存在相同名称的组织"
					},
					email: {
						required: "请输入Email地址",
						email: "请输入正确的email地址"
					},
					account:{
						required: "请输入账号"
					,remote:"存在同名账户"
					},
					name:{
						required: "请输入管理员姓名"
					},
					address:{
						required: "请输入所属单位"
					},
					phone:{
						required: "请输入手机号码",
						phoneNumber:"请输入正确的手机号码"
					},
					password: {
						required: "请输入密码",
						rangelength: "密码要在3-10位之间"
					},
					passwordConfirm: {
						required: "请输入确认密码",
						rangelength: "密码要在3-10位之间",
						equalTo: "两次输入密码不一致"
					}
				}
			});
		},
		confirmUpdateOrg:function(params){		
			params=JSON.stringify(params);
			console.info(params);
			$.ajax({            
				url : sugon.rootURL + "/orgs/updateOrg",
				method : 'POST',
				contentType: "application/json;charset=UTF-8",				
				dataType:"json",
				data:params,
				success : function(result) {
					if(result.flag){
						toastr.success('更新组织成功');
						layer.closeAll();
						$("#orgTable").datagrid('load');
					}else{
						toastr.error(result.message);
					}

				}
			});
		},
		confirmAddOrg:function(params){
			console.info(params);
			console.info(JSON.stringify(params));
			params=JSON.stringify(params);
			sugon.load({
				dataType:"json",
				action:sugon.rootURL + "/orgs/addOrg",
				data:params,
				type:'POST',
				contentType:"application/json;charset=UTF-8",
				callback : function(result) {
					console.info("-----------------------");
					console.info(result.flag);
					if(result.flag){
						toastr.success(result.message);
						layer.closeAll();
						/*$('#sugonDialog').dialog("close");	*/
						$("#orgTable").datagrid('reload');	
					}
					else{
						toastr.error(result.message);
					}
				}
			});
			
			
			
/*			$.ajax({            
				url : sugon.rootURL + "/api/organizations",
				method : 'POST',
				contentType: "application/json;charset=UTF-8",
				dataType:"json",
				data:params,
				success : function(result) {
					layer.closeAll();
					$("#orgTable").datagrid('reload');
					toastr.success('添加组织成功');
				},
				error:function(err){
					toastr.error(err.responseJSON.message);
				}
			   
			});*/
		},
		goToDetail:function(id){
			$.get(sugon.rootURL+'/orgs/goToDetail?id='+id+'&_=123123', {}, function(str){
				var index = layer.open({
					  type: 1,
					  title:'修改组织',
					  //skin: 'layui-layer-rim', //加上边框
					  area: ['600px', '500px'], //宽高
					  content: str
/*					 , btn:['确认','取消'],
					  yes:function(){
						  $('#updateOrgForm').submit();
						  //layer.close(index);
					  },
					  cannel:function(){
						  
					  }*/
				});
			});
		},
		cancelAdd:function(){
			layer.closeAll();
					},
		returnTo:function(){
			$("#orgManaLink").trigger("click");
		}
}
cloudmanager.namespace("projectManagement");
cloudmanager.projectManagement={
		defaultValue:{
			vmSelection:null,
			personSelection:null
		},
		initList:function(orgId,proId){
			console.info(orgId);
			var _this=this;
			if(proId){
				$("#editBtn").bind("click",function(){
					if($("#btnDiv").hasClass("orgEdit")){
						$("#proName-error").show();
						$(this).text("取消编辑");
						var proNameP=$("#proNameP"),
						remarksP=$("#remarksP");
						$("#updateProForm input").removeClass("orgEdit");
						proNameP.addClass("orgEdit");	
						remarksP.addClass("orgEdit");	

						$("#btnDiv").removeClass("orgEdit");
					}
					else{
						$(this).text("编辑");
						$("#proName-error").hide();
						$("#updateProForm input").addClass("orgEdit");
						$("#proNameP").removeClass("orgEdit");	
						$("#remarksP").removeClass("orgEdit");	
						$("#btnDiv").addClass("orgEdit");
					}

				});
				$("#updateProForm").validate({
					debug:false,
					focusInvalid: true, //当为false时，验证无效时，没有焦点响应  
					onkeyup: true,
					submitHandler:function(form){  
						console.info("------");
						var proId=$("#proList").find(".focus").data("proid");
						/*			 {"owner":"8a809e50536435960153643b33c30003","address":"sssss","create_time":{"date":23,"hours":9,"seconds":42,"month":2,"timezoneOffset":-480,"year":116,"minutes":58,"time":1458698322000,"day":3},"creater":"zhang3","name":"sdasdas","id":"402881f553a131340153a13202ca0000","remarks":"sssss","status":"A"} */	
						var params={id:proId,name:$("#proName").val(),description:$("#remarks").val()};
						_this.confirmUpdatePro(params);
					},
					rules: {
						proName: "required"
					},
					messages: {
						proName: "请输入项目名称"
					}
				});



				$("#proList").find("a:first-child").addClass("focus");
				$("#proList").find("a").on("click",function(){
					$("#proList").find(".focus").removeClass("focus");
					$(this).addClass("focus");
				});

				console.info($("#proList").find("a:first-child"));
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
				var role=$("#roleFlag").val();
				var vmColumns=[];
				var userColumns=[];
				if(role==='org_manager'){
					vmColumns=[	{field:"id",checkbox:true,width:50},      
					           	{field:"name",title:"虚拟机名称",align:"center",formatter:cloudmanager.projectManagement.formatName,sortable:true,resizable:true,width:250},
					           	{field:"vmStatus",title:"状态",align:"center",width:320},
					           	{field:"createTime",title:"创建时间",align:"center",width:300},
					           	{field:"operate",title:"操作",align:"center",formatter:cloudmanager.projectManagement.formatOper,width:360}
					           	];
					userColumns=[ {field:"id",checkbox:true,width:50},      
					              {field:"realname",title:"姓名",align:"center",sortable:true,resizable:true,width:250},
					              {field:"email",title:"邮箱",align:"center",width:250},
					              {field:"username",title:"用户名",align:"center",width:250},
					              {field:"operate",title:"操作",align:"center",formatter:cloudmanager.projectManagement.formatUserOper,width:330}
					              ];
				}
				else{
					vmColumns=[	{field:"id",checkbox:true,width:50},      
					           	{field:"name",title:"虚拟机名称",align:"center",formatter:cloudmanager.projectManagement.formatName,sortable:true,resizable:true,width:250},
					           	{field:"vmStatus",title:"状态",align:"center",width:320},
					           	{field:"createTime",title:"创建时间",align:"center",width:300}
					           	];
					userColumns=[ {field:"id",checkbox:true,width:50},      
					              {field:"realname",title:"姓名",align:"center",sortable:true,resizable:true,width:250},
					              {field:"email",title:"邮箱",align:"center",width:250},
					              {field:"username",title:"用户名",align:"center",width:250}
					              ];

				}
				$('#vmTable').datagrid({
					view: myview,
					toolbar:"#toolbar",
					emptyMsg: '无数据',
					url: sugon.rootURL+'/action/project/queryVmlist',//请求路径
					method: 'get',
					/*     queryParams: { 'id': id }, */
					idField: 'id', 
					queryParams:{
						name:"",
						orgId:orgId,
						proId:proId
					},
					loadMsg:"加载虚拟机列表数据,请稍后",
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
					columns: [vmColumns], 
					           	onBeforeLoad: function (param) {
					           	},
					           	onLoadSuccess: function (data) {
					           	},
					           	onLoadError: function () {

					           	},
					           	onClickCell: function (rowIndex, field, value) {

					           	}
				});	
				var p = $('#vmTable').datagrid('getPager');
				$(p).pagination({
					layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
					displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
					beforePageText:'跳转到第 ',
					afterPageText:' 页'
				});
				$('#personTable').datagrid({
					toolbar:"#toolbar",
					url: sugon.rootURL+'/action/project/queryUserlist',//请求路径
					method: 'get',
					queryParams: { 'name': "" }, 
					idField: 'id', 
					queryParams:{
						name:"",
						orgId:orgId,
						proId:proId
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
					showFooter: false, 
					columns: [userColumns], 
					           onBeforeLoad: function (param) {
					           },
					           onLoadSuccess: function (data) {
					           },
					           onLoadError: function () {

					           },
					           onClickCell: function (rowIndex, field, value) {

					           }
				});	
				var p = $('#personTable').datagrid('getPager');
				$(p).pagination({
					layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
					displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
					beforePageText:'跳转到第 ',
					afterPageText:' 页'
				});
			}

			$(".dd_grou").mCustomScrollbar({

			});
		},
		initDetail:function (id){
			var _this=this;
			$("#editBtn").bind("click",function(){
				console.info("取消编辑aaa");
				if($("#btnDiv").hasClass("orgEdit")){
					$("#updateOrgForm input").removeClass("orgEdit");
					$("#updateOrgForm p").addClass("orgEdit");	
					$("#btnDiv").removeClass("orgEdit");
				}
				else{
					console.info("取消编辑");
					console.info($("#proName-error"));
					$("#proName-error").hide();
					$("#updateOrgForm input").addClass("orgEdit");
					$("#updateOrgForm p").removeClass("orgEdit");	
					$("#btnDiv").addClass("orgEdit");
				}

			});
			$("#updateOrgForm").validate({
				debug:false,
				focusInvalid: true, //当为false时，验证无效时，没有焦点响应  
				onkeyup: true,
				submitHandler:function(form){  
					console.info("------");
					var proId=$("#proList").find(".focus").data("proid");
					/*			 {"owner":"8a809e50536435960153643b33c30003","address":"sssss","create_time":{"date":23,"hours":9,"seconds":42,"month":2,"timezoneOffset":-480,"year":116,"minutes":58,"time":1458698322000,"day":3},"creater":"zhang3","name":"sdasdas","id":"402881f553a131340153a13202ca0000","remarks":"sssss","status":"A"} */	
					var params={id:proId,name:$("#proName").val(),description:$("#remarks").val()};
					_this.confirmUpdatePro(params);
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
						required: "请输入地址",
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
		addProject:function(orgId){
			var _this=this;
			/*			$('#sugonDialog').dialog({
				title: '增加项目',
				width: 600,
				closed: false,
				cache: false,
				href: sugon.rootURL+'/action/project/toAddProject?orgId='+orgId,
				modal: true,
				left:400,
				top:0
			});*/
			$.get(sugon.rootURL+'/action/project/toAddProject?orgId='+orgId, {}, function(str){
				layer.open({
					type: 1,
					title:'添加项目',
					//skin: 'layui-layer-rim', //加上边框
					area: ['620px', '250px'], //宽高
					content: str,
					btn: ['确认 ', '取消'] //只是为了演示
				,yes: function(){	
					if($("#addProjectForm").valid()){
						var params={name:$("#projectName").val(),description:$("#remarks").val(),orgId:orgId};
						console.info(params);
						_this.confirmAddProject(params);
					}

				}
				,btn2: function(){
					layer.closeAll();
				}
				});
			});


		},
		deletePro:function () {
			/*  var row = $("#tt").datagrid("getSelected"); */
			/*	console.info(params);*/
			var proId=$("#proList").find(".focus").data("proid"),
			proName=$("#proList").find(".focus").data("name");
			param={id:proId,name:proName};
			param=JSON.stringify(param);
			layer.confirm('是否删除项目？', {
				title:'删除项目',
				icon: 3,
				btn: ['确认','取消'] //按钮
			}, function(index){
				sugon.load({
					dataType:"json",
					action:sugon.rootURL + "/action/project/deletePro",
					data:param,
					type:'POST',
					contentType:"application/json;charset=UTF-8",
					callback : function(result) {
						if(result.flag){
							toastr.success(result.message);	
							layer.closeAll();
							$("#proList").find(".focus").remove();
							$("#proList").find("a:first").trigger("click");
						}
						else{
							toastr.error(result.message);
							layer.closeAll();
						}
					}
				});
			}); 
		},
		formatOper:function (val,row,index){  
			console.info(row);
			return '<a href="#" onclick="cloudmanager.projectManagement.confirmRemoveVm(\''+row.id+'\')">移除</a>';  
		}  ,

		formatUserOper:function (val,row,index){  
			console.info(row);
			return '<a href="#" onclick="cloudmanager.projectManagement.confirmRemoveUser(\''+row.id+'\',\''+row.realname+'\')">移除</a>';  
		}  ,
		formatName:function (val,row,index){
			return '<a href="#" onclick="cloudmanager.orgManagement.goToDetail(\''+row.id+'\')">'+row.name+'</a>';  
		},
		formatStatus:function(val,row,index){
			var status;
			switch(val){
			case 'STARTING':status='启动中';break;
			case 'CREATING':status='创建中';break;
			default:status='未知状态';
			}
			return status;
		},
		initAdd:function(orgId){
			var _this=this;
			$("#addProjectForm").validate({
				debug:false,
				focusInvalid: true, //当为false时，验证无效时，没有焦点响应  
				onkeyup: true,
				rules: {
					projectName: "required"
				},
				messages: {
					projectName: "请输入项目名称"
				}
			});
		},
		cancelAdd:function(){
			$('#sugonDialog').dialog("close");
		},
		confirmUpdatePro:function(param){		
			params=JSON.stringify(param);
			$.ajax({            
				url : sugon.rootURL + "/action/project/updatePro",
				method : 'POST',
				contentType: "application/json;charset=UTF-8",				
				dataType:"json",
				data:params,
				success : function(result) {
					if(result.flag){
						toastr.success(result.message);
						$("#editBtn").trigger("click");
						$("#proList").find("a.focus").data("remarks",param.description).html("<i class='fa fa-dot-circle-o'></i>"+param.name).trigger("click");
					}
					else{
						toastr.error(result.message);
					}
				}
			});
		},
		confirmAddProject:function(params){
			console.info(params);
			console.info(JSON.stringify(params));
			params=JSON.stringify(params);
			sugon.load({
				dataType:"json",
				action:sugon.rootURL + "/action/project/addProject",
				data:params,
				type:'POST',
				contentType:"application/json;charset=UTF-8",
				callback : function(result) {
					if(result.flag){
						toastr.success(result.message);
						layer.closeAll();
						/*$("#proList .list-group-item:last")。after("<a data-name='sssss0000' data-createtime='2016-04-13 02:35:14' data-remarks='sssss' data-proid='8a80f349540e4c7c01540e54ba6c0005' onclick="cloudmanager.projectManagement.updateByProject('402881ef53c76c170153c771f4840000','8a80f349540e4c7c01540e54ba6c0005',this)" class="list-group-item" href="#"><i class="fa fa-dot-circle-o"></i>sssss0000</a>");*/
						setTimeout(function(){
							toastr.success('添加项目成功');
						},2000);
						window.location.reload();
					}
					else{
						toastr.error(result.message);
					}
				}
			});
		},
		goToDetail:function(id){
			$('#sugonDialog').dialog({
				title: '修改组织',
				width: 600,
				closed: false,
				cache: false,
				href: sugon.rootURL+'/orgs/goToDetail?id='+id,
				modal: true,
				left:400,
				top:100
			});
		},
		updateByProject:function(orgId,proId,thiz){	
			var queryParams1 = $('#vmTable').datagrid('options').queryParams,
			queryParams2 = $('#personTable').datagrid('options').queryParams;

			focusItem=$(thiz),
			proName=focusItem.text(),
			remarks=focusItem.data("remarks"),
			createTime=focusItem.data("createtime");
			$("#proNameP").text(proName);
			$("#proName").val(proName);
			$("#remarksP").text(remarks);
			$("#remarks").val(remarks);
			$("#createTime").text(createTime);
			queryParams1.orgId = orgId;
			queryParams1.proId = proId;
			queryParams2.orgId = orgId;
			queryParams2.proId = proId;

			$("#vmTable").datagrid('reload');
			$("#personTable").datagrid('reload');
			/*			queryParams = $('#personTable').datagrid('options').queryParams;
			queryParams.name = "";
			$("#personTable").datagrid('reload');*/
		},
		/*	addVm:function(orgId){
			var proId=$("#proList").find(".focus").data("proid");
			$('#sugonDialog').dialog({
				title: '增加项目',
				width: 600,
				closed: false,
				cache: true,
				href: sugon.rootURL+'/action/project/toAddVm?orgId='+orgId+'&proId='+proId,
				modal: true,
				left:400,
				top:0
			});
		},*/
		addVm:function(orgId){
			var proId=$("#proList").find(".focus").data("proid"),
			proName=$("#proList").find(".focus").data("name"),
			_this=this;
			$.get(sugon.rootURL+'/action/project/toAddVm?orgId='+orgId+'&proId='+proId+'&proName='+proName, {}, function(str){
				layer.open({
					type: 1,
					title:'添加虚拟机',
					//skin: 'layui-layer-rim', //加上边框
					area: ['620px', '400px'], //宽高
					content: str,
					btn: ['确认 ', '取消'] //只是为了演示
				,yes: function(){	
					var vmId=_this.defaultValue.vmSelection.getValue();
					if(vmId){
						var params={proId:proId,vmId:vmId,proName:proName};
						params=JSON.stringify(params);
						sugon.load({
							dataType:"json",
							action:sugon.rootURL + "/action/project/addVm",
							data:params,
							type:'POST',
							contentType:"application/json;charset=UTF-8",
							callback : function(result) {
								console.info("-----------------------");
								console.info(result.flag);
								if(result.flag){
									toastr.success(result.message);
									layer.closeAll();	
									$("#vmTable").datagrid('load');	
								}
								else{
									toastr.error(result.message);
								}
							}
						});		
					}		     			        
				}
				,btn2: function(){
					layer.closeAll();
				}
				});
			});
		},
		addUser:function(orgId){
			var proId=$("#proList").find(".focus").data("proid"),
			proName=$("#proList").find(".focus").data("name"),
			_this=this;
			$.get(sugon.rootURL+'/action/project/toAddUser?orgId='+orgId+'&proId='+proId+"&proName="+proName, {}, function(str){
				layer.open({
					type: 1,
					title:'添加成员',
					//skin: 'layui-layer-rim', //加上边框
					area: ['620px', '400px'], //宽高
					content: str,
					btn: ['确认 ', '取消'] //只是为了演示
				,yes: function(){	
					var userId=_this.defaultValue.personSelection.getValue();
					if(userId){
						var params={proId:proId,userId:userId,proName:proName};
						params=JSON.stringify(params);
						sugon.load({
							dataType:"json",
							action:sugon.rootURL + "/action/project/addUser",
							data:params,
							type:'POST',
							contentType:"application/json;charset=UTF-8",
							callback : function(result) {
								console.info("-----------------------");
								console.info(result.flag);
								if(result.flag){
									toastr.success(result.message);
									layer.closeAll();	
									$("#personTable").datagrid('reload');	
								}
								else{
									toastr.error(result.message);
								}
							}
						});		
					}		     			        
				}
				,btn2: function(){
					layer.closeAll();
				}
				});
			});
		},
		initAddVm:function(orgId){
			this.defaultValue.vmSelection=new Sugon.ui.selection({
				selector : "vmSele",
				size : 10,
				/*				leftData : [{value:"sss",label:"ssss"},{value:"sss",label:"ssss"}],*/
				width : 480,
				leftAction : {
					type : 'get',
					dataType : 'json',
					url : sugon.rootURL+'/action/project/queryAddingVmlist',
					data:{orgId:orgId}
				} 
			});
		},
		initAddUser:function(orgId,proId){
			this.defaultValue.personSelection=new Sugon.ui.selection({
				selector : "userSele",
				size : 10,
				/*				leftData : [{value:"sss",label:"ssss"},{value:"sss",label:"ssss"}],*/
				width : 480,
				leftAction : {
					type : 'get',
					dataType : 'json',
					url : sugon.rootURL+'/action/project/queryAddingUserlist',
					data:{orgId:orgId,proId:proId}
				} 
			});		
		},
		confirmAddVm:function(orgId,proId){
			var vmId=this.defaultValue.vmSelection.getValue();
			var params={proId:proId,vmId:vmId};
			params=JSON.stringify(params);
			sugon.load({
				dataType:"json",
				action:sugon.rootURL + "/action/project/addVm",
				data:params,
				type:'POST',
				contentType:"application/json;charset=UTF-8",
				callback : function(result) {
					console.info("-----------------------");
					console.info(result.flag);
					if(result.flag){
						toastr.success(result.message);
						$('#sugonDialog').dialog("close");
						$("#vmTable").datagrid('load');	
					}
					else{
						toastr.error(result.message);
					}
				}
			});
		},
		confirmRemoveVm:function(vmId){
			var proId=$("#proList").find(".focus").data("proid"),
			proName=$("#proList").find(".focus").data("name"),
			params={proId:proId,vmId:vmId,proName:proName};
			params=JSON.stringify(params);
			layer.confirm('是否移除该虚拟机？', {
				title:'移除虚拟机',
				icon: 3,
				btn: ['确认','取消'] //按钮
			}, function(index){
				sugon.load({
					dataType:"json",
					action:sugon.rootURL + "/action/project/removeVm",
					data:params,
					type:'POST',
					contentType:"application/json;charset=UTF-8",
					callback : function(result) {
						if(result.flag){
							toastr.success(result.message);
							layer.closeAll();
							$("#vmTable").datagrid('reload');
						}
						else{
							toastr.error(result.message);
						}
					}
				});
			});
		},
		confirmRemoveUser:function(userId,realname){
			var proId=$("#proList").find(".focus").data("proid"),
			proName=$("#proList").find(".focus").data("name"),
			params={proId:proId,userId:userId,proName:proName};
			params=JSON.stringify(params);		
			layer.confirm('是否移除该组织成员？', {
				title:'移除组织成员',
				icon: 3,
				btn: ['确认','取消'] //按钮
			}, function(index){
				sugon.load({
					dataType:"json",
					action:sugon.rootURL + "/action/project/removeUser",
					data:params,
					type:'POST',
					contentType:"application/json;charset=UTF-8",
					callback : function(result) {
						if(result.flag){
							toastr.success(result.message);
							layer.closeAll();
							$("#personTable").datagrid('reload');
							/*						$('#sugonDialog').dialog("close");
						$("#vmTable").datagrid('load');	*/
						}
						else{
							toastr.error(result.message);
						}
					}
				});
			});

		}
}
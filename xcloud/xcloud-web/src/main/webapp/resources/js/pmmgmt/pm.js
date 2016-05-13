cloudmanager.namespace("pmMgmt");
cloudmanager.pmMgmt = {
	openPmRecoveryPage:function(){
		var pms = $("#pmTableId").datagrid("getChecked");
		var pmIds=[];
		if(pms.length<=0){
			toastr.warning('请选择物理机信息！');
		}else{
			var tag=true;
			$.each(pms, function(index, item) {
				pmIds.push(item.id);
		       if(item.orgId==null||item.orgId==""){
		    	   toastr.warning('未分配的物理机不能进行回收！');
		    	   tag=false;
		    	   return false;
		       }
		    });
			if(tag){
				layer.confirm('确认回收？', {
					title:'回收物理机',
					icon: 3,
					btn: ['确认','取消'] //按钮
				}, function(index){
					sugon.load({
						dataType : 'json',
						type : 'POST',
						action : sugon.rootURL + '/pmMgmt/recoveryPm',
						data : {
							pmIds : pmIds,
						},
						callback : function(result) {
							if(result.success){
								layer.close(index);
								toastr.success('回收成功！');
								$("#pmTableId").datagrid('reload');
							}else{
								layer.close(index);
								toastr.error(result.message);
							}
						}
					});
				});
			}
		}
	},
	openPmDistributionPage:function(){
		var pms = $("#pmTableId").datagrid("getChecked");
		var pmIds=[];
		if(pms.length<=0){
			toastr.warning('请选择物理机信息！');
		}else{
			var tag=true;
			$.each(pms, function(index, item) {
				pmIds.push(item.id);
		       if(item.orgId!=null&&item.orgId!=""){
		    	   toastr.warning('已分配的物理机不能进行再分配！');
		    	   tag=false;
		    	   return false;
		       }
		    });
			if(tag){
				$.get(sugon.rootURL+'/pmMgmt/openPmDistributionPage', {}, function(str){
					layer.open({
						  type: 1,
						  title:'分配物理机',
						  area: ['400px', ''], //宽高
						  content: str,
						  btn:['确认','取消'],
						  yes: function(index, layero){
							  var orgId = $("#orgSelectId option:selected").val();
							  var orgName = $("#orgSelectId option:selected").text();
							  if(orgId!=""){
								  sugon.load({
										dataType : 'json',
										type : 'POST',
										action : sugon.rootURL + '/pmMgmt/distributionPm',
										data : {
											pmIds : pmIds,
											orgId : orgId,
											orgName : orgName,
										},
										callback : function(result) {
											if(result.success){
												layer.close(index);
												toastr.success('分配成功！');
												$("#pmTableId").datagrid('reload');
											}else{
												toastr.error(result.message);
											}
										}
									});
							  }else{
								  toastr.warning('请选择组织！');
							  }
						  },
						  btn2: function(index, layero){
						  },
					});
				});
			}
		}
	},
	openDeletePmPage:function(id){
		layer.confirm('确认删除？', {
			title:'删除物理机',
			icon: 3,
			btn: ['确认','取消'] //按钮
		}, function(index){
			sugon.load({
				dataType : 'json',
				type : 'POST',
				action : sugon.rootURL + '/pmMgmt/deletePm',
				data : {
					id : id,
				},
				callback : function(result) {
					if(result.success){
						layer.close(index);
						toastr.success('删除成功！');
						$("#pmTableId").datagrid('reload');
					}else{
						layer.close(index);
						toastr.error(result.message);
					}
				}
			});
		});
	},
	initUpdatePm:function(id){
		var _this=this;
		jQuery.validator.addMethod("ip", function(value, element) {    
		      return this.optional(element) || /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/.test(value);    
		    }, "请填写正确的IP地址");
		jQuery.validator.addMethod("mac", function(value, element) {       
			     var mac = /[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}/;    //电话号码格式010-12345678   
			     return this.optional(element) || (mac.test(value));       
			}, "请填写正确的MAC地址");   
		
		$("#updatePmForm").validate({
			errorPlacement:function(error,element) {  
				error.appendTo(element.parent().next("div"));
		   	},
			submitHandler:function(form){
				sugon.load({
					type : 'POST',
					action : sugon.rootURL + '/pmMgmt/updatePm',
					dataType : 'json',
					data:{
						id:id,
						name:$("#name").val(),
						ip:$("#ip").val(),
						os:$("#os").val(),
						ipmiIp:$("#ipmiIp").val(),
						ipmiUserName:$("#ipmiUserName").val(),
						ipmiPassword:$("#ipmiPassword").val(),
						mac:$("#mac").val(),
						hostType:$("#hostType").val(),
						cpuType:$("#cpuType").val(),
						deviceModel:$("#deviceModel").val(),
						serialNumber:$("#serialNumber").val(),
						description:$("#description").val(),
					},
					callback : function(result) {
						if(result.success){
							layer.closeAll();
							$("#pmTableId").datagrid('reload');
							toastr.success('创建成功！');
						}else{
							toastr.error(result.message);
						}
						
					}
				});
			},
			rules: {
				name: {
					required: true,
					rangelength:[3,20]
				},
				ip: {
					required: true,
					ip:true
				},
				os: {
					required: false,
					maxlength:50
				},
				ipmiIp: {
					required: false,
					ip:true
				},
				ipmiUserName:{
					required: false,
					rangelength:[3,50]
				},
				ipmiPassword:{
					required: false,
					rangelength:[3,50]
				},
				mac:{
					required: false,
					mac:true,
				},
				hostType:{
					required: false,
					maxlength:50,
				},
				cpuType:{
					required: false,
					maxlength:50,
				},
				deviceModel:{
					required: false,
					maxlength:50,
				},
				serialNumber:{
					required: false,
					maxlength:100,
				},
				description: {
					maxlength:120
				}
			},
			messages : {
				name: {
					rangelength:"请输入长度为 3 至 20 之间的字符串！"
				},
		    },
		});
	},
	openUpdatePmPage:function(id){
		$.get(sugon.rootURL+'/pmMgmt/openUpdatePmPage?id='+id, {}, function(str){
			layer.open({
				  type: 1,
				  title:'修改物理机',
				  area: ['800px', '600px'], //宽高
				  content: str,
				  btn:['确认','取消'],
				  yes: function(index, layero){
					  $("#updatePmForm").submit();
				  },
				  btn2: function(index, layero){
				  },
			});
		});
	},
	openPmDetail:function(id){
		$.get(sugon.rootURL+'/pmMgmt/openPmDetailPage?id='+id, {}, function(str){
			layer.open({
				  type: 1,
				  title:'物理机详情',
				  area: ['500px', ''], //宽高
				  content: str,
			});
		});
	},
	initCreatePm:function(){
		var _this=this;
		jQuery.validator.addMethod("ip", function(value, element) {    
		      return this.optional(element) || /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/.test(value);    
		    }, "请填写正确的IP地址");
		jQuery.validator.addMethod("mac", function(value, element) {       
			     var mac = /[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}/;    //电话号码格式010-12345678   
			     return this.optional(element) || (mac.test(value));       
			}, "请填写正确的MAC地址");   
		
		$("#createPmForm").validate({
			errorPlacement:function(error,element) {  
				error.appendTo(element.parent().next("div"));
		   	},
			submitHandler:function(form){
				sugon.load({
					type : 'POST',
					action : sugon.rootURL + '/pmMgmt/createPm',
					dataType : 'json',
					data:{
						name:$("#name").val(),
						ip:$("#ip").val(),
						os:$("#os").val(),
						ipmiIp:$("#ipmiIp").val(),
						ipmiUserName:$("#ipmiUserName").val(),
						ipmiPassword:$("#ipmiPassword").val(),
						mac:$("#mac").val(),
						hostType:$("#hostType").val(),
						cpuType:$("#cpuType").val(),
						deviceModel:$("#deviceModel").val(),
						serialNumber:$("#serialNumber").val(),
						description:$("#description").val(),
					},
					callback : function(result) {
						if(result.success){
							layer.closeAll();
							$("#pmTableId").datagrid('reload');
							toastr.success('创建成功！');
						}else{
							toastr.error(result.message);
						}
						
					}
				});
			},
			rules: {
				name: {
					required: true,
					rangelength:[3,20]
				},
				ip: {
					required: true,
					ip:true
				},
				os: {
					required: false,
					maxlength:50
				},
				ipmiIp: {
					required: false,
					ip:true
				},
				ipmiUserName:{
					required: false,
					rangelength:[3,50]
				},
				ipmiPassword:{
					required: false,
					rangelength:[3,50]
				},
				mac:{
					required: false,
					mac:true,
				},
				hostType:{
					required: false,
					maxlength:50,
				},
				cpuType:{
					required: false,
					maxlength:50,
				},
				deviceModel:{
					required: false,
					maxlength:50,
				},
				serialNumber:{
					required: false,
					maxlength:100,
				},
				description: {
					maxlength:120
				}
			},
			messages : {
				name: {
					rangelength:"请输入长度为 3 至 20 之间的字符串！"
				},
		    },
		});
	},
	openPmCreatePage:function(){
		$.get(sugon.rootURL+'/pmMgmt/openPmCreatePage', {}, function(str){
			layer.open({
				  type: 1,
				  title:'创建物理机',
				  area: ['800px', '600px'], //宽高
				  content: str,
				  btn:['确认','取消'],
				  yes: function(index, layero){
					  $("#createPmForm").submit();
				  },
				  btn2: function(index, layero){
				  },
			});
		});
	},
	init : function() {
		$('#pmTableId').datagrid({
			url : sugon.rootURL + '/pmMgmt/queryPmTable',
			method : 'post',
			striped : true,
			fitColumns : true,
			singleSelect : false,
			rownumbers : true,
			pagination : true,
			pageNumber : 1,
			nowrap : false,
			pageSize : 10,
			toolbar : '#pmtb',
			pageList : [ 5, 10, 20, 50, 100],
			queryParams : {},
			loadMsg : "加载中.....",
			showFooter : true,
			columns : [ [ {
                field:"id",
                checkbox:true,
                width:50
            },{
				field : "name",
				title : "名称",
				align : "left",
				width : '15%',
				formatter : cloudmanager.pmMgmt.formatName
			}, {
				field : "ip",
				title : "物理机IP地址",
				align : "center",
				width : '10%',
			}, {
				field : "ipmiIp",
				title : "物理机IPMI IP地址",
				align : "center",
				width : '15%',
			}, {
				field : "orgName",
				title : "组织名称",
				align : "center",
				width : '15%',
			}, {
				field : "createDate",
				title : "创建时间",
				align : "center",
				width : '14%',
			}, {
				field : "operate",
				title : "操作",
				align : "center",
				width : '15%',
				formatter : cloudmanager.pmMgmt.formatOper
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
		var p = $('#pmTableId').datagrid('getPager');
		$(p).pagination({
			layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
			displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
			beforePageText:'跳转到第 ',
			afterPageText:' 页'
		});
		$('#pmSearchInputId').searchbox({
			searcher:function(value,name){
				var queryParams = $('#pmTableId').datagrid('options').queryParams;
				queryParams.name = value;
				$("#pmTableId").datagrid('reload');
			},
		    menu:'#tableSearch',
		    prompt:'请输入查询内容'
		});
		$('#pmSearchInputId').searchbox('addClearBtn', 'icon-clear');
	},
	doSearch : function(value, name) {
		var queryParams = $('#pmTableId').datagrid('options').queryParams;
		queryParams.name = value;
		$("#pmTableId").datagrid('reload');
	},
	formatName : function(val, row, index) {
		return '<a href="#" onclick="cloudmanager.pmMgmt.openPmDetail(\''
				+ row.id + '\')">' + row.name + '</a>';
	},
	formatOper : function(val, row, index) {
		if(row.orgId!=null&&row.orgId!=""){
			return '<a href="#" onclick="cloudmanager.pmMgmt.openUpdatePmPage(\''+ row.id + '\')">修改</a>'
		}else{
			return '<a href="#" onclick="cloudmanager.pmMgmt.openUpdatePmPage(\''+ row.id + '\')">修改</a>'
			+'&nbsp;&nbsp;&nbsp;&nbsp;'
			+'<a href="#" onclick="cloudmanager.pmMgmt.openDeletePmPage(\''+row.id + '\')">删除</a>';
		}
	},
	
}
cloudmanager.namespace("applicationMgmt");
cloudmanager.applicationMgmt = {
	defaultValue:{
		memorySele:null,
		cpuSele:null,
		vmNum:null,
		clickDiskNum:0,
		clickNetNum:0
	},
	initRefuse:function(id){
		var _this=this;
		$("#refuseForm").validate({
			errorPlacement:function(error,element) {  
				error.appendTo(element.parent().next("div"));
		   	},
			submitHandler:function(form){
				sugon.load({
					type : 'POST',
					action : sugon.rootURL + '/applicationMgmt/refuse',
					dataType : 'json',
					data:{
						id:id,
						refusalReason:$("#refusalReason").val(),
					},
					callback : function(result) {
						if(result.success){
							layer.closeAll();
							$("#applicationTableId").datagrid('reload');
							toastr.success('拒绝成功！');
						}else{
							toastr.error(result.message);
						}
						
					}
				});
			},
			rules: {
				refusalReason: {
					required: true,
				},
			},
		});
	},
	openRefusePage:function(id){
		$.get(sugon.rootURL+'/applicationMgmt/openRefusePage?id='+id, {}, function(str){
			layer.open({
				  type: 1,
				  title:'审核不通过',
				  area: ['800px', ''], //宽高
				  content: str,
				  btn:['确认','取消'],
				  yes: function(index, layero){
					  $("#refuseForm").submit();
				  },
				  btn2: function(index, layero){
				  },
			});
		});
	},
	openPassedPage:function(id){
		layer.confirm('是否审批通过？', {
			title:'审批通过',
			icon: 3,
			btn: ['确认','取消'] //按钮
		}, function(index){
			sugon.load({
				dataType : 'json',
				type : 'POST',
				action : sugon.rootURL + '/applicationMgmt/passed',
				data : {
					id : id,
				},
				callback : function(result) {
					if(result.success){
						layer.close(index);
						toastr.success(result.message);
						$("#applicationTableId").datagrid('reload');
					}else{
						layer.close(index);
						toastr.error(result.message);
					}
				}
			});
		});
	},
	
	openApprovalPage:function(id){
		layer.confirm('是否提交？', {
			title:'提交申请单',
			icon: 3,
			btn: ['确认','取消'] //按钮
		}, function(index){
			sugon.load({
				dataType : 'json',
				type : 'POST',
				action : sugon.rootURL + '/applicationMgmt/approvalApplication',
				data : {
					id : id,
				},
				callback : function(result) {
					if(result.success){
						layer.close(index);
						toastr.success(result.message);
						$("#applicationTableId").datagrid('reload');
					}else{
						layer.close(index);
						toastr.error(result.message);
					}
				}
			});
		});
	},
	
	openDeleteApplicationPage:function(id,name){
		layer.confirm('是否删除申请单？', {
			title:'删除申请单',
			icon: 3,
			btn: ['确认','取消'] //按钮
		}, function(index){
			sugon.load({
				dataType : 'json',
				type : 'POST',
				action : sugon.rootURL + '/applicationMgmt/deleteApplication',
				data : {
					id : id,
					name : name,
				},
				callback : function(result) {
					if(result.success){
						layer.close(index);
						toastr.success('删除成功！');
						$("#applicationTableId").datagrid('reload');
					}else{
						layer.close(index);
						toastr.error(result.message);
					}
				}
			});
		});
	},
	
	openUpdateApplicationPage:function(id){
		$.get(sugon.rootURL+'/applicationMgmt/openApplicationUpdatePage?id='+id, {}, function(str){
			layer.open({
				  type: 1,
				  title:'修改申请单',
				  area: ['900px', '600'], //宽高
				  content: str,
				  btn:['提交','保存'],
				  yes: function(index, layero){
					  $("#updateApplicationForm").submit();
				  },
				  btn2: function(index, layero){
				  }
			});
		});
	},
	/*
	initUpdateApplication:function(){
		var _this=this;
		$("#updateApplicationForm").validate({
			errorPlacement:function(error,element) {  
				error.appendTo(element.parent().next("div"));
		   	},
			submitHandler:function(form){
				sugon.load({
					type : 'POST',
					action : sugon.rootURL + '/applicationMgmt/updateApplication',
					dataType : 'json',
					data:{
						applyId:$("#applyId").val(),
						applyName:$("#applyName").val(),
						remark:$("#remark").val(),
						status:$("#status").val(),
					},
					callback : function(result) {
						if(result.success){
							layer.closeAll();
							$("#applicationTableId").datagrid('reload');
							toastr.success('修改成功！');
						}else{
							toastr.error(result.message);
						}
						
					}
				});
			},
			rules: {
				applyName: {
					required: true,
					rangelength:[3,20]
				},
				remark: {
					required: true,
					rangelength:[3,120]
				},
			},
		});
	},
	*/
	openApplyCreatePage:function(){
		var _this=this;
		$.get(sugon.rootURL+'/applicationMgmt/openApplicationCreatePage', {}, function(str){
			layer.open({
				  type: 1,
				  title:'创建申请单',
				  area: ['1000px', '600px'], //宽高
				  content: str,
				  btn:['提交','取消'],
				  yes: function(index, layero){
					  _this.confirmAddVm();
				  },
				  btn2: function(index, layero){
				  },
			});
		});
	},
	confirmAddVm:function(params){
		if($("#createApplicationForm").valid()){
			var name=$("#vmName").val();
			var orgId=$("#orgId").val();
			var vdcId=$("input[name='computingPool']:checked").data("vdcid");
			var osUsername=$("#username").val();
			var osPassword=$("#password").val();
			var cPoolId=$("input[name='computingPool']:checked").val();
			var vCpuNumer=$("input[name='cpu_search']").val();
			var vMemCapacity=$("input[name='memory_search']").val()*1024;
			var sPoolId=$("#storPoolSele").val();
			var storCapacity=$("#vmTempSele").find("option:selected").data("storcapacity");
			var templateId=$("#vmTempSele").val();
			var vmNumber=this.defaultValue.vmNum.getValue();
			if(undefined==vdcId){
				toastr.warning("请选择提供者vDC！");
			}else if(""==templateId){
				toastr.warning("请选择模板！");
			}else{
				var templateOs=$("#vmTempSele option:selected").data("os");
				if(templateOs.indexOf("Windows")>1){
					osUsername="Administor";
				}else{
					osUsername="root";
				}
				if($("#setdelay").hasClass("active")){
					osPassword=null;
				}
				var netslist=new Array();
				if(!$("#ipForm").is(":hidden")){
					$("div.list-group-item").each(function(){
						var thiz=$(this);
						var data={netId:thiz.data("id"),vlan:thiz.data("vlan"),ip:thiz.data("ip"),gateway:thiz.data("gateway"),dns:thiz.data("dns"),subnet:thiz.data("subnet")};
						netslist.push(data);		
					});
				}
				if($("#addVmForm").valid()){
					var params = {
						name : name,
						remarks : "",
						orgId : orgId,
						vdcId : vdcId,
						osUsername : osUsername,
						osPassword:osPassword,
						cPoolId : cPoolId,
						vCpuNumer : vCpuNumer,
						vMemCapacity : vMemCapacity,
						sPoolId : sPoolId,
						storCapacity : storCapacity,
						templateId : templateId,
						nets : netslist
					};
					params=JSON.stringify(params);
					sugon.load({
						dataType:"json",
						action:sugon.rootURL + "/applicationMgmt/createApplication",
						data:{
							vmInfo:params,
							applyName:$("#applyName").val(),
							remark:$("#remark").val(),
							vmNumber:vmNumber
						},
						type:'POST',
						callback : function(result) {
							if(result.success){
								toastr.success(result.message);
								layer.closeAll();
								$("#applicationTableId").datagrid('load');	
							}
							else{
								toastr.error(result.message);
							}
						}
					});
				}
			}
		}
	},
	initCreateApplicationVm:function(){
		var _this=this;
		$("#setdelay").on("click",function(){
			//$("#usernamepart").hide();
			$("#passwordpart").hide();
			$("#password2part").hide();
		});
		$("#setnow").on("click",function(){
			//$("#usernamepart").show();
			$("#passwordpart").show();
			$("#password2part").show();
		});
		vmTempSele=$("#vmTempSele");
		//orgSele=$("#orgSele");
		mem=vmTempSele.find("option:selected").data("memory")/1024;
		cpuNum=vmTempSele.find("option:selected").data("cpunum");
		
		_this.renderSlider(mem,cpuNum);
		$("#vmAddConfig").bind("click",function(){
			_this.updateConfigInfo();
		});
		this.defaultValue.vmNum=new Sugon.ui.slider({
			selector : '#vmNumber',
			setting : [{
				"unit": 1,
				"min": 1,
				"max": 4,
				"align":"right",
				"label" : "4台"
			},{
				"unit": 1,
				"min": 4,
				"max": 8,
				"align":"right",
				"label" : "8台"
			},{
				"unit": 1,
				"min": 8,
				"max": 12,
				"align":"right",
				"label" : "12台"
			}],
			textAlign : 'bottom',// content bottom
			width : 460,
			hasInput : true,
			inputName : 'vm_number',
			inputUnit : '台',
			inputHeight : 10,
			inputWidth : 45,
			defaultValue : 1,
			fontSize : 12,
			height:28,
			changeHandler:function(res){
				if(res>1){
					$("#addNetDivId").hide();
				}
				else{
					$("#addNetDivId").show();
				}
			}
		});
		vmTempSele.on("change",function(){
			var memory=$(this).find("option:selected").data("memory")/1024,
			cpunum=$(this).find("option:selected").data("cpunum");
			_this.renderSlider(memory,cpunum);

		});
		/**
		orgSele.on("change",function(){
			var orgId=$(this).find("option:selected").val();
			$("#netSeleInfo").html("");
			$.ajax({
				url : sugon.rootURL + "/action/vm/queryNetsByOrg",
				type : 'get',
				contentType: "application/json; charset=utf-8",
				data:{orgId:orgId},
				success : function(result) {
					console.info(result);
					$("#netSele").html("");
					if(result){
						for(var a in result){
							console.info(result[a]);
							$("#netSele").append("<option data-vlan='"+result[a].vlanNO+"' data-added='false' data-gateway='"+result[a].gateway+"' data-dns='"+result[a].dns+"' data-subnet='"+result[a].subNet+"' value='"+result[a].netPoolId+"'>"+result[a].netName+"</option>");
						}					
					}
					else{
						$("#netSeleInfo").html("该组织没有网络池");
					}

				}
			});

		});
		*/
		
		$("#vdcSele label").on("click",function(){
			var proVDCId=$(this).find(":radio").data("vdcid");
			$.ajax({
				url : sugon.rootURL + "/applicationMgmt/queryStorByProVDC",
				type : 'get',
				dataType:'json',
				contentType: "application/json; charset=utf-8",
				data:{proVDCId:proVDCId},
				success : function(result) {
					$("#storPoolSele").html("");
					if(result){
						for(var a in result){
							if(typeof result[a] !="function" ){
								$("#storPoolSele").append("<option value='"+result[a].spId+"'>"+result[a].spName+"</option>");
							}
						}					
					}else{
						$("#storPoolSele").html("该提供者VDC没有存储池");
					}

				}
			});
		});
		$("#addVmForm").validate({
			submitHandler:function(form){  
			},
			rules: {
				vmName:"required",
				username: "required",
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
				vmName:"请输入虚拟机名称",
				username: "请输入姓名",
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
        $("#addNetcardBtn").on("click",function(){
        	var curOpt=$("#netSele option:selected");
        	if(curOpt.length==0&&$("#netSeleInfo").length==0){
        		$("#netSele").after('<span style="margin-left: 10px; color: red" id="netSeleInfo">无可以使用的网络池了</span>');	
        	}
        	if($("#ipForm").valid()&&curOpt.length==1){                                                                                
        		var item='<div class="list-group-item" data-gateway="'+curOpt.data("gateway")+'" data-subnet="'+curOpt.data("subnet")+'" data-vlan="'+curOpt.data("vlan")+'" data-dns="'+curOpt.data("dns")+'" data-id="'+curOpt.val()+'" data-name="'+curOpt.text()+'" data-ip="'+$("#netIP").val()+'"><a href="javascript:;" class="pull-right" onclick="cloudmanager.applicationMgmt.removeNetItem(this)"><i class="fa fa-times-circle"></i></a>'+curOpt.text()+',IP:'+$("#netIP").val()+'</div>';
	        	$("#addedNetcard").append(item);
	        	curOpt.remove();
        	}    	
        });
		_this.updateConfigInfo();
	},
	initCreateApplication:function(){
		$("#applicationType").change(function(){
			var applicationType=$("#applicationType option:selected").val();
			sugon.load({
				selector : '#applicationInfo',
				type : 'POST',
				action : sugon.rootURL + '/applicationMgmt/applicationTypeChange',
				data:{
					applicationType:applicationType
				},
				callback : function(result) {
				}
			});
		});
		var _this=this;
		$.validator.addMethod("applicationType", function(value, element, arg){
			if(value==""){
				return false;
			}else{
				return true;
			}
		}, "请选择申请单类型！");
		$("#createApplicationForm").validate({
			errorPlacement:function(error,element) {  
				error.appendTo(element.parent().next("div"));
		   	},
			submitHandler:function(form){
			},
			rules: {
				applyName: {
					required: true,
					rangelength:[3,20]
				},
				remark: {
					required: true,
					rangelength:[3,120]
				},
				applicationType : {
					applicationType:true
				},
			},
		});
	},
	removeNetItem:function(thiz){
		var item=$(thiz).parent(".list-group-item");
		var id=item.data("id");
		var name=item.data("name");
		var dns=item.data("dns");
		var subnet=item.data("subnet");
		var vlan=item.data("vlan");
		var gateway=item.data("gateway");
		console.info()
		var option="<option value='"+id+"' data-dns='"+dns+"' data-subnet='"+subnet+"' data-gateway='"+gateway+"' data-vlan='"+vlan+"' data-added='false'>"+name+"</option>";
		if($("#netSeleInfo").length!=0){
			$("#netSeleInfo").remove();
		}
		$("#netSele").append(option);
		item.remove();
	},
	updateConfigInfo:function(){
		$("#con_vmName").text($("#vmName").val());
		$("#con_proVDC").text($("input[name='computingPool']:checked").data("name"));
		//$("#con_org").text($("#orgSele option:checked").text());
		$("#con_cpu").text(vCpuNumer=$("input[name='cpu_search']").val());
		$("#con_memory").text($("input[name='memory_search']").val()+"GB");
		$("#con_network").text($("#netSele option:checked").text());
		$("#con_storage").text($("#storPoolSele option:checked").text());
	},
	renderSlider:function(memory,cpunum){
		$('#cpuDivId').html("");
		this.defaultValue.cpuSele=new Sugon.ui.slider({
			selector : '#cpuDivId',
			setting : [{
				"unit": 1,
				"min": 1,
				"max": 8,
				"align":"right",
				"label" : "8核"
			},{
				"unit": 1,
				"min": 8,
				"max": 16,
				"align":"right",
				"label" : "16核"
			},{
				"unit": 1,
				"min": 16,
				"max": 32,
				"align":"right",
				"label" : "32核"
			},{
				"unit": 1,
				"min": 32,
				"max": 64,
				"align":"right",
				"label" : "64核"
			}],
			textAlign : 'bottom',// content bottom
			width : 460,
			hasInput : true,
			inputName : 'cpu_search',
			inputUnit : '核',
			inputHeight : 10,
			inputWidth : 45,
			defaultValue : cpunum,
			fontSize : 12,
			height:28
		});
		$('#memoryDivId').html("");
		this.defaultValue.memorySele=new Sugon.ui.slider({
			selector : '#memoryDivId',
			setting : [{
				"unit": 1,
				"min": 1,
				"max": 4,
				"align":"right",
				"label" : "4GB"
			},{
				"unit": 1,
				"min": 4,
				"max": 16,
				"align":"right",
				"label" : "16GB"
			},{
				"unit": 1,
				"min": 16,
				"max": 32,
				"align":"right",
				"label" : "32GB"
			},{
				"unit": 1,
				"min": 32,
				"max": 64,
				"align":"right",
				"label" : "64GB"
			},{
				"unit": 1,
				"min": 64,
				"max": 128,
				"align":"right",
				"label" : "128GB"
			},{
				"unit": 1,
				"min": 128,
				"max": 256,
				"align":"right",
				"label" : "256GB"
			},{
				"unit": 1,
				"min": 256,
				"max": 512,
				"align":"right",
				"label" : "512GB"
			}],
			textAlign : 'bottom',// content bottom
			width : 460,
			hasInput : true,
			inputName : 'memory_search',
			inputUnit : 'GB',
			inputHeight : 10,
			inputWidth : 45,
			defaultValue : memory,
			fontSize : 12,
			height:28
		});
	},
	initOrgU : function() {
		$('#applicationTableId').datagrid({
			url : sugon.rootURL + '/applicationMgmt/queryApplicationTable',
			method : 'post',
			striped : true,
			fitColumns : true,
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			pageNumber : 1,
			nowrap : false,
			pageSize : 10,
			toolbar : '#applicationtb',
			pageList : [ 5, 10, 20, 50, 100],
			queryParams : {},
			loadMsg : "加载中.....",
			showFooter : true,
			columns : [ [ {
				field : "name",
				title : "名称",
				align : "left",
				width : '18%',
				formatter : cloudmanager.applicationMgmt.formatName,
			}, {
				field : "status",
				title : "申请单状态",
				align : "center",
				width : '10%',
				formatter : cloudmanager.applicationMgmt.formatStatus
			},{
				field : "remark",
				title : "备注",
				align : "center",
				width : '14%'
			}, {
				field : "refusalReason",
				title : "拒绝原因",
				align : "center",
				width : '18%',
			}, {
				field : "createDate",
				title : "创建时间",
				align : "center",
				width : '12%'
			}, {
				field : "operate",
				title : "操作",
				align : "center",
				width : '15%',
				formatter : cloudmanager.applicationMgmt.formatOperOrgU
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
		var p = $('#applicationTableId').datagrid('getPager');
		$(p).pagination({
			layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
			displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
			beforePageText:'跳转到第 ',
			afterPageText:' 页'
		});
		$('#applicationSearchInputId').searchbox({
			searcher:function(value,name){
				var queryParams = $('#applicationTableId').datagrid('options').queryParams;
				queryParams.name = value;
				$("#applicationTableId").datagrid('reload');
			},
		    menu:'#tableSearch',
		    prompt:'请输入查询内容'
		});
		$('#applicationSearchInputId').searchbox('addClearBtn', 'icon-clear');
	},
	initOrgM : function() {
		$('#applicationTableId').datagrid({
			url : sugon.rootURL + '/applicationMgmt/queryApplicationTable',
			method : 'post',
			striped : true,
			fitColumns : true,
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			pageNumber : 1,
			nowrap : false,
			pageSize : 20,
			toolbar : '#applicationtb',
			pageList : [ 5, 10, 20, 50, 100],
			queryParams : {},
			loadMsg : "加载中.....",
			showFooter : true,
			columns : [ [ {
				field : "name",
				title : "名称",
				align : "left",
				width : '18%',
				formatter : cloudmanager.applicationMgmt.formatName,
			}, {
				field : "status",
				title : "申请单状态",
				align : "center",
				width : '10%',
				formatter : cloudmanager.applicationMgmt.formatStatus
			},{
				field : "remark",
				title : "备注",
				align : "center",
				width : '14%'
			}, {
				field : "refusalReason",
				title : "拒绝原因",
				align : "center",
				width : '18%',
			}, {
				field : "createDate",
				title : "创建时间",
				align : "center",
				width : '12%'
			}, {
				field : "operate",
				title : "操作",
				align : "center",
				width : '15%',
				formatter : cloudmanager.applicationMgmt.formatOperOrgM
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
		var p = $('#applicationTableId').datagrid('getPager');
		$(p).pagination({
			layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
			displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
			beforePageText:'跳转到第 ',
			afterPageText:' 页'
		});
		$('#applicationSearchInputId').searchbox({
			searcher:function(value,name){
				var queryParams = $('#applicationTableId').datagrid('options').queryParams;
				queryParams.name = value;
				$("#applicationTableId").datagrid('reload');
			},
		    menu:'#tableSearch',
		    prompt:'请输入查询内容'
		});
		$('#applicationSearchInputId').searchbox('addClearBtn', 'icon-clear');
	},
	initOpeM : function() {
		$('#applicationTableId').datagrid({
			url : sugon.rootURL + '/applicationMgmt/queryApplicationTable',
			method : 'post',
			striped : true,
			fitColumns : true,
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			pageNumber : 1,
			nowrap : false,
			pageSize : 10,
			toolbar : '#applicationtb',
			pageList : [ 5, 10, 20, 50, 100],
			queryParams : {},
			loadMsg : "加载中.....",
			showFooter : true,
			columns : [ [ {
				field : "name",
				title : "名称",
				align : "left",
				width : '18%',
				formatter : cloudmanager.applicationMgmt.formatName,
			}, {
				field : "status",
				title : "申请单状态",
				align : "center",
				width : '10%',
				formatter : cloudmanager.applicationMgmt.formatStatus
			},{
				field : "remark",
				title : "备注",
				align : "center",
				width : '14%',
			}, {
				field : "refusalReason",
				title : "拒绝原因",
				align : "center",
				width : '18%',
			}, {
				field : "createDate",
				title : "创建时间",
				align : "center",
				width : '12%'
			}, {
				field : "operate",
				title : "操作",
				align : "center",
				width : '15%',
				formatter : cloudmanager.applicationMgmt.formatOperOpeM
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
		var p = $('#applicationTableId').datagrid('getPager');
		$(p).pagination({
			layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
			displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
			beforePageText:'跳转到第 ',
			afterPageText:' 页'
		});
		$('#applicationSearchInputId').searchbox({
			searcher:function(value,name){
				var queryParams = $('#applicationTableId').datagrid('options').queryParams;
				queryParams.name = value;
				$("#applicationTableId").datagrid('reload');
			},
		    menu:'#tableSearch',
		    prompt:'请输入查询内容'
		});
		$('#applicationSearchInputId').searchbox('addClearBtn', 'icon-clear');
	},
	applicationDetail:function(id){
		$.get(sugon.rootURL+'/applicationMgmt/applicationDetail?id='+id, {}, function(str){
			layer.open({
				  type: 1,
				  title:'申请单详情',
				  area: ['500px', ''], //宽高
				  content: str,
			});
		});
	},
	formatName : function(val, row, index) {
		return '<a href="#" onclick="cloudmanager.applicationMgmt.applicationDetail(\''
				+ row.id + '\')">' + row.name + '</a>';
	},
	doSearch : function(value, name) {
		var queryParams = $('#applicationTableId').datagrid('options').queryParams;
		queryParams.name = value;
		$("#applicationTableId").datagrid('reload');
	},
	formatRefusalReason: function(val, row, index) {
		return '<span  id="'+row.id+'"  onclick="cloudmanager.applicationMgmt.tips(\''+row.refusalReason+'\',\''+row.id+'\')">'+row.refusalReason+'</span>'
	},
	formatRemark : function(val, row, index) {
		return '<span  id="'+row.id+'"  onclick="cloudmanager.applicationMgmt.tips(\''+row.remark+'\',\''+row.id+'\')">'+row.remark+'</span>'
	},
	tips:function(val,id) {
		layer.tips(val, '#'+id);
	},
	formatStatus: function(val, row, index) {
		var displayName="新建";
		if(val=="1"){
			displayName="一级审批";
		}else if(val=="2"){
			displayName="二级审批";
		}else if(val=="3"){
			displayName="审批通过";
		}else if(val=="4"){
			displayName="审批拒绝";
		}
		return displayName;
	},
	formatOperOrgU : function(val, row, index) {
		var value='';
		if(row.status=='4'){
			value=value+'<a href="#" onclick="cloudmanager.applicationMgmt.openDeleteApplicationPage(\''+ row.id + '\',\''+ row.name + '\')">删除</a>';
		}
		return value
	},
	formatOperOrgM : function(val, row, index) {
		var value='';
		if(row.userRoleCoed=="org_user"&&row.status=='1'){
			value=value+'<a href="#" onclick="cloudmanager.applicationMgmt.openPassedPage(\''+row.id + '\')">通过</a>'
			+'&nbsp;&nbsp;&nbsp;&nbsp;'
			+'<a href="#" onclick="cloudmanager.applicationMgmt.openRefusePage(\''+ row.id + '\')">拒绝</a>';
		}
		if(row.userRoleCoed=="org_manager"&&row.status=='4'){
			value=value+'<a href="#" onclick="cloudmanager.applicationMgmt.openDeleteApplicationPage(\''+ row.id + '\',\''+ row.name + '\')">删除</a>';
		}
		return value
	},
	formatOperOpeM : function(val, row, index) {
		var value='';
		if(row.userRoleCoed=="org_manager"&&row.status=='1'){
			value=value+'<a href="#" onclick="cloudmanager.applicationMgmt.openPassedPage(\''+row.id + '\')">通过</a>'
			+'&nbsp;&nbsp;&nbsp;&nbsp;'
			+'<a href="#" onclick="cloudmanager.applicationMgmt.openRefusePage(\''+ row.id + '\')">拒绝</a>';
		}
		if(row.userRoleCoed=="org_user"&&row.status=='2'){
			value=value+'<a href="#" onclick="cloudmanager.applicationMgmt.openPassedPage(\''+row.id + '\')">通过</a>'
			+'&nbsp;&nbsp;&nbsp;&nbsp;'
			+'<a href="#" onclick="cloudmanager.applicationMgmt.openRefusePage(\''+ row.id + '\')">拒绝</a>';
		}
		return value
	},
}
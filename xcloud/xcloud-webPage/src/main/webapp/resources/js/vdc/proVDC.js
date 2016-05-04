cloudmanager.namespace("proVDC");
cloudmanager.proVDC = {
	init : function() {
		$('#proVDCTableId').datagrid({
			url : sugon.rootURL + '/proVDC/queryPorVDCTable',
			method : 'post',
			striped : true,
			fitColumns : true,
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			pageNumber : 1,
			nowrap : false,
			pageSize : 10,
			toolbar : '#proVDCtb',
			pageList : [ 5, 10, 20, 50, 100],
			queryParams : {},
			loadMsg : "加载中.....",
			showFooter : true,
			columns : [ [ {
				field : "name",
				title : "名称",
				align : "left",
				width : '25%',
				formatter : cloudmanager.proVDC.formatName
			}, {
				field : "vCpuNum",
				title : "vCPU总数(核)",
				align : "center",
				width : '8%'
			}, {
				field : "vCpuOverplus",
				title : "vCPU剩余(核)",
				align : "center",
				width : '8%'
			}, {
				field : "memorySize",
				title : "内存总量(GB)",
				align : "center",
				width : '8%',
				formatter : cloudmanager.proVDC.formatMemorySize
			}, {
				field : "memoryOverplus",
				title : "内存剩余(GB)",
				align : "center",
				width : '8%',
				formatter : cloudmanager.proVDC.formatMemorySize
			}, {
				field : "createDate",
				title : "创建时间",
				align : "center",
				width : '12%'
			}, {
				field : "operate",
				title : "操作",
				align : "center",
				width : '18%',
				formatter : cloudmanager.proVDC.formatOper
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
		var p = $('#proVDCTableId').datagrid('getPager');
		$(p).pagination({
			layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
			displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
			beforePageText:'跳转到第 ',
			afterPageText:' 页'
		});
		$('#proVDCSearchInputId').searchbox({
			searcher:function(value,name){
				var queryParams = $('#proVDCTableId').datagrid('options').queryParams;
				queryParams.name = value;
				$("#proVDCTableId").datagrid('reload');
			},
		    menu:'#tableSearch',
		    prompt:'请输入查询内容'
		});
		$('#proVDCSearchInputId').searchbox('addClearBtn', 'icon-clear');
	},
	doSearch : function(value, name) {
		var queryParams = $('#proVDCTableId').datagrid('options').queryParams;
		queryParams.name = value;
		$("#proVDCTableId").datagrid('reload');
	},
	formatMemorySize : function(val, row, index) {
		return Math.round(val/1024);
	},
	formatOper : function(val, row, index) {
		return '<a href="#" onclick="cloudmanager.proVDC.updateProVDC(\''
				+ row.pVDCId + '\')">修改</a>'+
				'&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="cloudmanager.proVDC.deleteProVDCConfirm(\''
				+ row.pVDCId + '\',\''+row.computingPoolId+'\')">删除</a>';
	},
	formatName : function(val, row, index) {
		return '<a href="#" onclick="cloudmanager.proVDC.proVDCDetail(\''
				+ row.pVDCId + '\')">' + row.name + '</a>';
	},
	
	proVDCDetail:function(id){
		$('.sugon-searchbox').searchbox('destroy'); 
		sugon.load({
			selector : '#main',
			type : 'POST',
			action : sugon.rootURL + '/proVDC/providerVDCDetail',
			data : {
				id : id,
			},
			callback : function(result) {
			}
		});
	},
	deleteProVDCConfirm:function(proVDCId,computingPoolId){
		sugon.load({
			type : 'POST',
			action : sugon.rootURL + '/proVDC/queryPorVDCVm',
			dataType : 'json',
			data : {
				proVDCId : proVDCId,
			},
			callback : function(result) {
				if(result.success){
					layer.confirm('是否删除提供者vDC？', {
						title:'删除提供者vDC',
						icon: 3,
						btn: ['确认','取消'] //按钮
					}, function(index){
						sugon.load({
							dataType : 'json',
							type : 'POST',
							action : sugon.rootURL + '/proVDC/deleteProVDC',
							data : {
								proVDCId : proVDCId,
								computingPoolId:computingPoolId
							},
							callback : function(result) {
								if(result.success){
									layer.close(index);
									toastr.success('删除成功！');
									$("#proVDCTableId").datagrid('reload');
								}else{
									layer.close(index);
									toastr.error(result.message);
								}
								
							}
						});
					});
				}else{
					toastr.error("提供者vDC下存在虚拟机，不允许删除！");
				}
			}
		});
	},
	addProVDC:function(){
		$.get(sugon.rootURL+'/proVDC/proVDCCreate', {}, function(str){
			layer.open({
				  type: 1,
				  title:'创建提供者vDC',
				  //skin: 'layui-layer-rim', //加上边框
				  area: ['900px', '603px'], //宽高
				  content: str
			});
		});
	},
	updateProVDC:function(pVDCId){
		$.get(sugon.rootURL+'/proVDC/proVDCUpdate?pVDCId='+pVDCId, {}, function(str){
			layer.open({
				  type: 1,
				  title:'修改提供者vDC',
				  //skin: 'layui-layer-rim', //加上边框
				  area: ['900px', '603px'], //宽高
				  content: str
			});
		});
	},
}
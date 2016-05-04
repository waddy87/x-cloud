cloudmanager.namespace("proVDCDetail");
cloudmanager.proVDCDetail = {
	init : function(pVDCId) {
		$('#proVDCVmTableId').datagrid({
			url : sugon.rootURL + '/proVDCDetail/queryPorVDCVmTable',
			method : 'post',
			striped : true,
			fitColumns : true,
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			pageNumber : 1,
			nowrap : false,
			pageSize : 10,
			toolbar : '#proVDCVmtb',
			pageList : [ 5, 10, 20, 50, 100],
			queryParams : {
				pVDCId:pVDCId
			},
			loadMsg : "加载中.....",
			showFooter : true,
			columns : [ [ {
				field : "name",
				title : "名称",
				align : "left",
				width : '25%',
			},{
				field : "vCpuNumer",
				title : "cpu数量",
				align : "center",
				width : '8%'
			}, {
				field : "vMemCapacity",
				title : "内存容量(GB)",
				align : "center",
				width : '10%',
				formatter : cloudmanager.proVDCDetail.formatMemorySize
			}, {
				field : "storCapacity",
				title : "存储容量(GB)",
				align : "center",
				width : '10%'
			},{
				field : "createTime",
				title : "创建时间",
				align : "center",
				width : '12%'
			}] ],
			onBeforeLoad : function(param) {
			},
			onLoadSuccess : function(data) {
			},
			onLoadError : function() {
			},
			onClickCell : function(rowIndex, field, value) {
			}
		});
		var p = $('#proVDCVmTableId').datagrid('getPager');
		$(p).pagination({
			layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
			displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
			beforePageText:'跳转到第 ',
			afterPageText:' 页'
		});
		$('#proVDCVmSearchInputId').searchbox({
			searcher:function(value,name){
				var queryParams = $('#proVDCVmTableId').datagrid('options').queryParams;
				queryParams.name = value;
				$("#proVDCVmTableId").datagrid('reload');
			},
		    menu:'#proVDCVmTableSearch',
		    prompt:'请输入查询内容'
		});
		$('#proVDCVmSearchInputId').searchbox('addClearBtn', 'icon-clear');
	},
	doSearch : function(value, name) {
		var queryParams = $('#proVDCVmTableId').datagrid('options').queryParams;
		queryParams.name = value;
		$("#proVDCVmTableId").datagrid('reload');
	},
	formatMemorySize : function(val, row, index) {
		return Math.round(val/1024);
	},
	proVDCDetailBack : function() {
		sugon.load({
			selector : '#main',
			type : 'get',
			action : sugon.rootURL + '/proVDC/providerVDCList',
			callback : function(result) {
			}
		});
	},
	proVDCDetailSummary : function(pVDCId) {
		sugon.load({
			selector : '#proVDCDetailDivId',
			type : 'POST',
			action : sugon.rootURL + '/proVDCDetail/proVDCDetailSummary',
			data:{
				pVDCId:pVDCId
			},
			callback : function(result) {
				
			}
		});
	},
	proVDCDetailRelevantObjs : function(pVDCId) {
		sugon.load({
			selector : '#proVDCDetailDivId',
			type : 'POST',
			action : sugon.rootURL + '/proVDCDetail/proVDCDetailRelevantObjs',
			data:{
				pVDCId:pVDCId
			},
			callback : function(result) {
			}
		});
	},
}
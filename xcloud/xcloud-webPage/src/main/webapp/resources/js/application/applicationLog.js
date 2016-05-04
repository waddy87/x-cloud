cloudmanager.namespace("applicationLogMgmt");
cloudmanager.applicationLogMgmt = {
	init : function() {
		$('#applicationLogTableId').datagrid({
			url : sugon.rootURL + '/applicationLogMgmt/queryApplicationLogTable',
			method : 'post',
			striped : true,
			fitColumns : true,
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			pageNumber : 1,
			nowrap : false,
			pageSize : 10,
			toolbar : '#applicationLogtb',
			pageList : [ 5, 10, 20, 50, 100],
			queryParams : {},
			loadMsg : "加载中.....",
			showFooter : true,
			columns : [ [ {
				field : "applicationName",
				title : "申请单名称",
				align : "left",
				width : '45%',
				formatter : cloudmanager.applicationLogMgmt.formatName,
			},  {
				field : "approvalResult",
				title : "审批结果",
				align : "center",
				width : '15%',
			},{
				field : "status",
				title : "申请单状态",
				align : "center",
				width : '15%',
				formatter : cloudmanager.applicationLogMgmt.formatStatus,
			},{
				field : "approvalDate",
				title : "审批时间",
				align : "center",
				width : '15%',
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
		var p = $('#applicationLogTableId').datagrid('getPager');
		$(p).pagination({
			layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
			displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
			beforePageText:'跳转到第 ',
			afterPageText:' 页'
		});
		$('#applicationLogSearchInputId').searchbox({
			searcher:function(value,name){
				var queryParams = $('#applicationLogTableId').datagrid('options').queryParams;
				queryParams.name = value;
				queryParams.startTime = $('#startTime').val();
				queryParams.endTime = $('#endTime').val();
				$("#applicationLogTableId").datagrid('reload');
			},
		    menu:'#tableSearch',
		    prompt:'请输入查询内容'
		});
		$('#applicationLogSearchInputId').searchbox('addClearBtn', 'icon-clear');
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
		return '<a href="#" onclick="cloudmanager.applicationLogMgmt.applicationDetail(\''
				+ row.applicationId + '\')">' + row.applicationName + '</a>';
	},
	doSearch : function(value, name) {
		var queryParams = $('#applicationTableId').datagrid('options').queryParams;
		queryParams.name = value;
		$("#applicationTableId").datagrid('reload');
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
}
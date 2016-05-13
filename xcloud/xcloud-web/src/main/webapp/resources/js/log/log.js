cloudmanager.namespace("log");
cloudmanager.log = {
		init : function() {
			$('#logIndexTableId').datagrid({
				url : sugon.rootURL + '/log/getAllLog4Table',
				method : 'post',
				striped : true,
				fitColumns : true,
				singleSelect : true,
				rownumbers : true,
				pagination : true,
				pageNumber : 1,
				nowrap : false,
				pageSize : 10,
				toolbar : '#logIndextb',
				pageList : [ 5, 10, 20, 50, 100 ],
				queryParams : {startTime:'', endTime:'', user:'', resourceId:'', model:''},
				loadMsg : "加载中.....",
				showFooter : true,
				columns : [ [ {
					field : "message",
					title : "信息",
					align : "left",
					width : '40%',
				}, {
					field : "userRealName",
					title : "操作人",
					align : "center",
					width : '15%',
				},{
					field : "operatingTime",
					title : "操作时间",
					align : "center",
					width : '20%'
				},  {
					field : "operationResult",
					title : "操作结果",
					align : "center",
					width : '10%',
					formatter : cloudmanager.log.formatstatus
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
			var p = $('#logIndexTableId').datagrid('getPager');
			$(p).pagination({
				layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
				displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
				beforePageText:'跳转到第 ',
				afterPageText:' 页'
			});
			$('#logIndexSearchInputId').searchbox({
				searcher:function(value,name){
					var queryParams = $('#logIndexTableId').datagrid('options').queryParams;
					if(name == 'user'){
						queryParams.user = value;
						queryParams.model = null;
						queryParams.resourceId = null;
					}else if(name == 'model'){
						queryParams.model = value;
						queryParams.resourceId = null;
						queryParams.user = null;
					}else if(name == 'resourceId'){
						queryParams.resourceId = value;
						queryParams.model = null;
						queryParams.user = null;
					}
					
					queryParams.name = value;
					console.info($('#startTime').val());
					queryParams.startTime =  $('#startTime').val();
					queryParams.endTime =  $('#endTime').val();
					$("#logIndexTableId").datagrid('reload');
				},
			    menu:'#logIndextableSearch',
			    prompt:'请输入查询内容'
			});
			$('#logIndexSearchInputId').searchbox('addClearBtn', 'icon-clear');
		},
		formatstatus:function(val,row,index){
			if(row.operationResult =='0'){
				return "成功";
			}else{
				return "失败";
			}
		},
	    doSearch : function(value, name) {
			var queryParams = $('#logIndexTableId').datagrid('options').queryParams;
			queryParams.name = value;
			$("#logIndexTableId").datagrid('reload');
		},
		
}
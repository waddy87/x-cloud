cloudmanager.namespace("account");
cloudmanager.account = {
		init : function() {
//			$(".form_datetime").datetimepicker({
//				 viewMode: 'days',
//				 format: 'YYYY-MM-DD HH:mm:ss',
//				 
//		    });
		
	          
			$('#accountIndexTableId').datagrid({
				url : sugon.rootURL + '/accounting/getAllAccount4Table',
				method : 'post',
				striped : true,
				fitColumns : true,
				singleSelect : true,
				rownumbers : true,
				pagination : true,
				pageNumber : 1,
				nowrap : false,
				pageSize : 10,
				toolbar : '#accountIndextb',
				pageList : [  5, 10, 20, 50, 100 ],
				queryParams : {},
				loadMsg : "加载中.....",
				showFooter : true,
				columns : [ [ {
					field : "accountType",
					title : "记账类型",
					align : "center",
					width : '10%',
				},{
					field : "resourceName",
					title : "资源名称",
					align : "left",
					width : '15%',
				},{
					field : "status",
					title : "状态",
					align : "left",
					width : '5%',
				}, {
					field : "startTime",
					title : "开始时间",
					align : "center",
					width : '10%'
				}, {
					field : "endTime",
					title : "结束时间",
					align : "center",
					width : '10%'
				}, {
					field : "cpu",
					title : "CPU(核)",
					align : "left",
					width : '5%'
				}, {
					field : "memory",
					title : "内存(MB)",
					align : "center",
					width : '10%'
				}, {
					field : "diskSize",
					title : "存储(GB)",
					align : "center",
					width : '10%'
				}, {
					field : "organizationE",
					title : "组织名称",
					align : "center",
					width : '10%',
					formatter:cloudmanager.account.formatUserName
				} ] ],
				onBeforeLoad : function(param) {
				},
				onLoadSuccess : function(data) {
					console.info(data.sumCpu/100);
					 $('#sumVmHost').html(data.sumVmHost);
					 $('#sumCpu').html(cloudmanager.account.fomatFloat(data.sumCpu, 2));
					 $('#sumMemory').html(cloudmanager.account.fomatFloat(data.sumMemory/1024, 2));
					 $('#sumDiskSize').html(cloudmanager.account.fomatFloat(data.sumDiskSize/1024, 2));
				},
				onLoadError : function() {
				},
				onClickCell : function(rowIndex, field, value) {
				}
			});
			var p = $('#accountIndexTableId').datagrid('getPager');
			$(p).pagination({
				layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
				displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
				beforePageText:'跳转到第 ',
				afterPageText:' 页'
			});
			$('#accountIndexSearchInputId').searchbox({
				searcher:function(value,name){
					var queryParams = $('#accountIndexTableId').datagrid('options').queryParams;
					queryParams.name = value;
					queryParams.startTime = $('#startTime').val();
					queryParams.endTime = $('#endTime').val();
					$("#accountIndexTableId").datagrid('reload');
				},
			    menu:'#accountIndextableSearch',
			    prompt:'请输入查询内容'
			});
			$('#accountIndexSearchInputId').searchbox('addClearBtn', 'icon-clear');
		},
	    doSearch : function(value, name) {
			var queryParams = $('#accountIndexTableId').datagrid('options').queryParams;
			queryParams.name = value;
			queryParams.startTime = $('#startTime').val();
			queryParams.endTime = $('#endTime').val();
			$("#accountIndexTableId").datagrid('reload');
		},
		formatName : function(val, row, index) {
			return '<a href="#" onclick="cloudmanager.account.proVDCDetail(\''
					+ row.pVDCId + '\')">' + row.name + '</a>';
		}
		,
		formatUserName : function(val, row, index) {
			return val.name;
			//return null;
		}
		,
		toDecimal : function(x) {//保留两位小数
			//功能：将浮点数四舍五入，取小数点后2位
			var f = parseFloat(x);
			if (isNaN(f)) {
			return;
			}
			f = Math.round(x*100)/100;
			return f;
			}
		,
		toDecimal2 : function(x){//制保留2位小数，如：2，会在2后面补上00.即2.00
			var f = parseFloat(x);
			if (isNaN(f)) {
			return false;
			}
			var f = Math.round(x*100)/100;
			var s = f.toString();
			var rs = s.indexOf('.');
			if (rs < 0) {
			rs = s.length;
			s += '.';
			}
			while (s.length <= rs + 2) {
			s += '0';
			}
			return s;
		}
		,
		fomatFloat: function(src,pos){
			return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);
		} 
}


cloudmanager.namespace("task");
cloudmanager.task = {
	init : function() {

		$('#taskTableId').datagrid({
			url : sugon.rootURL + '/task/querytaskTable',
			method : 'post',
			striped : true,
			fitColumns : true,
			singleSelect : false,
			rownumbers : true,
			pagination : true,
			pageNumber : 1,
			nowrap : false,
			pageSize : 10,
			toolbar : '#tasktb',
			pageList : [ 5,10, 20, 50, 100],
			idField : 'taskinfoId',
			queryParams : {},
			loadMsg : "加载中.....",
			showFooter : true,
			columns : [ [ {
				field : "taskinfoName",
				title : "任务名称",
				width : '12%',
			}, {
				field : "resourceName",
				title : "资源名称",
				width : '8%',
			}, {
				field : "status",
				title : "结果",
				align : "center",
				width : '8%'
			}, {
				field : "process",
				title : "进度%",
				align : "center",
				width : '18%',
				formatter : cloudmanager.task.formatProcess
			}, {
				field : "description",
				title : "描述",
				align : "center",
				width : '10%'
			}, {
				field : "createDate",
				title : "开始时间",
				align : "center",
				width : '15%'
			}, {
				field : "completDate",
				title : "完成时间",
				align : "center",
				width : '15%'
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

		var p = $('#taskTableId').datagrid('getPager');
		$(p).pagination(
				{
					layout : [ 'list', 'sep', 'first', 'prev', 'links', 'next',
							'last', 'sep', 'refresh', 'manual' ],
					displayMsg : '共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
					beforePageText : '跳转到第 ',
					afterPageText : ' 页'
				});
		$.extend($.fn.searchbox.methods, {
			addClearBtn : function(jq, iconCls) {
				return jq.each(function() {
					var t = $(this);
					var opts = t.searchbox('options');
					opts.icons = opts.icons || [];
					opts.icons.unshift({
						iconCls : iconCls,
						handler : function(e) {
							$(e.data.target).searchbox('clear').searchbox(
									'textbox').focus();
							$(this).css('visibility', 'hidden');
							cloudmanager.vnet.doSearch();
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
		});

		$('#taskSearchInputId')
				.searchbox(
						{
							searcher : function(value, name) {
								var queryParams = $('#taskTableId').datagrid(
										'options').queryParams;
								if (name == 'resourceName') {
									queryParams.resourceName = value;
									queryParams.model = null;
									queryParams.resourceId = null;
								}
								queryParams.name = value;
								queryParams.startTime = $('#startTime').val();
								queryParams.endTime = $('#endTime').val();
								console.info($('#startTime').val());
								console.info(queryParams.resourceName);
								$("#taskTableId").datagrid('reload');
							},
							menu : '#tableSearch',
							prompt : '请输入查询内容'
						});
	},
	formatProcess : function(val, row, index) {
		$('#p').progressbar('setValue', val);
		return $('#pContainer').html();
	},

}
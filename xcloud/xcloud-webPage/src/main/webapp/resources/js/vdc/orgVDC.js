cloudmanager.namespace("orgVDC");
cloudmanager.orgVDC = {
	init : function() {
		$('#orgVDCTableId').datagrid({
			url : sugon.rootURL + '/orgVDC/queryOrgVDCTable',
			method : 'post',
			striped : true,
			fitColumns : true,
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			pageNumber : 1,
			nowrap : false,
			pageSize : 20,
			toolbar : '#orgVDCtb',
			pageList : [ 2, 20, 50, 100, 150, 200 ],
			queryParams : {},
			loadMsg : "加载中.....",
			showFooter : true,
			columns : [ [ {
				field : "name",
				title : "名称",
				align : "left",
				width : '25%',
				formatter : cloudmanager.orgVDC.formatName
			}, {
				field : "vCpuNum",
				title : "vCPU总数(个)",
				align : "center",
				width : '8%'
			}, {
				field : "vCpuOverplus",
				title : "vCPU剩余(个)",
				align : "center",
				width : '8%'
			}, {
				field : "memorySize",
				title : "内存总量(GB)",
				align : "center",
				width : '8%'
			}, {
				field : "memoryOverplus",
				title : "内存剩余(GB)",
				align : "center",
				width : '8%'
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
				formatter : cloudmanager.orgVDC.formatOper
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
		var p = $('#orgVDCTableId').datagrid('getPager');
		$(p).pagination({
			layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
			displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
			beforePageText:'跳转到第 ',
			afterPageText:' 页'
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
							cloudmanager.orgVDC.doSearch();
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
		$('#orgVDCSearchInputId').searchbox('addClearBtn', 'icon-clear');
	},
	doSearch : function(value, name) {
		var queryParams = $('#orgVDCTableId').datagrid('options').queryParams;
		queryParams.name = value;
		$("#orgVDCTableId").datagrid('reload');
	},
	formatOper : function(val, row, index) {
		return '<a href="#" onclick="cloudmanager.orgManagement.deleteOrg(\''
				+ row.pVDCId + '\')">修改</a>'+
				'&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="cloudmanager.orgVDC.deleteOrgVDCConfirm(\''
				+ row.pVDCId + '\')">删除</a>';
	},
	formatName : function(val, row, index) {
		return '<a href="#" onclick="cloudmanager.orgVDC.orgVDCDetail(\''
				+ row.pVDCId + '\')">' + row.name + '</a>';
	},
	
	orgVDCDetail:function(id){
		sugon.load({
			selector : '#main',
			type : 'POST',
			action : sugon.rootURL + '/orgVDC/providerVDCDetail',
			data : {
				id : id,
			},
			callback : function(result) {
				
			}
		});
	},
	deleteOrgVDCConfirm:function(orgVDCId){
		$.messager.confirm('删除', '确认删除提供者vDC？', function(r){
			//提供者vdc下，有组织vdc时，不能删除，需要增加校验
			if (r){
				sugon.load({
					dataType : 'json',
					type : 'POST',
					action : sugon.rootURL + '/orgVDC/deleteOrgVDC',
					data : {
						orgVDCId : orgVDCId,
					},
					callback : function(result) {
						if(result.success){
							toastr.success('保存成功！');
							$("#orgVDCTableId").datagrid('reload');
						}else{
							toastr.error(result.message);
						}
						
					}
				});
			}
		});
	},
	addOrgVDC:function(){
		$('#orgVDCCreateDivId').dialog({
			title: '创建',
			width: 900,
			closed: false,
			cache: false,
			href: sugon.rootURL+'/orgVDC/orgVDCCreate',
			modal: true,
			hcenter:'center',
			top:50
		});
	},
}
cloudmanager.namespace("vmTemplet");
cloudmanager.vmTemplet={
		init:function(){
			$('#vmtempletTableID').datagrid({
				url : sugon.rootURL + '/templet/getAllVMTemplet4Table',
				method : 'post',
				striped : true,
				fitColumns : true,
				singleSelect : false,
				rownumbers : true,
				pagination : true,
				pageNumber : 1,
				nowrap : false,
				pageSize : 10,
				toolbar:'#vmtempletTB',
				pageList : [ 5, 10, 20, 50, 100 ],
				queryParams:{name:'',os:''
				},
				loadMsg:"加载中.....",
				showFooter : true,
				columns : [ [{
					field : "relationId",
					title : "ID",
					align : "center",
					width : '8%'
				} , {
					field : "name",
					title : "名称",
					align : "center",
					width : '20%',
					//formatter:cloudmanager.vmTemplet.formatName
				} , {
					field : "os",
					title : "操作系统",
					align : "center",
					width : '27%'
				}, {
					field : "status",
					title : "状态",
					align : "center",
					width : '8%',
					formatter:cloudmanager.vmTemplet.formatstatus
				}, {
					field : "visible",
					title : "配置",
					align : "center",
					width : '18%',
					formatter:cloudmanager.vmTemplet.formatvisible
				} , {
					field : "operate",
					title : "操作",
					align : "left",
					width : '8%',
					formatter:cloudmanager.vmTemplet.formatOper
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
			var p = $('#vmtempletTableID').datagrid('getPager');
			$(p).pagination({
				layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
				displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
				beforePageText:'跳转到第 ',
				afterPageText:' 页'
			});
			$('#vmtempletSearchInputID').searchbox({
				searcher:function(value,name){
					var queryParams = $('#vmtempletTableID').datagrid('options').queryParams;
					if(name == 'os'){
						queryParams.os = value;
						queryParams.name = null;
					}else if(name == 'name'){
						queryParams.name = value;
						queryParams.os = null;
					}
					$("#vmtempletTableID").datagrid('reload');
				    },
				    menu:'#tableSearch',
				    prompt:'请输入查询内容'
			});
			
			$('#vmtempletSearchInputID').searchbox('addClearBtn', 'icon-clear');
		},
		formatOper:function(val,row,index){
			if(row.status == '0'){
				return '<a href="#" onclick="cloudmanager.vmTemplet.releaseTemplet(\''+row.relationId+'\')">发布</a>'
				+'&nbsp;&nbsp;&nbsp;&nbsp;'; 
				//+'<a href="#" onclick="cloudmanager.vmTemplet.updateTemplet(\''+row.relationId+'\')">修改</a>'
			}else{
				return '<a href="#" onclick="cloudmanager.vmTemplet.unReleaseTemplet(\''+row.relationId+'\')">取消发布</a>'
				+'&nbsp;&nbsp;&nbsp;&nbsp;';
			}
		},
		formatstatus:function(val,row,index){
			if(row.status =='0'){
				return "未发布";
			}else{
				return "发布";
			}
		},
		formatvisible:function(val,row,index){
			
			return 'CPU：  '+row.cpu + '核/ 内存：'+ row.memory/1024+'GB/ 磁盘：'+ row.diskSize+'GB';
		},
		releaseTemplet:function (params) {
			layer.confirm('确认发布？', {
				title:'确认',
				icon: 3,
				btn: ['确认','取消'] //按钮
			}, function(index){
				sugon.load({
					type : 'get',
					data:{
						vmTempletId:params
					},
					action : sugon.rootURL + '/templet/releaseTemplet',
					callback : function(result) {
						$("#vmtempletTableID").datagrid('reload');
						toastr.success('发布模板成功!');
						layer.close(index);
						//$("#main").load(sugon.rootURL + '/templet/toVMTempletIndex'); 
					}
				});
			});
		},
		unReleaseTemplet:function (params) {
			layer.confirm('确认取消发布？', {
				title:'确认',
				icon: 3,
				btn: ['确认','取消'] //按钮
			}, function(index){
				sugon.load({
					type : 'get',
					data:{
						vmTempletId:params
					},
					action : sugon.rootURL + '/templet/unReleaseTemplet',
					callback : function(result) {
						$("#vmtempletTableID").datagrid('reload');
						toastr.success('取消发布模板成功!');
						layer.close(index);
						//$("#main").load(sugon.rootURL + '/templet/toVMTempletIndex'); 
					}
				}); 
			});
		}
		/*formatName:function(val,row,index){
			return '<a href="#" onclick="cloudmanager.vmTemplet.deleteOrg(\''+row.id+'\')">'+row.name+'</a>';  
		},*/
		
}


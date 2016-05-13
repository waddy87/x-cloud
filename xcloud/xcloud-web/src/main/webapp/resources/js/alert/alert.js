cloudmanager.namespace("alert");
var pageNum = 0;//记录当前页
cloudmanager.alert = {

		aquireBtn : function(resId,triggerId){
			$.messager.confirm('确认', '是否对该告警进行确认操作？', function(r){
				//提供者vdc下，有组织vdc时，不能删除，需要增加校验
				if (r){
					sugon.load({
						dataType : 'json',
						type : 'POST',
						action : sugon.rootURL + '/alert/aquire',
						data : {
							"resId" : resId,
							"triggerId" : triggerId,
						},
						callback : function(result) {
							console.debug(result);
							if(result.flag){
								toastr.success('确认成功！');
							}else{
								toastr.error(result.message);
							}
							$("#alertIndexTableId").datagrid('reload');
							
						}
					});
					//cloudmanager.alert.doSearch($("input[name='name']").val(),"name");
				}
			});
		},
		
		batchAquire : function(){

	        var checkedItems = $("#alertIndexTableId").datagrid("getChecked");
	        var ids = [];
	        var names = [];
	        $.each(checkedItems, function(index, item) {
	            ids.push(item.id);
	            names.push(item.name);
	        });
	        console.info(ids);
	        console.info(names);
	        if (ids.length <= 0) {
	            toastr.warning('请至少选择一个!');
	            return;
	        }
	        
	    
	        layer.confirm("是否批量确认告警？", {
	            title:'批量确认告警',
	            icon: 3,
	            btn: ['确认','取消'] //按钮
	        }, function(index) {
	            sugon.load({
	                type : 'post',
	                data:{
	                	ids:ids,
	                },
	                action : sugon.rootURL + '/alert/batchAquire',
	                callback : function(result) {
	                    console.info(result);
	                    var resultJson = JSON.parse(result);
	                    if(resultJson.flag){
	                        layer.close(index);
	                        toastr.success("批量确认告警成功！");
	                        $("#alertIndexTableId").datagrid('reload');
	                    }else{
	                        layer.close(index);
	                        toastr.error(resultJson.message);
	                    }
	                }
	            });
	        }); 

		}, 
		
		batchDelete : function(){

	        var checkedItems = $("#alertIndexTableId").datagrid("getChecked");
	        var ids = [];
	        var names = [];
	        $.each(checkedItems, function(index, item) {
	            ids.push(item.id);
	            names.push(item.name);
	        });
	        console.info(ids);
	        console.info(names);
	        if (ids.length <= 0) {
	            toastr.warning('请至少选择一个!');
	            return;
	        }
	        
	    
	        layer.confirm("是否批量删除告警？", {
	            title:'批量删除告警',
	            icon: 3,
	            btn: ['确认','取消'] //按钮
	        }, function(index) {
	            sugon.load({
	                type : 'post',
	                data:{
	                	ids:ids,
	                },
	                action : sugon.rootURL + '/alert/batchDelete',
	                callback : function(result) {
	                    console.info(result);
	                    var resultJson = JSON.parse(result);
	                    if(resultJson.flag){
	                        layer.close(index);
	                        toastr.success("批量删除告警成功！");
	                        $("#alertIndexTableId").datagrid('reload');
	                    }else{
	                        layer.close(index);
	                        toastr.error(resultJson.message);
	                    }
	                }
	            });
	        }); 

		}, 
		init : function() {
			$('#alertIndexTableId').datagrid({
				url : sugon.rootURL + '/alert/queryAlertInfoTable',
				method : 'post',
				striped : true,
				fitColumns : true,
				singleSelect : false,
				rownumbers : false,
				pagination : true,
				pageNumber : 1,
				nowrap : false,
				pageSize : 10,
				toolbar : '#alertIndextb',
				pageList : [  5, 10, 20, 50, 100 ],
				queryParams : {},
				loadMsg : "加载中.....",
				showFooter : true,
				checkOnSelect : false,//禁止单击行选中复选框
				queryParams:{
                      time:"now",
                      resType:"all"
                     
				},
				columns : [ [{
				    field:"id",
					checkbox:true,
					width:50
				 },{
					field : "alertLevel",
					title : "级别",
					align : "center",
					width : '8%',
					formatter : cloudmanager.alert.formatName
				},{
					field : "alertName",
					title : "标题",
					align : "left",
					width : '10%',
					//formatter : cloudmanager.alert.formatName
				}, {
					field : "alertTime",
					title : "产生时间",
					align : "center",
					width : '8%'
				}, {
					field : "resName",
					title : "对象",
					align : "center",
					width : '8%'
				}, {
					field : "resType",
					title : "资源类型",
					align : "center",
					width : '13%'
				}, {
					field : "description",
					title : "内容",
					align : "left",
					width : '18%'
				}, {
					field : "senderStatus",
					title : "告警发送",
					align : "center",
					width : '8%'
				}, {
					field : "aquire",
					title : "是否确认",
					align : "center",
					width : '12%',
					formatter:function(value,row){  
							if(row.aquire==true){
								return "已确认";
							}else{
								var btn = '<a class="aquireBtnCls" onclick="cloudmanager.alert.aquireBtn(\''+row.resId+'\',\''+row.triggerId+'\')" >确认</a>';  
								return btn;  
							}
							
			              
			            }  
				} ] ],
				onBeforeLoad : function(param) {
					 checkBoxes = [];
	                //获得选中的对象
	                var checkedItems = $("#alertIndexTableId").datagrid('getChecked');
	                //获得当前页
	                var pageNumber = $('#alertIndexTableId').datagrid('getPager').data("pagination").options.pageNumber;
	                if (pageNum != pageNumber) {
	                    //页码不一致，不用记录被选中的对象
	                    pageNum = pageNumber;
	                } else {
	                    //页码一致，记录选中对象的行号
	                    $.each(checkedItems, function(index, item) {
	                        checkBoxes.push($("#alertIndexTableId").datagrid('getRowIndex', item));
	                    });
	                }
				
				},
				onLoadSuccess : function(data) {
				   $.each(checkBoxes, function(index, item) {
	                    console.info("index:" + item);
	                    $("#alertSenderTableId").datagrid('checkRow', item);
	                });
				},
				onLoadError : function() {
				},
				onClickCell : function(rowIndex, field, value) {
				}
			});
			var p = $('#alertIndexTableId').datagrid('getPager');
			$(p).pagination({
				layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
				displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
				beforePageText:'跳转到第 ',
				afterPageText:' 页'
			});
			$('#alertIndexSearchInputId').searchbox({
				searcher:function(value,name){
					var queryParams = $('#alertIndexTableId').datagrid('options').queryParams;
					queryParams.name = value;
					queryParams.time = $("[name='options']:checked").val();
					queryParams.level = $("[name='options1']:checked").val();
					queryParams.resType = $("[name='options2']:checked").val();
					$("#alertIndexTableId").datagrid('reload');
				},
			    menu:'#alertIndextableSearch',
			    prompt:'请输入查询内容'
			});
			$('#alertIndexSearchInputId').searchbox('addClearBtn', 'icon-clear');
		},
	    doSearch : function(value, name) {
			var queryParams = $('#alertIndexTableId').datagrid('options').queryParams;
			queryParams.name = value;
			queryParams.time = $("[name='options']:checked").val();
			queryParams.level = $("[name='options1']:checked").val();
			queryParams.resType = $("[name='options2']:checked").val();
			$("#alertIndexTableId").datagrid('reload');
		},
		formatName : function(val, row, index) {
			if(row.alertLevel=="warning"){
				return "警告";
			}else if(row.alertLevel=="critical"){
				return "严重";
			}else{
				return "未知";
			}
		}
}
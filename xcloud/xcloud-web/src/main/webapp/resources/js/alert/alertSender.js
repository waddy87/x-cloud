cloudmanager.namespace("alertSender");
var pageNum = 0;//记录当前页
cloudmanager.alertSender = {
		init : function() {
			$('#alertSenderTableId').datagrid({
				url : sugon.rootURL + '/alert/queryAlertSenderTable',
				method : 'post',
				striped : true,
				fitColumns : true,
				singleSelect : false,
				rownumbers : false,
				pagination : true,
				pageNumber : 1,
				nowrap : false,
				pageSize : 10,
				toolbar : '#alertSendertb',
				pageList : [  5, 10, 20, 50, 100 ],
				queryParams : {},
				loadMsg : "加载中.....",
				showFooter : true,
				checkOnSelect : false,//禁止单击行选中复选框
				queryParams:{
                    name:""
				},
				columns : [ [ 
				 {
				    field:"id",
					checkbox:true,
					width:50
				 },
				 {
					field : "alertSendLevel",
					title : "级别",
					align : "center",
					width : '3%',
					//formatter : cloudmanager.alertSender.formatName
				 },{
					field : "resName",
					title : "对象",
					align : "left",
					width : '10%',
					//formatter : cloudmanager.alertSender.formatName
				}, {
					field : "name",
					title : "名称",
					align : "center",
					width : '8%'
				}, {
					field : "sendType",
					title : "告警类型",
					align : "center",
					width : '8%'
				}, {
					field : "sendDate",
					title : "时间",
					align : "left",
					width : '18%'
				}, {
					field : "receiver",
					title : "接收人",
					align : "center",
					width : '12%'
				}, {
					field : "message",
					title : "备注",
					align : "center",
					width : '18%'
				},{
					field:"operate",
	                title:"操作",
	                align:"center",
	                width:'8%',
	                formatter:cloudmanager.alertSender.formatOper
				} 
				] ],
				onBeforeLoad : function(param) {
					 checkBoxes = [];
	                //获得选中的对象
	                var checkedItems = $("#alertSenderTableId").datagrid('getChecked');
	                //获得当前页
	                var pageNumber = $('#alertSenderTableId').datagrid('getPager').data("pagination").options.pageNumber;
	                if (pageNum != pageNumber) {
	                    //页码不一致，不用记录被选中的对象
	                    pageNum = pageNumber;
	                } else {
	                    //页码一致，记录选中对象的行号
	                    $.each(checkedItems, function(index, item) {
	                        checkBoxes.push($("#alertSenderTableId").datagrid('getRowIndex', item));
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
					 $('#alertSenderTableId').datagrid('clearSelections');
				}
			});
			var p = $('#alertSenderTableId').datagrid('getPager');
			$(p).pagination({
				layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
				displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
				beforePageText:'跳转到第 ',
				afterPageText:' 页'
			});
			$('#alertSenderSearchInputId').searchbox({
				searcher:function(value,name){
					var queryParams = $('#alertSenderTableId').datagrid('options').queryParams;
					queryParams.name = value;
					queryParams.level = $("[name='options1']:checked").val();
					queryParams.resType = $("[name='options2']:checked").val();
					$("#alertSenderTableId").datagrid('reload');
				},
			    menu:'#alertSendertableSearch',
			    prompt:'请输入查询内容'
			});
			$('#alertSenderSearchInputId').searchbox('addClearBtn', 'icon-clear');
		},
	    doSearch : function(value, name) {
			var queryParams = $('#alertSenderTableId').datagrid('options').queryParams;
			queryParams.name = value;
			queryParams.level = $("[name='options1']:checked").val();
			queryParams.resType = $("[name='options2']:checked").val();
			$("#alertSenderTableId").datagrid('reload');
		},
		formatOper : function(val, row, index) {
			if(row.enable==true){
				return '<a href="#" onclick="cloudmanager.alertSender.eanbleSender(\''
	            + row.id + '\' ,\''+false+'\')">禁用</a>'+
	            '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="cloudmanager.alertSender.deleteAlertSender(\''
	            + row.id + '\')">删除</a>';
			}else{
				return '<a href="#" onclick="cloudmanager.alertSender.eanbleSender(\''
	            + row.id + '\' ,\''+true+'\')">启用</a>'+
	            '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="cloudmanager.alertSender.deleteAlertSender(\''
	            + row.id + '\')">删除</a>';
			}
		
		},
		deleteAlertSender : function(id){
	        layer.confirm("是否删除该告警策略？", {
	            title:'删除告警策略',
	            icon: 3,
	            btn: ['确认','取消'] //按钮
	        }, function(index) {
	            sugon.load({
	                type : 'get',
	                action : sugon.rootURL + '/alert/deleteSender/' + id,
	                callback : function(result) {
	                    console.info(result);
	                    var resultJson = JSON.parse(result);
	                    if(resultJson.flag){
	                        layer.close(index);
	                        toastr.success("删除告警策略成功！");
	                        $("#alertSenderTableId").datagrid('reload');
	                    }else{
	                        layer.close(index);
	                        toastr.error(resultJson.message);
	                    }
	                }
	            });
	        }); 
	    },
	    batchEanbleSender : function(status){

	        var checkedItems = $("#alertSenderTableId").datagrid("getChecked");
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
	       
	        var tip="批量启用";
	        if(ids.length > 0) {
		    	if(status==true){
		    		tip="批量启用";
		    	}else if(status==false){
		    		tip="批量禁用";
		    	}
		        layer.confirm("是否"+tip+"该告警策略？", {
		            title:tip+'告警策略',
		            icon: 3,
		            btn: ['确认','取消'] //按钮
		        }, function(index) {
		            sugon.load({
		                type : 'post',
		                data:{
		                	ids:ids,
		                	status:status
		                },
		                action : sugon.rootURL + '/alert/batchEanbleSender',
		                callback : function(result) {
		                    console.info(result);
		                    var resultJson = JSON.parse(result);
		                    if(resultJson.flag){
		                        layer.close(index);
		                        toastr.success(tip+"告警策略成功！");
		                        $("#alertSenderTableId").datagrid('reload');
		                    }else{
		                        layer.close(index);
		                        toastr.error(resultJson.message);
		                    }
		                }
		            });
		        }); 
	        }
	    
	    }, 
	    eanbleSender : function(id,status){
	    	var tip="禁用";
	    	if(status==="true"){
	    		tip="启用";
	    	}else if(status==="false"){
	    		tip="禁用";
	    	}
	    	
	        layer.confirm("是否"+tip+"该告警策略？", {
	            title:tip+'告警策略',
	            icon: 3,
	            btn: ['确认','取消'] //按钮
	        }, function(index) {
	            sugon.load({
	                type : 'get',
	                action : sugon.rootURL + '/alert/eanbleSender/' + id+"/"+status,
	                callback : function(result) {
	                    console.info(result);
	                    var resultJson = JSON.parse(result);
	                    if(resultJson.flag){
	                        layer.close(index);
	                        toastr.success(tip+"告警策略成功！");
	                        $("#alertSenderTableId").datagrid('reload');
	                    }else{
	                        layer.close(index);
	                        toastr.error(resultJson.message);
	                    }
	                }
	            });
	        }); 
	    }, 
	    
	    batchDeleteSender : function(){

	        var checkedItems = $("#alertSenderTableId").datagrid("getChecked");
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
	        layer.confirm("是否批量删除告警策略？", {
	            title:'批量删除告警策略',
	            icon: 3,
	            btn: ['确认','取消'] //按钮
	        }, function(index) {
	            sugon.load({
	                type : 'post',
	                data:{
	                	ids:ids
	                },
	                action : sugon.rootURL + '/alert/batchDeleteSender',
	                callback : function(result) {
	                    console.info(result);
	                    var resultJson = JSON.parse(result);
	                    if(resultJson.flag){
	                        layer.close(index);
	                        toastr.success("批量删除告警策略成功！");
	                        $("#alertSenderTableId").datagrid('reload');
	                    }else{
	                        layer.close(index);
	                        toastr.error(resultJson.message);
	                    }
	                }
	            });
	        })
	    }, 
	    
		addSender : function(){
			$.get(sugon.rootURL+'/alert/senderSaveForm', {}, function(str){
				layer.open({
					type: 1,
					title:'创建告警策略',
					//skin: 'layui-layer-rim', //加上边框
					area: ['620px', '600px'], //宽高
					content: str,
					btn: ['确定', '取消'] //只是为了演示
					,yes: function(orgID){			
				    senderName=$("#senderName").val();
				    resTypeId=$("#resTypeId").val();
				    resourceId=$("#resourceId").val();
				    resourceName=$("#resourceId").find("option:selected").text();
				    alarmId=$("#alarmId").val();
				    alarmName=$("#alarmId").find("option:selected").text();;
				    senderType=$("#senderType").val();
				    alertLevel=$("#alertLevel").val();
				    
				   /* if(senderType==0){
				    	  mailSMTP=$("#mailSMTP").val();*/
				    	  mailContent=$("#mailContent").val();
				    	  mailReceiver=$("#mailReceiver").val();
				    	 /* smsDevice='';
				    	  smsContent='';
				    	  smsReceiver='';*/
				  /*  }else if(senderType==1){
				    	  mailSMTP='';
				    	  mailContent='';
				    	  mailReceiver='';
				    	  smsDevice=$("#smsDevice").val();
				    	  smsContent=$("#smsContent").val();
				    	  smsReceiver=$("#smsReceiver").val();
				    }*/
				    checkStatus=$("#checkStatus").is(':checked'); ;
					    
					  //checkemail()
			    	 /* if(senderName==""){
			    	    $('#senderName').html("请输入名称!");
			    	    $('#senderName').focus;
			    	    return false;
			    	  }
			    	  
			    	  if($('#smsReceiver').val() == ""){
			    	    $('#smsReceiver').html("请输入收件人!");
			    	    $('#smsReceiver').focus;
			    	    return false;
			    	  }*/
		
				    
				    if($("#saveFormId").valid()){
					   //ajax提交请求
			    	  $.post(
			    		sugon.rootURL+'/alert/senderSavePost',
			    		{ 
			    		   "senderName":senderName,
			    		   "resTypeId":resTypeId,
			    		   "resourceId":resourceId,
			    		   "resourceName":resourceName,
						   "alarmId":alarmId,
						   "alarmName":alarmName,
						   "senderType":$("#senderType").find("option:selected").text(),
				    	 //  "mailSMTP":mailSMTP,
						   "alertLevel":alertLevel,
				    	   "mailContent":mailContent,
				    	   "mailReceiver":mailReceiver,
				    	   
						  /* "smsDevice":smsDevice,
						   "smsContent":smsContent,
						   "smsReceiver":smsReceiver,*/
						   "checkStatus":checkStatus
			    		},
			    		function(data){
			    			 layer.closeAll();
			    			 cloudmanager.alertSender.doSearch($("input[name='name']").val(),"name");
			    		});//这里返回的类型有：json,html,xml,text  
				    }}
				    ,btn2: function(){
				        layer.closeAll();
				    }
				});
			});
		}
}
cloudmanager.namespace("vmManagement");
cloudmanager.vmManagement={
		historyelementId:['cpuChart','memoryChart','netChart','diskChart'],
		historyshow:['Cpu使用率(%)','内存使用率(%)','网络IO(KB/s)','磁盘IO(KB/s)'],
		historyproperties:['cpuUsage','memUsage','diskTps','netTps'],
		historyColor:[{itemColor:'rgb(0, 133, 230)',areaColor1:'rgb(123, 227, 246)',areaColor2:"rgb(95, 178, 237)"},{itemColor:'rgb(255, 70, 131)',areaColor1:'rgb(255, 158, 68)',areaColor2:"rgb(255, 70, 131)"},{itemColor:'rgb(123, 227, 246)',areaColor1:'rgb(123, 227, 246)',areaColor2:"rgb(95, 178, 237)"},{itemColor:'rgb(255, 70, 131)',areaColor1:'rgb(255, 158, 68)',areaColor2:"rgb(255, 70, 131)"}],
		defaultValue:{
			memorySele:null,
			cpuSele:null,
			vmNum:null,
			clickDiskNum:0,
			clickNetNum:0
		},	
		renderLine : function(elementId,title,xdata,ydata,color){
			perfCPU24 = echarts.init(document.getElementById(elementId));
			var option = {
					tooltip : {
						trigger: 'axis'
					},
					legend: {
						top: 'bottom',
						data:['意向']
					},
					grid: {
						left:40,
						right:10,
						bottom:20,
						top:45
					},
					toolbox: {
						show: true,
						feature: {
							magicType: {show: true, type: ['line', 'bar']},
							/*	restore: {show: true},*/
							saveAsImage: {show: true}
						}
					},
					xAxis : {
						type: 'category',
						boundaryGap: false,
						data: xdata,
						axisLine: {
							lineStyle: {
								color: '#ddd'
							}
						},
						axisTick: {
							lineStyle: {
								color: '#ddd'
							}
						},
						splitLine: {
							lineStyle: {
								// 使用深浅的间隔色
								color: 'transparent'
							}
						},
						axisLabel: {
							textStyle: {
								color:'#666'
							}
						}
					},
					yAxis : 
					{
						type: 'value',
						/*						boundaryGap: [0, '100%'],
						min:0,
						max:100,*/
						boundaryGap:false,
						axisLine: {
							lineStyle: {
								color: '#ddd'
							}
						},
						axisTick: {
							lineStyle: {
								color: '#ddd'
							}
						},
						splitLine: {
							lineStyle: {
								// 使用深浅的间隔色
								color: '#eee'
							}
						}
					},
					series : [
					          {
					        	  name:title,
					        	  type:'line',
					        	  smooth:true,
					        	  symbol: 'none',
					        	  sampling: 'average',
					        	  itemStyle: {
					        		  normal: {
					        			  color: color.itemColor
					        		  }
					        	  },
					        	  areaStyle: {
					        		  normal: {
					        			  color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
					        				  offset: 0,
					        				  color: color.areaColor1
					        			  }, {
					        				  offset: 1,
					        				  color: color.areaColor2
					        			  }])
					        		  }
					        	  },
					        	  data:ydata
					          }
					          ]
			};
			perfCPU24.setOption(option);
		},
		initList:function(){
			var myview = $.extend({},$.fn.datagrid.defaults.view,{
				onAfterRender:function(target){
					$.fn.datagrid.defaults.view.onAfterRender.call(this,target);
					var opts = $(target).datagrid('options');
					var vc = $(target).datagrid('getPanel').children('div.datagrid-view');
					vc.children('div.datagrid-empty').remove();
					if (!$(target).datagrid('getRows').length){
						var d = $('<div class="datagrid-empty"></div>').html(opts.emptyMsg || 'no records').appendTo(vc);
						d.css({
							position:'absolute',
							left:0,
							top:40,
							width:'100%',
							textAlign:'center'
						});
					}
				}
			});
			$('#vmTable').datagrid({
				view:myview,
				emptyMsg: '无数据',
				toolbar:"#toolbar",
				url: sugon.rootURL+'/action/vm/list',//请求路径
				method: 'get',
				/*     queryParams: { 'id': id }, */
				idField: 'id', 
				queryParams:{
					name:""
				},
				loadMsg:"加载虚拟机列表数据,请稍后",
				striped : true,
				fitColumns : true,
				singleSelect : true,
				rownumbers : false,
				pagination : true,
				pageNumber : 1,
				nowrap : false,
				pageSize : 10,
				pageList: [5, 10, 20, 50, 100],
				/*  showFooter: false, */
				columns: [[	
				           /*{field:"id",checkbox:true,width:50} ,  */ 
				           {field:"name",title:"虚拟机名称",align:"center",formatter:cloudmanager.vmManagement.formatName,sortable:true,resizable:true,width:150},
				           {field:"organization",title:"所属组织",align:"center",formatter:cloudmanager.vmManagement.formatOrg,sortable:true,resizable:true,width:150},
				           {field:"config",title:"配置",align:"center",formatter:cloudmanager.vmManagement.formatConfig,sortable:true,resizable:true,width:250},
				           {field:"runStatus",title:"任务状态",align:"center",formatter:cloudmanager.vmManagement.formatStatus,width:100},
				           {field:"vmStatus",title:"虚拟机状态",align:"center",formatter:cloudmanager.vmManagement.formatVmStatus,width:100},
				           /*				           {field:"runStatus",title:"任务状态",align:"center",width:100},*/
				           {field:"isAvailable",title:"是否可用",formatter:cloudmanager.vmManagement.formatFlag,align:"center",width:100},  
				           /* {field:"vmStatus",title:"虚拟机状态",align:"center",width:100},*/
				           /*{field:"createTime",title:"创建时间",align:"center",width:200},*/
				           {field:"operate",title:"操作",align:"center",formatter:cloudmanager.vmManagement.formatOper,width:350}
				           ]], 
				           onBeforeLoad: function (param) {
				           },
				           onLoadSuccess: function (data) {
				           },
				           onLoadError: function () {

				           },
				           onClickCell: function (rowIndex, field, value) {

				           }
			});	
			var p = $('#vmTable').datagrid('getPager');
			$(p).pagination({
				layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
				displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
				beforePageText:'跳转到第 ',
				afterPageText:' 页'
			});
			$('#vmSearchInput').searchbox({
				searcher:function(value,name){
					var queryParams = $('#vmTable').datagrid('options').queryParams;
					queryParams.name = value;
					$("#vmTable").datagrid('reload');
				},
				menu:'#tableSearch',
				prompt:'请输入查询内容'
			});
			$('#vmSearchInput').searchbox('addClearBtn', 'icon-clear');
		},
		initDetail:function (vmId,historyData,internalId){
			var _this=this;
			var role=$("#roleFlag").val();
			$("#toSummaryLink").on("click",function(){
				$("#disk").hide();
				$("#netcard").hide();
			});	
			$("#toNetcardLink").on("click",function(){
				_this.defaultValue.clickNetNum++;
				$("#disk").hide();
				$("#netcard").show();
				if(_this.defaultValue.clickNetNum<2){
					$("#netcard .sugon-searchbox").removeClass("included-tab");
					var columns;
					if(role==="operation_manager"){
						columns=[[	
						          /*{field:"id",checkbox:true,width:50},   */   
						          {field:"net",title:"网络名称",align:"center",formatter:cloudmanager.vmManagement.formatNetName,sortable:true,resizable:true,width:100},
						          {field:"ip",title:"ip",align:"center",sortable:true,resizable:true,width:100},
						          {field:"subnet",title:"子网",align:"center",sortable:true,resizable:true,width:100},
						          {field:"vlan",title:"VLAN",align:"center",sortable:true,resizable:true,width:150},
						          {field:"gateway",title:"网关",align:"center",sortable:true,resizable:true,width:150},
						          {field:"dns",title:"DNS",align:"center",sortable:true,resizable:true,width:150}
						          ,{field:"operate",title:"操作",align:"center",formatter:cloudmanager.vmManagement.formatNetOper,width:150}
						          ]];
					}
					else{
						columns=[[	
						          /*{field:"id",checkbox:true,width:50},   */   
						          {field:"net",title:"网络名称",align:"center",formatter:cloudmanager.vmManagement.formatNetName,sortable:true,resizable:true,width:100},
						          {field:"ip",title:"ip",align:"center",sortable:true,resizable:true,width:100},
						          {field:"subnet",title:"子网",align:"center",sortable:true,resizable:true,width:100},
						          {field:"vlan",title:"VLAN",align:"center",sortable:true,resizable:true,width:150},
						          {field:"gateway",title:"网关",align:"center",sortable:true,resizable:true,width:150},
						          {field:"dns",title:"DNS",align:"center",sortable:true,resizable:true,width:150}
						          ]];

					}
					$('#netTable').datagrid({
						toolbar:"#toolbar2",
						url: sugon.rootURL+'/action/vm/netcardlist?vmId='+vmId,//请求路径
						method: 'get',
						idField: 'id', 
						queryParams:{
							name:""
						},
						loadMsg:"加载虚拟机网络数据,请稍后",
						striped : true,
						fitColumns : true,
						singleSelect : true,
						rownumbers : false,
						pagination : true,
						pageNumber : 1,
						nowrap : false,
						pageSize : 10,
						pageList: [5, 10, 20, 50, 100],   
						showFooter: false, 
						columns: columns, 
						onBeforeLoad: function (param) {
						},
						onLoadSuccess: function (data) {
						},
						onLoadError: function () {

						},
						onClickCell: function (rowIndex, field, value) {

						}
					});	
					var p = $('#netTable').datagrid('getPager');
					$(p).pagination({
						layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
						displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
						beforePageText:'跳转到第 ',
						afterPageText:' 页'
					});
					/*					$('#netSearchInput').searchbox({
						searcher:function(value,name){
							var queryParams = $('#netTable').datagrid('options').queryParams;
							queryParams.name = value;
							$("#netTable").datagrid('reload');
						},
						menu:'#tableSearch2',
						prompt:'请输入查询内容'
					});
					$('#netSearchInput').searchbox('addClearBtn', 'icon-clear');*/
				}
				else{
					$("#netTable").datagrid('reload');
				}
			});		
			$("#toDiskLink").on("click",function(){
				_this.defaultValue.clickDiskNum++;
				$("#disk").show();
				$("#netcard").hide();
				if(_this.defaultValue.clickDiskNum<2){
					var columns;
					if(role==="operation_manager"){
						columns=[[	
						          /* 	{field:"id",checkbox:true,width:50}, */
						          {field:"sPoolId",title:"存储池Id",align:"center",width:200},
						          {field:"totalCapacity",title:"总容量 ",align:"center",formatter:cloudmanager.vmManagement.formatStorage,sortable:true,resizable:true,width:350},
						          {field:"usedCapacity",title:"已使用容量",align:"center",formatter:cloudmanager.vmManagement.formatStorage,width:200},
						          {field:"createTime",title:"创建时间",align:"center",width:250}
						          ,{field:"operate",title:"操作",align:"center",formatter:cloudmanager.vmManagement.formatDiskOper,width:350}
						          ]];
					}
					else{
						columns=[[	
						          /* 	{field:"id",checkbox:true,width:50}, */
						          {field:"sPoolId",title:"存储池Id",align:"center",width:200},
						          {field:"totalCapacity",title:"总容量 ",align:"center",formatter:cloudmanager.vmManagement.formatStorage,sortable:true,resizable:true,width:350},
						          {field:"usedCapacity",title:"已使用容量",align:"center",formatter:cloudmanager.vmManagement.formatStorage,width:200},
						          {field:"createTime",title:"创建时间",align:"center",width:250}

						          ]];

					}
					$("#disk .sugon-searchbox").removeClass("included-tab");
					$('#diskTable').datagrid({
						toolbar:"#toolbar",
						url: sugon.rootURL+'/action/vm/disklist?vmId='+vmId,//请求路径
						method: 'get',
						idField: 'id', 
						queryParams:{
							name:""
						},
						loadMsg:"加载虚拟机磁盘数据,请稍后",
						striped : true,
						fitColumns : true,
						singleSelect : true,
						rownumbers : false,
						pagination : true,
						pageNumber : 1,
						nowrap : false,
						pageSize : 10,
						pageList: [5, 10, 20, 50, 100],
						showFooter: false, 
						columns: columns, 
						onBeforeLoad: function (param) {
						},
						onLoadSuccess: function (data) {
						},
						onLoadError: function () {

						},
						onClickCell: function (rowIndex, field, value) {

						}
					});	
					var p = $('#diskTable').datagrid('getPager');
					$(p).pagination({
						layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
						displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
						beforePageText:'跳转到第 ',
						afterPageText:' 页'
					});
					/*					$('#diskSearchInput').searchbox({
						searcher:function(value,name){
							var queryParams = $('#diskTable').datagrid('options').queryParams;
							queryParams.name = value;
							$("#diskTable").datagrid('reload');
						},
						menu:'#tableSearch',
						prompt:'请输入查询内容'
					});
					$('#diskSearchInput').searchbox('addClearBtn', 'icon-clear');*/
				}
				else{
					$("#diskTable").datagrid('reload');
				}
			});
			console.info(historyData==false);
			console.info(historyData=={});
			console.info(historyData.length);
			historyData=$.parseJSON(historyData);
			console.info(historyData.length);
			console.info(historyData);
			console.info(historyData==false);
			console.info(historyData=={});
			if(typeof historyData.length=="undefined"){
				console.info("没有数据");
				for(var n=0;n<4;n++){               
					_this.renderLine(_this.historyelementId[n], _this.historyshow[n], [],[],_this.historyColor[n]);
				}
			}
			else{
				console.info("有数据");
				for(var n=0;n<4;n++){
					var time=historyData.perf24[_this.historyproperties[n]].collectTime;
					var value=historyData.perf24[_this.historyproperties[n]].values;
					_this.renderLine(_this.historyelementId[n], _this.historyshow[n], time,value,_this.historyColor[n]);
				}


			}
			cloudmanager.timer.motorTimer=new sugon.timer({
				time : "10000",
				id : "summary",
				execuFun : function() {
					_this.updateHistoryData(vmId,internalId);
				}
			});
		},
		updateHistoryData:function(vmId,internalId){
			var _this=this;
			var params={vmId:vmId,internalId:internalId};
			params=JSON.stringify(params);
			$.ajax( {  
				type : 'post',  
				contentType : 'application/json',  
				url : sugon.rootURL + "/action/vm/queryVmHistory", 
				dataType : 'json',  
				data : params,
				success : function(result) {
					if(result){
						for(var n=0;n<4;n++){
							var time=result.perf24[_this.historyproperties[n]].collectTime;
							var value=result.perf24[_this.historyproperties[n]].values;
							_this.renderLine(_this.historyelementId[n], _this.historyshow[n], time,value,_this.historyColor[n]);
						}
					}	
				}
			}
			);


		},
		doSearch: function (name,value){
			console.info(name);
			console.info(value);
			var queryParams = $('#orgTable').datagrid('options').queryParams;
			queryParams.name = name;
			$("#orgTable").datagrid('reload');
		},
		createVm:function(){
			var _this=this;
			/*	$('#sugonDialog').dialog({
				title: '创建虚拟机',
				width: 900,
				height:540,
				closed: false,
				cache: true,
				href: sugon.rootURL+'/action/vm/toAddVm',
				modal: true,
				hcenter:'center',
				top:50,
				buttons:[{
					text:'取消',
					handler:function(){
						$('#sugonDialog').dialog("close");
					}
				},{
					text:'确定',
					handler:function(){
						cloudmanager.vmManagement.confirmAddVm();
					}
				}]
			});*/
			$.get(sugon.rootURL+'/action/vm/toAddVm', {}, function(str){
				layer.open({
					type: 1,
					title:'创建虚拟机',
					//skin: 'layui-layer-rim', //加上边框
					area: ['1000px', '600px'], //宽高
					content: str,
					shift:5,
					btn: ['确认 ', '取消'] //只是为了演示
				,yes: function(){	
					_this.confirmAddVm();  			        
				}
				,btn2: function(){
					layer.closeAll();
				}
				});
			});		

		},
		deleteOrg:function (params) {
			/*  var row = $("#tt").datagrid("getSelected"); */
			console.info(params);
			$.messager.confirm("确认", "是否删除组织", function (r) {  
				if (r) {  
					$.ajax({
						url : sugon.rootURL + "/api/organizations/"+params,
						type : 'delete',
						contentType: "application/json; charset=utf-8",
						success : function(result) {
							console.info(result);
							if(result.flag){
								$("#orgTable").datagrid('reload');
							}
						}
					}); 

				}  

			});  
		},

		formatOper:function (val,row,index){  
			console.info($("#roleFlag").val());
			var role=$("#roleFlag").val();
			var operation=
				'<a class="btn btn-link" onclick="cloudmanager.vmManagement.startVm(\''+row.id+'\',\''+row.name+'\',\''+row.runStatus+'\')">启动</a>'+
				'<a class="btn btn-link" onclick="cloudmanager.vmManagement.stopVm(\''+row.id+'\',\''+row.name+'\',\''+row.runStatus+'\')">停止</a>'+
				'<a class="btn btn-link" onclick="cloudmanager.vmManagement.refreshVm(\''+row.id+'\',\''+row.name+'\',this)">刷新</a>'+
				'<a class="btn btn-link" onclick="cloudmanager.vmManagement.goToVNC(\''+row.id+'\',\''+row.runStatus+'\')">VNC</a>'
				;
			if(role==='operation_manager'){
				operation=operation+
				'<a class="btn btn-link" onclick="cloudmanager.vmManagement.deleteVm(\''+row.id+'\',\''+row.name+'\')">删除</a>';
			}		
			if(role==='org_manager'){

				if(row.ownerId!=null&&row.ownerId!=""){
					operation=operation+
					'<a class="btn btn-link" onclick="cloudmanager.vmManagement.recycleVm(\''+row.id+'\',\''+row.name+'\')">回收</a>';
				}
				else{
					operation=operation+
					'<a class="btn btn-link" onclick="cloudmanager.vmManagement.distributeVm(\''+row.id+'\',\''+row.name+'\')">分配</a>';
				}


			}
			return operation;
		}  ,
		formatOrg:function(val,row,index){
			return val.name;
		},
		formatNetName:function(val,row,index){
			return val.netName;
		},
		formatDiskOper:function(val,row,index){
			return '<a class="btn btn-link" onclick="cloudmanager.vmManagement.deleteVmDisk(\''+row.vmId+'\',\''+row.id+'\')">删除</a>';
		},
		formatNetOper:function (val,row,index){  
			console.info(row);
			return '<a class="btn btn-link" onclick="cloudmanager.vmManagement.deleteVmNetcard(\''+row.vmId+'\',\''+row.id+'\')">删除</a>';
		}  ,
		formatName:function (val,row,index){
			return '<a href="#" onclick="cloudmanager.vmManagement.goToDetail(\''+row.id+'\')">'+row.name+'</a>';  
		},
		formatConfig:function (val,row,index){
			return "CPU："+row.vCpuNumer+"核  内存："+row.vMemCapacity/1024+"GB 磁盘："+row.storCapacity+"GB";
		},
		formatStatus:function(val,row,index){
			var status;
			switch(val){
			case 'STARTING':status='启动中';break;
			case 'CREATING':status='创建中';break;
			case 'DELETING':status='删除中';break;
			default:status='无';
			}
			return status;
		},
		formatFlag:function(val,row,index){
			var status;
			if(val){
				status="是";
			}else{
				status="否";
			}
			return status;
		},
		formatVmStatus:function(val,row,index){
			var status;
			switch(val){
			case 'INITED':status='已初始化';break;
			case 'DELETED':status='已删除';break;
			case 'STARTED':status='已启动';break;
			case 'STOPPED':status='已停止';break;
			default:status='未知';
			}
			return status;
		},
		formatStorage:function(val,row,index){
			return val+"GB";
		},
		deleteVmNetcard:function(vmId,id){
			layer.confirm('是否删除虚拟机网络？', {
				title:'删除虚拟机网络',
				icon: 3,
				btn: ['确认','取消'] //按钮
			}, function(index){
				var params={vmId:vmId,id:id};
				params=JSON.stringify(params);
				sugon.load({
					dataType:"json",
					action:sugon.rootURL + "/action/vm/deleteVmNetcard",
					data:params,
					type:'POST',
					contentType:"application/json;charset=UTF-8",
					callback : function(result) {
						if(result.flag){
							toastr.success(result.message);
							layer.closeAll();
							$("#netTable").datagrid('reload');	
							/*						$('#sugonDialog').dialog("close");
							$("#vmTable").datagrid('load');	*/
						}
						else{
							toastr.error(result.message);
						}
					}
				});
			});

		},
		goToVNC:function(id,runStatus){
			if(runStatus==="CREATING"){
				toastr.error("创建中的虚拟机不允许操作");
			}
			else if (runStatus==="CreatError"){
				toastr.error("创建失败的虚拟机不允许操作");
			}
			else{
				window.open(sugon.rootURL+'/action/vm/vnc?vmId='+id);
			}
			/*			var _this=this;
			var params={internalId:internalId};
			var url=sugon.rootURL+"/action/vm/toVNCPage?internalId="+internalId;
			newWin=window.open(url,internalId+"_blank","toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");

	        sugon.load({
	            selector : '#vncPage',
	            type : 'get',
	            action : url,
	            callback : function(result) {
	            }
	        });*/
			/*	params=JSON.stringify(params);
			$.ajax( {  
				type : 'post',  
				contentType : 'application/json',  
				url : sugon.rootURL + "/action/vm/queryVNCUrl", 
				dataType : 'json',  
				data : params,
				success : function(result) {
					console.info(result); 
					var url=result.url;
					   var _wmks = $("#wmksContainer")
                       .wmks({"useVNCHandshake" : false, "sendProperMouseWheelDeltas": true,"fitToParent":true})
                       .bind("wmksconnecting", function() {
                             console.log("The console is connecting");
                             })
                       .bind("wmksconnected", function() {
                             console.log("The console has been connected");
                             })
                       .bind("wmksdisconnected", function(evt, info) {
                             console.log("The console has been disconnected");
                             console.log(evt, info);
                             })
                       .bind("wmkserror", function(evt, errObj) {
                             console.log("Error!");
                             console.log(evt, errObj);
                             })
                       .bind("wmksiniterror", function(evt, customData) {
                             console.log(evt);
                             console.log(customData);
                             })
                       .bind("wmksresolutionchanged", function(canvas) {
                             console.log("Resolution has changed!");
                             })
                       _wmks.wmks("connect", url);	
				}
			}
			);*/
		},
		initVNC:function(url){		
			_wmks = $("#wmksContainer")
			.wmks({"useVNCHandshake" : false, "sendProperMouseWheelDeltas": true,"fitToParent":true})
			.bind("wmksconnecting", function() {
				console.log("The console is connecting");
			})
			.bind("wmksconnected", function() {
				console.log("The console has been connected");
			})
			.bind("wmksdisconnected", function(evt, info) {
				console.log("The console has been disconnected");
				console.log(evt, info);
			})
			.bind("wmkserror", function(evt, errObj) {
				console.log("Error!");
				console.log(evt, errObj);
			})
			.bind("wmksiniterror", function(evt, customData) {
				console.log(evt);
				console.log(customData);
			})
			.bind("wmksresolutionchanged", function(canvas) {
				console.log("Resolution has changed!");
			})

			_wmks.wmks("connect", url);
		},
		recycleVm:function(vmId,name){
			layer.confirm('是否回收该虚拟机？', {
				title:'回收虚拟机',
				icon: 3,
				btn: ['确认','取消'] //按钮
			}, function(index){
				var params={id:vmId,name:name};
				params=JSON.stringify(params);
				sugon.load({
					dataType:"json",
					action:sugon.rootURL + "/action/vm/recycleVm",
					data:params,
					type:'POST',
					contentType:"application/json;charset=UTF-8",
					callback : function(result) {
						if(result.flag){
							toastr.success(result.message);
							layer.closeAll();
							$("#vmTable").datagrid('reload');	
							/*						$('#sugonDialog').dialog("close");
						$("#vmTable").datagrid('load');	*/
						}
						else{
							toastr.error(result.message);
						}
					}
				});
			});
		},
		releaseVm:function(vmId,name){
			layer.confirm('是否释放虚拟机?', {
				title:'回收虚拟机',
				icon: 3,
				btn: ['确认','取消'] //按钮
			}, function(index){
				var params={id:vmId,name:name};
				params=JSON.stringify(params);
				sugon.load({
					dataType:"json",
					action:sugon.rootURL + "/action/vm/releaseVm",
					data:params,
					type:'POST',
					contentType:"application/json;charset=UTF-8",
					callback : function(result) {
						if(result.flag){
							toastr.success(result.message);
							layer.closeAll();
							$("#vmTable").datagrid('reload');	
							/*						$('#sugonDialog').dialog("close");
						$("#vmTable").datagrid('load');	*/
						}
						else{
							toastr.error(result.message);
						}
					}
				});
			});
		},
		distributeVm:function(vmId,name){
			var _this=this;
			/*	$(".sugon-searchbox").searchbox('destroy'); */
			$.get(sugon.rootURL+'/action/vm/toDistributeVm', {}, function(str){
				layer.open({
					type: 1,
					title:'虚拟机用户分配',
					//skin: 'layui-layer-rim', //加上边框
					area: ['900px', '400px'], //宽高
					content: str,
					shift:5,
					btn: ['确认 ', '取消'] //只是为了演示
				,yes: function(){
					_this.confirmDistributeVm(vmId,name);  			        
				}
				,btn2: function(){
					layer.closeAll();
				}
				});
			});	
		},
		confirmDistributeVm:function(vmId,name){
			var checkedItems = $("#userTableId").datagrid("getChecked");
			if(checkedItems.length!=0){
				var ownerId=checkedItems[0].id;
				var params={id:vmId,name:name,ownerId:ownerId};
				params=JSON.stringify(params);
				sugon.load({
					dataType:"json",
					action:sugon.rootURL + "/action/vm/distributeVm",
					data:params,
					type:'POST',
					contentType:"application/json;charset=UTF-8",
					callback : function(result) {
						if(result.flag){
							toastr.success(result.message);
							layer.closeAll();
							$("#vmTable").datagrid('reload');	
						}
						else{
							toastr.error(result.message);
						}
					}
				});

			}
			else{
				toastr.error("请选择一个组织成员");
			}


			/*			*/
		},
		deleteVmDisk:function(vmId,id){
			layer.confirm('是否删除虚拟机磁盘？', {
				title:'删除虚拟机磁盘',
				icon: 3,
				btn: ['确认','取消'] //按钮
			}, function(index){
				var params={vmId:vmId,id:id};
				params=JSON.stringify(params);
				sugon.load({
					dataType:"json",
					action:sugon.rootURL + "/action/vm/deleteVmDisk",
					data:params,
					type:'POST',
					contentType:"application/json;charset=UTF-8",
					callback : function(result) {
						if(result.flag){
							toastr.success(result.message);
							layer.closeAll();
							$("#diskTable").datagrid('reload');	
							/*						$('#sugonDialog').dialog("close");
						$("#vmTable").datagrid('load');	*/
						}
						else{
							toastr.error(result.message);
						}
					}
				});
			});

		},
		updateConfigInfo:function(){
			var networks='';
			$("#addedNetcard .list-group-item").each(function(){
				networks=networks+$(this).text()+"<br>";
			});
			var temp=$("#vmTempSele").find("option:selected");
			$("#con_vmName").text($("#vmName").val());
			$("#con_proVDC").text($("input[name='computingPool']:checked").data("name"));
			$("#con_org").text($("#orgSele option:checked").text());

			$("#con_network").html(networks);
			if(temp.length!=0){
				$("#con_storage").text(temp.data("storcapacity")+" GB");
				$("#con_cpu").text(temp.data("cpunum")+" 核");
				$("#con_memory").text(temp.data("memory")/1024+" GB");
			}

			$("#con_storagePool").text($("#storPoolSele option:selected").text());

			/*			osUsername=$("#username").val(),
			osPassword=$("#password").val(),
			cPoolId=$("input[name='computingPool']:checked").val(),
			vCpuNumer=$("input[name='cpu_search']").val(),
			vMemCapacity=$("input[name='memory_search']").val()*1024,
			sPoolId=$("#storPoolSele").val(),
			storCapacity=$("#vmTempSele").find("option:selected").data("storcapacity");*/
		},
		initDistributeVm:function(){
			$('#userTableId').datagrid({
				url : sugon.rootURL + '/action/vm/orgUserList',
				method : 'get',
				loadMsg:"加载虚拟机列表数据,请稍后",
				striped : true,
				fitColumns : true,
				singleSelect : true,
				rownumbers : false,
				pagination : true,
				pageNumber : 1,
				nowrap : false,
				pageSize : 10,
				/*	toolbar : '#usertb',*/
				pageList : [5, 10, 20, 50, 100],
				queryParams : {},
				loadMsg : "加载中.....",
				showFooter : true,
				columns : [ [ 
{field:"id",checkbox:true,width:'2%'},  	              
{
	field : "username",
	title : "用户名",
	align : "left",
	width : '8%',
	//formatter : cloudmanager.userMgmt.formatName
},{
	field : "realname",
	title : "真实姓名",
	align : "center",
	width : '8%'
},{
	field : "locked",
	title : "是否锁定",
	align : "center",
	width : '8%',
    formatter:cloudmanager.vmManagement.formatFlag
		/*,formatter : cloudmanager.userMgmt.formatLocked*/
}, {
	field : "isDelete",
	title : "是否禁用",
	align : "center",
	width : '8%',
	formatter:cloudmanager.vmManagement.formatFlag
		/*,formatter : cloudmanager.userMgmt.formatIsDelete*/
},{
	field : "email",
	title : "电子邮箱",
	align : "center",
	width : '15%'
}, {
	field : "telephone",
	title : "移动电话",
	align : "center",
	width : '8%'
}, {
	field : "isOnline",
	title : "是否在线",
	align : "center",
	width : '8%',
	formatter:cloudmanager.vmManagement.formatFlag
		/*,formatter :cloudmanager.userMgmt.isOnline*/
},  {
	field : "createDate",
	title : "创建时间",
	align : "center",
	width : '18%'
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
			var p = $('#userTableId').datagrid('getPager');
			$(p).pagination({
				layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
				displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
				beforePageText:'跳转到第 ',
				afterPageText:' 页'
			});
			/*			$('#userSearchInputId').searchbox({
				searcher:function(value,name){
					var queryParams = $('#userTableId').datagrid('options').queryParams;
					queryParams.name = value;
					$("#userTableId").datagrid('reload');
				},
				menu:'#tableSearch',
				prompt:'请输入查询内容'
			});
			$('#userSearchInputId').searchbox('addClearBtn', 'icon-clear');*/
		},
		initAdd:function(){
			var _this=this,
			vmTempSele=$("#vmTempSele");
			orgSele=$("#orgSele");
			mem=vmTempSele.find("option:selected").data("memory")/1024;
			cpuNum=vmTempSele.find("option:selected").data("cpunum");

			_this.renderSlider(mem,cpuNum);


			this.defaultValue.vmNum=new Sugon.ui.slider({
				selector : '#vmNumber',
				setting : [{
					"unit": 1,
					"min": 1,
					"max": 4,
					"align":"right",
					"label" : "4台"
				},{
					"unit": 1,
					"min": 4,
					"max": 8,
					"align":"right",
					"label" : "8台"
				},{
					"unit": 1,
					"min": 8,
					"max": 12,
					"align":"right",
					"label" : "12台"
				}],
				textAlign : 'bottom',// content bottom
				width : 460,
				hasInput : true,
				inputName : 'vm_number',
				inputUnit : '台',
				inputHeight : 10,
				inputWidth : 45,
				//backgroundColor : '#F9F9F9',
				//sliderColor : '#E9FBFD',
				defaultValue : 1,
				fontSize : 12,
				height:28,
				changeHandler:function(res){
					/*			console.info(res);		
					console.info("dddd");	*/
					if(res>1){
						$("#ipForm .input-group").hide();
					}
					else{
						$("#ipForm .input-group").show();
					}
				}
			});
			$("#vmAddConfig").bind("click",function(){
				/*				console.info("000000");
				console.info(_this.defaultValue.vmNum.getValue()>1);
				if(_this.defaultValue.vmNum.getValue()>1){
					$("#ipForm .input-group").hide();
				}
				else{
					console.info("ccc");
					console.info($("#ipForm .input-group"));
					$("#ipForm .input-group").show();
				}	*/			
				/*	if(_this.defaultValue.vmNum.getValue())*/
				_this.updateConfigInfo();
			});
			$("#setdelay").on("click",function(){
				$("#usernamepart").hide();
				$("#passwordpart").hide();
				$("#password2part").hide();
			});
			$("#setnow").on("click",function(){
				$("#usernamepart").show();
				$("#passwordpart").show();
				$("#password2part").show();
			});
			vmTempSele.on("change",function(){
				var memory=$(this).find("option:selected").data("memory")/1024,
				cpunum=$(this).find("option:selected").data("cpunum");

				console.info(memory);
				console.info($("input[name='memory_search']").val());
				_this.renderSlider(memory,cpunum);

			});
			orgSele.on("change",function(){
				var orgId=$(this).find("option:selected").val();
				$("#netSeleInfo").html("");
				$.ajax({
					url : sugon.rootURL + "/action/vm/queryNetsByOrg",
					type : 'get',
					contentType: "application/json; charset=utf-8",
					data:{orgId:orgId},
					success : function(result) {
						console.info(result);
						$("#netSele").html("");
						if(result){
							for(var a in result){
								console.info(result[a]);
								if(typeof result[a]!='function'){
									$("#netSele").append("<option data-vlan='"+result[a].vlanNO+"' data-added='false' data-gateway='"+result[a].gateway+"' data-dns='"+result[a].dns+"' data-subnet='"+result[a].subNet+"' value='"+result[a].netPoolId+"'>"+result[a].netName+"/"+result[a].subNet+"</option>");							
								}
							}					
						}
						else{
							$("#netSeleInfo").html("该组织没有网络池");
						}

					}
				});

			});
			/*			$("#netSele").on("change",function(){
				$("#netSeleInfo").html($(this).find("option:selected").data("subnet"));
			});*/

			$("#vdcSele label").on("click",function(){
				console.info($(this).find(":radio"));
				var proVDCId=$(this).find(":radio").data("vdcid");
				$.ajax({
					url : sugon.rootURL + "/action/vm/queryStorByProVDC",
					type : 'get',
					contentType: "application/json; charset=utf-8",
					data:{proVDCId:proVDCId},
					success : function(result) {
						console.info(result);
						$("#storPoolSele").html("");
						if(result){
							for(var a in result){
								console.info(result[a]);
								if(typeof result[a] !="function" ){
									$("#storPoolSele").append("<option value='"+result[a].spId+"'>"+result[a].spName+"</option>");
								}								
							}					
						}
						else{
							$("#storPoolSele").html("该提供者VDC没有存储池");
						}

					}
				});
			});
			jQuery.validator.addMethod(
					"ip",
					function(value, element) {
						return this.optional(element)
						|| /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([0-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/
						.test(value);
					}, "请填写正确的IP地址。");
			$("#addVmForm").validate({
				debug:false,
				focusInvalid: true, //当为false时，验证无效时，没有焦点响应  
				onkeyup: true,
				submitHandler:function(form){  
					/*					console.info("------");
								 {"owner":"8a809e50536435960153643b33c30003","address":"sssss","create_time":{"date":23,"hours":9,"seconds":42,"month":2,"timezoneOffset":-480,"year":116,"minutes":58,"time":1458698322000,"day":3},"creater":"zhang3","name":"sdasdas","id":"402881f553a131340153a13202ca0000","remarks":"sssss","status":"A"} 	
					var params={organization:{address:$("#address").val(),remarks:$("#remarks").val(),name:$("#orgName").val()}};
					console.info(params);
					_this.confirmAddVm(params);*/
				},
				rules: {
					vmName:{
						remote:{
							type:'POST',
							url:sugon.rootURL + "/action/vm/validVmName",
							data:{name:function(){return $("#vmName").val()}}
						},
						required:true
				
					},
					username: "required",
					password: {
						required: true,
						rangelength:[3,10]
					},
					passwordConfirm: {
						required: true,
						rangelength:[3,10],
						equalTo: "#password"
					}
				},
				messages: {
					vmName:{
						required:"请输入虚拟机名称",
						remote:"存在同名虚拟机"
					},
					username: "请输入用户名",
					password: {
						required: "请输入密码",
						rangelength: "密码要在3-10位之间"
					},
					passwordConfirm: {
						required: "请输入确认密码",
						rangelength: "密码要在3-10位之间",
						equalTo: "两次输入密码不一致"
					}
				}
			});
			$("#ipForm").validate({
				debug:false,
				focusInvalid: true, //当为false时，验证无效时，没有焦点响应  
				onkeyup: true,
				submitHandler:function(form){  
				},
				errorPlacement:function(error,element) {  
					error.appendTo(element.parent().prev("div.input-group-error"));
				},
				rules: {
					netIP: {
						required: true,
						ip:true
					}
				},
				messages: {
					netIP: {
						required: "请输入IP地址",
						ip: "IP地址格式不合法"
					}
				}
			});		
			$("#addNetcardBtn").on("click",function(){
				var curOpt=$("#netSele option:selected");
				console.info(curOpt.length);
				if(curOpt.length==0&&$("#netSeleInfo").length==0){
					$("#netSele").after('<span style="margin-left: 10px; color: red" id="netSeleInfo">无可以使用的网络池了</span>');	
				}
				if($("#ipForm").valid()&&curOpt.length==1){                                                                                
					var item='<div class="list-group-item" data-gateway="'+curOpt.data("gateway")+'" data-subnet="'+curOpt.data("subnet")+'" data-vlan="'+curOpt.data("vlan")+'" data-dns="'+curOpt.data("dns")+'" data-id="'+curOpt.val()+'" data-name="'+curOpt.text()+'" data-ip="'+$("#netIP").val()+'"><a href="javascript:;" class="pull-right" onclick="cloudmanager.vmManagement.removeNetItem(this)"><i class="fa fa-times-circle"></i></a>'+curOpt.text()+',IP:'+$("#netIP").val()+'</div>';
					$("#addedNetcard").append(item);
					curOpt.remove();
				}    	
			});
			_this.updateConfigInfo();
		},
		cancelAdd:function(){
			layer.closeAll();
		},
		confirmUpdateOrg:function(params,id){		
			params=JSON.stringify(params);
			console.info(params);
			$.ajax({            
				url : sugon.rootURL + "/api/organizations/"+id,
				method : 'PATCH',
				contentType: "application/json;charset=UTF-8",
				dataType:"json",
				data:params,
				success : function(result) {
					$('#sugonDialog').dialog("close");
					$("#orgTable").datagrid('load');
				}
			});
		},
		confirmAddVm:function(params){
			var name=$("#vmName").val(),
			orgId=$("#orgSele").val(),
			osUsername="",
			vdcId=$("input[name='computingPool']:checked").data("vdcid"),
			/*			osUsername=$("#username").val(),*/
			osPassword=$("#password").val(),
			cPoolId=$("input[name='computingPool']:checked").val(),
			vCpuNumer=$("input[name='cpu_search']").val(),
			vMemCapacity=$("input[name='memory_search']").val()*1024,
			sPoolId=$("#storPoolSele").val(),
			storCapacity=$("#vmTempSele").find("option:selected").data("storcapacity"),
			/*	storCapacity=20,*/
			templateId=$("#vmTempSele").val(),
			templateOs=$("#vmTempSele option:selected").data("os"),
			vmNumber=this.defaultValue.vmNum.getValue();
			if(templateOs.indexOf("Windows")>1){
				osUsername="Administrator";
			}
			else{
				osUsername="root";
			}
			if($("#setdelay").hasClass("active")){
				osPassword=null;
			}
			var netslist=new Array();
			if(!$("#ipForm .input-group").is(":hidden")){
				$("div.list-group-item").each(function(){
					var thiz=$(this);
					console.info(thiz);
					var data={netId:thiz.data("id"),vlan:thiz.data("vlan"),ip:thiz.data("ip"),gateway:thiz.data("gateway"),dns:thiz.data("dns"),subnet:thiz.data("subnet")};
					console.info(data);
					netslist.push(data);		
				});
			}

			if($("#addVmForm").valid()){

				var dataarray=new Array();
				for(var i=0;i<vmNumber;i++){
					var params={name:name,remarks:"",orgId:$("#orgSele").val(),vdcId:vdcId,osUsername:osUsername,osPassword:osPassword,cPoolId:cPoolId,vCpuNumer:vCpuNumer,vMemCapacity:vMemCapacity,sPoolId:sPoolId,storCapacity:storCapacity,templateId:templateId,nets:netslist};
					if(i!=0){
						params.name=name+i;
					}
					console.info(params.name);
					dataarray.push(params);
					console.info(JSON.stringify(dataarray));
				}
				var param=JSON.stringify(dataarray);
				console.info(param);
				sugon.load({
					dataType:"json",
					action:sugon.rootURL + "/action/vm/addVm",
					data:param,
					type:'POST',
					contentType:"application/json;charset=UTF-8",
					callback : function(result) {
						console.info(result.flag);
						if(result.length==1){
							if(result[0].flag){
								toastr.success(result[0].message);
								layer.closeAll();
								$("#vmTable").datagrid('reload');	
							}else{
								toastr.error(result[0].message);
							}
						}
						else{
							for(var a=0;a<result.length;a++){
								console.info(result[a]);
								if(result[a].flag){
									toastr.success(result[a].message);
									layer.closeAll();
									$("#vmTable").datagrid('reload');
								}else{
									toastr.error(result[a].message);
								}
							}

						}

					}
				});
			}
			/* var params={vmhost:{}}*/
			/*			$.ajax({            
				url : sugon.rootURL + "/api/organizations",
				method : 'POST',
				contentType: "application/json;charset=UTF-8",
				dataType:"json",
				data:params,
				success : function(result) {
					$('#sugonDialog').dialog("close");
					$("#orgTable").datagrid('load');
				},
				error:function(err){
					console.info(err);
				}

			});*/
			/*            sugon.load({
                type : 'POST',
                action : sugon.rootURL + "/api/organizations",
                dataType:'json',
                data:params,
                callback : function(result) {
                	console.info(result);
                }
            });*/
		},
		goToDetail:function(id){
			$('.sugon-searchbox').searchbox('destroy'); 
			if($(".sugon-dialog").parent().is(":hidden")){
				$('.sugon-dialog').dialog("destroy");
			}
			sugon.load({
				selector : '#main',
				type : 'get',
				action : sugon.rootURL + "/action/vm/goToDetail",
				data:{vmId:id},
				callback : function(result) {
					console.info(result);
				}
			});
		},
		renderSlider:function(memory,cpunum){
			$('#cpuDivId').html("");
			this.defaultValue.cpuSele=new Sugon.ui.slider({
				selector : '#cpuDivId',
				setting : [{
					"unit": 1,
					"min": 1,
					"max": 8,
					"align":"right",
					"label" : "8核"
				},{
					"unit": 1,
					"min": 8,
					"max": 16,
					"align":"right",
					"label" : "16核"
				},{
					"unit": 1,
					"min": 16,
					"max": 32,
					"align":"right",
					"label" : "32核"
				},{
					"unit": 1,
					"min": 32,
					"max": 64,
					"align":"right",
					"label" : "64核"
				}],
				textAlign : 'bottom',// content bottom
				width : 460,
				hasInput : true,
				inputName : 'cpu_search',
				inputUnit : '核',
				inputHeight : 10,
				inputWidth : 45,
				//backgroundColor : '#F9F9F9',
				//sliderColor : '#E9FBFD',
				defaultValue : cpunum,
				fontSize : 12,
				height:28
			});
			$('#memoryDivId').html("");
			this.defaultValue.memorySele=new Sugon.ui.slider({
				selector : '#memoryDivId',
				setting : [{
					"unit": 1,
					"min": 1,
					"max": 4,
					"align":"right",
					"label" : "4GB"
				},{
					"unit": 1,
					"min": 4,
					"max": 16,
					"align":"right",
					"label" : "16GB"
				},{
					"unit": 1,
					"min": 16,
					"max": 32,
					"align":"right",
					"label" : "32GB"
				},{
					"unit": 1,
					"min": 32,
					"max": 64,
					"align":"right",
					"label" : "64GB"
				},{
					"unit": 1,
					"min": 64,
					"max": 128,
					"align":"right",
					"label" : "128GB"
				},{
					"unit": 1,
					"min": 128,
					"max": 256,
					"align":"right",
					"label" : "256GB"
				},{
					"unit": 1,
					"min": 256,
					"max": 512,
					"align":"right",
					"label" : "512GB"
				}],
				textAlign : 'bottom',// content bottom
				width : 460,
				hasInput : true,
				inputName : 'memory_search',
				inputUnit : 'GB',
				inputHeight : 10,
				inputWidth : 45,
				//backgroundColor : '#F9F9F9',
				//sliderColor : '#E9FBFD',
				defaultValue : memory,
				fontSize : 12,
				height:28
			});

		},
		startVm:function(vmId,vmName,runStatus){
			if(runStatus==="CREATING"){
				toastr.error("创建中的虚拟机不允许操作");
			}
			else if (runStatus==="CreatError"){
				toastr.error("创建失败的虚拟机不允许操作");
			}
			else{
				layer.confirm('是否启动虚拟机？', {
					title:'启动虚拟机',
					icon: 3,
					btn: ['确认','取消'] //按钮
				}, function(index){
					var params={id:vmId,name:vmName};
					params=JSON.stringify(params);
					sugon.load({
						dataType:"json",
						action:sugon.rootURL + "/action/vm/startVm",
						data:params,
						type:'POST',
						contentType:"application/json;charset=UTF-8",
						callback : function(result) {
							layer.closeAll();
							if(result.flag){
								toastr.success(result.message);
							}
							else{
								toastr.error(result.message);
							}
						}
					});
				});		
			}
		},
		stopVm:function(vmId,vmName,runStatus){
			if(runStatus==="CREATING"){
				toastr.error("创建中的虚拟机不允许操作");
			}
			else if (runStatus==="CreatError"){
				toastr.error("创建失败的虚拟机不允许操作");
			}
			else{
				layer.confirm('是否停止虚拟机？', {
					title:'停止虚拟机',
					icon: 3,
					btn: ['确认','取消'] //按钮
				}, function(index){
					var params={id:vmId,name:vmName};
					params=JSON.stringify(params);
					sugon.load({
						dataType:"json",
						action:sugon.rootURL + "/action/vm/stopVm",
						data:params,
						type:'POST',
						contentType:"application/json;charset=UTF-8",
						callback : function(result) {
							layer.closeAll();
							if(result.flag){
								toastr.success(result.message);
								/*						$('#sugonDialog').dialog("close");
								$("#vmTable").datagrid('load');	*/
							}
							else{
								toastr.error(result.message);
							}
						}
					});	
				});
			}
		},
		refreshVm:function(vmId,vmName,thiz){
			var params={id:vmId,name:vmName};
			params=JSON.stringify(params);
			var _this=this;
			sugon.load({
				dataType:"json",
				action:sugon.rootURL + "/action/vm/refreshVm",
				data:params,
				type:'POST',
				contentType:"application/json;charset=UTF-8",
				callback : function(result) {
					$(thiz).parents("tr").find("td[field='runStatus'] div").text(_this.formatStatus(result.runStatus));
					$(thiz).parents("tr").find("td[field='vmStatus'] div").text(_this.formatVmStatus(result.vmStatus));
				}
			});
		},
		addNetcard:function(orgId,vmId){
			var _this=this;
			var params=JSON.stringify({id:vmId});
			sugon.load({
				dataType:"json",
				action:sugon.rootURL + "/action/vm/queryVmTaskStatus",
				data:params,
				type:'POST',
				contentType:"application/json;charset=UTF-8",
				callback : function(result) {
					if(result){
						$.get(sugon.rootURL+'/action/vm/toAddNetcard?orgId='+orgId+'&vmId='+vmId, {}, function(str){
							layer.open({
								type: 1,
								title:'添加网络',
								//skin: 'layui-layer-rim', //加上边框
								area: ['600px', '450px'], //宽高
								content: str,
								shift:5,
								btn: ['确认 ', '取消'] //只是为了演示
							,yes: function(){	
								_this.confirmAddNetcard(vmId);  			        
							}
							,btn2: function(){
								layer.closeAll();
							}
							});
						});	
					}
					else{
						toastr.error("该虚拟机正在操作中,请稍后再试");
					}

				}
			});		

		},	
		addDisk:function(vdcId,vmId){
			var _this=this;
			var params=JSON.stringify({id:vmId});
			sugon.load({
				dataType:"json",
				action:sugon.rootURL + "/action/vm/queryVmTaskStatus",
				data:params,
				type:'POST',
				contentType:"application/json;charset=UTF-8",
				callback : function(result) {
					if(result){				
						$.get(sugon.rootURL+'/action/vm/toAddDisk?proVdcId='+vdcId, {}, function(str){
							layer.open({
								type: 1,
								title:'添加磁盘',
								//skin: 'layui-layer-rim', //加上边框
								area: ['600px', '500px'], //宽高
								content: str,
								shift:5,
								btn: ['确认 ', '取消'] //只是为了演示
							,yes: function(){	
								_this.confirmAddDisk(vmId);  			        
							}
							,btn2: function(){
								layer.closeAll();
							}
							});
						});	
					}
					else{
						toastr.error("该虚拟机正在操作中,请稍后再试");
					}

				}
			});	

		},	

		config:function(vmId,name,proVdcId){
			var _this=this;
			var params=JSON.stringify({id:vmId});
			sugon.load({
				dataType:"json",
				action:sugon.rootURL + "/action/vm/queryVmTaskStatus",
				data:params,
				type:'POST',
				contentType:"application/json;charset=UTF-8",
				callback : function(result) {
					if(result){				
						$.get(sugon.rootURL+'/action/vm/toConfigVm?proVdcId='+proVdcId+'&vmId='+vmId, {}, function(str){
							layer.open({
								type: 1,
								title:'配置',
								//skin: 'layui-layer-rim', //加上边框
								area: ['500px', '300px'], //宽高
								content: str,
								shift:5,
								btn: ['确认 ', '取消'] //只是为了演示
							,yes: function(){	
								_this.confirmConfigVm(vmId,name);  			        
							}
							,btn2: function(){
								layer.closeAll();
							}
							});
						});	
					}
					else{
						toastr.error("该虚拟机正在操作中,请稍后再试");
					}

				}
			});
		},	
		updateVmName:function(vmId,name,description){
			var _this=this;
			var params=JSON.stringify({id:vmId});
			$.get(sugon.rootURL+'/action/vm/toUpdateVmName?vmId='+vmId+"&name="+name, {}, function(str){
				layer.open({
					type: 1,
					title:'编辑',
					//skin: 'layui-layer-rim', //加上边框
					area: ['500px', '300px'], //宽高
					content: str,
					shift:5,
					btn: ['确认 ', '取消'] //只是为了演示
				,yes: function(){	
					_this.confirmUpdateVmName(vmId,name);  			        
				}
				,btn2: function(){
					layer.closeAll();
				}
				});
			});	
		},	
		confirmUpdateVmName:function(vmId,vmName){
			var name=$("#vmName").val();
			var description=$("#description").val();
			var params={id:vmId,name:name,remarks:description};
			params=JSON.stringify(params);
			console.info(params);
			$("#updateVmForm").valid();
			if($("#updateVmForm").valid()){
				sugon.load({
					dataType:"json",
					action:sugon.rootURL + "/action/vm/updateVm",
					data:params,
					type:'POST',
					contentType:"application/json;charset=UTF-8",
					callback : function(result) {
						if(result.flag){
							layer.closeAll();
							console.info("#ddvmName");
							console.info(name);
							$("#ddvmName").text(name);
							$("#spanvmName").text("虚拟机 "+name);
							$("#ddDescription").text(description);
							toastr.success(result.message);

						}
						else{
							toastr.error(result.message);
						}
					}
				});
			}
		},
		confirmConfigVm:function(vmId,name){
			var memory=$("#memory").val()*1024;
			var cpuNum=$("#cpuNum").val();
			var params={vmId:vmId,name:name,vMemCapacity:memory,vCpuNumer:cpuNum};
			console.info(params);
			params=JSON.stringify(params);
			if($("#configVmForm").valid()){
				sugon.load({
					dataType:"json",
					action:sugon.rootURL + "/action/vm/configVm",
					data:params,
					type:'POST',
					contentType:"application/json;charset=UTF-8",
					callback : function(result) {
						if(result.flag){
							$("#ddCPU").text($("#cpuNum").val()+" 核");
							$("#ddMemory").text($("#memory").val()+" GB");
							toastr.success(result.message);
							layer.closeAll();
						}
						else{
							toastr.error(result.message);
						}
					}
				});
			}

		},
		initConfig:function(cpuNum,memory){
			$("#configVmForm").validate({
				debug:false,
				focusInvalid: true, //当为false时，验证无效时，没有焦点响应  
				onkeyup: true,
				submitHandler:function(form){  
				},
				rules: {

					cpuNum: {
						required: true,
						number:true,
						min:1,
						max:cpuNum
					},
					memory: {
						required: true,
						number:true,
						min:1,
						max:memory
					}
				},
				messages: {
					cpuNum: {
						required: "请输入CPU个数",
						number: "请输入整数",
						min:"最小容量为1",
						max:"不能超过VDC提供的CPU上限"+cpuNum+"个"
					},
					memory: {
						required: "请输入内存数",
						number:"请输入整数",
						min:"最小内存为1GB",
						max:"不能超过VDC提供的内存 上限"+memory+"GB"
					}
				}
			});

		},
		initUpdate:function(){
			$("#updateVmForm").validate({
				debug:false,
				focusInvalid: true, //当为false时，验证无效时，没有焦点响应  
				onkeyup: true,
				submitHandler:function(form){  
				},
				rules: {
					vmName: {
						required: true,
						rangelength:[1,100],
						remote:{
							type:'POST',
							url:sugon.rootURL + "/action/vm/validVmName",
							data:{name:function(){return $("#vmName").val()}}
						}
					}
				},
				messages: {
					vmName: {
						required: "请输入虚拟机名称",
						rangelength:"不能超过100个字符",
						remote:"存在相同名称虚拟机"
					}
				}
			});

		},
		confirmAddDisk:function(vmId,sPoolId){
			var params=new Array();
			console.info(vmId);
			var data={totalCapacity:$("#storSize").val(),vmId:vmId,sPoolId:$("#storId").text(),logicalPoolId:$("#storSele option:selected").val()};
			params.push(data);	
			/*			$("div.netcard-list").each(function(){
				var thiz=$(this);
				console.info(thiz);

				console.info(data);
				params.push(data);		
			});*/
			/*var params={netId:$("#netSele").val(),vmId:vmId,vlan:$("#vlan").text(),ip:$("#ip").val(),gateway:$("#gateway").text(),dns:$("#dns").text(),subnet:$("#subnet").text()};*/
			console.info(params);
			params=JSON.stringify(params);
			console.info(params);
			if($("#addDiskForm").valid()){
				sugon.load({
					dataType:"json",
					action:sugon.rootURL + "/action/vm/addVmDisk",
					data:params,
					type:'POST',
					contentType:"application/json;charset=UTF-8",
					callback : function(result) {
						for(var a=0;a<result.length;a++){
							console.info(result[a]);
							if(result[a].flag){
								toastr.success(result[a].message);
								layer.closeAll();
								$("#diskTable").datagrid('reload');
							}else{
								toastr.error(result[a].message);
							}
						}
					}
				});
			}
		},
		calculateSubnet:function(ip,netmask){
			var a=ip.split("."),
			b=netmask.split("."),
			c='';
			for( var i=0;i<4;i++){
				d=a[i]&b[i]
				if(i!=3){
					c=c+d+".";
				}
				else{
					c=c+d;
				}
			}
			return c;
		},
		confirmAddNetcard:function(vmId){
			/*			var netmask=$("#netmask").text();
			var ip=$("#ip").val();
			var subnet=this.calculateSubnet(ip,netmask);*/
			var params=new Array();
			console.info(vmId);
			var netId=$("#netSele option:selected").val();
			var vlan=$("#netSele option:selected").data("vlan");
			var gateway=$("#netSele option:selected").data("gateway");
			var dns=$("#netSele option:selected").data("dns");
			var subnet=$("#netSele option:selected").data("subnet");
			var data={netId:netId,vmId:vmId,vlan:vlan,ip:$("#ip").val(),gateway:gateway,dns:dns,subnet:subnet};
			params.push(data);	
			/*			$("div.netcard-list").each(function(){
				var thiz=$(this);
				console.info(thiz);
				var data={netId:thiz.data("id"),vmId:vmId,vlan:thiz.data("vlan"),ip:thiz.data("ip"),gateway:thiz.data("gateway"),dns:thiz.data("dns"),subnet:thiz.data("subnet")};
				console.info(data);
				params.push(data);		
			});*/
			/*var params={netId:$("#netSele").val(),vmId:vmId,vlan:$("#vlan").text(),ip:$("#ip").val(),gateway:$("#gateway").text(),dns:$("#dns").text(),subnet:$("#subnet").text()};*/
			console.info(params);
			params=JSON.stringify(params);
			console.info(params);
			if($("#addNetcardForm").valid()&&$("#netSele option:selected").length!=0){
				sugon.load({
					dataType:"json",
					action:sugon.rootURL + "/action/vm/addVmNetcard",
					data:params,
					type:'POST',
					contentType:"application/json;charset=UTF-8",
					callback : function(result) {
						for(var a=0;a<result.length;a++){
							console.info(result[a]);
							if(result[a].flag){
								toastr.success(result[a].message);
								layer.closeAll();
								$("#netTable").datagrid('reload');
							}else{
								toastr.error(result[a].message);
							}
						}
					}
				});
			}
		},
		initAddNDisk:function(){
			$("#addDiskForm").validate({
				debug:false,
				focusInvalid: true, //当为false时，验证无效时，没有焦点响应  
				onkeyup: true,
				submitHandler:function(form){  
				},
				rules: {

					storSize: {
						required: true,
						number:true,
						min:1,
						max:$("#storUnused").data("value")
					}

				},
				messages: {
					storSize: {
						required: "请输入磁盘容量",
						number: "请输入整数",
						min:"最小容量为1",
						max:"最大容量不能超过总容量"
					}
				}
			});
			/*			$("#addDiskBtn").on("click",function(){
				var avaiStorSize=$("#storUnused").data("value");
				console.info(avaiStorSize);
				$("#storUsed").text();
				var currentUnusedVal=avaiStorSize-$("#storSize").val();
				var currentUsedVal=parseInt($("#storUsed").data("value"))+parseInt($("#storSize").val());
				if($("#addDiskForm").valid()&&avaiStorSize>1&&currentUnusedVal>=0){
					var item='<div class="netcard-list" data-totalcapacity="'+$("#storSize").val()+'" data-spoolid="'+$("#storId").text()+'" data-logicalpoolid="'+$("#storSele option:selected").val()+'"><span>'+$("#storSize").val()+' GB</span><a href="javascript:;" onclick="cloudmanager.vmManagement.removeDiskItem(this)"><i class="fa fa-times"></i></a></div>';
					$("#addedDisk").append(item);
					$("#storUnused").data("value",currentUnusedVal);
					$("#storUnused").text(currentUnusedVal+" GB");
					$("#storUsed").text(currentUsedVal+" GB");
					$("#storUsed").data("value",currentUsedVal);
				}    	
			});*/
		},
		removeDiskItem:function(thiz){
			var item=$(thiz).parent(".netcard-list");
			var currentVal=$("#storUnused").data("value")+item.data("totalcapacity");
			$("#storUnused").data("value",currentVal);
			$("#storUnused").text(currentVal+" GB");
			$(thiz).parent(".netcard-list").remove();

		},
		initAddNetcard:function(){
			jQuery.validator.addMethod(
					"ip",
					function(value, element) {
						return this.optional(element)
						|| /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([0-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/
						.test(value);
					}, "请填写正确的IP地址。");
			$("#addNetcardForm").validate({
				debug:false,
				focusInvalid: false, //当为false时，验证无效时，没有焦点响应  
				onkeyup: false,
				submitHandler:function(form){  
				},
				rules: {

					ip: {
						required: true,
						ip:true
						/*						,remote:{type:'POST',
							url:sugon.rootURL + "/action/vm/validIp",
						    data:{ip:function(){return $("#ip").val()},
						    	subnet :function(){return $("#subnet").text() }
						    }
						}*/
					}
				},
				messages: {
					ip: {
						required: "请输入IP地址",
						ip: "IP地址格式不合法"
							/*,remote:"IP验证不通过"*/
					}
				}
			});
			$("#netSele").on("change",function(){
				console.info("aaaa");
				var curOpt=$("#netSele option:selected");
				$("#gateway").text(curOpt.data("gateway"));
				$("#subnet").text(curOpt.data("subnet"));
				$("#vlan").text(curOpt.data("vlan"));
				$("#dns").text(curOpt.data("dns"));
			});      
			/*			$("#addNetcardBtn").on("click",function(){
				var curOpt=$("#netSele option:selected");
				console.info(curOpt.length);
				if(curOpt.length==0&&$("#netSeleInfo").length==0){
					$("#netSele").after('<span style="margin-left: 10px; color: red" id="netSeleInfo">无可以使用的网络池了</span>');	
				}
				if($("#addNetcardForm").valid()&&curOpt.length==1){
					var item='<div class="netcard-list" data-gateway="'+$("#gateway").text()+'" data-subnet="'+$("#subnet").text()+'" data-vlan="'+$("#vlan").text()+'" data-dns="'+$("#dns").text()+'" data-id="'+curOpt.val()+'" data-name="'+curOpt.text()+'" data-ip="'+$("#ip").val()+'"><span>'+curOpt.text()+',</span><span>IP:'+$("#ip").val()+'</span><a href="javascript:;" onclick="cloudmanager.vmManagement.removeItem(this)"><i class="fa fa-times"></i></a></div>';
					$("#addedNetcard").append(item);
					curOpt.remove();
				}    	
			});*/
		},

		removeItem:function(thiz){
			var item=$(thiz).parent(".netcard-list");
			var id=item.data("id");
			var name=item.data("name");
			var dns=item.data("dns");
			var subnet=item.data("subnet");
			var vlan=item.data("vlan");
			var gateway=item.data("gateway");
			var option="<option value='"+id+"' data-dns='"+dns+"' data-subnet='"+subnet+"' data-gateway='"+gateway+"' data-vlan='"+vlan+"'>"+name+"</option>";
			if($("#netSeleInfo").length!=0){
				$("#netSeleInfo").remove();
			}
			$("#netSele").append(option);
			$(thiz).parent(".netcard-list").remove();
		},
		removeNetItem:function(thiz){
			console.info("aaaaaaaaaaa");
			var item=$(thiz).parent(".list-group-item");
			var id=item.data("id");
			var name=item.data("name");
			var dns=item.data("dns");
			var subnet=item.data("subnet");
			var vlan=item.data("vlan");
			var gateway=item.data("gateway");
			console.info()
			var option="<option value='"+id+"' data-dns='"+dns+"' data-subnet='"+subnet+"' data-gateway='"+gateway+"' data-vlan='"+vlan+"' data-added='false'>"+name+"</option>";
			if($("#netSeleInfo").length!=0){
				$("#netSeleInfo").remove();
			}
			$("#netSele").append(option);
			item.remove();
		},
		deleteVm:function(vmId,vmName){
			layer.confirm('是否删除虚拟机？', {
				title:'删除虚拟机',
				icon: 3,
				btn: ['确认','取消'] //按钮
			}, function(index){
				var params={id:vmId,name:vmName};
				params=JSON.stringify(params);
				sugon.load({
					dataType:"json",
					action:sugon.rootURL + "/action/vm/deleteVm",
					data:params,
					type:'POST',
					contentType:"application/json;charset=UTF-8",
					callback : function(result) {
						if(result.flag){
							toastr.success(result.message);
							layer.closeAll();
							$("#vmTable").datagrid('reload');	
							/*						$('#sugonDialog').dialog("close");
							$("#vmTable").datagrid('load');	*/
						}
						else{
							toastr.error(result.message);
							layer.closeAll();
						}
					}
				});
			});
		}
}
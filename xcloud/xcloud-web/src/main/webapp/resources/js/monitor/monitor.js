cloudmanager.namespace("monitor");
cloudmanager.monitor = {
		
		
	     // 主机top数据
		 listName : ['avgHostCpuUsage','hostCpuUsage','avgHostMemUsage','hostMemUsage', 'avgHostDiskIO','hostDiskIo', 'avgHostNetIO','hostNetIo'],
	     elementId : [ 'main-cpu-left', 'main-cpu-right', 'main-mem-left', 'main-mem-right','main-disk-left', 'main-disk-right','main-net-left' , 'main-net-right' ],
	     properties : [ 'topHostCpuUsage','', 'topHostMemUsage','', 'topHostDiskIO','', 'topHostNetIO','' ],
	     show : [ 'CPU使用率平均值(%)','CPU使用率(%)', '内存使用率平均值(%)', '内存使用率(%)','磁盘使用率平均值(KB/S)', '磁盘使用率(KB/S)','网络使用率平均值(KB/S)','网络使用率(KB/S)'],
   
	    // 虚拟机top数据
		 listName_vm : ['avgVMCpuUsage','vmCpuUsage','avgVMMemUsage','vmMemUsage', 'avgVMDiskIO','vmDiskIo', 'avgVMNetIO','vmNetIo'],
	     elementId_vm : [ 'virtual-cpu-left', 'virtual-cpu-right', 'virtual-mem-left', 'virtual-mem-right','virtual-disk-left', 'virtual-disk-right','virtual-net-left' , 'virtual-net-right' ],
	     properties_vm : [ 'topVMCpuUsage','', 'topVMMemUsage','', 'topVMDiskIO','', 'topVMNetIO','' ],
	 
		//物理机、虚拟机、存储状态饼图
	     elementId_status : ['gk-host','gk-vm','gk-store'],
	     title_tatus : ['主机','虚拟机','存储对象'],
	     properties_unaccess : ['hostUnaccessibleNum','vmUnaccessibleNum','storeUnaccessibleNum'],
	     properties_access : ['hostAccessibleNum','vmAccessibleNum','storeAccessibleNum'],
  
  
	     
	     //初始化列表页面topnN
	        initTopN : function() {
	            _this=this;
	            for(var n=0;n<8;n++){
	               /* $('#'+_this.elementId[n]).width(width);*/
	            	if(n%2==1){
	            		var name=new Array();
	 	                var value = new Array();
	 	                for(var i=0;i<3;i++){
	 	                        name[i]="";
	 	                        value[i]="";
	 	                }
	 	                cloudmanager.monitorSummary.initHorizonBar(_this.elementId[n],_this.show[n],name,value);
	 	                cloudmanager.monitorSummary.initHorizonBar(_this.elementId_vm[n],_this.show[n],name,value);
	            	}else{
	            		var max=100;
	 	                var value =0;
	 	                if(n>3){
	 	                	cloudmanager.monitorSummary.initGua(_this.elementId[n],_this.show[n],max,value,'{value}KB/S');
	 	                	cloudmanager.monitorSummary.initGua(_this.elementId_vm[n],_this.show[n],max,value,'{value}KB/S');
	 	                }else{
	 	                	cloudmanager.monitorSummary.initGua(_this.elementId[n],_this.show[n],max,value,'{value}%');
	 	                	cloudmanager.monitorSummary.initGua(_this.elementId_vm[n],_this.show[n],max,value,'{value}%');
	 	                }
	            		
	            	}
	               
	            }
	            
	            //初始化饼图
	            for(var n=0;n<3;n++){
	               cloudmanager.monitorSummary.initCircle(_this.elementId_status[n],_this.title_tatus[n],0,0); 
	            }
	            
	           
	        },
	        
	        //更新列表页面topN
	        updateTopN : function () {
//	            console.info("cluster---");
	            _this=this;
	            $.ajax( {  
	                type : 'get',  
	                contentType : 'application/json',  
	                url : sugon.rootURL + '/monitor/abstract/getHostInfo', 
	                dataType : 'json',  
	                success : function(data) {/*
	                    console.log(data);
	                    for(var n=0;n<8;n++){
	                    	if(n%2==1){
		                        var topn=3;
		                        var clusterSize=data[_this.listName[n]].length;
		                        var name=new Array();
		                        var value = new Array();
		                        for(var i=0;i<topn;i++){
		                            if (i<topn-clusterSize) {
		                                name[i]="";
		                                value[i]="";
		                            }else{
		                                var topnCpuUsage=data.hostInfo.topN[_this.listName[n]];
		                                name[i]=topnCpuUsage[topn-i-1].name;
		                                value[i]=topnCpuUsage[topn-i-1][_this.properties[n]];
		                            }
		                        }
		                        cloudmanager.monitorSummary.initHorizonBar(_this.elementId[n],_this.show[n],name,value);
	                    	}else{
	                    		var max='100';
	    	 	                var value =data.hostInfo[_this.listName[n]];
	    	 	                if(n>3){
	    	 	                	cloudmanager.monitorSummary.initGua(_this.elementId[n],_this.show[n],max,value,'{value}KB/S');
	    	 	                }else{
	    	 	                	cloudmanager.monitorSummary.initGua(_this.elementId[n],_this.show[n],max,value,'{value}%');
	    	 	                } 
	                    		
	                    	}
	                    }
	                */
	                	
	                	//修改cpu、内存、磁盘的使用信息
	                	$("#cpuTotalGHz").html(data.cpuTotalGHz+"GHz");
	                	$("#cpuUsedGHz").html(data.cpuUsedGHz+"GHz");
	                	$("#cpuFreeGHz").html(data.cpuFreeGHz+"GHz");
	                	$("#cpuUsage").html(data.cpuUsage+"%")
	                	$("#memTotalGB").html(data.memTotalGB+"GB");
	                	$("#memUsedGB").html(data.memUsedGB+"GB");
	                	$("#memFreeGB").html(data.memFreeGB+"GB");
	                	$("#memUsage").html(data.memUsage+"%");
	                	$("#storeTotalTB").html(data.storeTotalTB+"TB");
	                	$("#storeUsedTB").html(data.storeUsedTB+"TB");
	                	$("#storeFreeTB").html(data.storeFreeTB+"TB");
	                	$("#storeUsage").html(data.storeUsage+"%");
	                	
	                	$("#cpuUsagePie").width(data.cpuUsagePie+"%");	
	                	$("#memUsagePie").width(data.memUsagePie+"%");
	                	$("#diskUsagePie").width(data.diskUsagePie+"%");
	                	
	                	$("#hostNum").html(data.hostNum);
	                	$("#hostAccessibleNum").html(data.hostAccessibleNum);
	                	$("#hostUnaccessibleNum").html(data.hostUnaccessibleNum);
	                	$("#vmNum").html(data.vmNum);
	                	$("#vmAccessibleNum").html(data.vmAccessibleNum);
	                	$("#vmUnaccessibleNum").html(data.vmUnaccessibleNum);
	                	$("#storeNum").html(data.storeNum);
	                	$("#storeAccessibleNum").html(data.storeAccessibleNum);
	                	$("#storeUnaccessibleNum").html(data.storeUnaccessibleNum);
	                	
	                	  
	                	 //更新饼图
	    	            for(var n=0;n<3;n++){
	    	               cloudmanager.monitorSummary.initCircle(_this.elementId_status[n],_this.title_tatus[n],data[_this.properties_access[n]],data[_this.properties_unaccess[n]]); 
	    	            }
	    	            
	                	
	                	//更新告警分类图
	    	            $("#host_warning").html(data.host_warning);
	                	$("#host_ciritical").html(data.host_ciritical);
	                	$("#vm_warning").html(data.vm_warning);
	                	$("#vm_ciritical").html(data.vm_ciritical);
	                	$("#store_warning").html(data.store_warning);
	                	$("#store_ciritical").html(data.store_ciritical);
	                
	                	//更新主机、虚拟机、磁盘对象状态图
	                	/*updateEchartPie(cluster_host_status,data.hostVmStoreStatus[0]);
	                	updateEchartPie(cluster_vm_status,data.hostVmStoreStatus[1]);
	                	updateEchartPie(cluster_store_status,data.hostVmStoreStatus[2]);*/
	                	
	                	 for(var n=0;n<8;n++){
		                    	if(n%2==1){
			                        
		                    		cloudmanager.monitorSummary.initHorizonBar(_this.elementId[n],_this.show[n],data[_this.listName[n]].host,data[_this.listName[n]].value);
		                    		cloudmanager.monitorSummary.initHorizonBar(_this.elementId_vm[n],_this.show[n],data[_this.listName_vm[n]].vm,data[_this.listName_vm[n]].value);

		                    	}else{	
		    	 	                if(n>3){
		    	 	                	cloudmanager.monitorSummary.initGua(_this.elementId[n],_this.show[n],data[_this.properties[n]],data[_this.listName[n]],'{value}KB/S');
		    	 	                	cloudmanager.monitorSummary.initGua(_this.elementId_vm[n],_this.show[n],data[_this.properties_vm[n]],data[_this.listName_vm[n]],'{value}KB/S');
		    	 	                }else{
		    	 	                	cloudmanager.monitorSummary.initGua(_this.elementId[n],_this.show[n],data[_this.properties[n]],data[_this.listName[n]],'{value}%');
		    	 	                	cloudmanager.monitorSummary.initGua(_this.elementId_vm[n],_this.show[n],data[_this.properties_vm[n]],data[_this.listName_vm[n]],'{value}%');
		    	 	                }   
			         	        }
		                 }
	                	 
	                	 cloudmanager.monitorSummary.initAlarmTable("monitorIndexTableId",data.alarmList);
	                
	                },  
	                error : function() {
	                    console.info("从"+url+"获取数据失败！");
	                }  
	              }
	            );
	        },
		
		
		init : function() {
			$('#monitorIndexTableId').datagrid({
				url : sugon.rootURL + '/proVDC/queryPorVDCTable',
				method : 'post',
				striped : true,
				fitColumns : true,
				singleSelect : true,
				rownumbers : true,
				pagination : true,
				pageNumber : 1,
				nowrap : false,
				pageSize : 20,
				toolbar : '#monitorIndextb',
				pageList : [ 2, 20, 50, 100, 150, 200 ],
				queryParams : {},
				loadMsg : "加载中.....",
				showFooter : true,
				columns : [ [ {
					field : "name",
					title : "名称",
					align : "left",
					width : '25%',
					formatter : cloudmanager.monitor.formatName
				}, {
					field : "vCpuNum",
					title : "描述",
					align : "center",
					width : '8%'
				}, {
					field : "vCpuOverplus",
					title : "所属对象",
					align : "center",
					width : '8%'
				}, {
					field : "memorySize",
					title : "内存总量(GB)",
					align : "center",
					width : '8%'
				}, {
					field : "memoryOverplus",
					title : "是否确认",
					align : "center",
					width : '8%'
				}, {
					field : "createDate",
					title : "触发时间",
					align : "center",
					width : '12%'
				}, {
					field : "operate",
					title : "操作",
					align : "center",
					width : '18%',
					formatter : cloudmanager.monitor.formatOper
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
			var p = $('#monitorIndexTableId').datagrid('getPager');
			$(p).pagination({
				layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
				displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
				beforePageText:'跳转到第 ',
				afterPageText:' 页'
			});
			$('#monitorIndexSearchInputId').searchbox({
				searcher:function(value,name){
					var queryParams = $('#monitorIndexTableId').datagrid('options').queryParams;
					queryParams.name = value;
					$("#monitorIndexTableId").datagrid('reload');
				},
			    menu:'#tableSearch',
			    prompt:'请输入查询内容'
			});
			$('#monitorIndexSearchInputId').searchbox('addClearBtn', 'icon-clear');
			var myChart = echarts.init(document.getElementById('host'));
			var option = {
			    tooltip: {
			        trigger: 'item',
			        formatter: "{a} <br/>{b}: {c} ({d}%)",
			        position: [10,10]
			    },
			    series: [
			        {
			            name:'主机',
			            type:'pie',
			            radius: ['50%', '70%'],
			            avoidLabelOverlap: false,
			            hoverAnimation: false,
			            legendHoverLink: false,
			            color: ['#3facca', '#dcdbdb'],
			            label: {
			                normal: {
			                    show: true,
			                    position: 'center',
			                    formatter: '主机:20',
			                    textStyle: {
			                    	color: '#333333',
			                    	fontSize: 16
			                    }
			                    
			                },
			            },
			            labelLine: {
			                normal: {
			                    show: false
			                }
			            },
			            data:[
			                {value:2, name:'正常'},
			                {value:18, name:'异常'}
			            ]
			        }
			    ]
			};
			myChart.setOption(option); //主机圆圈

			var myChart1 = echarts.init(document.getElementById('gk-storage'));
			var option1 = {
			    tooltip: {
			        trigger: 'item',
			        formatter: "{a} <br/>{b}: {c} ({d}%)",
			        position: 'left'
			    },
			    series: [
			        {
			            name:'主机',
			            type:'pie',
			            radius: ['50%', '70%'],
			            avoidLabelOverlap: false,
			            hoverAnimation: false,
			            legendHoverLink: false,
			            color: ['#30c8b4', '#dcdbdb'],
			            label: {
			                normal: {
			                    show: true,
			                    position: 'center',
			                    formatter: '虚拟机:50',
			                    textStyle: {
			                    	color: '#333333',
			                    	fontSize: 16,
			                    }
			                    
			                },
			            },
			            labelLine: {
			                normal: {
			                    show: false
			                }
			            },
			            data:[
			                {value:14, name:'正常'},
			                {value:36, name:'异常'}
			            ]
			        }
			    ]
			};
			myChart1.setOption(option1);  //虚拟机圆圈

			var myChart2 = echarts.init(document.getElementById('gk-virtual'));
			var option2 = {
			    tooltip: {
			        trigger: 'item',
			        formatter: "{a} <br/>{b}: {c} ({d}%)",
			        position: 'left'
			    },
			    series: [
			        {
			            name:'主机',
			            type:'pie',
			            radius: ['50%', '70%'],
			            avoidLabelOverlap: false,
			            hoverAnimation: false,
			            legendHoverLink: false,
			            color: ['#94cb4c', '#dcdbdb'],
			            label: {
			                normal: {
			                    show: true,
			                    position: 'center',
			                    formatter: '存储:5',
			                    textStyle: {
			                    	color: '#333333',
			                    	fontSize: 16,
			                    }
			                    
			                },
			            },
			            labelLine: {
			                normal: {
			                    show: false
			                }
			            },
			            data:[
			                {value:4, name:'正常'},
			                {value:1, name:'异常'}
			            ]
			        }
			    ]
			};
			myChart2.setOption(option2);  //存储圆圈

			var myChart3 = echarts.init(document.getElementById('main-cpu-left'));
			var option3 = {
			    tooltip : {
			        formatter: "{a} <br/>{b} : {c}%"
			    },
			    title: {
			    	text: 'CPU使用率平均值(%)',
			    	top: 'bottom',
			    	left: 'center',
			    	textStyle: {
						color: '#333',
						fontStyle: 'normal',
						fontWeight: '300',
						fontFamily: '微软雅黑',
						fontSize: 14,
						},
			    },
			    series: [
			        {
			            name: '平均值',
			            type: 'gauge',
			            splitNumber: 5,
			            detail: {
			            	formatter:'{value}%',
			            	textStyle: {
			            		fontSize: 20,
			            	}
			            },
			            data: [{value: 60,}],
			            axisTick:{
			                length:15,
			                splitNumber: 4,
			                lineStyle:{
			                	color:'auto',
			                }
			            },
			            splitLine:{
			                length:18,
			                lineStyle:{
			                	color:'auto',
			                }
			            },
			            axisLabel:{
			            	textStyle: {
			            		fontSize: 12,
			            	},
			            },
			            pointer:{
			            	length:'70%',
			            	width: 5,
			            },
			            axisLine:{
			                lineStyle:{
			                    width:10,
			                    color:[[0.2, '#5dbb44'], [0.8, '#4488bb'], [1, '#f34c3b']]
			                }
			                
			            },

			            radius: '85%',
			        }
			    ]
			};
			myChart3.setOption(option3);//主机-cpu-仪表盘

			var myChart4 = echarts.init(document.getElementById('main-cpu-right'));
			var option4 = {
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    title: {
				    	text: 'CPU使用率(%)',
				    	top: 'bottom',
				    	left: 'center',
				    	textStyle: {
							color: '#333',
							fontStyle: 'normal',
							fontWeight: '300',
							fontFamily: '微软雅黑',
							fontSize: 14,
							},
			    	},
				    itemStyle: {
				    	normal: {
				    		color: ['#e67436'],
				    	},
				    },
				    grid: {
				        left: '2%',
				        right: '4%',
				        bottom: '15%',
				        width: '450px',
				        containLabel: true
				    },
				    xAxis:  {
				        type: 'value',
				        axisLine: {
				        	lineStyle: {
				        		color: ['#268cb6'],
				        		width: 3,
				        	},
				        },
				    },
				    yAxis: {
				        type: 'category',
				        data: ['10.33.23.455','10.33.42.23','10.33.44.55'],
				        axisLine: {
				        	lineStyle: {
				        		color: ['#268cb6'],
				        		width: 3,
				        	},
				        },
				    },
				    series: [
				        {
				            name: 'cpu使用率',
				            type: 'bar',
				            stack: '总量',
				            label: {
				                normal: {
				                    show: true,
				                    position: 'insideRight'
				                }
				            },
				            data: [60, 80, 100],
				        },
				        
				    ]
				};
			myChart4.setOption(option4);//主机-cpu-柱状图

			var myChart5 = echarts.init(document.getElementById('main-mem-left'));
			var option5 = {
			    tooltip : {
			        formatter: "{a} <br/>{b} : {c}%"
			    },
			    title: {
			    	text: '内存使用率平均值(%)',
			    	top: 'bottom',
			    	left: 'center',
			    	textStyle: {
						color: '#333',
						fontStyle: 'normal',
						fontWeight: '300',
						fontFamily: '微软雅黑',
						fontSize: 14,
						},
			    },
			    series: [
			        {
			            name: '平均值',
			            type: 'gauge',
			            splitNumber: 5,
			            detail: {
			            	formatter:'{value}%',
			            	textStyle: {
			            		fontSize: 20,
			            	}
			            },
			            data: [{value: 60,}],
			            axisTick:{
			                length:15,
			                splitNumber: 4,
			                lineStyle:{
			                	color:'auto',
			                }
			            },
			            splitLine:{
			                length:18,
			                lineStyle:{
			                	color:'auto',
			                }
			            },
			            axisLabel:{
			            	textStyle: {
			            		fontSize: 12,
			            	},
			            },
			            pointer:{
			            	length:'70%',
			            	width: 5,
			            },
			            axisLine:{
			                lineStyle:{
			                    width:10,
			                    color:[[0.2, '#5dbb44'], [0.8, '#4488bb'], [1, '#f34c3b']]
			                }
			                
			            },

			            radius: '85%',
			        }
			    ]
			};
			myChart5.setOption(option5);//主机-内存-仪表盘

			var myChart6 = echarts.init(document.getElementById('main-mem-right'));
			var option6 = {
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    title: {
				    	text: '内存使用率(%)',
				    	top: 'bottom',
				    	left: 'center',
				    	textStyle: {
							color: '#333',
							fontStyle: 'normal',
							fontWeight: '300',
							fontFamily: '微软雅黑',
							fontSize: 14,
							},
			    	},
				    itemStyle: {
				    	normal: {
				    		color: ['#e67436'],
				    	},
				    },
				    grid: {
				        left: '2%',
				        right: '4%',
				        bottom: '15%',
				        width: '450px',
				        containLabel: true
				    },
				    xAxis:  {
				        type: 'value',
				        axisLine: {
				        	lineStyle: {
				        		color: ['#268cb6'],
				        		width: 3,
				        	},
				        },
				    },
				    yAxis: {
				        type: 'category',
				        data: ['10.33.23.455','10.33.42.23','10.33.44.55'],
				        axisLine: {
				        	lineStyle: {
				        		color: ['#268cb6'],
				        		width: 3,
				        	},
				        },
				    },
				    series: [
				        {
				            name: '内存使用率',
				            type: 'bar',
				            stack: '总量',
				            label: {
				                normal: {
				                    show: true,
				                    position: 'insideRight'
				                }
				            },
				            data: [60, 80, 100],
				        },
				        
				    ]
				};
			myChart6.setOption(option6);//主机-内存-柱状图

			var myChart7 = echarts.init(document.getElementById('main-disk-left'));
			var option7 = {
			    tooltip : {
			        formatter: "{a} <br/>{b} : {c}%"
			    },
			    title: {
			    	text: '磁盘使用率平均值(%)',
			    	top: 'bottom',
			    	left: 'center',
			    	textStyle: {
						color: '#333',
						fontStyle: 'normal',
						fontWeight: '300',
						fontFamily: '微软雅黑',
						fontSize: 14,
						},
			    },
			    series: [
			        {
			            name: '平均值',
			            type: 'gauge',
			            splitNumber: 5,
			            detail: {
			            	formatter:'{value}%',
			            	textStyle: {
			            		fontSize: 20,
			            	}
			            },
			            data: [{value: 60,}],
			            axisTick:{
			                length:15,
			                splitNumber: 4,
			                lineStyle:{
			                	color:'auto',
			                }
			            },
			            splitLine:{
			                length:18,
			                lineStyle:{
			                	color:'auto',
			                }
			            },
			            axisLabel:{
			            	textStyle: {
			            		fontSize: 12,
			            	},
			            },
			            pointer:{
			            	length:'70%',
			            	width: 5,
			            },
			            axisLine:{
			                lineStyle:{
			                    width:10,
			                    color:[[0.2, '#5dbb44'], [0.8, '#4488bb'], [1, '#f34c3b']]
			                }
			                
			            },

			            radius: '85%',
			        }
			    ]
			};
			myChart7.setOption(option7);//主机-磁盘-仪表盘

			var myChart8 = echarts.init(document.getElementById('main-disk-right'));
			var option8 = {
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    title: {
				    	text: '磁盘使用率(%)',
				    	top: 'bottom',
				    	left: 'center',
				    	textStyle: {
							color: '#333',
							fontStyle: 'normal',
							fontWeight: '300',
							fontFamily: '微软雅黑',
							fontSize: 14,
							},
			    	},
				    itemStyle: {
				    	normal: {
				    		color: ['#e67436'],
				    	},
				    },
				    grid: {
				        left: '2%',
				        right: '4%',
				        bottom: '15%',
				        width: '450px',
				        containLabel: true
				    },
				    xAxis:  {
				        type: 'value',
				        axisLine: {
				        	lineStyle: {
				        		color: ['#268cb6'],
				        		width: 3,
				        	},
				        },
				    },
				    yAxis: {
				        type: 'category',
				        data: ['10.33.23.455','10.33.42.23','10.33.44.55'],
				        axisLine: {
				        	lineStyle: {
				        		color: ['#268cb6'],
				        		width: 3,
				        	},
				        },
				    },
				    series: [
				        {
				            name: '磁盘io速率',
				            type: 'bar',
				            stack: '总量',
				            label: {
				                normal: {
				                    show: true,
				                    position: 'insideRight'
				                }
				            },
				            data: [60, 80, 100],
				        },
				        
				    ]
				};
			myChart8.setOption(option8);//主机-磁盘-柱状图

			var myChart9 = echarts.init(document.getElementById('main-net-left'));
			var option9 = {
			    tooltip : {
			        formatter: "{a} <br/>{b} : {c}%"
			    },
			    title: {
			    	text: '网络使用率平均值(%)',
			    	top: 'bottom',
			    	left: 'center',
			    	textStyle: {
						color: '#333',
						fontStyle: 'normal',
						fontWeight: '300',
						fontFamily: '微软雅黑',
						fontSize: 14,
						},
			    },
			    series: [
			        {
			            name: '平均值',
			            type: 'gauge',
			            splitNumber: 5,
			            detail: {
			            	formatter:'{value}%',
			            	textStyle: {
			            		fontSize: 20,
			            	}
			            },
			            data: [{value: 60,}],
			            axisTick:{
			                length:15,
			                splitNumber: 4,
			                lineStyle:{
			                	color:'auto',
			                }
			            },
			            splitLine:{
			                length:18,
			                lineStyle:{
			                	color:'auto',
			                }
			            },
			            axisLabel:{
			            	textStyle: {
			            		fontSize: 12,
			            	},
			            },
			            pointer:{
			            	length:'70%',
			            	width: 5,
			            },
			            axisLine:{
			                lineStyle:{
			                    width:10,
			                    color:[[0.2, '#5dbb44'], [0.8, '#4488bb'], [1, '#f34c3b']]
			                }
			                
			            },

			            radius: '85%',
			        }
			    ]
			};
			myChart9.setOption(option9);//主机-网络-仪表盘

			var myChart10 = echarts.init(document.getElementById('main-net-right'));
			var option10 = {
				    tooltip : {
				        trigger: 'axis',
				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
				        }
				    },
				    title: {
				    	text: '网络使用率(%)',
				    	top: 'bottom',
				    	left: 'center',
				    	textStyle: {
							color: '#333',
							fontStyle: 'normal',
							fontWeight: '300',
							fontFamily: '微软雅黑',
							fontSize: 14,
							},
			    	},
				    itemStyle: {
				    	normal: {
				    		color: ['#e67436'],
				    	},
				    },
				    grid: {
				        left: '2%',
				        right: '4%',
				        bottom: '15%',
				        width: '450px',
				        containLabel: true
				    },
				    xAxis:  {
				        type: 'value',
				        axisLine: {
				        	lineStyle: {
				        		color: ['#268cb6'],
				        		width: 3,
				        	},
				        },
				    },
				    yAxis: {
				        type: 'category',
				        data: ['10.33.23.455','10.33.42.23','10.33.44.55'],
				        axisLine: {
				        	lineStyle: {
				        		color: ['#268cb6'],
				        		width: 3,
				        	},
				        },
				    },
				    series: [
				        {
				            name: '网络io速率',
				            type: 'bar',
				            stack: '总量',
				            label: {
				                normal: {
				                    show: true,
				                    position: 'insideRight'
				                }
				            },
				            data: [60, 80, 100],
				        },
				        
				    ]
				};
			myChart10.setOption(option10);//主机-网络-柱状图

			var myChartgj1 = echarts.init(document.getElementById('gj-virtual-right'));
			var optiongj1 = {
			    tooltip: {
			        trigger: 'item',
			        formatter: "{a} <br/>{b}: {c} ({d}%)",
			        position: [10,10]
			    },
			    title: {
			    	text: '总计：20',
			    	top: 'bottom',
			    	left: 'center',
			    	textStyle: {
						color: '#333',
						fontStyle: 'normal',
						fontWeight: '300',
						fontFamily: '微软雅黑',
						fontSize: 16,
						},
			    },
			    series: [
			        {
			            name:'主机',
			            type:'pie',
			            radius: ['50%', '60%'],
			            avoidLabelOverlap: false,
			            hoverAnimation: false,
			            legendHoverLink: false,
			            color: ['#3facca', '#dcdbdb'],
			            label: {
			                normal: {
			                    show: true,
			                    position: 'center',
			                    formatter: '异常：12',
			                    textStyle: {
			                    	color: '#333333',
			                    	fontSize: 14,
			                    	fontWeight: '300',
			                    }
			                    
			                },
			            },
			            labelLine: {
			                normal: {
			                    show: false
			                }
			            },
			            data:[
			                {value:8, name:'正常'},
			                {value:12, name:'异常'}
			            ]
			        }
			    ]
			};
			myChartgj1.setOption(optiongj1); //虚拟机告警

			var myChartgj2 = echarts.init(document.getElementById('gj-main-right'));
			var optiongj2 = {
			    tooltip: {
			        trigger: 'item',
			        formatter: "{a} <br/>{b}: {c} ({d}%)",
			        position: [10,10]
			    },
			    title: {
			    	text: '总计：20',
			    	top: 'bottom',
			    	left: 'center',
			    	textStyle: {
						color: '#333',
						fontStyle: 'normal',
						fontWeight: '300',
						fontFamily: '微软雅黑',
						fontSize: 16,
						},
			    },
			    series: [
			        {
			            name:'物理机',
			            type:'pie',
			            radius: ['50%', '60%'],
			            avoidLabelOverlap: false,
			            hoverAnimation: false,
			            legendHoverLink: false,
			            color: ['#3facca', '#dcdbdb'],
			            label: {
			                normal: {
			                    show: true,
			                    position: 'center',
			                    formatter: '异常：12',
			                    textStyle: {
			                    	color: '#333333',
			                    	fontSize: 14,
			                    	fontWeight: '300',
			                    }
			                    
			                },
			            },
			            labelLine: {
			                normal: {
			                    show: false
			                }
			            },
			            data:[
			                {value:8, name:'正常'},
			                {value:12, name:'异常'}
			            ]
			        }
			    ]
			};
			myChartgj2.setOption(optiongj2); //物理机告警

			var myChartgj3 = echarts.init(document.getElementById('gj-storage-right'));
			var optiongj3 = {
			    tooltip: {
			        trigger: 'item',
			        formatter: "{a} <br/>{b}: {c} ({d}%)",
			        position: [10,10]
			    },
			    title: {
			    	text: '总计：20',
			    	top: 'bottom',
			    	left: 'center',
			    	textStyle: {
						color: '#333',
						fontStyle: 'normal',
						fontWeight: '300',
						fontFamily: '微软雅黑',
						fontSize: 16,
						},
			    },
			    series: [
			        {
			            name:'存储',
			            type:'pie',
			            radius: ['50%', '60%'],
			            avoidLabelOverlap: false,
			            hoverAnimation: false,
			            legendHoverLink: false,
			            color: ['#3facca', '#dcdbdb'],
			            label: {
			                normal: {
			                    show: true,
			                    position: 'center',
			                    formatter: '异常：12',
			                    textStyle: {
			                    	color: '#333333',
			                    	fontSize: 14,
			                    	fontWeight: '300',
			                    }
			                    
			                },
			            },
			            labelLine: {
			                normal: {
			                    show: false
			                }
			            },
			            data:[
			                {value:8, name:'正常'},
			                {value:12, name:'异常'}
			            ]
			        }
			    ]
			};
			myChartgj3.setOption(optiongj3); //存储告警
			var myChartjq1 = echarts.init(document.getElementById('jq-cpu-first'));
			var optionjq1 = {
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    itemStyle: {
				    	normal: {
				    		color: ['#e67436'],
				    	},
				},
				title: {
				    	text: 'CPU使用率(%)',
				    	top: 'bottom',
				    	left: 'center',
				    	textStyle: {
							color: '#333',
							fontStyle: 'normal',
							fontWeight: '300',
							fontFamily: '微软雅黑',
							fontSize: 14,
							},
			    	},
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '10%',
			        top:'1%',
			        containLabel: true
			    },
			    xAxis:  {
			        type: 'value',
			        axisLine: {
				        	lineStyle: {
				        		color: ['#268cb6'],
				        		width: 3,
				        	},
				        },
			    },
			    yAxis: {
			        type: 'category',
			        data: ['10.33.23.455','10.33.42.23','10.33.44.55','10.33.23.45','10.33.42.3','10.33.44.5','10.33.3.455','10.33.2.23','10.33.4.55','10.45.343.2'],
			        axisLine: {
				        	lineStyle: {
				        		color: ['#268cb6'],
				        		width: 3,
				        	},
				        },
			    },
			    series: [
			        {
			            name: '使用率',
			            type: 'bar',
			            stack: '总量',
			            label: {
			                normal: {
			                    show: true,
			                    position: 'insideRight'
			                }
			            },
			            data: [60, 32, 81, 54, 39, 30, 20, 34, 88, 99],
			            barCategoryGap: '35%',
			        },
			        
			    ]
			};
			myChartjq1.setOption(optionjq1);//集群-cpu使用率

			var myChartjq5 = echarts.init(document.getElementById('jq-disk-first'));
			var optionjq5 = {
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    title: {
				    	text: '磁盘io速率(%)',
				    	top: 'bottom',
				    	left: 'center',
				    	textStyle: {
							color: '#333',
							fontStyle: 'normal',
							fontWeight: '300',
							fontFamily: '微软雅黑',
							fontSize: 14,
							},
			    	},
			    itemStyle: {
				    	normal: {
				    		color: ['#e67436'],
				    	},
				},
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '10%',
			        top:'1%',
			        containLabel: true
			    },
			    xAxis:  {
			        type: 'value',
			        axisLine: {
				        	lineStyle: {
				        		color: ['#268cb6'],
				        		width: 3,
				        	},
				        },
			    },
			    yAxis: {
			        type: 'category',
			        data: ['10.33.23.455','10.33.42.23','10.33.44.55','10.33.23.45','10.33.42.3','10.33.44.5','10.33.3.455','10.33.2.23','10.33.4.55','10.45.343.2'],
			        axisLine: {
				        	lineStyle: {
				        		color: ['#268cb6'],
				        		width: 3,
				        	},
				        },
			    },
			    series: [
			        {
			            name: '速率',
			            type: 'bar',
			            stack: '总量',
			            label: {
			                normal: {
			                    show: true,
			                    position: 'insideRight'
			                }
			            },
			            data: [40, 75, 21, 74, 99, 50, 20, 44, 58, 29],
			            barCategoryGap: '35%',
			        },
			        
			    ]
			};
			myChartjq5.setOption(optionjq5);//集群-磁盘io速率


			var myChartbm = echarts.init(document.getElementById('jq-board-main'));
			var optionbm = {
			    tooltip: {
			        trigger: 'item',
			        formatter: "{a} <br/>{b}: {c} ({d}%)",
			        position: [10,10]
			    },
			    title: {
			    	text: '物理机：3',
			    	top: 'bottom',
			    	left: 'center',
			    	textStyle: {
						color: '#333',
						fontStyle: 'normal',
						fontWeight: '300',
						fontFamily: '微软雅黑',
						fontSize: 16,
						},
			    },
			    series: [
			        {
			            name:'物理机',
			            type:'pie',
			            radius: ['40%', '55%'],
			            avoidLabelOverlap: false,
			            hoverAnimation: false,
			            legendHoverLink: false,
			            color: ['#3facca', '#dcdbdb'],
			            label: {
			                normal: {
			                    show: true,
			                    textStyle: {
			                    	color: '#333333',
			                    	fontSize: 14
			                    }
			                    
			                },
			            },
			            labelLine: {
			                normal: {
			                    show: true
			                }
			            },
			            data:[
			                {value:2, name:'正常'},
			                {value:1, name:'异常'}
			            ]
			        }
			    ]
			};
			myChartbm.setOption(optionbm); //物理机圆圈

			var myChartbv = echarts.init(document.getElementById('jq-board-virtual'));
			var optionbv = {
			    tooltip: {
			        trigger: 'item',
			        formatter: "{a} <br/>{b}: {c} ({d}%)",
			        position: [10,10]
			    },
			    title: {
			    	text: '虚拟机：11',
			    	top: 'bottom',
			    	left: 'center',
			    	textStyle: {
						color: '#333',
						fontStyle: 'normal',
						fontWeight: '300',
						fontFamily: '微软雅黑',
						fontSize: 16,
						},
			    },
			    series: [
			        {
			            name:'虚拟机',
			            type:'pie',
			            radius: ['40%', '55%'],
			            avoidLabelOverlap: false,
			            hoverAnimation: false,
			            legendHoverLink: false,
			            color: ['#30c8b4', '#dcdbdb'],
			            label: {
			                normal: {
			                    show: true,
			                    textStyle: {
			                    	color: '#333333',
			                    	fontSize: 14
			                    }
			                    
			                },
			            },
			            labelLine: {
			                normal: {
			                    show: true
			                }
			            },
			            data:[
			                {value:8, name:'正常'},
			                {value:3, name:'异常'}
			            ]
			        }
			    ]
			};
			myChartbv.setOption(optionbv); //虚拟机圆圈

			var myChartbs = echarts.init(document.getElementById('jq-board-storage'));
			var optionbs = {
			    tooltip: {
			        trigger: 'item',
			        formatter: "{a} <br/>{b}: {c} ({d}%)",
			        position: [10,10]
			    },
			    title: {
			    	text: '存储：11',
			    	top: 'bottom',
			    	left: 'center',
			    	textStyle: {
						color: '#333',
						fontStyle: 'normal',
						fontWeight: '300',
						fontFamily: '微软雅黑',
						fontSize: 16,
						},
			    },
			    series: [
			        {
			            name:'存储',
			            type:'pie',
			            radius: ['40%', '55%'],
			            avoidLabelOverlap: false,
			            hoverAnimation: false,
			            legendHoverLink: false,
			            color: ['#94cb4c', '#dcdbdb'],
			            label: {
			                normal: {
			                    show: true,
			                    textStyle: {
			                    	color: '#333333',
			                    	fontSize: 14
			                    }
			                    
			                },
			            },
			            labelLine: {
			                normal: {
			                    show: true
			                }
			            },
			            data:[
			                {value:8, name:'正常'},
			                {value:3, name:'异常'}
			            ]
			        }
			    ]
			};
			myChartbs.setOption(optionbs); //存储圆圈

			var myChartvch = echarts.init(document.getElementById('virtual-cpu-history'));
			var optionvch = {

			    tooltip : {
			        trigger: 'axis'
			    },
			    calculable : true,
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '2%',
			        top:'3%',
			        containLabel: true
			    },
			    itemStyle: {
				    	normal: {
				    		color: ['#e67436'],
				    	},
				},
			    xAxis : [
			        {
			            type : 'category',
			            data : ['vcenter','CloudManager','新建虚拟机thin']
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
			            min: 0,
			            max: 30
			        }
			    ],
			    series : [
			        {
			            name:'使用率',
			            type:'bar',
			            data:[15, 25, 20],
			        },
			    ]
			};
			myChartvch.setOption(optionvch); //虚拟机cpu
		},
		sideboardShow: function(){
			$('.vm-mask').fadeIn(200);
    		$('.jq-sideboard').animate({
    			"right":0
    		},300);
	    },
	    sideboardClose : function(){
	    	$('.vm-mask').fadeOut(200);
    		$('.jq-sideboard').animate({
    			"right":-750
    		},300);
	    },
	    doSearch : function(value, name) {
			var queryParams = $('#monitorIndexTableId').datagrid('options').queryParams;
			queryParams.name = value;
			$("#monitorIndexTableId").datagrid('reload');
		},
		formatOper : function(val, row, index) {
			return '<a href="#" onclick="cloudmanager.monitor.updateProVDC(\''
					+ row.pVDCId + '\')">修改</a>'+
					'&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="cloudmanager.monitor.deleteProVDCConfirm(\''
					+ row.pVDCId + '\',\''+row.computingPoolId+'\')">删除</a>';
		},
		formatName : function(val, row, index) {
			return '<a href="#" onclick="cloudmanager.monitor.proVDCDetail(\''
					+ row.pVDCId + '\')">' + row.name + '</a>';
		}
}
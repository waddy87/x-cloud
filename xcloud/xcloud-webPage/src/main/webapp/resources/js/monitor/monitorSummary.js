cloudmanager.namespace("monitorSummary");
cloudmanager.monitorSummary = {
        
		 //绘制冠状图
        initGua : function(elementId,title,max,value,unit) {
			var myChart3 = echarts.init(document.getElementById(elementId));
			var option3 = {
			    tooltip : {
			        formatter: "{a} <br/>{b} : {c}%"
			    },
			    title: {
			    	text: title, //设置title
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
			//设置最大值、value
			option3.series[0].data[0].value = value;
			option3.series[0].detail.formatter=unit;
			if (max>0) {
				option3.series[0].max = parseInt(max);
			} else {
				option3.series[0].max = 100;
			}
			myChart3.setOption(option3);

        },
		
		
        //绘制水平直方图
        initHorizonBar : function(elementId,title,ydata,xdata) {
		    var myChart = echarts.init(document.getElementById(elementId));
            var option = {
                    tooltip : {
                        trigger: 'axis',
                        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    title: {
                        text: title,
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
                        width: '90%',
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
                        data: ydata,
                        axisLine: {
                            lineStyle: {
                                color: ['#268cb6'],
                                width: 3,
                            },
                        },
                        axisLabel : {
                            interval : 0,
                            rotate : 0,
                            formatter : function (value){
                                if (value.length < 13)
                                    return value;
                                else                                
                                    return value.substr(0,12) + "...";
                            }
                        },
                    },
                    series: [
                        {
                            name: title,
                            type: 'bar',
                            stack: '总量',
                            label: {
                                normal: {
                                    show: true,
                                    position: 'insideRight'
                                }
                            },
                            data: xdata,
                        },
                        
                    ]
                };
            myChart.setOption(option);
        },
        
        //绘制竖直直方图
        initVerticalBar : function(elementId,title,ydata,xdata) {
            var myChart = echarts.init(document.getElementById(elementId));
            var option = {
                    tooltip : {
                        trigger: 'axis',
                        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    title: {
                        text: title,
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
                        bottom: '10%',
                        width: '90%',
                        containLabel: true
                    },
                    yAxis:  {
                        type: 'value',
                        axisLine: {
                            lineStyle: {
                                color: ['#268cb6'],
                                width: 3,
                            },
                        },
                    },
                    xAxis: {
                        type: 'category',
                        data: ydata,
                        axisLine: {
                            lineStyle: {
                                color: ['#268cb6'],
                                width: 3,
                            },
                        },
                    },
                    series: [
                        {
                            name: title,
                            type: 'bar',
                            stack: '总量',
                            label: {
                                normal: {
                                    show: true,
                                    position: 'insideRight'
                                }
                            },
                            data: xdata,
                        },
                        
                    ]
                };
            myChart.setOption(option);
        },
        
        //绘制正常、异常圆
        initCircle : function(elementId,title,normalNum,unNormalNum){
            var circleChart = echarts.init(document.getElementById(elementId));
            var option = {
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)",
                    position: [10,10]
                },legend: {
                    //orient: 'vertical',
                    x: 'center',
                    data:['正常','异常']
                },
                series: [
                    {
                        name:title,
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
                                formatter: title+':'+(parseInt(normalNum)+parseInt(unNormalNum)),
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
                            {value:normalNum, name:'正常'},
                            {value:unNormalNum, name:'异常'}
                        ]
                    }
                ]
            };
            circleChart.setOption(option); 
        },
        
      //绘制历史数据折线图
        initLine : function(elementId,title,ydata,xdata){
            perfCPU24 = echarts.init(document.getElementById(elementId));
            var option = {
                tooltip : {
                    trigger: 'axis'
                },

                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                xAxis : [
                    {
                        type : 'category',
                        boundaryGap : false,
                        data : ydata
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:title,
                        type:'line',
                        stack: '总量',
                        data:xdata
                    }
                ]
            };
            perfCPU24.setOption(option);
        },
        initAlarmTable:function(elementId,data){
            $("#"+elementId).datagrid({
                rownumbers:true,  
                fitColumns:true,  
                pagination:false,  
                data:data,  
                columns:[[  
                        {
                            field:'name', 
                            align:"center", 
                            title:"标题", 
                            width:"16%"
                        },
                        {
                            field:'description', 
                            align:"center", 
                            title:"描述", 
                            width:"18%"
                        },
                        {
                            field:'level', 
                            align:"center", 
                            title:"级别", 
                            width:"8%",
                            formatter : function(val, row, index){
                                var objStatus = "<span class=\"zt-gray\" >未知";
                                if (row.level == "ok"){
                                    objStatus = "<span class=\"zt-green\">正常";
                                }else if (row.level == "warning"){
                                    objStatus = "<span class=\"zt-yellow\">警告";
                                }else if (row.level == "critical"){
                                	
                                	objStatus = "<span class=\"zt-red\">严重";
                                   
                                }else{
                                    objStatus = "<span class=\"zt-gray\">未知";
                                }
                                return objStatus;
                            }
                        },{
                            field:'entityName', 
                            align:"center", 
                            title:"所属对象", 
                            width:"10%"
                        },
                        {
                            field:'resType', 
                            align:"center", 
                            title:"资源类型", 
                            width:"10%",
                            formatter : function(val, row, index){
                            	var objStatus="其它";
                            	console.debug(row.entityId);
                                if (row.entityId.indexOf('vm') == 0){
                                    objStatus = "虚拟机";
                                }else if (row.entityId.indexOf("host") ==0){
                                    objStatus = "云主机";
                                }else if (row.entityId.indexOf("datastore") ==0){
                                	objStatus = "存储";
                                }else if(row.entityId.indexOf("domain") ==0){
                                    objStatus = "集群";
                                }
                                return objStatus;
                            }
                        },
                        {
                            field:'time', 
                            align:"center", 
                            title:"触发时间", 
                            width:"12%"
                        },
                        {
                            field:'acknowledged', 
                            align:"center", 
                            title:"是否确认", 
                            width:"8%",
                            formatter : function(val, row, index){
                                if (row.acknowledged == true){
                                    objStatus = "已确认";
                                }else{
                                    objStatus = "未确认";
                                }
                                return objStatus;
                            }
                        }/*,
                        {
                            field:'acknowledgedTime', 
                            align:"center", 
                            title:"确认时间", 
                            width:"12%"
                        }*/
                    ]]  
            });  
  
            
          /*  var p = $("#"+elementId).datagrid('getPager');
            $(p).pagination({
                layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
                displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
                beforePageText:'跳转到第 ',
                afterPageText:' 页',
                total:data.length,  
                onSelectPage:function (pageNo, pageSize) {  
                    var start = (pageNo - 1) * pageSize;  
                    var end = start + pageSize;  
                    $("#"+elementId).datagrid("loadData", data.slice(start, end));  
                    pager.pagination('refresh', {  
                        total:data.length,  
                        pageNumber:pageNo  
                    });  
                }  
            });*/
          
        }
}
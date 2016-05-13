require.config({
	paths: {
		echarts: 'js/echarts'
	}
});
require(
	[
		'echarts',
		'echarts/chart/pie',
		'echarts/chart/line'
	],
	function (ec) {
		//饼图
		var memberGradeData = ec.init(document.getElementById('memberGradeData'));
		var allMemberData = ec.init(document.getElementById('allMemberData'));
		var memberGradeLine = ec.init(document.getElementById('memberGradeLine'));
		var labelTop = {
			    normal : {
			        label : {
			            show : false,
			            position : 'center',
			            formatter : '{b}',
			            textStyle: {
			                baseline : 'center',
			                color: '#000',
			                fontSize:12
			            }
			        },
			        labelLine : {
			            show : false
			        }
			    }
			};
			var labelFromatter = {
			    normal : {
			        label : {
			            formatter : function (params){
			                return params.series.data[1].value+'%';
			            },
			            textStyle: {
			                baseline : 'middle',
			                color: '#666',
							fontSize:20
			            }
			        }
			    }
			}
			var labelBottom = {
			    normal : {
			        color: '#ccc',
			        label : {
			            show : true,
			            position : 'center'
			        },
			        labelLine : {
			            show : false
			        }
			    },
			    emphasis: {
			        color: 'rgba(0,0,0,0)'
			    }
			};
			var radius = [40, 50];
			memberGradeDataOption = {
				color:['#fdaa29','#9cbc72','#56a8e7','#fa6353'],
			    title : {
			    	show:false
			    },
			    toolbox: {
			        show : false
			    },
				tooltip: {
			        show : true
			    },
			     legend: {
					x : 'center',
					y : 'bottom',
					data:['金牌会员','铂金会员','钻石会员','皇冠会员']
				},
				series : [
					{
						type : 'pie',
						center : ['18%', '50%'],
						radius : radius,
						x: '0%', // for funnel
						itemStyle : labelFromatter,
						data : [
							{name:'未完成', value:46, itemStyle : labelBottom},
							{name:'金牌会员', value:54,itemStyle : labelTop}
						]
					},
					{
						type : 'pie',
						center : ['39.5%', '50%'],
						radius : radius,
						x:'20%', // for funnel
						itemStyle : labelFromatter,
						data : [
							{name:'未完成', value:56, itemStyle : labelBottom},
							{name:'铂金会员', value:44,itemStyle : labelTop}
						]
					},
					{
						type : 'pie',
						center : ['61%', '50%'],
						radius : radius,
						x:'40%', // for funnel
						itemStyle : labelFromatter,
						data : [
							{name:'未完成', value:65, itemStyle : labelBottom},
							{name:'钻石会员', value:35,itemStyle : labelTop}
						]
					},
					{
						type : 'pie',
						center : ['82%', '50%'],
						radius : radius,
						x:'60%', // for funnel
						itemStyle : labelFromatter,
						data : [
							{name:'未完成', value:70, itemStyle : labelBottom},
							{name:'皇冠会员', value:30,itemStyle : labelTop}
						]
					}
				]
			};
			memberGradeData.setOption(memberGradeDataOption);
			allMemberDataOption = {
				color:['#fdaa29','#9cbc72','#56a8e7','#fa6353','#adacac'],
				tooltip : {
					trigger: 'item',
					formatter: "{a} <br/>{b} : {c} ({d}%)"
				},
				legend: {
					show : false,
					x : 'center',
					y : 'top',
					data:['金牌会员','铂金会员','钻石会员','皇冠会员','所有会员']
				},
				toolbox: {
					show : false
				},
				series : [
					{
						name:'完成进度',
						type:'pie',
						radius : ['50%', '70%'],
						itemStyle : {
							normal : {
								label : {
									formatter : function (params){
										return params.series.data[1].value+'%';
									},
									textStyle: {
										baseline : 'middle',
										color: '#666',
										fontSize:20
									}
								}
							},
							emphasis : {
								label : {
									show : true,
									position : 'center',
									textStyle : {
										fontSize : '30',
										fontWeight : 'bold'
									}
								}
							}
						},
						data:[
							{value:335, name:'金牌会员'},
							{value:310, name:'铂金会员'},
							{value:234, name:'钻石会员'},
							{value:135, name:'皇冠会员'},
							{value:1548, name:'所有会员'}
						]
					}
				]
			};
			allMemberData.setOption(allMemberDataOption);
			memberGradeLineOption = {
				color:['#fdaa29','#9cbc72','#56a8e7','#fa6353'],
				title : {
					show: false,
					text: '',
					subtext: ''
				},
				tooltip : {
					trigger: 'axis'
				},
				legend: {
					data:['最高气温','最低气温']
				},
				toolbox: {
					show : false
				},
				calculable : true,
				xAxis : [
					{
						type : 'category',
						boundaryGap : false,
						data : ['周一','周二','周三','周四','周五','周六','周日']
					}
				],
				yAxis : [
					{
						type : 'value',
						axisLabel : {
							formatter: '{value} °C'
						}
					}
				],
				series : [
					{
						name:'最高气温',
						type:'line',
						data:[11, 11, 15, 13, 12, 13, 10],
						markPoint : {
							data : [
								{type : 'max', name: '最大值'},
								{type : 'min', name: '最小值'}
							]
						},
						markLine : {
							data : [
								{type : 'average', name: '平均值'}
							]
						}
					},
					{
						name:'最低气温',
						type:'line',
						data:[1, -2, 2, 5, 3, 2, 0],
						markPoint : {
							data : [
								{name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
							]
						},
						markLine : {
							data : [
								{type : 'average', name : '平均值'}
							]
						}
					}
				]
			};
			memberGradeLine.setOption(memberGradeLineOption);
			$('.admin-work h2').click(function(){
				memberGradeData.resize();
				allMemberData.resize();
				memberGradeLine.resize();	
			});
	}
);
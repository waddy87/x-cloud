require.config({
	paths: {
		echarts: 'js/echarts'
	}
});
require(
	[
		'echarts',
		'echarts/chart/pie'
	],
	function (ec) {
		//饼图
		var projectChartOne = ec.init(document.getElementById('projectChart01'));
		var projectChartTwo = ec.init(document.getElementById('projectChart02'));
		var projectChartThree = ec.init(document.getElementById('projectChart03'));
		var projectChartFour = ec.init(document.getElementById('projectChart04'));
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
			                color: '#333'
			            }
			        }
			    },
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
			var radius = [20, 24];
			projectChartOneOption = {
				color:['#fdaa29'],
				legend: {
					show:false,
			        x : 'center',
			        y : 'top',
			        data:['已进行','未进行']
			    },
			    title : {
			    	show:false
			    },
			    toolbox: {
			        show : false
			    },
			    series : [
			        {
			            type : 'pie',
			            center : ['50%', '50%'],
			            radius : radius,
			            x: '0', // for funnel
			            itemStyle : labelFromatter,
			            data : [
			                {name:'未进行', value:'42',itemStyle : labelBottom},
			                {name:'已进行', value:'58',itemStyle : labelTop}
			            ]
			        }
			    ]
			};
			projectChartOne.setOption(projectChartOneOption);
			projectChartTwoOption = {
				color:['#fdaa29'],
				legend: {
					show:false,
			        x : 'center',
			        y : 'top',
			        data:['已进行','未进行']
			    },
			    title : {
			    	show:false
			    },
			    toolbox: {
			        show : false
			    },
			    series : [
			        {
			            type : 'pie',
			            center : ['50%', '50%'],
			            radius : radius,
			            x: '0', // for funnel
			            itemStyle : labelFromatter,
			            data : [
			                {name:'未进行', value:'20',itemStyle : labelBottom},
			                {name:'已进行', value:'80',itemStyle : labelTop}
			            ]
			        }
			    ]
			};
			projectChartTwo.setOption(projectChartTwoOption);
			projectChartThreeOption = {
				color:['#e74c3c'],
				legend: {
					show:false,
			        x : 'center',
			        y : 'top',
			        data:['已进行','未进行']
			    },
			    title : {
			    	show:false
			    },
			    toolbox: {
			        show : false
			    },
			    series : [
			        {
			            type : 'pie',
			            center : ['50%', '50%'],
			            radius : radius,
			            x: '0', // for funnel
			            itemStyle : labelFromatter,
			            data : [
			                {name:'未进行', value:'0',itemStyle : labelBottom},
			                {name:'已进行', value:'100',itemStyle : labelTop}
			            ]
			        }
			    ]
			};
			projectChartThree.setOption(projectChartThreeOption);
			projectChartFourOption = {
				color:['#fdaa29'],
				legend: {
					show:false,
			        x : 'center',
			        y : 'top',
			        data:['已进行','未进行']
			    },
			    title : {
			    	show:false
			    },
			    toolbox: {
			        show : false
			    },
			    series : [
			        {
			            type : 'pie',
			            center : ['50%', '50%'],
			            radius : radius,
			            x: '0', // for funnel
			            itemStyle : labelFromatter,
			            data : [
			                {name:'未进行', value:'42',itemStyle : labelBottom},
			                {name:'已进行', value:'58',itemStyle : labelTop}
			            ]
			        }
			    ]
			};
			projectChartFour.setOption(projectChartFourOption);
			
	}
);
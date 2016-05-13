<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div role="tabpanel" class="tab-pane active" id="summary">
	<div class="row">
		<div class="col-lg-4 col-md-4 col-sm-12">
			<ul class="list-group">
				<li class="list-group-item list-group-item-heading">vCPU信息(核)</li>
				<li class="list-group-item">
					<dl>
						<dt>vCPU总数：</dt>
						<dd>${proVDCInfo.vCpuNum }</dd>
					</dl>
				</li>
				<li class="list-group-item">
					<dl>
						<dt>vCPU剩余：</dt>
						<dd>${proVDCInfo.vCpuOverplus }</dd>
					</dl>
				</li>
				<li class="list-group-item list-group-item-heading">内存信息(GB)</li>
				<li class="list-group-item">
					<dl>
						<dt>内存总量：</dt>
						<dd><fmt:formatNumber type="number" value="${proVDCInfo.memorySize/1024}" maxFractionDigits="1"/></dd>
					</dl>
				</li>
				<li class="list-group-item">
					<dl>
						<dt>内存剩余：</dt>
						<dd><fmt:formatNumber type="number" value="${proVDCInfo.memoryOverplus/1024}" maxFractionDigits="1"/></dd>
					</dl>
				</li>
				<li class="list-group-item list-group-item-heading">存储信息(GB)</li>
				<c:forEach items="${proVDCSpList}" var="proVDCSpInfo"
					varStatus="status">
					<li class="list-group-item">
						<dl>
							<dt>存储池名称：</dt>
							<dd>${proVDCSpInfo.spName }</dd>
						</dl>
					</li>
					<li class="list-group-item">
						<dl>
							<dt>存储池总量：</dt>
							<dd>${proVDCSpInfo.spTotal}</dd>
						</dl>
					</li>
					<li class="list-group-item">
						<dl>
							<dt>存储池剩余：</dt>
							<dd>${proVDCSpInfo.spSurplus}</dd>
						</dl>
					</li>
					<li class="list-group-item">
						<dl>
							<dt>存储池已用：</dt>
							<dd>${proVDCSpInfo.spUsed}</dd>
						</dl>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="col-lg-8 col-md-8 col-sm-12">
			<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-12">
					<div class="panel panel-default">
  						<div class="panel-body">
							<div id="proVDCcpuChart" style="height:200px;"></div>
  						</div>
  					</div>
				</div>
				<div class="col-lg-6 col-md-6 col-sm-12">
					<div class="panel panel-default">
  						<div class="panel-body">
							<div id="proVDCmemoryChart" style="height:200px;"></div>
						</div>
					</div>
				</div>
				<div class="col-lg-12 col-md-12 col-sm-12">
					<div class="panel panel-default">
  						<div class="panel-body">
							<div id="proVDCdiskChart" style="height:275px;"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		var proVDCcpuChart = echarts.init(document.getElementById('proVDCcpuChart'));
		var proVDCcpuoption = {
			title: {
                text: 'vCPU使用率',
                top: 'top',
                left: 'left',
                textStyle: {
                    color: '#333',
                    fontStyle: 'normal',
                    fontWeight: '300',
                    fontFamily: '微软雅黑',
                    fontSize: 14,
                }
            },
            legend: {
                //orient: 'vertical',
                x: 'right',
                data:['已用','剩余']
            },
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)",
		        position: [10,10]
		    },
		    series: [
		        {
		            name:'vCPU使用率',
		            type:'pie',
		            radius: ['50%', '70%'],
		            center: ['50%', '60%'],
		            avoidLabelOverlap: false,
		            hoverAnimation: false,
		            legendHoverLink: false,
		            color: ['#3facca', '#dcdbdb'],
		            label: {
		                normal: {
		                    show: true,
		                    position: 'center',
		                    formatter: 'vCPU总数:'+'${proVDCInfo.vCpuNum}',
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
		                {value:'${proVDCInfo.vCpuUsed}', name:'已用'},
		                {value:'${proVDCInfo.vCpuOverplus}', name:'剩余'}
		            ]
		        }
		    ]
		};
		proVDCcpuChart.setOption(proVDCcpuoption);
		
		var proVDCmemoryChart = echarts.init(document.getElementById('proVDCmemoryChart'));
		var memorySize=Math.round('${proVDCInfo.memorySize/1024}');
		var memoryOverplus=Math.round('${proVDCInfo.memoryOverplus/1024}');
		var memoryUsed=Math.round('${proVDCInfo.memoryUsed/1024}');
		var proVDCmemoryoption = {
			title: {
                text: '内存使用率',
                top: 'top',
                left: 'left',
                textStyle: {
                    color: '#333',
                    fontStyle: 'normal',
                    fontWeight: '300',
                    fontFamily: '微软雅黑',
                    fontSize: 14,
                }
            },
            legend: {
                //orient: 'vertical',
                x: 'right',
                data:['已用','剩余']
            },
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)",
		        position: [10,10]
		    },
		    series: [
		        {
		            name:'内存使用率',
		            type:'pie',
		            radius: ['50%', '70%'],
		            center: ['50%', '60%'],
		            avoidLabelOverlap: false,
		            hoverAnimation: false,
		            legendHoverLink: false,
		            color: ['#3facca', '#dcdbdb'],
		            label: {
		                normal: {
		                    show: true,
		                    position: 'center',
		                    formatter: '内存总量:'+memorySize,
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
		                {value:memoryUsed, name:'已用'},
		                {value:memoryOverplus, name:'剩余'}
		            ]
		        }
		    ]
		};
		proVDCmemoryChart.setOption(proVDCmemoryoption);
		
		var proVDCdiskChart = echarts.init(document.getElementById('proVDCdiskChart'));
		var proVDCSpList=$.parseJSON('${proVDCSpList}');
		var spName=[];
		var spUsedValue=[];
		var spSurplusValue=[];
		for(var o in proVDCSpList){
			spName.push(proVDCSpList[o].spName);
			spUsedValue.push(Number(proVDCSpList[o].spUsed));
			spSurplusValue.push(Number(proVDCSpList[o].spSurplus));
	    }  
		var proVDCdiskoption = {
			title: {
                text: '磁盘概览',
                top: 'top',
                left: 'left',
                textStyle: {
                    color: '#333',
                    fontStyle: 'normal',
                    fontWeight: '300',
                    fontFamily: '微软雅黑',
                    fontSize: 14,
                }
            },
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            color: ['#3facca', '#dcdbdb'],
            legend: {
            	x:'right',
                data: ['已使用','剩余']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            yAxis:  {
                type: 'value'
            },
            xAxis: {
                type: 'category',
                data: spName
            },
            series: [
                {
                    name: '已使用',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        normal: {
                            show: true,
                            position: 'insideRight'
                        }
                    },
                    data: spUsedValue
                },
                {
                    name: '剩余',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        normal: {
                            show: true,
                            position: 'insideRight'
                        }
                    },
                    data: spSurplusValue
                }
            ]
		};
		proVDCdiskChart.setOption(proVDCdiskoption);
	});
</script>
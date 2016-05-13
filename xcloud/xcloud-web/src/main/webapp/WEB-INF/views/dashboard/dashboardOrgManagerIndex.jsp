<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>

<div class="container-fluid dashboard">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="alert alert-success alert-dismissible text-center" role="alert">
			  	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			  	<strong>系统正常！</strong>当前系统状态无异常。
			</div>
		</div>
		<div class="col-lg-6 col-md-6 col-sm-12">
			<div class="vm-box">
				<dl class="user clearfix">
					<dd class="user-photo">
						<img src="${ctx}/resources/image/user.png" width="64" height="64" />
					</dd>
					<dt>
						<h2>Hi! ${sessionScope.currentUser.username }</h2>
						<p>${sessionScope.currentUser.email }</p>
					</dt>
					<dd class="user-controller">
						<p>
							<span>未读消息：<a href="">0</a></span>
							<span>待办任务：<a href="">0</a></span>
							<span>工单消息：<a href="">0</a></span>
						</p>
					</dd>
				</dl>
			</div>
			<div class="vm-box">
				<div id="orgManagerResourceCharts" style="height:200px"></div>
			</div>
			<div class="vm-box">
				<div class="media">
					<div class="media-left media-middle">
						<i class="fa fa-qrcode"></i>
				  	</div>
				  	<div class="media-body">
					    <h4 class="media-heading">CPU<small>可用:<i id="cpuFreeGHz"></i></small></h4>
					    <div class="progress">
							<div class="progress-bar progress-bar1" role="progressbar" aria-valuenow="" aria-valuemin="0" aria-valuemax="100" style="width: 50%; background-color: #3facca">50%</div>
						</div>
						<h5 class="media-footing"><small>已用:</small><small>总量:</small></h5>
					</div>
				</div>
				<div class="media">
	                <div class="media-left media-middle">
						<i class="fa fa-ticket"></i>
				  	</div>
				  	<div class="media-body">
					    <h4 class="media-heading">内存<small>可用:</small></h4>
					    <div class="progress">
							<div class="progress-bar progress-bar2" role="progressbar" aria-valuenow="" aria-valuemin="0" aria-valuemax="100" style="width: 20%; background-color:#9d50a5;">20%</div>
						</div>
						<h5 class="media-footing"><small>已用:</small><small>总量:</small></h5>
					</div>
	            </div>
	            <div class="media">
	             	<div class="media-left media-middle">
						<i class="fa fa-database"></i>
				  	</div>
			             	<div class="media-body">
			                  <h4 class="media-heading">存储<small>可用:</small></h4>
			                  <div class="progress">
						    <div class="progress-bar progress-bar3" role="progressbar" aria-valuenow="" aria-valuemin="0" aria-valuemax="100" style="width: 30%; background-color: #5cb85c">30%</div>
						</div>
						<h5 class="media-footing"><small>已用:</small><small>总量:</small></h5>
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-6 col-md-6 col-sm-12">
			<div class="vm-box">
				<div class="warning-status">
					<div class="status">
						<dl>
							<dt>告警</dt>
							<dd><a class="text-danger" href="#">18</a></dd>
						</dl>
					</div>
					<div class="status">
						<p>严重：<a href="#"><span class="label label-danger">2</span></a></p>
						<p>警告：<a href="#"><span class="label label-warning">4</span></a></p>
						<p>未知：<a href="#"><span class="label label-default">12</span></a></p>
					</div>
					<div class="status">
						<dl>
							<dt>用户统计</dt>
							<dd>
								<p>在线用户：<a href="#"><span class="label label-success">1</span></a></p>
								<p>用户总数：<a href="#"><span class="label label-default">3</span></a></p>
							</dd>
						</dl>
					</div>
				</div>
			</div>
			<div class="vm-tabs dashboard-panel">
			  <!-- Nav tabs -->
			  <ul class="nav nav-tabs" role="tablist">
			    <li role="presentation" class="active"><a href="#project" aria-controls="project" role="tab" data-toggle="tab">项目<span class="badge">3</span></a></li>
			    <li role="presentation"><a href="#VDC" aria-controls="VDC" role="tab" data-toggle="tab">VDC<span class="badge">12</span></a></li>
			  </ul>
			
			  <!-- Tab panes -->
			  <div class="tab-content">
			    <div role="tabpanel" class="tab-pane active" id="project">
					<dl class="dashboard-project clearfix">
						<dt>项目一</dt>
						<dd>
							<h5>虚拟机</h5>
							<p>20</p>
						</dd>
						<dd>
							<h5>用户</h5>
							<p>10</p>
						</dd>
					</dl>
					<dl class="dashboard-project clearfix">
						<dt>项目一</dt>
						<dd>
							<h5>虚拟机</h5>
							<p>20</p>
						</dd>
						<dd>
							<h5>用户</h5>
							<p>10</p>
						</dd>
					</dl>
					<dl class="dashboard-project clearfix">
						<dt>项目一</dt>
						<dd>
							<h5>虚拟机</h5>
							<p>20</p>
						</dd>
						<dd>
							<h5>用户</h5>
							<p>10</p>
						</dd>
					</dl>
				</div>
			    <div role="tabpanel" class="tab-pane" id="VDC">
					<dl class="dashboard-project clearfix">
						<dt>VDC名称01</dt>
						<dd>
							<h5>虚拟机</h5>
							<p>20</p>
						</dd>
					</dl>
					<dl class="dashboard-project clearfix">
						<dt>VDC名称02</dt>
						<dd>
							<h5>虚拟机</h5>
							<p>20</p>
						</dd>
					</dl>
					<dl class="dashboard-project clearfix">
						<dt>VDC名称03</dt>
						<dd>
							<h5>虚拟机</h5>
							<p>20</p>
						</dd>
					</dl>
					<dl class="dashboard-project clearfix">
						<dt>VDC名称04</dt>
						<dd>
							<h5>虚拟机</h5>
							<p>20</p>
						</dd>
					</dl><dl class="dashboard-project clearfix">
						<dt>VDC名称05</dt>
						<dd>
							<h5>虚拟机</h5>
							<p>20</p>
						</dd>
					</dl>
					<dl class="dashboard-project clearfix">
						<dt>VDC名称06</dt>
						<dd>
							<h5>虚拟机</h5>
							<p>20</p>
						</dd>
					</dl>
					<dl class="dashboard-project clearfix">
						<dt>VDC名称07</dt>
						<dd>
							<h5>虚拟机</h5>
							<p>20</p>
						</dd>
					</dl>
					<dl class="dashboard-project clearfix">
						<dt>VDC名称07</dt>
						<dd>
							<h5>虚拟机</h5>
							<p>20</p>
						</dd>
					</dl>
				</div>
			  </div>
			</div>
		</div>
	</div>
</div>
<script>
$(function(){
	var orgManagerResourceCharts = echarts.init(document.getElementById('orgManagerResourceCharts'));
	var labelTop = {
	    normal : {
	        label : {
	            show : true,
	            position : 'center',
	            formatter : '{b}',
	            textStyle: {
	                baseline : 'bottom'
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
	                return 100 - params.value + '%'
	            },
	            textStyle: {
	                baseline : 'top'
	            }
	        }
	    },
	}
	var labelBottom = {
	    normal : {
	        color: '#DDD',
	        label : {
	            show : true,
	            position : 'center',
            	textStyle: {
            		color: '#999',
	                fontSize:18
	            }
	        },
	        labelLine : {
	            show : false
	        }
	    },
	    emphasis: {
	        color: 'rgba(0,0,0,0.1)'
	    }
	};
	var radius = [40, 50];
	orgManagerResourceChartsoption = {
	    legend: {
	        x : 'center',
	        y : 'bottom',
	        data:['虚拟机','物理机','利旧虚拟机', '网络']
	    },
	    title : {
	        text: '资源概况',
	        x: '10px',
	        y: '10px'
	    },
	    toolbox: {
	        show : false
	    },
	    series : [
	        {
	            type : 'pie',
	            center : ['15%', '50%'],
	            radius : radius,
	            x: '0%', // for funnel
	            itemStyle : labelFromatter,
	            data : [
	                {name:'other', value:46, itemStyle : labelBottom},
	                {name:'虚拟机', value:54,itemStyle : labelTop}
	            ]
	        },
	        {
	            type : 'pie',
	            center : ['38%', '50%'],
	            radius : radius,
	            x:'20%', // for funnel
	            itemStyle : labelFromatter,
	            data : [
	                {name:'other', value:56, itemStyle : labelBottom},
	                {name:'物理机', value:44,itemStyle : labelTop}
	            ]
	        },
	        {
	            type : 'pie',
	            center : ['62%', '50%'],
	            radius : radius,
	            y: '55%',   // for funnel
	            x: '0%',    // for funnel
	            itemStyle : labelFromatter,
	            data : [
	                {name:'other', value:78, itemStyle : labelBottom},
	                {name:'利旧虚拟机', value:22,itemStyle : labelTop}
	            ]
	        },
	        {
	            type : 'pie',
	            center : ['85%', '50%'],
	            radius : radius,
	            y: '55%',   // for funnel
	            x:'20%',    // for funnel
	            itemStyle : labelFromatter,
	            data : [
	                {name:'other', value:78, itemStyle : labelBottom},
	                {name:'网络', value:22,itemStyle : labelTop}
	            ]
	        }
	    ]
	};
	orgManagerResourceCharts.setOption(orgManagerResourceChartsoption);
});
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ include file="/WEB-INF/views/includeFile.jsp"%>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header">
				<h1>集群</h1>
				<div class="small">虚拟化环境是对计算、存储和网络等资源进行虚拟化的软件，例如FusionCompute。 系统中所使用和管理的虚拟资源都来源于虚拟化环境中的资源。
				</div>
			</div>
		</div>
	</div>
	
	<div class="row jiqun">
		<div class="jq-table">
			<div class="col-lg-6 col-md-6 col-sm-12">
			<div class="jq-table-content">
				  <!-- Nav tabs -->
				  <ul class="nav nav-tabs" role="tablist">
				  	<li>集群</li>
				    <li role="presentation" class="active"><a href="#jq1" aria-controls="home" role="tab" data-toggle="tab">CPU使用率</a></li>
				    <li role="presentation"><a href="#jq2" aria-controls="profile" role="tab" data-toggle="tab">CPU使用量</a></li>
				    <li role="presentation"><a href="#jq3" aria-controls="messages" role="tab" data-toggle="tab">内存使用率</a></li>
				    <li role="presentation"><a href="#jq4" aria-controls="settings" role="tab" data-toggle="tab">内存使用量</a></li>
				  </ul>
				
				  <!-- Tab panes -->
				  <div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="jq1">
						<div id="jq-cpu-first" style="width: 380px; height: 350px; padding-top: 20px; float: left;"></div>
					</div>
				    
				    <div role="tabpanel" class="tab-pane" id="jq2">
				    </div>
				    
				    <div role="tabpanel" class="tab-pane" id="jq3">
				    </div>
				    
				    <div role="tabpanel" class="tab-pane" id="jq4">
				    </div>
				  </div>
					
				</div>
		</div>
		
		<div class="col-lg-6 col-md-6 col-sm-12">
			<div class="jq-table-content">
				 <!-- Nav tabs -->
				  <ul class="nav nav-tabs" role="tablist">
				  	<li>集群</li>
				    <li role="presentation" class="active"><a href="#jq5" aria-controls="home" role="tab" data-toggle="tab">磁盘io速率</a></li>
				    <li role="presentation"><a href="#jq6" aria-controls="profile" role="tab" data-toggle="tab">网络io速率</a></li>
				    <li role="presentation"><a href="#jq7" aria-controls="messages" role="tab" data-toggle="tab">物理机数量</a></li>
				    <li role="presentation"><a href="#jq8" aria-controls="settings" role="tab" data-toggle="tab">虚拟机数量</a></li>
				  </ul>
				
				  <!-- Tab panes -->
				  <div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="jq5">
						<div id="jq-disk-first" style="width: 380px; height: 350px; padding-top: 20px; float: left;"></div>
					</div>
				    
				    <div role="tabpanel" class="tab-pane" id="jq6">
				    </div>
				    
				    <div role="tabpanel" class="tab-pane" id="jq7">
				    </div>
				    
				    <div role="tabpanel" class="tab-pane" id="jq8">
				    </div>
				  </div>
					
				</div>
			</div>
		</div>
	</div>
		
	<div class="row overview">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="gk-bottom">
				<div class="recent-gj">
					<h2>集群列表</h2>
					<a href="javascript:;" class="btn btn-link" onclick="cloudmanager.monitor.sideboardShow()">详情</a>
				</div>
			</div>
		</div>
	</div>
	
	
</div>
<div class="jq-sideboard">
	<a class="sideboard-close" href="javascript:;" onclick="cloudmanager.monitor.sideboardClose()"><i class="fa fa-remove"></i></a>
	<div class="jq-board">
		<h2>
			集群-cluster
		</h2>
		<div class="row jq-board-content">
			<div class="col-lg-12 col-md-12 col-sm-12 jqc1">
				<h3>
					基本信息
				</h3>
				<ul>
					<li>集群名称: cluster</li>
					<li>所属数据中心: 数据中心</li>
				</ul>
			</div>
		</div>
		
		<div class="row jq-board-content">
			<div class="col-lg-4 col-md-4 col-sm-12 jqc2">
				<div id="jq-board-main" style="width:210px; height:200px; margin-top: -30px;"></div>	
			</div>
			<div class="col-lg-4 col-md-4 col-sm-12 jqc2">
				<div id="jq-board-virtual" style="width:210px; height:200px; margin-top: -30px;"></div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-12 jqc2">
				<div id="jq-board-storage" style="width:210px; height:200px; margin-top: -30px; margin-left: -10px;"></div>
			</div>
		</div>
		
		<div class="row jq-board-content">
			<div class="jqc3">
				<div class="col-lg-12 col-md-12 col-sm-12">
					<div class="media" id="jq-board-cpu">
						<div class="media-left media-middle">
							<i class="fa fa-qrcode"></i>
						</div>
						<div class="media-body">
							<h4 class="media-heading">
								CPU使用率:<i id="cpuUsage"></i><small>可用:<i id="cpuFreeGHz"></i></small>
							</h4>
							<div class="progress">
								<div style="width: 0px; background-color: rgb(63, 172, 202);" aria-valuemax="100" aria-valuemin="0" aria-valuenow="" role="progressbar" class="progress-bar progress-bar1" id="cpuUsagePie"></div>
							</div>
							<h5 class="media-footing">
								<small>已用:<i id="cpuUsedGHz"></i></small><small>总量:<i id="cpuGHz"></i></small>
							</h5>
						</div>
					</div>
				</div>
				<div class="col-lg-12 col-md-12 col-sm-12">
					<div class="media" id="jq-board-nc">
						<div class="media-left media-middle">
							<i class="fa fa-ticket"></i>
						</div>
						<div class="media-body">
							<h4 class="media-heading">
								内存使用率:<i id="memUsage"></i><small>可用:<i id="memFree"></i></small>
							</h4>
							<div class="progress">
								<div style="width: 0px; background-color: rgb(157, 80, 165);" aria-valuemax="100" aria-valuemin="0" aria-valuenow="" role="progressbar" class="progress-bar progress-bar2" id="memUsagePie"></div>
							</div>
							<h5 class="media-footing">
								<small>已用:<i id="memUsed"></i></small><small>总量:<i id="mem"></i></small>
							</h5>
						</div>
					</div>
				</div>
				<div class="col-lg-12 col-md-12 col-sm-12">
					<div class="media" id="jq-board-cp">
						<div class="media-left media-middle">
							<i class="fa fa-database"></i>
						</div>
						<div class="media-body">
							<h4 class="media-heading">
								磁盘使用率:<i id="diskUsage"></i><small>可用:<i id="diskFree"></i></small>
							</h4>
							<div class="progress">
								<div style="width: 0px; background-color: rgb(92, 184, 92);" aria-valuemax="100" aria-valuemin="0" aria-valuenow="" role="progressbar" class="progress-bar progress-bar3" id="diskUsagePie"></div>
							</div>
							<h5 class="media-footing">
								<small>已用:<i id="diskUsed"></i></small><small>总量:<i id="disk"></i></small>
							</h5>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row jq-board-content">
			<div class="jqc4">
				<div class="col-lg-12 col-md-12 col-sm-12">
					  <!-- Nav tabs -->
					  <ul class="nav nav-tabs" role="tablist">
					  	<li>主机历史数据</li>
					    <li role="presentation" class="active"><a href="#jq-main-cpu-history" aria-controls="home" role="tab" data-toggle="tab">CPU使用率</a></li>
					    <li role="presentation"><a href="#jq-main-nc-history" aria-controls="profile" role="tab" data-toggle="tab">存储使用率</a></li>
					    <li role="presentation"><a href="#jq-main-cp-history" aria-controls="messages" role="tab" data-toggle="tab">磁盘io速率</a></li>
					    <li role="presentation"><a href="#jq-main-wl-history" aria-controls="settings" role="tab" data-toggle="tab">网络io速率</a></li>
					  </ul>
					
					  <!-- Tab panes -->
					  <div class="tab-content">
						    <div role="tabpanel" class="tab-pane active" id="jq-main-cpu-history">
								<div id="main-cpu-history" style="width: 480px; height: 220px; padding-top: 38px; float: left;"></div>
							</div>
					    
						    <div role="tabpanel" class="tab-pane" id="jq-main-nc-history">
						    	
						    </div>
						    
						    <div role="tabpanel" class="tab-pane" id="jq-main-cp-history">
						    	
						    </div>
						    <div role="tabpanel" class="tab-pane" id="jq-main-wl-history">
						    	
						    </div>
					  </div>
								
				</div>
			</div>
		</div>
		
		<div class="row jq-board-content">
			<div class="jqc4">
				<div class="col-lg-12 col-md-12 col-sm-12">
					  <!-- Nav tabs -->
					  <ul class="nav nav-tabs" role="tablist">
					  	<li>虚拟机概况</li>
					    <li role="presentation" class="active"><a href="#jq-virtual-cpu-history" aria-controls="home" role="tab" data-toggle="tab">CPU使用率</a></li>
					    <li role="presentation"><a href="#jq-virtual-nc-history" aria-controls="profile" role="tab" data-toggle="tab">存储使用率</a></li>
					    <li role="presentation"><a href="#jq-virtual-cp-history" aria-controls="messages" role="tab" data-toggle="tab">磁盘io速率</a></li>
					    <li role="presentation"><a href="#jq-virtual-wl-history" aria-controls="settings" role="tab" data-toggle="tab">网络io速率</a></li>
					  </ul>
					
					  <!-- Tab panes -->
					<div class="tab-content">
					    <div role="tabpanel" class="tab-pane active" id="jq-virtual-cpu-history">
							<div id="virtual-cpu-history" style="width: 480px; height: 280px; padding-top: 38px; float: left;"></div>
						</div>
				    
					    <div role="tabpanel" class="tab-pane" id="jq-virtual-nc-history">
					    	
					    </div>
					    
					    <div role="tabpanel" class="tab-pane" id="jq-virtual-cp-history">
					    	
					    </div>
					    
					    <div role="tabpanel" class="tab-pane" id="jq-virtual-wl-history">
					    	
					    </div>
					</div>
								
				</div>
			</div>
		</div>
		
		<div class="row jq-board-content">
			<div class="col-lg-12 col-md-12 col-sm-12 jqc5">
				<h3>
					告警
				</h3>
			</div>
		</div>
	</div>
</div>
<script src="${ctx}/resources/lib/echarts/echarts.min.js"></script>
<script src="${ctx}/resources/js/monitor/monitor.js"></script>
<script type="text/javascript">
$(function() {
	cloudmanager.monitor.init();
});
</script>
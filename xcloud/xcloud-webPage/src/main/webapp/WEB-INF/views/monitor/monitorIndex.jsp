<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ include file="/WEB-INF/views/includeFile.jsp"%>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header">
				<h1>监控概览</h1>
				<div class="small">虚拟化环境是对计算、存储和网络等资源进行虚拟化的软件，例如FusionCompute。 系统中所使用和管理的虚拟资源都来源于虚拟化环境中的资源。
				</div>
			</div>
		</div>
	</div>
	<div class="row overview">
		<div class="col-lg-4 col-md-4 col-sm-12">
			<div class="gk-top">
				<h2>CPU整体使用视图</h2>
				<div class="cont-char">
					<div class="media">
					  	<div class="media-left media-middle">
							<i class="fa fa-qrcode"></i>
					  	</div>
						<div class="media-body">
						    <h4 class="media-heading"><i id="cpuUsage">${cpuUsage}%</i> <small>可用:<i id="cpuFreeGHz">${cpuFreeGHz}GHz</i></small></h4>
						    <div class="progress">
								 <div id="cpuUsagePie" class="progress-bar progress-bar1" style="width:${cpuUsagePie}%;" aria-valuemax="100" aria-valuemin="0" aria-valuenow="${cpuUsagePie}" role="progressbar"></div>
							</div>
							<h5 class="media-footing"><small>已用:<i id="cpuUsedGHz">${cpuUsedGHz}GHz</i></small><small>总量:<i id="cpuTotalGHz">${cpuTotalGHz}GHz</i></small></h5>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-4 col-md-4 col-sm-12">
			<div class="gk-top">
				<h2>内存整体使用视图</h2>
				<div class="cont-char">
					<div class="media">
					  	<div class="media-left media-middle">
							<i class="fa fa-ticket"></i>
					  	</div>
						<div class="media-body">
						    <h4 class="media-heading">使用率：<i id="memUsage">${memUsage}%</i><small>可用：<i id="memFreeGB">${memFreeGB}GB</i></small></h4>
						        <div class="progress">
		                        <div id="memUsagePie" class="progress-bar progress-bar2" style="width:${memUsagePie}%;" aria-valuemax="100" aria-valuemin="0" aria-valuenow="${memUsagePie}" role="progressbar"></div>
		                        </div>
							<h5 class="media-footing"><small>已用：<i id="memUsedGB">${memUsedGB}GB</i></small><small>总量：<i id="memTotalGB">${memTotalGB}GB</i></small></h5>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-4 col-md-4 col-sm-12">
			<div class="gk-top">
				<h2>磁盘整体使用视图</h2>
				<div class="cont-char">
					<div class="media">
					  	<div class="media-left media-middle">
							<i class="fa fa-database"></i>
					  	</div>
						<div class="media-body">
						    <h4 class="media-heading">使用率：<i id="storeUsage">${storeUsage}%</i><small>可用：<i id="storeFreeTB">${storeFreeTB}TB</i></small></h4>
						     <div class="progress">
	                           <div id="diskUsagePie" class="progress-bar progress-bar3" style="width:${diskUsagePie}%;" aria-valuemax="100" aria-valuemin="0" aria-valuenow="${diskUsagePie}" role="progressbar"></div>
	                        </div>
							<h5 class="media-footing"><small>已用：<i id="storeUsedTB">${storeUsedTB}TB</i></small><small>总量：<i id="storeTotalTB">${storeTotalTB}TB</i></small></h5>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row overview">
		<div class="gk-cen">
			<div class="col-lg-4 col-md-4 col-sm-12">
				<div class="gk-cen1">
					<div id="gk-host" style="width:60%; height: 100%;"></div>
				</div>
				<ul>
					<li>
						正常
						<br>
						 <span id="hostAccessibleNum">${hostAccessibleNum}</span>
					</li>
					<li>
						异常
						<br>
						<span id="hostUnaccessibleNum">${hostUnaccessibleNum}</span>
					</li>
				</ul>
				<div class="yxz">
					<div class="yxz-left"></div>
					正常
				</div>
				<div class="ytz">
					<div class="ytz-left"></div>
					异常
				</div>
			</div>

			<div class="col-lg-4 col-md-4 col-sm-12">
				<div class="gk-cen1">
					<div id="gk-vm" style="width:60%; height: 100%;"></div>
				</div>
				<ul>
					<li>
						正常
						<br>
						<span id="vmAccessibleNum">${vmAccessibleNum}</span>
					</li>
					<li>
						异常
						<br>
						<span id="vmUnaccessibleNum">${vmUnaccessibleNum}</span>
					</li>
				</ul>
				<div class="yxz">
					<div class="yxz-left2"></div>
					正常
				</div>
				<div class="ytz">
					<div class="ytz-left"></div>
					异常
				</div>
			</div>

			<div class="col-lg-4 col-md-4 col-sm-12">
				<div class="gk-cen1">
					<div id="gk-store" style="width:60%; height: 100%;"></div>
				</div>
				<ul>
					<li>
						正常
						<br>
						<span id="storeAccessibleNum">${storeAccessibleNum}</span>
					</li>
					<li>
						异常
						<br>
						<span id="storeUnaccessibleNum">${storeUnaccessibleNum}</span>
					</li>
				</ul>
				<div class="yxz">
					<div class="yxz-left3"></div>
					正常
				</div>
				<div class="ytz">
					<div class="ytz-left"></div>
					异常
				</div>
			</div>
		</div>
	</div>
	
	<div class="row overview">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="gk-cen2">
				<div class="main-gk">
					  <!-- Nav tabs -->
					  <ul class="nav nav-tabs" role="tablist">
					  	<li>主机概况</li>
					    <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">CPU使用率</a></li>
					    <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">内存使用率</a></li>
					    <li role="presentation"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">磁盘IO速率</a></li>
					    <li role="presentation"><a href="#settings" aria-controls="settings" role="tab" data-toggle="tab">网络IO速率</a></li>
					  </ul>
					
					  <!-- Tab panes -->
					  <div class="tab-content">
					    <div role="tabpanel" class="tab-pane active" id="home">
							<div id="main-cpu-left" style="width: 340px; height: 260px; padding-top: 20px; float: left;"></div>
							<div id="main-cpu-right" style="width: 600px; height: 260px; float: right;"></div>
					    </div>
					    
					    <div role="tabpanel" class="tab-pane" id="profile">
					    	<div id="main-mem-left" style="width: 340px; height: 260px; padding-top: 20px; float: left;"></div>
							<div id="main-mem-right" style="width: 600px; height: 260px; float: right;"></div>
					    </div>
					    
					    <div role="tabpanel" class="tab-pane" id="messages">
					    	<div id="main-disk-left" style="width: 340px; height: 260px; padding-top: 20px; float: left;"></div>
							<div id="main-disk-right" style="width: 600px; height: 260px; float: right;"></div>
					    </div>
					    
					    <div role="tabpanel" class="tab-pane" id="settings">
					    	<div id="main-net-left" style="width: 340px; height: 260px; padding-top: 20px; float: left;"></div>
							<div id="main-net-right" style="width: 600px; height: 260px; float: right;"></div>
					    </div>
					  </div>
					
				</div>
			</div>
		</div>
	</div>
	
	<div class="row overview">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="gk-cen2">
				<div class="main-gk">
					  <!-- Nav tabs -->
					  <ul class="nav nav-tabs" role="tablist">
					  	<li>虚拟机概况</li>
					    <li role="presentation" class="active"><a href="#home2" aria-controls="home2" role="tab" data-toggle="tab">CPU使用率</a></li>
					    <li role="presentation"><a href="#profile2" aria-controls="profile2" role="tab" data-toggle="tab">内存使用率</a></li>
					    <li role="presentation"><a href="#messages2" aria-controls="messages2" role="tab" data-toggle="tab">磁盘IO速率</a></li>
					    <li role="presentation"><a href="#settings2" aria-controls="settings2" role="tab" data-toggle="tab">网络IO速率</a></li>
					  </ul>
					
					  <!-- Tab panes -->
					  <div class="tab-content">
					    <div role="tabpanel" class="tab-pane active" id="home2">
							<div id="virtual-cpu-left" style="width: 340px; height: 260px; padding-top: 20px; float: left;"></div>
							<div id="virtual-cpu-right" style="width: 600px; height: 260px; float: right;"></div>
					    </div>
					    
					    <div role="tabpanel" class="tab-pane" id="profile2">
					    	<div id="virtual-mem-left" style="width: 340px; height: 260px; padding-top: 20px; float: left;"></div>
							<div id="virtual-mem-right" style="width: 600px; height: 260px; float: right;"></div>
					    </div>
					    
					    <div role="tabpanel" class="tab-pane" id="messages2">
					    	<div id="virtual-disk-left" style="width: 340px; height: 260px; padding-top: 20px; float: left;"></div>
							<div id="virtual-disk-right" style="width: 600px; height: 260px; float: right;"></div>
					    </div>
					    <div role="tabpanel" class="tab-pane" id="settings2">
					    	<div id="virtual-net-left" style="width: 340px; height: 260px; padding-top: 20px; float: left;"></div>
							<div id="virtual-net-right" style="width: 600px; height: 260px; float: right;"></div>
					    </div>
					  </div>
					
				</div>
			</div>
		</div>
	</div>
	
	<!-- <div class="row overview">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="gk-cen2">
				<div class="main-gk">
					  Nav tabs
					  <ul class="nav nav-tabs" role="tablist">
					  	<li>集群概况</li>
					    <li role="presentation" class="active"><a href="#home3" aria-controls="home3" role="tab" data-toggle="tab">CPU使用率</a></li>
					    <li role="presentation"><a href="#profile3" aria-controls="profile3" role="tab" data-toggle="tab">存储使用率</a></li>
					    <li role="presentation"><a href="#messages3" aria-controls="messages3" role="tab" data-toggle="tab">磁盘io速率</a></li>
					    <li role="presentation"><a href="#settings3" aria-controls="settings3" role="tab" data-toggle="tab">网络io速率</a></li>
					  </ul>
					
					  Tab panes
					  <div class="tab-content">
					    <div role="tabpanel" class="tab-pane active" id="home3">
							<div id="cluster-cpu-left" style="width: 340px; height: 260px; padding-top: 20px; float: left;"></div>
							<div id="cluster-cpu-right" style="width: 600px; height: 260px; float: right;"></div>
					    </div>
					    
					    <div role="tabpanel" class="tab-pane" id="profile3">
					    	<div id="cluster-storage-left" style="width: 340px; height: 260px; padding-top: 20px; float: left;"></div>
							<div id="cluster-storage-right" style="width: 600px; height: 260px; float: right;"></div>
					    </div>
					    
					    <div role="tabpanel" class="tab-pane" id="messages3">
					    	<div id="cluster-disk-left" style="width: 340px; height: 260px; padding-top: 20px; float: left;"></div>
							<div id="cluster-disk-right" style="width: 600px; height: 260px; float: right;"></div>
					    </div>
					    <div role="tabpanel" class="tab-pane" id="settings3">
					    	<div id="cluster-net-left" style="width: 340px; height: 260px; padding-top: 20px; float: left;"></div>
							<div id="cluster-net-right" style="width: 600px; height: 260px; float: right;"></div>
					    </div>
					  </div>
					
				</div>
			</div>
		</div>
	</div>
	
	<div class="row overview">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="gk-cen2">
				<div class="main-gk">
					  Nav tabs
					  <ul class="nav nav-tabs" role="tablist">
					  	<li>存储概况</li>
					    <li role="presentation" class="active"><a href="#home4" aria-controls="home4" role="tab" data-toggle="tab">CPU使用率</a></li>
					    <li role="presentation"><a href="#profile4" aria-controls="profile4" role="tab" data-toggle="tab">存储使用率</a></li>
					    <li role="presentation"><a href="#messages4" aria-controls="messages4" role="tab" data-toggle="tab">磁盘io速率</a></li>
					    <li role="presentation"><a href="#settings4" aria-controls="settings4" role="tab" data-toggle="tab">网络io速率</a></li>
					  </ul>
					
					  Tab panes
					  <div class="tab-content">
					    <div role="tabpanel" class="tab-pane active" id="home4">
							<div id="store-cpu-left" style="width: 340px; height: 260px; padding-top: 20px; float: left;"></div>
							<div id="store-cpu-right" style="width: 600px; height: 260px; float: right;"></div>
					    </div>
					    
					    <div role="tabpanel" class="tab-pane" id="profile4">
					    	<div id="store-storage-left" style="width: 340px; height: 260px; padding-top: 20px; float: left;"></div>
							<div id="store-storage-right" style="width: 600px; height: 260px; float: right;"></div>
					    </div>
					    
					    <div role="tabpanel" class="tab-pane" id="messages4">
					    	<div id="store-disk-left" style="width: 340px; height: 260px; padding-top: 20px; float: left;"></div>
							<div id="store-disk-right" style="width: 600px; height: 260px; float: right;"></div>										    </div>
					    <div role="tabpanel" class="tab-pane" id="settings4">
					    	<div id="store-net-left" style="width: 340px; height: 260px; padding-top: 20px; float: left;"></div>
							<div id="store-net-right" style="width: 600px; height: 260px; float: right;"></div>
					    </div>
					  </div>
					
				</div>
			</div>
		</div>
	</div> -->
	
	<div class="row overview">
			<div class="col-lg-4 col-md-4 col-sm-12">
				<div class="gk-cen3">
					<h2>虚拟机告警</h2>
					<div class="gj-left">
						<div class="list-group list-group1">
						  <!-- 	<a href="#" class="list-group-item list-group-item1">
						  		一般
						  		<span class="label label-default virtual-square">0</span>
						  	</a>   -->	
						  	<a href="#" class="list-group-item list-group-item1">
						  		警告
						  		<span class="label label-warning virtual-square" id="vm_warning">0</span>
						  	</a>
						  	<a href="#" class="list-group-item list-group-item1">
						  		严重
						  		<span class="label label-danger virtual-square" id="vm_ciritical">0</span>
						  	</a>
						  	
						  	<!-- <a href="#" class="list-group-item list-group-item1">
						  		灾难
						  		<span class="label label-danger virtual-square">0</span>
						  	</a> -->
						</div>
					</div>
					<div id="gj-virtual-right" style="width: 150px; height: 150px; float: right; margin-right: 12px; margin-top: -20px;"></div>
				</div>
			</div>
			
			<div class="col-lg-4 col-md-4 col-sm-12">
				<div class="gk-cen3">
					<h2>物理机告警</h2>
					<div class="gj-left">
						<div class="list-group list-group1">
						  	<!-- <a href="#" class="list-group-item list-group-item1">
						  		一般
						  		<span class="label label-default virtual-square">0</span>
						  	</a>  	 -->
						  	<a href="#" class="list-group-item list-group-item1">
						  		警告
						  		<span class="label label-warning virtual-square" id="host_warning">0</span>
						  	</a>
						  	<a href="#" class="list-group-item list-group-item1">
						  		严重
						  		<span class="label label-danger virtual-square" id="host_ciritical">0</span>
						  	</a>
						  <!-- 	
						  	<a href="#" class="list-group-item list-group-item1">
						  		灾难
						  		<span class="label label-danger virtual-square">0</span>
						  	</a> -->
						</div>
					</div>
					<div id="gj-main-right" style="width: 150px; height: 150px; float: right; margin-right: 12px; margin-top: -20px;"></div>
				</div>	
			</div>
			
			<div class="col-lg-4 col-md-4 col-sm-12">
				<div class="gk-cen3">
					<h2>存储告警</h2>
					<div class="gj-left">
						<div class="list-group list-group1">
						  	<!-- <a href="#" class="list-group-item list-group-item1">
						  		一般
						  		<span class="label label-default virtual-square">0</span>
						  	</a>   -->	
						  	<a href="#" class="list-group-item list-group-item1">
						  		警告
						  		<span class="label label-warning virtual-square" id="store_warning">0</span>
						  	</a>
						  	<a href="#" class="list-group-item list-group-item1">
						  		严重
						  		<span class="label label-danger virtual-square" id="store_ciritical">0</span>
						  	</a>
						  <!-- 	
						  	<a href="#" class="list-group-item list-group-item1">
						  		灾难
						  		<span class="label label-danger virtual-square">0</span>
						  	</a> -->
						</div>
					</div>
					<div id="gj-storage-right" style="width: 150px; height: 150px; float: right; margin-right: 12px; margin-top: -20px;"></div>
				</div>
			</div>
	</div>
	
	<div class="row overview">
		<div class="col-lg-12 col-md-12 col-sm-12 vm-table">
			<div class="gk-bottom">
				<div class="recent-gj">
					<h2>最近20条告警</h2>
					<table id="monitorIndexTableId" class="easyui-datagrid">
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${ctx}/resources/lib/echarts/echarts.min.js"></script>
<script src="${ctx}/resources/js/monitor/monitor.js"></script>
<script src="${ctx}/resources/js/monitor/monitorSummary.js"></script>

<script type="text/javascript">
$(function() {
	/*  cloudmanager.monitor.init(); */
	// cloudmanager.monitor.initTopN();
     cloudmanager.monitor.updateTopN();
});
</script>
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
				  	<li>集群Top10</li>
				    <li role="presentation" class="active"><a href="#jq1" aria-controls="home" role="tab" data-toggle="tab">CPU使用率(%)</a></li>
				    <li role="presentation"><a href="#jq2" aria-controls="profile" role="tab" data-toggle="tab">CPU使用量(MHz)</a></li>
				    <li role="presentation"><a href="#jq3" aria-controls="messages" role="tab" data-toggle="tab">内存使用率(%)</a></li>
				    <li role="presentation"><a href="#jq4" aria-controls="settings" role="tab" data-toggle="tab">内存使用量(MB)</a></li>
				  </ul>
				
				  <!-- Tab panes -->
				  <div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="jq1">
						<div id="jq-cpu-usage" style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
					</div>
				    
				    <div role="tabpanel" class="tab-pane" id="jq2">
				        <div id="jq-cpu-used" style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
				    </div>
				    
				    <div role="tabpanel" class="tab-pane" id="jq3">
				        <div id="jq-mem-usage" style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
				    </div>
				    
				    <div role="tabpanel" class="tab-pane" id="jq4">
				        <div id="jq-mem-used" style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
				    </div>
				  </div>
					
				</div>
		</div>
		
		<div class="col-lg-6 col-md-6 col-sm-12">
			<div class="jq-table-content">
				 <!-- Nav tabs -->
				  <ul class="nav nav-tabs" role="tablist">
				  	<li>集群Top10</li>
				    <li role="presentation" class="active"><a href="#jq5" aria-controls="home" role="tab" data-toggle="tab">磁盘IO(KB/s)</a></li>
				    <li role="presentation"><a href="#jq6" aria-controls="profile" role="tab" data-toggle="tab">网络IO(KB/s)</a></li>
				    <li role="presentation"><a href="#jq7" aria-controls="messages" role="tab" data-toggle="tab">物理机数量(台)</a></li>
				    <li role="presentation"><a href="#jq8" aria-controls="settings" role="tab" data-toggle="tab">虚拟机数量(台)</a></li>
				  </ul>
				
				  <!-- Tab panes -->
				  <div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="jq5">
						<div id="jq-disk-io" style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
					</div>
				    
				    <div role="tabpanel" class="tab-pane" id="jq6">
				        <div id="jq-net-io" style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
				    </div>
				    
				    <div role="tabpanel" class="tab-pane" id="jq7">
				        <div id="jq-host-num" style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
				    </div>
				    
				    <div role="tabpanel" class="tab-pane" id="jq8">
				        <div id="jq-vm-num" style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
				    </div>
				  </div>
					
				</div>
			</div>
		</div>
	</div>	
	<div class="row overview">
        <div class="col-lg-12 col-md-12 col-sm-12 vm-table">
            <div class="gk-bottom">
                <div class="recent-gj">
                    <h2>集群列表</h2>
                    <table id="monitorIndexTableId" class="easyui-datagrid">
                    </table>
                    <div id="monitorIndextb">
                        <div class="row">
                            <div class="col-lg-6 col-md-6 col-sm-12">
                            </div>
                            <div class="col-lg-6 col-md-6 col-sm-12">
                                <div class="filter clearfix">
                                    <!-- <div class="filter-tool">
                                        <a role="button" href="#" class="btn btn-default">
                                            <i class="fa fa-external-link"></i>
                                        </a>
                                        <a role="button" href="#" class="btn btn-default">
                                            <i class="fa fa-cog"></i>
                                        </a>
                                    </div> -->
                                    <div class="filter-search">
                                        <input id="monitorIndexSearchInputId" class="sugon-searchbox"  style="width:80%; height:32px;">
                                        <div id="tableSearch">
                                            <div data-options="name:'name'">名称</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
	
</div>
<div class="jq-sideboard">
	<a class="sideboard-close" href="javascript:;" onclick="cloudmanager.monitorCluster.sideboardClose()"><i class="fa fa-remove"></i></a>
	<div class="jq-board">
		<h2>
			<span id="clusterTagName"></span>
		</h2>
		<div class="row jq-board-content">
			<div class="col-sm-6 vm-tabs">
				<ul class="list-group">
					<li class="list-group-item list-group-item-heading">基本信息</li>
					<li class="list-group-item"><span>集群名称: </span><span id="clusterName"></span></li>
					<li class="list-group-item"><span>所属数据中心: </span><span id="dataCenterName"></span></li>
				</ul>
			</div>
			<div class="col-sm-6">
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
				<div class="media" id="jq-board-nc">
					<div class="media-left media-middle">
						<i class="fa fa-ticket"></i>
					</div>
					<div class="media-body">
						<h4 class="media-heading">
							内存使用率:<i id="memUsage"></i><small>可用:<i id="memFreeGB"></i></small>
						</h4>
						<div class="progress">
							<div style="width: 0px; background-color: rgb(157, 80, 165);" aria-valuemax="100" aria-valuemin="0" aria-valuenow="" role="progressbar" class="progress-bar progress-bar2" id="memUsagePie"></div>
						</div>
						<h5 class="media-footing">
							<small>已用:<i id="memUsedGB"></i></small><small>总量:<i id="memGB"></i></small>
						</h5>
					</div>
				</div>
				<div class="media" id="jq-board-cp">
					<div class="media-left media-middle">
						<i class="fa fa-database"></i>
					</div>
					<div class="media-body">
						<h4 class="media-heading">
							磁盘使用率:<i id="diskUsage"></i><small>可用:<i id="diskFreetTB"></i></small>
						</h4>
						<div class="progress">
							<div style="width: 0px; background-color: rgb(92, 184, 92);" aria-valuemax="100" aria-valuemin="0" aria-valuenow="" role="progressbar" class="progress-bar progress-bar3" id="diskUsagePie"></div>
						</div>
						<h5 class="media-footing">
							<small>已用:<i id="diskUsedTB"></i></small><small>总量:<i id="diskTB"></i></small>
						</h5>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row jq-board-content">
			<div class="col-lg-4 col-md-4 col-sm-12 jqc2">
				<div id="jq-board-main" style="width:210px; height:200px; margin-top: -10px;"></div>	
			</div>
			<div class="col-lg-4 col-md-4 col-sm-12 jqc2">
				<div id="jq-board-virtual" style="width:210px; height:200px; margin-top: -10px;"></div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-12 jqc2">
				<div id="jq-board-storage" style="width:210px; height:200px; margin-top: -10px; margin-left: -10px;"></div>
			</div>
		</div>
		<div class="row jq-board-content">
	        <div class="col-lg-12 col-md-12 col-sm-12">
	              <!-- Nav tabs -->
	              <ul class="nav nav-tabs" role="tablist">
	                <li>历史数据</li>
	                <li role="presentation" class="active"><a href="#jq21" aria-controls="home" role="tab" data-toggle="tab">CPU</a></li>
	                <li role="presentation"><a href="#jq22" aria-controls="profile" role="tab" data-toggle="tab">内存</a></li>
	                <li role="presentation"><a href="#jq23" aria-controls="messages" role="tab" data-toggle="tab">磁盘IO</a></li>
	                <li role="presentation"><a href="#jq24" aria-controls="settings" role="tab" data-toggle="tab">网络IO</a></li>
	              </ul>
	            
	              <!-- Tab panes -->
	              <div class="tab-content">
	                  <div role="tabpanel" class="tab-pane active" id="jq21">
	                      <div id="jq-history-cpu" style="width: 480px; height: 220px;"></div>
	                  </div>
	              
	                  <div role="tabpanel" class="tab-pane" id="jq22">
	                      <div id="jq-history-mem" style="width: 480px; height: 220px;"></div>     
	                  </div>
	                  
	                  <div role="tabpanel" class="tab-pane" id="jq23">
	                      <div id="jq-history-disk" style="width: 480px; height: 220px;"></div>
	                  </div>
	                  
	                  <div role="tabpanel" class="tab-pane" id="jq24">
	                      <div id="jq-history-net" style="width: 480px; height: 220px;"></div>
	                  </div>
	              </div>          
	        </div>
		</div>
		
		<div class="row jq-board-content">
	        <div class="col-lg-12 col-md-12 col-sm-12">
	              <!-- Nav tabs -->
	              <ul class="nav nav-tabs" role="tablist">
	                <li>主机概况</li>
	                <li role="presentation" class="active"><a href="#jq11" aria-controls="home" role="tab" data-toggle="tab">CPU使用率(%)</a></li>
	                <li role="presentation"><a href="#jq12" aria-controls="profile" role="tab" data-toggle="tab">内存使用率(%)</a></li>
	                <li role="presentation"><a href="#jq13" aria-controls="messages" role="tab" data-toggle="tab">磁盘IO(KB/s)</a></li>
	                <li role="presentation"><a href="#jq14" aria-controls="settings" role="tab" data-toggle="tab">网络IO(KB/s)</a></li>
	              </ul>
	            
	              <!-- Tab panes -->
	              <div class="tab-content">
	                    <div role="tabpanel" class="tab-pane active" id="jq11">
	                        <div id="jq-host-cpu-usage" style="width: 480px; height: 220px;"></div>
	                    </div>
	                
	                    <div role="tabpanel" class="tab-pane" id="jq12">
	                        <div id="jq-host-mem-usage" style="width: 480px; height: 220px;"></div>     
	                    </div>
	                    
	                    <div role="tabpanel" class="tab-pane" id="jq13">
	                        <div id="jq-host-disk-speed" style="width: 480px; height: 220px;"></div>
	                    </div>
	                    
	                    <div role="tabpanel" class="tab-pane" id="jq14">
	                        <div id="jq-host-net-speed" style="width: 480px; height: 220px;"></div>
	                    </div>
	              </div>          
	        </div>
        </div>
		
		<div class="row jq-board-content">
	        <div class="col-lg-12 col-md-12 col-sm-12">
	              <!-- Nav tabs -->
	              <ul class="nav nav-tabs" role="tablist">
	                <li>虚拟机概况</li>
	                <li role="presentation" class="active"><a href="#jq15" aria-controls="home" role="tab" data-toggle="tab">CPU使用率(%)</a></li>
	                <li role="presentation"><a href="#jq16" aria-controls="profile" role="tab" data-toggle="tab">内存使用率(%)</a></li>
	                <li role="presentation"><a href="#jq17" aria-controls="messages" role="tab" data-toggle="tab">磁盘IO(KB/s)</a></li>
	                <li role="presentation"><a href="#jq18" aria-controls="settings" role="tab" data-toggle="tab">网络IO(KB/s)</a></li>
	              </ul>
	            
	              <!-- Tab panes -->
	              <div class="tab-content">
	                    <div role="tabpanel" class="tab-pane active" id="jq15">
	                        <div id="jq-vm-cpu-usage" style="width: 480px; height: 220px;"></div>
	                    </div>
	                
	                    <div role="tabpanel" class="tab-pane" id="jq16">
	                        <div id="jq-vm-mem-usage" style="width: 480px; height: 220px;"></div>     
	                    </div>
	                    
	                    <div role="tabpanel" class="tab-pane" id="jq17">
	                        <div id="jq-vm-disk-speed" style="width: 480px; height: 220px;"></div>
	                    </div>
	                    
	                    <div role="tabpanel" class="tab-pane" id="jq18">
	                        <div id="jq-vm-net-speed" style="width: 480px; height: 220px;"></div>
	                    </div>
	              </div>          
	        </div>
        </div>
		
		
		<div class="row jq-board-content">
            <div class="col-lg-12 col-md-12 col-sm-12  vm-table jqc5">
                <h3>最近20条告警</h3>
                <div class="gk-bottom">
                    <div class="recent-gj">
                     <table id="monitorAlarm" class="easyui-datagrid">
                    </table>
                   
                    </div>
                  </div>
              </div>
        </div>
	</div>
</div>
<script src="${ctx}/resources/lib/echarts/echarts.min.js"></script>
<script src="${ctx}/resources/js/monitor/monitorSummary.js"></script>
<script src="${ctx}/resources/js/monitor/monitorCluster.js"></script>
<script type="text/javascript">
$(function() {
    cloudmanager.monitorCluster.initTopN();
    cloudmanager.monitorCluster.updateTopN();
    cloudmanager.monitorCluster.initTable();
    //window.setInterval("cloudmanager.monitorCluster.update()",20000);
});
</script>
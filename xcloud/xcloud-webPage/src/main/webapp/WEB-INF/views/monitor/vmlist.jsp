<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ include file="/WEB-INF/views/includeFile.jsp"%>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header">
				<h1>虚拟机</h1>
				<div class="small">
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
				  	<li>虚拟机Top10</li>
				    <li role="presentation" class="active"><a href="#jq1" aria-controls="home" role="tab" data-toggle="tab">CPU使用率(%)</a></li>
				    <li role="presentation"><a href="#jq2" aria-controls="profile" role="tab" data-toggle="tab">CPU使用量(MHz)</a></li>
				    <li role="presentation"><a href="#jq3" aria-controls="messages" role="tab" data-toggle="tab">内存使用率(%)</a></li>
				    <li role="presentation"><a href="#jq4" aria-controls="settings" role="tab" data-toggle="tab">内存使用量(MB)</a></li>
				  </ul>
				
				  <!-- Tab panes -->
				  <div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="jq1">
						<div id="jqvm-cpu-usage" style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
					</div>
				    
				    <div role="tabpanel" class="tab-pane" id="jq2">
				        <div id="jqvm-cpu-used" style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
				    </div>
				    
				    <div role="tabpanel" class="tab-pane" id="jq3">
				        <div id="jqvm-mem-usage" style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
				    </div>
				    
				    <div role="tabpanel" class="tab-pane" id="jq4">
				        <div id="jqvm-mem-used" style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
				    </div>
				  </div>
					
				</div>
		</div>
		
		<div class="col-lg-6 col-md-6 col-sm-12">
			<div class="jq-table-content">
				 <!-- Nav tabs -->
				  <ul class="nav nav-tabs" role="tablist">
				  	<li>虚拟机Top10</li>
				    <li role="presentation" class="active"><a href="#jq5" aria-controls="home" role="tab" data-toggle="tab">磁盘IO(KB/s)</a></li>
				    <li role="presentation"><a href="#jq6" aria-controls="profile" role="tab" data-toggle="tab">网络IO(KB/s)</a></li>
				    <li role="presentation"><a href="#jq7" aria-controls="messages" role="tab" data-toggle="tab">磁盘使用率(%)</a></li>
				    <li role="presentation"><a href="#jq8" aria-controls="settings" role="tab" data-toggle="tab">磁盘IOPS</a></li>
				  </ul>
				
				  <!-- Tab panes -->
				  <div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="jq5">
						<div id="jqvm-disk-io" style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
					</div>
				    
				    <div role="tabpanel" class="tab-pane" id="jq6">
				        <div id="jqvm-net-io" style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
				    </div>
				    
				    <div role="tabpanel" class="tab-pane" id="jq7">
				        <div id="jqvm-disk-usage" style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
				    </div>
				    
				    <div role="tabpanel" class="tab-pane" id="jq8">
				        <div id="jqvm-disk-iops" style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
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
                    <h2>虚拟机列表</h2>
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
	<a class="sideboard-close" href="javascript:;" onclick="cloudmanager.monitorVm.sideboardClose()"><i class="fa fa-remove"></i></a>
	<div class="jq-board">
		<h2>
			<span id="vmTagName"></span>
		</h2>
		<div class="row jq-board-content">
			<div class="col-lg-12 col-md-12 col-sm-12 jqc1" style="height:140px;">
				<h3>
					基本信息
				</h3>
				<ul style="height:120px;">
					<li style="height:25%;"><span>虚拟机名称: </span><span id="vmName"></span></li>
					<li style="height:25%;"><span>所属数据中心: </span><span id="dataCenterName"></span></li>
					<li style="height:25%;"><span>所属集群: </span><span id="clusterName"></span></li>
					<li style="height:25%;"><span>地址信息: </span><span id="ipAddr"></span></li>
				</ul>
			</div>
		</div>
		
		<!-- <div class="row jq-board-content">
			<div class="col-lg-4 col-md-4 col-sm-12 jqc2">
				<div id="jq-board-main" style="width:210px; height:200px; margin-top: -10px;"></div>	
			</div>
			<div class="col-lg-4 col-md-4 col-sm-12 jqc2">
				<div id="jq-board-virtual" style="width:210px; height:200px; margin-top: -10px;"></div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-12 jqc2">
				<div id="jq-board-storage" style="width:210px; height:200px; margin-top: -10px; margin-left: -10px;"></div>
			</div>
		</div> -->
		
		<div class="row jq-board-content">
			<div class="jqc3">
				<div class="col-lg-12 col-md-12 col-sm-12">
					<div id="jq-board-cpu" class="jqc3-content">
						<span><i class="fa fa-cubes"></i></span>
						<ul>
							<li>
								CPU使用率:
								<i id="cpuUsage"></i>
							</li>
							<li>
								可用:
								<i id="cpuFreeGHz"></i>
							</li>
						</ul>
						<div class="progress">
							<div id="cpuUsagePie" class="progress-bar progress-bar1" role="progressbar" aria-valuenow="" aria-valuemin="0" aria-valuemax="100" style="width:0%;background-color:#3facca"></div>
						</div>
						<ul>
							<li>
								已用:
								<i id="cpuUsedGHz"></i>
							</li>
							<li>
								总量:
								<i id="cpuGHz"></i>
							</li>
						</ul>
					</div>	
				</div>
				<div class="col-lg-12 col-md-12 col-sm-12">
					<div id="jq-board-nc" class="jqc3-content">
						<span><i class="fa fa-inbox"></i></span>
								<ul>
									<li>
										内存使用率:
										<i id="memUsage"></i>
									</li>
									<li>
										可用:
										<i id="memFreeGB"></i>
									</li>
								</ul>
								<div class="progress">
									<div id="memUsagePie" class="progress-bar progress-bar2" role="progressbar" aria-valuenow="" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>
								</div>
								<ul>
									<li>
										已用:
										<i id="memUsedGB"></i>
									</li>
									<li>
										总量:
										<i id="memGB"></i>
									</li>
								</ul>
					</div>
				</div>
				<div class="col-lg-12 col-md-12 col-sm-12">
					<div id="jq-board-cp" class="jqc3-content">
						<span><i class="fa fa-ticket"></i></span>
								<ul>
									<li>
										磁盘使用率:
										<i id="diskUsage"></i>
									</li>
									<li>
										可用:
										<i id="diskFreeTB"></i>
									</li>
								</ul>
								<div class="progress">
									<div id="diskUsagePie" class="progress-bar progress-bar3" role="progressbar" aria-valuenow="" aria-valuemin="0" aria-valuemax="100" style="width:0%;background-color:#94cb4c"></div>
								</div>
								<ul>
									<li>
										已用:
										<i id="diskUsedTB"></i>
									</li>
									<li>
										总量:
										<i id="diskTB"></i>
									</li>
								</ul>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row jq-board-content">
			<div class="jqc4">
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
                                <div id="jq-history-cpu" style="width: 480px; height: 220px; padding-top: 38px; float: left;"></div>
                            </div>
                        
                            <div role="tabpanel" class="tab-pane" id="jq22">
                                <div id="jq-history-mem" style="width: 480px; height: 220px; padding-top: 38px; float: left;"></div>     
                            </div>
                            
                            <div role="tabpanel" class="tab-pane" id="jq23">
                                <div id="jq-history-disk" style="width: 480px; height: 220px; padding-top: 38px; float: left;"></div>
                            </div>
                            
                            <div role="tabpanel" class="tab-pane" id="jq24">
                                <div id="jq-history-net" style="width: 480px; height: 220px; padding-top: 38px; float: left;"></div>
                            </div>
                      </div>          
                </div>
            </div>
		</div>
		
		
		<div class="row jq-board-content">
            <div class="col-lg-12 col-md-12 col-sm-12  vm-table jqc5">
                <h3>最近10条告警</h3>
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
<script src="${ctx}/resources/js/monitor/monitorVm.js"></script>
<script type="text/javascript">
$(function() {
    cloudmanager.monitorVm.initTopN();
    cloudmanager.monitorVm.updateTopN();
    cloudmanager.monitorVm.initTable();
   // window.setInterval("cloudmanager.monitorVm.update()",20000);
});


</script>
<style type="text/css">
span.zt-green{ display: block; height: 18px; width: 38px; background: #70c383; color: #fff; line-height: 18px; text-align: center;left: 50%; margin-left: -19px; position: relative; border-radius: 3px;}
span.zt-yellow{ display: block; height: 18px; width: 38px; background: #f4d583; color: #fff; line-height: 18px; text-align: center;left: 50%; margin-left: -19px; position: relative; border-radius: 3px;}
span.zt-red{ display: block; height: 18px; width: 38px; background: #ff0000; color: #fff; line-height: 18px; text-align: center;left: 50%; margin-left: -19px; position: relative; border-radius: 3px;}
span.zt-gray{ display: block; height: 18px; width: 38px; background: #c0c0c0; color: #fff; line-height: 18px; text-align: center;left: 50%; margin-left: -19px; position: relative; border-radius: 3px;}

</style>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ include file="/WEB-INF/views/includeFile.jsp"%>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header">
				<h1>主机</h1>
				<div class="small">虚拟化环境是对计算、存储和网络等资源进行虚拟化的软件，例如FusionCompute。
					系统中所使用和管理的虚拟资源都来源于虚拟化环境中的资源。</div>
			</div>
		</div>
	</div>

	<div class="row jiqun">
		<div class="jq-table">
			<div class="col-lg-6 col-md-6 col-sm-12">
				<div class="jq-table-content">
					<!-- Nav tabs -->
					<ul class="nav nav-tabs" role="tablist">
						<li>主机</li>
						<li role="presentation" class="active"><a href="#host1"
							aria-controls="home" role="tab" data-toggle="tab">CPU使用率(%)</a></li>
						<li role="presentation"><a href="#host2"
							aria-controls="profile" role="tab" data-toggle="tab">CPU使用量(MHz)</a></li>
						<li role="presentation"><a href="#host3"
							aria-controls="messages" role="tab" data-toggle="tab">内存使用率(%)</a></li>
						<li role="presentation"><a href="#host4"
							aria-controls="settings" role="tab" data-toggle="tab">内存使用量(GB)</a></li>
					</ul>

					<!-- Tab panes -->
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active" id="host1">
							<div id="host-cpu-usage"
								style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
						</div>

						<div role="tabpanel" class="tab-pane" id="host2">
							<div id="host-cpu-used"
								style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
						</div>

						<div role="tabpanel" class="tab-pane" id="host3">
							<div id="host-mem-usage"
								style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
						</div>

						<div role="tabpanel" class="tab-pane" id="host4">
							<div id="host-mem-used"
								style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-lg-6 col-md-6 col-sm-12">
				<div class="jq-table-content">
					<!-- Nav tabs -->
					<ul class="nav nav-tabs" role="tablist">
						<li>主机</li>
						<li role="presentation" class="active"><a href="#host5"
							aria-controls="home" role="tab" data-toggle="tab">磁盘IO速率(KB/s)</a></li>
						<li role="presentation"><a href="#host6"
							aria-controls="profile" role="tab" data-toggle="tab">磁盘IOPS</a></li>
						<li role="presentation"><a href="#host7"
							aria-controls="messages" role="tab" data-toggle="tab">网络IO速率(KB/s)</a></li>
						<li role="presentation"><a href="#host8"
							aria-controls="settings" role="tab" data-toggle="tab">虚拟机数量(台)</a></li>
					</ul>

					<!-- Tab panes -->
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active" id="host5">
							<div id="host-disk-io"
								style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
						</div>

						<div role="tabpanel" class="tab-pane" id="host6">
							<div id="host-disk-iops"
								style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
						</div>

						<div role="tabpanel" class="tab-pane" id="host7">
							<div id="host-net-io"
								style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
						</div>

						<div role="tabpanel" class="tab-pane" id="host8">
							<div id="host-vm-num"
								style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
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
					<h2>主机列表</h2>
					<table id="monitorIndexTableId" class="easyui-datagrid">
					</table>
					<div id="monitorIndextb">
						<div class="row">
							<div class="col-lg-6 col-md-6 col-sm-12"></div>
							<div class="col-lg-6 col-md-6 col-sm-12">
								<div class="filter clearfix">
									<div class="filter-tool">
										<a role="button" href="#" class="btn btn-default"> <i
											class="fa fa-external-link"></i>
										</a> <a role="button" href="#" class="btn btn-default"> <i
											class="fa fa-cog"></i>
										</a>
									</div>
									<div class="filter-search">
										<input id="monitorIndexSearchInputId" class="sugon-searchbox"
											style="width: 80%; height: 32px;">
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
	<a class="sideboard-close" href="javascript:;"
		onclick="cloudmanager.monitorHost.sideboardClose()"><i
		class="fa fa-remove"></i></a>
	<div class="jq-board">
		<h2>
			<span id="hostTagName"></span>
		</h2>
		<div class="row jq-board-content">
			<div class="col-lg-12 col-md-12 col-sm-12 jqc1"
				style="height: 140px;">
				<h3>基本信息</h3>
				<ul style="height: 130px;">
					<li style="height: 25%;"><span>主机名称: </span><span
						id="hostName"></span></li>
					<li style="height: 25%;"><span>所属数据中心:</span><span
						id="dataCenterName"></span></li>
					<li style="height: 25%;"><span>所属集群: </span><span
						id="clusterName"></span></li>
					<li style="height: 25%;"><span>地址信息:</span><span id="hostIp"></span></li>
				</ul>
			</div>
		</div>

		<div class="row jq-board-content">
			<div class="col-lg-4 col-md-4 col-sm-12 jqc2">
				<div id="host-board-virtual"
					style="width: 210px; height: 200px; margin-top: -10px; margin-left: 100px;"></div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-12 jqc2">
				<div id="host-board-storage"
					style="width: 210px; height: 200px; margin-top: -10px; margin-left: 150px;"></div>
			</div>
		</div>

		<div class="row jq-board-content">
			<div class="jqc3">
				<div class="col-lg-12 col-md-12 col-sm-12">
					<div id="host-board-cpu" class="jqc3-content">
						<span><i class="fa fa-cubes"></i></span>
						<ul>
							<li>CPU使用率: <i id="cpuUsage"></i>
							</li>
							<li>可用: <i id="cpuFreeGHz"></i>
							</li>
						</ul>
						<div class="progress">
							<div id="cpuUsagePie" class="progress-bar progress-bar1"
								role="progressbar" aria-valuenow="" aria-valuemin="0"
								aria-valuemax="100" style="width: 0%; background-color: #3facca"></div>
						</div>
						<ul>
							<li>已用: <i id="cpuUsedGHz"></i>
							</li>
							<li>总量: <i id="cpuGHz"></i>
							</li>
						</ul>
					</div>
				</div>
				<div class="col-lg-12 col-md-12 col-sm-12">
					<div id="host-board-mem" class="jqc3-content">
						<span><i class="fa fa-inbox"></i></span>
						<ul>
							<li>内存使用率: <i id="memUsage"></i>
							</li>
							<li>可用: <i id="memFreeGB"></i>
							</li>
						</ul>
						<div class="progress">
							<div id="memUsagePie" class="progress-bar progress-bar2"
								role="progressbar" aria-valuenow="" aria-valuemin="0"
								aria-valuemax="100" style="width: 0%;"></div>
						</div>
						<ul>
							<li>已用: <i id="memUsedGB"></i>
							</li>
							<li>总量: <i id="memGB"></i>
							</li>
						</ul>
					</div>
				</div>
				<div class="col-lg-12 col-md-12 col-sm-12">
					<div id="jq-board-cp" class="jqc3-content">
						<span><i class="fa fa-ticket"></i></span>
						<ul>
							<li>磁盘使用率: <i id="diskUsage"></i>
							</li>
							<li>可用: <i id="diskFreeTB"></i>
							</li>
						</ul>
						<div class="progress">
							<div id="diskUsagePie" class="progress-bar progress-bar3"
								role="progressbar" aria-valuenow="" aria-valuemin="0"
								aria-valuemax="100" style="width: 0%; background-color: #94cb4c"></div>
						</div>
						<ul>
							<li>已用: <i id="diskUsedTB"></i>
							</li>
							<li>总量: <i id="diskTB"></i>
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
						<li>主机历史数据</li>
						<li role="presentation" class="active"><a
							href="#host-main-cpu-history" aria-controls="home" role="tab"
							data-toggle="tab">CPU使用率</a></li>
						<li role="presentation"><a href="#host-main-mem-history"
							aria-controls="profile" role="tab" data-toggle="tab">存储使用率</a></li>
						<li role="presentation"><a href="#host-main-disk-history"
							aria-controls="messages" role="tab" data-toggle="tab">磁盘IO速率</a></li>
						<li role="presentation"><a href="#host-main-net-history"
							aria-controls="settings" role="tab" data-toggle="tab">网络IO速率</a></li>
					</ul>

					<!-- Tab panes -->
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active"
							id="host-main-cpu-history">
							<div id="host-cpu-history"
								style="width: 480px; height: 220px; padding-top: 38px; float: left;"></div>
						</div>
						<div role="tabpanel" class="tab-pane" id="host-main-mem-history">
							<div id="host-mem-history"
								style="width: 480px; height: 220px; padding-top: 38px; float: left;"></div>
						</div>
						<div role="tabpanel" class="tab-pane" id="host-main-disk-history">
							<div id="host-disk-history"
								style="width: 480px; height: 220px; padding-top: 38px; float: left;"></div>
						</div>
						<div role="tabpanel" class="tab-pane" id="host-main-net-history">
							<div id="host-net-history"
								style="width: 480px; height: 220px; padding-top: 38px; float: left;"></div>
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
						<li role="presentation" class="active"><a href="#host-vm-cpu"
							aria-controls="home" role="tab" data-toggle="tab">CPU使用率</a></li>
						<li role="presentation"><a href="#host-vm-mem"
							aria-controls="profile" role="tab" data-toggle="tab">存储使用率</a></li>
						<li role="presentation"><a href="#host-vm-disk"
							aria-controls="messages" role="tab" data-toggle="tab">磁盘IO速率</a></li>
						<li role="presentation"><a href="#host-vm-net"
							aria-controls="settings" role="tab" data-toggle="tab">网络IO速率</a></li>
					</ul>

					<!-- Tab panes -->
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active" id="host-vm-cpu">
							<div id="vm-cpu-usage"
								style="width: 480px; height: 280px; padding-top: 38px; float: left;"></div>
						</div>
						<div role="tabpanel" class="tab-pane" id="host-vm-mem">
							<div id="vm-mem-usage"
								style="width: 480px; height: 280px; padding-top: 38px; float: left;"></div>
						</div>
						<div role="tabpanel" class="tab-pane" id="host-vm-disk">
							<div id="vm-disk-io"
								style="width: 480px; height: 280px; padding-top: 38px; float: left;"></div>
						</div>
						<div role="tabpanel" class="tab-pane" id="host-vm-net">
							<div id="vm-net-io"
								style="width: 480px; height: 280px; padding-top: 38px; float: left;"></div>
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
<script src="${ctx}/resources/js/monitor/monitorHost.js"></script>
<script type="text/javascript">
    $(function() {
        cloudmanager.monitorHost.initTopN();
        cloudmanager.monitorHost.updateTopN();
        cloudmanager.monitorHost.initTable();
        /* window.setInterval("cloudmanager.monitorHost.updateTopN()",20000);
        window.setInterval("cloudmanager.monitorHost.updateTable()",20000); */
    });
</script>
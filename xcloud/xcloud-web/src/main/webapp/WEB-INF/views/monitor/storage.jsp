<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ include file="/WEB-INF/views/includeFile.jsp"%>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header">
				<h1>存储</h1>
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
						<li>存储</li>
						<li role="presentation" class="active"><a href="#storage1"
							aria-controls="home" role="tab" data-toggle="tab">存储总容量(MB)</a></li>
						<li role="presentation"><a href="#storage2"
							aria-controls="profile" role="tab" data-toggle="tab">存储利用率(%)</a></li>
						<!-- <li role="presentation"><a href="#storage3"
                            aria-controls="messages" role="tab" data-toggle="tab">存储IOPS</a></li> -->
					</ul>

					<!-- Tab panes -->
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active" id="storage1">
							<div id="storage-disk-total"
								style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
						</div>
						<div role="tabpanel" class="tab-pane" id="storage2">
							<div id="storage-disk-usage"
								style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
						</div>
						<!-- <div role="tabpanel" class="tab-pane" id="storage3">
                            <div id="storage-disk-iops"
                                style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
                        </div> -->
					</div>
				</div>
			</div>
			<div class="col-lg-6 col-md-6 col-sm-12">
				<div class="jq-table-content">
					<!-- Nav tabs -->
					<ul class="nav nav-tabs" role="tablist">
						<li>存储</li>
						<li role="presentation" class="active"><a href="#storage4"
							aria-controls="home" role="tab" data-toggle="tab">物理机数量(台)</a></li>
						<li role="presentation"><a href="#storage5"
							aria-controls="profile" role="tab" data-toggle="tab">虚拟机数量(台)</a></li>
						<!-- <li role="presentation"><a href="#storage6"
                            aria-controls="messages" role="tab" data-toggle="tab">磁盘io速率</a></li> -->
					</ul>

					<!-- Tab panes -->
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active" id="storage4">
							<div id="storage-host-num"
								style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
						</div>
						<div role="tabpanel" class="tab-pane" id="storage5">
							<div id="storage-vm-num"
								style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
						</div>
						<!-- <div role="tabpanel" class="tab-pane" id="storage6">
                            <div id="storage-disk-io"
                                style="width: 72%; height: 350px; padding-top: 20px; float: left;"></div>
                        </div> -->
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="row overview">
		<div class="col-lg-12 col-md-12 col-sm-12 vm-table">
			<div class="gk-bottom">
				<div class="recent-gj">
					<h2>存储列表</h2>
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
		onclick="cloudmanager.monitorStorage.sideboardClose()"><i
		class="fa fa-remove"></i></a>
	<div class="jq-board">
		<h2>
			<span id="storageTagName"></span>
		</h2>
		<div class="row jq-board-content">
			<div class="col-lg-12 col-md-12 col-sm-12 jqc1">
				<h3>基本信息</h3>
				<ul>
					<li><span>存储名称: </span><span id="storageName"></span></li>
					<li><span>存储类型: </span><span id="storageType"></span></li>
				</ul>
			</div>
		</div>

		<div class="row jq-board-content">
			<div class="col-lg-4 col-md-4 col-sm-12 jqc2">
				<div id="storage-board-host"
					style="width: 210px; height: 200px; margin-top: -10px; margin-left: 100px;"></div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-12 jqc2">
				<div id="storage-board-vm"
					style="width: 210px; height: 200px; margin-top: -10px; margin-left: 150px;"></div>
			</div>
		</div>

		<div class="row jq-board-content" style="height: 110px;">
			<div class="jqc3" style="height: 100px;">
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
					<ul class="nav nav-tabs" role="tablist">
						<li>存储历史数据</li>
						<li role="presentation" class="active"><a
							href="#storage-main-disk-history" aria-controls="home" role="tab"
							data-toggle="tab">存储利用率</a></li>
						<li role="presentation"><a href="#storage-main-iops-history"
							aria-controls="profile" role="tab" data-toggle="tab">存储IOPS</a></li>
					</ul>

					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active"
							id="storage-main-disk-history">
							<div id="storage-disk-history"
								style="width: 480px; height: 220px; padding-top: 38px; float: left;"></div>
						</div>
						<div role="tabpanel" class="tab-pane" id="storage-main-iops-history">
                            <div id="disk-iops-history"
                                style="width: 480px; height: 220px; padding-top: 38px; float: left;"></div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row jq-board-content">
			<div class="jqc4">
				<div class="col-lg-12 col-md-12 col-sm-12">
					<ul class="nav nav-tabs" role="tablist">
						<li>主机概况</li>
						<li role="presentation" class="active"><a
							href="#storage-host-disk" aria-controls="home" role="tab"
							data-toggle="tab">存储利用率</a></li>
						<li role="presentation"><a href="#storage-host-iops"
							aria-controls="profile" role="tab" data-toggle="tab">存储IOPS</a></li>
					</ul>

					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active"
							id="storage-host-disk">
							<div id="host-disk-usage"
								style="width: 480px; height: 280px; padding-top: 38px; float: left;"></div>
						</div>
						<div role="tabpanel" class="tab-pane" id="storage-host-iops">
                            <div id="host-disk-iops"
                                style="width: 480px; height: 280px; padding-top: 38px; float: left;"></div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row jq-board-content">
			<div class="jqc4">
				<div class="col-lg-12 col-md-12 col-sm-12">
					<ul class="nav nav-tabs" role="tablist">
						<li>虚拟机概况</li>
						<li role="presentation" class="active"><a
							href="#storage-vm-disk" aria-controls="home" role="tab"
							data-toggle="tab">存储利用率</a></li>
						<li role="presentation"><a href="#storage-vm-iops"
							aria-controls="profile" role="tab" data-toggle="tab">存储IOPS</a></li>
					</ul>

					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active"
							id="storage-vm-disk">
							<div id="vm-disk-usage"
								style="width: 480px; height: 280px; padding-top: 38px; float: left;"></div>
						</div>
						<div role="tabpanel" class="tab-pane" id="storage-vm-iops">
                            <div id="vm-disk-iops"
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
<script src="${ctx}/resources/js/monitor/monitorStorage.js"></script>
<script type="text/javascript">
    $(function() {
        cloudmanager.monitorStorage.initTopN();
        cloudmanager.monitorStorage.updateTopN();
        cloudmanager.monitorStorage.initTable();
        /* window.setInterval("cloudmanager.monitorStorage.updateTopN()",20000);
        window.setInterval("cloudmanager.monitorStorage.updateTable()",20000); */
    });
</script>
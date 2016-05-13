<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ include file="/WEB-INF/views/includeFile.jsp"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header">
				<h1>利旧虚拟机</h1>
				<div class="small">利旧虚拟机为CloudVirtual环境中存在，但并非通过CloudManager创建的虚拟机</div>
			</div>
		</div>
	</div>

	<div class="row vm-table">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<table id="mandateVMTableId" class="easyui-datagrid"></table>
			<div id="mandateVMtb">
				<div class="row">
					<div class="col-lg-6 col-md-6 col-sm-12">
						<shiro:hasRole name="operation_manager">
							<a href="#" class="easyui-linkbutton"
								onclick="cloudmanager.mandateVMManagement.synchronizeManagedVM()">同步</a>
							<a href="#" class="easyui-linkbutton"
								onclick="cloudmanager.mandateVMManagement.assignManagedVM()">分配</a>
							<a href="#" class="easyui-linkbutton"
								onclick="cloudmanager.mandateVMManagement.recycleManagedVM()">回收</a>
						</shiro:hasRole>
						<a href="#" class="easyui-linkbutton"
							onclick="cloudmanager.mandateVMManagement.startManagedVM()">启动</a>
						<a href="#" class="easyui-linkbutton"
							onclick="cloudmanager.mandateVMManagement.stopManagedVM()">停止</a>
						<a href="#" class="easyui-linkbutton"
							onclick="cloudmanager.mandateVMManagement.restartManagedVM()">重启</a>
					</div>
					<div class="col-lg-6 col-md-6 col-sm-12">
						<div class="filter clearfix">
							<div class="filter-tool">
								<a role="button" href="#" class="btn btn-default"><i
									class="fa fa-external-link"></i></a> <a role="button" href="#"
									class="btn btn-default"><i class="fa fa-cog"></i></a>
							</div>
							<div class="filter-search">
								<input id="mandateVMSearchInputId" class="sugon-searchbox"
									style="width: 80%; height: 32px;"></input>
								<div id="tableSearch">
									<div data-options="name:'name'">名称</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="mandateVMDialogDivId" class="sugon-dialog"></div>
		</div>
	</div>
</div>

<!-- 虚拟机卡片 -->
<div class="jq-sideboard">
	<a class="sideboard-close" href="javascript:;"
		onclick="cloudmanager.mandateVMManagement.sideboardClose()"><i
		class="fa fa-remove"></i></a>
	<div class="jq-board">
		<h2>
			<span id="vmTagName"></span>
		</h2>
		<div class="row jq-board-content">
			<div class="col-sm-6 vm-tabs">
				<ul class="list-group">
					<li class="list-group-item list-group-item-heading">基本信息</li>
					<li class="list-group-item">
						<dl>
							<dt>虚拟机名称：</dt>
							<dd id="name"></dd>
						</dl>
					</li>
					<li class="list-group-item">
						<dl>
							<dt>所属组织：</dt>
							<dd id="orgName"></dd>
						</dl>
					</li>
					<li class="list-group-item">
						<dl>
							<dt>是否分配：</dt>
							<dd id="assignData"></dd>
						</dl>
					</li>
					<li class="list-group-item">
						<dl>
							<dt>CPU（核）：</dt>
							<dd id="cpuNumber"></dd>
						</dl>
					</li>
					<li class="list-group-item">
						<dl>
							<dt>IP：</dt>
							<dd id="ipAddr"></dd>
						</dl>
					</li>
				</ul>
			</div>
			<div class="col-sm-6">
				<div id="jq-board-cpu" class="media">
					<div class="media-left media-middle">
						<i class="fa fa-qrcode"></i>
					</div>
					<div class="media-body">
						<h4 class="media-heading">
							CPU使用率:<i id="cpuUsage"></i><small>可用:<i id="cpuFreeGHz"></i></small>
						</h4>
						<div class="progress">
							<div id="cpuUsagePie" class="progress-bar progress-bar1"
								role="progressbar" aria-valuenow="" aria-valuemin="0"
								aria-valuemax="100" style="width: 0%; background-color: #3facca"></div>
						</div>
						<h5 class="media-footing">
							<small>已用:<i id="cpuUsedGHz"></i></small><small>总量:<i
								id="cpuGHz"></i></small>
						</h5>
					</div>
				</div>
				<div id="jq-board-nc" class="media">
					<div class="media-left media-middle">
						<i class="fa fa-ticket"></i>
					</div>
					<div class="media-body">
						<h4 class="media-heading">
							内存使用率:<i id="memUsage"></i><small>可用:<i id="memFree"></i></small>
						</h4>
						<div class="progress">
							<div id="memUsagePie" class="progress-bar progress-bar2"
								role="progressbar" aria-valuenow="" aria-valuemin="0"
								aria-valuemax="100"
								style="width: 0%; background-color: #9d50a5;"></div>
						</div>
						<h5 class="media-footing">
							<small>已用:<i id="memUsed"></i></small><small>总量:<i
								id="mem"></i></small>
						</h5>
					</div>
				</div>
				<div id="jq-board-cp" class="media">
					<div class="media-left media-middle">
						<i class="fa fa-database"></i>
					</div>
					<div class="media-body">
						<h4 class="media-heading">
							磁盘使用率:<i id="diskUsage"></i><small>可用:<i id="diskFree"></i></small>
						</h4>
						<div class="progress">
							<div id="diskUsagePie" class="progress-bar progress-bar3"
								role="progressbar" aria-valuenow="" aria-valuemin="0"
								aria-valuemax="100" style="width: 0%; background-color: #5cb85c"></div>
						</div>
						<h5 class="media-footing">
							<small>已用:<i id="diskUsed"></i></small><small>总量:<i
								id="disk"></i></small>
						</h5>
					</div>
				</div>
			</div>
		</div>
		<div class="row jq-board-content">
			<div class="col-lg-12 col-md-12 col-sm-12">
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
					<li>历史数据</li>
					<li role="presentation" class="active"><a href="#jq21"
						aria-controls="home" role="tab" data-toggle="tab">CPU</a></li>
					<li role="presentation"><a href="#jq22"
						aria-controls="profile" role="tab" data-toggle="tab">内存</a></li>
					<li role="presentation"><a href="#jq23"
						aria-controls="messages" role="tab" data-toggle="tab">磁盘IO</a></li>
					<li role="presentation"><a href="#jq24"
						aria-controls="settings" role="tab" data-toggle="tab">网络IO</a></li>
				</ul>

				<!-- Tab panes -->
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane active" id="jq21">
						<div id="jq-history-cpu" style="width: 640px; height: 280px;"></div>
					</div>

					<div role="tabpanel" class="tab-pane" id="jq22">
						<div id="jq-history-mem" style="width: 640px; height: 280px;"></div>
					</div>

					<div role="tabpanel" class="tab-pane" id="jq23">
						<div id="jq-history-disk" style="width: 640px; height: 280px;"></div>
					</div>

					<div role="tabpanel" class="tab-pane" id="jq24">
						<div id="jq-history-net" style="width: 640px; height: 280px;"></div>
					</div>
				</div>
			</div>
		</div>

        <!-- 为了图表显示完整 -->
		<div style="height:100px"></div>

		<!--  <div class="row jq-board-content">
            <div class="col-lg-12 col-md-12 col-sm-12 jqc5">
                <h3>告警</h3>
            </div>
        </div>-->
	</div>
</div>

<script
	src="${ctx}/resources/js/managedVMManagement/mandateVMManagement.js"></script>
<script
	src="${ctx}/resources/js/managedVMManagement/orgMandateVMManagement.js"></script>

<script src="${ctx}/resources/lib/echarts/echarts.min.js"></script>
<script src="${ctx}/resources/js/monitor/monitorSummary.js"></script>

<shiro:hasRole name="operation_manager">
	<script type="text/javascript">
        $(function() {
            cloudmanager.mandateVMManagement.init();
        });

        // 定时刷新表格
        function refreshTable() {
            $("#mandateVMTableId").datagrid('reload');
            console.info("refresh");
        }

        function getStatus() {
            console.info("ready to refresh");
            var page = $('#mandateVMTableId').datagrid('getPager').data("pagination").options.pageNumber;
            var rows = $('#mandateVMTableId').datagrid('getPager').data("pagination").options.pageSize;
            //var orgId = ${orgId};
            //console.info(orgId);
            $.ajax({
                url : sugon.rootURL + '/oldvm/mandatedoldvms?page=' + page + '&rows=' + rows,
                type : "get",
                dataType : "json",
                contentType : "application/json;charset=UTF-8",
                success : function(data) {
                    console.info(data);
                    console.info(data.total);
                    console.info(data.rows.length);
                    for(var i = 0; i < data.rows.length; i ++) {
                        $("#mandateVMTableId").datagrid('updateRow', {
                            index : i,
                            row : {
                                powerStatus : data.rows[i].powerStatus
                            }
                        });
                    }
                }
            });
        }
        //window.setInterval(getStatus, 30000);
        
        function fun() {
            console.info("ready to leave");
            window.clearInterval(id);
        }

        //function fun1() {
        //alert('页面要关闭哦～');
        //}

        //window.onbeforeunload=fun1();
        //var id = window.setInterval(refreshTable, 10000);
    </script>
</shiro:hasRole>

<shiro:hasRole name="org_manager">
	<script type="text/javascript">
        $(function() {
            console.info("orgId是：" + "${orgId}");
            cloudmanager.orgMandateVMManagement.init("${orgId}");
        });

        // 定时刷新表格
        //function refreshTable(){
        //$("#mandateVMTableId").datagrid('reload');
        // }
        //window.setInterval(refreshTable, 60000);
    </script>
</shiro:hasRole>
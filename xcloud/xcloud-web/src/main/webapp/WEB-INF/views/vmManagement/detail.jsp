<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<style type="text/css">
.orgEdit {
	display: none;
}
</style>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header-detail">
				<a class="btn btn-link" href="#"
					onclick='cloudmanager.menu.openLink("/action/vm/vmIndex",this)'><i
					class="fa fa-chevron-left"></i>返回</a>
				<h2> 
					<%-- ${vmMonitor} --%>
					<i class="fa fa-desktop"></i> <span id="spanvmName">虚拟机${vmDetail.name}</span>
					<%-- ${vmDetail} --%>
					<!-- <a class="btn btn-default" href="#">刷新</a> -->
					<a
						class="btn btn-default" href="#" onclick="cloudmanager.vmManagement.startVm('${vmDetail.id}','${vmDetail.name}','${vmDetail.runStatus}')">启动</a> 
<a
						class="btn btn-default" href="#" onclick="cloudmanager.vmManagement.stopVm('${vmDetail.id}','${vmDetail.name}','${vmDetail.runStatus}')">停止</a> 
						<a class="btn btn-default" href="#"  onclick="cloudmanager.vmManagement.goToVNC('${vmDetail.internalId}','${vmDetail.runStatus}')">VNC访问</a>
						<shiro:hasRole name="operation_manager">
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle"
							data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							更多操作 <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
<!-- 							<li><a href="#" >加入网络</a></li> -->
							
							<input type="hidden" id="roleFlag" value="operation_manager"></input>
							<li><a href="#" onclick='cloudmanager.vmManagement.config("${vmDetail.id}","${vmDetail.name}","${vmDetail.vdcId}")'>配置</a></li>
							<li><a href="#" onclick='cloudmanager.vmManagement.updateVmName("${vmDetail.id}","${vmDetail.name}","${vmDetail.desciption}")'>编辑</a></li>
							<li><a href="#" onclick='cloudmanager.vmManagement.deleteVm("${vmDetail.id}","${vmDetail.name}")'>删除</a></li>
							
							
						
						</ul>
					</div>
					</shiro:hasRole>
				</h2>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="vm-tabs">
				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="active"><a href="#summary"
						aria-controls="summary" role="tab" data-toggle="tab"
						id="toSummaryLink">摘要</a></li>
					<li role="presentation"><a href="#disk" aria-controls="disk"
						role="tab" data-toggle="tab" id="toDiskLink">磁盘</a></li>
					<li role="presentation"><a href="#netcard"
						aria-controls="netcard" role="tab" data-toggle="tab"
						id="toNetcardLink">网络</a></li>
				</ul>
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane active" id="summary">
						<div class="row">
							<div class="col-lg-4 col-md-4 col-sm-12">
								<ul class="list-group">
									<li class="list-group-item list-group-item-heading">基本信息</li>
									<li class="list-group-item">
										<dl>
											<dt>名称:</dt>
											<dd id="ddvmName">${vmDetail.name}</dd>
										</dl>
									</li>
									<li class="list-group-item">
										<dl>
											<dt>所属vDC:</dt>
											<dd>${vmDetail.vdc.name}</dd>
										</dl>
									</li>
									<li class="list-group-item">
										<dl>
											<dt>所属组织:</dt>
											<dd>${vmDetail.organization.name}</dd>
										</dl>
									</li>
									<li class="list-group-item">
										<dl>
											<dt>状态:</dt>
											<dd>
												<i class="fa fa-circle text-success"></i>${vmDetail.vmStatus}
											</dd>
										</dl>
									</li>
									<li class="list-group-item">
										<dl>
											<dt>模板:</dt>
											<dd>${vmDetail.template.name}</dd>
										</dl>
									</li>
									<li class="list-group-item">
										<dl>
											<dt>存储池:</dt>
											<dd>${vmDetail.sPool.name}</dd>
										</dl>
									</li>
									<li class="list-group-item">
										<dl>
											<dt>拥有者:</dt>
											
											<dd>
											
											 <c:if test="${vmDetail.owner!='null'}">${vmDetail.owner.realname}</c:if> 
											</dd>
										</dl>
									</li>
									<li class="list-group-item">
										<dl>
											<dt>创建时间:</dt>
											<dd>${vmDetail.createTime}</dd>
										</dl>
									</li>
									<li class="list-group-item">
										<dl>
											<dt>描述:</dt>
											<dd id="ddDescription">${vmDetail.remarks}</dd>
										</dl>
									</li>
									<li class="list-group-item list-group-item-heading">配置信息</li>
																		<li class="list-group-item">
										<dl>
											<dt>内部名称:</dt>
											<dd id="ddCPU">${vmDetail.internalName} </dd>
										</dl>
									</li>
									<li class="list-group-item">
										<dl>
											<dt>CPU:</dt>
											<dd id="ddCPU">${vmDetail.vCpuNumer} 核</dd>
										</dl>
									</li>
									<li class="list-group-item">
										<dl>
											<dt>内存:</dt>
											<dd id="ddMemory">${vmDetail.vMemCapacity/1024} GB</dd>
										</dl>
									</li>
									<li class="list-group-item">
										<dl>
											<dt>操作系统:</dt>
											<dd>${vmDetail.template.os}</dd>
										</dl>
									</li>
									<li class="list-group-item">
										<dl>
											<dt>系统账号:</dt>
											<dd>${vmDetail.osUsername}</dd>
										</dl>
									</li>
									<li class="list-group-item">
										<dl>
											<dt>系统密码:</dt>
											<dd>${vmDetail.osPassword}</dd>
										</dl>
									</li>
<!-- 									<li class="list-group-item">
										<dl>
											<dt>位数:</dt>
											<dd>64</dd>
										</dl>
									</li>
									<li class="list-group-item">
										<dl>
											<dt>版本号:</dt>
											<dd>SP6</dd>
										</dl>
									</li>

									<li class="list-group-item">
										<dl>
											<dt>所属网络:</dt>
											<dd>基础子网1</dd>
										</dl>
									</li>
									<li class="list-group-item">
										<dl>
											<dt>IP:</dt>
											<dd>
												<p>10.0.33.36</p>
												<p>10.0.33.37</p>
												<p>10.0.33.38</p>
											</dd>
										</dl>
									</li>
									<li class="list-group-item">
										<dl>
											<dt>磁盘数量:</dt>
											<dd>3个</dd>
										</dl>
									</li> -->
								</ul>
							</div>
							<div class="col-lg-8 col-md-8 col-sm-12">
								<div class="well">
									<div class="row">
										<div class="col-sm-8">..</div>
										<div class="col-sm-4">
											<div class="list-group">
												<!-- <a class="list-group-item" href="#"><span class="label label-default">14</span>一般</a>  -->
												<a class="list-group-item" href="#"><span class="label label-info">3</span>警告</a> 
					<a
													class="list-group-item" href="#"><span
													class="label label-warning">8</span>严重</a> 
<!-- 													
													<a
													class="list-group-item" href="#"><span
													class="label label-danger">1</span>危险</a> -->
											</div>
										</div>
									</div>
								</div>
								<h5>监控信息</h5>
								<div class="panel panel-default">
									<div class="panel-body">
										<div id="cpuChart" style="height: 200px;"></div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-body">
										<div id="memoryChart" style="height: 200px;"></div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-body">
										<div id="netChart" style="height: 300px;"></div>
									</div>
								</div>
								<div class="panel panel-default">
									<div class="panel-body">
										<div id="diskChart" style="height: 300px;"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div role="tabpanel" class="tab-pane" id="disk">

						<!-- <div class="row">
							<div class="col-lg-6 col-md-6 col-sm-12">
								<a class="btn btn-default" href="#">创建磁盘</a> <a
									class="btn btn-default" href="#">挂载磁盘</a>
							</div>
							<div class="col-lg-6 col-md-6 col-sm-12">
								<div class="filter clearfix">
									<div class="filter-tool">
										<a class="btn btn-default" href="#" role="button"><i
											class="fa fa-refresh"></i></a> <a class="btn btn-default"
											href="#" role="button"><i class="fa fa-external-link"></i></a>
										<a class="btn btn-default" href="#" role="button"><i
											class="fa fa-cog"></i></a>
									</div>
									<div class="filter-search">
										<div class="input-group">
											<div class="input-group-btn">
												<button type="button"
													class="btn btn-default dropdown-toggle"
													data-toggle="dropdown" aria-haspopup="true"
													aria-expanded="false">
													全部类型 <span class="caret"></span>
												</button>
												<ul class="dropdown-menu">
													<li><a href="#">全部类型</a></li>
													<li><a href="#">名称</a></li>
													<li><a href="#">所属集群</a></li>
													<li><a href="#">所属资源池</a></li>
												</ul>
											</div>
											<input type="text" class="form-control" placeholder="请输入查询条件"
												aria-label="..."> <span class="input-group-btn">
													<button class="btn btn-default" type="button">
														<i class="fa fa-search"></i>
													</button>
											</span>
										</div>
									</div>
								</div>
							</div>
						</div -->
						<div class="row vm-table">
							<div class="col-lg-12 col-md-12 col-sm-12">
								<table id="diskTable" class="easyui-datagrid">

								</table>
								<div id="toolbar">
									<div class="row">
										<div class="col-lg-6 col-md-6 col-sm-12">
											<shiro:hasRole name="operation_manager">
												<a href="#" class="easyui-linkbutton"
													onclick="cloudmanager.vmManagement.addDisk('${vmDetail.vdcId}','${vmDetail.id}')">添加磁盘</a>
											</shiro:hasRole>

										</div>
<!-- 										<div class="col-lg-6 col-md-6 col-sm-12">
											<div class="filter clearfix">
												<div class="filter-tool">
													<a role="button" href="#" class="btn btn-default"><i
														class="fa fa-external-link"></i></a> <a role="button" href="#"
														class="btn btn-default"><i class="fa fa-cog"></i></a>
												</div>
												<div class="filter-search">
													<input id="diskSearchInput"
														class="sugon-searchbox included-tab"
														style="width: 80%; height: 32px;">
														<div id="tableSearch">
															<div data-options="name:'name'">名称</div>
														</div>
												</div>
											</div>
										</div> -->
									</div>
								</div>
							</div>
						</div>

					</div>
					<div role="tabpanel" class="tab-pane" id="netcard">
						<div class="row vm-table">
							<div class="col-lg-12 col-md-12 col-sm-12">
								<table id="netTable" class="easyui-datagrid">

								</table>
								<div id="toolbar2">
									<div class="row">
										<div class="col-lg-6 col-md-6 col-sm-12">
											<shiro:hasRole name="operation_manager">
												<a href="#" class="easyui-linkbutton"
													onclick="cloudmanager.vmManagement.addNetcard('${vmDetail.orgId}','${vmDetail.id}')">加入网络</a>
											</shiro:hasRole>
										</div>
<!-- 										<div class="col-lg-6 col-md-6 col-sm-12">
											<div class="filter clearfix">
												<div class="filter-tool">
													<a role="button" href="#" class="btn btn-default"><i
														class="fa fa-external-link"></i></a> <a role="button" href="#"
														class="btn btn-default"><i class="fa fa-cog"></i></a>
												</div>
												<div class="filter-search">
													<input id="netSearchInput"
														class="sugon-searchbox included-tab"
														style="width: 80%; height: 32px;">
														<div id="tableSearch2">
															<div data-options="name:'name'">名称</div>
														</div>
												</div>
											</div>
										</div> --> 
									</div>
								</div>
							</div>
						</div>
						<!-- <div class="row">
							<div class="col-lg-12 col-md-12 col-sm-12">
								<nav class="vm-pagination">
								<div class="dataTables_length">
									<label>显示 11 到 20 项，共 57 项 每页 <select
										name="DataTables_Table_0_length"
										aria-controls="DataTables_Table_0"
										class="form-control input-sm">
											<option value="10">10</option>
											<option value="25">25</option>
											<option value="50">50</option>
											<option value="100">100</option>
									</select> 条记录
									</label>
								</div>
								<ul class="pagination">
									<li class="disabled"><a aria-label="Previous" href="#"><span
											aria-hidden="true">«</span></a></li>
									<li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
									<li><a href="#">2</a></li>
									<li><a href="#">3</a></li>
									<li><a href="#">4</a></li>
									<li><a href="#">5</a></li>
									<li><a aria-label="Next" href="#"><span
											aria-hidden="true">»</span></a></li>
								</ul>
								</nav>
							</div>
						</div> -->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${ctx}/resources/lib/easyui/jquery.min.js"></script>
<script src="${ctx}/resources/lib/easyui/jquery.easyui.min.js"></script>
<script src="${ctx}/resources/lib/jquery/jquery.validate.min.js"></script>
<script type="text/javascript">
/* vmMonitor */
	$(function() {
		cloudmanager.vmManagement.initDetail('${vmDetail.id}','${vmMonitor}','${vmDetail.internalId}');
	});
</script>
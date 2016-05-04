<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%-- <%@ include file="/WEB-INF/views/includeFile.jsp"%> --%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<div class="container-fluid">
			<div class="row">
				<div class="col-lg-12 col-md-12 col-sm-12">
					<div class="page-header">
						<h1>虚拟机</h1>
						<div class="small"></div>
					</div>
				</div>
			</div>
	
	<div class="row vm-table">
		<div class="col-lg-12 col-md-12 col-sm-12">
<!-- 			<div id="toolbar" region="north" border="false"
				style="border-bottom: 1px solid #ddd; height: 32px; padding: 2px 5px; background: #fafafa;">
				<div style="float: left;">
					<a href="#" class="easyui-linkbutton" plain="true" icon="icon-add"
						onclick="cloudmanager.orgManagement.addOrg()">新增</a>
				</div>
				<div class="datagrid-btn-separator"></div>

				<div style="float: left;">
					<a href="#" class="easyui-linkbutton" plain="true" icon="icon-save"
						onclick="">编辑</a>
				</div>

				<div class="datagrid-btn-separator"></div>

				<div style="float: left;">
					<a href="#" class="easyui-linkbutton" plain="true"
						icon="icon-remove">删除</a>
				</div>

				<div id="tb" style="float: right;">
					<input id="searchName" class="easyui-searchbox" prompt='请输入姓名或拼音查询'
						style="width: 130px; vertical-align: middle;"></input>
					<button class="btn btn-primary"
						onclick="cloudmanager.orgManagement.doSearch()">搜索</button>
				</div>

			</div> -->
			<table id="vmTable" class="easyui-datagrid" >

			</table>
			<div id="toolbar">
				<div class="row">
					<div class="col-lg-6 col-md-6 col-sm-12">
						<shiro:hasRole name="operation_manager">
								<a href="#" class="easyui-linkbutton" onclick="cloudmanager.vmManagement.createVm()">创建</a>			
								<input type="hidden" id="roleFlag" value="operation_manager"></input>
						</shiro:hasRole>
						<shiro:hasRole name="org_manager">
						<input type="hidden" id="roleFlag" value="org_manager"></input>
								<!-- <a href="#" class="easyui-linkbutton" onclick="cloudmanager.vmManagement.distributeVm()">分配</a>
								<a href="#" class="easyui-linkbutton" onclick="cloudmanager.vmManagement.revokeVm()">回收</a> -->
						</shiro:hasRole>	
					</div>
					<div class="col-lg-6 col-md-6 col-sm-12">
						<div class="filter clearfix">
							<div class="filter-tool">
								<a role="button" href="#" class="btn btn-default"><i class="fa fa-external-link"></i></a>
								<a role="button" href="#" class="btn btn-default"><i class="fa fa-cog"></i></a>
							</div>
							<div class="filter-search">
								<input id="vmSearchInput" class="sugon-searchbox" style="width:80%; height:32px;">
								<div id="tableSearch">
									<div data-options="name:'name'">名称</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="sugonDialog" class="sugon-dialog"></div>
		</div>
	</div>
</div>
<div id="vncPage"></div>
<!-- <div id="wmksContainer" style="position:absolute;width:100%;height:100%"></div> -->
  <script src="${ctx}/resources/lib/easyui/jquery.min.js"></script> 
<%--  <script src="${ctx}/resources/lib/vnc/jquery-1.8.3.min.js"></script>  --%>

<%-- <script src="${ctx}/resources/lib/vnc/wmks.js"></script> --%>
<script src="${ctx}/resources/lib/easyui/jquery.easyui.min.js"></script>
<%--  <script src="${ctx}/resources/lib/vnc/jquery-ui-1.8.16.min.js"></script> --%>
<script src="${ctx}/resources/lib/jquery/jquery.validate.min.js"></script>
<script src="${ctx}/resources/js/vmManagement/vmManagement.js"></script> 
<script type="text/javascript">
 	$(function() {
		cloudmanager.vmManagement.initList();
	}); 
</script>
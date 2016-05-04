<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%-- <%@ include file="/WEB-INF/views/includeFile.jsp"%> --%>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header">
				<h1>用户管理</h1>
				<div class="small">用户管理简介</div>
			</div>
		</div>
	</div>
	<div class="row vm-table">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<table id="userTableId" class="easyui-datagrid">
			</table>
			<div id="usertb">
				<div class="row">
					<div class="col-lg-6 col-md-6 col-sm-12">
						<shiro:hasRole name="org_manager">
							<a href="#" class="easyui-linkbutton" onclick="cloudmanager.userMgmt.openUserCreatePage()">创建</a>
						</shiro:hasRole>
					</div>
					<div class="col-lg-6 col-md-6 col-sm-12">
						<div class="filter clearfix">
							<div class="filter-tool">
								<a role="button" href="#" class="btn btn-default">
									<i class="fa fa-external-link"></i>
								</a>
								<a role="button" href="#" class="btn btn-default"> <i class="fa fa-cog"></i></a>
							</div>
							<div class="filter-search">
								<input id="userSearchInputId" class="sugon-searchbox" style="width: 80%; height: 32px;">
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
<script src="${ctx}/resources/lib/easyui/jquery.min.js"></script>
<script src="${ctx}/resources/lib/easyui/jquery.easyui.min.js"></script>
<script src="${ctx}/resources/lib/jquery/jquery.validate.min.js"></script>
<script src="${ctx}/resources/lib/jquery/messages_zh.min.js"></script>
<script src="${ctx}/resources/js/user/user.js"></script>
<script type="text/javascript">
	$(function() {
		cloudmanager.userMgmt.init();
	});
</script>
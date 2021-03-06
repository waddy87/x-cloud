<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header">
				<h1>资源申请</h1>
				<div class="small">资源申请简介</div>
			</div>
		</div>
	</div>
	<div class="row vm-table">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<table id="applicationTableId" class="easyui-datagrid">
			</table>
			<div id="applicationtb">
				<div class="row">
					<div class="col-lg-6 col-md-6 col-sm-12">
							<a href="#" class="easyui-linkbutton" onclick="cloudmanager.applicationMgmt.openApplyCreatePage()">创建</a>
					</div>
					<div class="col-lg-6 col-md-6 col-sm-12">
						<div class="filter clearfix">
							<div class="filter-tool">
								<a role="button" href="#" class="btn btn-default">
									<i class="fa fa-external-link"></i>
								</a>
								<a role="button" href="#" class="btn btn-default">
									<i class="fa fa-cog"></i>
								</a>
							</div>
							<div class="filter-search">
								<input id="applicationSearchInputId" class="sugon-searchbox"  style="width:80%; height:32px;">
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
<script src="${ctx}/resources/js/application/application.js"></script> 
<script type="text/javascript">
$(function() {
	cloudmanager.applicationMgmt.initOrgM();
});
</script>
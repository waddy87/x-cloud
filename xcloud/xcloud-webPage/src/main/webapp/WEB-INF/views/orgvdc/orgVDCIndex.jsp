<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ include file="/WEB-INF/views/includeFile.jsp"%>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header">
				<h1>组织VDC</h1>
				<div class="small">组织VDC简介</div>
			</div>
		</div>
	</div>
	<div class="row vm-table">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<table id="orgVDCTableId" class="easyui-datagrid">
			</table>
			<div id="orgVDCtb">
				<div class="row">
					<div class="col-lg-6 col-md-6 col-sm-12">
						<a href="#" class="easyui-linkbutton" onclick="cloudmanager.orgVDC.addOrgVDC()">创建</a>
					</div>
					<div class="col-lg-6 col-md-6 col-sm-12">
						<div class="filter clearfix">
							<div class="filter-tool">
								<a role="button" href="#" class="btn btn-default"><i class="fa fa-external-link"></i></a>
								<a role="button" href="#" class="btn btn-default"><i class="fa fa-cog"></i></a>
							</div>
							<div class="filter-search">
								<input class="easyui-searchbox" id="orgVDCSearchInputId" data-options="prompt:'请输入查询内容',menu:'#tableSearch',searcher:cloudmanager.orgVDC.doSearch" style="width:100%; height:32px;">
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="orgVDCCreateDivId"></div>
		</div>
	</div>
</div>
<div id="tableSearch" style="display:none;">
	<div data-options="name:'name'">名称</div>
</div>
<script src="${ctx}/resources/js/vdc/orgVDC.js"></script> 
<script type="text/javascript">
$(function() {
	cloudmanager.orgVDC.init();
});
</script>
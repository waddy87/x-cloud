<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ include file="/WEB-INF/views/includeFile.jsp"%>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header">
				<h1>任务管理</h1>
				<div class="small">任务管理任务管理任务管理任务管理任务管理任务管理任务管理</div>
			</div>
		</div>
	</div>
	<div id="pContainer" style="left: -500px; position: absolute;"><div id="p" class="easyui-progressbar" style="width:200px;"></div></div>
	<div class="row vm-table">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<table id="taskTableId" class="easyui-datagrid">
			</table>
			<div id="tasktb">
				<div class="row">
					<div class="col-lg-3 col-md-3 col-sm-12">
						<div class="input-group date form_datetime">
							<span class="input-group-addon">开始时间</span>  
							<input type="text" id="startTime" name="startTime" class="form-control"  onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'2020-10-01',skin:'twoer'})" />
						
						</div>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-12">
						<div class="input-group date form_datetime">
							<span class="input-group-addon">结束时间</span> 
							<input type="text" id="endTime" name="endTime" class="form-control" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'2020-10-01',skin:'twoer'})" />
						</div>
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
								<input id="taskSearchInputId" class="sugon-searchbox"  style="width:80%; height:32px;">
								<div id="tableSearch">
									<div data-options="name:'resourceName'">资源名称</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${ctx}/resources/lib/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/resources/js/task/task.js"></script>
<script type="text/javascript">
$(function() {
	cloudmanager.task.init();
});
</script>
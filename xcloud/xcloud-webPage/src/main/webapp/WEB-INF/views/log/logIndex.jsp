<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ include file="/WEB-INF/views/includeFile.jsp"%>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header">
				<h1>日志管理</h1>
				<div class="small">日志管理日志管理日志管理日志管理日志管理</div>
			</div>
		</div>
	</div>
	<div class="row vm-table">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<table id="logIndexTableId" class="easyui-datagrid">
			</table>
			<div id="logIndextb">
				<div class="row">
					<div class="col-lg-3 col-md-3 col-sm-12">
						<div class="input-group date form_datetime">
							<span class="input-group-addon">开始时间</span> <input type="text"
								id="startTime" name="startTime" class="form-control"
								onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')||\'2020-10-01 00:00:00\'}',skin:'twoer'})" />
						</div>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-12">
						<div class="input-group date form_datetime">
							<span class="input-group-addon">结束时间</span> <input type="text"
								id="endTime" name="endTime" class="form-control"
								onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'2020-10-01 00:00:00',skin:'twoer'})" />
						</div>
					</div>
					<div class="col-lg-6 col-md-6 col-sm-12">
						<div class="filter clearfix">
							<div class="filter-tool">
								<a role="button" href="#" class="btn btn-default"> <i
									class="fa fa-external-link"></i>
								</a> <a role="button" href="#" class="btn btn-default"> <i
									class="fa fa-cog"></i>
								</a>
							</div>
							<shiro:hasRole name="operation_manager">
							<div class="filter-search">
								<input id="logIndexSearchInputId" class="sugon-searchbox"
									style="width: 80%; height: 32px;">
									<div id="logIndextableSearch">
											<div data-options="name:'user'">操作人</div>
									</div>
							</div>
							</shiro:hasRole>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="${ctx}/resources/lib/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/resources/js/log/log.js"></script>
<script type="text/javascript">
	$(function() {
		cloudmanager.log.init();
	});
</script>
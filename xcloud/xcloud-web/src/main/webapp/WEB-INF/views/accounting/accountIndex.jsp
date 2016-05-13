<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ include file="/WEB-INF/views/includeFile.jsp"%>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header">
				<h1>记账报表</h1>
				<div class="small">记账报表记账报表记账报表记账报表记账报表记账报表记账报表</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="vm-account-view">
				<dl>
					<dt>
						<i class="fa fa-desktop"></i>
						<h2>虚拟机</h2>
					</dt>
					<dd>
						<p><span id="sumVmHost"></span>台</p>
					</dd>
				</dl>
				<dl>
					<dt>
						<i class="fa fa-qrcode"></i>
						<h2>CPU</h2>
					</dt>
					<dd>
						<p><span id="sumCpu"></span>核*天</p>
					</dd>
				</dl>
				<dl>
					<dt>
						<i class="fa fa-ticket"></i>
						<h2>内存</h2>
					</dt>
					<dd>
						<p><span id="sumMemory"></span>GB*天</p>
					</dd>
				</dl>
				<dl>
					<dt>
						<i class="fa fa-database"></i>
						<h2>磁盘</h2>
					</dt>
					<dd>
						<p><span id="sumDiskSize"></span>GB</p>
					</dd>
				</dl>
			</div>
		</div>
	</div>
	<div class="row vm-table">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<table id="accountIndexTableId" class="easyui-datagrid">
			</table>
			<div id="accountIndextb">
				<div class="row">
					<div class="col-lg-3 col-md-3 col-sm-12">
						<div class="input-group date form_datetime">
							<span class="input-group-addon">开始时间</span> <input type="text" id="startTime" name="startTime" class="form-control"  onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')||\'2020-10-01\'}',skin:'twoer'})" />
						</div>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-12">
						<div class="input-group date form_datetime">
							<span class="input-group-addon">结束时间</span>  <input type="text" id="endTime" name="endTime" class="form-control" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'2020-10-01',skin:'twoer'})" />
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
								<input id="accountIndexSearchInputId" class="sugon-searchbox"  style="width:80%; height:32px;">
								<div id="accountIndextableSearch">
									<div data-options="name:'user'">组织名称</div>
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
<script src="${ctx}/resources/js/accounting/account.js"></script>
<script type="text/javascript">
$(function() {
	cloudmanager.account.init();
});
</script>
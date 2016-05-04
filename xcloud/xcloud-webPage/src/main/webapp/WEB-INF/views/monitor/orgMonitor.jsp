<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ include file="/WEB-INF/views/includeFile.jsp"%>
<div class="container-fluid dashboard">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header">
				<h1>资源视图TOP5</h1>
				<div class="small">虚拟化环境是对计算、存储和网络等资源进行虚拟化的软件，例如FusionCompute。 系统中所使用和管理的虚拟资源都来源于虚拟化环境中的资源。
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
		</div>
	</div>
	<div class="row">
		<div class="col-lg-6 col-md-6 col-sm-12">
			<div class="vm-box">
				<h3>CPU使用率(%)</h3>
				<div id="jqvm-cpu-usage" style="height:200px;"></div>
			</div>
		</div>
		<div class="col-lg-6 col-md-6 col-sm-12">
			<div class="vm-box">
				<h3>内存使用率(%)</h3>
				<div id="jqvm-mem-usage" style="height:200px;"></div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-6 col-md-6 col-sm-12">
			<div class="vm-box">
				<h3>磁盘IO(KB/s)</h3>
				<div id="jqvm-disk-io" style="height:200px;"></div>
			</div>
		</div>
		<div class="col-lg-6 col-md-6 col-sm-12">
			<div class="vm-box">
				<h3>网络IO(KB/s)</h3>
				<div id="jqvm-net-io" style="height:200px;"></div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="vm-box">
				<h3>磁盘使用率(%)</h3>
				<div id="jqvm-disk-usage" style="height:200px;"></div>
			</div>
		</div>
	</div>
</div>
<script src="${ctx}/resources/lib/echarts/echarts.min.js"></script>
<script src="${ctx}/resources/js/monitor/monitorSummary.js"></script>
<script src="${ctx}/resources/js/monitor/orgMonitor.js"></script>
<script type="text/javascript">
$(function() {
    cloudmanager.orgMonitor.initTopN();
    cloudmanager.orgMonitor.updateTopN();
});
</script>
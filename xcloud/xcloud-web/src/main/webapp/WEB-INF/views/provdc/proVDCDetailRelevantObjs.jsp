<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div role="tabpanel" class="tab-pane active" id="objects">
	<div class="vm-tabs-child">
		<ul class="nav nav-tabs" role="tablist">
			<!-- 
			<li role="presentation" class="active">
				<a href="#home" aria-controls="home" role="tab" data-toggle="tab">组织vDC</a>
			</li>
			 -->
			<li role="presentation" class="active">
				<a href="#vms" aria-controls="vms" role="tab" data-toggle="tab">虚拟机</a>
			</li>
		</ul>
		<div class="row vm-table">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<table id="proVDCVmTableId" class="easyui-datagrid">
			</table>
			<div id="proVDCVmtb">
				<div class="row">
					<div class="col-lg-6 col-md-6 col-sm-12">
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
								<input id="proVDCVmSearchInputId" class="sugon-searchbox"  style="width:80%; height:32px;">
								<div id="proVDCVmTableSearch">
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
</div>
<script src="${ctx}/resources/lib/easyui/jquery.min.js"></script>
<script src="${ctx}/resources/lib/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
$(function() {
	cloudmanager.proVDCDetail.init('${pVDCId}');
});
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header">
			</div>
		</div>
	</div>
	<div class="row vm-table">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<table id="userTableId" class="easyui-datagrid">
			</table>
			<!-- <div id="usertb">
				<div class="row">
					<div class="col-lg-6 col-md-6 col-sm-12">
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
			</div> -->
		</div>
	</div>
</div>
<%-- ${netPoolList}
 [{"isAvl":true,"netPoolId":"network-1041","orgName":"ssssstttasdasd","synDate":{"date":5,"hours":22,"seconds":17,"month":3,"timezoneOffset":-480,"year":116,"minutes":10,"time":1459865417000,"day":2},"configId":"ff80808153cc5fad0153cc78493700c7","netName":"VM Network 3","subNet":"255.255.255.0","dns":"8.8.8.8","gateway":"10.0.33.254","orgId":"8a80f349540f04ed01540f0f32270009","vlanNO":0}]  --%> 
<%-- <script type="text/javascript" src="${ctx}/resources/js/sg.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/sg.slider.js"></script> --%>
<script type="text/javascript">
	$(function() {
		  cloudmanager.vmManagement.initDistributeVm();  

	});
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<style type="text/css">
.orgEdit {
	display: none;
}
</style>
<div class="container-fluid">
	<form class="form-horizontal" name="updateOrgForm" id="updateOrgForm">
		<div class="form-title">
			组织基本信息
			<button class="btn btn-default btn-xs" type="button" id="editBtn" style="float:right">编辑</button>
		</div>
		<div class="form-group">
			<label class="col-sm-4 control-label" for="inputText01">组织名称</label>
			<div class="col-sm-7">
				<p class="form-control-static" ng-show="!editFlag">${orgDetail.name}</p>
				<input type="text" class="form-control orgEdit" name="orgName" id="orgName"
					value="${orgDetail.name}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-4 control-label" for="inputText02">所属单位</label>
			<div class="col-sm-7">
				<p class="form-control-static" ng-show="!editFlag">${orgDetail.address}</p>
				<input type="text" class="form-control orgEdit" name="address" id="address"
					value="${orgDetail.address}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-4 control-label" for="inputName01">备注</label>
			<div class="col-sm-7">
				<p class="form-control-static">${orgDetail.remarks}</p>
				<input type="text" ng-show="editFlag" class="form-control orgEdit"
					name="remarks" id="remarks" value="${orgDetail.remarks}">
			</div>
		</div>
		<div class="form-title">
			管理员基本信息
		</div>
				<div class="form-group">
			<label class="col-sm-4 control-label" for="inputName01">姓名</label>
			<div class="col-sm-7" style="margin-top: 7px">
				<span class="form-control-static">${orgUser.realname}</span>
<%-- 				<input type="text" ng-show="editFlag" class="form-control orgEdit"
					name="remarks" id="remarks" value="${orgDetail.remarks}"> --%>
			</div>
		</div>
						<div class="form-group">
			<label class="col-sm-4 control-label" for="inputName01">邮箱</label>
			<div class="col-sm-7" style="margin-top: 7px">
				<span class="form-control-static">${orgUser.email}</span>
<%-- 				<input type="text" ng-show="editFlag" class="form-control orgEdit"
					name="remarks" id="remarks" value="${orgDetail.remarks}"> --%>
			</div>
		</div>
						<div class="form-group">
			<label class="col-sm-4 control-label" for="inputName01">电话</label>
			<div class="col-sm-7" style="margin-top: 7px" >
				<span class="form-control-static">${orgUser.telephone}</span>
<%-- 				<input type="text" ng-show="editFlag" class="form-control orgEdit"
					name="remarks" id="remarks" value="${orgDetail.remarks}"> --%>
			</div>
		</div>
		<!-- 				<div class="form-group">
			<label class="col-sm-4 control-label" for="inputPassword01">密码</label>
			<div class="col-sm-7">
				<a data-target="#myModal-03-02" data-toggle="modal" role="button" class="btn btn-default" href="#">重置密码</a>
			</div>
		</div> -->
		<div class="modal-footer orgEdit" id="btnDiv">
			<button type="submit" class="btn btn-primary">确认</button>
			<button type="button" class="btn btn-default"
				onclick="cloudmanager.orgManagement.cancelAdd()">取消</button>
		</div>
	</form>
</div>
<script type="text/javascript">
	$(function() {
		cloudmanager.orgManagement.initDetail("${orgDetail.id}");
	});
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div class="modal-body">
		<form class="form-horizontal" name="createApplicationForm" id="createApplicationForm" >
			<div class="form-group">
				<label for="applyName" class="col-sm-4 control-label"><font class="star">* </font>申请单名称</label>
				<div class="col-sm-5">
					<input type="text" class="form-control"  name="applyName" id="applyName">
					<input type="hidden"   name="status" id="status">
				</div>
				<div class="col-sm-3"></div>
			</div>
			<div class="form-group">
				<label for="remark" class="col-sm-4 control-label"><font class="star">* </font>备注</label>
			    <div class="col-sm-5">
			    	<textarea id="remark" name="remark"  class="form-control required" rows="3"></textarea>
			    </div>
			    <div class="col-sm-3"></div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label" for="applicationType"><font class="star">* </font>申请单类型 </label> 
				<div class="col-sm-5">
					<select class="form-control" id="applicationType" name="applicationType">
						<option value="">请选择</option>
						<c:forEach items="${applicationTypeMap}" var="applicationTypeInfo" varStatus="status">
							<option value="${applicationTypeInfo.value}">${applicationTypeInfo.key}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-3"></div>
			</div>
		</form>
		<div class="form-horizontal" id="applicationInfo"></div>
</div>
<script type="text/javascript">
	$(function() {
		 cloudmanager.applicationMgmt.initCreateApplication(); 
	});
</script>
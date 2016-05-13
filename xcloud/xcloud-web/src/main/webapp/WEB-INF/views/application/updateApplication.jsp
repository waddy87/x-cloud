<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div>
	<div >
		<div class="modal-body">
			<form class="form-horizontal" name="updateApplicationForm" id="updateApplicationForm" >
				<div class="form-group">
					<label for="applyName" class="col-sm-4 control-label">申请单名称</label>
					<div class="col-sm-5">
						<input type="text" class="form-control"  name="applyName" id="applyName" value="${application.name }">
						<input type="hidden"   name="status" id="status">
						<input type="hidden"   name="applyId" id="applyId" value="${application.id }">
					</div>
					<div class="col-sm-3"></div>
				</div>
				<div class="form-group">
					<label for="remark" class="col-sm-4 control-label">备注</label>
				    <div class="col-sm-5">
				    	<textarea id="remark" name="remark"  class="form-control required" rows="3">${application.remark }</textarea>
				    </div>
				    <div class="col-sm-3"></div>
				</div>
			</form>		
		</div>
	</div>

</div>
<script type="text/javascript">
	$(function() {
		 cloudmanager.applicationMgmt.initUpdateApplication(); 
	});
</script>
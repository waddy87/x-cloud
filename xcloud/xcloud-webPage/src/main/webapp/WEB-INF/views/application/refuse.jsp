<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div>
	<div >
		<div class="modal-body">
			<form class="form-horizontal" name="refuseForm" id="refuseForm" >
				<div class="form-group">
					<label for="refuse" class="col-sm-4 control-label"><font class="star">* </font>拒绝原因</label>
				    <div class="col-sm-5">
				    	<textarea id="refusalReason" name="refusalReason"  class="form-control required" rows="3"></textarea>
				    </div>
				    <div class="col-sm-3"></div>
				</div>
			</form>		
		</div>
	</div>

</div>
<script type="text/javascript">
	$(function() {
		 cloudmanager.applicationMgmt.initRefuse('${applyId}'); 
	});
</script>
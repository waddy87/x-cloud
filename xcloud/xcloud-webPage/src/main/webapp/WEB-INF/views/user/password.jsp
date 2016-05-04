<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div>
	<div >
		<div class="modal-body">
			<form class="form-horizontal" name="updateUserPasswordForm" id="updateUserPasswordForm" >
				<div class="form-group">
					<label for="oldPassword" class="col-sm-4 control-label"><font class="star">* </font>旧密码</label>
					<div class="col-sm-5">
						<input type="password" class="form-control"  name="oldPassword" id="oldPassword" >
					</div>
					<div class="col-sm-3"></div>
				</div>
				<div class="form-group">
					<label for="newPassword" class="col-sm-4 control-label"><font class="star">* </font>新密码</label>
					<div class="col-sm-5">
						<input type="password" class="form-control"  name="newPassword" id="newPassword" >
					</div>
					<div class="col-sm-3"></div>
				</div>
				<div class="form-group">
					<label for="confirmNewPassword" class="col-sm-4 control-label"><font class="star">* </font>确认新密码</label>
					<div class="col-sm-5">
						<input type="password"  name="confirmNewPassword" class="form-control"
							id="confirmNewPassword" > 
					</div>
					<div class="col-sm-3"></div>
				</div>
			</form>		
		</div>
	</div>

</div>
<script type="text/javascript">
	$(function() {
		 cloudmanager.userPassword.initUpdatePassword(); 
	});
</script>
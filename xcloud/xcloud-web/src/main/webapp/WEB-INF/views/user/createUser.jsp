<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div>
	<div >
		<div class="modal-body">
			<form class="form-horizontal" name="createUserForm" id="createUserForm" >
				<div class="form-group">
					<label for="username" class="col-sm-4 control-label"><font class="star">* </font>用户名</label>
					<div class="col-sm-5">
						<input type="text" class="form-control"  name="username" id="username">
					</div>
					<div class="col-sm-3"></div>
				</div>
				<div class="form-group">
					<label for="password" class="col-sm-4 control-label"><font class="star">* </font>密码</label>
					<div class="col-sm-5">
						<input type="password" class="form-control"  name="password" id="password">
					</div>
					<div class="col-sm-3"></div>
				</div>
				<div class="form-group">
					<label for="confirmPassword" class="col-sm-4 control-label"><font class="star">* </font>确认密码</label>
					<div class="col-sm-5">
						<input type="password" class="form-control"  name="confirmPassword" id="confirmPassword">
					</div>
					<div class="col-sm-3"></div>
				</div>
				<div class="form-group">
					<label for="realname" class="col-sm-4 control-label"><font class="star">* </font>真实姓名</label>
					<div class="col-sm-5">
						<input type="text" class="form-control"  name="realname" id="realname" >
					</div>
					<div class="col-sm-3"></div>
				</div>
				<div class="form-group">
					<label for="telephone" class="col-sm-4 control-label"><font class="star">* </font>移动电话</label>
					<div class="col-sm-5">
						<input type="text" class="form-control"  name="telephone" id="telephone">
					</div>
					<div class="col-sm-3"></div>
				</div>
				<div class="form-group">
					<label for="email" class="col-sm-4 control-label"><font class="star">* </font>电子邮箱</label>
					<div class="col-sm-5">
						<input type="email"  name="email" class="form-control"
							id="email" value="${user.email }"> 
					</div>
					<div class="col-sm-3"></div>
				</div>
			</form>		
		</div>
	</div>

</div>
<script type="text/javascript">
	$(function() {
		 cloudmanager.userMgmt.initCreateUser(); 
	});
</script>
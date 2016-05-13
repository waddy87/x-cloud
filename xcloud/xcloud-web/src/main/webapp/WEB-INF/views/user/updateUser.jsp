<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div>
	<div >
		<div class="modal-body">
			<form class="form-horizontal" name="updateUserForm" id="updateUserForm" >
				<div class="form-group">
					<label  class="col-sm-4 control-label">用户名</label>
					<input type="hidden" name="userId" id="userId" value="${user.id }">
					<div class="col-sm-4">
						<p class="form-control-static">${user.username }</p>
					</div>
					<div class="col-sm-4"></div>
				</div>
				<div class="form-group">
					<label  class="col-sm-4 control-label">所属组织名称</label>
					<div class="col-sm-4">
						<p class="form-control-static">${user.orgName }</p>
					</div>
					<div class="col-sm-4"></div>
				</div>
				<div class="form-group">
					<label for="realname" class="col-sm-4 control-label"><font class="star">* </font>真实姓名</label>
					<div class="col-sm-4">
						<input type="text" class="form-control"  name="realname" id="realname" value="${user.realname }">
					</div>
					<div class="col-sm-4"></div>
				</div>
				<div class="form-group">
					<label for="telephone" class="col-sm-4 control-label"><font class="star">* </font>移动电话</label>
					<div class="col-sm-4">
						<input type="text" class="form-control"  name="telephone" id="telephone" value="${user.telephone }">
					</div>
					<div class="col-sm-4"></div>
				</div>
				<div class="form-group">
					<label for="email" class="col-sm-4 control-label"><font class="star">* </font>电子邮箱</label>
					<div class="col-sm-4">
						<input type="email" required name="email" class="form-control"
							id="email" value="${user.email }"> 
					</div>
					<div class="col-sm-4"></div>
				</div>
			</form>		
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		 cloudmanager.userMgmt.initUpdateUser(); 
	});
</script>
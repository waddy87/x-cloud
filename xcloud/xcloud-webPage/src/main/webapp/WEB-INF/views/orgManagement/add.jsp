<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div class="container-fluid">
	<form class="form-horizontal" name="addOrgForm" id="addOrgForm" >
	    <div class="form-title">
			组织基本信息
		</div>
		<div class="form-group">
			<label for="orgName" class="col-sm-4 control-label">组织名称</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" name="orgName" id="orgName" value="大兵哥组织"><span style="display: inline-block; position: absolute; margin-top: -20px; margin-left: 220px; color: red;">*</span> 
			
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-4 control-label" for="address">所属单位</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" name="address" id="address" value="中科睿光"> <span style="display: inline-block; position: absolute; margin-top: -20px; margin-left: 220px; color: red;">*</span>
			</div>
		</div>
		<div class="form-group">
			<label for="remarks" class="col-sm-4 control-label">组织备注</label>
			<div class="col-sm-5">
				<input type="text" class="form-control"  name="remarks" id="remarks" value="牛逼的云计算公司"> 
			</div>
		</div>
		<div class="form-title">
			组织管理员基本信息
		</div>
		<div class="form-group">
			<label for="name" class="col-sm-4 control-label">姓名</label>
			<div class="col-sm-5">
				<input type="text" class="form-control"   name="name"  id="name" value="大兵哥"><span style="display: inline-block; position: absolute; margin-top: -20px; margin-left: 220px; color: red;">*</span>
			</div>
		</div>
		<div class="form-group">
			<label for="phone" class="col-sm-4 control-label">联系电话</label>
			<div class="col-sm-5">
				<input type="text" class="form-control"  name="phone" id="phone" value="13223123123"><span style="display: inline-block; position: absolute; margin-top: -20px; margin-left: 220px; color: red;">*</span>
			</div>
		</div>
		<div class="form-group">
			<label for="email" class="col-sm-4 control-label">邮箱地址</label>
			<div class="col-sm-5">
				<input type="email" required name="email" class="form-control"
					id="email" value="dabingge@sugon.com"><span style="display: inline-block; position: absolute; margin-top: -20px; margin-left: 220px; color: red;">*</span> 
			</div>
		</div>
		<div class="form-group">
			<label for="account" class="col-sm-4 control-label">登录账户</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" id="account"
					name="account" value="dabingge"><span style="display: inline-block; position: absolute; margin-top: -20px; margin-left: 220px; color: red;">*</span>
			</div>
		</div>
		<div class="form-group">
			<label for="inputPassword01" class="col-sm-4 control-label">密码</label>
			<div class="col-sm-5">
				<input type="password" class="form-control"  name="password" id="password" value="123456"><span style="display: inline-block; position: absolute; margin-top: -20px; margin-left: 220px; color: red;">*</span>
			</div>
		</div>
		<div class="form-group">
			<label for="inputPassword02" class="col-sm-4 control-label">确认密码</label>
			<div class="col-sm-5">
				<input type="password" class="form-control" id="passwordConfirm" name="passwordConfirm" value="123456"><span style="display: inline-block; position: absolute; margin-top: -20px; margin-left: 220px; color: red;">*</span>
			</div>									
		</div>
	</form>
</div>
<script type="text/javascript">
	$(function() {
		 cloudmanager.orgManagement.initAdd(); 
	});
</script>
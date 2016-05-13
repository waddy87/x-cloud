<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div class="modal-body">
	<form class="form-horizontal" name="addConfigForm" id="addConfigForm">
		<div class="form-group">
			<label for="inputText01" class="col-sm-4 control-label">名称</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" id="configName"
					name="configName" value="vcenter"
					onchange="cloudmanager.venv.changedInput()">
			</div>
		</div>
		<div class="form-group">
			<label for="inputText02" class="col-sm-4 control-label">管理地址</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" id="ip" name="ip"
					onchange="cloudmanager.venv.changedInput()" value="10.0.31.251">
			</div>
		</div>
		<div class="form-group">
			<label for="inputName01" class="col-sm-4 control-label">用户名</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" id="username"
					name="username" onchange="cloudmanager.venv.changedInput()"
					value="admin">
			</div>
		</div>
		<div class="form-group">
			<label for="inputPassword01" class="col-sm-4 control-label">密码</label>
			<div class="col-sm-5">
				<input type="password" class="form-control" id="pwd" name="pwd"
					onchange="cloudmanager.venv.changedInput()" value="Sugon!123">
			</div>
		</div>
		<!-- 
		<div class="form-group">
			<label for="inputPassword02" class="col-sm-4 control-label">确认密码</label>
			<div class="col-sm-5">
				<input type="password" class="form-control" id="pwdConfirm"
					name="pwdConfirm" onchange="cloudmanager.venv.changedInput()"
					value="Sugon!123">
			</div>
		</div> -->

		<div class="form-group">
			<div class="col-sm-offset-4 col-sm-5">
				<button class="btn btn-default btn-block"
					onclick="cloudmanager.venv.addTestConfigInfo()">测试</button>
			</div>
		</div>
	</form>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-primary" id="addConfigInfo"
		disabled="disabled" onclick="cloudmanager.venv.addConfigInfo()">确定</button>
	<button type="button" class="btn btn-default"
		onclick="cloudmanager.venv.cancelConfigInfo()">取消</button>
</div>
<script type="text/javascript">
    $(function() {
        cloudmanager.venv.initAdd();
    });
</script>
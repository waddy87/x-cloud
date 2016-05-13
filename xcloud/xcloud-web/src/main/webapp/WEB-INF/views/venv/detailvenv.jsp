<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div class="modal-body">
	<input type="hidden" id="editid" name="editid"
		value="${configInfo.configId}">
	<form class="form-horizontal" name="editConfigForm" id="editConfigForm">
		<!-- 
		<div class="form-title">
			<button class="btn btn-default btn-xs" type="button" id="editbutton"
				onclick="cloudmanager.venv.editBtn()" >编辑</button>
		</div>
		 -->
		<div class="form-group">
			<label for="inputText01" class="col-sm-4 control-label">名称</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" id="editconfigName"
					name="editconfigName" value="${configInfo.configName}"
					 onchange="cloudmanager.venv.changedInput()">
			</div>
		</div>
		 
        <div class="form-group" style="display: none">
            <label for="inputText02" class="col-sm-4 control-label">CloudVM地址</label>
            <div class="col-sm-5">
                <input class="form-control" id="editip" name="editip"
                    value="${configInfo.iP}" readonly="readonly" onchange="cloudmanager.venv.changedInput()">
            </div>
        </div>
        

		<div class="form-group">
			<label for="inputText02" class="col-sm-4 control-label">用户名</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" id="editusername"
					name="editusername" value="${configInfo.userName}"
					 onchange="cloudmanager.venv.changedInput()">
			</div>
		</div>
		<div class="form-group">
			<label for="inputPassword01" class="col-sm-4 control-label">密码</label>
			<div class="col-sm-5">
				<input type="password" class="form-control" id="editpwd"
					name="editpwd" value="${configInfo.password}"
					onchange="cloudmanager.venv.changedInput()">
			</div>
		</div>
		<div class="form-group">
			<label for="inputPassword02" class="col-sm-4 control-label">确认密码</label>
			<div class="col-sm-5">
				<input type="password" class="form-control" id="editpwdConfirm"
					name="editpwdConfirm" 
					value="${configInfo.password}"
					onchange="cloudmanager.venv.changedInput()">
			</div>
		</div>
		 <!-- 
		<div class="form-group">
            <label for="inputText02" class="col-sm-4 control-label">CloudVM地址</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="editip" name="editip"
                    value="${configInfo.iP}"  onchange="cloudmanager.venv.changedInput()">
            </div>
        </div>
       
		<div class="form-group">
			<label for="inputName01" class="col-sm-4 control-label">版本</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" id="editversion"
					name="editversion" value="${configInfo.version}"
					readonly="readonly">
			</div>
		</div>
		 -->
		<div class="form-group">
			<div class="col-sm-offset-4 col-sm-5">
				<button class="btn btn-default btn-block" id="test"
					onclick="cloudmanager.venv.editTestConfigInfo()">测试</button>
			</div>
		</div>
	</form>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-default"
		onclick="cloudmanager.venv.editConfig()" id="editConfigInfo"
		disabled="disabled">确定</button>
	<button type="button" class="btn btn-default"
		onclick="cloudmanager.venv.cancelConfigInfo()">取消</button>
</div>
<script type="text/javascript">
    $(function() {
        cloudmanager.venv.initEdit();
    });
</script>
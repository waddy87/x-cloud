<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div>
	<input type="hidden" id="id" name="id" value="${netPool.netPoolId}">
	<form class="form-horizontal" id="updatevenvForm" name="updatevenvForm">
				<div class="form-group">
			<label for="inputText01" class="col-sm-4 control-label">名称</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" id="netName"
					name="netName" value="${netPool.netName}"
					readonly="readonly">
			</div>
		</div>
		<div class="form-group">
			<label for="inputText02" class="col-sm-4 control-label">VLAN</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" id="vlanNO" name="vlanNO"
					value="${netPool.vlanNO}" readonly="readonly">
			</div>
		</div>
		<div class="form-group">
            <label for="inputText03" class="col-sm-4 control-label">网关</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="gateway" name="gateway"
                    value="${netPool.gateway}" readonly="readonly">
            </div>
        </div>
        <div class="form-group">
            <label for="inputText04" class="col-sm-4 control-label">子网掩码</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="subNet" name="subNet"
                    value="${netPool.subNet}" readonly="readonly">
            </div>
        </div>
        <div class="form-group">
            <label for="inputText05" class="col-sm-4 control-label">DNS</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="dns" name="dns"
                    value="${netPool.dns}" readonly="readonly">
            </div>
        </div>
          <div class="form-group">
            <label for="inputText05" class="col-sm-4 control-label">所属组织</label>
            <div class="col-sm-5">
            <input type="text" class="form-control" id="org" name="org"
                    value="${org.name}" readonly="readonly">
            </div>
            
        </div>
	</form>
</div>


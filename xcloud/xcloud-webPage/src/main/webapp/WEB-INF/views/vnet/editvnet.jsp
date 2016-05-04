<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div class="modal-body">
	<input type="hidden" id="editid" name="editid"
		value="${netPool.netPoolId}">
	<form class="form-horizontal" id="updatevnetForm" name="updatevnetForm">
		<!-- 
		<div class="form-group">
			<label for="inputText01" class="col-sm-4 control-label">名称</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" id="editnetName" name="editnetName" value="${netPool.netName}" >
			</div>
		</div>
		 -->
		<div class="form-group">
			<label for="inputText04" class="col-sm-4 control-label">子网</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" style="display:inline-block; width:115px;" id="editsubNet"
					name="editsubNet" value="${netPool.subNet}" onblur="cloudmanager.vnet.validateSubnet()">
				<span>/</span>
				<input type="text" class="form-control" style="display:inline-block; width:50px;" id="editsubNetNo"
					name="editsubNetNo" value="${netPool.subNetNo}" onblur="cloudmanager.vnet.validateSubnet()">
			</div>
		</div>
		<div class="form-group">
			<label for="inputText03" class="col-sm-4 control-label">网关</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" id="editgateway"
					name="editgateway" value="${netPool.gateway}">
			</div>
		</div>
		<div class="form-group">
			<label for="inputText05" class="col-sm-4 control-label">DNS</label>
			<div class="col-sm-5">
				<input type="text" class="form-control" id="editdns" name="editdns"
					value="${netPool.dns}">
			</div>
		</div>
	</form>
</div>
<script type="text/javascript">
    $(function() {
        cloudmanager.vnet.initEdit();
    });
</script>

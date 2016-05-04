<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div class="modal-body">
	<div class="row">
		<div class="col-sm-12">
			<div class="well clearfix">
				<div class="form-group">
					<label for="inputText01" class="col-sm-4 control-label">VLAN</label><label
						for="inputText01" class="col-sm-4 control-label">网关</label> <label
						for="inputText01" class="col-sm-4 control-label">子网</label>
				</div>
				<c:forEach items="${lstNetpool}" var="netPool" varStatus="status">
					<div class="form-group">
						<label for="inputText01" class="col-sm-4 control-label">${netPool.vlanNO}</label>
						<label for="inputText01" class="col-sm-4 control-label">${netPool.gateway}</label>
						<label for="inputText01" class="col-sm-4 control-label">${netPool.subNet}</label>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-12">
			<div class="input-group">
				<span class="input-group-addon" id="basic-addon1">所属组织</span>
				<select class="form-control" id="orgs" name="orgs">
					<option value="-1">请选择</option>
					<c:forEach items="${lstOrg}" var="org" varStatus="status">
						<option value="${org.id }">${org.name }</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div class="modal-body">
	<div class="row">
		<div class="col-sm-12">
			<div class="input-group">
				<span class="input-group-addon" id="basic-addon1">所属组织</span>
				<select class="form-control" id="orgSelectId" name="orgSelectId">
					<option value="">请选择</option>
					<c:forEach items="${orgList}" var="org" varStatus="status">
						<option value="${org.id }">${org.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>
</div>
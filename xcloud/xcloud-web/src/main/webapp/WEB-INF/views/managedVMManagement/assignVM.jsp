<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>

<div class="modal-body" style="width:400px;">
	<div class="row">
		<%-- <div class="col-sm-12">
			<div class="well clearfix form-horizontal" style="max-height:200px; overflow:auto;">
				<div class="form-group" style="margin-bottom:0;">
					<label class="col-sm-3 control-label">利旧虚拟机:</label>
				    <div class="col-sm-9">
				    	<c:forEach items="${nameList}" var="nl" varStatus="iex">
				      		<p class="form-control-static">${nl}</p>
						</c:forEach>
				    </div>
				</div>
			</div>
		</div> --%>
		<div class="col-sm-12">
			<div class="input-group">
				<span class="input-group-addon">请选择组织</span>
				<select class="form-control" id="orgSele">
					<c:forEach items="${orgList}" var="ol" varStatus="iex">
						<option value="${ol.id}">${ol.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-12">
			<span style="margin-left: 10px; color: red" id="orgSeleInfo"><c:if test="${fn:length(orgList)==0}">当前没有组织</c:if></span>
		</div>
	</div>
</div>
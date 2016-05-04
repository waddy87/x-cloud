<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div class="modal-body vm-tabs" style="background-color:#f7fdff;">
	<ul class="list-group pmdetail">
		<li class="list-group-item">
			<dl>
				<dt>申请单名称：</dt>
				<dd>${applicationInfo.name}</dd>
			</dl>
		</li>
		<li class="list-group-item">
			<dl>
				<dt>备注：</dt>
				<dd>${applicationInfo.remark}</dd>
			</dl>
		</li>
		<li class="list-group-item">
			<dl>
				<dt>创建数量：</dt>
				<dd>${applicationInfo.createNum}</dd>
			</dl>
		</li>
		<li class="list-group-item">
			<dl>
				<dt>所属组织名称：</dt>
				<dd>${orgName}</dd>
			</dl>
		</li>
		<li class="list-group-item">
			<dl>
				<dt>所属提供者vDC名称：</dt>
				<dd>${pVDCName}</dd>
			</dl>
		</li>
		<li class="list-group-item">
			<dl>
				<dt>vCPU数量(核)：</dt>
				<dd>${vmInfo.vCpuNumer}</dd>
			</dl>
		</li>
		<li class="list-group-item">
			<dl>
				<dt>内存(GB)：</dt>
				<dd><fmt:formatNumber type="number" value="${vmInfo.vMemCapacity/1024}" maxFractionDigits="1"/></dd>
			</dl>
		</li>
		<li class="list-group-item">
			<dl>
				<dt>存储池名称：</dt>
				<dd>${spName}</dd>
			</dl>
		</li>
		<li class="list-group-item">
			<dl>
				<dt>存储大小(GB)：</dt>
				<dd>${vmInfo.storCapacity}</dd>
			</dl>
		</li>
		<li class="list-group-item">
			<dl>
				<dt>网卡信息：</dt>
				<dd>
					<c:forEach items="${vmInfo.nets}" var="netInfo" varStatus="status">
						<p>VLAN:${netInfo.vlan}</p>
						<p>IP:${netInfo.ip}</p>
						<p>网关:${netInfo.gateway}</p>
						<p>dns:${netInfo.dns}</p>
						<p>subnet:${netInfo.subnet}</p>
					</c:forEach>
				</dd>
			</dl>
		</li>
	</ul>
</div>


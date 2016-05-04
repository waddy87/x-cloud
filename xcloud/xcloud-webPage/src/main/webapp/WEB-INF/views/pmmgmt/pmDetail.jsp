<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div class="modal-body vm-tabs" style="background-color:#f7fdff;">
	<ul class="list-group pmdetail">
		<li class="list-group-item">
			<dl>
				<dt>物理机名称：</dt>
				<dd>${pmInfo.name}</dd>
			</dl>
		</li>
		<li class="list-group-item">
			<dl>
				<dt>所属组织：</dt>
				<dd>${pmInfo.orgName}</dd>
			</dl>
		</li>
		<li class="list-group-item">
			<dl>
				<dt>物理机IP地址：</dt>
				<dd>${pmInfo.ip}</dd>
			</dl>
		</li>
		
		<li class="list-group-item">
			<dl>
				<dt>物理机IPMI IP地址：</dt>
				<dd>${pmInfo.ipmiIp}</dd>
			</dl>
		</li>
		<li class="list-group-item">
			<dl>
				<dt>IPMI用户名：</dt>
				<dd>${pmInfo.ipmiUserName}</dd>
			</dl>
		</li>
		<!-- 
		<li class="list-group-item">
			<dl>
				<dt>操作系统：</dt>
				<dd>${pmInfo.os}</dd>
			</dl>
		</li>
		<li class="list-group-item">
			<dl>
				<dt>MAC地址：</dt>
				<dd>${pmInfo.monitorMac}</dd>
			</dl>
		</li>
		<li class="list-group-item">
			<dl>
				<dt>主机类型：</dt>
				<dd>${pmInfo.hostType}</dd>
			</dl>
		</li>
		<li class="list-group-item">
			<dl>
				<dt>CPU类型：</dt>
				<dd>${pmInfo.cpuType}</dd>
			</dl>
		</li>
		<li class="list-group-item">
			<dl>
				<dt>设备类型：</dt>
				<dd>${pmInfo.deviceModel}</dd>
			</dl>
		</li>
		<li class="list-group-item">
			<dl>
				<dt>序列号：</dt>
				<dd>${pmInfo.serialNumber}</dd>
			</dl>
		</li>
		 -->
		<li class="list-group-item">
			<dl>
				<dt>创建时间：</dt>
				<dd><fmt:formatDate value="${pmInfo.createDate}" type="date" dateStyle="default"/></dd>
			</dl>
		</li>
		<li class="list-group-item">
			<dl>
				<dt>备注：</dt>
				<dd>${pmInfo.description}</dd>
			</dl>
		</li>
	</ul>
</div>
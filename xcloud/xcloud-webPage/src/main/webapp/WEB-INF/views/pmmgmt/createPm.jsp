<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div>
	<div >
		<div class="modal-body">
			<form class="form-horizontal" name="createPmForm" id="createPmForm" >
				<div class="form-group">
					<label for="name" class="col-sm-4 control-label"><font class="star">* </font>物理机名称</label>
					<div class="col-sm-5">
						<input type="text" class="form-control"  name="name" id="name">
					</div>
					<div class="col-sm-3"></div>
				</div>
				<div class="form-group">
					<label for="ip" class="col-sm-4 control-label"><font class="star">* </font>物理机IP地址</label>
					<div class="col-sm-5">
						<input type="text" class="form-control"  name="ip" id="ip">
					</div>
					<div class="col-sm-3"></div>
				</div>
				<!-- 
				<div class="form-group">
					<label for="os" class="col-sm-4 control-label">操作系统</label>
					<div class="col-sm-5">
						<input type="text" class="form-control"  name="os" id="os">
					</div>
					<div class="col-sm-3"></div>
				</div>
				 -->
				<div class="form-group">
					<label for="ipmiIp" class="col-sm-4 control-label">物理机IPMI IP地址</label>
					<div class="col-sm-5">
						<input type="text" class="form-control"  name="ipmiIp" id="ipmiIp">
					</div>
					<div class="col-sm-3"></div>
				</div>
				<div class="form-group">
					<label for="ipmiUserName" class="col-sm-4 control-label">IPMI用户名</label>
					<div class="col-sm-5">
						<input type="text" class="form-control"  name="ipmiUserName" id="ipmiUserName">
					</div>
					<div class="col-sm-3"></div>
				</div>
				<div class="form-group">
					<label for="ipmiPassword" class="col-sm-4 control-label">IPMI密码</label>
					<div class="col-sm-5">
						<input type="password" class="form-control"  name="ipmiPassword" id="ipmiPassword">
					</div>
					<div class="col-sm-3"></div>
				</div>
				<!-- 
				<div class="form-group">
					<label for="mac" class="col-sm-4 control-label">MAC地址</label>
					<div class="col-sm-5">
						<input type="text" class="form-control"  name="mac" id="mac">
					</div>
					<div class="col-sm-3"></div>
				</div>
				<div class="form-group">
					<label for="hostType" class="col-sm-4 control-label">主机类型</label>
					<div class="col-sm-5">
						<input type="text" class="form-control"  name="hostType" id="hostType">
					</div>
					<div class="col-sm-3"></div>
				</div>
				<div class="form-group">
					<label for="cpuType" class="col-sm-4 control-label">CPU类型</label>
					<div class="col-sm-5">
						<input type="text" class="form-control"  name="cpuType" id="cpuType">
					</div>
					<div class="col-sm-3"></div>
				</div>
				<div class="form-group">
					<label for="serialNumber" class="col-sm-4 control-label">序列号</label>
					<div class="col-sm-5">
						<input type="text" class="form-control"  name="serialNumber" id="serialNumber">
					</div>
					<div class="col-sm-3"></div>
				</div>
				<div class="form-group">
					<label for="deviceModel" class="col-sm-4 control-label">设备类型</label>
					<div class="col-sm-5">
						<input type="text" class="form-control"  name="deviceModel" id="deviceModel">
					</div>
					<div class="col-sm-3"></div>
				</div>
				 -->
				<div class="form-group">
					<label for="description" class="col-sm-4 control-label">备注</label>
				    <div class="col-sm-5">
				    	<textarea id="description" name="description"  class="form-control" rows="3"></textarea>
				    </div>
				    <div class="col-sm-3"></div>
				</div>
			</form>		
		</div>
	</div>

</div>
<script type="text/javascript">
	$(function() {
		 cloudmanager.pmMgmt.initCreatePm(); 
	});
</script>
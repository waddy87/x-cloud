<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div class="row">
	<label class="col-sm-3 control-label">计算池信息</label>
	<div class="col-sm-3">
		<div class="list-group">
		  	<div class="list-group-item">
		  		<span id="cpuTotCapacitySpanId" class="badge">${computingPoolInfo.cpuTotCapacity} MHz</span>
		  		CPU总数
		  	</div>
		  	<div class="list-group-item">
		  		<span id="cpuUsedCapacitySpanId" class="badge">${computingPoolInfo.cpuUsedCapacity} MHz</span>
		  		CPU已用
		  	</div>	
		  	<div class="list-group-item">
		  		<span id="cpuAvlCapacitySpanId" class="badge">${computingPoolInfo.cpuAvlCapacity} MHz</span>
		  		CPU可用
		  	</div>
		</div>
	</div>
	<div class="col-sm-3">
		<div class="list-group">
		  	<div class="list-group-item">
		  		<span id="memoryTotCapacitySpanId" class="badge">
		  			<fmt:formatNumber type="number" value="${computingPoolInfo.memoryTotCapacity/1024}" maxFractionDigits="1"/> GB
		  		</span>
		  		内存总量
		  	</div>
		  	<div class="list-group-item">
		  		<span id="memoryUsedCapacitySpanId" class="badge">
		  			<fmt:formatNumber type="number" value="${computingPoolInfo.memoryUsedCapacity/1024}" maxFractionDigits="1"/> GB
		  		</span>
		  		内存已用
		  	</div>	
		  	<div class="list-group-item">
		  		<span id="memoryAvlCapacitySpanId" class="badge">
		  			<fmt:formatNumber type="number" value="${computingPoolInfo.memoryAvlCapacity/1024}" maxFractionDigits="1"/> GB
		  		</span>
		  		内存可用
		  	</div>
		</div>
	</div>
	<div class="col-sm-3">
	</div>
</div>
<div class="form-group">
	<label class="col-sm-3 control-label" for="vcpuNum"><font class="star">* </font>vCPU(核)</label>
	<div class="col-sm-6">
		<input id="vcpuNum" name="vcpuNum" type="text" class="form-control required digits"/>
	</div>
	<div class="col-sm-3"></div>
</div>
<div class="form-group">
	<label class="col-sm-3 control-label" for="memorySize"><font class="star">* </font>内存(GB)</label>
	<div class="col-sm-6">
		<input id="memorySize" name="memorySize" type="text" class="form-control required digits"/>
	</div>
	<div class="col-sm-3"></div>
</div>
<div class="form-group">
	<label class="col-sm-3 control-label"><font class="star">* </font>存储</label>
	<div class="col-sm-2">
	    <select id="storagePool" name="storagePool" class="form-control">
			<option value="">请选择</option>
			<c:forEach items="${storagePoolList}" var="storagePoolInfo" varStatus="status">
			<option value="${storagePoolInfo.spId }">${storagePoolInfo.name }</option>
			</c:forEach>
		</select>
	</div>
	<div class="col-sm-3">
		<div class="input-group">
		  	<span class="input-group-addon">大小</span>
		  	<input id="storageSize" name="storageSize" type="text" class="form-control"/>
		  	<span class="input-group-addon">GB</span>
		</div>
	</div>
	<div class="col-sm-1">
		<a class="btn btn-default btn-block" onclick="cloudmanager.proVDCCreate.addStoragePool();" style="min-width:auto;"><i class="fa fa-plus"></i></a>
	</div>
	<div class="col-sm-3" id="storagePoolMesDivId"></div>
</div>
<div class="row">
	<div class="col-sm-3"></div>
	<div class="col-sm-9" id="storagePoolInfoDivId"></div>
</div>
<div class="row">
	<div class="col-sm-3"></div>
	<div class="col-sm-6">
		<div class="list-group" id="proVDCCreateAddStorageDivId">
		</div>
	</div>
	<div class="col-sm-3"></div>
</div>
<script type="text/javascript">
$("#storagePool").change(function(){
	var storagePoolId=$("#storagePool option:selected").val();
	sugon.load({
		type : 'POST',
		action : sugon.rootURL + '/proVDC/getStoragePoolDetail',
		data:{
			storagePoolId:storagePoolId
		},
		dataType:"json",
		callback : function(result) {
			if(result.success){
				$("#storagePoolInfoDivId").text("存储剩余："+result.storagePoolInfo.spSurplus+"GB;"+"存储总量："+result.storagePoolInfo.spTotal+"GB");
			}else{
				
			}
		}
	});
});
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div class="modal-body">
	<div class="row">
		<div class="col-sm-12">
	<%-- 	${storPool} --%>
			<form class="form-horizontal" name="addDiskForm" id="addDiskForm">
				<div class="form-group">
					<label class="col-sm-3 control-label" for="inputText01">存储池</label>
					<div class="col-sm-7">
						<%-- 				<p class="form-control-static">${orgDetail.name}</p>
						<input type="text" class="form-control orgEdit" name="orgName" id="orgName"
							value="${orgDetail.name}"> --%>
							
						<select class="form-control" id="storSele">
							<c:forEach items="${storPools}" var="st" varStatus="iex">						
								 <option value="${st.pVDCStoragePoolId}" data-spid="${st.spId}" data-name="${st.spName}" data-total="${st.spTotal}">${st.spName}</option>		 				
							</c:forEach>
						</select>
						<c:if test="${storPools==null||fn:length(storPools)==0}">
							<span style="margin-left: 10px; color: red" id="storSeleInfo">该VDC没有存储池</span>
						</c:if>
					</div>
					<div class="col-sm-2"></div>
				</div>
				<div class="row">
					<div class="col-sm-3"></div>
					<div class="col-sm-7">
						<ul class="list-group">
						  	<li class="list-group-item"><span class="badge" id="storId">${storPools[0].spId}</span>物理存储池</li>
						  	<li class="list-group-item"><span class="badge" id="storName">${storPools[0].spName}</span>存储池名称</li>
						  	<li class="list-group-item"><span class="badge" id="storTotal">${storPools[0].spTotal} GB</span>总容量</li>
						  	<li class="list-group-item"><span class="badge" id="storUsed" data-value="${storPools[0].spUsed}">${storPools[0].spUsed} GB</span>已使用容量</li>
						  	<li class="list-group-item"><span class="badge" id="storUnused" data-value="${storPools[0].spTotal-storPools[0].spUsed}">${storPools[0].spTotal-storPools[0].spUsed} GB</span>可使用容量</li>
						</ul>
					</div>
					<div class="col-sm-2"></div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="inputText01" >磁盘大小(GB)</label>
					<div class="col-sm-7">
						<input type="text" class="form-control" name="storSize" id="storSize">
					</div>
					<div class="col-sm-2"></div>
				</div>
<!-- 				<div class="form-group">
					<div class="col-sm-offset-3 col-sm-9">
						<button class="btn btn-default btn-block" id="addDiskBtn">添加磁盘 </button>
					</div>
				</div> -->
			</form>
		</div>
		<!-- <div class="col-sm-6">
			<div class="panel panel-default vm-addnet-list">
				<div class="panel-heading">
					<h3 class="panel-title">已经添加的磁盘</h3>
				</div>
				<div class="panel-body" id="addedDisk">
					<div class="netcard-list">
						<span>网卡名称001</span>
						<span>IP:10.0.33.124</span>
						<a href="javascript:;" onclick="cloudmanager.vmManagement.removeItem()"><i class="fa fa-times"></i></a>
					</div>
					<div class="netcard-list">
						<span>网卡名称002</span>
						<span>IP:10.0.33.125</span>
						<a href="javascript:;"><i class="fa fa-times"></i></a>
					</div>
					<div class="netcard-list">
						<span>网卡名称003</span>
						<span>IP:10.0.33.126</span>
						<a href="javascript:;"><i class="fa fa-times"></i></a>
					</div>
				</div>
			</div>
		</div> -->
	</div>
</div>
<%-- ${netPoolList}
 [{"isAvl":true,"netPoolId":"network-1041","orgName":"ssssstttasdasd","synDate":{"date":5,"hours":22,"seconds":17,"month":3,"timezoneOffset":-480,"year":116,"minutes":10,"time":1459865417000,"day":2},"configId":"ff80808153cc5fad0153cc78493700c7","netName":"VM Network 3","subNet":"255.255.255.0","dns":"8.8.8.8","gateway":"10.0.33.254","orgId":"8a80f349540f04ed01540f0f32270009","vlanNO":0}]  --%> 
<%-- <script type="text/javascript" src="${ctx}/resources/js/sg.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/sg.slider.js"></script> --%>
<script type="text/javascript">
	$(function() {
		 cloudmanager.vmManagement.initAddNDisk(); 

	});
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div class="modal-body">
	<div class="row">
		<div class="col-sm-9" id="vmAddConfig">
			<dl class="vm-configure-block">
				<dt>
					<span>VDC</span>
				</dt>
				<dd>
					<div class="vm-configure-row">
						<label>提供者vDC</label>
						<%-- ${proVDCList} --%>
						<div class="vm-configure-row-con">
							<div class="btn-group" data-toggle="buttons" id="vdcSele">
								<c:forEach items="${proVDCList}" var="pv" varStatus="iex">
									<label class="btn ${iex.index==0?'active':''}"><input
										type="radio" name="computingPool"
										value="${pv.computingPoolId}" data-name="${pv.name}" data-vdcId="${pv.pVDCId}"
										autocomplete="off" ${iex.index==0?'checked':''}>${pv.name}</label>
								</c:forEach>
								<!-- 		  
								<label class="btn"><input type="radio" name="options" id="option2" autocomplete="off"> B区 </label> 
								<label class="btn"> <input type="radio" name="options" id="option3" autocomplete="off"> C区 </label> -->
							</div>
						</div>
					</div>
				</dd>
			</dl>
			<dl class="vm-configure-block">
				<dt>
					<span>组织</span>
				</dt>
				<dd>
					<div class="vm-configure-row">
						<label>选择组织:</label>
						<div class="vm-configure-row-con">
							<%-- ${vmTempList} --%>
							<select class="form-control" id="orgSele">
								<c:forEach items="${orgList}" var="ol" varStatus="iex">
									<option value="${ol.id}">${ol.name}</option>
								</c:forEach>
							</select> <span style="margin-left: 10px; color: red" id="orgSeleInfo"><c:if
									test="${fn:length(orgList)==0}">没有可用组织</c:if></span>
							<%-- ${orgList.length} --%>

						</div>
					</div>
				</dd>
			</dl>
			<dl class="vm-configure-block">
				<dt>
					<span>模板</span>
				</dt>
				<dd>
					<div class="vm-configure-row">
						<%-- 						${vmTempList} --%>
						<label>选择模板:</label>
						<div class="vm-configure-row-con">
							<%-- ${vmTempList} --%>
							<select class="form-control" id="vmTempSele">
								<c:forEach items="${vmTempList}" var="vt" varStatus="iex">
									<option value="${vt.relationId}" data-memory="${vt.memory}"
										data-os="${vt.os}" data-cpuNum="${vt.cpu}" data-storCapacity="${vt.diskSize}">${vt.name}</option>
								</c:forEach>
							</select> <span style="margin-left: 10px; color: red" id="templSeleInfo"><c:if
									test="${vmTempList==null||fn:length(vmTempList)==0}">没有模板</c:if></span>
						</div>
					</div>
				</dd>
			</dl>
			<dl class="vm-configure-block">
				<dt>
					<span>配置</span>
				</dt>
				<dd>
					<div class="vm-configure-row">
						<label>CPU:</label>
						<div class="vm-configure-row-con">
							<div id="cpuDivId">
								<!-- 			<input type="text" name="cpuNum" class="form-control"> -->
							</div>
						</div>
					</div>
					<div class="vm-configure-row">
						<label>内存:</label>
						<div class="vm-configure-row-con">
							<div id="memoryDivId">
								<!-- 	<input type="text" name="memory" class="form-control"> -->
							</div>
						</div>
					</div>
				</dd>
			</dl>
			<dl class="vm-configure-block">
				<dt>
					<span>网络</span>
				</dt>
				<dd>
					<div class="vm-configure-row">
						<label>子网:</label>
						<div class="vm-configure-row-con">
							<form id="ipForm" name="ipForm" style="float:right;">
								<div class="input-group-error"></div>
								<div class="input-group">
								  	<span class="input-group-addon">IP</span>
								  	<input id="netIP" name="netIP" type="text" class="form-control">
								  	<span class="input-group-btn">
							        	<button class="btn btn-default" type="button" id="addNetcardBtn"><i class="fa fa-plus"></i></button>
							      	</span>
								</div>
							</form>
							<select class="form-control" id="netSele">
								<c:forEach items="${netPoolList}" var="np" varStatus="iex">
									<option value="${np.netPoolId}" data-vlan="${np.vlanNO}" data-subnet="${np.subNet}" data-dns="${np.dns}" data-gateway="${np.gateway}" data-added="false">${np.netName}/${np.subNet}</option>
								</c:forEach>
							</select>
							<span style=" line-height: 0; clear: both; display: block; position: absolute; left: 300px; margin-left: 280px; margin-top: -15px;color:red" id="netSeleInfo">
							<%-- <c:if test="${netPoolList!=null&&fn:length(netPoolList)!=0}">子网号:${netPoolList[0].subNet}</c:if> --%>
								<c:if test="${netPoolList==null||fn:length(netPoolList)==0}"><span style="line-height:28px;color: red;">该组织没有网络池</span></c:if>
							</span>
							<div class="list-group"  id="addedNetcard">
							</div>  
						</div>
					</div>
				</dd>
			</dl>
			<dl class="vm-configure-block">
				<dt>
					<span>存储</span>
				</dt>

				<dd>
					<div class="vm-configure-row">
						<label>选择存储池:</label>
						<%-- 	${proVDCSpList} --%>
						<div class="vm-configure-row-con">
							<!--<div class="input-group-error"></div>
							<div class="input-group">
							  	<span class="input-group-addon">大小(GB)</span>
							  	<input id="storSize" name="storSize" type="text" class="form-control">
							  	<span class="input-group-btn">
						        	<button class="btn btn-default" type="button"><i class="fa fa-plus"></i></button>
						      	</span>
							</div>  -->
							<select class="form-control" id="storPoolSele">

								<c:forEach items="${proVDCSpList}" var="ps" varStatus="iex">
									<option value="${ps.spId}">${ps.spName}</option>
								</c:forEach>
							</select>

							<%-- 							<div class="list-group">
								<c:forEach items="${proVDCSpList}" var="ps" varStatus="iex">
									<div class="list-group-item"><a href='#' class='pull-right'><i class='fa fa-times-circle'></i></a>${ps.spName}</div>
								</c:forEach>
							</div>   --%>
						</div>
					</div>
				</dd>
			</dl>
			<dl class="vm-configure-block">
				<dt>
					<span>密码</span>
				</dt>
				<dd>
				<form id="addVmForm" name="addVmForm">
						<div class="vm-configure-row">
							<label>虚拟机名称:</label>
							<div class="vm-configure-row-con">
								<input type="text" class="form-control" name="vmName"
									placeholder="请输入虚拟机名" id="vmName">
							</div>
						</div>


<!-- 						<div class="vm-configure-row" id="usernamepart">
							<label>用户名:</label>
							<div class="vm-configure-row-con">
								<input type="text" class="form-control" name="username"
									placeholder="请输入用户名" id="username">
							</div>
						</div> -->
											<div class="vm-configure-row">
						<label>密码设置:</label>
						<div class="vm-configure-row-con">
							<div class="btn-group" data-toggle="buttons">
								<label class="btn active" id="setnow"> <input type="radio"
									name="options2"  autocomplete="off" checked>
										设定密码 </label> <label class="btn" id="setdelay"> <input type="radio"
									name="options2" autocomplete="off"> 随机密码
								</label>
							</div>
						</div>
					</div>
						<div class="vm-configure-row" id="passwordpart">
							<label>密码:</label>
							<div class="vm-configure-row-con">
								<input type="password" class="form-control" name="password"
									id="password" value="1234">
							</div>
						</div>
						<div class="vm-configure-row" id="password2part">
							<label>确认密码:</label>
							<div class="vm-configure-row-con">
								<input type="password" class="form-control"
									name="passwordConfirm" id="passwordConfirm" value="1234">
							</div>
						</div>
				</dd>
			</dl>
			<dl class="vm-configure-block">
				<dt>
					<span>数量</span>
				</dt>
				<dd>
					<div class="vm-configure-row">
						<label>创建数量:</label>
						<div class="vm-configure-row-con" id="vmNumber"></div>
					</div>
				</dd>
			</dl>
		</div>
		<div class="col-sm-3">
			<div class="vm-configure vm-tabs">
				<h5>当前配置</h5>
				<ul class="list-group">
					<li class="list-group-item">
						<dl>
							<dt>虚拟机名称：</dt>
							<dd><span id="con_vmName"></span></dd>
						</dl>
					</li>
					<li class="list-group-item">
						<dl>
							<dt>提供者VDC：</dt>
							<dd><span id="con_proVDC"></span></dd>
						</dl>
					</li>
					<li class="list-group-item">
						<dl>
							<dt>组织：</dt>
							<dd><span id="con_org"></span></dd>
						</dl>
					</li>
					<li class="list-group-item">
						<dl>
							<dt>CPU：</dt>
							<dd><span id="con_cpu"></span></dd>
						</dl>
					</li>
					<li class="list-group-item">
						<dl>
							<dt>内存：</dt>
							<dd><span id="con_memory"></span></dd>
						</dl>
					</li>
					<li class="list-group-item">
						<dl>
							<dt>存储容量：</dt>
							<dd><span id="con_storage"></span></dd>
						</dl>
					</li>
					<li class="list-group-item">
						<dl>
							<dt>子网：</dt>
							<dd><span id="con_network"></span></dd>
						</dl>
					</li>
					<li class="list-group-item">
						<dl>
							<dt>存储池：</dt>
							<dd><span id="con_storagePool"></span></dd>
						</dl>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>
<%-- <script type="text/javascript" src="${ctx}/resources/js/sg.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/sg.slider.js"></script> --%>
<script type="text/javascript">
	$(function() {
		 cloudmanager.vmManagement.initAdd(); 
	});
</script>
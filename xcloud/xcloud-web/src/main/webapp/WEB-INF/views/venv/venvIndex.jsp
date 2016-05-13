<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ include file="/WEB-INF/views/includeFile.jsp"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header">
				<h1>虚拟化环境</h1>
				<div class="small">虚拟化环境是对计算、存储和网络等资源进行虚拟化的软件，例如FusionCompute。
					系统中所使用和管理的虚拟资源都来源于虚拟化环境中的资源。</div>
			</div>
		</div>
	</div>
	<div class="row card-1">
		<c:forEach items="${configInfo1}" var="configInfo11"
			varStatus="status">
			<div class="col-lg-6 col-md-6 col-sm-12">
				<div class="card-1-content">
					<div class="card-1-c-r">
						<a class="btn btn-link btn-block"
							onclick="cloudmanager.venv.editConfigPage()"><i
							class="fa fa-pencil-square"></i>修改</a> <a
							class="btn btn-link btn-block" href="#"
							onclick="cloudmanager.venv.delConfigInfo()"><i
							class="fa fa-trash"></i>删除</a> 
							<a class="btn btn-link btn-block"
							href="#" onclick="cloudmanager.venv.sysData(${configInfo11.status})"><i
							class="fa fa-exchange"></i>同步</a>
					</div>
					<input type="hidden" id="id" name="id"
						value="${configInfo11.configId}"> <input type="hidden"
						id="configName" name="configName"
						value="${configInfo11.configName}"> <input type="hidden"
						id="ip" name="ip" value="${configInfo11.iP}"> <input
						type="hidden" id="username" name="username"
						value="${configInfo11.userName}"> <input type="hidden"
						id="pwd" name="pwd" value="${configInfo11.password}">
					<div class="card-1-c-l">
						<div class="card-1-c-info">
							<i class="fa fa-clone"></i>
							<dl>
								<dt>${configInfo11.configName}</dt>
								<dd>访问地址：${configInfo11.iP}</dd>
								<dd>版本：${configInfo.version}</dd>
								<dd>版本号：v6.0</dd>
								<dd>接入时间：${configInfo11.createDate}</dd>
							</dl>
						</div>
						<div class="card-1-c-status">
							<c:if test="${configInfo11.status=='1'}">
								<p>
									连接状态:<label id="result">正常</label>
								</p>
								<button type="button" class="btn btn-success btn-block"
									onclick="cloudmanager.venv.testStatus()" id="status">测试连接</button>
							</c:if>
							<c:if test="${configInfo11.status=='2'}">
								<p>
									连接状态:<label id="result">异常</label>
								</p>
								<button type="button" class="btn btn-warning btn-block"
									onclick="cloudmanager.venv.testStatus()" id="status">测试连接</button>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
		<c:if test="${configInfo1==null||configInfo1==''}">
			<div class="col-lg-6 col-md-6 col-sm-12">
				<div class="card-1-content-add">
					<button class="btn btn-link" type="button"
						onclick="cloudmanager.venv.addConfigInfoPage()">
						<i class="fa fa-plus-square"></i>
						<p>接入新的虚拟化环境</p>
					</button>
				</div>
			</div>
		</c:if>
	</div>
</div>
<div id="addvenv"></div>
<script src="${ctx}/resources/js/venvManagement/virtualenv.js"></script>

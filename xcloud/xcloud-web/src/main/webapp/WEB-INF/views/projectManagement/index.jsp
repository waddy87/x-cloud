<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<style type="text/css">
.orgEdit {
	display: none;
}

.project-list .list-group .list-group-item.focus {
	background-color: #e7eaee
}
</style>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header">
				<h1>项目管理</h1>
				<div class="small"></div>
			</div>
		</div>
	</div>
	<div class="row project-list">
		<div class="col-lg-3 col-md-3 col-sm-12">
		<shiro:hasRole name="org_manager">
			<a class="btn btn-block btn-default" href="#"
				onclick="cloudmanager.projectManagement.addProject('${orgId}')">新建项目</a>
				<input type="hidden" id="roleFlag" value="org_manager"></input>
		</shiro:hasRole>
		<shiro:hasRole name="org_user">
		<input type="hidden" id="roleFlag" value="org_user"></input>
		</shiro:hasRole>
			<div class="list-group dd_grou" id="proList"
				style="overflow: auto; height: 200px;">
				<c:forEach items="${proList}" var="pro" varStatus="iex">
					<a href="#" class="list-group-item"
						onclick="cloudmanager.projectManagement.updateByProject('${pro.orgId}','${pro.id}',this)"
						data-proid="${pro.id}" data-remarks="${pro.description}"
						data-createtime="${pro.createTime}" data-name="${pro.name}"><i
						class="fa fa-dot-circle-o"></i>${pro.name}</a>
				</c:forEach>
			</div>
		</div>
		<div class="col-lg-9 col-md-9 col-sm-12">
			<div class="panel panel-default">
				<div class="panel-body" style="padding: 15px; border-style: none">
					<div class="page-header">
						<h1>项目管理</h1>
						<c:if test="${fn:length(proList)!=0}">
							<form class="form-horizontal" name="updateProForm"
								id="updateProForm">
								<div class="form-title">
									项目基本信息
									<shiro:hasRole name="org_manager">
									<button class="btn btn-default btn-xs" type="button"
										style="float: right;"
										onclick="cloudmanager.projectManagement.deletePro()">删除</button>
									<button class="btn btn-default btn-xs" type="button"
										id="editBtn" style="float: right; margin-right: 20px;">编辑</button>
</shiro:hasRole>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label" for="inputText01">项目名称</label>
									<div class="col-sm-7">
										<p class="form-control-static" id="proNameP">${proList[0].name}</p>
										<input type="text" class="form-control orgEdit" name="proName"
											id="proName" value="${proList[0].name}">
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-4 control-label" for="inputName01">备注</label>
									<div class="col-sm-7">
										<p class="form-control-static" id="remarksP">${proList[0].description}</p>
										<input type="text" ng-show="editFlag"
											class="form-control orgEdit" name="remarks" id="remarks"
											value="${proList[0].description}">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-4 control-label" for="inputText02">创建时间</label>
									<div class="col-sm-7">
										<p class="form-control-static" id="createTime">${proList[0].createTime}</p>
									</div>
								</div>
								<!-- 				<div class="form-group">
					<label class="col-sm-4 control-label" for="inputPassword01">密码</label>
					<div class="col-sm-7">
						<a data-target="#myModal-03-02" data-toggle="modal" role="button" class="btn btn-default" href="#">重置密码</a>
					</div>
				</div> -->
								<div class="modal-footer orgEdit" id="btnDiv">
									<button type="submit" class="btn btn-primary">保存</button>
								</div>
							</form>
						</c:if>

					</div>
					<div class="row">
						<div class="col-lg-6 col-md-6 col-sm-12">
							<h5>成员管理</h5>
						</div>
					<shiro:hasRole name="org_manager">
						<div class="col-lg-6 col-md-6 col-sm-12 text-right">
							<a class="easyui-linkbutton" href="#"
								onclick="cloudmanager.projectManagement.addUser('${orgId}')">添加成员</a>
								
<!-- 							<a class="easyui-linkbutton" href="#"
								onclick="cloudmanager.projectManagement.removeUser()">移除成员</a> -->
						</div>
					</shiro:hasRole>	
					</div>
					<div class="row vm-table">
						<div class="col-lg-12 col-md-12 col-sm-12">
							<table id="personTable">

							</table>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-6 col-md-6 col-sm-12">
							<h5>项目虚拟机列表</h5>
						</div>
						<shiro:hasRole name="org_manager">
						<div class="col-lg-6 col-md-6 col-sm-12 text-right">
							<a class="easyui-linkbutton" href="#"
								onclick="cloudmanager.projectManagement.addVm('${orgId}')">添加虚拟机</a>
							<%-- <a class="easyui-linkbutton" href="#" onclick="cloudmanager.projectManagement.removeVm('${orgId}')">移除虚拟机</a> --%>
						</div>
						</shiro:hasRole>
					</div>
					<div class="row vm-table">
						<div class="col-lg-12 col-md-12 col-sm-12">
							<table id="vmTable">

							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="sugonDialog"></div>
	</div>
</div>
<script src="${ctx}/resources/lib/easyui/jquery.easyui.min.js"></script>
<script
	src="${ctx}/resources/lib/jquery/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="${ctx}/resources/js/projectManagement/projectManagement.js"></script>
<script type="text/javascript">
	$(function() {
		cloudmanager.projectManagement.initList("${orgId}", "${proList[0].id}");
	});
</script>
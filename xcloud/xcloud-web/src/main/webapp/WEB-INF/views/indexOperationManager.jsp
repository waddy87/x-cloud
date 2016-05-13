<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div class="header">
	<nav class="navbar navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"><img
					src="${ctx}/resources/image/logo.svg" /></a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-left">
					<li class="dropdown"><a href="javascript:void(0);"
						class="dropdown-toggle" data-toggle="dropdown" role="button"
						aria-haspopup="true" aria-expanded="false"><i
							class="glyphicon glyphicon-align-justify"></i></a></li>
					<li name="/dashboard/toDashboardOperationIndex"><a href="#"
						onclick='cloudmanager.menu.openLink("/dashboard/toDashboardOperationIndex",this)'>控制面板</a></li>
					<li><a href="#">我的待办</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li id="hellowLiId"></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false"><span class="user-photo"><img
								src="${ctx}/resources/image/user.png" width="32" height="32" /></span><span>${sessionScope.currentUser.username }</span></a>
						<ul class="dropdown-menu">
							<li><a href="javascript:;"
								onclick="cloudmanager.userPassword.openUpdateUserPasswordPage();"><i
									class="fa fa-lock"></i>修改密码</a></li>
							<li><a href="${ctx}/logout"><i class="fa fa-power-off"></i>退出</a></li>
						</ul></li>
					<li><a href="#"><i class="glyphicon glyphicon-cog"></i></a></li>
					<li><a href="#"><i
							class="glyphicon glyphicon-question-sign"></i></a></li>
					<li><a href="#"><i class="glyphicon glyphicon-bell"></i></a></li>
					<li id="taskBoardBtn" onclick="show()"><a href="javascript:;"><i
							class="glyphicon glyphicon-tasks"></i></a></li>
				</ul>
			</div>
		</div>
	</nav>
</div>
<div class="container-fluid">
	<div class="row">
		<div class="sidebar">
			<ul class="nav nav-pills nav-stacked" id="systemMenu">
			     <li class="nav-header">系统菜单</li>
			     <li>
			     	<a href="#" aria-expanded="true">
			     		<i class="fa fa-dropbox"></i>资源<span class="fa arrow"></span>
			     	</a>
			         <ul aria-expanded="true">
			              <li name="/venv/getConfigInfo">
			              	<a href="javascript:;" id="venvconfig"  onclick='cloudmanager.menu.openLink("/venv/getConfigInfo",this)'>
			              		<i class="fa fa-angle-right"></i>虚拟化环境
			              	</a>
			              </li>
			              <li name="/proVDC/providerVDCList">
			              	<a href="javascript:;" id="proVDCAId"  onclick='cloudmanager.menu.openLink("/proVDC/providerVDCList",this)'>
			              		<i class="fa fa-angle-right"></i>提供者vDC
			              	</a>
			              </li>
			              <li name="/vnet/vnetList">
			             	  <a href="javascript:;" id="vnet"   onclick='cloudmanager.menu.openLink("/vnet/vnetList",this)'>
			             	  	<i class="fa fa-angle-right"></i>网络
			             	  </a>
			              </li>
			         </ul>
			     </li>
			     <li>
			     	<a href="#" aria-expanded="false">
			     		<i class="fa fa-cloud"></i>服务 <span class="fa arrow"></span>
			     	</a>
			         <ul aria-expanded="false">
			              <li name="/templet/toVMTempletIndex">
			              	<a href="#" onclick='cloudmanager.menu.openLink("/templet/toVMTempletIndex",this)'>
			              		<i class="fa fa-angle-right"></i>模板管理
			              	</a>
			              </li>
			              <li name="/applicationMgmt/applicationIndex">
			              	<a href="#"  onclick='cloudmanager.menu.openLink("/applicationMgmt/applicationIndex",this)'>
			              		<i class="fa fa-angle-right"></i>资源审批
			              	</a>
			              </li>
			              <li name="/applicationLogMgmt/applicationLogIndex">
			              	<a href="#"  onclick='cloudmanager.menu.openLink("/applicationLogMgmt/applicationLogIndex",this)'>
			              		<i class="fa fa-angle-right"></i>审批历史
			              	</a>
			              </li>
			         </ul>
			     </li>
			     
			     <li>
			     	<a href="#" aria-expanded="false">
			     		<i class="fa fa-server"></i>资产 <span class="fa arrow"></span>
			     	</a>
			         <ul aria-expanded="false">
			             <li name="/action/vm/vmIndex">
					      	<a href="#" onclick='cloudmanager.menu.openLink("/action/vm/vmIndex",this)'>
					             <i class="fa fa-angle-right"></i>虚拟机
					        </a>
				        </li>
				        <li name="/oldvm/mandateIndex">
					        <a href="#" onclick='cloudmanager.menu.openLink("/oldvm/mandateIndex",this)'>
								<i class="fa fa-angle-right"></i>利旧虚拟机
							</a>
						</li>
					    <li name="/pmMgmt/pmIndex">
					        <a href="javascript:;" onclick='cloudmanager.menu.openLink("/pmMgmt/pmIndex",this)'>
					      		<i class="fa fa-angle-right"></i>物理机
					      	</a>
				      	</li>
				         </ul>
			     </li>
			      
			     <li>
			     	<a href="#" aria-expanded="false">
			     		<i class="fa fa-tachometer"></i>监控<span class="fa arrow"></span>
			     	</a>
			     	<ul aria-expanded="false">
			              <li name="/monitor/toMonitorIndex">
			              	<a href="javascript:;" onclick='cloudmanager.menu.openLink("/monitor/toMonitorIndex",this)'>
			              		<i class="fa fa-angle-right"></i>监控概览
			              	</a>
			              </li>
			              <li name="/monitor/toMonitorResource">
			              	<a href="javascript:;" onclick='cloudmanager.menu.openLink("/monitor/toMonitorResource",this)'>
			              		<i class="fa fa-angle-right"></i>集群视图
			              	</a>
			              </li>
			             <li name="/monitor/toMonitorHost">
			             	<a href="javascript:;" onclick='cloudmanager.menu.openLink("/monitor/toMonitorHost",this)'>
			             		<i class="fa fa-angle-right"></i>云主机视图
			             	</a>
			             </li>
			             <li name="/monitor/toMonitorVM">
			             	<a href="javascript:;" onclick='cloudmanager.menu.openLink("/monitor/toMonitorVM",this)'>
			             		<i class="fa fa-angle-right"></i>虚拟机视图
			             	</a>
			             </li>
			             <li name="/monitor/toMonitorStorage">
			             	<a href="javascript:;" onclick='cloudmanager.menu.openLink("/monitor/toMonitorStorage",this)'>
			             		<i class="fa fa-angle-right"></i>存储视图
			             	</a>
			             </li>
			             <li name="/alert/toAlertIndex">
			             	<a href="javascript:;" onclick='cloudmanager.menu.openLink("/alert/toAlertIndex",this)'>
			             		<i class="fa fa-angle-right"></i>告警概况
			             	</a>
			             </li>
			             <li name="/alert/alertSender">
			             	<a href="javascript:;" onclick='cloudmanager.menu.openLink("/alert/alertSender",this)'>
			             		<i class="fa fa-angle-right"></i>告警策略
			             	</a>
			             </li>
			         </ul>
			      </li>
			      <li name="/accounting/toAccountIndex">
			      	<a href="javascript:;" onclick='cloudmanager.menu.openLink("/accounting/toAccountIndex",this)'>
			      		<i class="fa fa-pie-chart"></i>记账
			      	</a>
			      </li>
			     <li>
			     	<a href="#" aria-expanded="false">
			     		<i class="fa fa-users"></i>用户 <span class="fa arrow"></span>
			     	</a>
			         <ul aria-expanded="false">
			              <li  name="/orgs/orgIndex">
			              	<a href="#" id="orgManaLink" onclick='cloudmanager.menu.openLink("/orgs/orgIndex",this)'>
			              		<i class="fa fa-angle-right"></i>组织管理
			              	</a>
			              </li>
			             	<li name="/userMgmt/userIndex">
			             		<a href="#" onclick='cloudmanager.menu.openLink("/userMgmt/userIndex",this)'>
			             			<i class="fa fa-angle-right"></i>用户管理
			             		</a>
			             	</li>
			         </ul>
			      </li>
			      <li>
			     	<a href="#" aria-expanded="false">
			     		<i class="fa fa-cog"></i>管理 <span class="fa arrow"></span>
			     	</a>
			         <ul aria-expanded="false">
			         	<li name="/messagePush/messageIndex"><a href="#"
						onclick='cloudmanager.menu.openLink("/messagePush/messageIndex",this)'> <i
							class="fa fa-angle-right"></i>消息
					</a></li>
					               	<li name="/log/toLogIndex">
						<a href="javascript:;" onclick='cloudmanager.menu.openLink("/log/toLogIndex",this)'>
							<i class="fa fa-angle-right"></i>日志
						</a>
					</li>
					               <li name="/task/toTaskIndex">
						<a href="javascript:;" onclick='cloudmanager.menu.openLink("/task/toTaskIndex",this)'>
							<i class="fa fa-angle-right"></i>任务
						</a>
					</li>
			         </ul>
			      </li>
			</ul>
		</div>
		<div class="main" id="main"></div>
	</div>
</div>

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
					<li name="/dashboard/toDashboardOrgManagerIndex"><a href="#"
						onclick='cloudmanager.menu.openLink("/dashboard/toDashboardOrgManagerIndex",this)'>控制面板</a></li>
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
			     <!-- 
			     <li name="/vnet/vnetList">
				   	  <a href="#" id="vnet" onclick='cloudmanager.menu.openLink("/vnet/vnetList",this)'>
				   	  	<i class="fa fa-connectdevelop"></i>网络
				   	  </a>
			     </li>
			      -->
				<li name="/action/vm/vmIndex">
			      	<a href="#"  onclick='cloudmanager.menu.openLink("/action/vm/vmIndex",this)'>
			    		<i class="fa fa-desktop"></i>虚拟机
			    	</a>
			    </li>
				<li name="/oldvm/mandateIndex">
					<a href="#" onclick='cloudmanager.menu.openLink("/oldvm/mandateIndex",this)'>
						<i class="fa fa-television"></i>利旧虚拟机
					</a>
				</li>
				<li name="/pmMgmt/pmIndex">
			        <a href="javascript:;" onclick='cloudmanager.menu.openLink("/pmMgmt/pmIndex",this)'>
			      		<i class="fa fa-cube"></i>物理机
			      	</a>
			     </li>
				<li name="/applicationMgmt/applicationIndex">
			    	<a href="#"  onclick='cloudmanager.menu.openLink("/applicationMgmt/applicationIndex",this)'>
			    		<i class="fa fa-dropbox"></i>资源申请
			    	</a>
			    </li>
			    <li name="/applicationLogMgmt/applicationLogIndex">
			    	<a href="#"  onclick='cloudmanager.menu.openLink("/applicationLogMgmt/applicationLogIndex",this)'>
			    		<i class="fa fa-calendar-check-o"></i>审批历史
			    	</a>
			    </li>
			    <li name="/action/project/projectIndex">
			        <a href="javascript:;"  onclick='cloudmanager.menu.openLink("/action/project/projectIndex")'>
			        <i class="fa fa-list"></i>项目管理
			        </a>
			    </li>          
			    <li name="/accounting/toAccountIndex">
			      	<a href="javascript:;" onclick='cloudmanager.menu.openLink("/accounting/toAccountIndex",this)'>
			      		<i class="fa fa-pie-chart"></i>记账
			      	</a>
			      </li>          
			    <li name="/userMgmt/userIndex">
			   		<a href="#" onclick='cloudmanager.menu.openLink("/userMgmt/userIndex",this)'>
			   			<i class="fa fa-users"></i>用户管理
			   		</a>
			   	</li>
			    <li name="/monitor/orgMonitor">
			    	<a href="javascript:;" onclick='cloudmanager.menu.openLink("/monitor/orgMonitor",this)'>
			    		<i class="fa fa-tachometer"></i>监控管理
			    	</a>
			    </li>        
			    <li name="/alert/toAlertIndex">
			    	<a href="javascript:;" onclick='cloudmanager.menu.openLink("/alert/toAlertIndex",this)'>
			    		<i class="fa fa-bell"></i>告警管理
			    	</a>
			    </li>
				<li name="/task/toTaskIndex">
					<a href="javascript:;" onclick='cloudmanager.menu.openLink("/task/toTaskIndex",this)'>
						<i class="fa fa-tasks"></i>任务管理
					</a>
				</li>
			   	<li name="/log/toLogIndex">
					<a href="javascript:;" onclick='cloudmanager.menu.openLink("/log/toLogIndex",this)'>
						<i class="fa fa-calendar"></i>日志管理
					</a>
				</li>
			</ul>
		</div>
		<div class="main" id="main"></div>
	</div>
</div>

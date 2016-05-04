<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ include file="/WEB-INF/views/includeFile.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="description" content="">
<meta name="author" content="">
<title>Cloudview 2.0 sp1</title>
<link href="${ctx}/resources/lib/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${ctx}/resources/lib/easyui/easyui.css" rel="stylesheet">
<link href="${ctx}/resources/css/jquery.mCustomScrollbar.min.css"
	rel="stylesheet">
<link href="${ctx}/resources/lib/fontawesome/css/font-awesome.min.css"
	rel="stylesheet" />
<link href="${ctx}/resources/lib/easyui/icon.css" rel="stylesheet">
<link href="${ctx}/resources/css/style.css" rel="stylesheet">
<link href="${ctx}/resources/css/widget.css" rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
          <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
<link rel="apple-touch-icon"
	href="${ctx}/resources/image/apple-touch-icon.png">
<link rel="icon" href="${ctx}/resources/image/favicon.ico">
</head>

<body>
	<shiro:hasRole name="operation_manager">
		<%@ include file="/WEB-INF/views/indexOperationManager.jsp"%>
	</shiro:hasRole>
	<shiro:hasRole name="org_manager">
		<%@ include file="/WEB-INF/views/indexOrgManager.jsp"%>
	</shiro:hasRole>
	<shiro:hasRole name="org_user">
		<%@ include file="/WEB-INF/views/indexOrgUser.jsp"%>
	</shiro:hasRole>
	<div class="vm-mask"></div>
	<div class="task-board" id="taskBoard">
		<h2>
			<a href="javascript:;" class="pull-right" onclick="initTask();"><i
				class="fa fa-refresh"></i>刷新</a>最近任务
		</h2>
		<div id="taskDivId">
			<%@ include file="/WEB-INF/views/taskRecent.jsp"%>
		</div>
	</div>

	<div class="frame-loading" style="display: none;">
		<div class="full-sketch-container" id="login_svg">
			<svg xmlns="http://www.w3.org/2000/svg" height="45" width="160"
				viewBox="0 0 160 45">
	            <path class="shade"
					d="M35.11,0.27H14A13.37,13.37,0,0,0,.67,13.64h0A13.31,13.31,0,0,0,14,26.95H31.79a4.45,4.45,0,0,1,0,8.89H5.11a4.45,4.45,0,0,0,0,8.89H31.84A13.28,13.28,0,0,0,45.12,31.44h0A13.39,13.39,0,0,0,31.73,18.05H14a4.45,4.45,0,0,1,0-8.89H35.15A15.72,15.72,0,0,1,48.43,16.5l5,10.94,5.1,11.08c2.41,5.22,4.36,6.21,7.1,6.21,3.21,0,4.74-1.34,7.11-6.23,4.4-9.58,12.76-27.39,12.95-27.81A4.33,4.33,0,0,1,89.85,8h0c2.51,0,4.47,1.63,4.47,4.14V39.52c0,1.22.32,5.21,4.37,5.21s4.46-4,4.46-5.21V17.15c0-5.55,4.11-8.8,9.77-8.8s9.49,3.15,9.49,8.8V39.52c0,1.22.32,5.21,4.37,5.21,1,0,4.47-.38,4.47-5.21V17.15c0-5.55,4.11-8.8,9.77-8.8s9.49,3.15,9.49,8.8V39.52c0,1.22.32,5.21,4.37,5.21,1,0,4.46-.38,4.46-5.21V14.05c0-9.52-10-13.45-18-13.45a21.35,21.35,0,0,0-12.88,4.27l-1,1c-3.49-3.73-8.88-5.29-14.59-5.29A20.1,20.1,0,0,0,100.65,5l-1.36.91c-2.41-3.12-5-5.29-9.65-5.29-6.08,0-10.12,2.76-12.7,8.66L65.72,33.87s-1.67-3.39-2.41-5C61.17,24.39,55,10.73,55,10.73s-3.24-5.4-7.48-7.52A31.08,31.08,0,0,0,35.11.27Z"></path>
	        </svg>
		</div>
	</div>
	<ul id="topContextmMenu" style="display:none;">    
		<li><a href="javascript:;">顺时针旋转</a></li>
		<li><a href="javascript:;">逆时针旋转</a></li>    
		<li><a href="javascript:;">更改颜色</a></li>
		<li><a href="javascript:;">放大</a></li>
		<li><a href="javascript:;">缩小</a></li>
		<li><a href="javascript:;">撤销</a></li>   
		<li><a href="javascript:;">删除</a></li>
	</ul>
	<script src="${ctx}/resources/lib/bootstrap/js/bootstrap.min.js"></script>
	<script src="${ctx}/resources/js/sugon.string.js"></script>
	<script src="${ctx}/resources/js/sugon.timer.js"></script>
	<script src="${ctx}/resources/lib/metismenu/metisMenu.min.js"></script>
	<script src="${ctx}/resources/js/cloudmanager.js"></script>
	<script src="${ctx}/resources/js/menu.js"></script>
	<script src="${ctx}/resources/js/password/password.js"></script>
	<script type="text/javascript">
		function initTask() {
			sugon.load({
				loader : false,
				selector : '#taskDivId',
				action : sugon.rootURL + "/getTask",
				type : 'post',
				callback : function(result) {
				}
			});
		}
		$(function() {
			cloudmanager.menu.init("${path}");
			now = new Date();
			hour = now.getHours();
			var hellowMes = "您好！";
			if (hour < 6) {
				hellowMes = "凌晨好！";
			} else if (hour < 9) {
				hellowMes = "早上好！";
			} else if (hour < 12) {
				hellowMes = "上午好！";
			} else if (hour < 14) {
				hellowMes = "中午好！";
			} else if (hour < 17) {
				hellowMes = "下午好！";
			} else if (hour < 19) {
				hellowMes = "傍晚好！";
			} else if (hour < 22) {
				hellowMes = "晚上好！";
			} else {
				hellowMes = "夜里好！";
			}
			$("#hellowLiId").html(hellowMes);

			/* new sugon.timer({
				time : "10000",
				id : "taskDivId",
				execuFun : function() {
					initTask();
				}
			}); */
			function initTask() {
				sugon.load({
					loader : false,
					selector : '#taskDivId',
					action : sugon.rootURL + "/getTask",
					type : 'post',
					callback : function(result) {
					}
				});
			}
		});

		var EX = {
			addEvent : function(k, v) {
				var me = this;
				if (me.addEventListener)
					me.addEventListener(k, v, false);
				else if (me.attachEvent)
					me.attachEvent("on" + k, v);
				else
					me["on" + k] = v;
			},
			removeEvent : function(k, v) {
				var me = this;
				if (me.removeEventListener)
					me.removeEventListener(k, v, false);
				else if (me.detachEvent)
					me.detachEvent("on" + k, v);
				else
					me["on" + k] = null;
			},
			stop : function(evt) {
				evt = evt || window.event;
				evt.stopPropagation ? evt.stopPropagation()
						: evt.cancelBubble = true;
			}
		};
		document.getElementById('taskBoard').onclick = EX.stop;
		function show() {
			var a = $(this).attr('class');
			if (a == 'active') {
				$(this).removeClass("active");
				$('.vm-mask').fadeOut(200);
				$('#taskBoard').animate({
					'right' : -420
				}, 300);
			} else {
				$(this).addClass("active");
				$('.vm-mask').fadeIn(200);
				$('#taskBoard').animate({
					'right' : 0
				}, 300);
			}
			setTimeout(function() {
				EX.addEvent.call(document, 'click', hide);
			});
		}
		function hide() {
			$('#taskBoardBtn').removeClass("active");
			$('.vm-mask').fadeOut(200);
			$('#taskBoard').animate({
				'right' : -420
			}, 300);
			EX.removeEvent.call(document, 'click', hide);
		}
	</script>
</body>

</html>
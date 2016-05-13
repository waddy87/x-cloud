<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div class="task-board-body">
	<div class="timeline">
		<div class="modal-body">
			<c:forEach items="${taskInfos}" var="taskInfo" varStatus="status">
				<div class="container-fluid timeline-item">
					<div class="row">
						<div class="col-xs-3 date">
							<i class="fa fa-dropbox"></i> ${taskInfo.completDate} <br>
						</div>
						<div class="col-xs-8 content no-top-border">
							<p>
								<strong>${taskInfo.taskinfoName}</strong>
							</p>
							<div class="progress">
								<div
									class="progress-bar progress-bar-success progress-bar-striped"
									role="progressbar" aria-valuenow="100" aria-valuemin="0"
									aria-valuemax="100" style="width: ${taskInfo.process}%;">
									<span class="sr-only">${taskInfo.process}%</span>
								</div>
							</div>
							<p>${taskInfo.process}%
								<br>${taskInfo.description}</p>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>

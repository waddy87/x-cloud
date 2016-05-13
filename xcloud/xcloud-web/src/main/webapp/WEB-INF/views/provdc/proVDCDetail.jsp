<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%-- <%@ include file="/WEB-INF/views/includeFile.jsp"%> --%>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header-detail">
				<a class="btn btn-link" href="#" 
					onclick='cloudmanager.menu.openLink("/proVDC/providerVDCList")'>
					<i class="fa fa-chevron-left"></i>返回
				</a>
				<h2>
					<i class="fa fa-cubes"></i> <span>${proVDCInfo.name }</span>
				</h2>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="vm-tabs">
				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="active">
						<a href="#summary" onclick="cloudmanager.proVDCDetail.proVDCDetailSummary('${proVDCInfo.pVDCId}');"
							aria-controls="summary" role="tab" data-toggle="tab">摘要
						</a>
					</li>
					<li role="presentation">
						<a href="#objects" onclick="cloudmanager.proVDCDetail.proVDCDetailRelevantObjs('${proVDCInfo.pVDCId}');"
							aria-controls="objects" role="tab" data-toggle="tab">相关对象
						</a>
					</li>
				</ul>
				<div class="tab-content" id="proVDCDetailDivId">
					<%@ include file="/WEB-INF/views/provdc/proVDCDetailSummary.jsp"%>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${ctx}/resources/lib/easyui/jquery.min.js"></script>
<script src="${ctx}/resources/lib/easyui/jquery.easyui.min.js"></script>
<script src="${ctx}/resources/js/vdc/proVDCDetail.js"></script> 
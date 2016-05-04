<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<div>
	<div >
<!-- 		<div class="modal-header">
			<button type="button" class="close" >
				<span aria-hidden="true">&times;</span>
			</button>
			<h4 class="modal-title" id="myModalLabel">创建组织</h4>
		</div> -->
		<div class="modal-body">
			<form class="form-horizontal" name="addProjectForm" id="addProjectForm" >
				<div class="form-group">
					<label for="projectName" class="col-sm-4 control-label">项目名称</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" name="projectName" id="projectName" value="sssss"> 
					</div>
				</div>
				<div class="form-group">
					<label for="remarks" class="col-sm-4 control-label">项目备注</label>
					<div class="col-sm-5">
						<input type="text" class="form-control"  name="remarks" id="remarks" value="sssss"> 
					</div>
				</div>
	<!-- 			<div class="modal-footer">
					<button type="button" class="btn btn-default" onclick="cloudmanager.projectManagement.cancelAdd()">取消</button>
					<button type="submit" class="btn btn-primary" onclick="cloudmanager.projectManagement.cancelAdd()">确认</button>
				</div> -->
			</form>		
		</div>
	</div>

</div>
<script type="text/javascript">
	$(function() {
		 cloudmanager.projectManagement.initAdd('${orgId}'); 
	});
</script>
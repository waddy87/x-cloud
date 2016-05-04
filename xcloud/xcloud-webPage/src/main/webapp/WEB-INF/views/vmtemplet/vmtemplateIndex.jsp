<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ include file="/WEB-INF/views/includeFile.jsp"%>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header">
				<h1>模板管理</h1>
				<div class="small">模板管理模板管理模板管理模板管理模板管理模板管理模板管理模板管理模板管理模板管理模板管理</div>
			</div>
		</div>
	</div>
	<div class="row vm-table">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<table id="vmtempletTableID" class="easyui-datagrid">
			</table>
			<div id="vmtempletTB">
				<div class="row">
					<div class="col-lg-6 col-md-6 col-sm-12">
						<a id="btn-synVMTemplet" class="easyui-linkbutton">同步</a>
					</div>
					<div class="col-lg-6 col-md-6 col-sm-12">
						<div class="filter clearfix">
							<div class="filter-tool">
								
								<a role="button" href="#" class="btn btn-default"><i class="fa fa-external-link"></i></a>
								<a role="button" href="#" class="btn btn-default"><i class="fa fa-cog"></i></a>
							</div>
							<div class="filter-search">
								<input class="sugon-searchbox" id="vmtempletSearchInputID"
									 style="width:100%; height:32px;">
								<div id="tableSearch">
									<div data-options="name:'name'">名称</div>
									<div data-options="name:'os'">操作系统</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${ctx}/resources/js/vmtemplet/vmtemplet.js"></script> 
<script type="text/javascript">
$(function() {
	cloudmanager.vmTemplet.init();
});
$(function() {
	$('#btn-synVMTemplet').click(function() {
		
		layer.confirm('确认同步数据？', {
			title:'确认',
			icon: 3,
			btn: ['确认','取消'] //按钮
		}, function(index){
			$('#vmtempletSearchInputID').searchbox("destroy");
			sugon.load({
				selector : '#main',
				type : 'get',
				action : sugon.rootURL + '/templet/synVMTemplet',
				callback : function(result) {
					toastr.success('同步模板数据成功!');
					$("#main").load(sugon.rootURL + '/templet/toVMTempletIndex'); 
					layer.close(index);
				}
			});
		})
		
		
	});
	
});


</script>
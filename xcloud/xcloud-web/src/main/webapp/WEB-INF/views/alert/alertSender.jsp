<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
 <%@ include file="/WEB-INF/views/includeFile.jsp" %>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header">
				<h1>策略管理</h1>
				<div class="small">策略管理策略管理策略管理策略管理策略管理策略管理</div>
			</div>
		</div>
	</div>
	<div class="row vm-table">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<table id="alertSenderTableId" class="easyui-datagrid">
			</table>
			<div id="alertSendertb">
				<div class="row">
					
					<div class="col-lg-3 col-md-3 col-sm-12">
                        <a href="javascript:;" class="easyui-linkbutton" onclick="cloudmanager.alertSender.addSender()">创建</a>
                        <a href="#" class="easyui-linkbutton" onclick="cloudmanager.alertSender.batchEanbleSender(true)">启用</a>
                        <a href="#" class="easyui-linkbutton" onclick="cloudmanager.alertSender.batchEanbleSender(false)">禁用</a>
                        <a href="#" class="easyui-linkbutton" onclick="cloudmanager.alertSender.batchDeleteSender()">删除</a>
                    </div> 
					
					<div class="col-lg-5 col-md-5 col-sm-12">
						<div class="btn-group" data-toggle="buttons">
						  	<label class="btn btn-defalut active">
						    	<input type="radio" name="options1" id="option16" autocomplete="off" checked value="all" onchange="radioSwitch()">全部
						  	</label>
						<!--   	<label class="btn btn-defalut">
						    	<input type="radio" name="options1" id="option17" autocomplete="off" value="nomal" onchange="radioSwitch()">一般
						  	</label> -->
						  	<label class="btn btn-defalut">
						    	<input type="radio" name="options1" id="option18" autocomplete="off" value="warning" onchange="radioSwitch()">警告
						  	</label>
						  	<label class="btn btn-defalut">
						    	<input type="radio" name="options1" id="option19" autocomplete="off" value="critical" onchange="radioSwitch()">严重
						  	</label>
						  <!-- 	<label class="btn btn-defalut">
						    	<input type="radio" name="options1" id="option20" autocomplete="off" value="disaster" onchange="radioSwitch()">灾难
						  	</label> -->
						</div>
						<div class="btn-group" data-toggle="buttons">
						      <label class="btn btn-defalut active">
                                <input type="radio" name="options2" id="option9" autocomplete="off" checked value="all" onchange="radioSwitch()">全部
                            </label>
                            <label class="btn btn-defalut">
                                <input type="radio" name="options2" id="option10" autocomplete="off" value="HostSystem" onchange="radioSwitch()">云主机
                            </label>
                            <label class="btn btn-defalut">
                                <input type="radio" name="options2" id="option11" autocomplete="off" value="VirtualMachine" onchange="radioSwitch()">虚拟机
                            </label>
                            <label class="btn btn-defalut">
                                <input type="radio" name="options2" id="option12" autocomplete="off" value="Datastore" onchange="radioSwitch()">存储
                            </label>
						</div>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-12">
						<div class="filter clearfix">
							<div class="filter-tool">
								<!-- <a role="button" href="#" class="btn btn-default">
									<i class="fa fa-external-link"></i>
								</a>
								<a role="button" href="#" class="btn btn-default">
									<i class="fa fa-cog"></i>
								</a> -->
							</div>
							<div class="filter-search">
								<input id="alertSenderSearchInputId" class="sugon-searchbox"  style="width:90%; height:32px;">
								<div id="alertSendertableSearch">
									<div data-options="name:'name'">对象</div>
								</div>
							</div>
						</div>
					</div>
					
				</div>
			</div>
		</div>
	</div>
</div>
<%-- <script src="${ctx}/resources/js/cloudmanager.js"></script> --%>
<script src="${ctx}/resources/js/alert/alertSender.js"></script> 
<script type="text/javascript">
$(function() {
	cloudmanager.alertSender.init();
});

//选中时刷新查询
function radioSwitch(){
    cloudmanager.alertSender.doSearch($("input[name='name']").val(),"name");
};
</script>
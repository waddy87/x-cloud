<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header">
				<h1>告警概况</h1>
				<div class="small">告警告警告警告警告警告警</div>
			</div>
		</div>
	</div>
	<div class="row vm-table">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<table id="alertIndexTableId" class="easyui-datagrid">
			</table>
			<div id="alertIndextb">
				<div class="row">
				
				    <div class="col-lg-2 col-md-2 col-sm-12">
                        <a href="#" class="easyui-linkbutton" onclick="cloudmanager.alert.batchAquire()">确认</a>
                        <a href="#" class="easyui-linkbutton" onclick="cloudmanager.alert.batchDelete()">删除</a>
                    </div> 
				
					<div class="col-lg-7 col-md-7 col-sm-12">
						<div class="btn-group" data-toggle="buttons" >
						  	<label class="btn btn-defalut">
						    	<input type="radio" name="options" id="option1" autocomplete="off"  value="all" onchange="radioSwitch()">全部
						  	</label>
						  	<label class="btn btn-defalut active" >
						    	<input type="radio" name="options" id="option2" autocomplete="off" checked value="now" onchange="radioSwitch()">实时
						  	</label>
						  	<label class="btn btn-defalut">
						    	<input type="radio" name="options" id="option3" autocomplete="off" value="history" onchange="radioSwitch()">历史
						  	</label>
						</div>
						<div class="btn-group" data-toggle="buttons">
						  	<label class="btn btn-defalut active">
						    	<input type="radio" name="options1" id="option4" autocomplete="off" checked value="all" onchange="radioSwitch()">全部
						  	</label>
						 <!--  	<label class="btn btn-defalut">
						    	<input type="radio" name="options1" id="option5" autocomplete="off" value="nomal" onchange="radioSwitch()">一般
						  	</label> -->
						  	<label class="btn btn-defalut">
						    	<input type="radio" name="options1" id="option6" autocomplete="off" value="warning" onchange="radioSwitch()">警告
						  	</label>
						  	<label class="btn btn-defalut">
						    	<input type="radio" name="options1" id="option7" autocomplete="off" value="critical" onchange="radioSwitch()">严重
						  	</label>
					<!-- 	  	<label class="btn btn-defalut">
						    	<input type="radio" name="options1" id="option8" autocomplete="off" value="disaster" onchange="radioSwitch()">灾难
						  	</label> -->
						</div>
						<div class="btn-group" data-toggle="buttons">
						     <shiro:hasRole name="operation_manager">
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
			                </shiro:hasRole>
			                <shiro:hasRole name="org_manager">
			                    <label class="btn btn-defalut" style="display:none;">
                                    <input type="hidden" name="options2" id="option11" autocomplete="off" checked value="VirtualMachine">虚拟机
                                </label>
			                </shiro:hasRole>
			                <shiro:hasRole name="org_user">
			                     <label class="btn btn-defalut" style="display:none;">
                                    <input type="hidden" name="options2" id="option11" autocomplete="off" checked value="VirtualMachine">虚拟机
                                </label>
			                </shiro:hasRole>
						  
						</div>
					</div>
					<div class="col-lg-3 col-md-3 col-sm-12">
						<div class="filter clearfix">
							<!-- <div class="filter-tool">
								<a role="button" href="#" class="btn btn-default">
									<i class="fa fa-external-link"></i>
								</a>
								<a role="button" href="#" class="btn btn-default">
									<i class="fa fa-cog"></i>
								</a>
							</div> -->
							<div class="filter-search">
								<input id="alertIndexSearchInputId" class="sugon-searchbox"  style="width:100%; height:32px;">
								<div id="alertIndextableSearch">
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
<script src="${ctx}/resources/js/alert/alert.js"></script> 
<script src="${ctx}/resources/lib/easyui/jquery.min.js"></script>
<script src="${ctx}/resources/lib/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
$(function() {
	cloudmanager.alert.init();
	/* console.info($("[name=='options1']:checked")); */
});

//选中时刷新查询
function radioSwitch(){
	cloudmanager.alert.doSearch($("input[name='name']").val(),"name");
};

</script>
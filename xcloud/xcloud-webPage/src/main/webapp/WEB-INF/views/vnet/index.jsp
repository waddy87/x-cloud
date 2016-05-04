<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ include file="/WEB-INF/views/includeFile.jsp"%>
<div class="container-fluid">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="page-header">
				<h1>网络</h1>
				<div class="small">网络网络网络网络网络网络网络网络网络网络网络</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="vm-tabs">
				<ul class="nav nav-tabs" role="tablist">
					<li role="presentation" class="active">
						<a href="#avl"
							aria-controls="avl" role="tab" data-toggle="tab" onclick="cloudmanager.vnet.init()">有效网络
						</a>
					</li>
					<li role="presentation">
						<a href="#unavl"
							aria-controls="unavl" role="tab" data-toggle="tab" onclick="cloudmanager.vnet.initUnavl()">无效网络
						</a>
					</li>
				</ul>
				<div class="tab-content" id="content">
					<div role="tabpanel" class="tab-pane active" id="avl">
						<div class="row vm-table">
							<div class="col-lg-12 col-md-12 col-sm-12">
								<table id="vnetTableId" class="easyui-datagrid">
								</table>
								<div id="vnettb">
									<div class="row">
										<div class="col-lg-6 col-md-6 col-sm-12" id="oper">
											<a href="#" class="easyui-linkbutton" onclick="cloudmanager.vnet.distrabutePage()">分配</a> 
											<!-- -->
											<a href="#"
												class="easyui-linkbutton"
												onclick="cloudmanager.vnet.synNetdata()">同步</a> 
										</div>
										<div class="col-lg-6 col-md-6 col-sm-12">
											<div class="filter clearfix">
											<div class="filter-tool">
					                                <a role="button" href="#" class="btn btn-default"><i class="fa fa-external-link"></i></a>
					                                <a role="button" href="#" class="btn btn-default"><i class="fa fa-cog"></i></a>
					                            </div>
												<div class="filter-search">
												 <input class="sugon-searchbox" id="vnetSearchInputId"
					                                    data-options="prompt:'请输入查询内容',menu:'#tableSearch',searcher:cloudmanager.vnet.doSearch"
					                                    style="width: 100%; height: 32px;"></input>
													<div id="tableSearch">
					                                    <div data-options="name:'name'">名称</div>
					                                </div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="editOrdetailVnet"></div>
<script src="${ctx}/resources/js/vnet/vnet.js"></script>
<script type="text/javascript">
$(function() {
    cloudmanager.vnet.init();
});
</script>
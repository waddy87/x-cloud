<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<%@ include file="/WEB-INF/views/includeFile.jsp"%>
<div class="container-fluid">
			<div class="row">
				<div class="col-lg-12 col-md-12 col-sm-12">
					<div class="page-header">
						<h1>虚拟机</h1>
						<div class="small">虚拟机介绍虚拟机介绍虚拟机介绍虚拟机介绍虚拟机介绍虚拟机介绍虚拟机介绍虚拟机介绍虚拟机介绍虚拟机介绍</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-12">
					<a class="btn btn-default" href="#" role="button" data-toggle="modal" data-target="#myModal-vm-01">创建</a>
					<a class="btn btn-default" href="#">启动</a>
					<a class="btn btn-default" href="#">关机</a>
					<div class="btn-group">
					  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					    更多操作 <span class="caret"></span>
					  </button>
					  <ul class="dropdown-menu">
					  	<li><a href="#">重启</a></li>
					    <li><a href="#">加入网络</a></li>
					    <li><a href="#">更改配置</a></li>
					    <li><a href="#">分配</a></li>
					    <li><a href="#">回收</a></li>
					    <li><a href="#">销毁</a></li>
					  </ul>
					</div>
				</div>
				<div class="col-lg-6 col-md-6 col-sm-12">
					<div class="filter clearfix">
						<div class="filter-tool">
							<a class="btn btn-default" href="#" role="button"><i class="fa fa-refresh"></i></a>
							<a class="btn btn-default" href="#" role="button"><i class="fa fa-external-link"></i></a>
							<a class="btn btn-default" href="#" role="button"><i class="fa fa-cog"></i></a>
						</div>
						<div class="filter-search">
							<div class="input-group">
								<div class="input-group-btn">
									<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										全部类型 <span class="caret"></span>
									</button>
									<ul class="dropdown-menu">
										<li><a href="#">全部类型</a></li>
										<li><a href="#">名称</a></li>
										<li><a href="#">所属集群</a></li>
										<li><a href="#">所属资源池</a></li>
									</ul>
								</div>
								<input type="text" class="form-control" placeholder="请输入查询条件" aria-label="...">
								<span class="input-group-btn">
							        <button class="btn btn-default" type="button"><i class="fa fa-search"></i></button>
							    </span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row vm-table">
				<div class="col-lg-12 col-md-12 col-sm-12">
					<table class="table table-striped table-hover">
						<thead>
							<tr>
								<th><input type="checkbox"></th>
								<th>名称</th>
								<th>状态</th>
								<th>操作系统</th>
								<th>配置</th>
								<th>IP</th>
								<th>所有者</th>
								<th>创建时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th scope="row"><input type="checkbox"></th>
								<td>虚拟机01</td>
								<td>运行中</td>
								<td>centOS 16.0</td>
								<td>cpu:2核 内存:4GB 磁盘:500GB</td>
								<td>10.0.31.224</td>
								<td>大兵哥</td>
								<td>2016-03-14</td>
								<td>
									<a href="#" class="btn btn-link">关机</a>
									<a href="#" class="btn btn-link">重启</a>
									<a href="#" class="btn btn-link">VNC</a>
								</td>
							</tr>
							<tr>
								<th scope="row"><input type="checkbox"></th>
								<td>虚拟机01</td>
								<td>启动中</td>
								<td>centOS 16.0</td>
								<td>cpu:2核 内存:4GB 磁盘:500GB</td>
								<td>10.0.31.224</td>
								<td>大兵哥</td>
								<td>2016-03-14</td>
								<td>
									<a href="#" class="btn btn-link">关机</a>
									<a href="#" class="btn btn-link">重启</a>
									<a href="#" class="btn btn-link">VNC</a>
								</td>
							</tr>
							<tr>
								<th scope="row"><input type="checkbox"></th>
								<td>虚拟机01</td>
								<td>已停止</td>
								<td>centOS 16.0</td>
								<td>cpu:2核 内存:4GB 磁盘:500GB</td>
								<td>10.0.31.224</td>
								<td>大兵哥</td>
								<td>2016-03-14</td>
								<td>
									<a href="#" class="btn btn-link">关机</a>
									<a href="#" class="btn btn-link">重启</a>
									<a href="#" class="btn btn-link">VNC</a>
								</td>
							</tr>
							<tr>
								<th scope="row"><input type="checkbox"></th>
								<td>虚拟机01</td>
								<td>停止中</td>
								<td>centOS 16.0</td>
								<td>cpu:2核 内存:4GB 磁盘:500GB</td>
								<td>10.0.31.224</td>
								<td>大兵哥</td>
								<td>2016-03-14</td>
								<td>
									<a href="#" class="btn btn-link">关机</a>
									<a href="#" class="btn btn-link">重启</a>
									<a href="#" class="btn btn-link">VNC</a>
								</td>
							</tr>
							<tr>
								<th scope="row"><input type="checkbox"></th>
								<td>虚拟机01</td>
								<td>创建中</td>
								<td>centOS 16.0</td>
								<td>cpu:2核 内存:4GB 磁盘:500GB</td>
								<td>10.0.31.224</td>
								<td>大兵哥</td>
								<td>2016-03-14</td>
								<td>
									<a href="#" class="btn btn-link">关机</a>
									<a href="#" class="btn btn-link">重启</a>
									<a href="#" class="btn btn-link">VNC</a>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12 col-md-12 col-sm-12">
					<nav class="vm-pagination">
						<div class="dataTables_length">
							<label>显示 11 到 20 项，共 57 项 每页
								<select name="DataTables_Table_0_length" aria-controls="DataTables_Table_0" class="form-control input-sm">
									<option value="10">10</option>
									<option value="25">25</option>
									<option value="50">50</option>
									<option value="100">100</option>
								</select> 条记录</label>
						</div>
						<ul class="pagination">
							<li class="disabled"><a aria-label="Previous" href="#"><span aria-hidden="true">«</span></a></li>
							<li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a aria-label="Next" href="#"><span aria-hidden="true">»</span></a></li>
						</ul>
					</nav>
				</div>
			</div>
		</div>
<div class="modal fade" id="myModal-vm-01" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog modal-lg" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="myModalLabel">创建虚拟机</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-sm-9">
								<dl class="vm-configure-block">
									<dt>
										<span>VDC</span>
									</dt>
									<dd>
										<div class="vm-configure-row">
											<label>提供者vDC</label>
											<div class="vm-configure-row-con">
												<div class="btn-group" data-toggle="buttons">
													<label class="btn active">
												    		<input type="radio" name="options" id="option1" autocomplete="off" checked> A区
												  	</label>
												  	<label class="btn">
												    		<input type="radio" name="options" id="option2" autocomplete="off"> B区
												  	</label>
												  	<label class="btn">
												    		<input type="radio" name="options" id="option3" autocomplete="off"> C区
												  	</label>
												</div>
											</div>
										</div>
									</dd>
								</dl>
								<dl class="vm-configure-block">
									<dt>
										<span>模板</span>
									</dt>
									<dd>
										<div class="vm-configure-row">
											<label>选择模板:</label>
											<div class="vm-configure-row-con">
												<select class="form-control">
												  	<option>centOS 7.0 64位</option>
												  	<option>centOS 6.0 64位</option>
												  	<option>centOS 6.0 32位</option>
												  	<option>centOS 5.0 64位</option>
												  	<option>centOS 4.0 32位</option>
												</select>
											</div>
										</div>
									</dd>
								</dl>
								<dl class="vm-configure-block">
									<dt>
										<span>配置</span>
									</dt>
									<dd>
										<div class="vm-configure-row">
											<label>CPU:</label>
											<div class="vm-configure-row-con">
												<div id="cpuDivId"></div>
											</div>
										</div>
										<div class="vm-configure-row">
											<label>内存:</label>
											<div class="vm-configure-row-con">
												<div id="memoryDivId"></div>
											</div>
										</div>
									</dd>
								</dl>
								<dl class="vm-configure-block">
									<dt>
										<span>网络</span>
									</dt>
									<dd>
										<div class="vm-configure-row">
											<label>子网:</label>
											<div class="vm-configure-row-con">
												<select class="form-control">
												  	<option>基础子网1</option>
												  	<option>基础子网2</option>
												  	<option>基础子网3</option>
												  	<option>基础子网4</option>
												  	<option>基础子网5</option>
												</select>
											</div>
										</div>
									</dd>
								</dl>
								<dl class="vm-configure-block">
									<dt>
										<span>存储</span>
									</dt>
									<dd>
										<div class="vm-configure-row">
											<label>选择存储池:</label>
											<div class="vm-configure-row-con">
												<div class="">
													<select class="form-control">
													  	<option>存储池1</option>
													  	<option>存储池2</option>
													  	<option>存储池3</option>
													  	<option>存储池4</option>
													  	<option>存储池5</option>
													</select>
												</div>
												<div class="">
													<button class="btn btn-default"><i class="fa fa-plus"></i></button>
													<small>dsdsdsd</small>
												</div>
											</div>
										</div>
									</dd>
								</dl>
								<dl class="vm-configure-block">
									<dt>
										<span>密码</span>
									</dt>
									<dd>
										<div class="vm-configure-row">
											<label>密码设置:</label>
											<div class="vm-configure-row-con">
												<div class="btn-group" data-toggle="buttons">
													<label class="btn active">
												    		<input type="radio" name="options2" id="option7" autocomplete="off" checked> 立即设置
												  	</label>
												  	<label class="btn">
												    		<input type="radio" name="options2" id="option8" autocomplete="off"> 创建后更改
												  	</label>
												</div>
											</div>
										</div>
										<div class="vm-configure-row">
											<label>用户名:</label>
											<div class="vm-configure-row-con">
												<input type="text" class="form-control" placeholder="请输入用户名">
											</div>
										</div>
										<div class="vm-configure-row">
											<label>密码:</label>
											<div class="vm-configure-row-con">
												<input type="password" class="form-control">
											</div>
										</div>
										<div class="vm-configure-row">
											<label>确认密码:</label>
											<div class="vm-configure-row-con">
												<input type="password" class="form-control">
											</div>
										</div>
									</dd>
								</dl>
								<dl class="vm-configure-block">
									<dt>
										<span>数量</span>
									</dt>
									<dd>
										<div class="vm-configure-row">
											<label>创建数量:</label>
											<div class="vm-configure-row-con">
												
											</div>
										</div>
									</dd>
								</dl>
							</div>
							<div class="col-sm-3">
								<div class="vm-configure">
									<h5>当前配置</h5>
									<ul>
										<li>
											<span></span>
											<span></span>
										</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary">确定</button>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			$(function(){
				new sugon.ui.slider({
					selector : '#cpuDivId',
					setting : [{
						"unit": 1,
						"min": 1,
						"max": 8,
						"align":"right",
						"label" : "8核"
					},{
						"unit": 1,
						"min": 8,
						"max": 16,
						"align":"right",
						"label" : "16核"
					},{
						"unit": 1,
						"min": 16,
						"max": 32,
						"align":"right",
						"label" : "32核"
					},{
						"unit": 1,
						"min": 32,
						"max": 64,
						"align":"right",
						"label" : "64核"
					}],
					textAlign : 'bottom',// content bottom
					width : 460,
					hasInput : true,
					inputName : 'cpu_search',
					inputUnit : '核',
					inputHeight : 10,
					inputWidth : 45,
					//backgroundColor : '#F9F9F9',
					//sliderColor : '#E9FBFD',
					defaultValue : 2,
					fontSize : 12,
					height:28
				});
				new sugon.ui.slider({
					selector : '#memoryDivId',
					setting : [{
						"unit": 1,
						"min": 1,
						"max": 4,
						"align":"right",
						"label" : "4GB"
					},{
						"unit": 1,
						"min": 4,
						"max": 16,
						"align":"right",
						"label" : "16GB"
					},{
						"unit": 1,
						"min": 16,
						"max": 32,
						"align":"right",
						"label" : "32GB"
					},{
						"unit": 1,
						"min": 32,
						"max": 64,
						"align":"right",
						"label" : "64GB"
					},{
						"unit": 1,
						"min": 64,
						"max": 128,
						"align":"right",
						"label" : "128GB"
					},{
						"unit": 1,
						"min": 128,
						"max": 256,
						"align":"right",
						"label" : "256GB"
					},{
						"unit": 1,
						"min": 256,
						"max": 512,
						"align":"right",
						"label" : "512GB"
					}],
					textAlign : 'bottom',// content bottom
					width : 460,
					hasInput : true,
					inputName : 'memory_search',
					inputUnit : 'GB',
					inputHeight : 10,
					inputWidth : 45,
					//backgroundColor : '#F9F9F9',
					//sliderColor : '#E9FBFD',
					defaultValue : 20,
					fontSize : 12,
					height:28
				});
			});
		</script>
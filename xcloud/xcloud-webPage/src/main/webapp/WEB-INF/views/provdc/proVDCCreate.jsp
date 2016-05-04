<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<form id="proVDCCreateFormId" action="#">
	<h3>基本信息</h3>
	<fieldset>
		<div class="form-horizontal">
			<div class="form-group">
				<label class="col-sm-3 control-label" for="name"><font class="star">* </font>名称 </label>
			    <div class="col-sm-6">
			    	<input type="text" id="name" name="name" placeholder="请输入名称" class="form-control required" minlength="2" maxlength="50"/>
			    </div>
			    <div class="col-sm-3"></div>
			</div>
			<div class="form-group">
			    <label for="description" class="col-sm-3 control-label"><font class="star">* </font>描述 </label>
			    <div class="col-sm-6">
			    	<textarea id="description" name="description"  class="form-control required" rows="3" minlength="2" maxlength="120" ></textarea>
			    </div>
			    <div class="col-sm-3"></div>
			</div>
		</div>
	</fieldset>
	<h3>配置信息</h3>
	<fieldset>
		<div class="form-horizontal">
			<div class="form-group">
				<label class="col-sm-3 control-label" for="computingPool"><font class="star">* </font>计算池 </label> 
				<div class="col-sm-6">
					<select class="form-control" id="computingPool" name="computingPool">
						<option value="">请选择</option>
						<c:forEach items="${cpList}" var="cpInfo" varStatus="status">
							<option value="${cpInfo.computingPoolId }">${cpInfo.computingPoolId}/${cpInfo.cptName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-3"></div>
			</div>
		</div>
		<div class="form-horizontal" id="computingPoolInfo"/>
	</fieldset>
	<h3>确认信息</h3>
	<fieldset>
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-8">
				<div class="media confirm-addprovdc">
				  	<div class="media-left">
					    <i class="fa fa-info-circle"></i>
				  	</div>
				  	<div class="media-body">
					    <h4 class="media-heading">请确认提供者vDC<span id="nameConfMes"></span>的信息</h4>
					    <div class="vm-tabs">
					    	<ul class="list-group">
					    		<li class="list-group-item">
					    			<dl>
					    				<dt>名称：</dt>
					    				<dd><span id="nameConfMesList"></span></dd>
					    			</dl>
					    		</li>
					    		<li class="list-group-item">
					    			<dl>
					    				<dt>描述：</dt>
					    				<dd><span id="descriptionConfMes"></span></dd>
					    			</dl>
					    		</li>
					    		<li class="list-group-item">
					    			<dl>
					    				<dt>计算池：</dt>
					    				<dd><span id="cpNameConfMes"></span></dd>
					    			</dl>
					    		</li>
					    		<!-- <li class="list-group-item">
					    			<dl>
					    				<dt>计算池vCpu：</dt>
					    				<dd><span id="cpVcpuConfMes"></span></dd>
					    			</dl>
					    		</li>
					    		<li class="list-group-item">
					    			<dl>
					    				<dt>计算池内存：</dt>
					    				<dd><span id="cpMemoryConfMes"></span></dd>
					    			</dl>
					    		</li> -->
					    		<li class="list-group-item">
					    			<dl>
					    				<dt>vCPU(核)：</dt>
					    				<dd><span id="vCPUConfMes"></span></dd>
					    			</dl>
					    		</li>
					    		<li class="list-group-item">
					    			<dl>
					    				<dt>内存(GB)：</dt>
					    				<dd><span id="memorySizeConfMes"></span></dd>
					    			</dl>
					    		</li>
					    		<li class="list-group-item">
					    			<dl>
					    				<dt>存储：</dt>
					    				<dd><div id="storagePoolConfMes"></div></dd>
					    			</dl>
					    		</li>
					    	</ul>
					    </div>
				  	</div>
				</div>
			</div>
			<div class="col-sm-2"></div>
		</div>
	</fieldset>
</form>
<script src="${ctx}/resources/js/vdc/proVDCCreate.js"></script> 
<script type="text/javascript">
	$(function() {
		$.validator.addMethod("computingPoolSelect", function(value, element, arg){
			if(value==""){
				return false;
			}else{
				return true;
			}
		}, "请选择计算池！");
		$.validator.addMethod("string", function(value, element){
			return this.optional(element) ||/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value);
		}, "只能输入汉字、数字、字母！");
		var form = $("#proVDCCreateFormId");
		form.steps({
				headerTag : "h3",
				bodyTag : "fieldset",
				transitionEffect : "slideLeft",
				enableCancelButton:true,
				titleTemplate: '<span class="number">#index#</span> #title#',
				labels: {
					cancel: "取消",
					finish: "完成",
					next: "下一步",
					previous: "上一步",
				},
				onInit: function (event, currentIndex){
					$("#computingPool").change(function(){
						var computingPoolId=$("#computingPool option:selected").val();
						sugon.load({
							selector : '#computingPoolInfo',
							type : 'POST',
							action : sugon.rootURL + '/proVDC/getComputingPoolDetail',
							data:{
								computingPoolId:computingPoolId
							},
							callback : function(result) {
							}
						});
					});
			    },
			    //Fires after the step has change.
			    onStepChanged: function (event, currentIndex, priorIndex){
			    	if(currentIndex==2&&priorIndex==1){//第二页完成后
			    		$("#nameConfMes").text($("#name").val());
			    		$("#nameConfMesList").text($("#name").val());
			    		$("#descriptionConfMes").text($("#description").val());
			    		//计算池
			    		$("#cpNameConfMes").text($("#computingPool option:selected").text());
			    		$.trim($("#memoryTotCapacitySpanId").text().replace('GB',''));//获取计算池cpu，内存
			    		
			    		
			    		$("#vCPUConfMes").text($("#vcpuNum").val());
			    		$("#memorySizeConfMes").text(Number($("#memorySize").val()));
			    		$("#storagePoolConfMes").html($("#proVDCCreateAddStorageDivId").html());
			    		
			    		//存储池
			    		var storagePoolConfMes="";
			    		$("#proVDCCreateAddStorageDivId").find("div").each(function(index,element){
			    			storagePoolConfMes='<p>'+storagePoolConfMes+'<span>'+$(element).text()+'</span></p>';
		    		    });
			    		$("#storagePoolConfMes").html(storagePoolConfMes);
			    	}
			    },
			    
			    //Fires before the step changes and can be used to prevent step changing by returning false. Very useful for form validation.
				onStepChanging: function (event, currentIndex, newIndex) {
					console.info("currentIndex="+currentIndex);
					console.info("newIndex="+newIndex);
					//总是允许倒退,即使当前步骤包含无效的值
					if (currentIndex > newIndex) {
						return true;
					}
					form.validate().settings.ignore = ":disabled,:hidden";
					if(currentIndex==0){
						if(newIndex==1){//第一页完成前
							var formValid=form.valid();
							if(formValid){//表单验证通过
								var tag=false;
								sugon.load({
									async:'on',
									type : 'POST',
									action : sugon.rootURL + '/proVDC/checkProviderVDCName',
									dataType : 'json',
									data:{
										pVDCName:$("#name").val(),
									},
									callback : function(result) {
										console.info(result);
										if(result.success){
											tag=true;
										}else{
											toastr.error('提供者vDC重名！');
										}
									}
								});
								return tag;
							}else{//表单验证不通过
								return false;
							}
						}
					}else if(currentIndex==1){
						if(newIndex==2){//第二页完成前
							var formValid=form.valid();
							if(formValid){//表单验证通过
								if($("#proVDCCreateAddStorageDivId").find("div").size()>0){
									return true;
								}else{
									$("#storagePoolMesDivId").append("<label class='error'>请点击加号，添加存储！</label>");
									return false;
								}
							}else{//表单验证不通过
								return false;
							}
						}
					}else{
						return form.valid();
					}
				},
				onFinishing : function(event, currentIndex) {
					form.validate().settings.ignore = ":disabled";
					return true;
				},
				onCanceled:function(event){
					layer.closeAll();
				},
				onFinished : function(event, currentIndex) {
					var name=$("#name").val();
					var description=$("#description").val();
					var computingPoolId=$("#computingPool option:selected").val();
					var computingPoolName=$("#computingPool option:selected").text();
					var vcpuNum=$("#vcpuNum").val();
					var memorySize=Number($("#memorySize").val())*1024;
					var storageValue="";
					$("#proVDCCreateAddStorageDivId").find("div").each(function(i) {
						storageValue=$(this).attr("name")+"@"+storageValue;
					});
					sugon.load({
						type : 'POST',
						action : sugon.rootURL + '/proVDC/createProVDC',
						dataType : 'json',
						data:{
							name:name,
							description:description,
							computingPoolId:computingPoolId,
							computingPoolName:computingPoolName,
							vcpuNum:vcpuNum,
							memorySize:memorySize,
							storageValue:storageValue
						},
						callback : function(result) {
							if(result.success){
								layer.closeAll();
								$("#proVDCTableId").datagrid('reload');
								toastr.success('创建成功！');
							}else{
								toastr.error(result.message);
							}
							
						}
					});
				}
			}).validate({
				//错误信息提示位置设置
				errorPlacement:function(error,element) {  
					error.appendTo(element.parent().next("div"));
			   	},
				rules: {
					name : {
						string:true
					},
					computingPool : {
						computingPoolSelect:true
					},
				}
			});
	});
</script>
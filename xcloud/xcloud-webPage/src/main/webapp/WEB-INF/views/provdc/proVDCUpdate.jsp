<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<form id="proVDCUpdateFormId" action="#">
	<h3>基本信息</h3>
	<fieldset>
		<div class="form-horizontal">
			<div class="form-group">
				<label class="col-sm-3 control-label" for="name"><font class="star">* </font>名称 </label>
			    <div class="col-sm-6">
			    	<input value="${providerVDC.pVDCId}" type="hidden" id="proVDC" name="proVDC"/>
			    	<input value="${providerVDC.name}" type="text" id="name" name="name" placeholder="请输入名称" class="form-control required" minlength="2" maxlength="50"/>
			    </div>
			    <div class="col-sm-3"></div>
			</div>
			<div class="form-group">
			    <label for="description" class="col-sm-3 control-label"><font class="star">* </font>描述</label>
			    <div class="col-sm-6">
			    	<textarea id="description" name="description"  class="form-control required" rows="3" minlength="2" maxlength="120">${providerVDC.description}</textarea>
			    </div>
			    <div class="col-sm-3"></div>
			</div>
		</div>
	</fieldset>
	<h3>配置信息</h3>
	<fieldset>
		<div class="form-horizontal">
			<div class="form-group">
				<label class="col-sm-3 control-label" for="computingPool">计算池 </label> 
				<div class="col-sm-6">
					<p class="form-control-static">${computingPoolInfo.computingPoolId}/${computingPoolInfo.cptName}</p>
				</div>
				<div class="col-sm-3"></div>
			</div>
		</div>
		<div class="form-horizontal" >
			<div class="row">
				<label class="col-sm-3 control-label">提供者vDC信息</label>
				<div class="col-sm-3">
					<div class="list-group">
					  	<div class="list-group-item"><span class="badge">${providerVDC.vCpuNum} 核</span>CPU总数</div>
					  	<div class="list-group-item"><span class="badge">${providerVDC.vCpuUsed} 核</span>CPU已用</div>	
					  	<div class="list-group-item"><span class="badge">${providerVDC.vCpuOverplus} 核</span>CPU可用</div>
					</div>
				</div>
				<div class="col-sm-3">
					<div class="list-group">
					  	<div class="list-group-item"><span class="badge"><fmt:formatNumber type="number" value="${providerVDC.memorySize/1024}" maxFractionDigits="1"/> GB</span>内存总量</div>
					  	<div class="list-group-item"><span class="badge"><fmt:formatNumber type="number" value="${providerVDC.memoryUsed/1024}" maxFractionDigits="1"/> GB</span>内存已用</div>	
					  	<div class="list-group-item"><span class="badge"><fmt:formatNumber type="number" value="${providerVDC.memoryOverplus/1024}" maxFractionDigits="1"/> GB</span>内存可用</div>
					</div>
				</div>
				<div class="col-sm-3">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="vcpuNum"><font class="star">* </font>vCPU(核)</label>
				<div class="col-sm-6">
					<input value="${providerVDC.vCpuNum }" 
						id="vcpuNum" name="vcpuNum" type="text" class="form-control required digits"/>
				</div>
				<div class="col-sm-3"></div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label" for="memorySize"><font class="star">* </font>内存(GB)</label>
				<div class="col-sm-6">
					<input value="<fmt:formatNumber type="number" value="${providerVDC.memorySize/1024}" maxFractionDigits="1"/>" 
						id="memorySize" name="memorySize" type="text" class="form-control required digits"/>
				</div>
				<div class="col-sm-3"></div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label"><font class="star">* </font>存储 </label>
				<div class="col-sm-2">
				    <select id="storagePool" name="storagePool" class="form-control">
						<option value="">请选择</option>
						<c:forEach items="${storagePoolList}" var="storagePoolInfo" varStatus="status">
						<option value="${storagePoolInfo.spId }">${storagePoolInfo.name }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-sm-3">
					<div class="input-group">
					  	<span class="input-group-addon">大小</span>
					  	<input id="storageSize" name="storageSize" type="text" class="form-control"/>
					  	<span class="input-group-addon">GB</span>
					</div>
				</div>
				<div class="col-sm-1">
					<a class="btn btn-default btn-block" onclick="cloudmanager.proVDCUpdate.addStoragePool();" style="min-width:auto;"><i class="fa fa-plus"></i></a>
				</div>
				<div class="col-sm-3" id="storagePoolMesDivId"></div>
			</div>
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-9" id="storagePoolInfoDivId"></div>
			</div>
			<div class="row">
				<div class="col-sm-3"></div>
				<div class="col-sm-6">
					<div class="list-group" id="proVDCUpdateAddStorageDivId">
						<c:forEach items="${pvdcSpListList}" var="pVDCSpInfo" varStatus="status">
							<div class="input-group" style="margin-bottom:10px;" id="${pVDCSpInfo.spId }" name="oldSpMes">
							  	<span class="input-group-addon" for="${pVDCSpInfo.pVDCStoragePoolId }" title="${pVDCSpInfo.spName }" style="width:150px; text-align:left; overflow:hidden;text-overflow: ellipsis; white-space: nowrap;">${pVDCSpInfo.spName }</span>
							  	<input value="${pVDCSpInfo.spTotal}" id="${pVDCSpInfo.pVDCStoragePoolId },${pVDCSpInfo.spId },${pVDCSpInfo.spUsed }" name="${pVDCSpInfo.pVDCStoragePoolId }" type="text" class="form-control required digits"/>
							  	<input value="${pVDCSpInfo.spTotal}" type="hidden"/>
							  	<span class="input-group-addon">GB</span>
							</div>
						</c:forEach>
					</div>
					<div>
					
					</div>
				</div>
				<div class="col-sm-3"></div>
			</div>
		</div>
	</fieldset>
</form>
<script src="${ctx}/resources/js/vdc/proVDCUpdate.js"></script> 
<script type="text/javascript">
	$(function() {
		$.validator.addMethod("string", function(value, element){
			return this.optional(element) ||/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value);
		}, "只能输入汉字、数字、字母！");
		var form = $("#proVDCUpdateFormId");
		var proVDCJson=$.parseJSON('${providerVDC}');
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
		    onStepChanged: function (event, currentIndex, priorIndex){
		    },
			onStepChanging: function (event, currentIndex, newIndex) {
				//总是允许倒退,即使当前步骤包含无效的值
				if (currentIndex > newIndex) {
					return true;
				}
				form.validate().settings.ignore = ":disabled,:hidden";
				
				
				
				if(currentIndex==0&&newIndex==1){//第二页完成前
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
								pVDCId:$("#proVDC").val(),
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
				}else{
					return form.valid();
				}
			},
			onFinishing : function(event, currentIndex) {
				form.validate().settings.ignore = ":disabled";
				var formValid=form.valid();
				if(formValid){//表单验证通过
					var vcpuNum=Number($("#vcpuNum").val());
					var vCpuUsed=proVDCJson.vCpuUsed;
					if(vcpuNum<vCpuUsed){
						toastr.warning('vCPU核数小于已用vCPU核数，不允许修改！');
						return false;
					}
					var memorySize=Number($("#memorySize").val()*1024);
					var memoryUsed=proVDCJson.memoryUsed;
					if(memorySize<memoryUsed){
						toastr.warning('内存小于已用内存，不允许修改！');
						return false;
					}
					
					var tag=true;
					$("#proVDCUpdateAddStorageDivId").find('div[name="oldSpMes"]').each(function(i) {
						var input0=Number($(this).children('input').get(0).value);
						var input1=Number($(this).children('input').get(1).value);
						if(input0<input1){
							toastr.warning('存储大小不允许减少！');
							tag=false;
							return false;
						}
					});
					return tag;
				}else{
					return false;
				}
			},
			onCanceled:function(event){
				layer.closeAll();
			},
			onFinished : function(event, currentIndex) {
				var pVDCId=$("#proVDC").val();
				var name=$("#name").val();
				var description=$("#description").val();
				var vcpuNum=$("#vcpuNum").val();
				var memorySize=Number($("#memorySize").val())*1024;
				
				var storageValueOld="";
				$("#proVDCUpdateAddStorageDivId").find("input").each(function(i) {
					storageValueOld=$(this).attr("id")+","+$(this).val()+"@"+storageValueOld;
				});
				var storageValueNew="";
				$("#proVDCUpdateAddStorageDivId").find("div").each(function(i) {
					storageValueNew=$(this).attr("name")+"@"+storageValueNew;
				});
				sugon.load({
					type : 'POST',
					action : sugon.rootURL + '/proVDC/updateProVDC',
					dataType : 'json',
					data:{
						pVDCId:pVDCId,
						name:name,
						description:description,
						vcpuNum:vcpuNum,
						memorySize:memorySize,
						storageValueOld:storageValueOld,
						storageValueNew:storageValueNew
					},
					callback : function(result) {
						if(result.success){
							layer.closeAll();
							$("#proVDCTableId").datagrid('reload');
							toastr.success('修改成功！');
						}else{
							toastr.error(result.message);
						}
						
					}
				});
			}
		}).validate({
			//错误信息提示位置设置
			errorPlacement:function(error,element) {
				if(element.parent().attr('class') == 'input-group'){
					error.appendTo(element.parent().parent().next("div"));
				}else{
					error.appendTo(element.parent().next("div"));
				}
		   	},
			rules: {
				name : {
					string:true
				},
			}
		});
		$("#storagePool").change(function(){
			var storagePoolId=$("#storagePool option:selected").val();
			sugon.load({
				type : 'POST',
				action : sugon.rootURL + '/proVDC/getStoragePoolDetail',
				data:{
					storagePoolId:storagePoolId
				},
				dataType:"json",
				callback : function(result) {
					if(result.success){
						$("#storagePoolInfoDivId").text("存储剩余："+result.storagePoolInfo.spSurplus+"GB;"+"存储总量："+result.storagePoolInfo.spTotal+"GB");
					}else{
						
					}
				}
			});
		});
	});
</script>
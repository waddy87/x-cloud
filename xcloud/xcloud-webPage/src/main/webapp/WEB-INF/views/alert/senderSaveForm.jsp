<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>

<style>
   .form-horizontal{overflow:hidden}
</style>
<form class="form-horizontal"  id="saveFormId">
    <div class="form-group">
    <span></span>
    <span></span>
    </div>
    
  	<div class="form-group">
	    <label for="cfq-name" class="col-sm-3 control-label control-label-new">名称</label>
	    <div class="col-sm-7">
	      <input type="text" class="form-control" id="senderName" name="senderName" placeholder="请输入名称" >
	    </div>
  	</div>
  	<div class="form-group">
	    <label for="cfq-source" class="col-sm-3 control-label control-label-new">资源类型</label>
	    <div class="col-sm-7">
	      	<select class="form-control-resType" id="resTypeId" onchange="getResListByResType()" >
			  <option value="all">全部</option>
			  <option value="HostSystem">云主机</option>
			  <option value="VirtualMachine">虚拟机</option>
			  <option value="Datastore">存储</option>        
			</select>
	    </div>
  	</div>
  	<div class="form-group">
	    <label for="cfq-object" class="col-sm-3 control-label control-label-new">对象</label>
	    <div class="col-sm-7">
	      	<select class="form-control-resourceId" id="resourceId" onchange="getAlarmListByRes()">
			  <option value="all">全部</option>
			</select>
	    </div>
  	</div>
  	<div class="form-group">
	    <label for="cfq-alert" class="col-sm-3 control-label control-label-new">告警类型</label>
	    <div class="col-sm-7">
	      	<select class="form-control-alarm" id="alarmId" >
			  <option value="all">全部</option>
			</select>
	    </div>
  	</div>
  	<div class="form-group">
  	     <label for="cfq-source" class="col-sm-3 control-label control-label-new">告警级别</label>
             <div class="col-sm-7">
            <select class="form-control-alertLevel" id="alertLevel"  >
              <option value="all">全部</option>
              <option value="warning">警告</option>
              <option value="critical">严重</option>       
            </select>
        </div>
     <!--  <div class="checkbox">
      	<label for="cfq-ponderance" class="col-sm-3 control-label control-label-new">严重性</label>
      	<div class="col-sm-7" id="cfq-ponderance">
	        <label class="checkbox-inline">
			  <input type="checkbox" value="option1"> 一般
			</label>
			<label class="checkbox-inline">
			  <input type="checkbox" value="option2"> 警告
			</label>
			<label class="checkbox-inline">
			  <input type="checkbox" value="option3"> 严重
			</label>
			<label class="checkbox-inline">
			  <input type="checkbox" value="option4"> 灾难
			</label>
		</div>
      </div> -->
  	</div>
 	<!-- <div class="form-group">
      <div class="checkbox">
      	<label for="cfq-bool" class="col-sm-3 control-label control-label-new">是否发送给用户</label>
      	<div class="col-sm-7" id="cfq-bool">
	        <label class="radio-inline">
			  <input type="radio" name="inlineRadioOptions" id="gj-show" value="option1"> 是
			</label>
			<label class="radio-inline">
			  <input type="radio" checked name="inlineRadioOptions" id="gj-hide" value="option2"> 否
			</label>
		</div>
      </div>
  	</div> -->
  	<div id="gj-user-send">
	  	<div class="form-group">
		    <label for="cfq-send" class="col-sm-3 control-label control-label-new">发送类型</label>
		    <div class="col-sm-7">
		      	<select class="form-control" id="senderType">
				  <option value="0">电子邮件</option>
				  <option value="1">短信</option>																			  
				</select>
		    </div>
  		</div>
  		<div id="gj-user-email">
  			<!-- <div class="form-group">
			    <label for="cfq-SMTP" class="col-sm-3 control-label control-label-new">SMTP服务器</label>
			    <div class="col-sm-7">
			      <input type="text" class="form-control" id="mailSMTP" placeholder="smtp.163.com">
			    </div>
		  	</div> -->
		  	
		  	<div class="form-group">
			    <label for="cfq-rece" class="col-sm-3 control-label control-label-new">收件人</label>
			    <div class="col-sm-4">
			      <input type="text" class="form-control" id="mailReceiver" name="mailReceiver" placeholder="administrator@sugon.com">
			    </div>
			    <div class="col-sm-3">
			      <button type="button" class="btn btn-default" data-dismiss="modal">导入邮箱</button>
			    </div>
		  	</div>
		  	
		  	<div class="form-group">
                <label for="cfq-content" class="col-sm-3 control-label control-label-new">备注</label>
                <div class="col-sm-7">
                  <textarea class="form-control" id="mailContent" rows="2"></textarea>
                </div>
            </div>
  		</div>
  		<!-- <div id="gj-user-message" style="display: none;">
  			<div class="form-group">
			    <label for="cfq-equi" class="col-sm-3 control-label control-label-new">发送设备</label>
			    <div class="col-sm-7">
			      	<select class="form-control" id="smsDevice">
					  <option value="duanxinmao">短信猫</option>
					  <option value="others">第三方短信平台</option>																				  
					</select>
			    </div>
		  	</div>
		 
		  	<div class="form-group">
			    <label for="cfq-rece" class="col-sm-3 control-label control-label-new">收件人</label>
			    <div class="col-sm-4">
			      <input type="text" class="form-control" id="smsReceiver" placeholder="panfei@sugon.com">
			    </div>
			    <div class="col-sm-3">
			      <button type="button" class="btn btn-default" data-dismiss="modal">导入邮箱</button>
			    </div>
		  	</div>
		  	
		  	<div class="form-group">
                <label for="cfq-content" class="col-sm-3 control-label control-label-new">消息内容</label>
                <div class="col-sm-7">
                  <textarea class="form-control" id="smsContent" rows="10"></textarea>
                </div>
            </div>
            
  		</div> -->
	</div>
	<div class="form-group">
      <div class="checkbox">
      	<label for="cfq-open" class="col-sm-3 control-label control-label-new">已启用</label>
      	<div class="col-sm-7" id="cfq-open">
	        <label class="checkbox-inline">
			  <input type="checkbox" value="option1" id="checkStatus" checked="checked">
			</label>																		
		</div>
      </div>
  	</div>
</form>
<script type="text/javascript">

	$(document).ready(function(){
		$("#saveFormId").validate({
			debug:false,
            focusInvalid: true, //当为false时，验证无效时，没有焦点响应  
            onkeyup: true,
            submitHandler:function(form){  
            },
		    // 添加验证规则
		    rules: {
		          senderName: {// 需要进行验证的输入框name
                       required: true,// 验证条件：必填
                       rangelength:[3,20]
                     
                   },
                   mailReceiver: {// 需要进行验证的输入框name
                       required: true,// 验证条件：必填
                       email: true// 验证条件：格式为email
                   }
               },
               messages: {
            	   senderName: {
                       required: "名称不允许为空!",// 验证未通过的消息
                       rangelength: "名称要在3-20位之间"
                   },
                   mailReceiver: {
                       required: "email不允许为空",
                       email: "邮件地址格式错误!"
                   }
               }
		});  
		
		$('#cfq-send').bind('change', function(){
		  	var val = $(this).val();
		  	switch(val){
		  		case '0': $('#gj-user-email').show(); $('#gj-user-message').hide(); break;
		  		case '1': $('#gj-user-message').show(); $('#gj-user-email').hide();
		  	}
		});
	});
	
    function getResListByResType(){
    	 var resType=$("#resTypeId").val();
    	 //触发ajax请求，获取资源类型对应的资源
   	     $.ajax( {  
   	        type : 'GET',  
   	        contentType : 'application/json',  
   	        url : 'alert/queryResList?resType='+ resType,  
   	        dataType : 'json',  
   	        success : function(data) {
   	            console.log(data);
   	            var resourceId = $("#resourceId");
   	            resourceId.empty(); 
   	            resourceId.append($("<option value='all'>" + "全部" + "</option>")); 
   	            for(var i=0;i< data.list.length;i++){
   	            	resourceId.append($("<option value='" + data.id[i] + "'>" + data.list[i] + "</option>")); 
   	            }       
   	        },  
   	        error : function() {  
   	         // alert("error");  
   	        }  
   	      }
   	    );
    }

   	  function getAlarmListByRes(){
          var resourceId=$("#resourceId").val();
          //触发ajax请求，获取资源类型对应的资源
          $.ajax( {  
             type : 'GET',  
             contentType : 'application/json',  
             url : 'alert/queryAlarmList?resource='+ resourceId,  
             dataType : 'json',  
             success : function(data) {
                 console.log(data);
                 var alarmId = $("#alarmId");
                 alarmId.empty(); 
                 alarmId.append($("<option value='all'>" + "全部" + "</option>")); 
                 for(var i=0;i< data.list.length;i++){
                	 alarmId.append($("<option value='" + data.id[i] + "'>" + data.list[i] + "</option>")); 
                 }       
             },  
             error : function() {  
              // alert("error");  
             }  
           }
         );
   	  }

	
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/includeTag.jsp"%>

<script src="${ctx}/resources/lib/jtopo/jtopo-0.4.8-min.js"></script>
<script src="${ctx}/resources/lib/jtopo/toolbar.js"></script>
<div class="container-fluid dashboard">
	<div class="row">
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="alert alert-success alert-dismissible text-center" role="alert">
			  	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			  	<strong>系统正常！</strong>当前系统状态无异常。
			</div>
		</div>
		<div class="col-lg-6 col-md-6 col-sm-12">
			<div class="vm-box">
				<dl class="user clearfix">
					<dd class="user-photo">
						<img src="${ctx}/resources/image/user.png" width="64" height="64" />
					</dd>
					<dt>
						<h2>Hi! ${sessionScope.currentUser.username }</h2>
						<p>${sessionScope.currentUser.email }</p>
					</dt>
					<dd class="user-controller">
						<p>
							<span>未读消息：<a href="">0</a></span>
							<span>待办任务：<a href="">0</a></span>
							<span>工单消息：<a href="">0</a></span>
						</p>
					</dd>
				</dl>
			</div>
			<div class="vm-box">
				<h3>虚拟机统计</h3>
				<div class="clearfix">
					<div class="pull-left" id="vmChartsDashboard" style="width:180px; height:120px;"></div>
					<div class="vm-creat">
						<dl>
							<dt>已创建</dt>
							<dd class="text-success">1</dd>
						</dl>
						<dl>
							<dt>预计可创建</dt>
							<dd>9</dd>
						</dl>
					</div>
					<div class="vm-creat-size">
						<p class="text-right">规格: 4 vCPU, 8 GB 内存 <a href="#">更改</a></p>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-7 col-md-7 col-sm-12">
					<div class="vm-box">
						<h3>用户统计</h3>
						<div class="vm-user">
							<dl>
								<dt>在线</dt>
								<dd class="text-success">1</dd>
							</dl>
							<dl>
								<dt>总数</dt>
								<dd>9</dd>
							</dl>
						</div>
					</div>
				</div>
				<div class="col-lg-5 col-md-5 col-sm-12">
					<div class="vm-box">
						<h3>VDC统计</h3>
						<dl class="vm-vdc">
							<dt>总数</dt>
							<dd>5</dd>
						</dl>
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-6 col-md-6 col-sm-12">
			<div class="vm-box">
				<div class="warning-status">
					<div class="status">
						<dl>
							<dt>告警</dt>
							<dd><a class="text-danger" href="#">18</a></dd>
						</dl>
					</div>
					<div class="status">
						<p>严重：<a href="#"><span class="label label-danger">2</span></a></p>
						<p>警告：<a href="#"><span class="label label-warning">4</span></a></p>
						<p>未知：<a href="#"><span class="label label-default">12</span></a></p>
					</div>
					<div class="status">
						<dl>
							<dt>虚拟化环境</dt>
							<dd>
								<p>正常：<a href="#"><span class="label label-success">1</span></a></p>
								<p>异常：<a href="#"><span class="label label-default">0</span></a></p>
							</dd>
						</dl>
					</div>
				</div>
			</div>
			<div class="vm-box">
				<div class="media">
					<div class="media-left media-middle">
						<i class="fa fa-qrcode"></i>
				  	</div>
				  	<div class="media-body">
					    <h4 class="media-heading">CPU<small>可用:<i id="cpuFreeGHz"></i></small></h4>
					    <div class="progress">
							<div class="progress-bar progress-bar1" role="progressbar" aria-valuenow="" aria-valuemin="0" aria-valuemax="100" style="width: 50%; background-color: #3facca">50%</div>
						</div>
						<h5 class="media-footing"><small>已用:</small><small>总量:</small></h5>
					</div>
				</div>
				<div class="media">
	                <div class="media-left media-middle">
						<i class="fa fa-ticket"></i>
				  	</div>
				  	<div class="media-body">
					    <h4 class="media-heading">内存<small>可用:</small></h4>
					    <div class="progress">
							<div class="progress-bar progress-bar2" role="progressbar" aria-valuenow="" aria-valuemin="0" aria-valuemax="100" style="width: 20%; background-color:#9d50a5;">20%</div>
						</div>
						<h5 class="media-footing"><small>已用:</small><small>总量:</small></h5>
					</div>
	            </div>
	            <div class="media">
	             	<div class="media-left media-middle">
						<i class="fa fa-database"></i>
				  	</div>
			             	<div class="media-body">
			                  <h4 class="media-heading">存储<small>可用:</small></h4>
			                  <div class="progress">
						    <div class="progress-bar progress-bar3" role="progressbar" aria-valuenow="" aria-valuemin="0" aria-valuemax="100" style="width: 30%; background-color: #5cb85c">30%</div>
						</div>
						<h5 class="media-footing"><small>已用:</small><small>总量:</small></h5>
					</div>
				</div>
			</div>
		</div>
		<div class="col-lg-12 col-md-12 col-sm-12">
			<div class="vm-box">
				<h3>资产拓扑</h3>
				<div id="content">
					<canvas height="600" id="canvas" style="display:block; background:url(${ctx}/resources/image/canvasbg.gif) 0 0 repeat;"></canvas>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(window).resize(resizeCanvas);  
function resizeCanvas() {
	var canvas = document.getElementById('canvas');
	$('#canvas').attr("width", $('#content').width());
};
resizeCanvas();
function DrawCluster(clusters,hosts){
	
	var clusterTotal=0;
	var hostTotal=0;
	var vmTotal=0;
	var hostOk=0;
	var hostCritic=0;
	var hostWar=0;
	var hostUnknow=0;
	var vmTotal=0;
	var vmOk=0;
	var vmCritic=0;
	var vmWar=0;
	var vmUnknow=0;
	
	
	var canvas = document.getElementById('canvas'); 
    var stage = new JTopo.Stage(canvas);
    var scene = new JTopo.Scene(stage);
    var currentNode = null;
    showJTopoToobar(stage);
    scene.alpha = 0;
    stage.click(function(event){
        if(event.button == 0){// 右键
            // 关闭弹出菜单（div）
            $("#topContextmMenu").hide();
        }
    });
    $("#topContextmMenu a").click(function(){
        var text = $(this).text();
        if(text == '删除'){
            scene.remove(currentNode);
        }if(text == '撤销'){
            currentNode.restore();
        }else{
            currentNode.save();
        }
        if(text == '更改颜色'){
            currentNode.fillColor = JTopo.util.randomColor();
        }else if(text == '顺时针旋转'){
            currentNode.rotate += 0.5;
        }else if(text == '逆时针旋转'){
            currentNode.rotate -= 0.5;
        }else if(text == '放大'){
            currentNode.scaleX += 0.2;
            currentNode.scaleY += 0.2;
        }else if(text == '缩小'){
            currentNode.scaleX -= 0.2;
            currentNode.scaleY -= 0.2;
        }
        $("#topContextmMenu").hide();
    });
    function addNode(text, pic){
        var node = new JTopo.Node();
        node.setSize(48, 48);
        node.setImage('${ctx}/resources/image/'+ pic +'.png', true);
        node.fontColor = '51,51,51';
        scene.add(node);
        
        node.mouseover(function(){
            this.text = text;
        });
        node.mouseout(function(){
            this.text = null;
        });
        return node;
    }
    function addLink(nodeA, nodeZ){
        var link = new JTopo.FlexionalLink(nodeA, nodeZ);
        link.strokeColor = '204,204,204';
        link.lineWidth = 2;
        scene.add(link);
        return link;
    }
    var rootNode = addNode('DateCenter', 'datecenter');
    function handler(event){
        if(event.button == 2){// 右键
            // 当前位置弹出菜单（div）
            $("#topContextmMenu").css({
                top: event.pageY,
                left: event.pageX
            }).show();    
        }
    }
    rootNode.addEventListener('mouseup', function(event){
        currentNode = this;
        handler(event);
    });
    for(var h = 0; h < hosts.length; h++){
    	hostTotal++;
    	var hostsname = hosts[h].hostName;
    	var hostvms = hosts[h].vmsTopo;
    	var hostsWarning= hosts[h].status;
    	var hostsNode = addNode(hostsname, hosts[h].powerStatus+'physical');
		addLink(rootNode, hostsNode);
		function handler(event){
	        if(event.button == 2){// 右键
	            // 当前位置弹出菜单（div）
	            $("#topContextmMenu").css({
	                top: event.pageY,
	                left: event.pageX
	            }).show();    
	        }
	    }
		hostsNode.addEventListener('mouseup', function(event){
	        currentNode = this;
	        handler(event);
	    });
		if(hostsWarning ==''){
			hostUnknow++;
			hostsNode.alarm = null;
		}else if(hostsWarning =='ok'){
			hostOk++;
			hostsNode.alarm = null;
    	}else if(hostsWarning =='critical'){
    		hostCritic++;
            hostsNode.alarm = '告警';
        }else if(hostsWarning =='warning'){
        	hostWar++;
            hostsNode.alarm = '告警';
        }
        
		for(var v = 0; v < hostvms.length; v++){
			vmTotal++;
			var hostsvmNodeWarning = hostvms[v].status;
			var hostsvmNode = addNode(hostvms[v].vmName, hostvms[v].powerStatus+'vmserver');
			addLink(hostsNode, hostsvmNode);
			if(hostsvmNodeWarning ==''){
	            vmUnknow++;
	            hostsvmNode.alarm = null;
	        }else if(hostsvmNodeWarning =='ok'){
	            vmOk++;
	            hostsvmNode.alarm = null;
	        }else if(hostsvmNodeWarning =='critical'){
	            vmCritic++;
	            hostsvmNode.alarm = '告警';
	        }else if(hostsvmNodeWarning =='warning'){
	            vmWar++;
	            hostsvmNode.alarm = '告警';
	        }
			function handler(event){
		        if(event.button == 2){// 右键
		            // 当前位置弹出菜单（div）
		            $("#topContextmMenu").css({
		                top: event.pageY,
		                left: event.pageX
		            }).show();    
		        }
		    }
			hostsvmNode.addEventListener('mouseup', function(event){
		        currentNode = this;
		        handler(event);
		    });
		}
    }
	 for(var l=0; l<clusters.length; l++ ){  
		 clusterTotal++;
		 var name=clusters[l].clusterName;
		 var id=clusters[l].clusterId;
		 var hostList=clusters[l].hostsTopo;
		 var ClusterNode = addNode(name, 'cluster');
		 addLink(rootNode, ClusterNode);
	        function handler(event){
	            if(event.button == 2){// 右键
	                // 当前位置弹出菜单（div）
	                $("#topContextmMenu").css({
	                    top: event.pageY,
	                    left: event.pageX
	                }).show();    
	            }
	        }
	        ClusterNode.addEventListener('mouseup', function(event){
	            currentNode = this;
	            handler(event);
	        });
		 
		 //创建物理机节点
		 for(var i=0; i<hostList.length; i++){
			 hostTotal++;
			 var PhysicalNodeWarning = hostList[i].status;
			 var PhysicalNode = addNode(hostList[i].hostName, hostList[i].powerStatus+'physical');
			 addLink(ClusterNode, PhysicalNode);
			if(PhysicalNodeWarning ==''){
	            hostUnknow++;
	            PhysicalNode.alarm = null;
	        }else if(PhysicalNodeWarning =='ok'){
	            hostOk++;
	            PhysicalNode.alarm = null;
	        }else if(PhysicalNodeWarning =='critical'){
	            hostCritic++;
	            PhysicalNode.alarm = '告警';
	        }else if(PhysicalNodeWarning =='warning'){
	            hostWar++;
	            PhysicalNode.alarm = '告警';
	        }
		        function handler(event){
		            if(event.button == 2){// 右键
		                // 当前位置弹出菜单（div）
		                $("#topContextmMenu").css({
		                    top: event.pageY,
		                    left: event.pageX
		                }).show();    
		            }
		        }
		        PhysicalNode.addEventListener('mouseup', function(event){
		            currentNode = this;
		            handler(event);
		        });
		        
		        //创建虚拟机节点
		        var vms=hostList[i].vmsTopo;
		        for(var j=0; j<vms.length; j++){
		        	vmTotal++;
		        	var vmsWarning = vms[j].status;
		        	var vmNode = addNode(vms[j].vmName, vms[j].powerStatus+'vmserver');
					addLink(PhysicalNode, vmNode);
					if(vmsWarning ==''||vmsWarning =='ok'){
						vmNode.alarm = null;
					}else{
						vmNode.alarm = '告警';
			    	}
					
					if(vmsWarning ==''){
		                vmUnknow++;
		                vmNode.alarm = null;
		            }else if(vmsWarning =='ok'){
		                vmOk++;
		                vmNode.alarm = null;
		            }else if(vmsWarning =='critical'){
		                vmCritic++;
		                vmNode.alarm = '告警';
		            }else if(vmsWarning =='warning'){
		                vmWar++;
		                vmNode.alarm = '告警';
		            }
					
			        function handler(event){
			            if(event.button == 2){// 右键
			                // 当前位置弹出菜单（div）
			                $("#topContextmMenu").css({
			                    top: event.pageY,
			                    left: event.pageX
			                }).show();  
			            }
			        }
			        vmNode.addEventListener('mouseup', function(event){
			            currentNode = this;
			            handler(event);
			        });
		            
		        }
		    }

	   
		 scene.doLayout(JTopo.layout.TreeLayout('down', 48, 136));
		   
	 }
	 $('#ClusterNum').html(clusterTotal);
     $('#PhysicalNumAll').html(hostTotal);
     $('#PhysicalNumSuccess').html(hostOk);
     $('#PhysicalNumWarning').html(hostWar);
     $('#PhysicalNumDanger').html(hostCritic);
     $('#PhysicalNumUnknow').html(hostUnknow);
     $('#VmServerNumAll').html(vmTotal);
     $('#VmServerNumSuccess').html(vmOk);
     $('#VmServerNumWarning').html(vmWar);
     $('#VmServerNumDanger').html(vmCritic);
     $('#VmServerNumUnknow').html(vmUnknow);
    //添加鼠标动作
	 scene.addEventListener('mouseup', function(e){
         if(e.target && e.target.layout){
        	 scene.doLayout(JTopo.layout.TreeLayout('down', 48, 136)); 
         }                
     });
}
function DrawTopo(){
    var resType=$("#resTypeId").val();
    //触发ajax请求，获取资源类型对应的资源
    $.ajax( {  
       type : 'GET',  
       contentType : 'application/json',  
       url : 'monitor/getTopo',  
       dataType : 'json',  
       success : function(data) {
           console.log(data);
           DrawCluster(data.clusters,data.hosts);
           //DrawHost(data.hosts);
           
       },  
       error : function() {  
        // alert("error");  
       }  
     }
   );
}

var vmChartsDashboard = echarts.init(document.getElementById('vmChartsDashboard'));
var vmChartsDashboardOption = {
	tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        x: 'right',
        y: 'center',
        data:[{
        	name:'运行中',
        	icon:'circle'
        },
        {
        	name:'已停止',
        	icon:'circle'
        },
        {
        	name:'其他',
        	icon:'circle'
        }]
    },
    series: [
        {
            name:'虚拟机数量',
            type:'pie',
            radius: ['50%', '70%'],
            center: ['35%','50%'],
            avoidLabelOverlap: false,
            label: {
                normal: {
                    show: false,
                    position: 'center'
                },
                emphasis: {
                    show: true,
                    textStyle: {
                        fontSize: '18',
                        fontWeight: 'bold'
                    }
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data:[
                {value:335, name:'运行中'},
                {value:310, name:'已停止'},
                {value:234, name:'其他'}
            ]
        }
    ]
};
vmChartsDashboard.setOption(vmChartsDashboardOption);
DrawTopo();
</script>
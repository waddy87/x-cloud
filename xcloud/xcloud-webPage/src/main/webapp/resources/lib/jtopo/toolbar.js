
	
// 页面工具栏
function showJTopoToobar(stage){
	var toobarDiv = $('<div class="jtopo_toolbar">').html(''
//		+'<input type="radio" name="modeRadio" value="normal" checked id="r1"/>'
//		+'<label for="r1"> 默认</label>'
//		+'&nbsp;<input type="radio" name="modeRadio" value="select" id="r2"/><label for="r2"> 框选</label>'
//		+'&nbsp;<input type="radio" name="modeRadio" value="edit" id="r4"/><label for="r4"> 加线</label>'
		+'<div class="jtopo_toolbar_left"><button id="centerButton" title="居中显示"><i class="fa fa-align-center"></i></button>'
//		+'<input type="button" id="fullScreenButton" value="全屏显示"/>'
		+'<button id="zoomOutButton" title="放大"><i class="fa fa-search-plus"></i></button>'
		+'<button id="zoomInButton" title="缩小"><i class="fa fa-search-minus"></i></button>'
		+'<div class="button"><input type="checkbox" id="viewBox"/><label for="viewBox">鸟瞰</label></div>'
		+'<div class="button"><input type="checkbox" id="zoomCheckbox"/><label for="zoomCheckbox">鼠标缩放</label></div></div>'
//		+'&nbsp;&nbsp;<input type="text" id="findText" style="width: 100px;" value="" onkeydown="enterPressHandler(event)">'
//		+ '<input type="button" id="findButton" value=" 查 询 ">' 
//		+ '&nbsp;&nbsp;<input type="button" id="cloneButton" value="选中克隆">'
		+'<div class="jtopo_toolbar_right"><button id="exportButton" title="导出PNG"><i class="fa fa-file-image-o"></i></button>'
		+'<button id="printButton" title="导出PDF"><i class="fa fa-file-pdf-o"></i></button></div>'
		+'<div class="jtopo_toolbar_center">'
		+'<span>集群：<strong class="label label-primary" title="总数" id="ClusterNum"></strong></span>'
		+'<span>物理机：'
		+'<strong class="label label-primary" title="总数" id="PhysicalNumAll"></strong>'
		+'<strong class="label label-success" title="正常" id="PhysicalNumSuccess"></strong>'
		+'<strong class="label label-warning" title="警告" id="PhysicalNumWarning"></strong>'
		+'<strong class="label label-danger" title="严重" id="PhysicalNumDanger"></strong>'
		+'<strong class="label label-default" title="未知" id="PhysicalNumUnknow"></strong>'
		+'</span>'
		+'<span>虚拟机：'
		+'<strong class="label label-primary" title="总数" id="VmServerNumAll"></strong>'
		+'<strong class="label label-success" title="正常" id="VmServerNumSuccess"></strong>'
		+'<strong class="label label-warning" title="警告" id="VmServerNumWarning"></strong>'
		+'<strong class="label label-danger" title="严重" id="VmServerNumDanger"></strong>'
		+'<strong class="label label-default" title="未知" id="VmServerNumUnknow"></strong>'
		+'</span>'
		+'</div>');	
		
	$('#content').prepend(toobarDiv);
	// 工具栏按钮处理
//	$("input[name='modeRadio']").click(function(){			
//		stage.mode = $("input[name='modeRadio']:checked").val();
//	});
	$('#centerButton').click(function(){
		stage.centerAndZoom(); //缩放并居中显示
	});
	$('#zoomOutButton').click(function(){
		stage.zoomOut();
	});
	$('#zoomInButton').click(function(){
		stage.zoomIn();
	});
//	$('#cloneButton').click(function(){
//		stage.saveImageInfo();
//	});
	$('#exportButton').click(function() {
	    stage.saveImageInfo();
	});
	$('#printButton').click(function() {
	    stage.saveImageInfo();
	});
	$('#zoomCheckbox').click(function(){
		if($('#zoomCheckbox').is(':checked')){
			stage.wheelZoom = 1.2; // 设置鼠标缩放比例
		}else{
			stage.wheelZoom = null; // 取消鼠标缩放比例
		}
	});
	$('#viewBox').click(function(){
		if($('#viewBox').is(':checked')){
			stage.eagleEye.visible = true;
		}else{
			stage.eagleEye.visible = false;
		}
	});
//	$('#fullScreenButton').click(function(){
//		runPrefixMethod(stage.canvas, "RequestFullScreen")
//	});

//	window.enterPressHandler = function (event){
//		if(event.keyCode == 13 || event.which == 13){
//			$('#findButton').click();
//		}
//	};
	
	// 查询
//	$('#findButton').click(function(){
//		var text = $('#findText').val().trim();
//		//var nodes = stage.find('node[text="'+text+'"]');
//		var scene = stage.childs[0];
//		var nodes = scene.childs.filter(function(e){ 
//			return e instanceof JTopo.Node; 
//		});
//		nodes = nodes.filter(function(e){
//			if(e.text == null) return false;
//			return e.text.indexOf(text) != -1;
//		});
//		
//		if(nodes.length > 0){
//			var node = nodes[0];
//			node.selected = true;
//			var location = node.getCenterLocation();
//			// 查询到的节点居中显示
//			stage.setCenter(location.x, location.y);
//			
//			function nodeFlash(node, n){
//				if(n == 0) {
//					node.selected = false;
//					return;
//				};
//				node.selected = !node.selected;
//				setTimeout(function(){
//					nodeFlash(node, n-1);
//				}, 300);
//			}
//			
//			// 闪烁几下
//			nodeFlash(node, 6);
//		}
//	});
}

var runPrefixMethod = function(element, method) {
	var usablePrefixMethod;
	["webkit", "moz", "ms", "o", ""].forEach(function(prefix) {
		if (usablePrefixMethod) return;
		if (prefix === "") {
			// 无前缀，方法首字母小写
			method = method.slice(0,1).toLowerCase() + method.slice(1);
		}
		var typePrefixMethod = typeof element[prefix + method];
		if (typePrefixMethod + "" !== "undefined") {
			if (typePrefixMethod === "function") {
				usablePrefixMethod = element[prefix + method]();
			} else {
				usablePrefixMethod = element[prefix + method];
			}
		}
	}
);

return usablePrefixMethod;
};
/*
runPrefixMethod(this, "RequestFullScreen");
if (typeof window.screenX === "number") {
var eleFull = canvas;
eleFull.addEventListener("click", function() {
	if (runPrefixMethod(document, "FullScreen") || runPrefixMethod(document, "IsFullScreen")) {
		runPrefixMethod(document, "CancelFullScreen");
		this.title = this.title.replace("退出", "");
	} else if (runPrefixMethod(this, "RequestFullScreen")) {
		this.title = this.title.replace("点击", "点击退出");
	}
});
} else {
alert("浏览器不支持");
}*/

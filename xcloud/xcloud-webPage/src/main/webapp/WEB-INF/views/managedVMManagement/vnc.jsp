<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/views/includeTag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cloudview 2.0 sp1</title>
<link rel="apple-touch-icon"
	href="${ctx}/resources/image/apple-touch-icon.png">
<link rel="icon" href="${ctx}/resources/image/favicon.ico">
<link href="${ctx}/resources/css/vncDependent/wmks-all.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/css/vncDependent/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/resources/lib/vncDependent/jquery-1.8.3.min.js"></script>
<script src="${ctx}/resources/lib/vncDependent/jquery-ui-1.8.16.min.js"></script>
<script src="${ctx}/resources/lib/vncDependent/wmks.js"></script>
<script>
	var url = '${vncUrl}';
</script>

</head>
<body>
<div id="bar">

<div id="buttonBar" >
<div id="cadButton" class="buttonT">
<div class="buttonR">&nbsp;</div>
<div class="buttonC" style="margin-left:700px;">
<button id="toggle-relativepad" onclick="toggleRelativePad()">Toggle RelativePad</button>
<button id="toggle-keyboard" onclick="toggleKeyboard()">Toggle Keyboard</button>
<button id="console-fullscreen">View Fullscreen</button>
<button id="sendCAD">Send Ctrl+Alt+Delete</button></div>
<div class="buttonL">&nbsp;</div>
</div>
</div>

<div id="vmName">
<span id="vmTitle"><!-- filled programmatically --></span>
</div>
</div>

<div id="console" style="position:absolute;width:800px;height:500px;left:300px;">
</div>

<div id="spinner" style="width:300px; height:300px; opacity:0.6;">
<div class="bar1"></div>
<div class="bar2"></div>
<div class="bar3"></div>
<div class="bar4"></div>
<div class="bar5"></div>
<div class="bar6"></div>
<div class="bar7"></div>
<div class="bar8"></div>
<div class="bar9"></div>
<div class="bar10"></div>
<div class="bar11"></div>
<div class="bar12"></div>
</div>
	
<script>

/* document.getElementById("console").style.position = "absolute"; */

function getURLParameter(name) {
	// This regEx retrieves a URL for anything other than "installVMRC" (escp. for name param "vmName").
	var regEx = (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1];
	return decodeURIComponent(regEx);
}
(function(){

	var console = document.getElementById("console")
	consoleFullScreen = document.getElementById("console-fullscreen");

	// Fullscreen API names are different for each browser
	var prefix = "";

	// Setting the prefix for fullscreen based on browser (Mozilla and Chrome)
	if (console) {
		if (console.mozRequestFullScreen) {
			prefix = "moz";
		} else if (console.webkitRequestFullScreen) {
			prefix = "webkit";
		} else if (console.msRequestFullscreen) {
		 prefix = "ms";
	  }
}

	// Will perform fullscreen request when requested by the user
if (console && consoleFullScreen) {
	consoleFullScreen.addEventListener("click", function (evt) {
	if (console.requestFullscreen) {
		console.requestFullscreen();
	} else if (console.mozRequestFullScreen) {
		console.mozRequestFullScreen();
	} else if (console.webkitRequestFullScreen) {
		console.webkitRequestFullScreen(Element.ALLOW_KEYBOARD_INPUT);
	} else if (console.msRequestFullscreen) {
	 console.msRequestFullscreen();
	}
}, false);
}
})();
</script>
<script>

function rePosition()
{
	var w = window.innerWidth,
	h = window.innerHeight;
	console.log("resize window, the current window size is " + w + " X " + h);
	if(!WMKS.UTIL.isFullscreenNow())
	{
		_wmks.css({"top":$("#bar").outerHeight() + "px","left":"30px"});
		console.log("not full screen mode");
		console.info(h);
		console.info($("#bar").height());
		h -= $("#bar").height()+30;
		w-=60;
	}
	else
	{
		_wmks.css({"top":"60px","left":"300px"});
		console.log("full screen mode");
		w=800;
		h=500;
	}
	/* alert(w+"-========"+h) */
	console.info(w+"-========"+h);
	_wmks.width(w);
	_wmks.height(h);
	_wmks.wmks('rescale');
	console.log("resize console to " + w + " x " + h );
}
function relayout() {
$("#console").height( $(window).height() - $("#bar").outerHeight() );
$("#spinner").css("margin-left",  $("#console").width()/2 - $("#spinner").width() );
if (!WMKS.UTIL.isFullscreenNow()) {
	 _wmks.wmks("rescaleOrResize",true);
}
}

function toggleRelativePad() {
    _wmks.wmks("toggleRelativePad");
}

function toggleKeyboard() {
if (_wmks.wmks("option", "fixANSIEquivalentKeys") == false) {
	_wmks.wmks("option", "fixANSIEquivalentKeys", true);
} else {
	_wmks.wmks("option", "fixANSIEquivalentKeys", false);
}
}

function showRemoteConsoleMessage(message) {
	/* alert(message); */
	console.info("*******************");
	console.info(message);
$('#console').html(message);
$('#console').css('text-align', 'center');
$('#console').css('color', 'black');
$("#bar").slideDown("fast", relayout);
$("#spinner").removeClass("spinner");
}

//If locale is other than en/en_US , only then we need to change keyboardLayout as en is by default
//Currently webmks supports only Japanese, hence we need to check for that only.
function changeKeyboardLanguage(locale){
	if(locale == "ja" || locale == "ja_JP" || locale == "ja-JP"){
		console.log("Changing the keyboard layout to japanese");
		_wmks.wmks("option", "keyboardLayoutId", "ja-JP_106/109");
	}
}

var rtime = new Date();
var timeout = false;
var delta = 200;
//listen for resize events
$(window).resize(function() {
	/* alert(1); */
            rtime = new Date();
            if (timeout === false) {
                timeout = true;
                setTimeout(resizeend, delta);
            }
/* 	setTimeout(function(){
		var w = window.innerWidth,
		h = window.innerHeight;
		console.info(w+"==========="+h);
	},1000); */

/* 	rePosition(); */
});

function resizeend() {
    if (new Date() - rtime < delta) {
        setTimeout(resizeend, delta);
    } else {
        timeout = false;
        rePosition();
    }
}

$(document).ready(function(){
_wmks = $("#console")
 .wmks({"useVNCHandshake" : false, "sendProperMouseWheelDeltas": true,"fitToParent":true,"useNativePixels":true})
 .bind("wmksconnecting", function() {
	console.log("The console is connecting");
	$("#bar").slideUp("slow", relayout);
 })
 .bind("wmksconnected", function() {
	console.log("The console has been connected");
	$("#spinner").removeClass("spinner");
	$("#bar").slideDown("fast", relayout);
 })
 .bind("wmksdisconnected", function(evt, info) {
	console.log("The console has been disconnected");
	console.log(evt, info);
	showRemoteConsoleMessage("控制台已断开连接,请关闭窗口重新打开VNC控制台!");
 })
 .bind("wmkserror", function(evt, errObj) {
	console.log("Error!");
	console.log(evt, errObj);
	
	if(errObj.error) {
	   var idx = errObj.error.lastIndexOf(".") + 1;
	  /*  alert(errObj.error.substr(idx) + " - " + errObj.msg); */
	}
 })
 .bind("wmksiniterror", function(evt, customData) {
	console.log(evt);
	console.log(customData);
	showRemoteConsoleMessage(customData.errorMsg);
 })
 .bind("wmksresolutionchanged", function(canvas) {
	 console.info(canvas);
	console.log("Resolution has changed!");
 })

// if params are provided, no need to show chrome
if (location.search) {
  $("#bar").hide();
  var authd = location.search.substr(1);
  var loc = document.location;
  var path = loc.host + loc.pathname;
  path = path.substring(0, path.indexOf(".html"));
  // path = "ip:9443/vsphere-client/webconsole"
 // _wmks.wmks("connect", "wss://" + path + "/authd?" + authd);
  _wmks.wmks("connect", url);
  $("#spinner").addClass("spinner");
}

//var locale = getURLParameter("locale");
//changeKeyboardLanguage(locale);

//var vmTitleText = getURLParameter("vmName");
//$("#vmTitle").text(vmTitleText);
//document.title=vmTitleText;

$("#sendCAD").click(function() {
  _wmks.wmks('sendKeyCodes', [
	 $.ui.keyCode.CONTROL,
	 $.ui.keyCode.ALT,
	 $.ui.keyCode.DELETE
  ]);
});

});
</script>
</body>
</html>
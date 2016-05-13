cloudmanager.namespace("menu");
cloudmanager.menu={
		openLink:function(link,thiz){
			  /*  $("#main").load(link);  */
			var _this=this;
		 $(".sugon-searchbox").each(function(){
			 if($(this).is(":hidden")&&!$(this).hasClass("included-tab")){
				 console.info("click销毁searchbox");
				 console.info($(this));
				 $(this).searchbox('destroy'); 
			 }			 
		 });
/*		    if($('.sugon-searchbox').is(":hidden")&&!$('.sugon-searchbox').hasClass("included-tab")){
			    $('.sugon-searchbox').searchbox('destroy'); 
		    }*/
	
/*			$('#proVDCSearchInputId').searchbox('destroy');*/
//   		if($("#proVDCCreateDivId").parent().is(":hidden")){
//    			$('#proVDCCreateDivId').dialog("destroy");
//    		}
//    		if($("#proVDCUpdateDivId").parent().is(":hidden")){
//    			$('#proVDCUpdateDivId').dialog("destroy");
//    		}
//		    console.info($('.sugon-dialog').dialog());
    		if($(".sugon-dialog").parent().is(":hidden")){
    			$('.sugon-dialog').dialog("destroy");
    		}
    		console.info($("li.active")!=$(thiz));
    		if($("li.active")!=$(thiz).parent("li")){
    			$("li.active").removeClass("active");   			
    		}
    		$(thiz).parent("li").addClass("active");
		    sugon.load({
		        selector : '#main',
		        type : 'get',
		        action : sugon.rootURL + link,
		        callback : function(result) {
		        	_this.htmlHistory("4",sugon.rootURL + '/login?path='+link,{a:"a",b:"b"});
		        }
		    });
		},
		init:function (path){
			if(path){
				$('.frame-loading').remove();
				$('.sugon-searchbox').searchbox('destroy'); 
	    		if($(".sugonDialog").parent().is(":hidden")){
	    			$('.sugonDialog').dialog("destroy");
	    		}
	    		$("li[name='"+path+"']").each(function(index){
	    			console.info($(this).attr("name"));
	    			console.info($(this).parents("li"));
	    			if($(this).parents("li").length==1){	    				
	    				$(this).parents("li").addClass("active");
	    				$('#systemMenu').metisMenu();
	    				$(this).parents("li").removeClass("active");
	        			$(this).addClass("active");
	    			} 
	    			else{
	        			$(this).addClass("active");
	        			$('#systemMenu').metisMenu();
	    			}
	
	    		});
		        sugon.load({
		            selector : '#main',
		            type : 'get',
		            action : sugon.rootURL + path,
		            callback : function(result) {
		            }
		        });
			}
			else{
				$('.frame-loading').show();
				cloudmanager.menu.showSvg();
			}
		},
		htmlHistory:function(title,url,options){
			var state = {
				    title: title,
				    url: url,
				    options: options
				};
			console.info(document.title);
				window.history.pushState(state, document.title, url);
		},
		showSvg : function (){
			var supportsInlineSvg = (function() {
			    var div = document.createElement('div');
			    div.innerHTML = '<svg/>';
			    return (div.firstChild && div.firstChild.namespaceURI) == 'http://www.w3.org/2000/svg';
			}());
		    if (!supportsInlineSvg) return;
		    document.documentElement.className += ' inline-svg';
		    
		    var svg = document.querySelector('#login_svg svg'),
		        path = document.querySelector('#login_svg svg path'),
		        begin = 0,
		        durations = (function() {
		            var length = path.getTotalLength();
		            var className = path.getAttribute('class') || '';
		            path.style.strokeDasharray = length + ' ' + length;
		            path.style.strokeDashoffset = length;
		            if (className.indexOf('shade') != -1) {
		                return 2;
		            }else {
		                return Math.pow(length, 0.5) * 0.03;
		            }
		        })();
		        path.getBoundingClientRect();
		        path.style.transition = svg.style.WebkitTransition = 'stroke-dashoffset ' + durations + 's ' + begin + 's ease-in-out';
		        path.style.strokeDashoffset = '0';
		        begin += durations + 0.1;
		    
		    setTimeout(function(){
		    	jQuery(".full-sketch-container").addClass('back').delay(2000).fadeOut(500,function(){
		            $(".frame-loading").fadeOut(500,function(){
		                $(this).remove();
		                console.info($("li[name]:first"));
						$("li[name]:first").parents("li").addClass("active");
						$('#systemMenu').metisMenu();
						$("li[name]:first").parents("li").removeClass("active");
						$("li[name]:first a").trigger("click");
		/*					       
				        $("#venvconfig").parents("li").addClass("active");
				        $("#venvconfig").trigger("click");*/
		            });
		        });
		    },2000);
		}
		
}


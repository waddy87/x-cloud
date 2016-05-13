(function(win, jq) {
	var obj = function() {
		this.contentType = 'application/x-www-form-urlencoded; charset=UTF-8';
		this.rootURL = (function() {
			var curWwwPath = window.document.location.href, pathName = window.document.location.pathname, pos = curWwwPath
					.indexOf(pathName), localhostPaht = curWwwPath.substring(0,
					pos);
			return localhostPaht;
		}());
	};
	obj.prototype.init = function(options) {
		jQuery('body').attr('onselectstart','return false;').css('-moz-user-select','none');
		this.rootURL += options.pathName;
	};
	/**
	 * 获取一个时间戳
	 * 
	 */
	obj.prototype.getTimeStamp = function() {
		return new Date().getTime();
	};
	/**
	 * 包装ajax请求参数
	 */
	obj.prototype.actionPackage = function(action) {
		action.indexOf("?") > 0 ? '&dt=' + this.getTimeStamp() : "?dt="
				+ this.getTimeStamp();
		return action;
	};
	/**
	 * frag加载 参数格式 {selector : '' ,action : '' ,dataType : '',type : '',data : ''
	 * ,loader : '' ,callback : '' }
	 * 
	 * 参数说明：
	 * 
	 * @selector 要替换的域
	 * @action 加载内容地址
	 * @dataType 返回数据类型,默认html
	 * @type 请求方式,post/get,默认post
	 * @data 参数
	 * @loader 是否有加载进度条,true/false
	 * @displayMsg 是否显示提示信息,true/false
	 * 
	 * @callback 回调函数
	 * 
	 */
	obj.prototype.load = function(params) {
		params.action = params.action ? this.actionPackage(params.action) : 'undefined';
		this.contentType=params.contentType?params.contentType:"application/x-www-form-urlencoded; charset=UTF-8";
		params.loader = params.loader == false || params.loader == 'false' ? false : true;
		params.type = params.type == 'get' ? 'get' : 'post';	
		params.dataType = (params.dataType && (params.dataType == 'html'
				|| params.dataType == 'json' || params.dataType == 'text'
				|| params.dataType == 'xml' || params.dataType == 'script' || params.dataType == 'jsonp')) ? params.dataType
				: 'html';
		params.displayMsg = params.displayMsg == false || params.displayMsg == 'false' ? false : true;		
		params.async = params.async == "on" ? false : true;		
		params.timeout = params.timeout || 10000;
		params.loadId = null;
		return jQuery
				.ajax({
					contentType : this.contentType,
					url : params.action,
					type : params.type,
					dataType : params.dataType,
					data : params.data,
					async : params.async,
					traditional : true,
					error : function(jqXHR, textStatus, errorThrown){
			            if(textStatus == "timeout"){
			                console.info('加载超时，请稍后重试！');
			            }
			        },
			        beforeSend : function(XHR) {
			        	if (params.loader) {
							params.loadId = layer.load(1,{
								offset: ['35%','48%']
								//time : params.timeout
							});
						}
					},
					complete : function(XHR, TS) {
						if (params.loader) {
							layer.close(params.loadId);
						}
						
						//以下代码是拦截ajax请求，如果session失效，将跳转到登录页面
				        var sessionstatus=XHR.getResponseHeader("sessionstatus");
				        if(sessionstatus=="timeoutajax"){ 
				        	window.location = sugon.rootURL+"/userlogin";  
				        }
					},
					success : function(result) {
						// 回调刷新DIV数据方法
						if (jQuery(params.selector).length > 0) {
							jQuery(params.selector).html(result);
						}
						if (params.callback && typeof (params.callback) === 'function') {
							params.callback(result);
						}
					}
				});
	};
	win.sugon = new obj();	
	/**
	 * 飘框全局配置
	 */
    toastr.options = {  		  
            closeButton: true,  
            debug: false,  
            progressBar: false,  
            positionClass: "toast-top-right",  
            showDuration: "300",  
            hideDuration: "1000",  
            timeOut: "40000",  
            extendedTimeOut: "1000",  
            showEasing: "swing",  
            hideEasing: "linear",  
            showMethod: "fadeIn",  
            hideMethod: "fadeOut" ,
            tapToDismiss:false
        };  
	/**
	 * layer全局配置
	 */    
})(window, jQuery);
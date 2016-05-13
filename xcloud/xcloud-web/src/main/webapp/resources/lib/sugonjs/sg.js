/***Object添加长属性***/
Object.size = function(obj) {
    var size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key)) size++;
    }
    return size;
};
/***Object添加长属性***/
(function(jq,win){
	/** 基类 **/
	var sugon = {
		attr : new Object(),
		fn : new Object(),
		uibase : (function(){
			var base = function(){};
			base.prototype = new Object();
			return base;
		})()
	};
	
	sugon.init = function(options){
		sugon.attr.userName = options.userName;
		cloudview.business.attribute.shade = jQuery(options.maskSelector);
	};
	
	sugon.ui = new sugon.uibase();
	
	/** 项目参数信息 **/
	sugon.attr.userName = null;
	sugon.attr.chartsPath = '/statics/charts/';
	sugon.attr.contentType = "application/x-www-form-urlencoded; charset=UTF-8";
	sugon.attr.rootPath = (function(){
	    var curWwwPath = window.document.location.href;
	    var pathName = window.document.location.pathname;
	    var pos = curWwwPath.indexOf(pathName);
	    var localhostPaht = curWwwPath.substring(0,pos);
	    
	    return localhostPaht;
	}());
	sugon.attr.rootURL = (function(){
		var div = document.createElement('div');
		div.innerHTML = '<a href="./">sadfasdfadsf</a>"';
		var url = div.firstChild.href;
		var curWwwPath = window.document.location.href;
	    var localhostPaht = curWwwPath.substring(0,curWwwPath.indexOf(window.document.location.pathname));
	    return url.substring(localhostPaht.length,url.length - 1);
	}());
	/** 项目参数信息 **/

	/** 创建一个类，必须包含initialize初始方法**/
	sugon.fn.classCreate = function(){
		return function(){
			this.initialize.apply(this,arguments);
		};
	};
	/** 创建一个类，必须包含initialize初始方法**/

	/** 数学函数 **/
	sugon.fn.triangleFunction = function(diameter,angle){
		var offset = {top : 0,left : 0},
		diameter = diameter / 2,
		d = angle;

		angle = d / 2;
		var sin = Math.sin(angle * Math.PI/180);
		var cos = Math.cos(angle * Math.PI/180);

		cos = Math.abs(cos);
		offset.left = sin * diameter / 2;
		offset.top = cos * diameter / 2;
		
		if(d <= 180){
			offset.top = diameter - offset.top;
			offset.left = diameter - offset.left;
		}else if(d > 160){
			offset.top = diameter + offset.top;
			offset.left = diameter - offset.left;
		}
		offset.top = offset.top - 7.5;
		offset.left = offset.left - 11;
		return offset;
	};
	/** 数学函数 **/

	/** 避免js反复加载问题 **/
	sugon.fn.validScript = function(obj,srcs){
		try{
			var o = eval(obj);
			if(typeof(o) === 'undefined'){
				if(srcs instanceof Array){
					jQuery.each(srcs,function(i,temp){
						jQuery('body').append('<script type="text/javascript" src="' + temp + '"></script>');
					});
				}else{
					jQuery('body').append('<script type="text/javascript" src="' + srcs + '"></script>');
				}
			}
		}catch (e){
//			console.info(e.message);
//			var msg = e.message;
//			if(undefined !== msg && msg !== '' && msg.indexOf('not defined')){
//				if(srcs instanceof Array){
//					jQuery.each(srcs,function(i,temp){
//						jQuery('body').append('<script type="text/javascript" src="' + temp + '"></script>');
//					});
//				}else{
//					jQuery('body').append('<script type="text/javascript" src="' + srcs + '"></script>');
//				}
//			}
		} 
	};
	/** 避免js反复加载问题 **/
	
	sugon.fn.ajax = function(options){
		options = jQuery.extend({
			success : function(result){
				console.info('suon.fn.ajax:result:' + result);
			}
		},options);
		jQuery.ajax(options);
	};
	
	/*
	 *mb、gb单位换算
	 *默认mb
	 *保留两位小数
	 *
	 *return number(不带单位)
	 */
	sugon.fn.conversionForNum = function(sizeString){
		var size = 0;
		size = isNaN(sizeString) ? 0 : parseInt(sizeString);
		if(size > (1024 * 1024)){
			size = size / (1024 * 1024);
		}else if(size > 1024){
			size = size / 1024;
		}
		return size.toFixed(2);
	}
	/*
	 *mb、gb、tb单位换算
	 *默认mb
	 *保留两位小数
	 *
	 **return string(带单位)
	 */
	sugon.fn.conversionForStr = function(sizeString){
		var size = 0;
		size = isNaN(sizeString) ? 0 : parseInt(sizeString);
		if(size > (1024 * 1024 * 1024 * 1024)){
			size = (size / (1024 * 1024 * 1024 * 1024)).toFixed(2) + 'TB';
		}else if(size > (1024 * 1024 * 1024)){
			size = (size / (1024 * 1024 * 1024)).toFixed(2) + 'GB';
		}else if(size > (1024 * 1024)){
			size = (size / (1024 * 1024)).toFixed(2) + 'MB';
		}else if(size > 1024){
			size = (size / 1024).toFixed(2) + 'KB';
		}
		return size;
	}
	/** 暂留函数 **/
//	sugon.fn.limit = function(el,str){
//		var _this = this;
//		var self = jQuery(el);
//		if(undefined !== el && undefined !== str){
//			self = jQuery('#' + el).find(str);
//		}
//		self.each(function() {
//			var objString = jQuery(this).text();
//			var objLength = jQuery(this).text().length;
//			var num = jQuery(this).attr("limit");
//			if (objLength > num) {
//				jQuery(this).attr("title", objString);
//				objString = jQuery(this).text(_this.subString(objString, num));
//			}
//		});
//	};
	
//	sugon.fn.rpSpecialCharts = function(v){
//	var rs = "";
//	var regExp = new RegExp("[。~!@#$%\^\+\*&\\\/\?\|:\.<>{}()';=]");
//	for (var i = 0; i < v.length; i++) {
//		rs = rs + v.substr(i, 1).replace(regExp,'');
//	}
//	return rs;
//};
//sugon.fn.validSpecialCharts = function(){
//	var regExp = new RegExp("[。~!@#$%\^\+\*&\\\/\?\|:\.<>{}()';=]");
//	return regExp.test(v);
//};
//sugon.fn.subString = function(str, maxlen){
//	var char_length = 0;
//	var son_str = '';
//	for (var i = 0; i < str.length; i++) {
//		son_str = str.charAt(i);
//		encodeURI(son_str).length > 2 ? char_length += 1 : char_length += 0.5;
//		if (char_length >= maxlen) {
//			var sub_len = char_length == maxlen ? i + 1 : i;
//			var tmp = str.substr(0, sub_len);
//			return tmp + "..";
//		}
//	}
//	return str;
//};
	/** 暂留函数 **/
	
	win['sugon'] = sugon;
})(jQuery,window);
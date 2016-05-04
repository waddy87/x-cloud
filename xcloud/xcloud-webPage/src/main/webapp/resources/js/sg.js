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
	//---------------------------------------------------------
	
	
	
	//---------------------------------------------------------
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
	
	/**
	 * 字符串过滤
	 * 描述：处理类似于 
	 * 删除/添加字符串'value1;value2;value3'中的value2
	 * 
	 * 参数格式
	 * 'value1;value2;value3'中的value2
	 * 
	 * 参数说明：
	 * @src 要处理的源字符串
	 * @target 目标字符串
	 * @options 操作类型
	 * @separator 分割符号
	 */
	sugon.fn.addOrDelString = function(src,target,operate,separator){
		var array = undefined;
		if(typeof(operate) === 'undefined'){
			operate = 'del';
		}
		if(typeof(separator) === 'undefined'){
			separator = ';';
		}
		if(typeof(src) === 'undefined' || '' === src){
			array = new Array();
		}else{
			array = src.split(separator);
		}
		if(operate === 'del'){
			array.splice(array.indexOf(target),1);
		}else if(operate === 'add'){
			array.push(target);
		}
		return array.join(separator);
	};
	
	/**
	 * 去除用字符隔开的字符串中的一项
	 * 描述：处理类似于 
	 * 删除/添加字符串'value1;value2;value3'中的value2
	 * 
	 * 参数格式
	 * 'value1;value2;value3'中的value2
	 * 
	 * 参数说明：
	 * @src 要处理的源字符串
	 * @target 目标字符串
	 * @separator 分割符号
	 */
	sugon.fn.stringRemoveOption = function(src,target,separator){
		
		if(undefined !== src){
			if(undefined === separator){
				separator = ',';
			}
			
			if(src.indexOf(separator + target) > -1){
				src = src.replace(separator + target,'');
			}else if(src.indexOf(target + separator) > -1){
				src = src.replace(target + separator,'');
			}else{
				src = src.replace(target,'');
			}
		}
		return src;
	};
	
	/**
	 * 
	 * 描述：处理类似于 
	 * 批量替换字符串中的字符
	 * 
	 * 参数格式
	 * 'value1;value2;value3'中的value2
	 * 
	 * 参数说明：
	 * @src 要处理的源字符串
	 * @separator 要替换的字符串
	 * @target 要替换成的字符串
	 */
	sugon.fn.splitAll = function(src,separator,target){
		
		if(undefined !== src && undefined !== separator){
			var reg = eval("/" + separator + "/g");
			src = src.replace(reg,target);
		}
		return src;
	};
	
	sugon.fn.jsonArrangement = function(o){
		for(var item in o){
			if((typeof o[item] === 'string' && o[item] === '') || typeof(o[item]) === 'undefined'){
				delete o[item];
			}else if(typeof(o[item]) === 'object'){
				if(o[item] === null){
					delete o[item];
				}else{
					sugon.fn.jsonArrangement(o[item]);
				}
				
			}
		}
		return o;
	};
	sugon.fn.stringToArray = function(str){
		var array = new Array();
		if(!!!str || str.length == 0){
			return null;
		}
		var s = str.substring(1,str.length - 1);
		s = s.replace(/\'/g,'"');
		s = s.replace(/},{/g,'}|{');
		s.split('|').forEach(function(i,temp){
			array.push(jQuery.parseJSON(i));
		});
		return array;
	};
	
	/** 数据处理函数 **/
	sugon.fn.parseDouble = function(v){
		if(typeof v === 'boolean'){
			return v;
		}else if(typeof v === 'string'){
			if(v === 'true'){
				return true;
			}else if(undefined === v || v === 'false'){
				return false;
			}else{
				return false;
			}
		}
	};
	/**
	 * 字符串过滤
	 * 描述：处理类似于 
	 * 删除/添加字符串'value1;value2;value3'中的value2
	 * 
	 * 参数格式
	 * 'value1;value2;value3'中的value2
	 * 
	 * 参数说明：
	 * @src 要处理的源字符串
	 * @target 目标字符串
	 * @options 操作类型
	 * @separator 分割符号
	 */
	sugon.fn.addOrDelString = function(src,target,operate,separator){
		var array = undefined;
		if(typeof(operate) === 'undefined'){
			operate = 'del';
		}
		if(typeof(separator) === 'undefined'){
			separator = ';';
		}
		if(typeof(src) === 'undefined' || '' === src){
			array = new Array();
		}else{
			array = src.split(separator);
		}
		if(operate === 'del'){
			array.splice(array.indexOf(target),1);
		}else if(operate === 'add'){
			array.push(target);
		}
		return array.join(separator);
	};
	
	/**
	 * 去除用字符隔开的字符串中的一项
	 * 描述：处理类似于 
	 * 删除/添加字符串'value1;value2;value3'中的value2
	 * 
	 * 参数格式
	 * 'value1;value2;value3'中的value2
	 * 
	 * 参数说明：
	 * @src 要处理的源字符串
	 * @target 目标字符串
	 * @separator 分割符号
	 */
	sugon.fn.stringRemoveOption = function(src,target,separator){		
		if(undefined !== src){
			if(undefined === separator){
				separator = ',';
			}
			
			if(src.indexOf(separator + target) > -1){
				src = src.replace(separator + target,'');
			}else if(src.indexOf(target + separator) > -1){
				src = src.replace(target + separator,'');
			}else{
				src = src.replace(target,'');
			}
		}
		return src;
	};
	
	/**
	 * 
	 * 描述：处理类似于 
	 * 批量替换字符串中的字符
	 * 
	 * 参数格式
	 * 'value1;value2;value3'中的value2
	 * 
	 * 参数说明：
	 * @src 要处理的源字符串
	 * @separator 要替换的字符串
	 * @target 要替换成的字符串
	 */
	sugon.fn.jsonArrangement = function(o){
		for(var item in o){
			if((typeof o[item] === 'string' && o[item] === '') || typeof(o[item]) === 'undefined'){
				delete o[item];
			}else if(typeof(o[item]) === 'object'){
				if(o[item] === null){
					delete o[item];
				}else{
					sugon.fn.jsonArrangement(o[item]);
				}
				
			}
		}
		return o;
	};
	
	sugon.fn.stringToArray = function(str){
		var array = new Array();
		if(!!!str || str.length == 0){
			return null;
		}
		var s = str.substring(1,str.length - 1);
		s = s.replace(/\'/g,'"');
		s = s.replace(/},{/g,'}|{');
		s.split('|').forEach(function(i,temp){
			array.push(jQuery.parseJSON(i));
		});
		return array;
	};
	
	/** 数据处理函数 **/
	sugon.fn.parseDouble = function(v){
		if(typeof v === 'boolean'){
			return v;
		}else if(typeof v === 'string'){
			if(v === 'true'){
				return true;
			}else if(undefined === v || v === 'false'){
				return false;
			}else{
				return false;
			}
		}
	};
	/** 暂留函数 **/


	
	win['Sugon'] = sugon;
})(jQuery,window);
(function(container, jq) {
	var string = {};
	/**
	 * 字符串过滤 描述：处理类似于 删除/添加字符串'value1;value2;value3'中的value2
	 * 
	 * 参数格式 'value1;value2;value3'中的value2
	 * 
	 * 参数说明：
	 * 
	 * @src 要处理的源字符串
	 * @target 目标字符串
	 * @options 操作类型
	 * @separator 分割符号
	 */
	string.addOrDelString = function(src, target, operate, separator) {
		var array = undefined;
		if (typeof (operate) === 'undefined') {
			operate = 'del';
		}
		if (typeof (separator) === 'undefined') {
			separator = ';';
		}
		if (typeof (src) === 'undefined' || '' === src) {
			array = new Array();
		} else {
			array = src.split(separator);
		}
		if (operate === 'del') {
			array.splice(array.indexOf(target), 1);
		} else if (operate === 'add') {
			array.push(target);
		}
		return array.join(separator);
	};
	/**
	 * 去除用字符隔开的字符串中的一项 描述：处理类似于 删除/添加字符串'value1;value2;value3'中的value2
	 * 
	 * 参数格式 'value1;value2;value3'中的value2
	 * 
	 * 参数说明：
	 * 
	 * @src 要处理的源字符串
	 * @target 目标字符串
	 * @separator 分割符号
	 */
	string.stringRemoveOption = function(src, target, separator) {
		if (undefined !== src) {
			if (undefined === separator) {
				separator = ',';
			}
			if (src.indexOf(separator + target) > -1) {
				src = src.replace(separator + target, '');
			} else if (src.indexOf(target + separator) > -1) {
				src = src.replace(target + separator, '');
			} else {
				src = src.replace(target, '');
			}
		}
		return src;
	};

	/**
	 * 
	 * 描述：处理类似于 批量替换字符串中的字符
	 * 
	 * 参数格式 'value1;value2;value3'中的value2
	 * 
	 * 参数说明：
	 * 
	 * @src 要处理的源字符串
	 * @separator 要替换的字符串
	 * @target 要替换成的字符串
	 */
	string.splitAll = function(src, separator, target) {
		if (undefined !== src && undefined !== separator) {
			var reg = eval("/" + separator + "/g");
			src = src.replace(reg, target);
		}
		return src;
	};
	string.jsonArrangement = function(o) {
		for ( var item in o) {
			if ((typeof o[item] === 'string' && o[item] === '')
					|| typeof (o[item]) === 'undefined') {
				delete o[item];
			} else if (typeof (o[item]) === 'object') {
				if (o[item] === null) {
					delete o[item];
				} else {
					container.jsonArrangement(o[item]);
				}

			}
		}
		return o;
	};
	string.stringToArray = function(str) {
		var array = new Array();
		if (!!!str || str.length == 0) {
			return null;
		}
		var s = str.substring(1, str.length - 1);
		s = s.replace(/\'/g, '"');
		s = s.replace(/},{/g, '}|{');
		s.split('|').forEach(function(i, temp) {
			array.push(jq.parseJSON(i));
		});
		return array;
	};
	/** 数据处理函数 * */
	string.parseDouble = function(v) {
		if (typeof v === 'boolean') {
			return v;
		} else if (typeof v === 'string') {
			if (v === 'true') {
				return true;
			} else if (undefined === v || v === 'false') {
				return false;
			} else {
				return false;
			}
		}
	};
	jq.extend(container,{
		string : string
	});
})(sugon, jQuery);
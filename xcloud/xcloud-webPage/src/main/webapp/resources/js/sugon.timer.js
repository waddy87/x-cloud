(function(container, jq) {
	
	var timer = function(options) {
		this.initialize(options);
	};
	timer.prototype.initialize = function(options) {
		container.string.jsonArrangement(options);
		this.options = jq.extend(true, {
			type : 'loop', // loop or once and trigger
			update : '', // refresh element id
			array : [],
			refreshType : 'frag',// frag銆乮mg
			refreshArray : [],
			time : 1000,
			interval : null,
			id : '',
			action : {
				type : 'get', // get or post
				dataType : 'json', // html or json
				url : '' // url
			},
			execuFun : null
		}, options);

		if (jq('#' + options.id).length === 0) {
			console.info('not find element id!');
		} else {
			this.init();
		}
	};
	timer.prototype.init = function() {
		this.initUpdate();
		if (!!this.options.type && this.options.type !== 'trigger') {
			if (!!this.options.type && this.options.type === 'loop') {
				this.options.interval = setInterval(this.initFun(),this.options.time);
			} else if (!!this.options.type && this.options.type === 'once') {
				this.options.interval = setTimeout(this.initFun(),this.options.time);
			}
			jq('#' + this.options.id).data(this.options.id + '_interval_id', this.options.interval);
		}
	};
	timer.prototype.start = function() {
		this.options.type = this.options.type === 'once' ? this.options.type : 'loop';
		this.init();
	};
	timer.prototype.stop = function() {
		this.clearInterval();
	};
	timer.prototype.initFun = function() {
		var _this = this;
		function interval() {
			var fun = null;
			if (container.getInterVal(_this.options.id) == _this.options.interval) {
				if (typeof (_this.options.execuFun) == 'function') {
					fun = _this.options.execuFun;
					fun();
				} else {
					jq.ajax({
						type : _this.options.action.type,
						dataType : _this.options.action.dataType,
						url : _this.options.action.url,
						success : function(result) {
							_this.refreshEle(result);
						},
						error : function(e) {
							_this.options.array.forEach(function(i) {
								if (undefined !== jq('#' + i)
										&& jq('#' + i).length > 0) {
									jq('#' + i).html('0');
								}
							});
						}
					});
				}
			} else {
				_this.clearInterval();
			}
		}
		return interval;
	};
	timer.prototype.clearInterval = function() {
		if (!!this.options.type && this.options.type === 'loop') {
			clearInterval(this.options.interval);
		} else if (!!this.options.type && this.options.type === 'once') {
			clearTimeout(this.options.interval);
		}
	};
	timer.prototype.initUpdate = function() {
		this.options.array = this.options.update.split(',');
		this.options.refreshArray = this.options.refreshType.split(',');
	};
	timer.prototype.refreshEle = function() {
		var _this = this;
		var f = function() {
		};
		if (_this.options.action.dataType === 'html') {
			_this.options.refresh_frag(_this.options.array[0], result);
		} else if (_this.options.action.dataType === 'json') {
			_this.options.array.forEach(function(temp, i) {
				eval('f = _this.refresh_' + _this.options.refreshArray[i]);
				if (typeof (f) == 'function') {
					f(temp, result);
				}
			});
		}
		if (!!result && result.stop === true) {
			_this.clearInterval();
		}
	};
	timer.prototype.refresh_img = function() {
		jq('#' + selector).attr('src', container.rootURL + result[selector]);
	};

	timer.prototype.refresh_frag = function() {
		jq('#' + selector).html(result[selector]);
	};
	jq.extend(container,{
		timer : timer,
		getInterVal : function(selector) {
			var el = jq('#' + selector);
			return el.length > 0 ? jq('#' + selector).data(selector + '_interval_id') : null;
		}
	});
})(sugon, jQuery);
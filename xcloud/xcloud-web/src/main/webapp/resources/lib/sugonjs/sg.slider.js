/** 可拖动组件 **/
sugon.uibase.prototype.slider = sugon.fn.classCreate();

sugon.fn.getSliderValue = function(selector){
	var el = jQuery(selector);
	if(!!el && el.length > 0){
		return jQuery.data(el.get(0),'sg_slider_' + selector);
	}
	return null;
};

/** slider **/
sugon.uibase.prototype.slider.prototype = {
	initialize : function(options){
		this.defaults = jQuery.extend({
			selector : null,
//				sliderDom : jQuery(selector),
			rangeDom : null,  // _t.sliderDom.find('.range')
			containerDom : null,  //_t.sliderDom.find('.container')
			dragDom : null, // _t.sliderDom.find('.drag'),
			barDom : null,  //_t.sliderDom.find('.bar'),
			blockDom : null,  //_t.sliderDom.find('.block'),
			unitDom : null,  //_t.sliderDom.find('.unit'),
			inputDom : null,
				 
			ismousedown : false,
			diffX : 0,
			distanceX : 0,
			start : 0,
			minWidth : 0,
			maxWidth : 0,
			minValue : 0,
			maxValue : 0,
			currentWidth : 0,
			currentIndex : 0,
			currentValue : 0,
			blockWidth : [],
			block : [],
			hasInput : false,
			changeHandler : function(){},
			inputName : null,
			inputUnit : null,
			inputHeight : 20,
			width : 600,
			height : 20,
			sliderWidth : 0,
			backgroundColor : null,
			sliderColor : null,
			fontSize : '12',
			inputWidth : 22
		},options);
		this.init();
	},
	initData : function(){
		if(!!!this.defaults.width){
			this.defaults.width = this.defaults.sliderDom.width();
		}
		this.defaults.sliderWidth = this.defaults.width;
		
		if(!!this.defaults.fontSize && this.defaults.fontSize - 0 > 12){
			this.defaults.inputWidth = this.defaults.inputWidth + this.defaults.fontSize;
		}

		if(!!this.defaults.hasInput){
			this.defaults.sliderWidth = (this.defaults.width - this.defaults.inputWidth - 5 - 5 - 35);
		}
	},
	initHtml : function(){
		var _t = this;
		
		this.defaults.sliderDom = jQuery(this.defaults.selector);
		
		var ranghm = jQuery('<div>').addClass('range');
		if(!!this.defaults.backgroundColor){
			ranghm.css('background-color',this.defaults.backgroundColor);
		}
		var spanhm = jQuery('<span>').addClass('uc-slider').append(ranghm).width(this.defaults.sliderWidth);
		var containerhm = jQuery('<div>').addClass('container').css('overflow','hidden');
		var currenthm = jQuery('<div>').addClass('current');
		containerhm.append(currenthm);
		if(!!this.defaults.sliderColor){
			currenthm.css('background-color',this.defaults.sliderColor);
		}
		this.defaults.sliderDom.append(spanhm);
		if(!!this.defaults.hasInput){
			var inputhm = jQuery('<input>').width(this.defaults.inputWidth).addClass('uc-input').css('height',!!this.defaults.inputHeight ? this.defaults.inputHeight + 'px' : '').attr('value',0).attr('name',!!this.defaults.inputName ? this.defaults.inputName : this.defaults.sliderDom.attr('id') + '_name');
			if(!!this.defaults.fontSize){
				inputhm.css('font-size',this.defaults.fontSize);
			}
			this.defaults.sliderDom.append(inputhm);
			if(!!this.defaults.inputUnit){
				var unithm = jQuery('<span>').html(this.defaults.inputUnit).css('font-size','12px');
				if(!!this.defaults.fontSize){
					unithm.css('font-size',this.defaults.fontSize);
				}
				this.defaults.sliderDom.append(unithm);
			}
		}else{
			this.defaults.sliderDom.append(spanhm);
		}
		this.defaults.sliderDom = spanhm;

		var len = this.defaults.block.length;
		var w = this.defaults.sliderWidth / len;
		jQuery.each(this.defaults.block,function(i,o){
			currenthm.append(jQuery('<span>').addClass('unit').width(w).html('<div style="' + (!!_t.defaults.fontSize? 'font-size:' + _t.defaults.fontSize + 'px;' : '') + 'text-align:' + (!!o.align ? o.align : 'right') + ';' + (o.align === 'left' ? 'margin-left:5px;' : '') + '">' + o.label + '</div>'));
			ranghm.append(jQuery('<span>').width(w).addClass('block').html('<div><span style="' + (!!_t.defaults.fontSize? 'font-size:' + _t.defaults.fontSize + 'px;' : '') + 'float:' + (!!o.align ? o.align : 'right') + ';' + (o.align ==='left' ? 'margin-left:5px' : '') + '">' + o.label + '</span></div>'));
		});
		ranghm.append(containerhm);
		ranghm.append(jQuery('<div>').addClass('bar'));
		ranghm.append(jQuery('<a>').addClass('drag').attr('target','_self').attr('href','javascript:;').attr('rel','nofollow').attr('hidefocus',''));

		if(!!this.defaults.height){
			spanhm.css('height',this.defaults.height);
			ranghm.css('height',this.defaults.height);
			ranghm.find('.block').css('height',this.defaults.height);

			ranghm.find('.block').find('div').css('height',this.defaults.height);

			containerhm.css('height',this.defaults.height);
			currenthm.css('height',this.defaults.height);
			currenthm.find('.unit').css('height',this.defaults.height);
			ranghm.find('.bar').css('height',this.defaults.height);
			ranghm.find('.drag').css('height',this.defaults.height + 8);
			spanhm.parent().find('input').css('height',this.defaults.height);
			currenthm.find('div').height(this.defaults.height);
		}
		this.defaults.sliderDom.height(this.defaults.height).css('line-height',this.defaults.height + 'px');
		
		jQuery.extend(this.defaults,{
			rangeDom : this.defaults.sliderDom.find('.range'),
			containerDom : this.defaults.sliderDom.find('.container'),
			dragDom : this.defaults.sliderDom.find('.drag'),
			barDom : this.defaults.sliderDom.find('.bar'),
			blockDom : this.defaults.sliderDom.find('.block'),
			unitDom : this.defaults.sliderDom.find('.unit')
		});
	},
    
    initUnit : function(){
    	var _t = this.defaults;
    	var w;
        for(var i = 0, len = _t.blockDom.length; i < len; i++){
        	w = jQuery(_t.blockDom[i]).width();
            jQuery(_t.unitDom[i]).width(w);
            _t.blockWidth.push(w);
            _t.maxWidth += w;
        }
        _t.barDom.width(_t.maxWidth);
        
        _t.currentValue = _t.minValue = !!_t.min ? _t.min : _t.block[0].min;
        _t.maxValue = !!_t.max ? _t.max : _t.block[len - 1].max;
    },
    
    initInput : function(){
		if(this.defaults.hasInput){
			this.defaults.inputDom = jQuery('input[name=' + this.defaults.inputName + ']');
		}
    },
    
    initValue : function(){
        if(!!this.defaults.defaultValue){
        	this.defaults.currentValue = this.defaults.defaultValue - 0;
        }
    	this.valueToWidth();
        this.moveToX();
    },
    
    showValue : function(){
        if(this.defaults.hasInput){
        	this.defaults.inputDom.val(this.defaults.currentValue);
        }
    },
    setValue : function(){
    	jQuery.data(jQuery(this.defaults.selector).get(0),'sg_slider_' + this.defaults.selector,this.defaults.currentValue);
	},
	getValue : function(){
    	return this.defaults.currentValue;
	},
    
    changeInputValue : function(){
    	var th = this;
    	var _t = this.defaults;
        if(_t.hasInput){
        	function handler(){
	            var t = jQuery(this), val = t.val(), reg = /^\d+$/, max, min, unit, idx;
	            
	            min = _t.block[0].min;
	            max = _t.block[_t.block.length - 1].max;
	            
	            if(reg.test(val)){
	            	idx = th.valueToIndex(val);
	            	unit = _t.block[idx].unit;		            	
		            val = val - 0 < min ? min : (val - 0 > max ? max : val);
		            val = Math.ceil(val / unit)	* unit;						            
	            }else{
	            	// val = min;
	            	val = _t.currentValue !== undefined ? _t.currentValue : min;
		        }
		        
		        _t.currentValue = val - 0;
		        th.valueToWidth();
		        th.moveToX();
	            t.val(_t.currentValue);
	        }
        
//            _t.inputDom.blur(handler);
        	_t.inputDom.off('change');
            _t.inputDom.change(handler);							            
        }
    },
    
    showUnit : function(){
    	var _t = this.defaults;
    	_t.unitDom.find('span').css('color', '#93c4e2');
    	_t.unitDom.find('i').css('display', 'none');
        jQuery(_t.unitDom[_t.currentIndex]).find('span').css('color', '#000');
        jQuery(_t.unitDom[_t.currentIndex]).find('i').css('display', 'inline');
   },
    
    setMaxValue : function(val){
    	var _t = this.defaults;
        _t.maxValue = val < _t.block[_t.block.length - 1].max ? val : _t.block[_t.block.length - 1].max;
        if(_t.maxValue < _t.minValue){
	        _t.minValue = _t.maxValue;
        }
        
        return _t;
    },
    
    setMinValue : function(val){
    	var _t = this.defaults;
        _t.minValue = val > _t.block[0].min ? val : _t.block[0].min;
        return _t;
    },
    
    change : function(fun){
        this.defaults.changeHandler = fun;
        return _t;
    },
    					            
    calculateVal : function(){						            
        this.widthToValue();	
        this.showValue();
        this.showUnit();	
    },
    
    moveToX : function(){	
        this.showValue();
        this.showUnit();		            	
        this.defaults.dragDom.stop().animate({
           	'left' : this.defaults.currentWidth - 6
       	}, 200);
       	this.defaults.containerDom.stop().animate({
           	'width' : this.defaults.currentWidth
       	}, 200);
       	this.defaults.changeHandler(this.defaults.currentValue);
       	this.setValue();
    },
    
    valueToIndex : function(v){
    	var _t = this.defaults;
        var len = _t.block.length, idx = 0, i = 0, val;
        
        val = !!v ? v : _t.currentValue;
        
    	for(; i < len; i++){
        	if(val <= _t.block[i].max){
            	idx = i;
            	break;
        	}
    	}
    	
    	return idx;
    },
    
    widthToValue : function(width){
    	var _t = this.defaults;
    	
    	var i = 0, w = 0, len = _t.blockWidth.length, unit, _w, _v;
    	
    	_w = !!width ? width : _t.currentWidth;
    						            	
    	for(; i < len; i++){
        	if(_w <= w + _t.blockWidth[i]){
            	break;
        	}						            	
        	w += _t.blockWidth[i];
    	}
    	
    	unit = _t.block[i];
    	
    	_w = (_w - w) / _t.blockWidth[i];
    	_v = _t.block[i].min + Math.ceil(_w * ((unit.max - unit.min) / unit.unit)) * unit.unit;
    	/* 调整值 */
    	_t.currentValue = _v = _v < _t.minValue ? _t.minValue : (_v > _t.maxValue ? _t.maxValue : _v);
    	
    	_t.currentValue = _v;
    	_t.currentIndex = this.valueToIndex();
    						            
        return _t.currentValue;
    },
    
    valueToWidth : function(value){
    	var _t = this.defaults;
    	
    	var i = 0, w = 0, len, idx = 0, unit, _v;
    	
    	_v = !!value ? value : _t.currentValue;
    	
    	/* 调整值 */
    	_t.currentValue = _v = _v < _t.minValue ? _t.minValue : (_v > _t.maxValue ? _t.maxValue : _v);
    	
    	len = _t.block.length;
    	for(; i < len; i++){
        	if(_v <= _t.block[i].max){
            	idx = i;
            	break;
        	}
    	}
    	
    	for(i = 0, len = idx; i < len; i++){
        	w += _t.blockWidth[i];
    	}
    	
    	unit = _t.block[idx];
    	w += Math.floor((_v - _t.block[idx].min) / (unit.max - unit.min) * _t.blockWidth[idx]);
    	_t.currentWidth = w;
    	_t.currentIndex = idx;

        return w;
    },
    
    value : function(val){
    	this.currentValue = val - 0;
    	this.valueToWidth();
        this.moveToX();
        
        return _t;
    },

	initHandler : function(){
		var _t = this;
		this.defaults.barDom.click(function(ev){
			var width;
			
			_t.defaults.distanceX = _t.defaults.rangeDom.offset().left;
			width = Math.floor(ev.clientX + jQuery(document).scrollLeft() - _t.defaults.distanceX);
			_t.defaults.currentWidth = width < 0 ? 0 : (width > _t.defaults.maxWidth ? _t.defaults.maxWidth : width);
			_t.widthToValue();
			_t.valueToWidth();
			_t.moveToX();
			
		});
		
		_t.defaults.dragDom.mousedown(function(ev){
			
			ev.preventDefault();
			
			_t.defaults.minWidth	= 0;
			_t.defaults.maxWidth;
			_t.defaults.distanceX = _t.defaults.rangeDom.offset().left;
			_t.defaults.diffX = ev.clientX + jQuery(document).scrollLeft() - _t.defaults.dragDom.offset().left;							          					            
			_t.defaults.ismousedown = true;
		});
		
		jQuery(document).mouseup(function(ev){
			
			if(_t.defaults.ismousedown){
				_t.valueToWidth();
				_t.moveToX();
				_t.defaults.ismousedown = false;
			}
		});
		
		jQuery(document).mousemove(function(ev){
			var x;
			
			if(!_t.defaults.ismousedown){
				return;
			}
			x = ev.clientX + jQuery(document).scrollLeft() - _t.defaults.diffX - _t.defaults.distanceX;
			x = x <= 0 ? 0 : (x > _t.defaults.maxWidth ? _t.defaults.maxWidth : x);
			
			
			
			_t.defaults.currentWidth = x > _t.defaults.maxWith ? _t.defaults.maxWith : x;
			
//			console.info(_t.defaults.currentWidth);
			
			_t.defaults.containerDom.css('width', (_t.defaults.currentWidth) > 0 ? _t.defaults.currentWidth : 0);							            
			_t.defaults.dragDom.css('left', x);
			
			_t.widthToValue();
		});
	},
	init : function(){
		var _t = this;
		
//		this.defaults.setting
		
		jQuery.extend(_t.defaults,{
			block : _t.defaults.setting,
			defaults : {
				data : _t.defaults.setting
			}
		});
		_t.initData();
		_t.initHtml();
		_t.initHandler();
		_t.initUnit();
		_t.initInput();
		_t.initValue();
		_t.showValue();
		_t.changeInputValue();
		_t.setValue();
		
//		jQuery.getJSON(this.defaults.setUrl,function(data){
//			jQuery.extend(_t.defaults,{
//				block : data,
//				defaults : {
//					data : data
//				}
//			});
//			_t.initData();
//			_t.initHtml();
//			_t.initHandler();
//			_t.initUnit();
//			_t.initInput();
//			_t.initValue();
//			_t.showValue();
//			_t.changeInputValue();
//			_t.setValue();
//		});
	}
};
/** slider **/
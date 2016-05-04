/** 左右选择框 **/
Sugon.uibase.prototype.selection = Sugon.fn.classCreate();

Sugon.uibase.prototype.selection.prototype = {
	config : {
		self : null,
		leftSelector : null,
		rightSelector : null,
		leftSelf : null,
		rightSelf : null,
		btnSelf : null,
		classes : {
			parentClassName : 'user_sela_body',
			titleClassName : 'user_title',
			leftClassName : 'user_sela dmb_bg',
			rightClassName : 'user_sela dmb_bg',
			btnClassName : 'user_as',
			toRightBtnClassName : 'fa fa-arrow-circle-right',
			toLeftBtnClassName : 'fa fa-arrow-circle-left'
		}
	},
	initialize : function(options){
		this.privates = jQuery.extend(true,{},this.config);
		this.defaults = jQuery.extend(true,{
				classes : this.privates.classes,
				titleSet : {
					leftTitle : '未选择',
					rightTitle : '已选择',
					fontSize : 12,
					height : '22'
				}
			},{
				selector : null,
				size : 5,
				leftData : null,
				rightData : null,
				leftAction : {
					type : 'post',
					dataType : 'json',
					url : null,
					data:null
				},
				label : 'label',
				value : 'value',
				width : 0,
				type : 'multiple',//single
				checkedFun : null
		},options);
		this.privates.self = jQuery('#' + this.defaults.selector);
		if(this.privates.self.length > 0){
			this.init();
		}
	},
	init : function(){
		this.privates.leftSelector = this.defaults.selector + '_left';
		this.privates.rightSelector = this.defaults.selector + '_right';
		this.createStruct();
		this.createOptions();
	},
	createStruct : function(){
		var parent = jQuery('<div class="' + this.defaults.classes.parentClassName + '"></div>');
		if(0 === this.defaults.width){
			parent.width('100%');
		}else{
			parent.width(this.defaults.width);
		}
		this.privates.btnSelf = jQuery('<div class=' + this.defaults.classes.btnClassName + '><a href="javascript:void(0);"><i class="' + this.defaults.classes.toRightBtnClassName + '"></i></a><a href="javascript:void(0);" class="user_Lb1"><i class="' + this.defaults.classes.toLeftBtnClassName + '"></i></a></div>');
		this.privates.leftSelf = jQuery('<select id="' + this.privates.leftSelector + '" class="' + this.defaults.classes.leftClassName + '" size=' + this.defaults.size + '></select>');
		this.privates.rightSelf = jQuery('<select id="' + this.privates.rightSelector + '" class="' + this.defaults.classes.rightClassName + '" size=' + this.defaults.size + '></select>');
		parent.append('<div class="' + this.defaults.classes.titleClassName + '" style="height:' + this.defaults.titleSet.height + 'px;line-height:' + this.defaults.titleSet.height + 'px;"><span style="font-size:' + this.defaults.titleSet.fontSize + 'px">' + this.defaults.titleSet.leftTitle + '</span><span style="font-size:' + this.defaults.titleSet.fontSize + 'px">' + this.defaults.titleSet.rightTitle + '</span></div>').append(this.privates.leftSelf).append(this.privates.btnSelf).append(this.privates.rightSelf);
		this.privates.self.append(parent);
		this.privates.rightSelf = jQuery('#' + this.privates.rightSelector);
	},
	createOptionsByDom : function(dom){
		var liHtml = '';
		var _object = null;
		jQuery.each(dom,function(i){
			_object = jQuery(this);
			if (_object.attr("selected")) {
				liHtml += '<option value="' + _object.attr("value") + '">' + _object.attr("text") + '</option>';
			} else {
				liHtml += '<option value="' + _object.attr("value") + '">' + _object.attr("text") + '</option>';
			}
		});
		return liHtml;
	},
	createOptionsByJson : function(array){
		var _this = this;
		var liHtml = '';
		jQuery.each(array,function(i,temp){
			if (temp.selected) {
				liHtml += '<option value="' + temp[_this.defaults.value] + '">' + temp[_this.defaults.label] + '</option>';
			} else {
				liHtml += '<option value="' + temp[_this.defaults.value] + '">' + temp[_this.defaults.label] + '</option>';
			}
		});
		return liHtml;
	},
	createOptions : function(){
		if(undefined !== this.defaults.rightData && this.defaults.rightData instanceof Array){
			var rightHtml = this.createOptionsByJson(this.defaults.rightData);
			if(null !== rightHtml){
				this.privates.rightSelf.append(rightHtml);
			}
		}
		if(undefined !== this.defaults.leftData && this.defaults.leftData instanceof Array){
			this.render(this.defaults.leftData);
		}else if(null !== this.defaults.leftAction.url){
			this.refresh();
		}
	},
	render : function(result,type){
		var leftHtml = '';
		if(type === 'dom'){
			leftHtml = this.createOptionsByDom(result);
		}else if(undefined !== result && result instanceof Array){
			leftHtml = this.createOptionsByJson(result);
		}else{
			console.info('leftData : 数据格式不正确!');
		}
		if(leftHtml){
			this.privates.leftSelf.append(leftHtml);
		}
		this.setEvents();
		this.dblClickFun();
	},
	createRightHtml:function(result){
		if(result){
			this.privates.rightSelf.append(result);
		}
	},
	eventFun : function(src,target,thiz){
		var canMove = true;
		var leftDom = src.get(0);
		var rightDom = target.get(0);
		var index = leftDom.selectedIndex;
		if('single' === this.defaults.type && target === this.privates.rightSelf){
			if(rightDom.options.length > 0){
				canMove = false;
			}
		}
		if (index >= 0 && canMove) {
			var t = leftDom.options[index].text;
			var op = new Option(t);
			op.value = leftDom.options[index].value;
			op.selected = true;
			leftDom.remove(index);
			rightDom.options.add(op);
			this.setEventByUnit(op, target, src);
			if(null !== this.defaults.checkedFun && typeof(this.defaults.checkedFun) === 'function'){
				this.defaults.checkedFun(op);
			}
		}
	},
	setEventByUnit : function(thiz,src,target){
		var _this = this;
		jQuery(thiz).off('dblclick');
		jQuery(thiz).dblclick(function(){
			_this.eventFun(src, target, thiz);
		});
	},
	dblClickFun : function(){
		var _this = this;
		$.each(this.privates.leftSelf.children(),function(){
			_this.setEventByUnit(this, _this.privates.leftSelf, _this.privates.rightSelf);
		});
		$.each(this.privates.rightSelf.children(),function(){
			_this.setEventByUnit(this, _this.privates.rightSelf, _this.privates.leftSelf);
		});
	},
	setEvents : function(){
		var _this = this;
		var toRightBtn = '.' + _this.defaults.classes.toRightBtnClassName;
		var toLeftBtn = '.' + _this.defaults.classes.toLeftBtnClassName;
		
		if(toRightBtn.indexOf(' ') > 0){
			toRightBtn = Sugon.fn.splitAll(toRightBtn,' ','.');
		}
		if(toLeftBtn.indexOf(' ') > 0){
			toLeftBtn = Sugon.fn.splitAll(toLeftBtn,' ','.');
		}
		jQuery(toRightBtn,_this.privates.btnSelf).parent().off('click');
		jQuery(toRightBtn,_this.privates.btnSelf).parent().on('click',function(e){
			_this.eventFun(_this.privates.leftSelf,_this.privates.rightSelf);
			e.stopPropagation();
		});
		jQuery(toLeftBtn,this.privates.btnSelf).parent().off('click');
		jQuery(toLeftBtn,this.privates.btnSelf).parent().on('click',function(e){
			_this.eventFun(_this.privates.rightSelf,_this.privates.leftSelf);
			e.stopPropagation();
		});
	},
	refresh : function(param,result){
		var _this = this;
		if(undefined === param || null === param){
			param = {};
		}
		if(result){
			jQuery.extend(result,{
				isAppend : false,
				type : 'json'
			});
			
			if(result.isAppend){
				_this.render(result.data, result.data);
			}else{
				_this.clearValue();
				_this.render(result.data, result.data);
			}
		}else{
			//_this.clearValue();
			_this.privates.leftSelf.children().remove();
			jQuery.ajax({
			   type: this.defaults.leftAction.type,
			   dataType : this.defaults.leftAction.dataType,
			   url: this.defaults.leftAction.url,
			   data : this.defaults.leftAction.data,
			   success: function(result){
				   _this.render(result);
			   },
			   error : function(e){
				   
			   }
			});
		}
	},
	clearValue : function(){
		if(this.privates.rightSelf.children().length > 0){
			this.render(this.privates.rightSelf.children(), 'dom');
		}
		this.privates.leftSelf.children().remove();
		this.privates.rightSelf.children().remove();
	},
	getValue : function(separator){
		var v = '';
		if(undefined === separator){
			separator = ';';
		}
		$.each(this.privates.rightSelf.children(),function(){
			v += this.value + separator;
		});
		if('' !== v && v.length > 0){
			v = v.substring(0,v.length - separator.length);
		}else{
			v = undefined;
		}
		return v;
	},
	getText : function(separator){
		var v = '';
		if(undefined === separator){
			separator = ';';
		}
		$.each(this.privates.rightSelf.children(),function(){
			v += this.text + separator;
		});
		if('' !== v && v.length > 0){
			v = v.substring(0,v.length - separator.length);
		}else{
			v = undefined;
		}
		return v;
	}
};
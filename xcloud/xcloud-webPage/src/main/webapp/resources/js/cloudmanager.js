

/**
 * 项目全局对象
 */
var cloudmanager = {
		timer:{
		},
	namespace : function(str){
		var parts = str.split("."),
	    parent = this,
	    _this = this,
	    i=0;
	    
	    for(i = 0; i < parts.length; i++){
	        if(typeof parent[parts[i]] === "undefined"){
	            parent[parts[i]] = {
	            		namespace : function(str){
	            			_this.namespace.call(this,str);
	            		}
	            };
	        }
	        parent = parent[parts[i]];
	    }
	    return parent;
	},
	clearTimer:function(){
		this.timer={};
	}

};
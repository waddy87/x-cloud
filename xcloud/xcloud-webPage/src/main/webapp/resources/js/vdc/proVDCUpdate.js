cloudmanager.namespace("proVDCUpdate");
cloudmanager.proVDCUpdate = {
	addStoragePool:function(){
		$("#storagePoolMesDivId").children("label").remove();
		var storagePoolName=$("#storagePool option:selected").text();
		var storagePoolId=$("#storagePool option:selected").val();
		var storageSize=$("#storageSize").val();
		var tag=true;
		if(storagePoolId==""){
			$("#storagePoolMesDivId").append("<label class='error'>请选择存储池</label>");
			return;
		}
		if(storageSize==""){
			$("#storagePoolMesDivId").append("<label class='error'>必填</label>");
			return;
		}
		if(!(/^(\+|-)?\d+$/.test( storageSize ))||storageSize<0){  
			$("#storagePoolMesDivId").append("<label class='error'>必须为正整数</label>");
			return;
	    }
		$("#proVDCUpdateAddStorageDivId").find("div").each(function(i) {
			if($(this).attr("id")==storagePoolId){
				$("#storagePoolMesDivId").append("<label class='error'>存储池不能重复添加！</label>");
				tag= false;
				return false;
			}
		});
		if(tag){
			var sId=storagePoolId+","+storageSize+","+storagePoolName;
			var sName="存储池："+storagePoolName+" 存储大小："+storageSize+"GB";
			$("#proVDCUpdateAddStorageDivId").append("<div name='"+sId+"' id='"+storagePoolId+"' class='list-group-item'><a href='#' onclick='cloudmanager.proVDCUpdate.delStoragePool(this)' class='pull-right'><i class='fa fa-times-circle'></i></a>"+sName+"</div>");
		}
	},
	delStoragePool:function(thiz){
		$(thiz).parent('div').remove();
	},
}
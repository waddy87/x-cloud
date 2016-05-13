cloudmanager.namespace("mandateVMManagement");
var pageNum = 0;//记录当前页
cloudmanager.mandateVMManagement={
        //虚拟机详情历史数据
        historyelementId:['jq-history-cpu','jq-history-mem','jq-history-disk','jq-history-net'],
        historyshow:['Cpu使用率(%)','内存使用率(%)','磁盘IO(KB/s)','网络IO(KB/s)'],
        historyproperties:['cpuUsage','memUsage','diskTps','netTps'],

    init:function() {
        $('#mandateVMTableId').datagrid({
            url : sugon.rootURL + '/oldvm/mandatedoldvms',
            method : 'get',
            striped : true,
            fitColumns : true,
            singleSelect : false,
            rownumbers : false,
            pagination : true,
            pageNumber : 1,
            nowrap : false,
            pageSize : 10,
            toolbar : '#mandateVMtb',
            pageList : [ 5, 10, 20, 50, 100 ],
            queryParams : {},
            loadMsg : "加载中.....",
            showFooter : true,
            checkOnSelect : false,//禁止单击行选中复选框
            columns:[[{
                field:"id",
                checkbox:true,
                width:50
            }, {
                field:"name",
                title:"虚拟机名称",
                align:"left",
                width:'10%',
                formatter:cloudmanager.mandateVMManagement.formatName,
                sortable:true,
                resizable:true
            }, {
                field:"os",
                title:"操作系统",
                align:"center",
                width:'10%',
            }, {
                field:"config",
                title:"配置",
                align:"center",
                width:'18%',
                formatter:cloudmanager.mandateVMManagement.formatConfig,
                sortable:true,
                resizable:true
            }, {
                field:"orgName",
                title:"所属组织",
                align:"center",
                width:'5%'
            }, {
                field:"ipAddr",
                title:"IP",
                align:"center",
                width:'10%'
            }, {
                field:"powerStatus",
                title:"电源状态",
                align:"center",
                width:'5%',
                formatter:cloudmanager.mandateVMManagement.formatPowerStatus
            }, {
                field:"assignData",
                title:"是否分配",
                align:"center",
                width:'5%'
            }, {
                field:"operate",
                title:"操作",
                align:"center",
                width:'20%',
                formatter:cloudmanager.mandateVMManagement.formatOper
            }
            ]],
            onBeforeLoad : function(param) {
                checkBoxes = [];
                //获得选中的对象
                var checkedItems = $("#mandateVMTableId").datagrid('getChecked');
                //获得当前页
                var pageNumber = $('#mandateVMTableId').datagrid('getPager').data("pagination").options.pageNumber;
                if (pageNum != pageNumber) {
                    //页码不一致，不用记录被选中的对象
                    pageNum = pageNumber;
                } else {
                    //页码一致，记录选中对象的行号
                    $.each(checkedItems, function(index, item) {
                        checkBoxes.push($("#mandateVMTableId").datagrid('getRowIndex', item));
                    });
                }
            },
            onLoadSuccess : function(data) {
                $.each(checkBoxes, function(index, item) {
                    console.info("index:" + item);
                    $("#mandateVMTableId").datagrid('checkRow', item);
                });
            },
            onLoadError : function() {
            },
            onClickCell : function(rowIndex, field, value) {
                $('#mandateVMTableId').datagrid('clearSelections');
            }
        });
        
        var p = $('#mandateVMTableId').datagrid('getPager');
        $(p).pagination({
            layout:['list','sep','first','prev','links','next','last','sep','refresh','manual'],
            displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
            beforePageText:'跳转到第 ',
            afterPageText:' 页',

        });
        $('#mandateVMSearchInputId').searchbox({
            searcher:function(value,name){
                var queryParams = $('#mandateVMTableId').datagrid('options').queryParams;
                queryParams.name = value;
                $("#mandateVMTableId").datagrid('reload');
            },
            menu:'#tableSearch',
            prompt:'请输入查询内容'
        });
        $('#mandateVMSearchInputId').searchbox('addClearBtn', 'icon-clear');
    },
    formatPowerStatus : function(val, row, index) {
        var powerStatus = "";
        if(row.powerStatus == "on") {
            powerStatus = "运行中";
//            powerStatus = "<span class=\"zt-green\">正常";
        } else if (row.powerStatus == "off") {
            powerStatus = "已关闭";
        } else if(row.powerStatus == "suspend") {
            powerStatus = "已挂起";
        } else {
            powerStatus = "未知";
        }
        return powerStatus;
    },
    formatOper : function(val, row, index) {
        return '<a href="#" onclick="cloudmanager.mandateVMManagement.assignOldVM(\''
                + row.id + '\',\''+row.name+'\',\''+row.orgName+'\')">分配</a>'+
                '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="cloudmanager.mandateVMManagement.recycleOldVM(\''
                + row.id + '\',\''+row.orgName+'\')">回收</a>'+
                '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="cloudmanager.mandateVMManagement.deleteOldVM(\''
                + row.id + '\')">删除</a>'+
                '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="cloudmanager.mandateVMManagement.startOldVM(\''
                + row.id + '\')">启动</a>'+
                '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="cloudmanager.mandateVMManagement.stopOldVM(\''
                + row.id + '\')">停止</a>'+
                '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="cloudmanager.mandateVMManagement.restartOldVM(\''
                + row.id + '\')">重启</a>'+
                '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="cloudmanager.mandateVMManagement.vncOldVM(\''
                + row.vmId + '\')">VNC</a>';
    },
    formatName : function(val, row, index) {
        if(row.name.endsWith("（孤立的）")) {
            return row.name;
        } else {
            return '<a href="#" onclick="cloudmanager.mandateVMManagement.mandateVMDetail(\'' + row.id + '\')">' + row.name + '</a>';
        }

    },
    formatConfig : function(val, row, index) {
        return "CPU："+row.cpuNumber+"核  内存："+(row.memoryTotal/1024).toFixed(0)+"GB 磁盘："+(row.diskTotal/1024).toFixed(0)+"GB";
    },
    mandateVMDetail : function(id) {
        //$('.sugon-searchbox').searchbox('destory');
        $('.sugon-searchbox').searchbox('setValue', null);
        //return '<a href="#" onclick="cloudmanager.mandateVMManagement.sideboardShow(\'' + id + '\')"></a>';
        cloudmanager.mandateVMManagement.sideboardShow(id);
    },
    //表格上方按钮事件
    synchronizeManagedVM : function() {
    	layer.confirm('确认同步利旧虚拟机？', {
			title:'同步',
			icon: 3,
			btn: ['确认','取消'] //按钮
		}, function(index){
			sugon.load({
                type : 'get',
                data:{},
                action : sugon.rootURL + '/oldvm/synchronize',
                callback : function(result) {
                    console.info(result);
                    var resultJson = JSON.parse(result);
                    if(resultJson.flag){
                        layer.close(index);
                        toastr.success('同步成功！');
                        $("#mandateVMTableId").datagrid('reload');
                    }else{
                        layer.close(index);
                        toastr.error(resultJson.message.desc + "," + resultJson.message.solution);
                    }
                }
            });
		});
    },
    assignManagedVM : function() {
        var checkedItems = $("#mandateVMTableId").datagrid("getChecked");
        var ids = [];
        var names = [];
        var orgs = [];
        $.each(checkedItems, function(index, item) {
            ids.push(item.id);
            names.push(item.name);
            orgs.push(item.orgName);
        });
        console.info(ids);
        console.info(names);
        if (ids.length <= 0) {
            toastr.warning('请至少选择一个 ！');
        }
        var flag = true;
		for (var i = 0; i < orgs.length; i++) {
			if (orgs[i] != "") {
				console.info(orgs[i]);
				flag = false;
				toastr.warning('已分配的利旧虚拟机不能再次分配 ！');
				break;
			}
		}
        if(flag && ids.length > 0) {
        	$.get(sugon.rootURL + '/oldvm/assignVM/' + names, {}, function(str){
    			layer.open({
    				  type: 1,
    				  title:'分配利旧虚拟机',
    				  //skin: 'layui-layer-rim', //加上边框
    				  area: ['550', ''], //宽高
    				  content: str,
    				  btn:['确定','取消'],
    				  yes:function(index){
    					  cloudmanager.mandateVMManagement.confirmAssignVM(ids);
    				  }
    			});
    		});
        }
    },
    recycleManagedVM : function(ids) {
        
        var checkedItems = $("#mandateVMTableId").datagrid("getChecked");
        var ids = [];
        var orgs = [];
        $.each(checkedItems, function(index, item) {
            ids.push(item.id);
            orgs.push(item.orgName);
        });
        console.info(ids);
        if (ids.length <= 0) {
            toastr.warning('请至少选择一个！');
        }
        var flag = true;
		for (var i = 0; i < orgs.length; i++) {
			if (orgs[i] == "") {
				console.info(orgs[i]);
				flag = false;
				toastr.warning('未分配的利旧虚拟机不可回收 ！');
				break;
			}
		}
        if(flag && ids.length > 0) {
            layer.confirm("是否回收利旧虚拟机？", {
                title:'回收利旧虚拟机',
                icon: 3,
                btn: ['确认','取消'] //按钮
            }, function(index) {
                sugon.load({
                    type : 'post',
                    data:{
                        ids : ids
                    },
                    action : sugon.rootURL + '/oldvm/recycle',
                    callback : function(result) {
                        console.info(result);
                        var resultJson = JSON.parse(result);
                        if(resultJson.flag){
                            layer.close(index);
                            toastr.success(resultJson.result);
                            $("#mandateVMTableId").datagrid('reload');
                        }else{
                            layer.close(index);
                            toastr.error(resultJson.message.desc + "," + resultJson.message.solution);
                        }
                    }
                }); 
            }); 
        }
    },
    startManagedVM : function(ids) {
        
        var checkedItems = $("#mandateVMTableId").datagrid("getChecked");
        var ids = [];
        $.each(checkedItems, function(index, item) {
            ids.push(item.id);
        });
        console.info(ids);
        if (ids.length <= 0) {
            toastr.warning('请至少选择一个!');
        }
        if(ids.length > 0) {
            layer.confirm("是否开启利旧虚拟机？", {
                title:'开启利旧虚拟机',
                icon: 3,
                btn: ['确认','取消'] //按钮
            }, function(index) {
                sugon.load({
                    type : 'post',
                    data:{
                        ids : ids
                    },
                    action : sugon.rootURL + '/oldvm/start',
                    callback : function(result) {
                        console.info(result);
                        var resultJson = JSON.parse(result);
                        if(resultJson.flag){
                            layer.close(index);
                            toastr.success(resultJson.result);
                            $("#mandateVMTableId").datagrid('reload');
                        }else{
                            layer.close(index);
                            toastr.error(resultJson.message.desc + "," + resultJson.message.solution);
                        }
                        
                        //设置定时任务刷新虚拟机启动的状态成功变化
                       
                        setTimeout('refushVMStatus()',10000); //指定1秒刷新一次 
                    }
                }); 
            });
        }
    },
    refushVMStatus : function(id){//刷新虚拟机状态任务
    	 sugon.load({
             type : 'post',
             data:{
                 ids : ids
             },
             action : sugon.rootURL + '/oldvm/refushVMStatus',
             callback : function(result) {
            	 
             }})
    },
    stopManagedVM : function(ids) {
        
        var checkedItems = $("#mandateVMTableId").datagrid("getChecked");
        var ids = [];
        $.each(checkedItems, function(index, item) {
            ids.push(item.id);
        });
        console.info(ids);
        if (ids.length <= 0) {
            toastr.warning('请至少选择一个!');
        }
        if(ids.length > 0) {
            layer.confirm("是否停止利旧虚拟机？", {
                title:'停止利旧虚拟机',
                icon: 3,
                btn: ['确认','取消'] //按钮
            }, function(index) {
                sugon.load({
                    type : 'post',
                    data:{
                        ids : ids
                    },
                    action : sugon.rootURL + '/oldvm/stop',
                    callback : function(result) {
                        console.info(result);
                        var resultJson = JSON.parse(result);
                        if(resultJson.flag){
                            layer.close(index);
                            toastr.success(resultJson.result);
                            $("#mandateVMTableId").datagrid('reload');
                        }else{
                            layer.close(index);
                            toastr.error(resultJson.message.desc + "," + resultJson.message.solution);
                        }
                    }
                }); 
            });
        }
    },
    restartManagedVM : function(ids) {
        
        var checkedItems = $("#mandateVMTableId").datagrid("getChecked");
        var ids = [];
        $.each(checkedItems, function(index, item) {
            ids.push(item.id);
        });
        console.info(ids);
        if (ids.length <= 0) {
            toastr.warning('请至少选择一个!');
        }
        if(ids.length > 0) {
            layer.confirm("是否重启利旧虚拟机？", {
                title:'重启利旧虚拟机',
                icon: 3,
                btn: ['确认','取消'] //按钮
            }, function(index) {
                sugon.load({
                    type : 'post',
                    data:{
                        ids : ids
                    },
                    action : sugon.rootURL + '/oldvm/restart',
                    callback : function(result) {
                        console.info(result);
                        var resultJson = JSON.parse(result);
                        if(resultJson.flag){
                            layer.close(index);
                            toastr.success(resultJson.result);
                            $("#mandateVMTableId").datagrid('reload');
                        }else{
                            layer.close(index);
                            toastr.error(resultJson.message.desc + "," + resultJson.message.solution);
                        }
                    }
                });
            });
        }
    },
    //动作栏中的按钮函数
    assignOldVM : function(id, name, orgName) {
        var names = name;
        if(orgName != ""){
        	 toastr.warning('已分配的利旧虚拟机不能再次分配 ！');
        	 return;
        }
        $.get(sugon.rootURL + '/oldvm/assignVM/' + names, {}, function(str){
			layer.open({
				  type: 1,
				  title:'分配利旧虚拟机',
				  //skin: 'layui-layer-rim', //加上边框
				  area: ['600px', ''], //宽高
				  content: str,
				  btn:['确定','取消'],
				  yes:function(index){
					  var ids = id;
	                  cloudmanager.mandateVMManagement.confirmAssignVM(ids);
	                  layer.close(index);
				  }
			});
		});
    },vncOldVM : function(id){
    	window.open( sugon.rootURL + '/oldvm/vmVnc?vmId=' + id);
    },
    recycleOldVM : function(id, orgName){
    	 if(orgName == ""){
        	 toastr.warning('未分配的利旧虚拟机不可回收 ！');
        	 return;
        }
        layer.confirm("是否回收利旧虚拟机？", {
            title:'回收利旧虚拟机',
            icon: 3,
            btn: ['确认','取消'] //按钮
        }, function(index) {
            sugon.load({
                type : 'post',
                data:{
                    id : id
                },
                action : sugon.rootURL + '/oldvm/recycle/' + id,
                callback : function(result) {
                    console.info(result);
                    var resultJson = JSON.parse(result);
                    if(resultJson.flag){
                        layer.close(index);
                        toastr.success('回收成功！');
                        $("#mandateVMTableId").datagrid('reload');
                    }else{
                        layer.close(index);
                        toastr.error(resultJson.message.desc + "," + resultJson.message.solution);
                    }
                }
            });
        }); 
    },
    deleteOldVM : function(id){
        layer.confirm("是否删除利旧虚拟机？", {
            title:'删除利旧虚拟机',
            icon: 3,
            btn: ['确认','取消'] //按钮
        }, function(index) {
            sugon.load({
                type : 'post',
                data:{
                    id : id
                },
                action : sugon.rootURL + '/oldvm/delete/' + id,
                callback : function(result) {
                    console.info(result);
                    var resultJson = JSON.parse(result);
                    if(resultJson.flag){
                        layer.close(index);
                        toastr.success(resultJson.result);
                        $("#mandateVMTableId").datagrid('reload');
                    }else{
                        layer.close(index);
                        toastr.error(resultJson.message.desc + "," + resultJson.message.solution);
                    }
                }
            });
        }); 
    },
    startOldVM : function(id){
        layer.confirm("是否开启利旧虚拟机？", {
            title:'开启利旧虚拟机',
            icon: 3,
            btn: ['确认','取消'] //按钮
        }, function(index) {
            sugon.load({
                type : 'get',
                data:{
                    id : id
                },
                action : sugon.rootURL + '/oldvm/start/' + id,
                callback : function(result) {
                    console.info(result);
                    var resultJson = JSON.parse(result);
                    if(resultJson.flag){
                        layer.close(index);
                        toastr.success(resultJson.result);
                        $("#mandateVMTableId").datagrid('reload');
                    }else{
                        layer.close(index);
                        toastr.error(resultJson.message.desc + "," + resultJson.message.solution);
                    }
                }
            }); 
        }); 
    },
    stopOldVM : function(id){
        layer.confirm("是否停止利旧虚拟机？", {
            title:'停止利旧虚拟机',
            icon: 3,
            btn: ['确认','取消'] //按钮
        }, function(index) {
            sugon.load({
                type : 'get',
                data:{
                    id : id
                },
                action : sugon.rootURL + '/oldvm/stop/' + id,
                callback : function(result) {
                    console.info(result);
                    var resultJson = JSON.parse(result);
                    if(resultJson.flag){
                        layer.close(index);
                        toastr.success(resultJson.result);
                        $("#mandateVMTableId").datagrid('reload');
                    }else{
                        layer.close(index);
                        toastr.error(resultJson.message.desc + "," + resultJson.message.solution);
                    }
                }
            }); 
        });
    },
    restartOldVM : function(id){
        layer.confirm("是否重启利旧虚拟机？", {
            title:'重启利旧虚拟机',
            icon: 3,
            btn: ['确认','取消'] //按钮
        }, function(index) {
            sugon.load({
                type : 'get',
                data:{
                    id : id
                },
                action : sugon.rootURL + '/oldvm/restart/' + id,
                callback : function(result) {
                    console.info(result);
                    var resultJson = JSON.parse(result);
                    if(resultJson.flag){
                        layer.close(index);
                        toastr.success('重启虚拟机成功！');
                        $("#mandateVMTableId").datagrid('reload');
                    }else{
                        layer.close(index);
                        toastr.error(resultJson.message.desc + "," + resultJson.message.solution);
                    }
                }
            });
        }); 
    },
    confirmAssignVM : function(ids){
        var ids = ids; 
        var orgId = $("#orgSele").val();
        console.info(ids + "id");
        console.info("orgId:" + orgId);
        sugon.load({
            type:'post',
            data:{
                ids : ids,
                orgId : orgId
            },
            action:sugon.rootURL + "/oldvm/assign",
            callback : function(result) {
                console.info(result);
                var resultJson = JSON.parse(result);
                if(resultJson.flag){
                    toastr.success('分配利旧虚拟机成功');
                    layer.closeAll();
                    $("#mandateVMTableId").datagrid('reload'); 
                }
                else{
                    toastr.error(resultJson.message.desc + "," + resultJson.message.solution);
                }
            }
        });
    },
    //显示详情
    sideboardShow : function(id){
    	$('.vm-mask').fadeIn(200);
        $('.jq-sideboard').animate({
            "right":0
        },300);
        cloudmanager.mandateVMManagement.initDetail();
        cloudmanager.mandateVMManagement.updateDetial(id);
    },
    //关闭详情
    sideboardClose : function(){
    	$('.vm-mask').fadeOut(200);
        $('.jq-sideboard').animate({
            "right":-750
        },300);
        cloudmanager.mandateVMManagement.initDetail();
    },
    //初始化详情页面数据
    initDetail : function(){
        $('#vmTagName').html("");
        $('#name').html("");
        $('#orgName').html("");
        $('#assignData').html("");
        $('#memoryTotal').html("");
        $('#ipAddr').html("");
        
        $('#cpuUsage').html("");
        $('#cpuFreeGHz').html("");
        $('#cpuUsedGHz').html("");
        $('#cpuGHz').html("");
        $('#cpuUsagePie').width(0);
        
        $('#memUsage').html("");
        $('#memFree').html("");
        $('#memUsed').html("");
        $('#mem').html("");
        $('#memUsagePie').width(0);
        
        $('#diskUsage').html("");
        $('#diskFree').html("");
        $('#diskUsed').html("");
        $('#disk').html("");
        $('#diskUsagePie').width(0);
        
        $('#jq-history-cpu').html("");
        $('#jq-history-mem').html("");
        $('#jq-history-disk').html("");
        $('#jq-history-net').html("");
    },
  //更新详情页面数据
    updateDetial : function(id){
        _this=this;
        $.ajax( {  
            type : 'post',  
            contentType : 'application/json',  
            url : sugon.rootURL + '/oldvm/display', 
            dataType : 'json',  
            data : id,
            success : function(data) {
                console.log(data);
                $('#vmTagName').html("虚拟机-"+data.oldVM.name);
                $('#name').html(data.oldVM.name);
                $('#orgName').html(data.oldVM.orgName);
                $('#assignData').html(data.oldVM.assignData);
                $('#cpuNumber').html(data.oldVM.cpuNumber);
                $('#ipAddr').html(data.oldVM.ipAddr);
                
                $('#cpuUsage').html(data.oldVM.cpuUsage+"%");
                $('#cpuFreeGHz').html((data.oldVM.cpuMHZTotal-data.oldVM.cpuMHZUsed).toFixed(2)+"MHz");
                $('#cpuUsedGHz').html(data.oldVM.cpuMHZUsed+"MHz");
                $('#cpuGHz').html(data.oldVM.cpuMHZTotal+"MHz");
                $('#cpuUsagePie').width(data.oldVM.cpuUsage+"%");
                
                $('#memUsage').html(data.oldVM.memoryUsage+"%");
                $('#memFree').html((data.oldVM.memoryTotal-data.oldVM.memoryUsed).toFixed(2)+"MB");
                $('#memUsed').html(data.oldVM.memoryUsed+"MB");
                $('#mem').html(data.oldVM.memoryTotal+"MB");
                $('#memUsagePie').width(data.oldVM.memoryUsage+"%");
                
                $('#diskUsage').html(data.oldVM.diskUsage+"%");
                var diskTotalGB = data.oldVM.diskTotal / 1024;
                var diskUsedGB = data.oldVM.diskUsed / 1024;
                $('#diskFree').html((diskTotalGB-diskUsedGB).toFixed(2)+"GB");
                $('#diskUsed').html(diskUsedGB.toFixed(2)+"GB");
                $('#disk').html(diskTotalGB.toFixed(2)+"GB");
                $('#diskUsagePie').width(data.oldVM.diskUsage+"%");
                
                for(var n=0;n<4;n++){
                    var time=data.history.perf24[_this.historyproperties[n]].collectTime;
                    var value=data.history.perf24[_this.historyproperties[n]].values;
                    cloudmanager.monitorSummary.initLine(_this.historyelementId[n], _this.historyshow, time,value);
                }
            },  
            error : function() {
                console.info("获取数据失败！");
            }  
          }
        );
    },
}
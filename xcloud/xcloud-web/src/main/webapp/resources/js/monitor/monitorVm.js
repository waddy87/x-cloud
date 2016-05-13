cloudmanager.namespace("monitorVm");
var searchFirst = -1;
var dataAll= new Object();

cloudmanager.monitorVm = {
        //虚拟机列表页面数据
        listName:['topnCpuUsage','topnCpuUsed','topnMemUsage','topnMemUsed','topnDiskIO','topnNetIO','topnDiskUsage','topnDiskIOPS'],
        elementId:['jqvm-cpu-usage','jqvm-cpu-used','jqvm-mem-usage','jqvm-mem-used','jqvm-disk-io','jqvm-net-io','jqvm-disk-usage','jqvm-disk-iops'],
        properties:['cpuUsage','cpuMHZUsed','memoryUsage','memoryUsed','diskIOSpeed','networkTransmitSpeed','diskUsage','diskIops'],
        show:['CPU使用率(%)','CPU使用量(MHz)','内存使用率(%)','内存使用率(MB)','磁盘IO(KB/s)','网络IO(KB/s)','磁盘使用率(%)','磁盘IOPS'],
        
        //虚拟机详情页面数据
        detaillistName:['topnHostCpuUsage','topnHostMemUsage','topnHostDiskIO','topnHostNetIO','topnVMCpuUsage','topnVMMemUsage','topnVMDiskIO','topnVMNetIO'],
        detailelementId:['jq-host-cpu-usage','jq-host-mem-usage','jq-host-disk-speed','jq-host-net-speed','jq-vm-cpu-usage','jq-vm-mem-usage','jq-vm-disk-speed','jq-vm-net-speed'],
        detailproperties:['cpuUsage','memoryUsage','diskIOSpeed','networkTransmitSpeed','cpuUsage','memoryUsage','diskIOSpeed','networkTransmitSpeed'],
        detailshow:['CPU使用率(%)','内存使用率(%)','磁盘IO(KB/s)','网络IO(KB/s)','CPU使用率(%)','内存使用率(%)','磁盘IO(KB/s)','网络IO(KB/s)'],
         
        //虚拟机详情历史数据
        historyelementId:['jq-history-cpu','jq-history-mem','jq-history-disk','jq-history-net'],
        historyshow:['CPU使用率(%)','内存使用率(%)','磁盘IO(KB/s)','网络IO(KB/s)'],
        historyproperties:['cpuUsage','memUsage','diskTps','netTps'],

       loop:false, //启动及关闭按钮  
       
        //初始化列表页面topnN
        initTopN : function() {
            _this=this;
            var width=$('.jq-table-content').width()-140;
            for(var n=0;n<8;n++){
                $('#'+_this.elementId[n]).width(width);
                var name=new Array();
                var value = new Array();
                for(var i=0;i<10;i++){
                        name[i]="";
                        value[i]="";
                }
                cloudmanager.monitorSummary.initHorizonBar(_this.elementId[n],_this.show[n],name,value);
            }
        },
        
        //更新列表页面topN
        updateTopN : function () {
//            console.info("vm---");
            _this=this;
            $.ajax( {  
                type : 'post',  
                contentType : 'application/json',  
                url : sugon.rootURL + '/monitor/vm/vmTopN', 
                dataType : 'json',  
                success : function(data) {
                    for(var n=0;n<8;n++){
                        var topn=10;
                        var clusterSize=data[_this.listName[n]].length;
                        var name=new Array();
                        var value = new Array();
                        for(var i=0;i<topn;i++){
                            if (i<topn-clusterSize) {
                                name[i]="";
                                value[i]="";
                            }else{
                                var topnCpuUsage=data[_this.listName[n]];
                                name[i]=topnCpuUsage[topn-i-1].name;
                                value[i]=topnCpuUsage[topn-i-1][_this.properties[n]];
                            }
                        }
                        cloudmanager.monitorSummary.initHorizonBar(_this.elementId[n],_this.show[n],name,value);
                    }
                },  
                error : function() {
                    console.info("从"+url+"获取数据失败！");
                }  
              }
            );
        },
        
        
        //初始化列表
        initTable : function() {
            _this=this;
            $('#monitorIndexTableId').datagrid({
                url : sugon.rootURL + '/monitor/vm/vmList',
                method : 'post',
                striped : true,
                fitColumns : true,
                singleSelect : true,
                rownumbers : true,
                pagination : true,
                pageNumber : 1,
                nowrap : false,
                pageSize : 10,
                toolbar : '#monitorIndextb',
                pageList : [  5, 10, 20, 50, 100],
                loader :_this.myLoader,
                queryParams : {},
                loadMsg : "加载中.....",
                showFooter : true,
                columns : [ [ {
                    field : "name",
                    title : "名称",
                    align : "center",
                    width : '18%',
                    formatter : _this.toDetail
                }, {
                    field : "hostName",
                    title : "运行主机",
                    align : "center",
                    width : '8%'
                }, {
                    field : "cpuUsage",
                    title : "CPU使用率(%)",
                    align : "center",
                    width : '8%'
                }, {
                    field : "memoryUsage",
                    title : "内存使用率(%)",
                    align : "center",
                    width : '8%'
                }, {
                    field : "diskIOSpeed",
                    title : "磁盘IO(KB/s)",
                    align : "center",
                    width : '8%'
                }, {
                    field : "networkTransmitSpeed",
                    title : "网络IO(KB/s)",
                    align : "center",
                    width : '7%'
                }, {
                    field : "diskIops",
                    title : "磁盘IOPS",
                    align : "center",
                    width : '7%'
                } , {
                    field : "diskUsage",
                    title : "磁盘使用率(%)",
                    align : "center",
                    width : '7%'
                } , {
                    field : "status",
                    title : "状况",
                    align : "center",
                    width : '6%',
                    formatter : function(val, row, index){
                        var objStatus = "<span class=\"zt-gray\" >未知";
                        if (row.status == "ok"){
                            objStatus = "<span class=\"zt-green\">正常";
                        }else if (row.status == "warning"){
                            objStatus = "<span class=\"zt-yellow\">警告";
                        }else if (row.status == "critical"){
                        	objStatus = "<span class=\"zt-red\">严重";
                        }else{
                            objStatus = "<span class=\"zt-gray\">未知";
                        }
                        return objStatus;
                    }
                } , {
                    field : "powerStatus",
                    title : "状态",
                    align : "center",
                    width : '6%',
                    formatter : function(val, row, index){
                        var objPowerStatus = "运行中";
                        if (row.powerStatus == "on"){
                            objPowerStatus = "运行中";
                        }else if (row.powerStatus == "off"){
                            objPowerStatus = "已关闭";
                        }else if (row.powerStatus == "suspend"){
                            objPowerStatus = "已挂起";
                        }else{
                            objPowerStatus = "未知";
                        }
                        return objPowerStatus;
                    }
                } ] ],
                onBeforeLoad : function(param) {
                },
                onLoadSuccess : function(data) {
//                    	//第一次加载
                	if(searchFirst == -1){
                		dataAll=$("#monitorIndexTableId").data().datagrid.cache;
                	}
                	searchFirst++; 
                },
                onLoadError : function() {
                },
                onClickCell : function(rowIndex, field, value) {
                }
            });
            
            var p = $('#monitorIndexTableId').datagrid('getPager');
            $(p).pagination({
                layout: ['list','sep','first','prev','links','next','last','sep','refresh','manual'],
                displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
                beforePageText:'跳转到第 ',
                afterPageText:' 页'
            });
            $('#monitorIndexSearchInputId').searchbox({
                searcher:function(value,name){
                	var temp_total=dataAll.total;
                	var temp_rows=[];
                	temp_rows=dataAll.rows;
                	
                	//查询结果集
                	var query_total=0;
                	var query_rows=[];
                	for(var i=0;i<temp_total;i++){
                		console.info(temp_rows[i].name);
                		if(temp_rows[i].name.indexOf(value)>=0){
                			query_total++;
                			query_rows.push(temp_rows[i]);
                		}
                	}
                	//查询结果放入到cache中
                	var query_cache={};
                	query_cache.total=query_total;
                	query_cache.rows=query_rows;
                	
                	$("#monitorIndexTableId").data().datagrid.cache=query_cache;
                    $("#monitorIndexTableId").datagrid('reload');
                },
                menu:'#tableSearch',
                prompt:'请输入查询内容'
            });
//            $('#monitorIndexSearchInputId').searchbox('addClearBtn', 'icon-clear');
        },
        
        myLoader:function (param, success, error) {
            var that = $("#monitorIndexTableId");  
            var opts = that.datagrid("options");  
            if (!opts.url) {  
                return false;  
            }  
            var cache = that.data().datagrid.cache;
            if (!cache) {  
                $.ajax({  
                    type: opts.method,  
                    url: opts.url,  
                    data: param,  
                    dataType: "json",  
                    success: function (data) {  
                        that.data().datagrid['cache'] = data;  
                        success(bulidData(data));  
                    },  
                    error: function () {  
                        error.apply(this, arguments);  
                    }  
                });  
            } else {  
                success(bulidData(cache));  
            } 
          
            function bulidData(data) {  
                var temp = $.extend({}, data);  
                var tempRows = [];  
                var start = (param.page - 1) * parseInt(param.rows);  
                var end = start + parseInt(param.rows);  
                var rows = data.rows;  
                for (var i = start; i < end; i++) {  
                    if (rows[i]) {
                        tempRows.push(rows[i]);  
                    } else {  
                        break;  
                    }  
                }  
                temp.rows = tempRows;  
                return temp;  
            }  
        },  
        
        updateTable : function(){
            _this=this;
            $("#monitorIndexTableId").data().datagrid.cache = null;
            $("#monitorIndexTableId").datagrid('reload');
        },
        
        doSearch : function(value, name) {
            var queryParams = $('#monitorIndexTableId').datagrid('options').queryParams;
            queryParams.name = value;
            $("#monitorIndexTableId").datagrid('reload');
        },
        toDetail : function(val, row, index) {
            return '<a href="#" onclick="cloudmanager.monitorVm.sideboardShow(\''
            + row.id + '\')">' + row.name + '</a>';
        },
        
        
        
        //显示详情
        sideboardShow : function(id){
            _this=this;
            $('.vm-mask').fadeIn(200);
            $('.jq-sideboard').animate({
                "right":0
            },300);
            _this.initDetail();
//            _this.updateDetail(id);
            
            _this.loop=true;
            _this.addDetailTimer(id);
        },
        //关闭详情
        sideboardClose : function(){
        	$('.vm-mask').fadeOut(200);
            $('.jq-sideboard').animate({
                "right":-750
            },300);
            _this.initDetail();
            _this.loop=false;
        },
      
        //更新详情页面数据
        updateDetail : function(id){
            console.info(id);
            _this=this;
            $.ajax( {  
                type : 'post',  
                contentType : 'application/json',  
                url : sugon.rootURL + '/monitor/vm/detail', 
                dataType : 'json',  
                data : id,
                success : function(data) {
                    $('#vmTagName').html("虚拟机-"+data.vm.name);
                    $('#vmName').html(data.vm.name);
                    $('#dataCenterName').html(data.vm.dateCenterName);
                    $('#clusterName').html(data.vm.clusterName);
                    $('#ipAddr').html(data.vm.ipAddr);
                    
                    $('#cpuUsage').html(data.vm.cpuUsage+"%");
                    $('#cpuFreeGHz').html((data.vm.cpuMHZTotal-data.vm.cpuMHZUsed).toFixed(2)+"MHz");
                    $('#cpuUsedGHz').html(data.vm.cpuMHZUsed+"MHz");
                    $('#cpuGHz').html(data.vm.cpuMHZTotal+"MHz");
                    $('#cpuUsagePie').width(data.vm.cpuUsage+"%");
                    
                    $('#memUsage').html(data.vm.memoryUsage+"%");
                    $('#memFreeGB').html((data.vm.memoryTotal-data.vm.memoryUsed).toFixed(2)+"MB");
                    $('#memUsedGB').html(data.vm.memoryUsed+"MB");
                    $('#memGB').html(data.vm.memoryTotal+"MB");
                    $('#memUsagePie').width(data.vm.memoryUsage+"%");
                    
                    $('#diskUsage').html(data.vm.diskUsage+"%");
                    var diskUsedGB=(data.vm.diskUsed/1024).toFixed(2);
                    var diskGB=(data.vm.diskTotal/1024).toFixed(2);
                    $('#diskFreeTB').html((diskGB-diskUsedGB).toFixed(2)+"GB");
                    $('#diskUsedTB').html(diskUsedGB+"GB");
                    $('#diskTB').html(diskGB+"GB");
                    $('#diskUsagePie').width(data.vm.diskUsage+"%");
                    
                    cloudmanager.monitorSummary.initAlarmTable("monitorAlarm",data.vm.triggeredAlarm);

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
        
        //初始化详情页面数据
        initDetail : function(){
            $('#vmTagName').html("");
            $('#vmName').html("");
            $('#dataCenterName').html("");
            $('#clusterName').html("");
            $('#ipAddr').html("");
            
            $('#cpuUsage').html("");
            $('#cpuFreeGHz').html("");
            $('#cpuUsedGHz').html("");
            $('#cpuGHz').html("");
            $('#cpuUsagePie').width("0%");
            
            $('#memUsage').html("");
            $('#memFreeGB').html("");
            $('#memUsedGB').html("");
            $('#memGB').html("");
            $('#memUsagePie').width("0%");
            
            $('#diskUsage').html("");
            $('#diskFreeTB').html("");
            $('#diskUsedTB').html("");
            $('#diskTB').html("");
            $('#diskUsagePie').width("0%");
            

//            for(var n=0;n<4;n++){
//                var time="";
//                var value="";
//                cloudmanager.monitorSummary.initLine(_this.historyelementId[n], _this.historyshow, time,value);
//            }
        },
        
        addDetailTimer:function(id){
            if(!_this.loop) return;  
            cloudmanager.monitorVm.updateDetail(id);
            window.setTimeout(function(){cloudmanager.monitorVm.addDetailTimer(id)},20000); 
        },
        update:function(){
            _this.updateTopN();
            _this.updateTable();
        }
}
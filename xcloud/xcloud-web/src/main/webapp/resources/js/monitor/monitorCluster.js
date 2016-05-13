cloudmanager.namespace("monitorCluster");
var searchFirst = -1;
var dataAll= new Object();

cloudmanager.monitorCluster = {
        //集群列表页面数据
        listName:['topnCpuUsage','topnCpuUsed','topnMemUsage','topnMemUsed','topnDiskIO','topnNetIO','topnHostNum','topnVMNum'],
        elementId:['jq-cpu-usage','jq-cpu-used','jq-mem-usage','jq-mem-used','jq-disk-io','jq-net-io','jq-host-num','jq-vm-num'],
        properties:['cpuUsage','cpuMHZUsed','memoryUsage','memoryUsed','diskIOSpeed','networkTransmitSpeed','hostNumber','vmNum'],
        show:['CPU使用率(%)','CPU使用量(MHz)','内存使用率(%)','内存使用率(MB)','磁盘IO(KB/s)','网络IO(KB/s)','物理机数量(台)','虚拟机数量(台)'],
        
        //集群详情页面数据
        detaillistName:['topnHostCpuUsage','topnHostMemUsage','topnHostDiskIO','topnHostNetIO','topnVMCpuUsage','topnVMMemUsage','topnVMDiskIO','topnVMNetIO'],
        detailelementId:['jq-host-cpu-usage','jq-host-mem-usage','jq-host-disk-speed','jq-host-net-speed','jq-vm-cpu-usage','jq-vm-mem-usage','jq-vm-disk-speed','jq-vm-net-speed'],
        detailproperties:['cpuUsage','memoryUsage','diskIOSpeed','networkTransmitSpeed','cpuUsage','memoryUsage','diskIOSpeed','networkTransmitSpeed'],
        detailshow:['CPU使用率(%)','内存使用率(%)','磁盘IO(KB/s)','网络IO(KB/s)','CPU使用率(%)','内存使用率(%)','磁盘IO(KB/s)','网络IO(KB/s)'],
         
        //集群详情历史数据
        historyelementId:['jq-history-cpu','jq-history-mem','jq-history-disk','jq-history-net'],
        historyshow:['CPU使用率(%)','内存使用率(%)','磁盘IO(KB/s)','网络IO(KB/s)'],
        historyproperties:['cpuUsage','memUsage','diskTps','netTps'],
        
        loop:false,
       
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
//            console.info("cluster---");
            _this=this;
            $.ajax( {  
                type : 'post',  
                contentType : 'application/json',  
                url : sugon.rootURL + '/monitor/cluster/clusterTopN', 
                dataType : 'json',  
                success : function(data) {
                    console.log(data);
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
                url : sugon.rootURL + '/monitor/cluster/clusterList',
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
                pageList : [  5, 10, 20, 50, 100 ],
                loader :_this.myLoader,
                queryParams : {},
                loadMsg : "加载中.....",
                showFooter : true,
                columns : [ [ {
                    field : "name",
                    title : "名称",
                    align : "center",
                    width : '10%',
                    formatter : _this.toDetail
                }, {
                    field : "dataCenterName",
                    title : "数据中心",
                    align : "center",
                    width : '10%'
                }, {
                    field : "cpuUsage",
                    title : "CPU使用率(%)",
                    align : "center",
                    width : '10%'
                }, {
                    field : "memoryUsage",
                    title : "内存使用率(%)",
                    align : "center",
                    width : '10%'
                }, {
                    field : "diskIOSpeed",
                    title : "磁盘IO(KB/s)",
                    align : "center",
                    width : '10%'
                }, {
                    field : "networkTransmitSpeed",
                    title : "网络IO(KB/s)",
                    align : "center",
                    width : '10%'
                }, {
                    field : "diskIops",
                    title : "磁盘IOPS",
                    align : "center",
                    width : '7%'
                } , {
                    field : "hostNumber",
                    title : "物理机数(台)",
                    align : "center",
                    width : '8%'
                } , {
                    field : "vmNum",
                    title : "虚拟机数(台)",
                    align : "center",
                    width : '8%'
                } /*, {
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
                }*/ 
                ] ],
                onBeforeLoad : function(param) {
                },
                onLoadSuccess : function(data) {
                	//第一次加载
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
            $('#monitorIndexSearchInputId').searchbox('addClearBtn', 'icon-clear');
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
            return '<a href="#" onclick="cloudmanager.monitorCluster.sideboardShow(\''
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
            _this.loop=false;
            _this.initDetail();
        },
      
        //更新详情页面数据
        updateDetail : function(id){
            _this=this;
            $.ajax( {  
                type : 'post',  
                contentType : 'application/json',  
                url : sugon.rootURL + '/monitor/cluster/detail', 
                dataType : 'json',  
                data : id,
                success : function(data) {
                    console.log(data);
                    $('#clusterTagName').html("集群-"+data.cluster.name);
                    $('#clusterName').html(data.cluster.name);
                    $('#dataCenterName').html(data.cluster.dataCenterName);
                    
                    var normal=data.cluster.hostAccessibleNum;
                    var unNormal=data.cluster.hostUnaccessibleNum;
                    cloudmanager.monitorSummary.initCircle("jq-board-main", "主机", normal, unNormal);
                    normal=data.cluster.vmAccessibleNum;
                    unNormal=data.cluster.vmUnaccessibleNum;
                    cloudmanager.monitorSummary.initCircle("jq-board-virtual", "虚拟机", normal, unNormal);
                    normal=data.cluster.storeAccessibleNum;
                    unNormal=data.cluster.storeUnaccessibleNum;
                    cloudmanager.monitorSummary.initCircle("jq-board-storage", "存储", normal, unNormal);
                    
                    $('#cpuUsage').html(data.cluster.cpuUsage+"%");
                    $('#cpuFreeGHz').html((data.cluster.cpuGHz-data.cluster.cpuUsedGHz).toFixed(2)+"GHz");
                    $('#cpuUsedGHz').html(data.cluster.cpuUsedGHz+"GHz");
                    $('#cpuGHz').html(data.cluster.cpuGHz+"GHz");
                    $('#cpuUsagePie').width(data.cluster.cpuUsage+"%");
                    
                    $('#memUsage').html(data.cluster.memoryUsage+"%");
                    $('#memFreeGB').html((data.cluster.memGB-data.cluster.memUsedGB).toFixed(2)+"GB");
                    $('#memUsedGB').html(data.cluster.memUsedGB+"GB");
                    $('#memGB').html(data.cluster.memGB+"GB");
                    $('#memUsagePie').width(data.cluster.memoryUsage+"%");
                    
                    $('#diskUsage').html(data.cluster.diskUsage+"%");
                    $('#diskFreeTB').html((data.cluster.storeTB-data.cluster.storeUsedTB).toFixed(2)+"TB");
                    $('#diskUsedTB').html(data.cluster.storeUsedTB+"TB");
                    $('#diskTB').html(data.cluster.storeTB+"TB");
                    $('#diskUsagePie').width(data.cluster.diskUsage+"%");
                    
                    for(var n=0;n<8;n++){
                        var topn=3;
                        var clusterSize=data[_this.detaillistName[n]].length;
                        var name=new Array();
                        var value = new Array();
                        for(var i=0;i<topn;i++){
                            if (i<topn-clusterSize) {
                                name[i]="";
                                value[i]="";
                            }else{
                                var topnCpuUsage=data[_this.detaillistName[n]];
                                name[i]=topnCpuUsage[topn-i-1].name;
                                value[i]=topnCpuUsage[topn-i-1][_this.detailproperties[n]];
                            }
                        }
                        cloudmanager.monitorSummary.initHorizonBar(_this.detailelementId[n],_this.detailshow[n],name,value);
                    }
                    
                    for(var n=0;n<4;n++){
                        var time=data.history.perf24[_this.historyproperties[n]].collectTime;
                        var value=data.history.perf24[_this.historyproperties[n]].values;
                        cloudmanager.monitorSummary.initLine(_this.historyelementId[n], _this.historyshow, time,value);
                    }
                    cloudmanager.monitorSummary.initAlarmTable("monitorAlarm",data.cluster.triggeredAlarm);
                },  
                error : function() {
                    console.info("获取数据失败！");
                }  
              }
            );
        },
        
        //初始化详情页面数据
        initDetail : function(){
            $('#clusterTagName').html("");
            $('#clusterName').html("");
            $('#dataCenterName').html("");
            var normal=0;
            var unNormal=0;
            cloudmanager.monitorSummary.initCircle("jq-board-main", "主机", normal, unNormal);
            cloudmanager.monitorSummary.initCircle("jq-board-virtual", "虚拟机", normal, unNormal);
            cloudmanager.monitorSummary.initCircle("jq-board-storage", "存储", normal, unNormal);
            var width=$('.jqc4').width()-180;
            var height=$('.jqc4').height()-40;
            for(var n=0;n<8;n++){
                $('#'+_this.detailelementId[n]).width(width);
                $('#'+_this.detailelementId[n]).height(height);
            }
            
            
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
            
//            for(var n=0;n<8;n++){
//                var name="";
//                var value ="";
//                cloudmanager.monitorSummary.initHorizonBar(_this.detailelementId[n],_this.detailshow[n],name,value);
//            }
//            
//            for(var n=0;n<4;n++){
//                var time="";
//                var value="";
//                cloudmanager.monitorSummary.initLine(_this.historyelementId[n], _this.historyshow, time,value);
//            }
        },
        addDetailTimer:function(id){
            if(!_this.loop) return;  
            cloudmanager.monitorCluster.updateDetail(id);
            window.setTimeout(function(){cloudmanager.monitorCluster.addDetailTimer(id)},20000); 
        },
        update:function(){
            _this.updateTopN();
            _this.updateTable();
        }
}
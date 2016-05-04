cloudmanager.namespace("monitorStorage");
var searchFirst = -1;
var dataAll= new Object();

cloudmanager.monitorStorage = {
    // 存储列表页面数据
    listName : [ 'topnDiskTotal', 'topnDiskUsage', 'topnHostNum', 'topnVMNum' ],
    elementId : [ 'storage-disk-total', 'storage-disk-usage', 'storage-host-num', 'storage-vm-num' ],
    properties : [ 'diskTotal', 'diskUsage', 'hostNum', 'vmNum' ],
    show : [ '存储总容量(MB)', '存储利用率(%)', '物理机数量(台)', '虚拟机数量(台)' ],

    // 存储详情页面数据
    detaillistName : [ 'topnHostDiskUsage', 'topnHostIops', 'topnVmDiskUsage', 'topnVmIops' ],
    detailelementId : [ 'host-disk-usage', 'host-disk-iops', 'vm-disk-usage', 'vm-disk-iops' ],
    detailproperties : [ 'diskUsage', 'diskIops', 'diskUsage', 'diskIops' ],
    detailshow : [ '存储利用率(%)', '存储IOPS', '存储利用率(%)', '存储IOPS' ],

    // //存储详情历史数据
    // historyelementId:['storage-disk-history','storage-iops-history'],
    // historyshow:['存储利用率(%)','磁盘IOPS'],
    // historyproperties:['diskUsage','diskIops'],
    // 存储详情历史数据
    historyelementId : [ 'storage-disk-history' ],
    historyshow : [ '存储利用率(%)' ],
    historyproperties : [ 'storeUsage' ],

    // 初始化列表页面topnN
    initTopN : function() {
        _this = this;
        var width = $('.jq-table-content').width() - 140;
        for (var n = 0; n < 8; n++) {
            $('#' + _this.elementId[n]).width(width);
            var name = new Array();
            var value = new Array();
            for (var i = 0; i < 10; i++) {
                name[i] = "";
                value[i] = "";
            }
        }
    },

    // 更新列表页面topN
    updateTopN : function() {
        _this = this;
        $.ajax({
            type : 'post',
            contentType : 'application/json',
            url : sugon.rootURL + '/monitor/storage/storageTopN',
            dataType : 'json',
            success : function(data) {
                console.log(data);
                for (var n = 0; n < 4; n++) {
                    var topn = 10;
                    var storageSize = data[_this.listName[n]].length;
                    var name = new Array();
                    var value = new Array();
                    for (var i = 0; i < topn; i++) {
                        if (i < topn - storageSize) {
                            name[i] = "";
                            value[i] = "";
                        } else {
                            var topnCpuUsage = data[_this.listName[n]];
                            name[i] = topnCpuUsage[topn - i - 1].name;
                            value[i] = topnCpuUsage[topn - i - 1][_this.properties[n]];
                        }
                    }
                    cloudmanager.monitorSummary.initHorizonBar(_this.elementId[n], _this.show[n], name, value);
                }
            },
            error : function() {
                console.info("从" + url + "获取数据失败！");
            }
        });
    },

    // 初始化列表
    initTable : function() {
        _this = this;
        $('#monitorIndexTableId').datagrid({
            url : sugon.rootURL + '/monitor/storage/storageList',
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
                width : '20.5%',
                formatter : _this.toDetail
            }, {
                field : "storeTBTotal",
                title : "总容量(TB)",
                align : "center",
                width : '14%'
            }, {
                field : "diskUsage",
                title : "利用率(%)",
                align : "center",
                width : '14%'
            }, {
                field : "hostNum",
                title : "物理机数量(台)",
                align : "center",
                width : '10%'
            }, {
                field : "vmNum",
                title : "虚拟机数量(台)",
                align : "center",
                width : '10%'
            }, {
                field : "status",
                title : "状况",
                align : "center",
                width : '10%',
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
            }, {
                field : "powerStatus",
                title : "状态",
                align : "center",
                width : '10%',
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
            layout : [ 'list', 'sep', 'first', 'prev', 'links', 'next', 'last', 'sep', 'refresh', 'manual' ],
            displayMsg : '共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
            beforePageText : '跳转到第 ',
            afterPageText : ' 页'
        });
        $('#monitorIndexSearchInputId').searchbox({
            searcher : function(value, name) {
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
            menu : '#tableSearch',
            prompt : '请输入查询内容'
        });
        $('#monitorIndexSearchInputId').searchbox('addClearBtn', 'icon-clear');
    },
    myLoader : function(param, success, error) {
        var that = $("#monitorIndexTableId");
        var opts = that.datagrid("options");
        if (!opts.url) {
            return false;
        }
        var cache = that.data().datagrid.cache;
        if (!cache) {
            $.ajax({
                type : opts.method,
                url : opts.url,
                data : param,
                dataType : "json",
                success : function(data) {
                    that.data().datagrid['cache'] = data;
                    success(bulidData(data));
                },
                error : function() {
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
                    console.info("----" + rows[i]);
                    tempRows.push(rows[i]);
                } else {
                    break;
                }
            }
            temp.rows = tempRows;
            return temp;
        }
    },
    updateTable : function() {
        _this = this;
        $("#monitorIndexTableId").data().datagrid.cache = null;
        $("#monitorIndexTableId").datagrid('reload');
    },
    doSearch : function(value, name) {
        var queryParams = $('#monitorIndexTableId').datagrid('options').queryParams;
        queryParams.name = value;
        $("#monitorIndexTableId").datagrid('reload');
    },
    toDetail : function(val, row, index) {
        return '<a href="#" onclick="cloudmanager.monitorStorage.sideboardShow(\'' + row.id + '\')">' + row.name
                + '</a>';
    },
    // 显示详情
    sideboardShow : function(id) {
        _this = this;
        $('.vm-mask').fadeIn(200);
        $('.jq-sideboard').animate({
            "right" : 0
        }, 300);
        _this.initDetail();
        _this.updateDetial(id);
    },
    // 关闭详情
    sideboardClose : function() {
    	$('.vm-mask').fadeOut(200);
        $('.jq-sideboard').animate({
            "right" : -750
        }, 300);
        _this.initDetail();
    },

    // 更新详情页面数据
    updateDetial : function(id) {
        _this = this;
        $.ajax({
            type : 'post',
            contentType : 'application/json',
            url : sugon.rootURL + '/monitor/storage/detail',
            dataType : 'json',
            data : id,
            success : function(data) {
                console.log(data);
                $('#storageTagName').html("存储-" + data.storage.name);
                $('#storageName').html(data.storage.name);
                $('#storageType').html(data.storage.storageType);

                var normal = data.storage.hostAccessibleNum;
                var unNormal = data.storage.hostUnaccessibleNum;
                cloudmanager.monitorSummary.initCircle("storage-board-host", "物理机", normal, unNormal);
                normal = data.storage.vmAccessibleNum;
                unNormal = data.storage.vmUnaccessibleNum;
                cloudmanager.monitorSummary.initCircle("storage-board-vm", "虚拟机", normal, unNormal);

//                $('#diskUsage').html(data.storage.diskUsage + "%");
//                $('#diskFreeTB').html((data.storage.diskTotal - data.storage.diskUsed) + "TB");
//                $('#diskUsedTB').html(data.storage.diskUsed + "TB");
//                $('#diskTB').html(data.storage.diskTotal + "TB");
//                $('#diskUsagePie').width(data.storage.diskUsage + "%");

                $('#diskUsage').html(data.storage.diskUsage + "%");
                $('#diskFreeTB').html((data.storage.storeTBTotal - data.storage.storeTBUsed).toFixed(2) + "TB");
                $('#diskUsedTB').html(data.storage.storeTBUsed + "TB");
                $('#diskTB').html(data.storage.storeTBTotal + "TB");
                $('#diskUsagePie').width(data.storage.diskUsage + "%");
                
                for (var n = 0; n < 4; n++) {
                    var topn = 3;
                    var storageSize = data[_this.detaillistName[n]].length;
                    var name = new Array();
                    var value = new Array();
                    for (var i = 0; i < topn; i++) {
                        if (i < topn - storageSize) {
                            name[i] = "";
                            value[i] = "";
                        } else {
                            var topnCpuUsage = data[_this.detaillistName[n]];
                            name[i] = topnCpuUsage[topn - i - 1].name;
                            value[i] = topnCpuUsage[topn - i - 1][_this.detailproperties[n]];
                        }
                    }
                    cloudmanager.monitorSummary.initHorizonBar(_this.detailelementId[n], _this.detailshow[n], name,
                            value);
                }

                for (var n = 0; n < 1; n++) {
                    var time = data.history.perf24[_this.historyproperties[n]].collectTime;
                    var value = data.history.perf24[_this.historyproperties[n]].values;
                    cloudmanager.monitorSummary.initLine(_this.historyelementId[n], _this.historyshow, time, value);
                }
                cloudmanager.monitorSummary.initAlarmTable("monitorAlarm", data.storage.triggeredAlarm);
            },
            error : function() {
                console.info("获取数据失败！");
            }
        });
    },

    // 初始化详情页面数据
    initDetail : function() {
        $('#storageTagName').html("");
        $('#storageName').html("");
        $('#storageType').html("");
        var normal = 0;
        var unNormal = 0;
        cloudmanager.monitorSummary.initCircle("storage-board-host", "物理机", normal, unNormal);
        cloudmanager.monitorSummary.initCircle("storage-board-vm", "虚拟机", normal, unNormal);
        var width = $('.jqc4').width() - 180;
        var height = $('.jqc4').height() - 40;
        for (var n = 0; n < 8; n++) {
            $('#' + _this.detailelementId[n]).width(width);
            $('#' + _this.detailelementId[n]).height(height);
        }
    },
//    addDetailTimer : function(id) {
//        if (!_this.loop)
//            return;
//        cloudmanager.monitorStorage.updateDetail(id);
//        window.setTimeout(function() {
//            cloudmanager.monitorStorage.addDetailTimer(id)
//        }, 20000);
//    }
}
cloudmanager.namespace("orgMonitor");
cloudmanager.orgMonitor = {
        //虚拟机列表页面数据
        listName:['topnCpuUsage','topnMemUsage','topnDiskIO','topnNetIO','topnDiskUsage'],
        elementId:['jqvm-cpu-usage','jqvm-mem-usage','jqvm-disk-io','jqvm-net-io','jqvm-disk-usage'],
        properties:['cpuUsage','memoryUsage','diskIOSpeed','networkTransmitSpeed','diskUsage'],
        show:['Cpu使用率(%)','内存使用率(%)','磁盘IO(KB/s)','网络IO(KB/s)','磁盘使用率(%)'],

       loop:false, //启动及关闭按钮  
       
        //初始化列表页面topnN
        initTopN : function() {
            _this=this;
            for(var n=0;n<5;n++){
                var name=new Array();
                var value = new Array();
                for(var i=0;i<5;i++){
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
                url : sugon.rootURL + '/monitor/orgMonitor/vmTopN', 
                dataType : 'json',  
                success : function(data) {
                    for(var n=0;n<5;n++){
                        var topn=5;
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
        }
}
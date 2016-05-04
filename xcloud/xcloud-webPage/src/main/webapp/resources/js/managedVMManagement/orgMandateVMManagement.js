cloudmanager.namespace("orgMandateVMManagement");
cloudmanager.orgMandateVMManagement = {
        init : function(orgId) {
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
            queryParams : {
                orgId : orgId
            },
            loadMsg : "加载中.....",
            showFooter : true,
            checkOnSelect : false,// 禁止单击行选中复选框
            columns : [ [ {
                field : "id",
                checkbox : true,
                width : 50
            }, {
                field:"name",
                title:"虚拟机名称",
                align:"left",
                width:'11%',
                formatter:cloudmanager.mandateVMManagement.formatName,
                sortable:true,
                resizable:true
            }, {
                field:"os",
                title:"操作系统",
                align:"center",
                width:'11%',
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
                field : "operate",
                title : "操作",
                align : "center",
                width : '20%',
                formatter : cloudmanager.orgMandateVMManagement.formatOper
            } ] ],
            onBeforeLoad : function(param) {
            },
            onLoadSuccess : function(data) {
            },
            onLoadError : function() {
            },
            onClickCell : function(rowIndex, field, value) {
                $('#mandateVMTableId').datagrid('clearSelections');
            }
        });

        var p = $('#mandateVMTableId').datagrid('getPager');
        $(p).pagination({
            layout : [ 'list', 'sep', 'first', 'prev', 'links', 'next', 'last', 'sep', 'refresh', 'manual' ],
            displayMsg : '共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
            beforePageText : '跳转到第 ',
            afterPageText : ' 页'
        });
        $('#mandateVMSearchInputId').searchbox({
            searcher : function(value, name) {
                var queryParams = $('#mandateVMTableId').datagrid('options').queryParams;
                queryParams.name = value;
                $("#mandateVMTableId").datagrid('reload');
            },
            menu : '#tableSearch',
            prompt : '请输入查询内容'
        });
        $('#mandateVMSearchInputId').searchbox('addClearBtn', 'icon-clear');
    },
    formatOper : function(val, row, index) {
        return '<a href="#" onclick="cloudmanager.mandateVMManagement.startOldVM(\''
                + row.id + '\')">启动</a>'+
                '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="cloudmanager.mandateVMManagement.stopOldVM(\''
                + row.id + '\')">停止</a>'+
                '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="cloudmanager.mandateVMManagement.restartOldVM(\''
                + row.id + '\')">重启</a>'+
                '&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="cloudmanager.mandateVMManagement.vncOldVM(\''
                + row.vmId + '\')">VNC</a>';
    },
}
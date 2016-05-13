cloudmanager.namespace("messagePush");
cloudmanager.messagePush={
    init:function() {
        $('#unMandateVMTableId').datagrid({
            url : sugon.rootURL + '/messagePush/list',
            method : 'get',
            striped : true,
            fitColumns : true,
            singleSelect : false,
            rownumbers : false,
            pagination : true,
            pageNumber : 1,
            nowrap : false,
            pageSize : 10,
            toolbar : '#unMandateVMtb',
            pageList : [  5, 10, 20, 50, 100 ],
            queryParams : {},
            loadMsg : "加载中.....",
            showFooter : true,
            columns:[[
               {
                field:"mailSubject",
                title:"消息主题",
                align:"center",
                width:'20%'
                //formatter:cloudmanager.unMandateVMManagement.formatName
            }, {
                field:"mailContent",
                title:"消息内容",
                align:"center",
                width:'36%'
            }, {
                field:"eventState",
                title:"状态",
                align:"center",
                width:'20%'
            }, {
                field:"eventTime",
                title:"发送时间",
                align:"center",
                width:'20%'
            }
            ]],
            onBeforeLoad : function(param) {
            },
            onLoadSuccess : function(data) {
            },
            onLoadError : function() {
            },
            onClickCell : function(rowIndex, field, value) {
            }
        });
        
        var p = $('#unMandateVMTableId').datagrid('getPager');
        $(p).pagination({
            layout:['list','sep','first','prev','links','next','last','sep','refresh','manual'],
            displayMsg:'共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
            beforePageText:'跳转到第 ',
            afterPageText:' 页'
        });
        
        $('#mailObjectQuery').searchbox({
            searcher:function(value,name){
                var queryParams = $('#unMandateVMTableId').datagrid('options').queryParams;
                queryParams.name = value;
                $("#unMandateVMTableId").datagrid('reload');
            },
            menu:'#tableSearch',
            prompt:'请输入查询内容'
        });
        $('mailObjectQuery').searchbox('addClearBtn', 'icon-clear');
    },
    
    doSearch:function(value, name) {
        var queryParams = $('#unMandateVMTableId').datagrid('options').queryParams;
        queryParams.name = value;
        $("#unMandateVMTableId").datagrid('reload');
    },
}
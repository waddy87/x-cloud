cloudmanager.namespace("venv");
cloudmanager.venv = {
    init : function() {
    },
    doSearch : function(value, name) {
        var queryParams = $('#vnetTableId').datagrid('options').queryParams;
        queryParams.name = value;
        $("#vnetTableId").datagrid('reload');
    },
    formatOper : function(val, row, index) {
        return '<a href="#" onclick="cloudmanager.vnet.editVnet(\'' + row.netPoolId + '\')">配置</a>';
    },
    formatName : function(val, row, index) {
        return '<a href="#" onclick="cloudmanager.vnet.proVDCDetail(\'' + row.netPoolId + '\')">' + row.netName
                + '</a>';
    },
    /**
     * 同步虚拟化环境数据
     */
    sysData : function(param) {
        console.info(param);
        if (param == 2) {
            toastr.success('请先处理虚拟化环境的异常状态!');
        }
        if (param == 1) {
        	layer.confirm('同步虚拟化环境数据，会有数据丢失风险，确认同步虚拟化环境数据？', {
    			title:'同步',
    			icon: 3,
    			btn: ['确认','取消'] //按钮
    		}, function(index){
    			sugon.load({
                    dataType : "json",
                    action : sugon.rootURL + "/venv/sysDataFromCloudvmPage",
                    type : 'post',
                    callback : function(result) {
                        console.info(result);
                        if (result.flag=="0") {
                            layer.closeAll();
                            toastr.success(result.msg);
                        } 
                        if (result.flag=="1") {
                            layer.closeAll();
                            toastr.warning(result.msg);
                        }
                        if (result.flag=="2") {
                            layer.closeAll();
                            toastr.warning(result.msg);
                        }
                        if (result.flag=="-1") {
                            layer.closeAll();
                            toastr.error(result.msg);
                        }
                    }
                });
    		});
        }
    },
    /**
     * 删除虚拟化环境
     */
    delConfigInfo : function() {
    	layer.confirm('确认删除该虚拟化环境，及其内资源？', {
			title:'删除',
			icon: 3,
			btn: ['确认','取消'] //按钮
		}, function(index){
			var id = $("#id").val();
            console.info(id);
            $.ajax({
                method : "POST",
                url : sugon.rootURL + '/venv/delByconfigId',
                dataType : "json",
                data : {
                    id : id
                },
                success : function(result) {
                    layer.closeAll();
                    $("#venvconfig").trigger("click");
                    toastr.success('删除虚拟化环境成功!');
                }
            });
		});
    },
    /**
     * 点击详情，弹出虚拟化环境详情页面
     */
    editConfigPage : function() {
        var id = $("#id").val();
        console.info(id);
        $.get(sugon.rootURL+'/venv/editByvenvconfigIdPage?id=' + id, {}, function(str){
            layer.open({
                type: 1,
                title:'修改虚拟化环境',
                //skin: 'layui-layer-rim', //加上边框
                area: ['500px', ''], //宽高
                content: str
            });
        });
    },
    /**
     * 确认修改虚拟化环境
     */
    editConfig : function() {
        var id = $("#editid").val();
        var configname = $("#editconfigName").val();
        var ip = $("#editip").val();
        var username = $("#editusername").val();
        var pwd = $("#editpwd").val();
        var params = {
            configname : configname,
            ip : ip,
            username : username,
            pwd : pwd
        };
        console.info("id=" + id + ",configname=" + configname + ",ip=" + ip + ",username=" + username + ",pwd=" + pwd);
        $.ajax({
            url : sugon.rootURL + '/venv/editByvenvconfigId',
            method : "post",
            dataType : "json",
            data : {
                id : id,
                configname : configname,
                ip : ip,
                username : username,
                pwd : pwd
            },
            success : function(result) {
                toastr.success('修改成功');
               layer.closeAll();
                $("#venvconfig").trigger("click");
            }
        })

    },
    /**
     * 编辑：新建虚拟化环境时，测试是否可连接
     */
    testConfigInfo : function(configname, ip, username, pwd) {
    	 console.info("testConfigInfo");
        sugon.load({
            dataType : "json",
            action : sugon.rootURL + "/venv/testvenvConfigInfo",
            data : {
                id : '',
                configname : configname,
                ip : ip,
                username : username,
                pwd : pwd
            },
            type : 'get',
            callback : function(result) {
               
                console.info(result.flag);
                if (result.flag) {
                    $('#addConfigInfo').removeAttr("disabled");
                    $('#editConfigInfo').removeAttr("disabled");
                    toastr.success('测试通过');
                } else {
                    toastr.error('测试不通过!')
                }
            }
        })
        /*$.ajax({
            url : sugon.rootURL + '/venv/testvenvConfigInfo',
            method : "get",
            dataType : "json",
            data : {
                id : '',
                configname : configname,
                ip : ip,
                username : username,
                pwd : pwd
            },
            success : function(result) {
                console.info(result);
                if (result.flag) {
                    $('#addConfigInfo').removeAttr("disabled");
                    $('#editConfigInfo').removeAttr("disabled");
                    toastr.success('测试通过');
                } else {
                    toastr.error('测试不通过!');
                }
            }
        })*/
    },
    /**
     * 虚拟化环境，连接状态测试，测试结果保存到数据库中
     */
    testStatus : function() {
    	layer.confirm('确认测试连接？', {
			title:'连接',
			icon: 3,
			btn: ['确认','取消'] //按钮
		}, function(index){
			var configname = $("#configName").val();
            var ip = $("#ip").val();
            var username = $("#username").val();
            var pwd = $("#pwd").val();
            var id = $("#id").val();
            console.info("configname=" + configname + ",ip=" + ip + ",username=" + username + ",pwd=" + pwd);
          
            sugon.load({
                dataType : "json",
                action : sugon.rootURL + "/venv/testvenvConfigInfo",
                data : {
                    id : id,
                    configname : configname,
                    ip : ip,
                    username : username,
                    pwd : pwd
                },
                type : 'get',
                callback : function(result) {
                    console.info("-----------------------");
                    console.info(result.flag);
                    if (result.flag) {
                        console.info(result);
                        toastr.success('测试连接成功!');
                        layer.closeAll();
                        $("#venvconfig").trigger("click");
                    } else {
                        toastr.error('测试连接失败!');
                        layer.closeAll();
                        $("#venvconfig").trigger("click");
                    }
                }
            });
		});
    },
    /**
     * 新加、编辑时，进行输入框校验，若已经点击完成测试，且通过后，又修改输入框，则将确认按钮再次置灰
     */
    changedInput : function() {
        $("#editConfigInfo").attr("disabled", "true");
        $("#addConfigInfo").attr("disabled", "true");
    },
    /**
     * 弹出框，取消按钮
     */
    cancelConfigInfo : function() {
        layer.closeAll();
    },
    /**
     * 添加虚拟化环境数据格式校验
     */
    initAdd : function() {
        // IP地址验证
        jQuery.validator
                .addMethod(
                        "ip",
                        function(value, element) {
                            return this.optional(element)
                                    || /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/
                                            .test(value);
                        }, "请填写正确的IP地址。");
        var _this = this;
        $("#addConfigForm").validate({
            debug : false,
            focusInvalid : true, // 当为false时，验证无效时，没有焦点响应
            onkeyup : true,
            submitHandler : function(form) {
                console.info("验证成功！");
            },
            rules : {
                configName : {
                    required : true,
                    maxlength : 50
                },

                username : {
                    required : true,
                    maxlength : 50
                },
                pwd : {
                    required : true,
                    rangelength : [ 3, 10 ]
                },
                pwdConfirm : {
                    required : true,
                    rangelength : [ 3, 10 ],
                    equalTo : "#pwd"
                }
            },
            messages : {
                configName : {
                    required : "请输入名称",
                    maxlength : "最多50个字符"
                },
                username : {
                    required : "请输入用户名",
                    maxlength : "最多50个字符"
                },
                pwd : {
                    required : "请输入密码",
                    rangelength : "密码要在3-10位之间"
                },
                pwdConfirm : {
                    required : "请输入确认密码",
                    rangelength : "密码要在3-10位之间",
                    equalTo : "两次输入密码不一致"
                }
            }
        });
    },
    /**
     * 编辑虚拟化环境，数据格式校验
     */
    initEdit : function() {
        // IP地址验证
        jQuery.validator
                .addMethod(
                        "ip",
                        function(value, element) {
                            return this.optional(element)
                                    || /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/
                                            .test(value);
                        }, "请填写正确的IP地址。");
        $("#editConfigForm").validate({
            debug : false,
            focusInvalid : true, // 当为false时，验证无效时，没有焦点响应
            onkeyup : true,
            submitHandler : function(form) {
                console.info("验证成功！");
            },
            rules : {
                editconfigName : {
                    required : true,
                    maxlength : 50
                },

                editusername : {
                    required : true,
                    maxlength : 50
                },
                editpwd : {
                    required : true,
                    rangelength : [ 3, 10 ]
                },
                editpwdConfirm : {
                    required : true,
                    rangelength : [ 3, 10 ],
                    equalTo : "#editpwd"
                }
            },
            messages : {
                editconfigName : {
                    required : "请输入名称",
                    maxlength : "最多5个字符"
                },
                editusername : {
                    required : "请输入用户名",
                    maxlength : "最多5个字符"
                },
                editpwd : {
                    required : "请输入密码",
                    rangelength : "密码要在3-10位之间"
                },
                editpwdConfirm : {
                    required : "请输入确认密码",
                    rangelength : "密码要在3-10位之间",
                    equalTo : "两次输入密码不一致"
                }
            }
        });
    },
    /**
     * 确认添加虚拟化环境
     */
    addConfigInfo : function() {
        var configname = $("#configName").val();
        var ip = $("#ip").val();
        var username = $("#username").val();
        var pwd = $("#pwd").val();
        sugon.load({
            dataType : "json",
            action : sugon.rootURL + "/venv/addvenvConfigInfo",
            data : {
                configname : configname,
                ip : ip,
                username : username,
                pwd : pwd
            },
            type : 'POST',
            callback : function(result) {
                console.info(result.flag);
                if (result.flag=="0") {
                    layer.closeAll();
                     $("#venvconfig").trigger("click");
                     toastr.success(result.message);
                 }
                if (result.flag=="1") {
                   layer.closeAll();
                    $("#venvconfig").trigger("click");
                    toastr.warning(result.message);
                } 
                if (result.flag=="2") {
                     layer.closeAll();
                     $("#venvconfig").trigger("click");
                     toastr.warning(result.message);
                 } 
                if (result.flag=="-1") {
                	 layer.closeAll();
                     $("#venvconfig").trigger("click");
                     toastr.error(result.message);
                 } 
            }
        })
    },
    /**
     * 弹出虚拟化环境窗口
     */
    addConfigInfoPage : function() {
        $.get(sugon.rootURL+'/venv/addvenvConfigInfoPage', {}, function(str){
            layer.open({
                type: 1,
                title:'接入虚拟化环境',
                //skin: 'layui-layer-rim', //加上边框
                area: ['500px', ''], //宽高
                content: str
            });
        });
        
    },
    /**
     * 添加测试连接
     */
    addTestConfigInfo : function() {
        console.info("addTestConfigInfo");
        if ($("#addConfigForm").valid()) {
            var configname = $("#configName").val();
            var ip = $("#ip").val();
            var username = $("#username").val();
            var pwd = $("#pwd").val();
            var params = {
                configname : configname,
                ip : ip,
                username : username,
                pwd : pwd
            };
            console.info(params);
            this.testConfigInfo(configname, ip, username, pwd);
        }
    },
    /**
     * 修改测试连接
     */
    editTestConfigInfo : function() {
        console.info("editTestConfigInfo");
        if ($("#editConfigForm").valid()) {
            var editid = $("#editid").val();
            var configname = $("#editconfigName").val();
            var ip = $("#editip").val();
            var username = $("#editusername").val();
            var pwd = $("#editpwd").val();
            var params = {
                editid : editid,
                configname : configname,
                ip : ip,
                username : username,
                pwd : pwd
            };
            console.info(params);
            this.testConfigInfo(configname, ip, username, pwd);
        }

    }
}
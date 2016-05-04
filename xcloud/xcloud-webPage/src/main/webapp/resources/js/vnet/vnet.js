cloudmanager.namespace("vnet");
cloudmanager.vnet = {
	init : function() {
		var _this = this;
		$('#oper').show();
		$('#vnetTableId').datagrid({
			url : sugon.rootURL + '/vnet/querynetTable',
			method : 'post',
			striped : true,
			fitColumns : true,
			singleSelect : false,
			rownumbers : true,
			pagination : true,
			pageNumber : 1,
			nowrap : false,
			pageSize : 10,
			toolbar : '#vnettb',
			pageList : [ 5,10, 20, 50, 100],
			idField : 'netPoolId',
			queryParams : {},
			loadMsg : "加载中.....",
			showFooter : true,
			columns : [ [ {
				field : "netPoolId",
				checkbox : true,
				width : 50
			}, {
				field : "netName",
				title : "名称",
				align : "left",
				width : '10%',
			// formatter : cloudmanager.vnet.formatName
			}, {
				field : "orgName",
				title : "组织",
				align : "left",
				width : '15%',
			}, {
				field : "subNet",
				title : "子网",
				align : "center",
				width : '8%'
			}, {
				field : "vlanNO",
				title : "VLAN",
				align : "center",
				width : '8%'
			}, {
				field : "gateway",
				title : "网关",
				align : "center",
				width : '8%'
			}, {
				field : "dns",
				title : "DNS",
				align : "center",
				width : '8%'
			}, {
				field : "synDate",
				title : "同步时间",
				align : "center",
				width : '12%'
			}, {
				field : "operate",
				title : "操作",
				align : "center",
				width : '12%',
				formatter : cloudmanager.vnet.formatOper
			} ] ],
			onBeforeLoad : function(param) {
			},
			onLoadSuccess : function(data) {
			},
			onLoadError : function() {
			},
			onClickCell : function(rowIndex, field, value) {
			}
		});

		var p = $('#vnetTableId').datagrid('getPager');
		$(p).pagination(
				{
					layout : [ 'list', 'sep', 'first', 'prev', 'links', 'next',
							'last', 'sep', 'refresh', 'manual' ],
					displayMsg : '共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
					beforePageText : '跳转到第 ',
					afterPageText : ' 页'
				});
		$.extend($.fn.searchbox.methods, {
			addClearBtn : function(jq, iconCls) {
				return jq.each(function() {
					var t = $(this);
					var opts = t.searchbox('options');
					opts.icons = opts.icons || [];
					opts.icons.unshift({
						iconCls : iconCls,
						handler : function(e) {
							$(e.data.target).searchbox('clear').searchbox(
									'textbox').focus();
							$(this).css('visibility', 'hidden');
							cloudmanager.vnet.doSearch();
						}
					});
					t.searchbox();
					if (!t.searchbox('getText')) {
						t.searchbox('getIcon', 0).css('visibility', 'hidden');
					}
					t.searchbox('textbox').bind('keyup', function() {
						var icon = t.searchbox('getIcon', 0);
						if ($(this).val()) {
							icon.css('visibility', 'visible');
						} else {
							icon.css('visibility', 'hidden');
						}
					});
				});
			}
		});
		$('#vnetSearchInputId')
				.searchbox(
						{
							searcher : function(value, name) {
								var queryParams = $('#vnetTableId').datagrid(
										'options').queryParams;
								queryParams.name = value;
								$("#vnetTableId").datagrid('reload');
							},
							menu : '#tableSearch',
							prompt : '请输入查询内容'
						});
	},
	doSearch : function(value, name) {
		var queryParams = $('#vnetTableId').datagrid('options').queryParams;
		queryParams.name = value;
		$("#vnetTableId").datagrid('reload');
	},
	formatOper : function(val, row, index) {
		return '<a href="#" onclick="cloudmanager.vnet.editVnet(\''
				+ row.netPoolId
				+ '\')">配置</a>&nbsp;&nbsp<a href="#" onclick="cloudmanager.vnet.distrabutePageOne(\''
				+ row.netPoolId + '\',\'' + row.orgName + '\')">分配</a>';
	},
	formatName : function(val, row, index) {
		return '<a href="#" onclick="cloudmanager.vnet.detailVnet(\''
				+ row.netPoolId + '\')">' + row.netName + '</a>';
	},

	/**
	 * 编辑，将控件置为可读写状态
	 */
	editBtn : (function() {
		var obj = document.getElementsByClassName("form-control");
		for (i = 0; i < obj.length; i++) {
			if (obj[i].type = "text" && obj[i].id != "vlanNO"
					&& obj[i].id != "org") {
				obj[i].readOnly = false;
			}
		}
	}),
	/**
	 * 弹出详情网络窗口
	 */
	detailVnet : function(id) {
		$('#editOrdetailVnet').dialog({
			title : '详情',
			width : 600,
			closed : false,
			cache : false,
			href : sugon.rootURL + '/vnet/detailVnetPage?netpoolid=' + id,
			modal : true,
			hcenter : 'center',
			top : 50,
			buttons : [ {
				text : '确定',
				handler : function() {
					$('#editOrdetailVnet').dialog("close");
				}
			} ]
		});
	},
	/**
	 * 弹出配置网络窗口
	 */
	editVnet : function(id) {
		$.get(sugon.rootURL + '/vnet/editVnetPage?netpoolid=' + id, {},
				function(str) {
					layer.open({
						type : 1,
						title : '配置',
						// skin: 'layui-layer-rim', //加上边框
						area : [ '500px', '' ], // 宽高
						content : str,
						btn : [ '确认 ', '取消' ] // 只是为了演示
						,
						yes : function() {
							cloudmanager.vnet.editVnetCofirm();
						},
						cancel : function(index) {
							layer.close(index);
						}
					});
				});
	},

	/**
	 * 添加虚拟网络ip地址格式校验
	 */
	initEdit : function() {
		// IP地址验证
		jQuery.validator
				.addMethod(
						"ip",
						function(value, element) {
							return this.optional(element)
									|| /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([0-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([0-9]|([0-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/
											.test(value);
						}, "请填写正确的IP地址。");

		var _this = this;
		$("#updatevnetForm").validate({
			debug : false,
			focusInvalid : true, // 当为false时，验证无效时，没有焦点响应
			onkeyup : true,
			submitHandler : function(form) {
				console.info("验证成功！");
			},
			rules : {
				editnetName : {
					required : true,
					maxlength : 15
				},
				editgateway : {
					required : true,
					ip : true
				},
				editsubNet : {
					required : true,
					ip : true
				},
				editsubNetNo : {
					required : true,
					min : 1,
					max : 32
				},
				editdns : {
					required : true,
					ip : true
				}
			},
			messages : {
				configName : {
					required : "请输入名称",
					maxlength : "最多15个字符"
				},
				editgateway : {
					required : "请输入网关",
					ip : "请输入正确的网关地址"
				},
				editsubNet : {
					required : "请输入子网掩码",
					ip : "请输入正确的子网掩码"
				},
				editsubNetNo : {
					required : "请输入子网位数",
					min : "最小值是1",
					max : "最大值是32"
				},
				editdns : {
					required : "请输入DNS",
					ip : "请输入正确的DNS地址"
				}
			}
		});
	},
	/**
	 * 配置虚拟网络
	 */
	editVnetCofirm : function() {
		if ($("#updatevnetForm").valid()) {
			var gateway = $("#editgateway").val();
			var subNet = $("#editsubNet").val();
			var subNetNo = $("#editsubNetNo").val();
			var dns = $("#editdns").val();
			var id = $("#editid").val();
			console.info("id=" + id + ",gateway=" + gateway + ",dns=" + dns);
			$.ajax({
				method : "POST",
				url : sugon.rootURL + '/vnet/editBynetpoolId',
				dataType : "json",
				data : {
					gateway : gateway,
					subNet : subNet,
					subNetNo : subNetNo,
					dns : dns,
					id : id
				},
				success : function(result) {
					console.info(result);
					// 0:修改成功；1子网号不对；2：修改失败
					if (result.flag == "0") {
						layer.closeAll();
						toastr.success('修改网络成功!');
						$("#vnetTableId").datagrid('reload');
					}
					if (result.flag == "1") {
						$("#editsubNet").val(result.messeage);
						toastr.warning('您设置的子网IP不正确，已帮您更正!');
						console.info(result.messeage);
						console.info("------------");
					}
					if (result.flag == "2") {
						layer.closeAll();
						toastr.error('修改网络失败!');
						$("#vnetTableId").datagrid('reload');
					}
				}
			})
		}
	},

	distrabutePageOne : function(netpoolId, orgName) {
		console.info("netpoolId=" + netpoolId + ",orgName=" + orgName);
		var names = [];
		var orgs = []
		names.push(netpoolId);
		orgs.push(orgName)
		if (names.length <= 0) {
			toastr.warning('请至少选择一个!');
		}
		var flag = true;
		for (var i = 0; i < orgs.length; i++) {
			if (orgs[i] != "") {
				console.info(orgs[i]);
				flag = false;
				toastr.warning('已分配的网络不能再次分配!');
				break;
			}
		}
		if (flag && names.length > 0) {
			console.info(names);
			$.get(sugon.rootURL + '/vnet/distrabutePage?netpoolids=' + names,
					{}, function(str) {
						layer.open({
							type : 1,
							title : '分配',
							// skin: 'layui-layer-rim', //加上边框
							area : [ '500px', '' ], // 宽高
							content : str,
							btn : [ '确认 ', '取消' ] // 只是为了演示
							,
							yes : function() {
								cloudmanager.vnet.distrabuteNetPool();
							}
						});
					});
		}
	},
	/**
	 * 弹出分配网络窗口
	 */
	distrabutePage : function() {
		var checkedItems = $("#vnetTableId").datagrid("getChecked");
		var names = [];
		var orgs = []
		$.each(checkedItems, function(index, item) {
			names.push(item.netPoolId);
			orgs.push(item.orgName)
		});
		if (names.length <= 0) {
			toastr.warning('请至少选择一个!');
		}
		var flag = true;
		for (var i = 0; i < orgs.length; i++) {
			if (orgs[i] != "") {
				console.info(orgs[i]);
				flag = false;
				toastr.warning('所选列表中包括已分配的网络！已分配的网络不能再次分配!');
				break;
			}
		}
		if (flag && names.length > 0) {
			console.info(names);
			$.get(sugon.rootURL + '/vnet/distrabutePage?netpoolids=' + names,
					{}, function(str) {
						layer.open({
							type : 1,
							title : '分配',
							// skin: 'layui-layer-rim', //加上边框
							area : [ '500px', '' ], // 宽高
							content : str,
							btn : [ '确认 ', '取消' ] // 只是为了演示
							,
							yes : function() {
								cloudmanager.vnet.distrabuteNetPool();
							}
						});
					});
		}
	},
	/**
	 * 确认分配网给某一组织
	 */
	distrabuteNetPool : function() {
		var checkedItems = $("#vnetTableId").datagrid("getChecked");
		var names = [];
		$.each(checkedItems, function(index, item) {
			names.push(item.netPoolId);
		});
		console.info(names);
		var orgId = $("#orgs option:selected").val();
		var orgName = $("#orgs option:selected").text();
		console.info("orgId" + orgId + "orgName" + orgName);
		if (orgId == "-1") {
			toastr.warning('请选择组织!');
		} else {
			$.ajax({
				method : "post",
				url : sugon.rootURL + '/vnet/distrabuteNetPool?netpoolIds='
						+ names + "&orgId=" + orgId + "&orgName=" + orgName,
				dataType : "json",
				success : function(result) {
					console.info(result);
					layer.closeAll();
					$("#vnetTableId").datagrid('reload');
					toastr.success('分配网络成功!');
				}
			})
		}
	},

	/**
	 * 同步网络数据
	 */
	synNetdata : function() {
		layer.confirm('确认同步网络数据？', {
			title : '同步',
			icon : 3,
			btn : [ '确认', '取消' ]
		// 按钮
		}, function(index) {
			sugon.load({
				type : 'POST',
				action : sugon.rootURL + '/vnet/synNetpoolDataVenvConfig',
				callback : function(result) {
					$("#vnetTableId").datagrid('reload');
					layer.closeAll();
					toastr.success('同步网络数据成功!');
				}
			});
		});
	},
	/*
	 * 加载无效网络
	 */
	getUnAvlnetpool : function(pVDCId) {
		sugon.load({
			selector : '#content',
			type : 'get',
			action : sugon.rootURL + '/vnet/unquerynetTable',
			callback : function(result) {
			}
		});
	},

	/*
	 * 初始化无效网络成功
	 */
	initUnavl : function() {
		$('#oper').hide();
		$('#vnetTableId').datagrid({
			url : sugon.rootURL + '/vnet/unquerynetTable',
			method : 'post',
			striped : true,
			fitColumns : true,
			singleSelect : false,
			rownumbers : true,
			pagination : true,
			pageNumber : 1,
			nowrap : false,
			pageSize : 20,
			toolbar : '#vnettb',
			pageList : [ 2, 20, 50, 100, 150, 200 ],
			idField : 'netPoolId',
			queryParams : {},
			loadMsg : "加载中.....",
			showFooter : true,
			columns : [ [ {
				field : "netName",
				title : "名称",
				align : "left",
				width : '20%',
			// formatter : cloudmanager.vnet.formatName
			}, {
				field : "orgName",
				title : "组织",
				align : "left",
				width : '20%',
			}, {
				field : "subNet",
				title : "子网",
				align : "center",
				width : '8%'
			}, {
				field : "vlanNO",
				title : "VLAN",
				align : "center",
				width : '8%'
			}, {
				field : "gateway",
				title : "网关",
				align : "center",
				width : '8%'
			}, {
				field : "dns",
				title : "DNS",
				align : "center",
				width : '8%'
			}, {
				field : "synDate",
				title : "同步时间",
				align : "center",
				width : '12%'
			} ] ],
			onBeforeLoad : function(param) {
			},
			onLoadSuccess : function(data) {
			},
			onLoadError : function() {
			},
			onClickCell : function(rowIndex, field, value) {
			}
		});

		var p = $('#vnetTableId').datagrid('getPager');
		$(p).pagination(
				{
					layout : [ 'list', 'sep', 'first', 'prev', 'links', 'next',
							'last', 'sep', 'refresh', 'manual' ],
					displayMsg : '共 {total} 条数据 当前显示第 {from} - {to} 条数据️',
					beforePageText : '跳转到第 ',
					afterPageText : ' 页'
				});
		$.extend($.fn.searchbox.methods, {
			addClearBtn : function(jq, iconCls) {
				return jq.each(function() {
					var t = $(this);
					var opts = t.searchbox('options');
					opts.icons = opts.icons || [];
					opts.icons.unshift({
						iconCls : iconCls,
						handler : function(e) {
							$(e.data.target).searchbox('clear').searchbox(
									'textbox').focus();
							$(this).css('visibility', 'hidden');
							cloudmanager.vnet.doSearch();
						}
					});
					t.searchbox();
					if (!t.searchbox('getText')) {
						t.searchbox('getIcon', 0).css('visibility', 'hidden');
					}
					t.searchbox('textbox').bind('keyup', function() {
						var icon = t.searchbox('getIcon', 0);
						if ($(this).val()) {
							icon.css('visibility', 'visible');
						} else {
							icon.css('visibility', 'hidden');
						}
					});
				});
			}
		});
		$('#vnetSearchInputId')
				.searchbox(
						{
							searcher : function(value, name) {
								var queryParams = $('#vnetTableId').datagrid(
										'options').queryParams;
								queryParams.name = value;
								$("#vnetTableId").datagrid('reload');
							},
							menu : '#tableSearch',
							prompt : '请输入查询内容'
						});
	},

	proVDCDetailBack : function() {
		sugon.load({
			selector : '#main',
			type : 'POST',
			action : sugon.rootURL + '/proVDC/providerVDCList',
			callback : function(result) {
			}
		});
	},
	validateSubnet : function() {
		if ($("#updatevnetForm").valid()) {
			console.info("-------------start-----------");
			var subNet = $("#editsubNet").val();
			var subNetNo = $("#editsubNetNo").val();
			if(subNet!="0.0.0.0"){
				console.info("-------------" + subNetNo + "-----------");
				this.IPFix.setIp(subNet);
				this.IPFix.setPrefix(subNetNo);
				this.IPFix.init();
				console.info("-------------" + this.IPFix.netaddr + "-----------");
				if(subNet!=this.IPFix.netaddr){
					$("#editsubNet").val(this.IPFix.netaddr);
					toastr.warning('您设置的子网IP不正确，已帮您更正!');
				}
			}
		}
	},
	IPFix : {
		ip : "",
		prefix : 0,
		netmask : "",
		netaddr : "",
		setPrefix : function(prefix) {
			this.prefix = prefix;
		},
		setIp : function(ip) {
			this.ip = ip;
		},
		show : function() {
			alert("ip addr:" + this.ip);
			alert("net addr:" + this.netaddr);
			alert("netmask:" + this.netmask);
		},
		init : function() {
			var i = 1;
			var netmask = "";
			var netaddr = "";
			var ip = this.ip;
			while (i <= 32) {

				if (i > this.prefix)
					netmask += '0';
				else
					netmask += '1';
				if (i < 32 && i % 8 == 0)
					netmask += '.';
				i++;
			}
			var ips = ip.split(".");
			var netmasks = netmask.split(".");
			netmask = "";
			for (var index = 0;index < netmasks.length;index++) {
				var ip = parseInt(ips[index]);
				var mask = parseInt(netmasks[index], 2);
				netmask += mask;
				netaddr += ip & mask;
				if (index != 3) {
					netmask += '.';
					netaddr += '.';
				}
			}
			console.info("-------------" + netmask + "-----------");
			console.info("-------------" + netaddr + "-----------");
			this.netmask = netmask;
			this.netaddr = netaddr;
		}
	}

};
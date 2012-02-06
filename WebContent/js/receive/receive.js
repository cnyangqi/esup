// ---------------------------------------------公共代码开始-----------------------------------------------------

/** 格式化日期 */
function formatterDate(value, rec) {
	if (value) {
		var date = new Date(value);
		var year = date.getFullYear();
		var month = (date.getMonth() + 1).toString().length == 2 ? (date
				.getMonth() + 1) : '0' + (date.getMonth() + 1);
		var day = date.getDate().toString().length == 2 ? date.getDate() : '0'
				+ date.getDate();
		var hour = date.getHours().toString().length == 2
				? date.getHours()
				: '0' + date.getHours();
		var minute = date.getMinutes().toString().length == 2 ? date
				.getMinutes() : '0' + date.getMinutes();

		return year + '-' + month + '-' + day + ' ' + hour + ':' + minute;
	} else {
		return '';
	}
}

/** easyui datetimebox 时间格式 */
function formatterDatetimebox(value, rec) {
	if (value) {
		var date = new Date(value);
		var year = date.getFullYear();
		var month = (date.getMonth() + 1).toString().length == 2 ? (date
				.getMonth() + 1) : '0' + (date.getMonth() + 1);
		var day = date.getDate().toString().length == 2 ? date.getDate() : '0'
				+ date.getDate();
		var hour = date.getHours().toString().length == 2
				? date.getHours()
				: '0' + date.getHours();
		var minute = date.getMinutes().toString().length == 2 ? date
				.getMinutes() : '0' + date.getMinutes();
		var second = date.getSeconds().toString().length == 2 ? date
				.getSeconds() : '0' + date.getSeconds();

		return year + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':'
				+ second;
	} else {
		return '';
	}
}

// ---------------------------------------------公共代码结束-----------------------------------------------------

// ---------------------------------------------数据字典开始-----------------------------------------------------
var getComboxDataUrl = '/datadictionary/datadictionary!queryAllDataDictionaryByTypeCode.action';

var YWLX;// 业务类型
var SPLX;// 商品类型
var SPDW;// 商品单位
var SHLX;// 收货联系
var SHFS;// 收货方式

function initDataDictionary() {

	$.ajax({
				async : false,// required
				type : 'post',
				dataType : 'json',
				timeout : 10000,
				url : ctx + getComboxDataUrl,
				data : {
					typeCodes : 'ywlx,splx,spdw,shlx,shfs'
				},
				success : function(data) {
					YWLX = data.ywlx;
					SPLX = data.splx;
					SPDW = data.spdw;
					SHLX = data.shlx;
					SHFS = data.shfs;

					$('#businessType').combobox({// 业务类型
						width : 152,
						valueField : 'value',
						textField : 'name'
					}).combobox('loadData', YWLX);

					$('#category').combobox({// 商品类型
						width : 152,
						valueField : 'value',
						textField : 'name'
					}).combobox('loadData', SPLX);

					$('#unit').combobox({// 商品单位
						width : 152,
						valueField : 'value',
						textField : 'name'
					}).combobox('loadData', SPDW);

					$('#traderQq').combobox({// 收货联系
						width : 152,
						valueField : 'value',
						textField : 'name'
					}).combobox('loadData', SHLX);

					$('#traderMode').combobox({// 收货方式
						width : 152,
						valueField : 'value',
						textField : 'name'
					}).combobox('loadData', SHFS);

				}
			});

}

// ---------------------------------------------数据字典结束-----------------------------------------------------

// ---------------------------------------------树组件开始-------------------------------------------------------

// ----------------------------------------------树组件1开始------------------------------------------------------

var queryNodeUrl = '/receive/receive.action';
var saveNodeUrl = '/receive/receive!save.action';
var deleteNodeUrl = '/receive/receive!delete.action';
var queryNodeByIdUrl = '/receive/receive!input.action';

var selectedNode;// 树操作节点
var parentNode;// 树父亲节点
var grandfatherNode;// 树祖父节点

/** 记录树节点操作状态 */
function recordNode($tree) {
	selectedNode = $tree.tree('getSelected');
	if (selectedNode) {
		parentNode = $tree.tree('getParent', selectedNode.target);
	}
	if (parentNode) {
		grandfatherNode = $tree.tree('getParent', parentNode.target);
	}
}

/** 关闭树节点管理窗口 */
function closeNodeWin() {

}

/** 初始化树节点保存表单1 */
function initForm1() {
	$('#form_tree1').form({
				url : ctx + saveNodeUrl,
				onSubmit : function() {
					return $(this).form('validate');
				},
				success : function(data) {
					closeNodeWin();

					if (selectedNode) {
						if (parentNode) {// 如果父亲节点存在，则新增节点类型为普通子节点
							$('#tree1').tree('reload', parentNode.target);
							return;
						}
					}
					$('#tree1').tree('reload');// 刷新树根节点
				}
			});
}

/** 初始化树1 */
function initTree1() {
	$('#tree1').tree({
				url : ctx + queryNodeUrl,
				onClick : function(node) {// 配置树节点单击开关函数，同时记录当前操作节点以及其父亲节点、祖父节点
					$(this).tree('toggle', node.target);
					recordNode($(this));
				},
				onLoadSuccess : function() {// 当树reload完毕，恢复操作节点的选择状态，并更新操作节点等状态
					if (selectedNode) {
						var node = $(this).tree('find', selectedNode.id);
						$(this).tree('select', node.target);
						$(this).tree('expand', node.target);
					}
					recordNode($(this));
				}
			});
}

// ----------------------------------------------树组件1结束------------------------------------------------------

// ---------------------------------------------树组件结束-------------------------------------------------------

// ---------------------------------------------列表组件开始-----------------------------------------------------

// ---------------------------------------------列表组件1开始----------------------------------------------------

var queryRecordUrl = '/receive/receive.action';
var saveRecordUrl = '/receive/receive!save.action';
var deleteRecordUrl = '/receive/receive!delete.action';
var batchDeleteRecordUrl = '/receive/receive!batchDelete.action';
var queryRecordByIdUrl = '/receive/receive!input.action';
var toggleRecordStatusUrl = '/receive/receive!toggleReceiveStatus.action';

/** 手动刷新列表查询参数 */
function refreshQuery() {
	var query = '{';
	query = query + '\"LIKES_game\":\"%' + $('#key1').val() + '%\",';// 模糊查询任意位置
	query = query + '\"LIKES_server\":\"' + $('#key2').val() + '\",';
	query = query + '\"GTN_unitPrice\":\"' + $('#key3').val() + '\",';
	query = query + '\"GED_publicDate\":\"'
			+ $('#key4').datetimebox("getValue") + '\",';
	query = query + '\"LED_expirationDate\":\"'
			+ $('#key5').datetimebox("getValue") + '\"';
	query += '}';
	return query;
}

/** 手动刷新列表 */
function refreshDatagrid() {
	$('#datagrid1').datagrid('reload');
}

/** 查询列表记录 */
function queryRecord() {

	var value4 = $('#key4').datetimebox("getValue");
	var value5 = $('#key5').datetimebox("getValue");

	if ($('#key1').val().length > 0 || $('#key2').val().length > 0
			|| $('#key3').val().length > 0 || value4.length > 0
			|| value5.length > 0) {
		refreshDatagrid();
	} else {
		showMsg('查询条件没有填写。');
	}

}

/** 重置列表记录查询 */
function queryReset() {
	$('#key1').val('');
	$('#key2').val('');
	$('#key3').val('');
	$('#key4').datetimebox('setValue', '');
	$('#key5').datetimebox('setValue', '');
	refreshDatagrid();
}

/** 打开新增列表记录窗口 */
function addRecordWin() {

	$('#businessType').combobox('select', '1');// 业务类型默认网游类
	$('#traderMode').combobox('select', '1');// 交易方式默认见面
	$('#category').combobox('select', '2');// 商品类型默认游戏币
	$('#unit').combobox('select', '1');// 收货单位默认金

	$('#win_record').window({
				iconCls : 'icon-page_add',
				maximizable : false
			}).window('setTitle', '新增收货单').window('open');

}

/** 打开修改窗口 */
function editRecordWin(id) {
	var temp = $('#form_datagrid1').json2form({
				url : ctx + queryRecordByIdUrl,
				data : {
					'id' : id
				}
			});

	$('#businessType').combobox('select', temp.businessType);
	$('#category').combobox('select', temp.category);
	$('#traderMode').combobox('select', temp.traderMode);
	$('#unit').combobox('select', temp.unit);
	$('#traderQq').combobox('select', temp.traderQq);

	$('#expirationDate').datetimebox('setValue',
			formatterDatetimebox(temp.expirationDate));

	$('#win_record').window({
				iconCls : 'icon-page_edit',
				maximizable : false
			}).window('setTitle', '修改收货单').window('open');
}

/** 重置列表记录管理窗口 */
function resetRecordWin() {
	$('#form_datagrid1').form('clear');
	$('#form_datagrid1 input[name="id"]').get(0).value = '';
}

/** 关闭列表记录管理窗口 */
function closeRecordWin() {
	resetRecordWin();
	$('#win_record').window('close');
}

/** 保存列表记录 */
function saveRecord() {
	$('#form_datagrid1').submit();
}

/** 批量删除列表记录 */
function batchDeleteRecord(ids) {
	var selects = $('#datagrid1').datagrid('getSelections');
	var tmp = [];
	if (selects.length > 0) {
		for (var i = 0; i < selects.length; i++) {
			tmp.push(selects[i].id);
		}

		$.ajax({
					async : true,// required
					type : 'post',
					dataType : 'json',
					timeout : 10000,
					url : ctx + batchDeleteRecordUrl,
					data : {
						ids : tmp.join(',')
					},
					success : function(data) {
						refreshDatagrid();
						showMsg('删除数据成功。');
					}
				});
	} else {
		showMsg('请选择您要删除的数据。');
	}
}

/** 审核收货单 */
function pass(id) {
	if (id) {
		$.ajax({
					async : true,// required
					type : 'post',
					dataType : 'json',
					timeout : 10000,
					url : ctx + toggleRecordStatusUrl,
					data : {
						id : id
					},
					success : function(data) {
						refreshDatagrid();
						showMsg('审核收货单成功。');
					}
				});
	}
}

/** 初始化搜索字段 */
function initSearchField() {
	$('#key4').datetimebox();
	$('#key5').datetimebox();
}

/** 初始化表单截止日期 */
function initExpirationDate() {
	$('#expirationDate').datetimebox();
}

/** 初始化表单选择按钮 */
function initSelectButton() {
	$('#game_button').click(function() {
				$('#win_select_game').window({
							iconCls : 'icon-page_add',
							maximizable : false
						}).window('setTitle', '选择游戏').window('open');
			});

	$('#area_button').click(function() {
				var tmp1 = $('#form_datagrid1 input[name="gameid"]').get(0).value;

				if (tmp1.length > 0) {
					$('#win_select_gamearea').window({
								iconCls : 'icon-page_add',
								maximizable : false
							}).window('setTitle', '选择游戏区').window('open');

					refresh_datagrid3();
				} else {
					showMsg('必须先选择游戏，再选择游戏区，最后才能选择游戏服务器');
				}
			});

	$('#server_button').click(function() {
				var tmp2 = $('#form_datagrid1 input[name="gameid"]').get(0).value;
				var tmp3 = $('#form_datagrid1 input[name="areaid"]').get(0).value;

				if (tmp2 > 0 && tmp3 > 0) {
					$('#win_select_gameserver').window({
								iconCls : 'icon-page_add',
								maximizable : false
							}).window('setTitle', '选择游戏服务器').window('open');

					refresh_datagrid4();
				} else {
					showMsg('必须先选择游戏，再选择游戏区，最后才能选择游戏服务器');
				}
			});
}

/** 初始化表单Ajax提交 */
function initForm1() {

	$('#form_datagrid1').form({
				url : ctx + saveRecordUrl,
				onSubmit : function() {
					return $(this).form('validate');
				},
				success : function(data) {
					closeRecordWin();
					refreshDatagrid();
				}
			});
}

/** 初始化列表1 */
function initDataGrid1() {
	$('#datagrid1').datagrid({
		nowrap : false,
		striped : true,
		border : false,
		url : ctx + queryRecordUrl,
		pageSize : 20,
		sortName : '',
		sortOrder : 'DESC',// DESC 降序，ASC升序
		columns : [[{
					field : 'ck',
					title : '全选取消',
					checkbox : true,
					width : 80
				}, {
					field : 'serialNumber',
					title : '流水号',
					align : 'center',
					width : 130
				}, {
					field : 'category',
					title : '收货类型',
					align : 'center',
					width : 60,
					formatter : function(value, rec) {
						for (var i = 0; i < SPLX.length; i++) {
							if (SPLX[i].value == value) {
								return SPLX[i].name;
							}
						}
					}
				}, {
					field : 'game',
					title : '游戏名',
					align : 'center',
					width : 90,
					formatter : function(value, rec) {
						return '<font color="blue">' + value + '</font>';
					}
				}, {
					field : 'area',
					title : '区名',
					align : 'center',
					width : 100
				}, {
					field : 'server',
					title : '服务器名',
					align : 'center',
					width : 150
				}, {
					field : 'total',
					title : '采购总量',
					align : 'center',
					width : 80
				}, {
					field : 'unit',
					title : '单位',
					align : 'center',
					width : 50,
					formatter : function(value, rec) {
						for (var i = 0; i < SPDW.length; i++) {
							if (SPDW[i].value == value) {
								return SPDW[i].name;
							}
						}
					}
				}, {
					field : 'unitPrice',
					title : '单价(元)',
					align : 'center',
					width : 60,
					formatter : function(value, rec) {
						return '<font color="red">' + value + '</font>';
					}
				}, {
					field : 'tmp1',
					title : '总价(元)',
					align : 'right',
					width : 80,
					formatter : function(value, rec) {
						return '<font color="red">'
								+ (rec.total * rec.unitPrice).toFixed(0)
								+ '</font>';
					}
				}, {
					field : 'demand',
					title : '剩余采购量',
					align : 'center',
					width : 80
				}, {
					field : 'minSupply',
					title : '交易下限',
					align : 'center',
					width : 80
				}, {
					field : 'publicDate',
					title : '发布日期',
					width : 110,
					formatter : formatterDate
				}, {
					field : 'expirationDate',
					title : '截止日期',
					width : 110,
					formatter : formatterDate
				}, {
					field : 'publishName',
					title : '发布用户',
					align : 'center',
					width : 80,
					formatter : function(value, rec) {
						return '<font color="blue">' + value + '</font>';
					}
				}, {
					field : 'traderQq',
					title : '交易QQ',
					align : 'center',
					width : 80,
					formatter : function(value, rec) {
						return '<font color="blue">' + value + '</font>';
					}
				}, {
					field : 'status',
					title : '状态',
					align : 'center',
					width : 50,
					formatter : function(value, rec) {
						if (value == '1') {
							return '<font color="green">已审核</font>';
						} else {
							return '<font color="red">未审核</font>';
						}
					}
				}, {
					field : 'action',
					title : '操作',
					rowspan : 3,
					width : 190,
					formatter : function(value, rec) {
						var open = '<a class="l-btn l-btn-plain" style="float:left;" href="javascript: pass(\''
								+ rec.id + '\');">';
						open += ' <span class="l-btn-left" style="float: left;">';
						open += ' <span class="l-btn-text icon-ok" style="padding-left: 20px;">审核</span>';
						open += ' </span>';
						open += ' </a>';
						var stop = '<a class="l-btn l-btn-plain" style="float:left;" href="javascript: stop_dd(\''
								+ rec.id + '\');">';
						stop += ' <span class="l-btn-left" style="float: left;">';
						stop += ' <span class="l-btn-text icon-cross" style="padding-left: 20px;">停用</span>';
						stop += ' </span>';
						stop += ' </a>';
						var edit = '<a class="l-btn l-btn-plain" style="float:left;" href="javascript: editRecordWin(\''
								+ rec.id + '\');">';
						edit += ' <span class="l-btn-left" style="float: left;">';
						edit += ' <span class="l-btn-text icon-page_edit" style="padding-left: 20px;">修改</span>';
						edit += ' </span>';
						edit += ' </a>';
						var del = '<a class="l-btn l-btn-plain" style="float:left;" href="javascript: del_dd(\''
								+ rec.id + '\');">';
						del += ' <span class="l-btn-left" style="float: left;">';
						del += ' <span class="l-btn-text icon-dd_delete" style="padding-left: 20px;">删除</span>';
						del += ' </span>';
						del += ' </a>';

						if (rec.status && rec.status == '1') {// 已审核
							// return stop + edit + del;
							// return stop;
						} else {
							if (auditFlag) {// 有审核权限
								return open + edit;
							} else {
								return edit;
							}
						}
					}
				}]],
		toolbar : [{
					id : 'btnadd',
					text : '新增',
					iconCls : 'icon-page_add',
					handler : function() {
						addRecordWin();
					}
				}, {
					id : 'btncut',
					text : '批量删除',
					iconCls : 'icon-page_delete',
					handler : function() {
						batchDeleteRecord();
					}
				}],
		pagination : true,
		rownumbers : true,
		onBeforeLoad : function(param) {
			param.query = refreshQuery;
		}
	});
}

// ---------------------------------------------列表组件1结束----------------------------------------------------

// ---------------------------------------------列表组件结束-----------------------------------------------------

// ---------------------------------------------选择窗口开始-----------------------------------------------------

// ---------------------------------------------选择窗口1开始----------------------------------------------------
var queryRecordUrl2 = '/game/game.action';

/** 手动刷新列表查询参数(选择游戏) */
function refreshQuery2() {
	var query = '{';
	query = query + '\"LIKES_gamename\":\"%' + $('#key11').val() + '%\"';
	// query = query + '\" \":\"' + $('# ').val() + '\"';
	query += '}';
	return query;
}

/** 手动刷新列表 */
function refresh_datagrid2() {
	$('#datagrid2').datagrid('reload');
}

/** 游戏查询 */
function queryRecord2() {
	if ($('#key11').val().length > 0) {
		refresh_datagrid2();
	} else {
		showMsg('查询条件没有填写。');
	}
}

/** 游戏查询重置 */
function queryReset2() {
	$('#key11').val('');
	refresh_datagrid2();
}

/** 保存选择好的游戏 */
function saveRecord2() {
	var record = $('#datagrid2').datagrid('getSelected');
	if (record) {
		$('#form_datagrid1 input[name="game"]').get(0).value = record.gamename;
		$('#form_datagrid1 input[name="gameid"]').get(0).value = record.id;

		// 联动游戏区和服务器
		$('#form_datagrid1 input[name="area"]').get(0).value = '';
		$('#form_datagrid1 input[name="areaid"]').get(0).value = '';
		$('#form_datagrid1 input[name="server"]').get(0).value = '';
		$('#form_datagrid1 input[name="serverid"]').get(0).value = '';

		closeRecordWin2();
	} else {
		showMsg('请选择你要保存的记录。');
	}
}

/** 关闭游戏选择窗口 */
function closeRecordWin2() {
	$('#datagrid2').datagrid('unselectAll');
	$('#win_select_game').window('close');
}

/** 选择游戏列表初始化 */
function initSelectGameGrid() {
	$('#datagrid2').datagrid({
				nowrap : false,
				striped : true,
				border : false,
				singleSelect : true,
				url : ctx + queryRecordUrl2,
				sortName : '',
				sortOrder : 'ASC',// DESC 降序，ASC升序
				columns : [[{
							field : 'ck',
							title : '全选取消',
							checkbox : true,
							width : 80
						}, {
							field : 'gamename',
							title : '游戏名称',
							align : 'center',
							width : 130
						}, {
							field : 'firstcode',
							title : '排序首字母',
							align : 'center',
							width : 60
						}, {
							field : 'ishot',
							title : '是否热门',
							align : 'center',
							width : 90,
							formatter : function(value, rec) {
								return '<font color="blue">' + value
										+ '</font>';
							}
						}, {
							field : 'visible',
							title : '是否可见',
							align : 'center',
							width : 150
						}]],
				pagination : true,
				rownumbers : true,
				onBeforeLoad : function(param) {
					param.query = refreshQuery2;
				}
			});
}

// ---------------------------------------------选择窗口1结束----------------------------------------------------

// --------------------------------------------选择窗口2开始----------------------------------------------------
var queryRecordUrl3 = '/gamearea/gamearea.action';

/** 手动刷新列表查询参数(选择游戏) */
function refreshQuery3() {
	var gameid = $('#form_datagrid1 input[name="gameid"]').get(0).value;

	var query = '{';
	query = query + '\"LIKES_areaname\":\"%' + $('#key111').val() + '%\",';
	query = query + '\"EQL_gameid\":\"' + gameid + '\"';
	query += '}';
	return query;
}

/** 手动刷新列表 */
function refresh_datagrid3() {
	$('#datagrid3').datagrid('reload');
}

/** 游戏查询 */
function queryRecord3() {
	if ($('#key111').val().length > 0) {
		refresh_datagrid3();
	} else {
		showMsg('查询条件没有填写。');
	}
}

/** 游戏查询重置 */
function queryReset3() {
	$('#key111').val('');
	refresh_datagrid3();
}

/** 保存选择好的游戏 */
function saveRecord3() {
	var record = $('#datagrid3').datagrid('getSelected');
	if (record) {
		$('#form_datagrid1 input[name="area"]').get(0).value = record.areaname;
		$('#form_datagrid1 input[name="areaid"]').get(0).value = record.id;
		closeRecordWin3();
	} else {
		showMsg('请选择你要保存的记录。');
	}
}

/** 关闭游戏选择窗口 */
function closeRecordWin3() {
	$('#datagrid3').datagrid('unselectAll');
	$('#win_select_gamearea').window('close');
}

/** 选择游戏列表初始化 */
function initSelectGameAreaGrid() {
	$('#datagrid3').datagrid({
				nowrap : false,
				striped : true,
				border : false,
				singleSelect : true,
				url : ctx + queryRecordUrl3,
				sortName : '',
				sortOrder : 'ASC',// DESC 降序，ASC升序
				columns : [[{
							field : 'ck',
							title : '全选取消',
							checkbox : true,
							width : 80
						}, {
							field : 'areaname',
							title : '游戏区名称',
							align : 'center',
							width : 130
						}, {
							field : 'visible',
							title : '是否可见',
							align : 'center',
							width : 150
						}]],
				pagination : true,
				rownumbers : true,
				onBeforeLoad : function(param) {
					param.query = refreshQuery3;
				}
			});
}

// --------------------------------------------选择窗口2结束----------------------------------------------------

// --------------------------------------------选择窗口3开始----------------------------------------------------
var queryRecordUrl4 = '/gameserver/gameserver.action';

/** 手动刷新列表查询参数(选择游戏) */
function refreshQuery4() {
	var gameid = $('#form_datagrid1 input[name="gameid"]').get(0).value;
	var areaid = $('#form_datagrid1 input[name="areaid"]').get(0).value;

	var query = '{';
	query = query + '\"LIKES_servername\":\"%' + $('#key1111').val() + '%\",';
	query = query + '\"EQL_gameid\":\"' + gameid + '\",';
	query = query + '\"EQL_areaid\":\"' + areaid + '\"';
	query += '}';
	return query;
}

/** 手动刷新列表 */
function refresh_datagrid4() {
	$('#datagrid4').datagrid('reload');
}

/** 游戏查询 */
function queryRecord4() {
	if ($('#key1111').val().length > 0) {
		refresh_datagrid4();
	} else {
		showMsg('查询条件没有填写。');
	}
}

/** 游戏查询重置 */
function queryReset4() {
	$('#key1111').val('');
	refresh_datagrid4();
}

/** 保存选择好的游戏 */
function saveRecord4() {
	var record = $('#datagrid4').datagrid('getSelected');
	if (record) {
		$('#form_datagrid1 input[name="server"]').get(0).value = record.servername;
		$('#form_datagrid1 input[name="serverid"]').get(0).value = record.id;
		closeRecordWin4();
	} else {
		showMsg('请选择你要保存的记录。');
	}
}

/** 关闭游戏选择窗口 */
function closeRecordWin4() {
	$('#datagrid4').datagrid('unselectAll');
	$('#win_select_gameserver').window('close');
}

/** 选择游戏列表初始化 */
function initSelectGameServerGrid() {
	$('#datagrid4').datagrid({
				nowrap : false,
				striped : true,
				border : false,
				singleSelect : true,
				url : ctx + queryRecordUrl4,
				sortName : '',
				sortOrder : 'ASC',// DESC 降序，ASC升序
				columns : [[{
							field : 'ck',
							title : '全选取消',
							checkbox : true,
							width : 80
						}, {
							field : 'servername',
							title : '游戏服务器名称',
							align : 'center',
							width : 130
						}, {
							field : 'visible',
							title : '是否可见',
							align : 'center',
							width : 150
						}]],
				pagination : true,
				rownumbers : true,
				onBeforeLoad : function(param) {
					param.query = refreshQuery4;
				}
			});
}

// ---------------------------------------------选择窗口3结束----------------------------------------------------

// ---------------------------------------------选择窗口结束-----------------------------------------------------

// ---------------------------------------------DOM渲染完毕，执行JavaScript初始化模块-----------------------------

$(function() {
			initDataDictionary();// 数据字典初始化

			initSearchField();
			initExpirationDate();
			initSelectButton();
			initForm1();
			initDataGrid1();

			initSelectGameGrid();
			initSelectGameAreaGrid();
			initSelectGameServerGrid();
		});

// ---------------------------------------------DOM渲染完毕，执行JavaScript初始化模块结束-------------------------

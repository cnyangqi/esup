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
		var hour = date.getHours().toString().length == 2 ? date.getHours()
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
		var hour = date.getHours().toString().length == 2 ? date.getHours()
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

var ZHLX;// 账户类型

function initDataDictionary() {

	$.ajax({
		async : false,// required
		type : 'post',
		dataType : 'json',
		timeout : 10000,
		url : ctx + getComboxDataUrl,
		data : {
			typeCodes : 'zhlx'
		},
		success : function(data) {
			ZHLX = data.zhlx;

			$('#accountType').combobox({// 账户类型
				width : 152,
				valueField : 'value',
				textField : 'name'
			}).combobox('loadData', ZHLX);

			$('#accountType').combobox(
					{
						'onSelect' : function(rec) {
							$('#form_datagrid1 input[name="accountBank"]').get(
									0).value = '';

							if (rec.name == '支付宝') {
								$('#form_datagrid1 input[name="accountBank"]')
										.get(0).value = rec.value;
								$('#form_datagrid1 input[name="accountBank"]')
										.attr('readonly', 'readonly');
							}
							if (rec.name == '财付通') {
								$('#form_datagrid1 input[name="accountBank"]')
										.get(0).value = rec.value;
								$('#form_datagrid1 input[name="accountBank"]')
										.attr('readonly', 'readonly');
							}
						}
					});

		}
	});

}

// ---------------------------------------------数据字典结束-----------------------------------------------------

// ---------------------------------------------列表组件开始-----------------------------------------------------

// ---------------------------------------------列表组件1开始----------------------------------------------------

var queryRecordUrl = '/savingaccount/savingaccount.action';
var saveRecordUrl = '/savingaccount/savingaccount!save.action';
var deleteRecordUrl = '/savingaccount/savingaccount!delete.action';
var batchDeleteRecordUrl = '/savingaccount/savingaccount!batchDelete.action';
var queryRecordByIdUrl = '/savingaccount/savingaccount!input.action';
var toggleRecordStatusUrl = '/savingaccount/savingaccount!toggleSavingaccountStatus.action';

/** 手动刷新列表查询参数 */
function refreshQuery() {
	var query = '{';
	query = query + '\"LIKES_accountBank\":\"%' + $('#key1').val() + '%\",';
	query = query + '\"LIKES_accountName\":\"%' + $('#key2').val() + '%\",';
	query = query + '\"LIKES_account\":\"%' + $('#key3').val() + '%\",';
	query = query + '\"GED_updateDate\":\"'
			+ $('#key4').datetimebox("getValue") + '\",';
	query = query + '\"LED_updateDate\":\"'
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
	$('#form_datagrid1 input[name="accountBank"]').removeAttr('readonly');

	$('#win_record').window({
		iconCls : 'icon-coins_add',
		maximizable : false
	}).window('setTitle', '新增账户').window('open');

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
	$('#traderContact').combobox('select', temp.traderContact);

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
		for ( var i = 0; i < selects.length; i++) {
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
				showMsg('删除记录成功。');
			}
		});
	} else {
		showMsg('请选择您要删除的记录。');
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
	$('#datagrid1')
			.datagrid(
					{
						nowrap : false,
						striped : true,
						border : false,
						url : ctx + queryRecordUrl,
						sortName : '',
						sortOrder : 'ASC',// DESC 降序，ASC升序
						columns : [ [
								{
									field : 'ck',
									title : '全选取消',
									checkbox : true,
									width : 80
								},
								{
									field : 'accountType',
									title : '账户类型',
									align : 'center',
									width : 130,
									formatter : function(value, rec) {
										for ( var i = 0; i < ZHLX.length; i++) {
											if (ZHLX[i].value == value) {
												return ZHLX[i].name;
											}
										}
									}
								},
								{
									field : 'accountBank',
									title : '开户行',
									align : 'center',
									width : 200
								},
								{
									field : 'accountName',
									title : '收款人',
									align : 'center',
									width : 90,
									formatter : function(value, rec) {
										return '<font color="blue">' + value
												+ '</font>';
									}
								},
								{
									field : 'account',
									title : '收款账户',
									align : 'center',
									width : 100
								},
								{
									field : 'updateDate',
									title : '更新日期',
									width : 110,
									formatter : formatterDate
								},
								{
									field : 'status',
									title : '账户状态',
									align : 'center',
									width : 80,
									formatter : function(value, rec) {
										if (value == '1') {
											return '<font color="green">默认收款</font>';
										}
									}
								},
								{
									field : 'action',
									title : '操作',
									rowspan : 3,
									width : 190,
									formatter : function(value, rec) {
										var defa = '<a class="l-btn l-btn-plain" style="float:left;" href="javascript: pass(\''
												+ rec.id + '\');">';
										defa += ' <span class="l-btn-left" style="float: left;">';
										defa += ' <span class="l-btn-text icon-ok" style="padding-left: 20px;">默认</span>';
										defa += ' </span>';
										defa += ' </a>';

										if (rec.status && rec.status == '1') {// 已为默认账户

										} else {
											return defa;
										}
									}
								} ] ],
						toolbar : [ {
							id : 'btnadd',
							text : '新增',
							iconCls : 'icon-coins_add',
							handler : function() {
								addRecordWin();
							}
						}, {
							id : 'btncut',
							text : '批量删除',
							iconCls : 'icon-coins_delete',
							handler : function() {
								batchDeleteRecord();
							}
						} ],
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
		columns : [ [ {
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
				return '<font color="blue">' + value + '</font>';
			}
		}, {
			field : 'visible',
			title : '是否可见',
			align : 'center',
			width : 150
		} ] ],
		pagination : true,
		rownumbers : true,
		onBeforeLoad : function(param) {
			param.query = refreshQuery2;
		}
	});
}

// ---------------------------------------------选择窗口1结束----------------------------------------------------

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
});

// ---------------------------------------------DOM渲染完毕，执行JavaScript初始化模块结束-------------------------

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

		}
	});

}

// ---------------------------------------------数据字典结束-----------------------------------------------------

// ---------------------------------------------列表组件开始-----------------------------------------------------

// ---------------------------------------------列表组件1开始----------------------------------------------------

var queryRecordUrl = '/supply/supply.action';
var queryRecordByIdUrl = '/receive/receive!input.action';

var saveRecordUrl2 = '/receive/receive!genPayable.action';

/** 手动刷新列表查询参数 */
function refreshQuery() {
	var query = '{';
	query = query + '\"EQS_status\":\"' + 1 + '\",';

	query = query + '\"LIKES_dealName\":\"%' + $('#key1').val() + '%\",';
	// query = query + '\"EQS_status\":\"' + $('#key2').combobox('getValue')
	// + '\",';
	// query = query + '\"GTN_unitPrice\":\"' + $('#key3').val() + '\",';
	query = query + '\"GED_submitDate\":\"'
			+ $('#key4').datetimebox("getValue") + '\",';
	query = query + '\"LED_submitDate\":\"'
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

	if ($('#key1').val().length > 0 || value2.length > 0 || value4.length > 0
			|| value5.length > 0) {
		refreshDatagrid();
	} else {
		showMsg('查询条件没有填写。');
	}

}

/** 重置列表记录查询 */
function queryReset() {
	$('#key1').val('');
	$('#key4').datetimebox('setValue', '');
	$('#key5').datetimebox('setValue', '');
	refreshDatagrid();
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

/** 查看收货单详情 */
function detail() {
	var record = $('#datagrid1').datagrid('getSelected');

	var temp = $('#form_datagrid1').json2form({
		data : record
	});

	$('#businessType').combobox('select', temp.businessType);
	$('#category').combobox('select', temp.category);
	$('#traderMode').combobox('select', temp.traderMode);
	$('#unit').combobox('select', temp.unit);
	$('#traderQq').combobox('select', temp.traderQq);

	$('#expirationDate').datetimebox('setValue',
			formatterDatetimebox(temp.expirationDate));

	$('#win_record').window({
		iconCls : 'icon-search',
		maximizable : false
	}).window('setTitle', '查看收货单详情').window('open');
}

/** 收货完成，填报实际收货数量 */
function fill(id) {
	$('#form_datagrid2 input[name="id"]').get(0).value = id;

	$('#win_select_game').window({
		iconCls : 'icon-page_edit',
		maximizable : false
	}).window('setTitle', '填报实际收货数量').window('open');
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

/** 初始化列表1 */
function initDataGrid1() {
	$('#datagrid1')
			.datagrid(
					{
						nowrap : false,
						striped : true,
						border : false,
						url : ctx + queryRecordUrl,
						pageSize : 20,
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
									field : 'serialNumber',
									title : '供货单号',
									align : 'center',
									width : 120,
									formatter : function(value, rec) {
										return '<font color="blue">' + value
												+ '</font>';
									}
								},
								{
									field : 'supplyName',
									title : '供货商',
									align : 'center',
									width : 80,
									formatter : function(value, rec) {
										return '<font color="blue">' + value
												+ '</font>';
									}
								},
								{
									field : 'temp2',
									title : '联系收货',
									align : 'center',
									width : 100,
									formatter : function(value, rec) {
										var q = '<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin='
												+ rec.traderQq
												+ '&site=qq&menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:'
												+ rec.traderQq
												+ ':41" alt="点击这里给我发消息" title="点击这里给我发消息"></a>';
										return q;
									}
								},
								{
									field : 'traderMobi',
									title : '联系手机',
									align : 'center',
									width : 110
								},
								// {
								// field : 'status',
								// title : '交易状态',
								// align : 'center',
								// width : 80,
								// formatter : function(value, rec) {
								// for ( var i = 0; i < GHDZT.length; i++) {
								// if (GHDZT[i].value == value) {
								// return GHDZT[i].name;
								// }
								// }
								// }
								// },
								{
									field : 'category',
									title : '收货类型',
									align : 'center',
									width : 60,
									formatter : function(value, rec) {
										for ( var i = 0; i < SPLX.length; i++) {
											if (SPLX[i].value == value) {
												return SPLX[i].name;
											}
										}
									}
								},
								{
									field : 'game',
									title : '游戏名',
									align : 'center',
									width : 90,
									formatter : function(value, rec) {
										return '<font color="blue">' + value
												+ '</font>';
									}
								},
								{
									field : 'area',
									title : '区名',
									align : 'center',
									width : 100
								},
								{
									field : 'server',
									title : '服务器名',
									align : 'center',
									width : 150
								},
								{
									field : 'quantity',
									title : '收货数量',
									align : 'center',
									width : 80,
									formatter : function(value, rec) {
										return '<font color="green">' + value ? value
												: '' + '</font>';
									}
								},
								// {
								// field : 'reality',
								// title : '实际数量',
								// align : 'center',
								// width : 80,
								// formatter : function(value, rec) {
								// return '<font color="blue">' + value ? value
								// : '' + '</font>';
								// }
								// },
								{
									field : 'unit',
									title : '单位',
									align : 'center',
									width : 50,
									formatter : function(value, rec) {
										for ( var i = 0; i < SPDW.length; i++) {
											if (SPDW[i].value == value) {
												return SPDW[i].name;
											}
										}
									}
								},
								// {
								// field : 'unitPrice',
								// title : '单价(元)',
								// align : 'center',
								// width : 60,
								// formatter : function(value, rec) {
								// return '<font color="red">' + value
								// + '</font>';
								// }
								// },
								// {
								// field : 'tmp1',
								// title : '总价(元)',
								// align : 'center',
								// width : 80,
								// formatter : function(value, rec) {
								// if (rec.reality) {
								// return '<font color="red">'
								// + (rec.reality * rec.unitPrice)
								// .toFixed(0)
								// + '</font>';
								// } else {
								// return '<font color="red">'
								// + (rec.quantity * rec.unitPrice)
								// .toFixed(0)
								// + '</font>';
								// }
								// }
								// },
								{
									field : 'dealName',
									title : '供货角色',
									align : 'center',
									width : 80
								},
								{
									field : 'submitDate',
									title : '供货单提交时间',
									align : 'center',
									width : 110,
									formatter : formatterDate
								},
								{
									field : 'remark',
									title : '备注',
									align : 'center',
									width : 120,
									formatter : function(value, rec) {
										return '<font color="green">' + value
												+ '</font>';
									}
								},

								{
									field : 'action',
									title : '操作',
									rowspan : 3,
									width : 190,
									formatter : function(value, rec) {
										var fill = '<a class="l-btn l-btn-plain" style="float:left;" href="javascript: fill(\''
												+ rec.id + '\');">';
										fill += ' <span class="l-btn-left" style="float: left;">';
										fill += ' <span class="l-btn-text icon-ok" style="padding-left: 20px;">确认收货</span>';
										fill += ' </span>';
										fill += ' </a>';
										if (rec.status == 1) {
											return fill;
										}
									}
								} ] ],
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

/** 初始化表单Ajax提交 */
function initForm2() {

	$('#form_datagrid2').form({
		url : ctx + saveRecordUrl2,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			closeRecordWin2();
			refreshDatagrid();
			showMsg('加油，此笔收货已经完成。');
		}
	});
}

/** 保存选择好的游戏 */
function saveRecord2() {
	$('#form_datagrid2').submit();
}

/** 关闭游戏选择窗口 */
function closeRecordWin2() {
	$('#win_select_game').form('clear');
	$('#win_select_game').window('close');
}

// ---------------------------------------------选择窗口1结束----------------------------------------------------

// ---------------------------------------------选择窗口结束-----------------------------------------------------

// ---------------------------------------------DOM渲染完毕，执行JavaScript初始化模块-----------------------------

$(function() {
	initDataDictionary();// 数据字典初始化

	initSearchField();
	initExpirationDate();
	initDataGrid1();

	initForm2();

});

// ---------------------------------------------DOM渲染完毕，执行JavaScript初始化模块结束-------------------------

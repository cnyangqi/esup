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

// ---------------------------------------------列表组件开始-----------------------------------------------------

// ---------------------------------------------列表组件1开始----------------------------------------------------

var queryRecordUrl = '/receive/receive.action';
var queryRecordByIdUrl = '/receive/receive!input.action';

var saveRecordUrl2 = '/supply/supply!save.action';

/** 手动刷新列表查询参数 */
function refreshQuery() {
	var query = '{';
	// 只查询审核过的记录
	query = query + '\"EQS_status\":\"' + 1 + '\",';
	// 不显示已经超过截止收货日期的记录
	query = query + '\"GED_expirationDate\":\"'
			+ formatterDatetimebox(new Date()) + '\",';
	query = query + '\"LIKES_game\":\"%' + $('#key1').val() + '%\",';// 模糊查询任意位置
	query = query + '\"LIKES_server\":\"' + $('#key2').val() + '\",';
	query = query + '\"GTN_unitPrice\":\"' + $('#key3').val() + '\",';
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

var DEMAND;// 剩余供货数量
/** 供货 */
function supply(id) {
	var record = $('#datagrid1').datagrid('getSelected');

	DEMAND = record.demand;

	$('#form_datagrid2 input[name="receiveId"]').get(0).value = record.id;
	$('#form_datagrid2 input[name="category"]').get(0).value = record.category;
	$('#form_datagrid2 input[name="game"]').get(0).value = record.game;
	$('#form_datagrid2 input[name="area"]').get(0).value = record.area;
	$('#form_datagrid2 input[name="server"]').get(0).value = record.server;
	$('#form_datagrid2 input[name="unitPrice"]').get(0).value = record.unitPrice;
	$('#form_datagrid2 input[name="unit"]').get(0).value = record.unit;
	$('#form_datagrid2 input[name="receiveQq"]').get(0).value = record.traderQq;

	$('#form_datagrid2 input[name="traderQq"]').get(0).value = qq;
	$('#form_datagrid2 input[name="traderMobi"]').get(0).value = mobi;

	$('#win_select_game').window({
		iconCls : 'icon-page_edit',
		maximizable : false
	}).window('setTitle', '供货单填报').window('open');
}

/** 初始化搜索字段 */
function initSearchField() {
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
						singleSelect : true,
						pageSize : 20,
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
									field : 'demand',
									title : '剩余收货量',
									align : 'center',
									width : 80,
									formatter : function(value, rec) {
										return '<font color="red">' + value
												+ '</font>';
									}
								},
								{
									field : 'minSupply',
									title : '收货量下限',
									align : 'center',
									width : 80
								},
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
								{
									field : 'unitPrice',
									title : '单价(元)',
									align : 'center',
									width : 60,
									formatter : function(value, rec) {
										return '<font color="red">' + value
												+ '</font>';
									}
								},
								{
									field : 'expirationDate',
									title : '截止日期',
									width : 110,
									formatter : formatterDate
								},
								{
									field : 'action',
									title : '操作',
									rowspan : 3,
									width : 190,
									formatter : function(value, rec) {
										var supply = '<a class="l-btn l-btn-plain" style="float:left;" href="javascript: supply(\''
												+ rec.id + '\');">';
										supply += ' <span class="l-btn-left" style="float: left;">';
										supply += ' <span class="l-btn-text icon-ok" style="padding-left: 20px;">供货</span>';
										supply += ' </span>';
										supply += ' </a>';
										return supply;
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
			if (DEMAND >= $('#quantity').val()) {// 付款金额不能大于未付总金额
				return $(this).form('validate');
			} else {
				showMsg('供货数量不能大于剩余收获量，请重新填写');
				$('#quantity').val('');
				return false;
			}
		},
		success : function(data) {
			closeRecordWin2();
			refreshDatagrid();
			showMsg('您的供货单已经提交成功，请到“出货管理”中联系交易QQ，或者等待收货员联系交易。');
		}
	});
}

/** 保存选择好的游戏 */
function saveRecord2() {
	$('#form_datagrid2').submit();
}

/** 关闭游戏选择窗口 */
function closeRecordWin2() {
	$('#form_datagrid2').form('clear');
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

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
					typeCodes : 'ghdzt,ywlx,splx,spdw,shlx,shfs'
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

var queryRecordUrl = '/payable/payable.action';
var queryRecordByIdUrl = '/payable/payable!input.action';

/** 手动刷新列表查询参数 */
function refreshQuery() {
	var query = '{';
	// query = query + '\"GTN_unpaid\":\"' + 0 + '\",';
	query = query + '\"LIKES_supplyName\":\"%' + $('#key1').val() + '%\",';
	query = query + '\"GED_genDate\":\"' + $('#key4').datetimebox("getValue")
			+ '\",';
	query = query + '\"LED_genDate\":\"' + $('#key5').datetimebox("getValue")
			+ '\"';
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

	if ($('#key1').val().length > 0 || value4.length > 0 || value5.length > 0) {
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

var UNPAID;// 未付总金额

/** 付款 */
function pay(id) {
	var record = $('#datagrid1').datagrid('getSelected');

	UNPAID = record.unpaid;

	$('#form_datagrid2 input[name="supplySerialNumber"]').get(0).value = record.supplySerialNumber;
	$('#form_datagrid2 input[name="payableId"]').get(0).value = record.id;
	$('#form_datagrid2 input[name="tmp1"]').get(0).value = record.accountBank;
	$('#form_datagrid2 input[name="tmp2"]').get(0).value = record.accountName;
	$('#form_datagrid2 input[name="tmp3"]').get(0).value = record.account;

	$('#win_select_game').window({
				iconCls : 'icon-coins_delete',
				maximizable : false
			}).window('setTitle', '付款').window('open');
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
	$('#datagrid1').datagrid({
		nowrap : false,
		striped : true,
		border : false,
		singleSelect : true,
		url : ctx + queryRecordUrl,
		sortName : '',
		sortOrder : 'ASC',// DESC 降序，ASC升序
		columns : [[{
					field : 'ck',
					title : '全选取消',
					checkbox : true,
					width : 80
				}, {
					field : 'supplySerialNumber',
					title : '供货单号',
					align : 'center',
					width : 120,
					formatter : function(value, rec) {
						return '<font color="blue">' + value + '</font>';
					}
				}, {
					field : 'supplyName',
					title : '供货商',
					align : 'center',
					width : 80
				}, {
					field : 'temp2',
					title : '联系QQ',
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
				}, {
					field : 'traderMobi',
					title : '联系手机',
					align : 'center',
					width : 80
				}, {
					field : 'category',
					title : '商品类型',
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
					field : 'reality',
					title : '实际供货数量',
					align : 'center',
					width : 80,
					formatter : function(value, rec) {
						return '<font color="blue">' + value ? value : ''
								+ '</font>';
					}
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
					field : 'totalMoney',
					title : '应付款(元)',
					align : 'center',
					width : 80
				}, {
					field : 'unpaid',
					title : '还欠款(元)',
					align : 'center',
					width : 80
				}, {
					field : 'genDate',
					title : '收货完成日期',
					align : 'center',
					width : 110,
					formatter : formatterDate
				},
				// {
				// field : 'remark',
				// title : '备注',
				// align : 'center',
				// width : 120,
				// formatter : function(value, rec) {
				// return '<font color="green">' + value
				// + '</font>';
				// }
				// },
				{
					field : 'action',
					title : '操作',
					rowspan : 3,
					width : 190,
					formatter : function(value, rec) {
						var pay = '<a class="l-btn l-btn-plain" style="float:left;" href="javascript: pay(\''
								+ rec.id + '\');">';
						pay += ' <span class="l-btn-left" style="float: left;">';
						pay += ' <span class="l-btn-text icon-ok" style="padding-left: 20px;">付款</span>';
						pay += ' </span>';
						pay += ' </a>';
						if (auditFlag && rec.unpaid != 0) {
							return pay;
						}
					}
				}]],
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
var saveRecordUrl2 = '/payrecords/payrecords!save.action';
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
					if (UNPAID >= $('#payMoney').val()) {// 付款金额不能大于未付总金额
						return $(this).form('validate');
					} else {
						showMsg('付款金额不能大于未付总金额，请重新填写');
						$('#payMoney').val('');
						return false;
					}
				},
				success : function(data) {
					closeRecordWin2();
					showMsg('付款成功。');
					refreshDatagrid();
				}
			});
}

/** 保存选择好的游戏 */
function saveRecord2() {
	$('#form_datagrid2').submit();
}

/** 关闭游戏选择窗口 */
function closeRecordWin2() {
	$('#datagrid2').form('clear');
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

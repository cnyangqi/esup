// ---------------------------------------------数据字典开始-----------------------------------------------------
var getComboxDataUrl = '/datadictionary/datadictionary!queryAllDataDictionaryByTypeCode.action';
var DXZT;// 用户账户状态Combobox数据

function initDataDictionary() {

	$.ajax({
		async : false,// required
		type : 'post',
		dataType : 'json',
		timeout : 10000,
		url : ctx + getComboxDataUrl,
		data : {
			typeCodes : 'dxzt'
		},
		success : function(data) {
			DXZT = data.dxzt;

			$('#key2').combobox({// 查询
				width : 110,
				valueField : 'value',
				textField : 'name'
			}).combobox('loadData', DXZT);

			$('#status').combobox({// 新增/修改
				width : 152,
				valueField : 'value',
				textField : 'name'
			}).combobox('loadData', DXZT);

		}
	});

}

// ---------------------------------------------数据字典结束-----------------------------------------------------

var queryObjectGridViewUrl = '/account/user.action';// 查询对象列表视图地址
var toggleObjectUrl = '/account/user!toggleUserStatus.action';// 切换对象状态地址
var deleteObjectUrl = '/account/user!delete.action';// 删除对象地址
var batchDeleteObjectUrl = '/account/user!batchDelete.action';// 批量删除对象地址
var checkLoginNameUrl = '/account/user!checkLoginName.action';// 检查登陆名是否重复地址
var saveObjectUrl = '/account/user!save.action';// 保存对象地址
var queryObjectByIdUrl = '/account/user!queryUserById.action';// 通过对象主键查询对象

/** 检查登陆名是否重复 */
function checkLoginName(loginName) {
	// post("/account/user!checkLoginName.action","loginName="+loginName);

	$.ajax({
		async : true,// required
		type : 'post',
		dataType : 'json',
		timeout : 10000,
		url : ctx + checkLoginNameUrl,
		data : {
			loginName : loginName
		},
		success : function(data) {
			if (data.result == 'false') {
				showMsg('用户名已存在，请重新输入');
				$('#loginName').val('');
			}
		}
	});

}

/** 手动刷新查询参数 */
function refresh_query() {
	// query['LIKES_typeName'] = $('#dd_type').val();

	var status = $('#key2').combobox('getValue');

	var query = '{';
	query = query + '\"LIKES_loginName\":\"' + $('#key1').val() + '\",';
	query = query + '\"EQS_status\":\"' + status + '\"';
	query += '}';
	return query;
}

/** 查询重置 */
function query_reset() {
	$('#key1').val('');
	$('#key2').combobox('setValue', '');
	$('#tt').datagrid('reload');
}

/** 手动刷新列表 */
function refresh_datagrid() {
	$('#tt').datagrid('reload');
}

/** 对象查询 */
function query_object() {
	var value = $('#key2').combobox('getValue');

	if ($('#key1').val().length > 0 || value.length > 0) {
		refresh_datagrid();
	} else {
		showMsg('查询条件没有填写。');
	}
}

/** 停用对象 */
function stop(id) {
	if (id) {
		$.ajax({
			async : true,// required
			type : 'post',
			dataType : 'json',
			timeout : 10000,
			url : ctx + toggleObjectUrl,
			data : {
				id : id
			},
			success : function(data) {
				refresh_datagrid();
				showMsg('停用对象状态成功。');
			}
		});
	}
}

/** 启用对象 */
function open(id) {
	if (id) {
		$.ajax({
			async : true,// required
			type : 'post',
			dataType : 'json',
			timeout : 10000,
			url : ctx + toggleObjectUrl,
			data : {
				id : id
			},
			success : function(data) {
				refresh_datagrid();
				showMsg('启用对象状态成功。');
			}
		});
	}
}

/** 删除对象 */
function del(id) {
	if (id) {
		$.ajax({
			async : true,// required
			type : 'post',
			dataType : 'json',
			timeout : 10000,
			url : ctx + deleteObjectUrl,
			data : {
				id : id
			},
			success : function(data) {
				refresh_datagrid();
				showMsg('删除数据成功。');
			}
		});
	}
}

/** 批量删除对象 */
function batch_del(ids) {
	var selects = $('#tt').datagrid('getSelections');
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
			url : ctx + batchDeleteObjectUrl,
			data : {
				ids : tmp.join(',')
			},
			success : function(data) {
				refresh_datagrid();
				showMsg('删除数据成功。');
			}
		});
	} else {
		showMsg('请选择您要删除的数据。');
	}
}

/** 新增对象 */
function add_win() {
	$('#form_user').form('clear');

	$('#status').combobox('select', '1');// 后台新增对象默认启用

	$('#win_user').window({
		iconCls : 'icon-user_add',
		maximizable : false
	}).window('setTitle', '新增用户').window('open');
}

/** 保存对象 */
function save() {
	$('#form_user').submit();
}

/** 修改对象 */
function edit(id) {
	$('#id').val('');
	$('#loginName').attr('readonly', 'readonly');

	var temp = $('#form_user').json2form({
		url : ctx + queryObjectByIdUrl,
		data : {
			'id' : id
		}
	});

	$('#status').combobox('select', temp.status);

	$('#win_user').window({
		iconCls : 'icon-user_edit',
		maximizable : false
	}).window('setTitle', '修改用户').window('open');

}

/** 关闭对象窗口 */
function cancel() {
	$('#win_user').window('close');
}

$(function() {

	initDataDictionary();// 数据字典初始化

	$('#win_user').window({
		'onClose' : function() {
			$(this).form('clear');
			$('#loginName').removeAttr('readonly');
		}
	});

	$('#form_user').form({
		url : ctx + saveObjectUrl,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			cancel();
			refresh_datagrid();
		}
	});

	$('#tt')
			.datagrid(
					{
						nowrap : false,
						striped : true,
						border : false,
						url : ctx + queryObjectGridViewUrl,
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
									field : 'loginName',
									title : '登陆账户',
									width : 100
								},
								{
									field : 'name',
									title : '用户姓名',
									width : 150
								},
								{
									field : 'nickname',
									title : '用户昵称',
									width : 150
								},
								{
									field : 'mobi',
									title : '手机号码',
									width : 100
								},
								{
									field : 'tel',
									title : '电话号码',
									width : 100
								},
								{
									field : 'qq',
									title : 'QQ号码',
									width : 100
								},
								{
									field : 'email',
									title : '电子邮件',
									width : 100
								},
								{
									field : 'status',
									title : '账户状态',
									width : 100,
									formatter : function(value, rec) {
										for ( var i = 0; i < DXZT.length; i++) {
											if (DXZT[i].value == value) {
												return DXZT[i].name;
											}
										}
									}
								},
								{
									field : 'action',
									title : '操作',
									rowspan : 3,
									width : 190,
									formatter : function(value, rec) {
										var open = '<a class="l-btn l-btn-plain" style="float:left;" href="javascript: open(\''
												+ rec.id + '\');">';
										open += ' <span class="l-btn-left" style="float: left;">';
										open += ' <span class="l-btn-text icon-ok" style="padding-left: 20px;">启用</span>';
										open += ' </span>';
										open += ' </a>';
										var stop = '<a class="l-btn l-btn-plain" style="float:left;" href="javascript: stop(\''
												+ rec.id + '\');">';
										stop += ' <span class="l-btn-left" style="float: left;">';
										stop += ' <span class="l-btn-text icon-cross" style="padding-left: 20px;">停用</span>';
										stop += ' </span>';
										stop += ' </a>';
										var edit = '<a class="l-btn l-btn-plain" style="float:left;" href="javascript: edit(\''
												+ rec.id + '\');">';
										edit += ' <span class="l-btn-left" style="float: left;">';
										edit += ' <span class="l-btn-text icon-user_edit" style="padding-left: 20px;">修改</span>';
										edit += ' </span>';
										edit += ' </a>';
										var del = '<a class="l-btn l-btn-plain" style="float:left;" href="javascript: del(\''
												+ rec.id + '\');">';
										del += ' <span class="l-btn-left" style="float: left;">';
										del += ' <span class="l-btn-text icon-user_delete" style="padding-left: 20px;">删除</span>';
										del += ' </span>';
										del += ' </a>';

										if (rec.status && rec.status == '1') {
											return stop + edit + del;
										} else {
											return open + edit + del;
										}
									}
								} ] ],
						toolbar : [ {
							id : 'btnadd',
							text : '新增',
							iconCls : 'icon-user_add',
							handler : function() {
								add_win();
							}
						}, {
							id : 'btncut',
							text : '批量删除',
							iconCls : 'icon-user_delete',
							handler : function() {
								batch_del();
							}
						} ],
						pagination : true,
						rownumbers : true,
						onBeforeLoad : function(param) {
							param.query = refresh_query;
						}
					});

});
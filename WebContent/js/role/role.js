// ---------------------------------------------数据字典开始-----------------------------------------------------
var getComboxDataUrl = '/datadictionary/datadictionary!queryAllDataDictionaryByTypeCode.action';

var DXZT;// 对象状态

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

					$('#key2').combobox({// 业务类型
						width : 110,
						valueField : 'value',
						textField : 'name'
					}).combobox('loadData', DXZT);

				}
			});

}

// ---------------------------------------------数据字典结束-----------------------------------------------------

var getRoleComboxDataUrl = '/account/role!queryRoleCombobox.action';// 获取系统角色combobox

var queryObejctGridViewUrl = '/account/role!roleList.action';// 查询对象列表视图地址
var saveObjectGridUrl = '/account/role!saveUserRole.action';// 保存列表视图对象地址
var deleteObjectGridUrl = '/account/role!deleteUserRole.action';// 删除对象列表视图地址

var queryUserGridViewUrl = '/account/user.action';// 查询用户列表视图地址

// ** 用户选择窗口专区开始 *//

/** 手动刷新查询参数_用户选择 */

function refresh_query_yhxz() {
	// query['LIKES_typeName'] = $('#dd_type').val();

	var status = $('#key2').combobox('getValue');

	var query = '{';
	query = query + '\"LIKES_loginName\":\"' + $('#key1').val() + '\",';
	query = query + '\"EQS_status\":\"' + status + '\"';
	query += '}';
	return query;
}

/** 对象查询 */
function query_object_yhxz() {
	var value = $('#key2').combobox('getValue');

	if ($('#key1').val().length > 0 || value.length > 0) {
		$('#tt2').datagrid('reload');
	} else {
		showMsg('查询条件没有填写。');
	}
}

/** 查询重置 */
function query_reset_yhxz() {
	$('#key1').val('');
	$('#key2').combobox('setValue', '');
	$('#tt2').datagrid('reload');
}

// **用户选择窗口专区结束*//

/** 记录节点信息 */
function recordNode($tree) {
	selected_node = $tree.tree('getSelected');
}

/** 手动刷新列表 */
function refresh_datagrid() {
	$('#tt').datagrid('reload');
}

/** 手动刷新查询参数 */
function refresh_query() {
	// query['LIKES_typeName'] = $('#dd_type').val();

	var query = '{';
	query = query + '\"`user`.login_name\":\"' + $('#login_name').val() + '\",';
	query = query + '\"role.`name`\":\"' + $('#role_name').val() + '\"';
	query += '}';
	return query;
}

/** 打开新增角色窗口 */
function add_win_ddt() {

	$('#win_ddt').window({
				iconCls : 'icon-key_add',
				maximizable : false
			}).window('setTitle', '新增角色').window('open');

}

/** 打开新增用户窗口 */
function add_win_dd() {

	$('#win_dd').window({
				iconCls : 'icon-key_add',
				maximizable : false
			}).window('setTitle', '新增用户角色').window('open');

}

/** 重置角色管理窗口 */
function reset_win_ddt() {
	$('#form_ddt').form('clear');
	$('#form_ddt input[name="id"]').get(0).value = '';
	$('#form_ddt input[name="sequNum"]').get(0).value = '';
}

/** 关闭角色管理窗口 */
function close_win_ddt() {
	reset_win_ddt();
	$('#win_ddt').window('close');
}

/** 重置用户角色管理窗口 */
function reset_win_dd() {
	$('#form_dd').form('clear');
	$('#form_dd input[name="id"]').get(0).value = '';
	$('#form_dd input[name="userId"]').get(0).value = '';
	$('#form_dd input[name="roleId"]').get(0).value = '';
	$('#form_dd input[name="loginName"]').get(0).value = '';
}

/** 关闭用户角色管理窗口 */
function close_win_dd() {
	reset_win_dd();
	$('#win_dd').window('close');
}

/** 保存数据字典到数据库 */
function save_dd() {
	$('#form_dd').submit();
}

/** 保存选择的用户 */
function save_select_ddt() {
	var node = $('#tt2').datagrid('getSelected');
	$('#form_dd input[name="userId"]').get(0).value = node.id;
	$('#form_dd input[name="loginName"]').get(0).value = node.loginName;
	close_win_select_ddt();
}

/** 关闭选择用户窗口 */
function close_win_select_ddt() {
	$('#win_select_user').window('close');
}

/** 保存选择的所属角色 */
function save_select_dd() {
	var node = $('#tree_select_dd').tree('getSelected');
	selected_node = node;
	$('#form_dd input[name="roleId"]').get(0).value = node.id;
	$('#form_dd input[name="roleName"]').get(0).value = node.text;
	close_win_select_dd();
}

/** 关闭选择数据字典类型窗口 */
function close_win_select_dd() {
	$('#win_select_dd').window('close');
}

/** 数据字典查询重置 */
function query_reset() {
	$('#role_name').val('');
	$('#login_name').val('');
	$('#tt').datagrid('reload');
}

/** 权限查询 */
function query_data_dictionary() {
	if ($('#role_name').val().length > 0 || $('#login_name').val().length > 0) {
		refresh_datagrid();
	} else {
		showMsg('查询条件没有填写。');
	}

	// $.ajax({
	// async : true,// required
	// type : 'post',
	// dataType : 'json',
	// timeout : 10000,
	// url : ctx + toggleDataDictionaryTpyeUrl,
	// data : {
	// id : id
	// },
	// success : function(data) {
	// refresh_datagrid();
	// showMsg('启用数据字典状态成功。');
	// }
	// });
}

/** 用户权限删除 */
function del(ids) {

	if (ids) {
		$.ajax({
					async : true,// required
					type : 'post',
					dataType : 'json',
					timeout : 10000,
					url : ctx + deleteObjectGridUrl,
					data : {
						ids : ids
						// ids=userId,roleId
					},
					success : function(data) {
						refresh_datagrid();
						showMsg('删除成功。');
					}
				});
	}

}

/** 程序初始化 */
$(function() {

	// 分配角色
	$('#roleId').combobox({
				width : 152,
				url : ctx + getRoleComboxDataUrl,
				valueField : 'value',
				textField : 'name',
				onSelect : function(record) {
					$(this).val(record.value);
				}
			});

	// 弹出选择父级数据字典类型窗口
	$('#select_parent').click(function() {

				$('#tree_select_ddt').tree({
							url : ctx + queryObjectTreeViewUrl,
							onClick : function(node) {
								$(this).tree('toggle', node.target);
							}
						});

				$('#win_select_ddt').window({
							iconCls : 'icon-dd',
							maximizable : false
						}).window('setTitle', '选择父级数据字典类型').window('open');
			});

	// 弹出选择用户窗口
	$('#select_type').click(function() {
		
				initDataDictionary();

				$('#tt2').datagrid({
							nowrap : false,
							striped : true,
							border : false,
							singleSelect : true,
							url : ctx + queryUserGridViewUrl,
							// queryParams : {},
							sortName : '',
							sortOrder : 'ASC',// DESC 降序，ASC升序
							columns : [[{
										field : 'ck',
										title : '全选取消',
										checkbox : true,
										width : 80
									}, {
										field : 'loginName',
										title : '登陆账户',
										width : 100
									}, {
										field : 'name',
										title : '用户姓名',
										width : 100
									}, {
										field : 'nickname',
										title : '用户昵称',
										width : 100
									}, {
										field : 'status',
										title : '用户状态',
										width : 100,
										formatter : function(value, rec) {
											// return "<a
											// title='"+s+"'>"+s+"</a>";
											if (value == '1') {
												return '<font color="green">启用</font>';
											} else {
												return '<font color="red">停用</font>';
											}
										}
									}]],
							pagination : true,
							rownumbers : true,
							onBeforeLoad : function(param) {
								param.query = refresh_query_yhxz;
							}
						});

				$('#win_select_user').window({
							iconCls : 'icon-user',
							maximizable : false
						}).window('setTitle', '选择用户').window('open');
			});

	// 数据字典表单异步提交
	$('#form_dd').form({
				url : ctx + saveObjectGridUrl,
				onSubmit : function() {
					return $(this).form('validate');
				},
				success : function(data) {
					close_win_dd();
					refresh_datagrid();
				}
			});

	$('#tt').datagrid({
		nowrap : false,
		striped : true,
		border : false,
		singleSelect : true,
		url : ctx + queryObejctGridViewUrl,
		// queryParams : {},
		sortName : '',
		sortOrder : 'ASC',// DESC 降序，ASC升序
		columns : [[{
					field : 'ck',
					title : '全选取消',
					checkbox : true,
					width : 80
				},
				// {
				// field : 'productid',
				// title : '序号',
				// width : 100,
				// align : 'right'
				// },
				{
					field : 'loginName',
					title : '用户账户',
					width : 150
				}, {
					field : 'roleName',
					title : '角色名称',
					width : 150
				}, {
					field : 'action',
					title : '操作',
					rowspan : 3,
					width : 190,
					formatter : function(value, rec) {
						var del = '<a class="l-btn l-btn-plain" style="float:left;" href="javascript: del(\''
								+ rec.userId + ',' + rec.roleId + '\');">';
						del += ' <span class="l-btn-left" style="float: left;">';
						del += ' <span class="l-btn-text icon-key_delete" style="padding-left: 20px;">删除</span>';
						del += ' </span>';
						del += ' </a>';

						return del;
					}
				}]],
		toolbar : [{
					id : 'btnadd',
					text : '新增',
					iconCls : 'icon-role_add',
					handler : function() {
						add_win_dd();
					}
				}],
		pagination : true,
		rownumbers : true,
		onBeforeLoad : function(param) {
			param.query = refresh_query;
		}
	});

});

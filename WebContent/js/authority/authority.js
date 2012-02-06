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

					$('#status').combobox({// 业务类型
						width : 152,
						valueField : 'value',
						textField : 'name'
					}).combobox('loadData', DXZT);

				}
			});

}

// ---------------------------------------------数据字典结束-----------------------------------------------------

var queryObjectTreeViewUrl = '/account/role.action';// 查询对象树视图地址
var saveObjectTreeUrl = '/account/role!save.action';// 保存树视图对象地址
var deleteObjectTreeUrl = '/account/role!delete.action';// 删除树视图对象地址
var queryObjectTreeByIdUrl = '/account/role!input.action';// 通过主键查询树视图对象地址

var queryObejctGridViewUrl = '/account/role!authList.action';// 查询对象列表视图地址
var saveObjectGridUrl = '/account/role!saveAuth.action';// 保存列表视图对象地址
var batchDeleteObjectGridUrl = '/account/role!batchDeleteAuth.action';// 批量删除列表视图对象地址
var toggleObjectGridUrl = '/account/role!toggleAuthStatus.action';// 切换列表视图对象地址

var selected_node;// 当前树操作节点
var parent_node;// 父亲节点
var grandfather_node;// 祖父节点

/** 记录节点信息 */
function recordNode($tree) {
	selected_node = $tree.tree('getSelected');
}

/** 手动刷新列表 */
function refresh_datagrid() {

	if (selected_node) {
		$('#tt').datagrid('reload', {
					id : selected_node.id
				});
	} else {
		$('#tt').datagrid('reload');
	}

}

/** 手动刷新查询参数 */
function refresh_query() {
	// query['LIKES_typeName'] = $('#dd_type').val();

	var query = '{';
	query = query + '\"t.role_name\":\"' + $('#role_name').val() + '\",';
	query = query + '\"t.`name`\":\"' + $('#auth_name').val() + '\"';
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

/** 打开新增权限窗口 */
function add_win_dd() {

	if (selected_node) {// 检查新增数据字典前是否有预订（选择）过父节点
		$('#form_dd input[name="roleName"]').get(0).value = selected_node.text;
		$('#form_dd input[name="roleId"]').get(0).value = selected_node.id;
	}

	$('#status').combobox('select', '1');

	$('#win_dd').window({
				iconCls : 'icon-key_add',
				maximizable : false
			}).window('setTitle', '新增权限').window('open');

}

/** 批量删除权限 */
function batch_delete_dd() {
	var selects = $('#tt').datagrid('getSelections');

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
					url : ctx + batchDeleteObjectGridUrl,
					data : {
						ids : tmp.join(',')
					},
					success : function(data) {
						refresh_datagrid();
						showMsg('删除权限成功。');
					}
				});
	} else {
		showMsg('请选择您要删除的权限。');
	}
}

/** 删除数据字典 */
function del_dd(id) {
	if (id) {
		$.ajax({
					async : true,// required
					type : 'post',
					dataType : 'json',
					timeout : 10000,
					url : ctx + deleteDataDictionaryUrl,
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

/** 停用权限 */
function stop_dd(id) {
	if (id) {
		$.ajax({
					async : true,// required
					type : 'post',
					dataType : 'json',
					timeout : 10000,
					url : ctx + toggleObjectGridUrl,
					data : {
						id : id
					},
					success : function(data) {
						refresh_datagrid();
						showMsg('停用权限成功。');
					}
				});
	}
}

/** 启用数据字典 */
function open_dd(id) {
	if (id) {
		$.ajax({
					async : true,// required
					type : 'post',
					dataType : 'json',
					timeout : 10000,
					url : ctx + toggleObjectGridUrl,
					data : {
						id : id
					},
					success : function(data) {
						refresh_datagrid();
						showMsg('启用权限成功。');
					}
				});
	}
}

/** 修改数据字典 */
function edit_dd(id) {
	$('#form_dd').json2form({
				url : ctx + queryDataDictionaryByIdUrl,
				data : {
					'id' : id
				}
			});

	$('#win_dd').window({
				iconCls : 'icon-dd_edit',
				maximizable : false
			}).window('setTitle', '修改数据字典').window('open');
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

/** 重置权限管理窗口 */
function reset_win_dd() {
	$('#form_dd').form('clear');
	$('#form_dd input[name="id"]').get(0).value = '';
	$('#form_dd input[name="roleId"]').get(0).value = '';
	$('#form_dd input[name="roleName"]').get(0).value = '';
	$('#form_dd input[name="status"]').get(0).value = '';
}

/** 关闭权限管理窗口 */
function close_win_dd() {
	reset_win_dd();
	$('#win_dd').window('close');
}

/** 保存数据字典类型到数据库 */
function save_ddt() {

	// -- 紧急备用代码，切勿删除。 -- //
	// if ($('#form_ddt').form('validate')) {
	//
	// $.ajax({
	// async : true,// required
	// type : 'post',
	// dataType : 'json',
	// timeout : 10000,
	// url : ctx + saveDataDictionaryTypeUrl,
	// data : $('#form_ddt').serializeObject(),
	// success : function(data) {
	// close_win_ddt();
	//
	// if (selected_node) {
	// if (parent_node) {// 如果父亲节点存在，则新增节点类型为普通子节点
	// $('#tree_ddt').tree('reload',
	// parent_node.target);
	// return;
	// }
	// }
	// $('#tree_ddt').tree('reload');// 刷新树根节点
	// }
	// });
	//
	// }

	$('#form_ddt').submit();
}

/** 保存数据字典到数据库 */
function save_dd() {
	$('#form_dd').submit();
}

/** 删除角色 */
function delete_ddt() {

	// 删除判断
	if (selected_node) {

		$.ajax({
					async : true,// required
					type : 'post',
					dataType : 'json',
					timeout : 10000,
					url : ctx + deleteObjectTreeUrl,
					data : {
						'id' : selected_node.id
					},
					success : function(data) {
						if (data.result == 'true') {
							showMsg('删除角色成功。');
							$('#tree_ddt').tree('reload');
						}
					}
				});

	} else {
		showMsg('请选择您要删除的角色');
	}
}

/** 修改角色 */
function edit_ddt() {

	if (selected_node) {

		$('#form_ddt').json2form({
					url : ctx + queryObjectTreeByIdUrl,
					data : {
						'id' : selected_node.id
					}
				});

		$('#win_ddt').window({
					iconCls : 'icon-key_go',
					maximizable : false
				}).window('setTitle', '修改角色').window('open');

	} else {
		showMsg('请选择您要修改的角色');
	}
}

/** 保存选择的父级数据字典类型 */
function save_select_ddt() {
	var node = $('#tree_select_ddt').tree('getSelected');
	$('#form_ddt input[name="parentId"]').get(0).value = node.id;
	$('#form_ddt input[name="parentName"]').get(0).value = node.text;
	close_win_select_ddt();
}

/** 关闭选择父级数据字典类型窗口 */
function close_win_select_ddt() {
	$('#win_select_ddt').window('close');
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
	$('#auth_name').val('');
	$('#tt').datagrid('reload');
}

/** 权限查询 */
function query_data_dictionary() {
	if ($('#role_name').val().length > 0 || $('#auth_name').val().length > 0) {
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

/** 程序初始化 */
$(function() {

	initDataDictionary();

	$('#status').combobox('select', '1');

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

	// 弹出选择所属角色窗口
	$('#select_type').click(function() {

				$('#tree_select_dd').tree({
							url : ctx + queryObjectTreeViewUrl,
							onClick : function(node) {
								$(this).tree('toggle', node.target);
							}
						});

				$('#win_select_dd').window({
							iconCls : 'icon-key',
							maximizable : false
						}).window('setTitle', '选择所属角色').window('open');
			});

	// 数据字典类型树
	$('#tree_ddt').tree({
				url : ctx + queryObjectTreeViewUrl,
				onClick : function(node) {// 配置树节点单击开关函数，同时记录当前操作节点以及其父亲节点、祖父节点
					recordNode($(this));
					refresh_datagrid();
				},
				onLoadSuccess : function() {// 当树reload完毕，恢复操作节点的选择状态，并更新操作节点等状态
				}
			});

	// 数据字典类型表单异步提交
	$('#form_ddt').form({
				url : ctx + saveObjectTreeUrl,
				onSubmit : function() {
					return $(this).form('validate');
				},
				success : function(data) {
					close_win_ddt();

					$('#tree_ddt').tree('reload');// 刷新树根节点
				}
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
					field : 'name',
					title : '权限名称',
					width : 160
				}, {
					field : 'prefixedName',
					title : '权限值',
					width : 160
				}, {
					field : 'roleName',
					title : '所属角色',
					width : 160
				}, {
					field : 'status',
					title : '权限状态',
					width : 100,
					formatter : function(value, rec) {
						for (var i = 0; i < DXZT.length; i++) {
							if (DXZT[i].value == value) {
								return DXZT[i].name;
							}
						}
					}
				}, {
					field : 'action',
					title : '操作',
					rowspan : 3,
					width : 190,
					formatter : function(value, rec) {
						var open = '<a class="l-btn l-btn-plain" style="float:left;" href="javascript: open_dd(\''
								+ rec.id + '\');">';
						open += ' <span class="l-btn-left" style="float: left;">';
						open += ' <span class="l-btn-text icon-ok" style="padding-left: 20px;">启用</span>';
						open += ' </span>';
						open += ' </a>';
						var stop = '<a class="l-btn l-btn-plain" style="float:left;" href="javascript: stop_dd(\''
								+ rec.id + '\');">';
						stop += ' <span class="l-btn-left" style="float: left;">';
						stop += ' <span class="l-btn-text icon-cross" style="padding-left: 20px;">停用</span>';
						stop += ' </span>';
						stop += ' </a>';
						var edit = '<a class="l-btn l-btn-plain" style="float:left;" href="javascript: edit_dd(\''
								+ rec.id + '\');">';
						edit += ' <span class="l-btn-left" style="float: left;">';
						edit += ' <span class="l-btn-text icon-dd_edit" style="padding-left: 20px;">修改</span>';
						edit += ' </span>';
						edit += ' </a>';
						var del = '<a class="l-btn l-btn-plain" style="float:left;" href="javascript: del_dd(\''
								+ rec.id + '\');">';
						del += ' <span class="l-btn-left" style="float: left;">';
						del += ' <span class="l-btn-text icon-dd_delete" style="padding-left: 20px;">删除</span>';
						del += ' </span>';
						del += ' </a>';

						if (rec.status && rec.status == '1') {
							// return stop + edit + del;
							return stop;
						} else {
							// return open + edit + del;
							return open;
						}
					}
				}]],
		toolbar : [{
					id : 'btnadd',
					text : '新增',
					iconCls : 'icon-key_add',
					handler : function() {
						add_win_dd();
					}
				}, {
					id : 'btncut',
					text : '批量删除',
					iconCls : 'icon-key_delete',
					handler : function() {
						batch_delete_dd();
					}
				}],
		pagination : true,
		rownumbers : true,
		onBeforeLoad : function(param) {
			param.query = refresh_query;
		}
	});

});
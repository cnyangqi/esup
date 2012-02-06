var queryDataDictionaryTypeTreeViewUrl = '/datadictionary/datadictionarytype.action';// 查询数据字典类型树形视图地址
var saveDataDictionaryTypeUrl = '/datadictionary/datadictionarytype!save.action';// 保存数据字典类型地址
var deleteDataDictionaryTpyeUrl = '/datadictionary/datadictionarytype!delete.action';// 删除数据字典类型地址
var queryDataDictionaryTypeByIdUrl = '/datadictionary/datadictionarytype!input.action';// 通过数据字典类型主键查询数据字典类型地址

var queryDataDictionaryGridViewUrl = '/datadictionary/datadictionary.action';// 查询数据字典列表视图地址
var saveDataDictionaryUrl = '/datadictionary/datadictionary!save.action';// 保存数据字典地址
var batchDeleteDataDictionaryUrl = '/datadictionary/datadictionary!batchDelete.action';// 批量删除数据字典地址
var deleteDataDictionaryUrl = '/datadictionary/datadictionary!delete.action';// 批量删除数据字典地址
var toggleDataDictionaryUrl = '/datadictionary/datadictionary!toggleDataDictionaryStatus.action';// 切换数据字典状态地址
var queryDataDictionaryByIdUrl = '/datadictionary/datadictionary!input.action';// 通过数据字典主键查询数据字典地址

var selected_node;// 操作节点
var parent_node;// 父亲节点
var grandfather_node;// 祖父节点

/** 记录节点信息 */
function recordNode($tree) {
	selected_node = $tree.tree('getSelected');
	if (selected_node) {
		parent_node = $tree.tree('getParent', selected_node.target);
	}
	if (parent_node) {
		grandfather_node = $tree.tree('getParent', parent_node.target);
	}
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
	query = query + '\"LIKES_typeName\":\"' + $('#dd_type').val() + '\",';
	query = query + '\"LIKES_name\":\"' + $('#dd_name').val() + '\"';
	query += '}';
	return query;
}

/** 打开新增数据字典类型窗口 */
function add_win_ddt() {

	if (selected_node) {// 检查新增数据字典类型前是否有预订（选择）过父节点
		$('#form_ddt input[name="parentName"]').get(0).value = selected_node.text;
		$('#form_ddt input[name="parentId"]').get(0).value = selected_node.id;
	}

	$('#win_ddt').window({
		iconCls : 'icon-dd_add',
		maximizable : false
	}).window('setTitle', '新增数据字典类型').window('open');

}

/** 打开新增数据字典窗口 */
function add_win_dd() {

	if (selected_node) {// 检查新增数据字典前是否有预订（选择）过父节点
		$('#form_dd input[name="typeName"]').get(0).value = selected_node.text;
		$('#form_dd input[name="typeId"]').get(0).value = selected_node.id;
	}

	$('#win_dd').window({
		iconCls : 'icon-dd_add',
		maximizable : false
	}).window('setTitle', '新增数据字典').window('open');

}

/** 批量删除数据字典 */
function batch_delete_dd() {
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
			url : ctx + batchDeleteDataDictionaryUrl,
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

/** 停用数据字典 */
function stop_dd(id) {
	if (id) {
		$.ajax({
			async : true,// required
			type : 'post',
			dataType : 'json',
			timeout : 10000,
			url : ctx + toggleDataDictionaryUrl,
			data : {
				id : id
			},
			success : function(data) {
				refresh_datagrid();
				showMsg('停用数据字典状态成功。');
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
			url : ctx + toggleDataDictionaryUrl,
			data : {
				id : id
			},
			success : function(data) {
				refresh_datagrid();
				showMsg('启用数据字典状态成功。');
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

/** 重置数据字典类型管理窗口 */
function reset_win_ddt() {
	$('#form_ddt').form('clear');
	$('#form_ddt input[name="id"]').get(0).value = '';
	$('#form_ddt input[name="parentId"]').get(0).value = '';
	$('#form_ddt input[name="parentName"]').get(0).value = '';
	$('#form_ddt input[name="sequNum"]').get(0).value = '';

	$('#only_new').show();
}

/** 关闭数据字典类型管理窗口 */
function close_win_ddt() {
	reset_win_ddt();
	$('#win_ddt').window('close');
}

/** 重置数据字典管理窗口 */
function reset_win_dd() {
	$('#form_dd').form('clear');
	$('#form_dd input[name="id"]').get(0).value = '';
	$('#form_dd input[name="typeId"]').get(0).value = '';
	$('#form_dd input[name="typeName"]').get(0).value = '';
	$('#form_dd input[name="value"]').get(0).value = '';
	$('#form_dd input[name="sequNum"]').get(0).value = '';
}

/** 关闭数据字典管理窗口 */
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

/** 删除数据字典类型 */
function delete_ddt() {

	// 删除操作 简单传id 后台进行复杂逻辑判断
	function run_del(b) {
		if (b) {
			$
					.ajax({
						async : true,// required
						type : 'post',
						dataType : 'json',
						timeout : 10000,
						url : ctx + deleteDataDictionaryTpyeUrl,
						data : {
							'id' : selected_node.id
						},
						success : function(data) {

							if (grandfather_node) {// 如果存在祖父节点，则父亲节点不是树的根节点
								if (parent_node.attributes.subTypeNum == 1) {// 父亲节点下属节点只有一个，即删除操作节点
									$('#tree_ddt').tree('reload',
											grandfather_node.target);

									selected_node = grandfather_node;// 刷新完成，操作节点指向祖父节点

								} else {
									parent_node.attributes.subTypeNum--;// 手动刷新树节点状态
									$('#tree_ddt')
											.tree(
													'update',
													{
														target : parent_node.target,
														'attributes["subTypeNum"]' : parent_node.attributes.subTypeNum
													});

									$('#tree_ddt').tree('reload',
											parent_node.target);

									selected_node = parent_node;// 刷新完成，操作节点指向父亲节点

								}
							} else {// 删除根节点或其直接下属节点
								$('#tree_ddt').tree('reload');
								if (parent_node) {// 如果被删除节点为根节点下属节点，则将操作节点指向父亲节点
									selected_node = parent_node;
								} else {
									selected_node = false;// 操作节点已经被删除
								}
							}

						}
					});
		} else {
			return;
		}
	}

	// 删除判断
	if (selected_node) {

		if (selected_node.attributes.subTypeNum > 0) {// 如果要删除的树节点有子节点

			$.messager.confirm('批量删除确认', '该树节点下面还有子节点，您确认要一起删除吗？', function(b) {
				run_del(b);
			});

		} else {// 操作节点无下属节点的时候，无需提示直接删除
			run_del(true);
		}

	} else {
		showMsg('请选择您要删除的数据字典类型');
	}
}

/** 修改数据字典类型 */
function edit_ddt() {
	$('#only_new').hide();// 暂不支持修改父级类型

	if (selected_node) {

		$('#form_ddt').json2form({
			url : ctx + queryDataDictionaryTypeByIdUrl,
			data : {
				'id' : selected_node.id
			}
		});

		$('#win_ddt').window({
			iconCls : 'icon-dd_edit',
			maximizable : false
		}).window('setTitle', '修改数据字典类型').window('open');

	} else {
		showMsg('请选择您要修改的数据字典类型');
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

/** 保存选择的数据字典类型 */
function save_select_dd() {
	var node = $('#tree_select_dd').tree('getSelected');
	selected_node = node;
	$('#form_dd input[name="typeId"]').get(0).value = node.id;
	$('#form_dd input[name="typeName"]').get(0).value = node.text;
	close_win_select_dd();
}

/** 关闭选择数据字典类型窗口 */
function close_win_select_dd() {
	$('#win_select_dd').window('close');
}

/** 数据字典查询重置 */
function query_reset() {
	$('#dd_type').val('');
	$('#dd_name').val('');
	$('#tt').datagrid('reload');
}

/** 数据字典查询 */
function query_data_dictionary() {
	if ($('#dd_type').val().length > 0 || $('#dd_name').val().length > 0) {
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

	// 弹出选择父级数据字典类型窗口
	$('#select_parent').click(function() {

		$('#tree_select_ddt').tree({
			url : ctx + queryDataDictionaryTypeTreeViewUrl,
			onClick : function(node) {
				$(this).tree('toggle', node.target);
			}
		});

		$('#win_select_ddt').window({
			iconCls : 'icon-dd',
			maximizable : false
		}).window('setTitle', '选择父级数据字典类型').window('open');
	});

	// 弹出选择数据字典类型窗口
	$('#select_type').click(function() {

		$('#tree_select_dd').tree({
			url : ctx + queryDataDictionaryTypeTreeViewUrl,
			onClick : function(node) {
				$(this).tree('toggle', node.target);
			}
		});

		$('#win_select_dd').window({
			iconCls : 'icon-dd',
			maximizable : false
		}).window('setTitle', '选择数据字典类型').window('open');
	});

	// 数据字典类型树
	$('#tree_ddt').tree({
		url : ctx + queryDataDictionaryTypeTreeViewUrl,
		onClick : function(node) {// 配置树节点单击开关函数，同时记录当前操作节点以及其父亲节点、祖父节点
			$(this).tree('toggle', node.target);
			recordNode($(this));
			refresh_datagrid();
		},
		onLoadSuccess : function() {// 当树reload完毕，恢复操作节点的选择状态，并更新操作节点等状态
			if (selected_node) {
				var node = $(this).tree('find', selected_node.id);
				$(this).tree('select', node.target);
				$(this).tree('expand', node.target);
			}
			recordNode($(this));
		}
	});

	// 数据字典类型表单异步提交
	$('#form_ddt').form({
		url : ctx + saveDataDictionaryTypeUrl,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			close_win_ddt();

			if (selected_node) {
				if (parent_node) {// 如果父亲节点存在，则新增节点类型为普通子节点
					$('#tree_ddt').tree('reload', parent_node.target);
					return;
				}
			}
			$('#tree_ddt').tree('reload');// 刷新树根节点
		}
	});

	// 数据字典表单异步提交
	$('#form_dd').form({
		url : ctx + saveDataDictionaryUrl,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			close_win_dd();
			refresh_datagrid();
		}
	});

	$('#tt')
			.datagrid(
					{
						nowrap : false,
						striped : true,
						border : false,
						url : ctx + queryDataDictionaryGridViewUrl,
						// queryParams : {},
						sortName : '',
						sortOrder : 'ASC',// DESC 降序，ASC升序
						columns : [ [
								{
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
									field : 'typeName',
									title : '类型名称',
									width : 100
								},
								{
									field : 'name',
									title : '字典名称',
									width : 100
								},
								{
									field : 'value',
									title : '数据字典值',
									width : 200
								},
								{
									field : 'status',
									title : '数据字典状态',
									width : 100,
									formatter : function(value, rec) {
										// return "<a title='"+s+"'>"+s+"</a>";
										if (value == '1') {
											return '<font color="green">启用</font>';
										} else {
											return '<font color="red">停用</font>';
										}
									}
								},
								{
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
											return stop + edit + del;
										} else {
											return open + edit + del;
										}
									}
								} ] ],
						toolbar : [ {
							id : 'btnadd',
							text : '新增',
							iconCls : 'icon-dd_add',
							handler : function() {
								add_win_dd();
							}
						}, {
							id : 'btncut',
							text : '批量删除',
							iconCls : 'icon-dd_delete',
							handler : function() {
								batch_delete_dd();
							}
						} ],
						pagination : true,
						rownumbers : true,
						onBeforeLoad : function(param) {
							param.query = refresh_query;
						}
					});

});


// 1.增加功能完善，支持验证，并能够自动记忆用户操作前状态（如操作节点），执行完毕（数据库同步完毕）自动恢复到之前操作节点，方便用户连续操作。2011.11.2
// 2.删除功能完善，根据操作结果，自动切换操作节点，方便用户连续操作。2011.11.2
// 3.修改功能完成，暂不支持修改父级类型。修改父级类型可以通过把所有数据字典类型除自身外做个选择下拉框。2011.11.3

// Bug
// 1.新增数据字典类型的时候，点击数据字典类型树后就无法新增根节点了。2011.11.8
// 2.修改了父级数据字典类型名称，没有同步其下的数据字典状态。2011.12.1
// 3.删除父级数据字典类型，却没有关联删除其下的数据字典。2011.12.1

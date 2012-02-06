$(function() {
			$('#mb1,#mb2,#mb3,#mb4,#mb5').menubutton({
						plain : false
					});
		});

/** 收货管理 */
function receiveOnClick() {
	$('#ifr').attr('src', ctx + '/receive/receivemanage.action');
}

/** 采购单发布 */
function publishOnClick() {
	$('#ifr').attr('src', ctx + '/receive/index.action');
}

/** 手机短信群发 */
function msgOnClick() {
	showMsg('功能已实现，测试版暂未开发。');
}

/** 电子邮件群发 */
function emailOnClick() {
	showMsg('功能已实现，测试版暂未开发。');
}

/** 后台用户管理 */
function userOnClick() {
	$('#ifr').attr('src', ctx + '/account/index.action');
}

/** 应付款管理 */
function payableOnClick() {
	$('#ifr').attr('src', ctx + '/payable/index.action');
}

/** 收货结算管理 */
function receiveBalanceOnClick() {
	$('#ifr').attr('src', ctx + '/supply/supplybalance.action');
}

/** 数据字典管理 */
function ddOnClick() {
	$('#ifr').attr('src', ctx + '/datadictionary/index.action');
}

/** 角色权限 */
function authOnClick() {
	$('#ifr').attr('src', ctx + '/authority/index.action');
}

/** 用户角色 */
function roleOnClick() {
	$('#ifr').attr('src', ctx + '/role/index.action');
}
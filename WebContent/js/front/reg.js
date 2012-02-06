var getComboxDataUrl = '/datadictionary/datadictionary!queryAllDataDictionaryByTypeCode.action';
var checkLoginNameUrl = '/account/user!checkLoginName.action';// 检查登陆名是否重复地址
var saveUserUrl = '/account/user!save.action';// 保存用户地址

/** get combox data */
function getComboxData(typeCodes, selectId) {
	$.ajax({
		async : false,// required
		type : 'post',
		dataType : 'json',
		timeout : 10000,
		url : ctx + getComboxDataUrl,
		data : {
			typeCodes : typeCodes
		},
		success : function(data) {
			for ( var i = 0; i < data.mmwt.length; i++) {
				$("#" + selectId + "").append(
						"<option value=" + data.mmwt[i].id + ">"
								+ data.mmwt[i].value + "</option>");
			}
		}
	});
}

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
				alert('用户名已存在，请重新输入');
				$('#loginName').val('');
			}
		}
	});

}

/** 检查用户注册填写的资料 */
function checkFill() {

	if ($('#loginName').val() == "") {
		alert('用户名不能为空。');
		return;
	}

	if ($('#password').val() == "" || $('#pwd').val() == "") {
		alert('登录密码或确认密码不能为空。');
		return;
	}

	if ($('#password').val() != $('#pwd').val()) {
		alert('您两次输入的密码不正确，请重新确认输入。 ');
		return;
	}

	if ($('#name').val() == "") {
		alert('姓名不能为空。');
		return;
	}

	if ($('#mobi').val() == "") {
		alert('手机号码不能为空。');
		return;
	}

	if ($('#mobi').val().length > 0) {
		var value = $('#mobi').val();
		if (!((/^13\d{9}$/g.test(value)) || (/^15[0-35-9]\d{8}$/g.test(value)) || (/^18[5-9]\d{8}$/g
				.test(value)))) {
			alert('手机号码填写不正确，请重新填写');
			$('#mobi').val('');
			return;
		}
	}

	if ($('#qq').val() == "") {
		alert('联系QQ不能为空。');
		return;
	}

	if ($('#qq').val().length > 0) {
		var value = $('#qq').val();
		if (!/^\d{5,10}$/.test(value)) {
			alert('QQ号码填写不正确，请重新填写');
			$('#qq').val('');
			return;
		}
	}

	if ($('#email').val() == "") {
		alert('电子邮件不能为空。');
		return;
	}

	if ($('#email').val().length > 0) {
		var value = $('#email').val();
		if (!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value)) {
			alert('电子信箱填写不正确，请重新填写');
			$('#email').val('');
			return;
		}
	}

	if ($('#answer').val() == "") {
		alert('密码答案不能为空。');
		return;
	}

	if (!$('#ty').get(0).checked) {
		alert('请勾选同意协议内容。');
		return;
	}

	return true;
}

$(function() {

	/** 获取密码问题下拉选项框数据 */
	getComboxData('mmwt', 'questionId');

	/** 注册用户 */
	$('#regUser').click(function() {
		if (checkFill()) {

			$.ajax({
				async : true,// required
				type : 'post',
				dataType : 'json',
				timeout : 10000,
				url : ctx + saveUserUrl,
				data : $('#regbox').serializeObject(),
				success : function(data) {
					window.location.href = 'page-commit';
				}
			});
			// alert('注册成功，等待管理用户验证账户完毕，账户才能使用。');
			// window.history.go(-1);
		}
	});
});

// 1.暂不实现输入格式校验

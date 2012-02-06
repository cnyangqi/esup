var loginUrl = '/j_spring_security_check';// 登录地址

$(function() {

	/** 用户登录 */
	$('#btlogin').click(function() {

		$.ajax({
			async : true,// required
			type : 'post',
			// dataType : 'json',
			timeout : 10000,
			url : ctx + loginUrl,
			data : $('#loginform').serializeObject(),
			success : function(data) {
				location.href = ctx + '/portal/portal.action';
			}
		});

	});

	$("#login").keyup(function(event) {
		if (event.keyCode == 13) {
			$('#btlogin').click();
		}
	});

});
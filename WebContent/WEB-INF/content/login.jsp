<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter"%>
<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>
<%@ page import="org.springframework.security.web.WebAttributes"%>
<meta http-equiv="refresh" content="3;url=${ctx}/page-login">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Esup</title>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/header.jsp"%>
<script type="text/javascript" src="${ctx}/js/account/login.js"></script>

<script type="text/javascript">
	$(function(){
		var authen_exception="<%=session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) %>";
		if (authen_exception.length > 4) {
			showMsg('用户名或密码错误，请重新输入。');
		}
	});
</script>
</head>
<body style="visibility:hidden;">

	<%-- 系统登录窗口 --%>
	<div id="w" class="easyui-window" title="Esup系统后台登陆" iconCls="icon-key" style="width:500px;height:300px;padding:5px;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				<div class="easyui-layout" fit="true">
					<div region="west" border="false" style="width: 280px;" >
						系统公告：
						<li style="margin-top: 10px;"><a href="#" target="_blank">1.</a></li>
					</div>
					<div region="center" border="" style="padding-top: 50px;padding-left: 10px;padding-right: 5px;">
						<div>
							<form id="f" action="${ctx}/j_spring_security_check" method="post">
								<div>
									<label for="">用户名：</label>
									<input class="easyui-validatebox" type="text" name="j_username" required="true" id="j_username" style="width: 140px;" />
								</div>
								<div style="margin-top: 10px;">
									<label for="">密&nbsp;&nbsp;码：</label>
									<input class="easyui-validatebox" type="password" name="j_password" required="true" id="j_password" style="width: 140px;" />
								</div>
								<div style="margin-top: 10px;">
									<input type="checkbox" style="" name="_spring_security_remember_me" id="_spring_security_remember_me"/>
									<font style="">两周内记住我</font>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div region="south" border="false" style="text-align:right;height:30px;line-height:30px;">
				<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="javascript:$('#f').submit()">登陆</a>
				<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeWindow()">关闭</a>
			</div>
		</div>
	</div>
	
</body>
</html>

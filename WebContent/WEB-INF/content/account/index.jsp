<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.springside.modules.security.springsecurity.SpringSecurityUtils"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Esup</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/header.jsp"%>
<script type="text/javascript" src="${ctx}/js/account/index.js"></script>
</head>
<body class="easyui-layout" style="height: 100%; width: 100%; overflow: hidden; border: none; visibility: hidden;">

	<!-- 界面布局 -->
	<div region="center">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false" style="overflow: hidden;">
				<div class="easyui-layout" fit="true"> 
				<div class="easyui-panel" region="north" border="false" style="height: 40px; overflow: hidden;">
				<div style="clear: both;" class="SearchArea">
					登陆账户：<input id="key1" name="key1" type="text" style="width: 100px;"/>
					账户状态：<input class="easyui-combobox" id="key2" name="key2" type="text" style="width: 100px;"/>
					<a href="javascript:query_object()" class="easyui-linkbutton" plain="true" icon="icon-search" >查询</a>
					<a href="javascript:query_reset()" class="easyui-linkbutton" plain="true" icon="icon-refresh" >重置</a>
				</div>
				</div>
				<div region="center" style="border: 1px;">
					<table id="tt" fit="true"  style="overflow: hidden; "></table>
				</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 用户管理窗口 -->
	<div id="win_user" class="easyui-window" closed="true" modal="true" iconCls="icon-user" style="width: 530px; height: 240px; padding: 5px; background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
				<form id="form_user" method="post">
					<input type="hidden" id="id" name="id"/><!-- 用户id -->
					<table align="center">
						<tr>
							<td align="right">登陆账户：</td>
							<td><input id="loginName" name="loginName" type="text" class="easyui-validatebox" required="true" onchange="checkLoginName(this.value)" /><font color="red">*</font></td>
							<td align="right">用户姓名：</td>
							<td><input name="name" type="text" class="easyui-validatebox" required="true"/><font color="red">*</font></td>
						</tr>
						<tr>
							<td align="right">用户昵称：</td>
							<td>
								<input name="nickname" type="text"/>
							</td>
							<td align="right">用户状态：</td>
							<td>
								<input id="status" name="status" type="text" class="easyui-combobox" required="true"/><font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td align="right">手机号码：</td>
							<td>
								<input name="mobi" type="text" />
							</td>
							<td align="right">电话号码：</td>
							<td>
								<input name="tel" type="text" />
							</td>
						</tr>
						<tr>
							<td align="right">QQ号码：</td>
							<td><input name="qq" type="text"/></td>
							<td align="right">电子邮件：</td>
							<td><input name="email" type="text" class="easyui-validatebox" validType="email"/></td>
						</tr>
						<tr>
							<td align="right">用户密码：</td>
							<td><input id="password" name="password" type="password" class="easyui-validatebox" required="true" /><font color="red">*</font></td>
							<td></td>
							<td></td>
						</tr>
					<!-- <tr> -->
					<!--	<td align="right">角色：</td> -->
					<!--		<td colspan="3"> -->
					<!--		<input id="roles" type="text" size="50" readonly="readonly"/> -->
					<!--	<input type="button" value=".." onclick="rolesSelectTree('11e0-9bef-577885a5-85e9-2b6963eb3ccf')"/> -->
					<!-- 	</td> -->
					<!-- </tr> -->
					</table>
				</form>
			</div>
			<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
				<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="save()">保存</a>
				<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="cancel()">取消</a>
			</div>
		</div>
		</div>

</body>
</html>
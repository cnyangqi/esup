<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.springside.modules.security.springsecurity.SpringSecurityUtils"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Esup_用户角色</title>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/header.jsp"%>
<script type="text/javascript" src="${ctx}/js/role/role.js"></script>
</head>
<body class="easyui-layout" style="height: 100%;width: 100%;overflow: hidden;border: none;visibility: hidden;">

	<%-- 界面布局 --%>
	<div region="center" iconCls="icon-role" title="用户角色管理" style="overflow: hidden;">
		<div class="easyui-layout" fit="true"> 
			<div class="easyui-panel" region="north" border="false" style="height: 40px; overflow: hidden;">
				<div style="clear: both;" class="SearchArea">
					账户名称：<input id="login_name" name="login_name" type="text" size="10"/>
					角色名称：<input id="role_name" name="role_name" type="text" size="10"/>
					<a href="javascript:query_data_dictionary()" class="easyui-linkbutton" plain="true" icon="icon-search" >查询</a>
					<a href="javascript:query_reset()" class="easyui-linkbutton" plain="true" icon="icon-refresh" >重置</a>
				</div>
			</div>
			<div region="center" style="border: 1px;">
				<table id="tt" fit="true"  style="overflow: hidden; "></table>
			</div>
		</div>
	</div>
	
	<%-- 选择用户账户窗口 --%>
	<div id="win_select_user" class="easyui-window" closed="true" modal="true" style="width: 600px; height: 320px; padding: 5px; background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false" style="overflow: hidden;">
				<div class="easyui-layout" fit="true"> 
				<div class="easyui-panel" region="north" border="false" style="height: 40px; overflow: hidden;">
				<div style="clear: both;" class="SearchArea">
					登陆账户：<input id="key1" name="key1" type="text" style="width: 100px;"/>
					账户状态：<input class="easyui-combobox" id="key2" name="key2" type="text" style="width: 100px;"/>
					<a href="javascript:query_object_yhxz()" class="easyui-linkbutton" plain="true" icon="icon-search" >查询</a>
					<a href="javascript:query_reset_yhxz()" class="easyui-linkbutton" plain="true" icon="icon-refresh" >重置</a>
				</div>
				</div>
				<div region="center" style="border: 1px;">
					<table id="tt2" fit="true"  style="overflow: hidden; "></table>
				</div>
				</div>
			</div>
			<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
				<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="save_select_ddt()">保存</a>
				<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="close_win_select_ddt()">取消</a>
			</div>
		</div>
	</div>
	
	<%-- 用户角色窗口 --%>
	<div id="win_dd" class="easyui-window" closed="true" modal="true" style="width: 350px; height: 160px; padding: 5px; background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" style="padding: 10px; background: #fff; border: 1px solid #ccc;overflow: hidden;">
				<form id="form_dd" action="" method="post">
				
					<input type="hidden" name="id" /><!-- id -->
					<input type="hidden" name="userId" /><!-- typeId -->
					
					<table align="center">
						<tr>
							<td align="right">用户账户：</td>
							<td>
							<input name="loginName" type="text" class="easyui-validatebox" required="true" readonly="readonly"/>
							<font color="red">*</font>
							<input id="select_type" name="select_type" type="button" value="&lt;"/>
							</td>
						</tr>
						<tr>
							<td align="right">分配角色：</td>
							<td>
							<input id="roleId" name="roleId" type="text" class="easyui-validatebox" required="true"/>
							<font color="red">*</font>
							</td>
						</tr>
						<%-- 
						<tr>
							<td align="right">字典值：</td>
							<td>
								<input name="value" type="text" class="easyui-validatebox" required="true"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td align="right">权限状态：</td>
							<td><input class="easyui-combobox" id="status" name="status" type="text" /></td>
						</tr>
						--%>
					</table>
					
				</form>
			</div>
			<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
				<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="save_dd()">保存</a>
				<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="close_win_dd()">取消</a>
			</div>
		</div>
	</div>
	
</body>
</html>
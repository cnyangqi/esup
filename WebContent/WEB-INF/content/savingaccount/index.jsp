<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.springside.modules.security.springsecurity.SpringSecurityUtils"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Esup_供货商财务账户管理</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/header.jsp"%>
<script type="text/javascript" src="${ctx}/js/savingaccount/savingaccount.js"></script>
</head>
<body class="easyui-layout" style="height: 100%;width: 100%;overflow: hidden;border: none;visibility: hidden;">

	<%-- 界面布局 --%>
	
	<div region="center" iconCls="icon-coins" title="收款账户管理" style="overflow: hidden;">
		<div class="easyui-layout" fit="true"> 
			<div class="easyui-panel" region="north" border="false" style="height: 40px; overflow: hidden;">
				<div style="clear: both;" class="SearchArea">
					开户行：<input id="key1" name="key1" type="text" size="10"/>
					收款人：<input id="key2" name="key2" type="text" size="10"/>
					收款账户：<input id="key3" name="key3" type="text" size="10"/>
					起始日期：<input id="key4" name="key4" type="text" size="10"/>
					结束日期：<input id="key5" name="key5" type="text" size="10"/>
					<a href="javascript:queryRecord()" class="easyui-linkbutton" plain="true" icon="icon-search" >查询</a>
					<a href="javascript:queryReset()" class="easyui-linkbutton" plain="true" icon="icon-refresh" >重置</a>
				</div>
			</div>
			<div region="center" style="border: 1px;">
				<table id="datagrid1" fit="true" style="overflow: hidden;"></table>
			</div>
		</div>
	</div>
	
	<%-- 新增record窗口 --%>
	<div id="win_record" class="easyui-window" closed="true" modal="true" title="角色管理" style="width: 350px; height: 220px; padding: 5px; background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" style="padding: 10px; background: #fff; border: 1px solid #ccc;overflow: hidden;">
				<form id="form_datagrid1" action="" method="post">
				
					<input type="hidden" name="id" /><!-- id -->
					
					<table align="center">
						<tr>
							<td align="right">账户类型：</td>
							<td>
								<input id="accountType" name="accountType" type="text" class="easyui-combobox" required="true" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td align="right">开户行：</td>
							<td>
								<input name="accountBank" type="text" class="easyui-validatebox" required="true"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td align="right">收款人：</td>
							<td>
								<input name="accountName" type="text" class="easyui-validatebox" required="true"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td align="right">收款账户：</td>
							<td>
								<input name="account" type="text" class="easyui-validatebox" required="true"/>
								<font color="red">*</font>
							</td>
						</tr>
					</table>
					
				</form>
			</div>
			<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
				<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="saveRecord()">保存</a>
				<%-- <a class="easyui-linkbutton" iconCls="icon-reset" href="javascript:void(0)" onclick="reset_win_ddt()">重置</a> --%>
				<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeRecordWin()">取消</a>
			</div>
		</div>
	</div>
	
</body>
</html>
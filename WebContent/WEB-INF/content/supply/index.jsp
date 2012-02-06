<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.springside.modules.security.springsecurity.SpringSecurityUtils"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Esup_供货单申报</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/header.jsp"%>
<script type="text/javascript" src="${ctx}/js/supply/supply.js"></script>
<script type="text/javascript">
	var qq='<security:authentication property="principal.user.qq"/>';
	var mobi='<security:authentication property="principal.user.mobi"/>';
</script>
</head>
<body class="easyui-layout" style="height: 100%;width: 100%;overflow: hidden;border: none;visibility: hidden;">

	<%-- 界面布局 --%>
	
	<div region="center" iconCls="icon-page" title="收货列表" style="overflow: hidden;">
		<div class="easyui-layout" fit="true"> 
			<div class="easyui-panel" region="north" border="false" style="height: 40px; overflow: hidden;">
				<div style="clear: both;" class="SearchArea">
					游戏名：<input id="key1" name="key1" type="text" size="10"/>
					服务器名：<input id="key2" name="key2" type="text" size="10"/>
					单价大于：<input id="key3" name="key3" type="text" size="10"/>
					截止日期：<input id="key5" name="key5" type="text" style="width:110px" readonly="readonly"/>
					<a href="javascript:queryRecord()" class="easyui-linkbutton" plain="true" icon="icon-search" >查询</a>
					<a href="javascript:queryReset()" class="easyui-linkbutton" plain="true" icon="icon-refresh" >重置</a>
				</div>
			</div>
			<div region="center" style="border: 1px;">
				<table id="datagrid1" fit="true" style="overflow: hidden;"></table>
			</div>
		</div>
	</div>
	
	<%-- 填报供货单窗口 --%>
	<div id="win_select_game" class="easyui-window" closed="true" modal="true" style="width: 330px; height: 300px; padding: 5px; background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" style="padding: 10px; background: #fff; border: 1px solid #ccc;overflow: hidden;">
				<form id="form_datagrid2" action="" method="post">
				
					<input type="hidden" name="receiveId" /><!-- 收货单主键 -->
					<input type="hidden" name="category" /><!-- 收货类型 -->
					<input type="hidden" name="game" /><!-- 收货游戏名称 -->
					<input type="hidden" name="area" /><!-- 收货游戏区名 -->
					<input type="hidden" name="server" /><!-- 收货游戏服务器名 -->
					<input type="hidden" name="unitPrice" /><!-- 收货单价 -->
					<input type="hidden" name="unit" /><!-- 货物单位 -->
					<input type="hidden" name="receiveQq" /><!-- 收货方联系QQ -->
					
					<table align="center">
						<tr>
							<td align="right">供货角色：</td>
							<td>
								<input name="dealName" class="easyui-validatebox" required="true" type="text" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td align="right">供货数量：</td>
							<td>
								<input id="quantity" name="quantity" class="easyui-validatebox" required="true" type="text" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td align="right">联系QQ：</td>
							<td>
								<input name="traderQq" class="easyui-validatebox" required="true" type="text" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td align="right">手机号码：</td>
							<td>
								<input name="traderMobi" required="true" type="text" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td align="right">备注：</td>
							<td colspan="3">
								<textarea rows="3" cols="15" name="remark" type="text"></textarea>
							</td>
						</tr>
					</table>
					
				</form>
			</div>
			<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
				<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="saveRecord2()">保存</a>
				<%-- <a class="easyui-linkbutton" iconCls="icon-reset" href="javascript:void(0)" onclick="reset_win_ddt()">重置</a> --%>
				<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeRecordWin2()">取消</a>
			</div>
		</div>
	</div>
	
</body>
</html>
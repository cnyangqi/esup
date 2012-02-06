<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.springside.modules.security.springsecurity.SpringSecurityUtils"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Esup_收货单发布</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/header.jsp"%>
<script type="text/javascript" src="${ctx}/js/receive/receive.js"></script>
<script type="text/javascript">
	var auditFlag=<%=SpringSecurityUtils.hasAnyRole("ROLE_审核收货单") %> 
</script>
</head>
<body class="easyui-layout" style="height: 100%;width: 100%;overflow: hidden;border: none;visibility: hidden;">

	<%-- 界面布局 --%>
	
	<%--
	<div region="west" iconCls="" title="管理" split="true" style="width:200px; padding:1px; overflow: auto;">
		<a href="javascript:addNode()" class="easyui-linkbutton" plain="true" iconCls="">新增</a>
		<a href="javascript:deleteNode()" class="easyui-linkbutton" plain="true" iconCls="">删除</a>
		<a href="javascript:editNode()" class="easyui-linkbutton" plain="true" iconCls="">修改</a>
		<hr/>
		<!-- 数据字典类型树 -->
		<ul id="tree1" class="easyui-tree" ></ul>
	</div>
	--%>
	
	<div region="center" iconCls="icon-page" title="收货单管理" style="overflow: hidden;">
		<div class="easyui-layout" fit="true"> 
			<div class="easyui-panel" region="north" border="false" style="height: 40px; overflow: hidden;">
				<div style="clear: both;" class="SearchArea">
					游戏名：<input id="key1" name="key1" type="text" size="10"/>
					服务器名：<input id="key2" name="key2" type="text" size="10"/>
					单价大于：<input id="key3" name="key3" type="text" size="10"/>
					发布日期：<input id="key4" name="key4" type="text" style="width:110px" readonly="readonly"/>
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
	
	<%-- 游戏名选择窗口 --%>
	<div id="win_select_game" class="easyui-window" closed="true" modal="true" style="width: 600px; height: 320px; padding: 5px; background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false" style="overflow: hidden;">
				<div class="easyui-layout" fit="true"> 
				<div class="easyui-panel" region="north" border="false" style="height: 40px; overflow: hidden;">
				<div style="clear: both;" class="SearchArea">
					游戏名称：<input id="key11" name="key11" type="text" style="width: 100px;"/>
					<a href="javascript:queryRecord2()" class="easyui-linkbutton" plain="true" icon="icon-search" >查询</a>
					<a href="javascript:queryReset2()" class="easyui-linkbutton" plain="true" icon="icon-refresh" >重置</a>
				</div>
				</div>
				<div region="center" style="border: 1px;">
					<table id="datagrid2" fit="true" style="overflow: hidden; "></table>
				</div>
				</div>
			</div>
			<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
				<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="saveRecord2()">保存</a>
				<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeRecordWin2()">取消</a>
			</div>
		</div>
	</div>
	
	<%-- 游戏区选择窗口 --%>
	<div id="win_select_gamearea" class="easyui-window" closed="true" modal="true" style="width: 600px; height: 320px; padding: 5px; background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false" style="overflow: hidden;">
				<div class="easyui-layout" fit="true"> 
				<div class="easyui-panel" region="north" border="false" style="height: 40px; overflow: hidden;">
				<div style="clear: both;" class="SearchArea">
					游戏区名称：<input id="key111" name="key111" type="text" style="width: 100px;"/>
					<a href="javascript:queryRecord3()" class="easyui-linkbutton" plain="true" icon="icon-search" >查询</a>
					<a href="javascript:queryReset3()" class="easyui-linkbutton" plain="true" icon="icon-refresh" >重置</a>
				</div>
				</div>
				<div region="center" style="border: 1px;">
					<table id="datagrid3" fit="true" style="overflow: hidden; "></table>
				</div>
				</div>
			</div>
			<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
				<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="saveRecord3()">保存</a>
				<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeRecordWin3()">取消</a>
			</div>
		</div>
	</div>
	
	<%-- 游戏服务器选择窗口 --%>
	<div id="win_select_gameserver" class="easyui-window" closed="true" modal="true" style="width: 600px; height: 320px; padding: 5px; background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false" style="overflow: hidden;">
				<div class="easyui-layout" fit="true"> 
				<div class="easyui-panel" region="north" border="false" style="height: 40px; overflow: hidden;">
				<div style="clear: both;" class="SearchArea">
					游戏区名称：<input id="key1111" name="key1111" type="text" style="width: 100px;"/>
					<a href="javascript:queryRecord4()" class="easyui-linkbutton" plain="true" icon="icon-search" >查询</a>
					<a href="javascript:queryReset4()" class="easyui-linkbutton" plain="true" icon="icon-refresh" >重置</a>
				</div>
				</div>
				<div region="center" style="border: 1px;">
					<table id="datagrid4" fit="true" style="overflow: hidden; "></table>
				</div>
				</div>
			</div>
			<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
				<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="saveRecord4()">保存</a>
				<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="closeRecordWin4()">取消</a>
			</div>
		</div>
	</div>
	
	<%-- 新增record窗口 --%>
	<div id="win_record" class="easyui-window" closed="true" modal="true" title="角色管理" style="width: 600px; height: 330px; padding: 5px; background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" style="padding: 10px; background: #fff; border: 1px solid #ccc;overflow: hidden;">
				<form id="form_datagrid1" action="" method="post">
				
					<input type="hidden" name="id" /><!-- id -->
					<input type="hidden" name="gameid" /><!-- 游戏主键 -->
					<input type="hidden" name="areaid" /><!-- 游戏区主键 -->
					<input type="hidden" name="serverid" /><!-- 游戏服务器主键 -->
					
					<table align="center">
						<tr>
							<td align="right">游戏名：</td>
							<td>
								<input name="game" class="easyui-validatebox" required="true" type="text" readonly="readonly"/>
								<input id="game_button" type="button" value="&lt;"/>
								<font color="red">*</font>
							</td>
							<td align="right">游戏区名：</td>
							<td>
								<input name="area" class="easyui-validatebox" required="true" type="text" readonly="readonly"/>
								<input id="area_button" type="button" value="&lt;"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td align="right">服务器名：</td>
							<td>
								<input name="server" class="easyui-validatebox" required="true" type="text" readonly="readonly"/>
								<input id="server_button" type="button" value="&lt;"/>
								<font color="red">*</font>
							</td>
							<td align="right">业务类型：</td>
							<td>
								<input id="businessType" name="businessType" class="easyui-combobox" required="true" type="text" readonly="readonly"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td align="right">采购总量：</td>
							<td>
								<input name="total" class="easyui-validatebox" required="true" type="text"/>
								<font color="red">*</font>
							</td>
							<td align="right">交易下限：</td>
							<td>
								<input name="minSupply" class="easyui-validatebox" required="true" type="text"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td align="right">商品类型：</td>
							<td>
								<input id="category" name="category" class="easyui-combobox" required="true" type="text" readonly="readonly"/>
								<font color="red">*</font>
							</td>
							<td align="right">商品单位</td>
							<td>
								<input id="unit" name="unit" class="easyui-combobox" required="true" type="text" readonly="readonly"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td align="right">收货单价：</td>
							<td>
								<input name="unitPrice" class="easyui-validatebox" required="true" type="text"/>
								<font color="red">*</font>
							</td>
							<td align="right">截止日期：</td>
							<td>
								<input id="expirationDate" name="expirationDate" required="true" type="text" style="width:152px" readonly="readonly"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td align="right">交易方式：</td>
							<td>
								<input id="traderMode" name="traderMode" type="text" class="easyui-combobox" required="true" readonly="readonly"/>
								<font color="red">*</font>
							</td>
							<td align="right">收货联系：</td>
							<td>
								<input id="traderQq" name="traderQq" class="easyui-combobox" type="text" required="true" readonly="readonly"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td align="right">备注：</td>
							<td colspan="3">
								<textarea rows="3" cols="49" name="remark" type="text"></textarea>
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
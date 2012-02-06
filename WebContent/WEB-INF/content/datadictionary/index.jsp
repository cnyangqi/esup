<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.springside.modules.security.springsecurity.SpringSecurityUtils"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Esup_电子字典</title>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/header.jsp"%>
<script type="text/javascript" src="${ctx}/js/datadictionary/datadictionary.js"></script>
</head>
<body class="easyui-layout" style="height: 100%;width: 100%;overflow: hidden;border: none;visibility: hidden;">

	<%-- 界面布局 --%>
	<div region="west" iconCls="icon-dd" title="数据字典类型管理" split="true" style="width:200px; padding:1px; overflow: auto;">
		<a href="javascript:add_win_ddt()" class="easyui-linkbutton" plain="true" iconCls="icon-dd_add">新增</a>
		<a href="javascript:delete_ddt()" class="easyui-linkbutton" plain="true" iconCls="icon-dd_delete">删除</a>
		<a href="javascript:edit_ddt()" class="easyui-linkbutton" plain="true" iconCls="icon-dd_edit">修改</a>
		<hr/>
		<!-- 数据字典类型树 -->
		<ul id="tree_ddt" class="easyui-tree" ></ul>
	</div>	
	
	<div region="center" iconCls="icon-dd" title="数据字典管理" style="overflow: hidden;">
		<div class="easyui-layout" fit="true"> 
			<div class="easyui-panel" region="north" border="false" style="height: 40px; overflow: hidden;">
				<div style="clear: both;" class="SearchArea">
					类型名称：<input id="dd_type" name="dd_type" type="text" size="10"/>
					字典名称：<input id="dd_name" name="dd_name" type="text" size="10"/>
					<a href="javascript:query_data_dictionary()" class="easyui-linkbutton" plain="true" icon="icon-search" >查询</a>
					<a href="javascript:query_reset()" class="easyui-linkbutton" plain="true" icon="icon-refresh" >重置</a>
				</div>
			</div>
			<div region="center" style="border: 1px;">
				<table id="tt" fit="true"  style="overflow: hidden; "></table>
			</div>
		</div>
	</div>
	
	<%-- 选择父级数据字典类型窗口 --%>
	<div id="win_select_ddt" class="easyui-window" closed="true" modal="true" style="width: 220px; height: 320px; padding: 5px; background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" style="padding: 10px; background: #fff; border: 1px solid #ccc;overflow: hidden;">
				<!-- 选择数据字典类型树 -->
				<ul id="tree_select_ddt" class="easyui-tree" ></ul>
			</div>
				<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
				<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="save_select_ddt()">保存</a>
				<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="close_win_select_ddt()">取消</a>
			</div>
		</div>
	</div>
	
	<%-- 选择父级数据字典类型窗口 --%>
	<div id="win_select_dd" class="easyui-window" closed="true" modal="true" style="width: 220px; height: 320px; padding: 5px; background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" style="padding: 10px; background: #fff; border: 1px solid #ccc;overflow: hidden;">
				<!-- 选择数据字典类型树 -->
				<ul id="tree_select_dd" class="easyui-tree" ></ul>
			</div>
				<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
				<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="save_select_dd()">保存</a>
				<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="close_win_select_dd()">取消</a>
			</div>
		</div>
	</div>
	
	<%-- 数据字典类型窗口 --%>
	<div id="win_ddt" class="easyui-window" closed="true" modal="true" title="数据字典类型管理" style="width: 320px; height: 180px; padding: 5px; background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" style="padding: 10px; background: #fff; border: 1px solid #ccc;overflow: hidden;">
				<form id="form_ddt" action="" method="post">
				
					<input type="hidden" name="id" /><!-- id -->
					<input type="hidden" name="parentId" /><!-- parentId -->
					
					<table align="center">
						<tr>
							<td align="right">类型名称：</td>
							<td><input name="name" type="text" class="easyui-validatebox" required="true" validType="length[2,50]"/><font color="red">*</font></td>
						</tr>
						<tr id="only_new">
							<td align="right">父级类型：</td>
							<td>
								<input name="parentName" type="text" readonly="readonly"/>
								<input id="select_parent" name="select_parent" type="button" value="..."/>
							</td>
						</tr>
						<tr>
							<td align="right">排序号：</td>
							<td><input name="sequNum" type="text" /></td>
						</tr>
					</table>
					
				</form>
			</div>
			<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
				<a class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" onclick="save_ddt()">保存</a>
				<%-- <a class="easyui-linkbutton" iconCls="icon-reset" href="javascript:void(0)" onclick="reset_win_ddt()">重置</a> --%>
				<a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)" onclick="close_win_ddt()">取消</a>
			</div>
		</div>
	</div>
	
	<%-- 数据字典窗口 --%>
	<div id="win_dd" class="easyui-window" closed="true" modal="true" title="数据字典管理" style="width: 350px; height: 210px; padding: 5px; background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" style="padding: 10px; background: #fff; border: 1px solid #ccc;overflow: hidden;">
				<form id="form_dd" action="" method="post">
				
					<input type="hidden" name="id" /><!-- id -->
					<input type="hidden" name="typeId" /><!-- typeId -->
					
					<table align="center">
						<tr>
							<td align="right">字典类型：</td>
							<td>
							<input name="typeName" type="text" class="easyui-validatebox" required="true" readonly="readonly"/>
							<font color="red">*</font>
							<input id="select_type" name="select_type" type="button" value="..."/>
							</td>
						</tr>
						<tr>
							<td align="right">字典名称：</td>
							<td>
							<input name="name" type="text" class="easyui-validatebox" required="true"/>
							<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td align="right">字典值：</td>
							<td>
								<input name="value" type="text" class="easyui-validatebox" required="true"/>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td align="right">排序号：</td>
							<td><input name="sequNum" type="text" /></td>
						</tr>
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
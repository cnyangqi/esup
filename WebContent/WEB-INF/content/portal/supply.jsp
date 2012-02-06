<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.springside.modules.security.springsecurity.SpringSecurityUtils"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Esup_供货用户门户</title>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/style/portal.css">
<script type="text/javascript" src="${ctx}/js/portal/supply.js"></script>
</head>
<body class="easyui-layout" style="visibility: hidden;">
	<div id="top" region="north" border="false">
		<div class="topbg">
			<div class="user">
				当前用户：<label id="username"><%=SpringSecurityUtils.getCurrentUserName()%></label>
			</div>
			<div class="userOption">
				<font color="red">1<a>2</a></font>
				<a href="#">[修改个人资料]</a>
				<a href="#">[修改密码]</a>
				<a id="logout" href="${ctx}/j_spring_security_logout">[退出系统]</a>
			</div>
		</div>
	</div>
	<div region="center">
		<div class="easyui-layout" fit="true">
			<div region="north" border="1" style="overflow:hidden; ">
				<div style="background:#C9EDCC;padding:5px;width:100%;">
				<a href="javascript:supplyOnClick()" class="easyui-linkbutton" iconCls="icon-page">我要供货</a>
				<a href="javascript:supplyManageOnClick()" class="easyui-linkbutton" iconCls="icon-page_edit">供货管理</a>
				<a href="javascript:savingaccountOnClick()" class="easyui-linkbutton" iconCls="icon-coins">收款账户</a>
				<a href="javascript:paymentOnClick()" class="easyui-linkbutton" iconCls="icon-coins">已结货款</a>
				</div>
			</div>
			
			<%-- view iframe --%>
			<div region="center" border="false" style="overflow:hidden;">
			<iframe id="ifr" src="${ctx}/images/supply.jpg" width="100%" height="100%" frameborder="0" style="border: 0;"></iframe>
			</div>
		</div>
	</div>
</body>
</html>

<%--
	1、菜单循环用itr Struts2 系统模块管理
--%>
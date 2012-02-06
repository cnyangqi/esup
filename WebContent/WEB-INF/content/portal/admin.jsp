<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.springside.modules.security.springsecurity.SpringSecurityUtils"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title>Esup_管理门户</title>
<%@ include file="/common/meta.jsp"%>
<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/style/portal.css">
<script type="text/javascript" src="${ctx}/js/portal/admin.js"></script>
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
				<a href="javascript:void(0)" id="mb1" class="easyui-menubutton" menu="#mm1" iconCls="icon-user">业务中心</a>
				<security:authorize ifAnyGranted="ROLE_浏览结算中心菜单">
					<a href="javascript:void(0)" id="mb2" class="easyui-menubutton" menu="#mm2" iconCls="icon-coins">结算中心</a>
				</security:authorize>
				<security:authorize ifAnyGranted="ROLE_浏览管理中心菜单">
					<a href="javascript:void(0)" id="mb3" class="easyui-menubutton" menu="#mm3" iconCls="icon-chart_bar">管理中心</a>
				</security:authorize>
				<a href="javascript:void(0)" id="mb4" class="easyui-menubutton" menu="#mm4" iconCls="icon-anchor">快捷通道</a>
				<security:authorize ifAnyGranted="ROLE_浏览系统配置菜单">
					<a href="javascript:void(0)" id="mb5" class="easyui-menubutton" menu="#mm5" iconCls="icon-cog_edit">系统配置</a>
				</security:authorize>
				</div>
				
				<div id="mm1" style="width:150px;">
					<security:authorize ifAnyGranted="ROLE_浏览收货管理菜单">
					<div iconCls="icon-page" onclick="receiveOnClick()">收货管理</div>
					</security:authorize>
					
					<security:authorize ifAnyGranted="ROLE_浏览收货发布菜单">
					<div iconCls="icon-page" onclick="publishOnClick()">收货发布</div>
					</security:authorize>
					
					<div iconCls="icon-phone" onclick="msgOnClick()">短信群发</div>
					<div iconCls="icon-email" onclick="emailOnClick()">邮件群发</div>
					
					<security:authorize ifAnyGranted="ROLE_浏览用户">
					<div iconCls="icon-user" onclick="userOnClick()">人员管理</div>
					</security:authorize>
				</div>
				
				<security:authorize ifAnyGranted="ROLE_浏览结算中心菜单">
				<div id="mm2" style="width:150px;">
					<div iconCls="icon-coins_delete" onclick="payableOnClick()">应付款管理</div>
				</div>
				</security:authorize>
				
				<security:authorize ifAnyGranted="ROLE_浏览系统配置菜单">
				<div id="mm5" style="width:150px;">
					<div iconCls="icon-dd" onclick="ddOnClick()">数据字典</div>
					<div iconCls="icon-key" onclick="authOnClick()">角色权限</div>
					<div iconCls="icon-role" onclick="roleOnClick()">用户角色</div>
					<div iconCls="" onclick="">快捷配置</div>
				</div>
				</security:authorize>
		
			</div>
			
			<%-- view iframe --%>
			<div region="center" border="false" style="overflow:hidden;">
			<iframe id="ifr" src="${ctx}/images/manage.jpg" width="100%" height="100%" frameborder="0" style="border: 0;"></iframe>
			</div>
		</div>
	</div>
</body>
</html>

<%--
	1、菜单循环用itr Struts2 系统模块管理
--%>
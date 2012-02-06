<%@page import="cn.esup.entity.account.User"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.springside.modules.security.springsecurity.SpringSecurityUtils"%>
<%@include file="/common/taglibs.jsp"%>
<%
	if (SpringSecurityUtils.hasAnyRole("ROLE_浏览后台门户")) {
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/portal/admin.action");
		rd.forward(request, response);
	}

	if (SpringSecurityUtils.hasAnyRole("ROLE_浏览供货门户")) {
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/portal/supply.action");
		rd.forward(request, response);
	}

	// 1.用户日志
	// 2.角色判定最终还是依赖判定权限
%>
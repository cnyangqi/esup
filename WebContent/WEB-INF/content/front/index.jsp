<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<html>
<head>
<title>呦嘻网|游戏币收购|游戏币出售|游戏币交易|游戏辅助程序</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/header_front.jsp"%>
<link href="${ctx}/style/ofui.global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/style/lbesup.index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/front/index.js"></script>
<style>
.tab_block {
	border-bottom: 4px solid #A0A0A0;
	position: relative;
}
</style>
</head>
<body>
	<div class="pagewrap">
		<div class="header">
			<a href="${ctx}" class="logo">Esup</a>
			<div class="topnav">
				<ul>
					<li><i class="ico-nav icoindex"></i><a href="${ctx}/page-index" class="index"><span title="首页">首页</span></a></li>
					<li><i class="ico-nav icoreg"></i><a href="${ctx}/page-reg"><span title="注册">注册</span></a></li>
					<li class="last"><i class="ico-nav icodown"></i><a href="${ctx}/page-login"><span title="登录">登录</span></a></li>
				</ul>
			</div>

		</div>
		<div class="content" style="padding: 0;">
			<div class="infolist">
							<div class="protext">
					<h3>呦嘻网</h3>
					<p>呦嘻网络成立于2009年8月18日，呦嘻网络的目标 随着中国网游产业迅速发展，不断完善用户体验，持续优化安全机制，努力提升服务质量，力争成为互联网网游及数字产品电子商务网站的佼佼者。</p>
					<p>在“用户至上”思想的指导下，在“追求卓越、超越自我”的精神激励下，呦嘻网络凭借先进的技术和严谨的管理，以快捷的交易速度、便利的交易流程、人性化的交易服务和不断优化的交易安全机制，为用户提供良好的网游道具、帐号、点卡、代练交易服务。</p>
				</div>
				<!--activities-->
				<div class="activities">
					<h3>活动公告</h3>
					<div class="newlist">
						<ul>
							<li><a href="news.html"><span class="right">[2011.12.06]</span>呦嘻网游交易平台测试开通，敬请期待！<i class="new"></i></a></li>
							<li><a href="#"><span class="right"></span></a></li>
							<li><a href="#"><span class="right"></span></a></li>
							<li><a href="#"><span class="right"></span></a></li>
							<li><a href="#"><span class="right"></span></a></li>
						</ul>
					</div>
				</div>
			</div>

		</div>
		<br />
		<div class="detailWrap">
			<div class="tab_block pusht"></div>
			<table class="tablelist" border="0">
				<tbody>
					<c:forEach var="entity" items="${top10List }">
						<tr class="tablelist">
							<td width="7%" align="center"><span class="orange">收购</span></td>
							<td width="10%" align="center"><span class="blue">${entity.category }</span></td>
							<td width="26%" align="center"><span class="red">${entity.game }-${entity.area }-${entity.server }</span></td>
							<td width="43%" align="center">${entity.remark }</td>
							<td width="14%" align="center"><fmt:formatDate value="${entity.publicDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
					</c:forEach>
					<tr class="tablelist">
						<td colspan="5" align="right"><a href="#"></a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="footer">
		<p>
			<a href="#" target="_blank">关于我们</a>
			<a href="#" target="_blank">帮助中心</a>
			<a href="#" target="_blank">网站地图</a>
			<a href="${ctx}/page-connectus" target="_blank">联系我们</a>
			<a href="#" target="_blank">找回密码</a>
			<br> &copy;2009 - 2011 呦嘻网络
		</p>
	</div>
</body>
</html>
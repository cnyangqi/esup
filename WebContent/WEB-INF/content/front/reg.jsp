<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>呦嘻网|游戏币收购|游戏币出售|游戏币交易|游戏辅助程序-注册页面</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/header_front.jsp"%>
<link href="${ctx}/style/ofui.global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/style/lbesup.index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/front/reg.js"></script>
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
		<div class="content">
			<div class="leftside regleft">
				<div class="stepbox">
					<h4 class="step">申请流程</h4>
					<div class="steppic"></div>
				</div>
			</div>
			<div class="rightside regright regarea">
				<h4 class="reg">
					<small class="right gray">注：以下均为必填项</small>申请注册<small>（第一步：填写账户信息）</small>
				</h4>
				<div id="regbox" class="regbox">
					<ul class="form">
						<li><label>用户名 : </label><input id="loginName" name="loginName" type="text" onblur="checkLoginName(this.value)" /></li>
						<li><label>登录密码 : </label><input id="pwd" type="password" /></li>
						<li><label>确认密码: </label><input id="password" name="password" type="password" /></li>
						<li><label>姓名 : </label><input id="name" name="name" type="text" /></li>
						<li><label>手机号码 : </label><input id="mobi" name="mobi" type="text" /></li>
						<li><label>联系QQ : </label><input id="qq" name="qq" type="text" /></li>
						<li><label>电子邮件 : </label><input id="email" name="email" type="text" /></li>
						<li><label>密码问题 : </label><select id="questionId" name="questionId"></select></li>
						<li><label>密码答案 : </label><input id="answer" name="answer" type="text" /></li>
						<li class="label agree">
							<ul class="transverse">
								<li><input type="checkbox" id="ty" /><label for="ty">同意</label></li>
								<li><a href="#" target="_bank">呦嘻网络交易协议</a></li>
							</ul>
						<li class="label"><button id="regUser" type="button">注册</button></li>
					</ul>
				</div>
			</div>
		</div>
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
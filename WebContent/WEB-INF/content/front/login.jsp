<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>呦嘻网|游戏币收购|游戏币出售|游戏币交易|游戏辅助程序-登陆页面</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/header_front.jsp"%>
<link href="${ctx}/style/ofui.global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/style/lbesup.index.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/style/ptlogin090aa5.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/front/login.js"></script>
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
			<DIV id=login class="login_container">
				<DIV class=login_box>
					<DIV class=logo_title>
						<H1>登录呦嘻网</H1>
					</DIV>
					<DIV style="DISPLAY: none" id=qlogin class=qlogin></DIV>
					<DIV style="DISPLAY: block;" id=web_login class=login_operate sty>
						<FORM id=loginform action="${ctx}/j_spring_security_check" method=post name=loginform target=_self autocomplete="on">
							<DIV class=username>
								<INPUT class="txt password" id="j_username" name="j_username" style="background: #FFF; width: 295px">
							</DIV>
							<DIV class=password>
								<INPUT class="txt password" id="j_password" name="j_password" type="password" style="background: #FFF; width: 295px;">
							</DIV>
							<div class="about_password">
								<input class="remerber_password" type="checkbox" id="_spring_security_remember_me" name="_spring_security_remember_me" tabindex="5">
								<label for="remerber_password">记住登录状态</label>
								<a class="forgetPassword" href="/cgi-bin/loginpage?t=getpwdback" target="_blank">忘记密码？</a>
							</div>
							<DIV class=login_submit>
								<a class=login_btn_wrapper href="javascript:;">
								<INPUT id="btlogin" class="login_btn" tabIndex="5" value="登录" type="button"></a>
							</DIV>
						</FORM>
					</DIV>
					<DIV class=login_tips>
						<span class="gray">合作伙伴账号登录</span>&nbsp;<a>QQ</a>&nbsp;<a>微博</a>&nbsp;<a>支付宝</a>&nbsp;<a>百度</a>&nbsp;<a>开心网</a>
						<P>
							<SPAN class=gray>还没有帐号？</SPAN><A
								href="${ctx}/page-reg">立即注册</A>
						</P>
					</DIV>
				</DIV>
			</DIV>
			<DIV class=mail_advert>
				<DIV class=advert_pic>
					<IMG
						style="WIDTH: 146px; BACKGROUND: url(${ctx}/style/images/146.jpg) no-repeat; HEIGHT: 168px" />
				</DIV>
				<DIV class=advert_txt>
					<H1>
						<A href="${ctx}" target=_blank>呦嘻网，迎双节！</A>
					</H1>
					<P>每交易500元返利回5元</P>
					<P>每500元返利回5元</P>
					<P>1500元返利还15元</P>
					<P>以此类推。。。。。</P>
					<P style="MARGIN: 6px 0px 0px; FONT-SIZE: 12px">面向呦嘻网络注册的所有用户哦！</P>
				</DIV>
			</DIV>
			<DIV class=mail_intro>
				<H1>呦嘻网使用提示</H1>
				<UL class=gray>
					<LI>已有帐号，请直接登录。
					<LI>您还可以<A href="${ctx}/page-reg">注册</A>一个帐号，并以此登录。

					
					<LI>将呦嘻网<A href="${ctx}" target=_blank>（77676.com）</A>加入收藏夹，便于使用。
					</LI>
				</UL>
			</DIV>
		</DIV>
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
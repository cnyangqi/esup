<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Ajax</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/header.jsp"%>
<meta content="text/html; charset=utf-8" http-equiv="content-type" />
<link rel="stylesheet" type="text/css" href="${ctx}//js/kindeditor/themes/simple/simple.css">
<script type="text/javascript" src="${ctx}/js/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" src="${ctx}/js/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/test/test.js"></script>
</head>

<body class="easyui-layout" style="height: 100%; width: 100%; overflow: hidden; border: none; visibility: hidden;">
	<div region="center" iconCls="icon-page" title="编辑" style="overflow: hidden;">
		<textarea id="editor_id" name="content" style="width: 700px; height: 300px;">
			&lt;strong&gt;HTML内容&lt;/strong&gt;
		</textarea>
	</div>
</body>
</html>

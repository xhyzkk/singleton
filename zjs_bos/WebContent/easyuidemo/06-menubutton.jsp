<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>messager消息提示框</title>
	<!-- 引入easyui相关的资源文件 -->
	<link rel="stylesheet" type="text/css"
	 href="${pageContext.request.contextPath}/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
	<script type="text/javascript"
	 src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript"
	 src="${pageContext.request.contextPath}/js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<!-- 引入ztree资源文件 -->
	 <link rel="stylesheet"
	  href="${pageContext.request.contextPath}/js/ztree/zTreeStyle.css" type="text/css">
	<script type="text/javascript"
	 src="${pageContext.request.contextPath}/js/ztree/jquery.ztree.all-3.5.js"></script>
	 <script type="text/javascript">
	 	$(function(){
		 	
	 	});
	 </script>
</head>
<body>
	<a class="easyui-menubutton" data-options="menu:'#mm',iconCls:'icon-help'">系统菜单</a>
	<!-- 下拉菜单选项使用div制作 -->
	<div id="mm">
		<!-- 每个在div是其中的一个菜单项 -->
		<div data-options="iconCls:'icon-add'">用户管理</div>
		<div>角色管理</div>
		<div class="menu-sep"></div>
		<div>部门管理</div>
	</div>
</body>
</html>
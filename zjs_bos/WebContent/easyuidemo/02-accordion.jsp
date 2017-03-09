<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>layout页面布局</title>
	<!-- 引入easyui相关的资源文件 -->
	<link rel="stylesheet" type="text/css"
	 href="${pageContext.request.contextPath}/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
	<script type="text/javascript"
	 src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
	<!-- 每个div是一个区域 -->
	<div data-options="region:'north',title:'XXX管理系统'" style="height: 150px">北部区域</div>
	<div data-options="region:'west',title:'系统菜单'" style="width: 200px">
		<!-- 折叠面板 -->
		<div class="easyui-accordion" data-options="fit:true">
			<!-- 每个子div是一个面板 -->
			<div data-options="iconCls:'icon-search'" title="面板一">
				内容一
			</div>
			<div title="面板二">
				内容二
			</div>
			<div title="面板三">
				内容三
			</div>
		</div>
	</div>
	<div data-options="region:'center'">中部区域</div>
	<div data-options="region:'south'" style="height: 50px">南部区域</div>
</body>
</html>
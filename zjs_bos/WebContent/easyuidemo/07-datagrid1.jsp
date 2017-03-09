<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>datagrid数据表格使用</title>
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
	
</head>
<body>
	<h3>数据表格使用方式一：将静态html代码渲染为datagrid</h3>
	<table class="easyui-datagrid">
		<thead>
			<tr>
				<th data-options="field:'id'">编号</th>
				<th data-options="field:'name'">姓名</th>
				<th data-options="field:'age'">年龄</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>001</td>
				<td>张三</td>
				<td>20</td>
			</tr>
			<tr>
				<td>002</td>
				<td>李四</td>
				<td>30</td>
			</tr>
			<tr>
				<td>003</td>
				<td>王五</td>
				<td>40</td>
			</tr>
		</tbody>
	</table>
</body>
</html>
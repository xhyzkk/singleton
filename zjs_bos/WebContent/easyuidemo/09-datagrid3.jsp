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
	<script type="text/javascript">
		$(function(){
			//将页面中的table变为datagrid样式
			$("#grid").datagrid(
					{
						//定义表头
						columns:[[
						          {field:'id',title:'编号',checkbox:true},//每个json表示一列的信息
						          {field:'name',title:'姓名'},
						          {field:'age',title:'年龄'}
						          ]],
						//指定一个url地址，datagrid会向这个地址发送ajax请求，获取json数据
						url:'${pageContext.request.contextPath }/json/data.json',
						pagination:true,//显示分页工具条
						singleSelect:true,//只能单选
						rownumbers:true,//显示行号
						toolbar:[
						         {text:'添加',iconCls:'icon-add',handler:function(){
						        	 alert('添加操作');
						         }},//每个json对应一个按钮
						         {text:'删除',iconCls:'icon-remove'},
						         {text:'修改',iconCls:'icon-edit'}
						         ]
					}
			);
		});
	</script>
</head>
<body>
	<h3>数据表格使用方式三：使用js代码生成datagrid</h3>
	<table id="grid"></table>
</body>
</html>
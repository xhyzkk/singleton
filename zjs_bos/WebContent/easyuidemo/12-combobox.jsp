<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>combobox下拉框使用</title>
<!-- 引入easyui相关的资源文件 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/easyui/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<!-- 引入ztree资源文件 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/ztree/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/ztree/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.ocupload-1.1.2.js"></script>
<script type="text/javascript">
	
</script>
</head>
<body>
	<h3>方式一：使用select标签描述数据，通过样式easyui-combobox</h3>
	<select id="cc" class="easyui-combobox" name="dept"
		style="width: 200px;">
		<option value="aa">aitem1</option>
		<option>bitem2</option>
		<option>bitem3</option>
		<option>ditem4</option>
		<option>eitem5</option>
	</select>
	<h3>方式二：创建下拉列表框从input标记</h3>
	<input
	 data-options="valueField:'id',textField:'name',url:'/json/data.json'"
	  class="easyui-combobox" type="text" name="dept">
	<h3>方式三：使用js代码动态创建combobox</h3>
	<input type="text" id="test">
	<script type="text/javascript">
		$(function(){
			$("#test").combobox({
				valueField:'id',
				textField:'name',
				url:'/zjs_bos/json/data.json'
			});
		});
	</script>
</body>
</html>
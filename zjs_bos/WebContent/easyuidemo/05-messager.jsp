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
		 	//show
		 	$.messager.show({
			  title:'提示信息',  	//提示标题
			  msg:'欢迎您登录BOS系统',  	//内容
			  timeout:2000,  	//2秒钟后自动消失
			  showType:'slide'  //动画效果
			});
		 	
		 	//alert
		 	//$.messager.alert('标题','提示内容','question');
		 	
		 	//confirm
		 	//$.messager.confirm('提示信息','你确定要删除当前数据吗？',function(data){
		 		//alert(data);
		 	//});
		 	
		 	//prompt
		 	//$.messager.prompt('标题','请输入您的姓名',function(data){
		 		//alert('你好：'+data);
		 	//});
		 	
		 	//progress
		 	$.messager.progress();
		 	
		 	window.setTimeout(function(){
		 		$.messager.progress('close'); //关闭进度条
		 	}, 2000);
	 	});
	 </script>
</head>
<body>
</body>
</html>
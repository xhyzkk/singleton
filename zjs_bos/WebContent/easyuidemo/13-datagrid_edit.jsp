<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>datagrid编辑功能使用</title>
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
	$(function(){
		var myIndex;
		$("#grid").datagrid({
			onAfterEdit:function(rowIndex,rowData,changes){
				//当结束编辑后，执行此方法，发送ajax请求，将当前修改的数据提交到服务端，完成数据库的更新操作
				var url = "test";
				$.post(url,rowData,function(data){
					
				});
			},
			columns:[[
			          {field:'id',title:'编号',checkbox:true},//每个json表示一列的信息
			          {field:'name',title:'姓名',
			        	  editor:{
			        		  type:'validatebox',
			        		  options:{
			        			  required:true
			        		  }
			        	  }
			          },
			          {field:'age',title:'年龄',
			        	  editor:{
			        		  type:'validatebox',
			        		  options:{
			        			  required:true
			        		  }
			        	  }  
			          }
			          ]],
			url:'${pageContext.request.contextPath }/json/data.json',
			toolbar:[
			         {text:'新增一行',iconCls:'icon-add',handler:function(){
			        	 $("#grid").datagrid("insertRow",{
			        		 index:0,//在表格的第一行插入
			        		 row:{}//插入空行
			        	 });
			        	 myIndex = 0;
			        	 //将第一行变为编辑状态
			        	 $("#grid").datagrid("beginEdit",0);
			         }},//每个json对应一个按钮
			         {text:'编辑',iconCls:'icon-edit',handler:function(){
			        	 //将当前选中的行开启编辑状态
			        	 var rows = $("#grid").datagrid("getSelections");
			        	 if(rows.length == 1){
			        		 //选中了一行，可以开启编辑状态
			        		 //获得当前选中行的索引
			        		 var index = $("#grid").datagrid("getRowIndex",rows[0]);
				        	 $("#grid").datagrid("beginEdit",index);
				        	 myIndex = index;
			        	 }else{
			        		 //不能开启编辑状态
			        	 }
			         }},
			         {text:'保存',iconCls:'icon-save',handler:function(){
			        	 //结束编辑状态
			        	 $("#grid").datagrid("endEdit",myIndex);
			         }}
			         ]
		});
	});
</script>
</head>
<body>
	<table id="grid"></table>
</body>
</html>
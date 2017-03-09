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
	<!-- 引入ztree资源文件 -->
	 <link rel="stylesheet"
	  href="${pageContext.request.contextPath}/js/ztree/zTreeStyle.css" type="text/css">
	<script type="text/javascript"
	 src="${pageContext.request.contextPath}/js/ztree/jquery.ztree.all-3.5.js"></script>
	 <script type="text/javascript">
	 	<!--创建ztree树-->
	 	var setting = {};//树的所有属性使用默认
	 	//构造json数据----标准json数据
	 	var zNodes = [
	 	              {name:'节点一',children:[
	 	                                    	{name:'节点11',children:[
	 	                                    	                       	{name:'节点111'},
	 	                                    	                       	{name:'节点112'}
	 	                                    	                       ]},
	 	                                    	{name:'节点12'}
	 	                                    ]},
	 	              {name:'节点二'},
	 	              {name:'节点三'}
	 	              ];
	 	$(function(){
		 	//初始化树
		 	$.fn.zTree.init($("#myTree"), setting, zNodes);
	 	});
	 </script>
</head>
<body class="easyui-layout">
	<!-- 每个div是一个区域 -->
	<div data-options="region:'north',title:'XXX管理系统'" style="height: 150px">北部区域</div>
	<div data-options="region:'west',title:'系统菜单'" style="width: 200px">
		<!-- 折叠面板 -->
		<div class="easyui-accordion" data-options="fit:true">
			<!-- 每个子div是一个面板 -->
			<div data-options="iconCls:'icon-search'" title="面板一">
				<script type="text/javascript">
					function addTabs(){
						//添加选项卡
						$("#tabs").tabs("add",{
							iconCls:'icon-help',
							closable:true,
							title:'百度',//指定添加出来的选项卡的标题
							content:'<iframe frameborder="0" width="100%" height="100%" src="http://www.baidu.com"></iframe>'
						});
					}
				</script>
				
				<a onclick="addTabs();" id="baidulink" class="easyui-linkbutton">百度</a>
			</div>
			<div title="面板二">
				<ul id="myTree" class="ztree"></ul>
			</div>
			<div title="面板三">
				<script type="text/javascript">
					function onClick(event, treeId, treeNode, clickFlag) {
						//alert(treeNode.name);
						var page = treeNode.page;
						if(page != undefined){
							//判断当前选项卡是否存在
							var ex = $("#tabs").tabs("exists",treeNode.name);
							if(ex){
								//已经存在，选中选项卡
								$("#tabs").tabs("select",treeNode.name);
							}else{
								//不存在，添加
								//动态添加选项卡面板
								$("#tabs").tabs("add",{
									closable:true,
									title:treeNode.name,
									content:'<iframe frameborder="0" width="100%" height="100%" src="'+page+'"></iframe>'
								});
							}
						}
					}
					var setting2 = {
							data: {
								simpleData: {
									enable: true
								}
							},
							callback: {
								onClick: onClick
							}
					};//使用简单json数据
					//构造节点数据----使用简单json数据
					var zNodes2 = [
									{ id:1, pId:0, name:"父节点1 - 展开", open:true},
									{ id:11, pId:1, name:"父节点11 - 折叠"},
									{ id:111, pId:11, name:"叶子节点111"},
									{ id:112, pId:11, name:"叶子节点112"},
									{ id:113, pId:11, name:"叶子节点113"},
									{ id:114, pId:11, name:"百度",page:'http://www.baidu.com'}
					               ];
					//初始化ztree树
					$(function(){
						$.fn.zTree.init($("#myTree2"), setting2, zNodes2);
					});
				</script>
				<ul id="myTree2" class="ztree"></ul>
			</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<!-- 选项卡面板 -->
		<div id="tabs" class="easyui-tabs" data-options="fit:true">
			<!-- 每个子div是一个选项卡 -->
			<div data-options="closable:true,iconCls:'icon-reload'" title="选项卡一">
				选项卡一内容
			</div>
			<div title="选项卡二">
				选项卡二内容
			</div>
			<div title="选项卡三">
				选项卡三内容
			</div>
		</div>
	</div>
	<div data-options="region:'south'" style="height: 50px">南部区域</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
	<%@ include file="/WEB-INF/jsp/common/resource.jspf"%>
	<title>zmock平台</title>
	<script type="text/javascript">

		function refreshCollections(){
            $('#collectionsTree').tree("reload");
		}

		function submitAddForm(){
			$("#addCollectionSelect").combobox('setValue',$('#addCollectionSelect').combobox('getText'));
            submitForm({'formId':'addCollectionForm','url':'${ctx}/zmock/add','dialogId':'addCollection','fn':'refreshCollections()'});
		}

        function submitEditForm(){
            $("#editCollectionSelect").combobox('setValue',$('#editCollectionSelect').combobox('getText'));
            submitForm({'formId':'editCollectionForm','url':'${ctx}/zmock/edit','dialogId':'editCollection','fn':'refreshCollections()'});
        }

	</script>
</head>
<body id="zmockFrame" class="easyui-layout">
	<div id="north" data-options="region:'north'" style="border:0px; height: 50px; background:url('${ctx}/zmock/resources/img/rehd.png') 0px 0px repeat-x scroll transparent">
	    <div style="height: 34px; padding:15px 15px 0px 18px; border: 0px; font-size:18px; background: url('${ctx}/zmock/resources/img/bghd.png') no-repeat scroll 0 0 transparent; ">
	    	<input  type ="hidden" id="sys_index_flag"/>
	        <span class="logo" style="font-weight:bold;">zmock---你值得拥有!</span>
		</div>
	</div>
    <div region="south" style="height: 25px; background: #D2E0F2;">
        <div class="footer">zmock@copyright</div>
    </div>

	<!-- 左边菜单栏 -->
	<div id="west" region="west" split="true" title="COLLECTIONS" iconCls="icon-tip" style="width:300px;padding:1px;overflow:hidden;">
		<%@ include file="collections.jsp"%>
	</div>
	<!-- 中间内容显示 -->
	<div id="center" region="center" style="overflow: hidden;">
		<%@ include file="content.jsp"%>
	</div>
	<div id="addCollection" class="easyui-dialog" title="添加COLLECTION" data-options="iconCls:'icon-save',resizable:true,modal:true"
		 closed="true" buttons="#addcollection-dlg-buttons" style="width:300px;height:400px;padding:5px">
		<div id="addcollection-dlg-buttons">
			<a href="#" class="easyui-linkbutton" iconcls="icon-save" onclick="submitAddForm()">确定</a>
			<a href="#" class="easyui-linkbutton" iconcls="icon-cancel" onclick="$('#addCollection').dialog('close')">取消</a>
		</div>
	</div>
	<div id="editCollection" class="easyui-dialog" title="编辑COLLECTION" data-options="iconCls:'icon-save',resizable:true,modal:true"
		 closed="true" buttons="#editcollection-dlg-buttons" style="width:300px;height:400px;padding:5px">
		<div id="editcollection-dlg-buttons">
			<a href="#" class="easyui-linkbutton" iconcls="icon-save" onclick="submitEditForm()">确定</a>
			<a href="#" class="easyui-linkbutton" iconcls="icon-cancel" onclick="$('#editCollection').dialog('close')">取消</a>
		</div>
	</div>
</body>
</html>
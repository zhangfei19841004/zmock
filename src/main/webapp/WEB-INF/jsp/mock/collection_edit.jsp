<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script type="text/javascript">

    $("#editCollectionSelect").combobox({
		onLoadSuccess: function () {
            $("#editCollectionSelect").combobox("setValue","${collectionName}");
        }
	});
</script>

<form id="editCollectionForm" method="post" data-options="validate:true">
	<div style="margin-top:15px;margin-bottom:20px">
		<div style="margin-bottom: 5px">COLLECTION:</div>
		<input  id="editCollectionSelect" class="easyui-combobox" data-options="required:true,valueField:'id',textField:'text',url:'${ctx}/zmock/collections'," name="collectionName" style="width:100%;height:32px">
	</div>
	<c:if test="${showMockName}">
		<div style="margin-bottom:20px">
			<div style="margin-bottom: 5px">MOCK接口:</div>
			<input class="easyui-textbox" name="mockName" value="${mockName}" data-options="prompt:'请输入MOCK接口名称',required:true" style="width:100%;height:32px">
		</div>
	</c:if>
	<input type="hidden" name="oldCollectionName" value="${collectionName}">
	<input type="hidden" name="oldMockName" value="${mockName}">
</form>
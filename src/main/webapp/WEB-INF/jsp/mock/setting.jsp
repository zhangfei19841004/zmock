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
			function setting(param){
				$.post("${ctx}/zmock/setting",param,function(json){
					if(json.retCode=='200'){
                        $.messager.alert('消息',json.retMsg);
                    }else{
                        $.messager.alert('消息','保存失败');
                    }
                });
			}
		</script>
	</head>
	<body>
		<div id="mockSetting" class="easyui-panel" fit="true" style="margin: 5px;background:#f5f9fc">
			<table cellpadding="0" cellspacing="0" class="tab_info" style="width: 50%; margin: 10px auto">
				<tr>
					<td class="inquire_item" style="width: 30%">mock数据保存目录:</td>
					<td class="inquire_form" style="width: 70%">
						<input class="easyui-textbox" data-options="prompt:'Enter something here...',iconWidth: 22,
										icons:[{
											iconCls:'icon-ok',
											handler: function(e){
												setting({'mockDataDir':$(e.data.target).textbox('getValue')});
											}
										}]" style="width: 80%" name="mockDataDir" value="${mockDataDir}"/>
					</td>
				</tr>
				<tr>
					<td class="inquire_item" style="width: 30%">mock数据数量:</td>
					<td class="inquire_form" style="width: 0%">
						<input class="easyui-textbox" data-options="prompt:'Enter something here...',iconWidth: 22,
										icons:[{
											iconCls:'icon-ok',
											handler: function(e){
												setting({'mockDataCount':$(e.data.target).textbox('getValue')});
											}
										}]" style="width: 80%" name="mockDataCount" value="${mockDataCount}"/>
					</td>
				</tr>
				<tr>
					<td class="inquire_item" style="width: 30%">mock数据删除权限:</td>
					<td class="inquire_form" style="width: 70%">
						<select class="easyui-combobox" style="width: 80%"
							   name="mockDataRole"
							   data-options="icons:[{
									iconCls:'icon-ok',
									handler: function(e){
										setting({'mockDataRole':$(e.data.target).combobox('getValue')});
									}
								}]">
							<option value="1" <c:if test="${mockDataRole==1}">selected</c:if>>允许</option>
							<option value="0" <c:if test="${mockDataRole==0}">selected</c:if>>不允许</option>
						</select>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>